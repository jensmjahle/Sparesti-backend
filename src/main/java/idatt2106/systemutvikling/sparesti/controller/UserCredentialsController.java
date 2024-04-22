package idatt2106.systemutvikling.sparesti.controller;

import idatt2106.systemutvikling.sparesti.dto.UserCredentialsDTO;
import idatt2106.systemutvikling.sparesti.dto.UserDTO;
import idatt2106.systemutvikling.sparesti.model.LoginRequestModel;
import idatt2106.systemutvikling.sparesti.service.UserService;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//any cross origin
@CrossOrigin
@RestController
@RequestMapping("/userCredentials")
public class UserCredentialsController {
Logger logger = Logger.getLogger(UserCredentialsController.class.getName());
    private final UserService userService;

@Autowired
public UserCredentialsController(UserService userService) {
    this.userService = userService;
}

@RequestMapping("/login")
public ResponseEntity<UserDTO> loginRequest(@RequestBody LoginRequestModel user) {
    logger.info("Received request to login user with username: " + user.getUsername() + ".");
    return userService.login(user);
}

@RequestMapping("/create")
public ResponseEntity<String> createUser(@RequestBody UserCredentialsDTO user) {
    logger.info("Received request to create user with username: " + user.getUsername() + ".");
    return userService.createUser(user);
}

}
