package com.scm.scm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
    @RequestMapping("services")
    public String services()
    {
        System.out.println("Services page loading");
        return "services";
    }

}
