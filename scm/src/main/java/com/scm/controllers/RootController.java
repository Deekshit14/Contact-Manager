package com.scm.controllers;

import com.scm.entities.User;
import com.scm.helpers.Helper;
import com.scm.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

// This is used to display username at navbar when logged in
@ControllerAdvice
public class RootController {

    @Autowired
    private UserService userService;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @ModelAttribute
    public void addLoggedInUserInformation(Model model, Authentication authentication) {
        if (authentication == null)
            return;

        System.out.println("Adding logged in user details in user information to the model");
        String username = Helper.getEmailOfLoggedInUser(authentication);    // contains user email
//        logger.info("User logged in: {}", username);

        // Fetch from database
        User user = userService.getUserByEmail(username);   // This contains User details using the email(username)

        System.out.println(user);
        System.out.println(user.getName());     // User name
        System.out.println(user.getEmail());    // User email
        model.addAttribute("loggedInUser", user);


    }
}
