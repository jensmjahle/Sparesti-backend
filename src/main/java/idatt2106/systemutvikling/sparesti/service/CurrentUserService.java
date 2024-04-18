package idatt2106.systemutvikling.sparesti.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * A service for delivering information about the current user.
 * The information is based on the authentication registered in the current SecurityContext.
 */
@Service
public class CurrentUserService {

    public static String KEY_USERNAME = "Username";

    /**
     * @return the username of the currently authenticated user.
     */
    public static String getCurrentUsername() {
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated())
            return null;

        Map<String, Object> userDetails = (Map<String, Object>) SecurityContextHolder.getContext().getAuthentication().getDetails();

        return (String) userDetails.get(KEY_USERNAME);
    }
}
