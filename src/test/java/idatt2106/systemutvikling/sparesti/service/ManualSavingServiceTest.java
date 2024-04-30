package idatt2106.systemutvikling.sparesti.service;

import idatt2106.systemutvikling.sparesti.dao.ManualSavingDAO;
import idatt2106.systemutvikling.sparesti.dao.UserDAO;
import idatt2106.systemutvikling.sparesti.repository.ManualSavingRepository;
import idatt2106.systemutvikling.sparesti.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ManualSavingServiceTest {

  @Mock
  private ManualSavingRepository manualSavingRepository;

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private ManualSavingService manualSavingService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    manualSavingRepository = Mockito.mock(ManualSavingRepository.class);
    ReflectionTestUtils.setField(manualSavingService, "dbManualSaving", manualSavingRepository);
  }

  @Test
  @DisplayName("Test registerNewManualSavingDAO returns ManualSavingDAO object when given valid input")
  public void testRegisterNewManualSavingDAO() {
    // Arrange
    UserDAO user = new UserDAO();
    user.setUsername("JohnDoe");

    ManualSavingDAO manualSavingDAO = new ManualSavingDAO();
    manualSavingDAO.setUser(user);
    manualSavingDAO.setMilestoneId(1L);
    manualSavingDAO.setAmount(100L);
    manualSavingDAO.setTimeOfTransfer(new Date());

    // Act
    when(manualSavingRepository.save(any(ManualSavingDAO.class))).thenReturn(manualSavingDAO);
    when(userRepository.findByUsername("JohnDoe")).thenReturn(user);
    ManualSavingDAO result = manualSavingService.registerNewManualSavingDAO(1L, 100L, "JohnDoe");

    // Assert
    assertEquals(manualSavingDAO, result);
    assertNotNull(result);
  }

  @Test
  @DisplayName("Test registerNewManualSavingDAO returns null when given invalid input (null userDAO)")
  public void testRegisterNewManualSavingDAOInvalidInputUserDAO() {
    // Arrange
    UserDAO user = new UserDAO();
    user.setUsername("JohnDoe");

    ManualSavingDAO manualSavingDAO = new ManualSavingDAO();
    manualSavingDAO.setUser(user);
    manualSavingDAO.setMilestoneId(1L);
    manualSavingDAO.setAmount(100L);
    manualSavingDAO.setTimeOfTransfer(new Date());

    // Act
    when(manualSavingRepository.save(any(ManualSavingDAO.class))).thenReturn(manualSavingDAO);
    when(userRepository.findByUsername("JohnDoe")).thenReturn(null);
    ManualSavingDAO result = manualSavingService.registerNewManualSavingDAO(1L, 100L, "JohnDoe");

    // Assert
    assertNull(result);
  }

  @Test
  @DisplayName("Test registerNewManualSavingDAO returns null when given invalid input (null username)")
  void testRegisterNewManualSavingDAOInvalidInputUsername() {
    // Arrange
    UserDAO user = new UserDAO();
    user.setUsername("JohnDoe");

    ManualSavingDAO manualSavingDAO = new ManualSavingDAO();
    manualSavingDAO.setUser(user);
    manualSavingDAO.setMilestoneId(1L);
    manualSavingDAO.setAmount(100L);
    manualSavingDAO.setTimeOfTransfer(new Date());

    // Act
    when(manualSavingRepository.save(any(ManualSavingDAO.class))).thenReturn(manualSavingDAO);
    when(userRepository.findByUsername(null)).thenReturn(null);
    ManualSavingDAO result = manualSavingService.registerNewManualSavingDAO(1L, 100L, null);

    // Assert
    assertNull(result);
  }


  @Test
  @DisplayName("Test removeManualSavingEntry deletes ManualSavingDAO object")
  public void testRemoveManualSavingEntry() {
    // Arrange
    ManualSavingDAO manualSavingDAO = new ManualSavingDAO();
    manualSavingDAO.setUser(new UserDAO());
    manualSavingDAO.setMilestoneId(1L);
    manualSavingDAO.setAmount(100L);
    manualSavingDAO.setTimeOfTransfer(new Date());

    // Act
    manualSavingService.removeManualSavingEntry(manualSavingDAO);

    // Assert
    Mockito.verify(manualSavingRepository, Mockito.times(1)).delete(manualSavingDAO);
  }

  @Test
  @DisplayName("Test removeManualSavingEntry deletes ManualSavingDAO object by id")
  public void testRemoveManualSavingEntryById() {
    // Arrange
    ManualSavingDAO manualSavingDAO = new ManualSavingDAO();
    manualSavingDAO.setUser(new UserDAO());
    manualSavingDAO.setMilestoneId(1L);
    manualSavingDAO.setAmount(100L);
    manualSavingDAO.setTimeOfTransfer(new Date());

    // Act
    manualSavingService.removeManualSavingEntry(1L);

    // Assert
    Mockito.verify(manualSavingRepository, Mockito.times(1)).deleteById(1L);
  }
}
