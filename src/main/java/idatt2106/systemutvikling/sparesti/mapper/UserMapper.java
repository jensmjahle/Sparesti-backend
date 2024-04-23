package idatt2106.systemutvikling.sparesti.mapper;

import idatt2106.systemutvikling.sparesti.dao.UserDAO;
import idatt2106.systemutvikling.sparesti.dto.UserCredentialsDTO;
import idatt2106.systemutvikling.sparesti.dto.UserDTO;
import org.springframework.stereotype.Component;

/**
 * Mapper class for UserDAO and UserDTO
 */

@Component
public class UserMapper {

  /**
   * Converts a `UserDAO` object to a `UserDTO` object.
   *
   * @param userDAO The `UserDAO` instance to be mapped.
   * @return A new `UserDTO` containing the relevant data from the `UserDAO`.
   */

  public static UserDTO toUserDTO(UserDAO userDAO) {
    UserDTO userDTO = new UserDTO();

    userDTO.setUsername(userDAO.getUsername());
    userDTO.setEmail(userDAO.getEmail());
    userDTO.setFirstName(userDAO.getFirstName());
    userDTO.setLastName(userDAO.getLastName());
    userDTO.setBirthDate(userDAO.getBirthDate());
    userDTO.setProfilePictureBase64(Base64Mapper.toBase64String(userDAO.getProfilePicture()));
    userDTO.setMonthlyIncome(userDAO.getMonthlyIncome());
    userDTO.setMonthlySavings(userDAO.getMonthlySavings());
    userDTO.setMonthlyFixedExpenses(userDAO.getMonthlyFixedExpenses());
    userDTO.setCurrentAccount(userDAO.getCurrentAccount());
    userDTO.setSavingsAccount(userDAO.getSavingsAccount());
    userDTO.setAchievementDTOList(AchievementMapper.toDTOList(userDAO.getAchievements()));

    return userDTO;
  }

  /**
   * Converts a UserCredentialsDTO object to a UserDAO object.
   *
   * @param userCredentialsDTO The UserCredentialsDTO instance to be mapped.
   * @return A new UserDAO containing the relevant data from the UserCredentialsDTO.
   */
  public static UserDAO userCredentialsDTOToUserDAO(UserCredentialsDTO userCredentialsDTO) {
    UserDAO userDAO = new UserDAO();

    userDAO.setUsername(userCredentialsDTO.getUsername());
    userDAO.setPassword(userCredentialsDTO.getPassword());
    userDAO.setEmail(userCredentialsDTO.getEmail());
    userDAO.setFirstName(userCredentialsDTO.getFirstName());
    userDAO.setLastName(userCredentialsDTO.getLastName());
    userDAO.setBirthDate(userCredentialsDTO.getBirthDate());

    return userDAO;
  }

  /**
   * Converts a UserDTO object to a UserDAO object.
   *
   * @param userDTO The UserDTO instance to be mapped.
   * @return A new UserDAO containing the relevant data from the UserDTO.
   */
  public static UserDAO userDTOToUserDAO(UserDTO userDTO) {
    UserDAO userDAO = new UserDAO();

    userDAO.setUsername(userDTO.getUsername());
    userDAO.setEmail(userDTO.getEmail());
    userDAO.setFirstName(userDTO.getFirstName());
    userDAO.setLastName(userDTO.getLastName());
    userDAO.setBirthDate(userDTO.getBirthDate());
    userDAO.setProfilePicture(Base64Mapper.toByteArray(userDTO.getProfilePictureBase64()));
    userDAO.setMonthlyIncome(userDTO.getMonthlyIncome());
    userDAO.setMonthlySavings(userDTO.getMonthlySavings());
    userDAO.setMonthlyFixedExpenses(userDTO.getMonthlyFixedExpenses());
    userDAO.setCurrentAccount(userDTO.getCurrentAccount());
    userDAO.setSavingsAccount(userDTO.getSavingsAccount());

    return userDAO;
  }
}
