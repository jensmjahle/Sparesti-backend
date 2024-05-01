package idatt2106.systemutvikling.sparesti.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration class for password encoding.
 */
@Configuration
public class PasswordConfiguration {

  /**
   * Method for creating a password encoder bean from BCryptPasswordEncoder within spring security.
   *
   * @return a password encoder bean
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}