package com.scm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping ("/user")
public class UserController {

    // User dashboard page
    @GetMapping("/dashboard")
    public String userDashboard() {
        System.out.println("User Dashboard");
        return "user/dashboard";
    }


    // User Profile page
    @GetMapping("/profile")
    public String userProfile() {
        System.out.println("User Profile");
        return "user/profile";
    }


    // User add contacts page


    // user view contacts page


    // user edit contact page


    // user delete contact page


}
