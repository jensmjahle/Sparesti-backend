package idatt2106.systemutvikling.sparesti.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;
import java.util.List;

@EnableWebSecurity
@AllArgsConstructor
@Configuration
public class SecurityConfig {

  public static final String BASIC = "BASIC";
  public static final String ROLE_BASIC = "ROLE_" + BASIC;
  public static final String COMPLETE = "COMPLETE";
  public static final String ROLE_COMPLETE = "ROLE_" + COMPLETE;

  private SecretsConfig secrets;

  /**
   * Configures the security filter chain. This method is used to configure the security filter
   * chain. The method configures the security filter chain to disable csrf, set cors configuration,
   * authorize http requests, set session management to stateless and add a JWT authorization filter
   * before the UsernamePasswordAuthenticationFilter.
   *
   * @param http the http security object
   * @return the security filter chain
   * @throws Exception if an error occurs
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.setAllowedOrigins(
        List.of("http://localhost:5173")); // specify your allowed origins
    corsConfiguration.setAllowedMethods(
        Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // specify your allowed methods
    corsConfiguration.setAllowCredentials(true);
    corsConfiguration.setAllowedHeaders(List.of("*"));

    http
        .csrf(AbstractHttpConfigurer::disable)
        .cors(cors -> cors.configurationSource(request -> corsConfiguration))
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers("/userCredentials/create",
                "/auth/login",
                "/users/get/totalSavings",
                "/v3/api-docs/**",
                "/swagger-ui/**",
                "/swagger-ui.html",
                "/swagger-ui/index.html",
                "/swagger-resources/",
                "/webjars/", "/error").permitAll()
            .requestMatchers("/user/**").hasAnyRole(BASIC, COMPLETE)
            .anyRequest().authenticated()
        )
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(new JWTAuthorizationFilter(secrets),
            UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }
}