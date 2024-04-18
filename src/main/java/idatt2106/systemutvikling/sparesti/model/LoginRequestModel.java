package idatt2106.systemutvikling.sparesti.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model class for login requests.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginRequestModel {

  private final String username;
  private final String password;

  /**
   * Constructor for the LoginRequest class.
   * @param username the username of the user
   * @param password the password of the user
   */
  @JsonCreator
  public LoginRequestModel(@JsonProperty("username") final String username, @JsonProperty("password") final String password) {
    this.username = username;
    this.password = password;
  }

  /**
   * Getter for the username.
   * @return the username
   */
  @JsonProperty("username")
  public String getUsername() {
    return username;
  }

  /**
   * Getter for the password.
   * @return the password
   */
  @JsonProperty("password")
  public String getPassword() {
    return password;
  }
}

