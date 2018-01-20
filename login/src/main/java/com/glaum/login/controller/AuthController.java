package com.glaum.login.controller;

import com.glaum.login.configuration.RefererRedirectionAuthenticationSuccessHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@Controller
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @RequestMapping(value={"/login"}, method = RequestMethod.GET)
    public String login(@RequestParam(value = "error", required = false) boolean error, Map<String, Object> model) throws IOException {
        if(error) {
            model.put("message", "Login failed. Please check username and password.");
        }
        return "th_login";
    }

    @RequestMapping(value={"/home"}, method = RequestMethod.GET)
    public String home() throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LOGGER.info(authentication.getName());
        return "home";
    }

    @RequestMapping(value={"/accessdenied"}, method = RequestMethod.GET)
    public String accessdenied() throws IOException {
        return "access_denied";
    }

}
