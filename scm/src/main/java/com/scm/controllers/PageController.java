package com.scm.controllers;

import com.scm.entities.User;
import com.scm.forms.UserForm;
import com.scm.helpers.Message;
import com.scm.helpers.MessageType;
import com.scm.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class PageController {

    @Autowired
    private UserService userService;


    @GetMapping({"/", "/home"})
    public String home(Model model)
    {
        // Sending data for view
        model.addAttribute("name", "Smart Contact Manager");
        model.addAttribute("Language", "SpringBoot");
        model.addAttribute("email", "deekshit1404");
        return "home";
    }

    // about route
    @GetMapping("/about")
    public String aboutPage(Model model)
    {
        model.addAttribute("isLogin", true);
//        System.out.println("About page loading");
        return "about";
    }

    // Services
    @GetMapping("/services")
    public String services()
    {
//        System.out.println("Services page loading");
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
    public String register(Model model)
    {
        UserForm userForm = new UserForm();

//        userForm.setName("Deekshit");     // We can set Default value
        model.addAttribute("userForm", userForm);

        return "register";
    }


    // Registration Process page
    @PostMapping ("/do-register")
    public String processRegister(@Valid @ModelAttribute UserForm userForm, BindingResult rBindingResult, HttpSession session) {

        // Fetch Form data


        // UserForm
        System.out.println(userForm);


        // Validate Form data
        if (rBindingResult.hasErrors()) {
            return "register";
        }

        try {
            User user = new User();
            user.setName(userForm.getName());
            user.setEmail(userForm.getEmail());
            user.setPassword(userForm.getPassword());
            user.setPhoneNumber(userForm.getPhoneNumber());
            user.setAbout(userForm.getAbout());
            user.setProfilePic("https://th.bing.com/th/id/OIP.xkVR0PWQY69tZCzh1vDnagHaHa?rs=1&pid=ImgDetMain");

            // Save to Database (UserService)
            User savedUser = userService.saveUser(user);
//            System.out.println("user saved");
            session.setAttribute("message", new Message("Registration successful", MessageType.green));
        }
        catch (RuntimeException e) {
            session.setAttribute("message", new Message(e.getMessage(), MessageType.red));
            return "register";
        }

        // Redirect to login page
        return "redirect:/register";    // Redirecting to same page i.e, register
    }
}
