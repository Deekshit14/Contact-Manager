package com.scm.config;

import com.scm.entities.Providers;
import com.scm.entities.User;
import com.scm.helpers.AppConstants;
import com.scm.repositories.UserRepo;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.UUID;


// Strategy used to handle a Successful authentication using oauth2
@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {


    @Autowired
    private UserRepo userRepo;

    Logger logger = LoggerFactory.getLogger(OAuthAuthenticationSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        logger.info("OAuthAuthenticationSuccessHandler");

        // Identify the provider
//        var oauth2AuthenticationToken = (OAuth2AuthenticationToken)authentication;
        OAuth2AuthenticationToken oauth2AuthenticationToken = (OAuth2AuthenticationToken)authentication;
        String authorizedClientRegistrationId = oauth2AuthenticationToken.getAuthorizedClientRegistrationId();
        logger.info(authorizedClientRegistrationId);

//        var oauthUser = (DefaultOAuth2User)authentication.getPrincipal();
        DefaultOAuth2User oauthUser = (DefaultOAuth2User)authentication.getPrincipal();
        oauthUser.getAttributes().forEach( (key, value) -> {
            logger.info(key + " : " + value);
        });

        User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setRoleList(List.of(AppConstants.ROLL_USER));
        user.setEmailVerified(true);
        user.setEnabled(true);

        if (authorizedClientRegistrationId.equalsIgnoreCase("google")) {
            // google
            user.setEmail(oauthUser.getAttribute("email").toString());
            user.setProfilePic(oauthUser.getAttribute("picture").toString());
            user.setName(oauthUser.getAttribute("name").toString());
            user.setProviderUserId(oauthUser.getName());
            user.setProvider(Providers.GOOGLE);
            user.setPassword("password");
            user.setAbout("This account is created using google..");

        }

        else if (authorizedClientRegistrationId.equalsIgnoreCase("github")) {
            // github
            if (oauthUser.getAttribute("email") != null) {
                user.setEmail(oauthUser.getAttribute("email").toString());
            }
            else {
                user.setEmail(oauthUser.getAttribute("login").toString() + "@gmail.com");
            }

            user.setProfilePic(oauthUser.getAttribute("avatar_url").toString());
            user.setName(oauthUser.getAttribute("name").toString());
            user.setProviderUserId(oauthUser.getName());
            user.setProvider(Providers.GITHUB);
            user.setPassword("password");
            user.setAbout("This account is created using github..");
        }
        else {
            logger.info("Unknown Provider");
        }


        /*

        DefaultOAuth2User user = (DefaultOAuth2User)authentication.getPrincipal();

//        logger.info(user.getName());
//
//        user.getAttributes().forEach((key, value) -> {
//            logger.info("{} => {}", key, value);
//        });
//
//        logger.info(user.getAuthorities().toString());


        // Getting the data
        String email = user.getAttribute("email").toString();
        String name = user.getAttribute("name").toString();
        String picture = user.getAttribute("picture").toString();

        // create user and save in database
        User user1 = new User();
        user1.setName(name);
        user1.setEmail(email);
        user1.setProfilePic(picture);
        user1.setPassword("password");
        user1.setUserId(UUID.randomUUID().toString());
        user1.setProvider(Providers.GOOGLE);
        user1.setEnabled(true);
        user1.setEmailVerified(true);
        user1.setProviderUserId(user.getName());
        user1.setRoleList(List.of(AppConstants.ROLL_USER));
        user1.setAbout("This account is created using google..");

        User saveUser = userRepo.findByEmail(email).orElse(null);
        if (saveUser == null) {
            userRepo.save(user1);
            logger.info("User saved " + email);
        }

        //        response.sendRedirect("/home");

         */

        User saveUser = userRepo.findByEmail(user.getEmail()).orElse(null);     // If user with this email not exists then save the user in database
        if (saveUser == null) {
            userRepo.save(user);
        }

        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");     // Used for redirecting once authentication is successful using oauth2

    }

}
