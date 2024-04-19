package idatt2106.systemutvikling.sparesti.service;

import idatt2106.systemutvikling.sparesti.dao.AchievementDAO;
import idatt2106.systemutvikling.sparesti.dao.UserDAO;
import idatt2106.systemutvikling.sparesti.dto.AchievementDTO;
import idatt2106.systemutvikling.sparesti.repository.AchievementRepository;
import idatt2106.systemutvikling.sparesti.repository.ConditionRepository;
import idatt2106.systemutvikling.sparesti.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class AchievementServiceTest {

  @Mock
  private AchievementRepository achievementRepository;

  @Mock
  private ConditionRepository conditionRepository;

  @Mock
  private ConditionService conditionService;

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private AchievementService achievementService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void getLockedAchievements_ReturnsLockedAchievements() {
    // Arrange
    UserDAO user = new UserDAO();
    AchievementDAO achievement1 = new AchievementDAO();
    achievement1.setAchievementId(1L);
    AchievementDAO achievement2 = new AchievementDAO();
    achievement2.setAchievementId(2L);
    List<AchievementDAO> achieved = new ArrayList<>();
    achieved.add(achievement1);
    achieved.add(achievement2);
    user.setAchievements(achieved);

    AchievementDAO achievement3 = new AchievementDAO();
    achievement3.setAchievementId(3L);
    AchievementDAO achievement4 = new AchievementDAO();
    achievement4.setAchievementId(4L);
    List<AchievementDAO> allAchievements = new ArrayList<>();
    allAchievements.add(achievement1);
    allAchievements.add(achievement2);
    allAchievements.add(achievement3);
    allAchievements.add(achievement4);

    when(achievementRepository.findAll()).thenReturn(allAchievements);

    // Act
    List<AchievementDAO> lockedAchievements = achievementService.getLockedAchievements(user.getUsername());

    // Assert
    assertEquals(2, lockedAchievements.size());
    assertTrue(lockedAchievements.contains(achievement3));
    assertTrue(lockedAchievements.contains(achievement4));

    // Verify that achievementRepository.findAll() is called once
    verify(achievementRepository, times(1)).findAll();
  }

  @Test
  public void testCheckForUnlockedAchievements() {
    // Create a test user
    UserDAO user = new UserDAO();
    user.setUsername("johndoe");
    user.setAchievements(new ArrayList<>());

    // Create test achievement
    AchievementDAO achievement = new AchievementDAO();
    achievement.setAchievementId(1L);

    // Mock the behavior of the repositories
    when(achievementRepository.findAll()).thenReturn(List.of(achievement));
    when(conditionRepository.findAllByAchievementDAO_AchievementId(1L)).thenReturn(new ArrayList<>());
    when(conditionService.isConditionMet(null)).thenReturn(true); // Mock condition always being met

    // Call the method to test
    ResponseEntity<List<AchievementDTO>> unlockedAchievementsResponse = achievementService.checkForUnlockedAchievements(user.getUsername());

    // Verify the result
    assertEquals(1, unlockedAchievementsResponse.getBody().size());
    assertEquals(1, user.getAchievements().size());
  }
}
