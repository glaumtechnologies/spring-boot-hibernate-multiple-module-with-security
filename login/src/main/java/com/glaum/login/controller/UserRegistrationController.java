package com.glaum.login.controller;

import com.glaum.login.model.User;
import com.glaum.login.service.UserRegistrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;

@Controller
public class UserRegistrationController {

    @Autowired
    private AuthController authController;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRegistrationController.class);

    @Autowired
    private UserRegistrationService userRegistrationService;

    @RequestMapping(value={"/registration"}, method = RequestMethod.GET, consumes = {})
    public String registration(Model model, Map<String, Object> modelMap) throws IOException {
        model.addAttribute("user", new User());
        return "th_registration";
    }

    @RequestMapping(value={"/registration"}, method = RequestMethod.POST, consumes = APPLICATION_FORM_URLENCODED_VALUE)
    public String userCreate(@ModelAttribute User user, Map<String, Object> modelMap,
                             Model model,
                             HttpServletRequest httpServletRequest) throws IOException {
        boolean isSuccess = userRegistrationService.createUser(user);
        LOGGER.info("User Registration isSuccess = "+ isSuccess);
        if(!isSuccess) {
            modelMap.put("message", "Registration failed. Please check username.");
            return registration(model, modelMap);
        } else {
            return authController.login(false, null, httpServletRequest);
        }
    }

    @RequestMapping(value={"/registration/admin"}, method = RequestMethod.GET)
    public String registrationAdmin(Model model, Map<String, Object> modelMap) throws IOException {
        model.addAttribute("user", new User());
        return "th_registration_admin";
    }

    @RequestMapping(value={"/registration/admin"}, method = RequestMethod.POST, consumes = APPLICATION_FORM_URLENCODED_VALUE)
    public String adminCreate(@ModelAttribute User user, Map<String, Object> modelMap,
                              Model model,
                              HttpServletRequest httpServletRequest) throws IOException {
        boolean isSuccess = userRegistrationService.createAdmin(user);
        LOGGER.info("User Admin Registration isSuccess = "+ isSuccess);
        if(!isSuccess) {
            modelMap.put("message", "Registration failed. Please check username.");
            return registrationAdmin(model, modelMap);
        } else {
            return authController.login(false, null, httpServletRequest);
        }


    }

}
