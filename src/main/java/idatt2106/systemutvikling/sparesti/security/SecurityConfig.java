package idatt2106.systemutvikling.sparesti.security;

import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for the security settings.
 */
@Configuration
public class SecurityConfig {

  //placeholder for the filter chain

  /*
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
            .csrf().disable()
            .cors().and()
            .authorizeHttpRequests()
            .requestMatchers(
            ............
            ).permitAll()
            .anyRequest().authenticated().and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);



    return http.build();
  }*/

}
