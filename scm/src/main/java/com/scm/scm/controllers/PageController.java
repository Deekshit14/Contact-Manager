package com.scm.scm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageController {

    @RequestMapping("/home")
    public String home(Model model)
    {
        // Sending data for view
        model.addAttribute("name", "Smart Contact Manager");
        model.addAttribute("Language", "SpringBoot");
        model.addAttribute("email", "deekshit1404");
        return "home";
    }

    // about route
    @RequestMapping("/about")
    public String aboutPage(Model model)
    {
        model.addAttribute("isLogin", true);
        System.out.println("About page loading");
        return "about";
    }

    // Services
    @RequestMapping("/services")
    public String services()
    {
        System.out.println("Services page loading");
        return "services";
    }

    // contact page
    @GetMapping ("/contact")
    public String contact()
    {
        return "contact";
    }

    @GetMapping ("/login")
    public String login()
    {
        return "login";
    }

    @GetMapping ("/register")
    public String register()
    {
        return "register";
    }
}
