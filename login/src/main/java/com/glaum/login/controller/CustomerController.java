package com.glaum.login.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.glaum.login.configuration.Authorized;
import com.glaum.login.entity.permission;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class CustomerController {

	@Autowired
	HttpSession httpsessionobj;
	
	
	
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);
    
    //@PreAuthorize("hasPermission('customer', #httpSession)")
    @RequestMapping(value={"/customer"}, method = RequestMethod.GET)
    @Authorized(keys = "view_dashboard")
    public String customer(HttpSession httpSession,HttpServletRequest request,HttpServletResponse res) throws IOException {
    	
        return "admin_customer";
    }
    
    @RequestMapping(value={"/quote_delete"}, method = RequestMethod.GET)
    @Authorized(keys = {"quote_delete","quote_read","quote_create"})
    public String customerdel(HttpSession httpSession,HttpServletRequest request,HttpServletResponse res) throws IOException {
    	
        return "admin_customer";
    }
    
   

}
