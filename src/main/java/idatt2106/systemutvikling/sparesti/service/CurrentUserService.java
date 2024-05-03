package idatt2106.systemutvikling.sparesti.service;

import idatt2106.systemutvikling.sparesti.security.SecurityConfig;
import java.util.Map;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * A service for delivering information about the current user. The information is based on the
 * authentication registered in the current SecurityContext.
 */
@Service
public class CurrentUserService {

  public static String KEY_USERNAME = "Username";

  /**
   * Method to get the current username. If the user is not authenticated, null is returned. The
   * username is stored in the SecurityContext of the token.
   *
   * @return the username of the current user
   */
  public static String getCurrentUsername() {
      if (!SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
          return null;
      }

    Map<String, Object> userDetails = (Map<String, Object>) SecurityContextHolder.getContext()
        .getAuthentication().getDetails();

    return (String) userDetails.get(KEY_USERNAME);
  }

  /**
   * Method to check if the current user is a complete user. A user is complete if they have two
   * accounts.
   *
   * @return true if the user is complete, false otherwise
   */
  public static boolean isCompleteUser() {
    return SecurityContextHolder
        .getContext()
        .getAuthentication()
        .getAuthorities()
        .contains(new SimpleGrantedAuthority(SecurityConfig.ROLE_COMPLETE));
  }

  /**
   * Method to get the details of the current user. The details are stored in the SecurityContext of
   * the token.
   *
   * @return the details of the current user
   */
  public Object getUserDetails() {
    return SecurityContextHolder.getContext().getAuthentication().getDetails();
  }
}
