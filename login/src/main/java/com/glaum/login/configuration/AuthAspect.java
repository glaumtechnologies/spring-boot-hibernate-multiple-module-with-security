package com.glaum.login.configuration;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.glaum.login.entity.permission;
import com.glaum.login.service.AuthorizationImpl;
import com.google.common.collect.Lists;

import groovy.util.logging.Slf4j;

@Slf4j
@Aspect
@Component
public class AuthAspect {
@Autowired
   AuthorizationImpl authBean;


   
  @Before("@annotation(Authorized) && args(httpSession,request,res) ")
   public static void before(JoinPoint jt,HttpSession httpSession,HttpServletRequest request,HttpServletResponse res){
	 
	  boolean authper=false;
	  String roleid=""; 
      List<permission> lstobj=Lists.newArrayList(); 
      int bit=-1;
      
      MethodSignature signature = (MethodSignature) jt.getSignature();
      Method method = signature.getMethod();
      
      Authorized objauth=method.getAnnotation(Authorized.class);
      String[] arrofpermission = objauth.keys();
      System.out.println("printing aspect val"+((Arrays.toString(arrofpermission))));   
      
      

      
      for (Field f : httpSession.getClass().getDeclaredFields()) {
          f.setAccessible(true);
          Object o;
          try {
             HttpSession s= (HttpSession) f.get(httpSession);	              
             lstobj= (List<com.glaum.login.entity.permission>) s.getAttribute("permissionval");
             roleid=s.getAttribute("roleid").toString();
          } catch (Exception e) {
              o = e;
          }	           
      }
     // System.out.println("role"+roleid);
      for(String s:arrofpermission)
      {
      for (permission per : lstobj) {
          if (per.getname().equalsIgnoreCase(s)) {
          	bit=per.getbit();          	
          	if((Integer.parseInt(roleid) & bit) == bit)
          	{
          		authper=true;
          	}
          }
      }
      }
//	System.out.println((Integer.parseInt(roleid) & bit) == bit);
//      authper=((Integer.parseInt(roleid) & bit) == bit);
      if(!authper)
		try {
			res.sendRedirect(request.getContextPath() + "/accessdenied");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

   } 
//   




   
}