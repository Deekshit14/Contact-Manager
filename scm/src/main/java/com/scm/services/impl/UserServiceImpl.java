package com.scm.services.impl;

import com.scm.entities.User;
import com.scm.helpers.AppConstants;
import com.scm.helpers.ResourceNotFoundException;
import com.scm.repositories.UserRepo;
import com.scm.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean isUserExistByPhoneNumber(String phoneNumber) {
        return userRepo.findByPhoneNumber(phoneNumber).isPresent();
    }

    @Override
    public User saveUser(User user) {
        // Check if email or phone number already exists
        if (isUserExistByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        if (isUserExistByPhoneNumber(user.getPhoneNumber())) {
            throw new RuntimeException("Phone number already exists");
        }

        // User id generating
        String userId = UUID.randomUUID().toString();
        user.setUserId(userId);

        // Password encoding
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Setting the default user role
        user.setRoleList(List.of(AppConstants.ROLL_USER));


        logger.info(user.getProvider().toString());

        // Save the new user to the database
        return userRepo.save(user);
    }


    @Override
    public Optional<User> getUserById(String id) {
        return userRepo.findById(id);
    }

    @Override
    public Optional<User> updateUser(User user) {
        User user2 = userRepo.findById(user.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));

//        user2.setName(user.getName());
//        user2.setEmail(user.getEmail());
//        user2.setPassword(user.getPassword());
//        user2.setPhoneNumber(user.getPhoneNumber());
//        user2.setAbout(user.getAbout());
//        user2.setProfilePic(user.getProfilePic());
//        user2.setEnabled(user.isEnabled());
//        user2.setEmailVerified(user.isEmailVerified());
//        user2.setPhoneVerified(user.isPhoneVerified());
//        user2.setProvider(user.getProvider());
//        user2.setProviderUserId(user.getProviderUserId());
//
//        // save the changes
//        User save = userRepo.save(user2);
//
//        return Optional.ofNullable(save);

        // Copy properties from 'user' to 'user2'
        BeanUtils.copyProperties(user, user2, "userId", "contacts");
        // Save the changes
        User savedUser = userRepo.save(user2);
        return Optional.ofNullable(savedUser);
    }

    @Override
    public void deleteUser(String id) {
        User user2 = userRepo.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException("User Not Found"));

        userRepo.delete(user2);
    }

    @Override
    public boolean isUserExist(String userId) {
        User user = userRepo.findById(userId).orElse(null);
        return user != null ? true : false;
    }

    @Override
    public boolean isUserExistByEmail(String email) {
        User user = userRepo.findByEmail(email).orElse(null);
        return user != null ? true : false;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

//    @Override
//    public User getUserByEmail(String email) {
//        return userRepo.findByEmail(email).orElse(null);
//
//    }

}
