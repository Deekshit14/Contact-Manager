package com.scm.controllers;

import com.scm.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping ("/user")
public class UserController {


    private Logger logger = LoggerFactory.getLogger(UserController.class);


    @Autowired
    private UserService userService;



    // User dashboard page
    @GetMapping("/dashboard")
    public String userDashboard() {
//        System.out.println("User Dashboard");
        return "user/dashboard";
    }


    // User Profile page
    @GetMapping("/profile")
    public String userProfile(Model model, Authentication authentication) {

        return "user/profile";
    }


    // User add contacts page


    // user view contacts page


    // user edit contact page


    // user delete contact page


}
