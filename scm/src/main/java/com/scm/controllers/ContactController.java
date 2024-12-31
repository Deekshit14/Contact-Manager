package com.scm.controllers;

import com.scm.entities.Contact;
import com.scm.entities.User;
import com.scm.forms.ContactForm;
import com.scm.forms.ContactSearchForm;
import com.scm.helpers.AppConstants;
import com.scm.helpers.Helper;
import com.scm.helpers.Message;
import com.scm.helpers.MessageType;
import com.scm.services.ContactServices;
import com.scm.services.ImageService;
import com.scm.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping ("/user/contacts")
public class ContactController {

    private Logger logger = org.slf4j.LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private ImageService imageService;

    @Autowired
    private ContactServices contactServices;

    @Autowired
    private UserService userService;

    // add contact page: handler
    @GetMapping ("/add")
    public String addContactView(Model model) {
        ContactForm contactForm = new ContactForm();
//        contactForm.setName("Deekshit M");
        contactForm.setFavorite(true);
        model.addAttribute("contactForm", contactForm);
        return "user/add_contact";
    }


    @PostMapping ("/add")
    public String saveContact(@Valid @ModelAttribute ContactForm contactForm, BindingResult result,
                              Authentication authentication, HttpSession session) {

        // Processing the form data
        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> logger.info(error.toString()));
            session.setAttribute("message", new Message("Please correct the mistakes", MessageType.red));
            return "user/add_contact";
        }

        String userName = Helper.getEmailOfLoggedInUser(authentication);

        // form -> contact
        User user = userService.getUserByEmail(userName);

        // Process the contact picture
        // image process
//        logger.info("file information : {}", contactForm.getContactImage().getOriginalFilename());

        String filename = UUID.randomUUID().toString();

        String fileURL = imageService.uploadImage(contactForm.getContactImage(), filename);


        try {
            Contact contact = new Contact();
            contact.setName(contactForm.getName());
            contact.setEmail(contactForm.getEmail());
            contact.setPhoneNumber(contactForm.getPhoneNumber());
            contact.setAddress(contactForm.getAddress());
            contact.setDescription(contactForm.getDescription());
            contact.setUser(user);
            contact.setWebsiteLink(contactForm.getWebsiteLink());
            contact.setLinkedInLink(contactForm.getLinkedInLink());
            contact.setPicture(fileURL);
            contact.setCloudinaryImagePublicId(filename);
            contact.setFavorite(contactForm.isFavorite());

            contactServices.save(contact);

            session.setAttribute("message", new Message("You have successfully added a new contact", MessageType.green));
        }
        catch (RuntimeException e) {
            session.setAttribute("message", new Message(e.getMessage(), MessageType.red));
            return "user/add_contact";
        }

        // set message to be displayed on the view after submitting the form


        return "redirect:/user/contacts/add";
    }


    // view contacts
    @GetMapping
    public String viewContacts(@RequestParam(value = "page", defaultValue = "0") int page,
                               @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE + "") int size,
                               @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
                               @RequestParam(value = "direction", defaultValue = "asc") String direction,
                               Model model, Authentication authentication) {
        // Load all the contacts
        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmail(username);

        Page<Contact> pageContact = contactServices.getByUser(user, page, size, sortBy, direction);

        model.addAttribute("pageContact", pageContact);
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
        model.addAttribute("contactSearchForm", new ContactSearchForm());
        return "user/contacts";
    }

    // search handler
    @GetMapping("/search")
    public String searchHandler(@ModelAttribute ContactSearchForm contactSearchForm,
                                @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE + "") int size,
                                @RequestParam(value = "page", defaultValue = "0") int page,
                                @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
                                @RequestParam(value = "direction", defaultValue = "asc") String direction,
                                Model model,
                                Authentication authentication
                                ) {
        logger.info("field {} keyword {}",contactSearchForm.getField(), contactSearchForm.getValue());

        var user = userService.getUserByEmail(Helper.getEmailOfLoggedInUser(authentication));

        Page<Contact> pageContact;
        if (contactSearchForm.getField().equalsIgnoreCase("name")) {
            pageContact = contactServices.searchByName(contactSearchForm.getValue(), size, page, sortBy, direction, user);
        }
        else if (contactSearchForm.getField().equalsIgnoreCase("email")) {
            pageContact = contactServices.searchByEmail(contactSearchForm.getValue(), size, page, sortBy, direction, user);
        }
        else if (contactSearchForm.getField().equalsIgnoreCase("phone")){
            pageContact = contactServices.searchByPhoneNumber(contactSearchForm.getValue(), size, page, sortBy, direction, user);
        }
        else {
            pageContact = contactServices.searchByName(contactSearchForm.getValue(), size, page, sortBy, direction, user);
        }

        logger.info("pageContact {}", pageContact);
        model.addAttribute("contactSearchForm", contactSearchForm);
        model.addAttribute("pageContact", pageContact);
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);

        return "user/search";
    }

}
