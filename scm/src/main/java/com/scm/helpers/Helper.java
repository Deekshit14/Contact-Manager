//package com.scm.helpers;
//
//import org.springframework.security.core.Authentication;
//import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
//import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
//
//import java.security.Principal;
//
//public class Helper {
//
//    public static String getEmailOfLoggedInUser(Authentication authentication) {
//
//        Principal principal = (Principal)authentication.getPrincipal();
//
//        // If logged in using email and password
//        if (principal instanceof OAuth2AuthenticatedPrincipal) {
//
//            OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken)authentication;
//            String clientId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
//
//            if (clientId.equalsIgnoreCase("google")) {
//                // If logged in using google
//                System.out.println("Getting email from google client");
//            }
//            else if (clientId.equalsIgnoreCase("github")) {
//                // If logged in using gitHub
//                System.out.println("Getting email from github client");
//            }
//
//        }
//        else
//            return principal.getName();
//        return "";
//    }
//}

package com.scm.helpers;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class Helper {

    public static String getEmailOfLoggedInUser(Authentication authentication) {

        // If authentication is an instance of OAuth2AuthenticationToken
        if (authentication instanceof OAuth2AuthenticationToken) {

            OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
            String clientId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
            OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
            String userName = "";

            if (clientId.equalsIgnoreCase("google")) {
                // Extract email from Google OAuth2User
                System.out.println("Getting email from Google client");
                userName = oAuth2User.getAttribute("email").toString();
            }

            else if (clientId.equalsIgnoreCase("github")) {
                // Extract email from GitHub OAuth2User
                System.out.println("Getting email from GitHub client");
                userName = oAuth2User.getAttribute("email") != null ? oAuth2User.getAttribute("email").toString()
                        : oAuth2User.getAttribute("login").toString() + "@gmail.com";
            }

            return userName;

        } else {
            // If logged in using standard username and password (non-OAuth2 flow)
            return authentication.getName();
        }

    }
}
