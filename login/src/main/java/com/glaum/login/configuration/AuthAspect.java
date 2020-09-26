package com.glaum.login.configuration;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.glaum.login.entity.Permission;
import com.google.common.collect.Lists;

import groovy.util.logging.Slf4j;

@Slf4j
@Aspect
@Component
public class AuthAspect {

	@Before("@annotation(Authorized) && args() ")
	public static void before(JoinPoint jointpoint) {

		boolean authallowuser = false;
		int roleid = 0;
		List<Permission> lstofpermission = Lists.newArrayList();
		int bit = -1;

		MethodSignature signature = (MethodSignature) jointpoint.getSignature();
		Method method = signature.getMethod();
		Authorized objauthorized = method.getAnnotation(Authorized.class);
		String[] arrayofpermission = objauthorized.keys();

		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		HttpServletRequest httprequest = ((ServletRequestAttributes) requestAttributes).getRequest();
		HttpServletResponse httpresponse = ((ServletRequestAttributes) requestAttributes).getResponse();
		HttpSession session = httprequest.getSession();

		roleid = (int) session.getAttribute("userRole");
		lstofpermission = (List<com.glaum.login.entity.Permission>) session.getAttribute("userPermissions");

		for (String permissionreq : arrayofpermission) {
			for (Permission availablepermission : lstofpermission) {
				if (availablepermission.getname().equalsIgnoreCase(permissionreq)) {
					bit = availablepermission.getbit();
					if ((roleid & bit) == bit) {
						authallowuser = true;
						break;
					}
				}
			}
			if(authallowuser)
				break;
		}

		if (!authallowuser)
			try {
				httpresponse.sendRedirect(httprequest.getContextPath() + "/accessdenied");
			} catch (IOException e) {
				e.printStackTrace();
			}

	}
//   

}
