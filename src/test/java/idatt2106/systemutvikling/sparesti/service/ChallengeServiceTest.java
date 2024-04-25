package idatt2106.systemutvikling.sparesti.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import idatt2106.systemutvikling.sparesti.dao.ChallengeDAO;
import idatt2106.systemutvikling.sparesti.dao.UserDAO;
import idatt2106.systemutvikling.sparesti.dto.ChallengeDTO;
import idatt2106.systemutvikling.sparesti.enums.RecurringInterval;
import idatt2106.systemutvikling.sparesti.mapper.ChallengeMapper;
import idatt2106.systemutvikling.sparesti.repository.ChallengeRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.util.ReflectionTestUtils;


public class ChallengeServiceTest {

  @Mock
  private ChallengeRepository challengeRepository;

  @InjectMocks
  private ChallengeService challengeService;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    challengeRepository = Mockito.mock(ChallengeRepository.class);
    ReflectionTestUtils.setField(challengeService, "challengeRepository", challengeRepository);
  }

  @Test
  @DisplayName("Test getChallengesByActiveAndUsername returns a list of challenges")
  public void testGetChallengesByActiveAndUsername() {
    ChallengeDAO challenge = new ChallengeDAO();
    challenge.setActive(true);
    ChallengeDAO challenge2 = new ChallengeDAO();
    challenge2.setActive(true);

    List<ChallengeDAO> challenges = List.of(challenge, challenge2);

    when(challengeRepository.findChallengeDAOByActiveAndUserDAO_Username(true,
        "username")).thenReturn(challenges);

    assertEquals(challenges, challengeService.getChallengesByActiveAndUsername("username", true));
  }

  @Test
  @DisplayName("Test getChallengesByActiveAndUsername throws exception when repository throws exception")
  public void testGetChallengesByActiveAndUsernameThrowsException() {
    when(challengeRepository.findChallengeDAOByActiveAndUserDAO_Username(true,
        "username")).thenThrow(new RuntimeException("Failed to get challenges"));

    assertNull(challengeService.getChallengesByActiveAndUsername("username", true));
  }

  @Test
  @DisplayName("Test getChallengesStartedAfterDate returns a list of challenges")
  public void testGetChallengesStartedAfterDate() {
    ChallengeDAO challenge = new ChallengeDAO();
    challenge.setStartDate(LocalDateTime.parse("2022-04-20T12:00:00"));
    ChallengeDAO challenge2 = new ChallengeDAO();
    challenge2.setStartDate(LocalDateTime.parse("2022-04-20T12:00:00"));

    List<ChallengeDAO> challenges = List.of(challenge, challenge2);

    when(challengeRepository.findChallengeDAOSByStartDateAfterAndUserDAO_Username(
        LocalDateTime.parse("2022-04-20T12:00:00"),
        "username")).thenReturn(challenges);

    assertEquals(challenges,
        challengeService.getChallengesStartedAfterDate(LocalDateTime.parse("2022-04-20T12:00:00"),
            "username"));
  }

  @Test
  @DisplayName("Test getChallengesStartedAfterDate throws exception when repository throws exception")
  public void testGetChallengesStartedAfterDateThrowsException() {
    when(challengeRepository.findChallengeDAOSByStartDateAfterAndUserDAO_Username(
        LocalDateTime.parse("2022-04-20T12:00:00"),
        "username")).thenThrow(new RuntimeException("Failed to get challenges"));

    assertThrows(RuntimeException.class, () -> challengeService.getChallengesStartedAfterDate(
        LocalDateTime.parse("2022-04-20T12:00:00"),
        "username"));
  }

  @Test
  @DisplayName("Test getChallenge returns a challenge")
  void testGetChallenge() {
    UserDAO user1 = new UserDAO();
    user1.setUsername("JohnSmith12");
    user1.setEmail("johnsmith@gmail.com");
    user1.setBirthDate(LocalDate.of(1999, 1, 1));
    user1.setFirstName("John");
    user1.setLastName("Smith");
    user1.setPassword("password");
    user1.setAchievements(List.of());

    // Arrange
    Long challengeId = 1L;
    ChallengeDAO challengeDAO = new ChallengeDAO();
    challengeDAO.setChallengeId(challengeId);
    challengeDAO.setUserDAO(user1);
    challengeDAO.setActive(true);
    challengeDAO.setRecurringInterval(RecurringInterval.NONE);
    when(challengeRepository.findChallengeDAOByChallengeId(challengeId)).thenReturn(challengeDAO);

    // Act
    ChallengeDTO result = challengeService.getChallenge(challengeId);

    // Assert
    assertEquals(challengeId, result.getChallengeId());
  }
}
