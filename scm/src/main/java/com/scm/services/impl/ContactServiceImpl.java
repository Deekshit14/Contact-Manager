package com.scm.services.impl;

import com.scm.entities.Contact;
import com.scm.entities.User;
import com.scm.helpers.ResourceNotFoundException;
import com.scm.repositories.ContactRepo;
import com.scm.services.ContactServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ContactServiceImpl implements ContactServices {

    @Autowired
    private ContactRepo contactRepo;


    // Check if a contact with the given email already exists for the current user
    public boolean contactExistsByEmailAndUser(String email, User user) {
        return contactRepo.existsByEmailAndUser(email, user);
    }


    // Check if a contact with the given phone number already exists for the current user
    public boolean contactExistsByPhoneNumberAndUser(String phoneNumber, User user) {
        return contactRepo.existsByPhoneNumberAndUser(phoneNumber, user);
    }


    @Override
    public Contact save(Contact contact) {
        // Check if a contact with the given email already exists for the current user
        if (contactExistsByEmailAndUser(contact.getEmail(), contact.getUser())) {
            throw new RuntimeException("A contact with this email already exists.");
        }


        // Check if a contact with the given phone number already exists for the current user
        if (contactExistsByPhoneNumberAndUser(contact.getPhoneNumber(), contact.getUser())) {
            throw new RuntimeException("A contact with this phone number already exists.");
        }

        String contactId = UUID.randomUUID().toString();
        contact.setId(contactId);
        return contactRepo.save(contact);
    }


    @Override
    public Contact update(Contact contact) {
        return null;
    }


    @Override
    public List<Contact> getAll() {
        return contactRepo.findAll();
    }


    @Override
    public Contact getById(String id) {
        return contactRepo.findById(id).orElseThrow( () -> new ResourceNotFoundException("Contact not found for the given id " + id));
    }


    @Override
    public void delete(String id) {
        Contact contact = getById(id);  // multiple steps done to get exception if id not exists
        contactRepo.delete(contact);
    }


    @Override
    public List<Contact> getByUserId(String userId) {
        return contactRepo.findByUserId(userId);
    }

    @Override
    public Page<Contact> getByUser(User user, int page, int size, String sortBy, String direction) {
        Sort sort = direction.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, sort);

        return contactRepo.findByUser(user, pageable);
    }


    @Override
    public Page<Contact> searchByName(String nameKeyword, int size, int page, String sortBy, String order, User user) {
        Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, sort);
        return contactRepo.findByUserAndNameContaining(user, nameKeyword, pageable);
    }

    @Override
    public Page<Contact> searchByEmail(String emailKeyword, int size, int page, String sortBy, String order, User user) {
        Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, sort);
        return contactRepo.findByUserAndEmailContaining(user, emailKeyword, pageable);
    }

    @Override
    public Page<Contact> searchByPhoneNumber(String phoneNumberKeyword, int size, int page, String sortBy,
                                             String order, User user) {
        Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, sort);
        return contactRepo.findByUserAndPhoneNumberContaining(user, phoneNumberKeyword, pageable);
    }


}
