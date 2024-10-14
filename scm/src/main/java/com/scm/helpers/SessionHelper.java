package com.scm.helpers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


// This class is used for refreshing the page so that the page data entered previously get reset
@Component
public class SessionHelper {

    public static void removeMessage(){
        try {
            System.out.println("Removing the message from session");
            HttpSession session = ( (ServletRequestAttributes)RequestContextHolder.getRequestAttributes() )
                    .getRequest()
                    .getSession();

            session.removeAttribute("message");
        }
        catch (Exception e) {
            System.out.println("Error in SessionHelper: " + e);
        }
    }
}
