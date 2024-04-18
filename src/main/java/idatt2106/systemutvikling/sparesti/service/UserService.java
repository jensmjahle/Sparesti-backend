package idatt2106.systemutvikling.sparesti.service;

import idatt2106.systemutvikling.sparesti.dto.UserCredentialsDTO;
import idatt2106.systemutvikling.sparesti.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  //TODO: Implement methods
  public ResponseEntity<UserDTO> getUserDTO(String username) {
    //TODO: Return entire userDTO from database
    return null;
  }

  public ResponseEntity<String> updateUserDTO(String username, UserDTO updatedUserDTO) {
    //TODO: Update userDTO in database
    return null;
  }

  public ResponseEntity<UserDTO> deleteUserDTO(String username) {
    //TODO: Delete userDTO from database
    return null;
  }

  public ResponseEntity<UserDTO> login(UserCredentialsDTO user) {
    //TODO: Check if user exists in database, and if password is correct
    //TODO: Return userDTO if login is successful
    //TODO: Return only profile picture, username and isConnectedToBank if login is successful
    return null;
  }

  public ResponseEntity<UserDTO> createUser(UserCredentialsDTO user) {
    //TODO: Check if user already exists in database
    //TODO: Create userDTO in database
    //TODO: Return only profile picture, username and isConnectedToBank if register is successful
    return null;
  }
}
