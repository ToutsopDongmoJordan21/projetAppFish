package com.example.demo.security.util;

//import com.project.caref.security.services.UserDetailsImpl;
import com.example.demo.security.services.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author JordanT
 *
 */
public final class SecurityUtils {

    private SecurityUtils() {
    }


    /**
     * Get the login of the current user.
     *
     * @return the login of the current user
     */
    public static String getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String userName = null;
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof UserDetails) {
                UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
                userName = springSecurityUser.getUsername();
            } else if (authentication.getPrincipal() instanceof UserDetailsImpl) { // (UserPrincipal) authentication.getPrincipal()
                UserDetailsImpl springSecurityUser = (UserDetailsImpl) authentication.getPrincipal();
                userName = springSecurityUser.getUsername();
            } else if (authentication.getPrincipal() instanceof String) {
                userName = (String) authentication.getPrincipal();
            }
        }
        return userName;
    }

    /**
     * Get the userID of the current user.
     *
     * @return the userID of the current user
     */
    public static Long getCurrentUserId() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        Long userId = null;
        if (authentication != null && (authentication.getPrincipal() instanceof UserDetailsImpl)) {
            UserDetailsImpl springSecurityUser = (UserDetailsImpl) authentication.getPrincipal();
            userId = springSecurityUser.getId();
        }
        return userId;
    }
}

