package idatt2106.systemutvikling.sparesti.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import idatt2106.systemutvikling.sparesti.dao.ChallengeDAO;
import idatt2106.systemutvikling.sparesti.dao.ChallengeLogDAO;
import idatt2106.systemutvikling.sparesti.dao.UserDAO;
import idatt2106.systemutvikling.sparesti.dto.ChallengeDTO;
import idatt2106.systemutvikling.sparesti.enums.RecurringInterval;
import idatt2106.systemutvikling.sparesti.mapper.ChallengeMapper;
import idatt2106.systemutvikling.sparesti.repository.ChallengeLogRepository;
import idatt2106.systemutvikling.sparesti.repository.ChallengeRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;
import org.springframework.test.util.ReflectionTestUtils;


public class ChallengeServiceTest {

  @Mock
  private ChallengeRepository challengeRepository;

  @Mock
  private ChallengeLogRepository challengeLogRepository;

  @InjectMocks
  private ChallengeService challengeService;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    challengeRepository = Mockito.mock(ChallengeRepository.class);
    challengeLogRepository = Mockito.mock(ChallengeLogRepository.class);
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


  @Test
  @DisplayName("Test getAllChallenges returns a list of challenges")
  void testGetAllChallenges() {
    UserDAO user1 = new UserDAO();
    user1.setUsername("JohnSmith12");

    ChallengeDAO challenge1 = new ChallengeDAO();
    challenge1.setChallengeId(1L);
    challenge1.setUserDAO(user1);
    challenge1.setActive(true);
    challenge1.setRecurringInterval(RecurringInterval.NONE);

    ChallengeDAO challenge2 = new ChallengeDAO();
    challenge2.setChallengeId(2L);
    challenge2.setUserDAO(user1);
    challenge2.setActive(true);
    challenge2.setRecurringInterval(RecurringInterval.NONE);

    List<ChallengeDAO> challenges = List.of(challenge1, challenge2);
    List<ChallengeDTO> challengeDTOs = List.of(ChallengeMapper.toDTO(challenge1),
        ChallengeMapper.toDTO(challenge2));

    when(challengeRepository.findAll()).thenReturn(challenges);

    // ChallengeIds are equal
    assertEquals(challengeDTOs.get(0).getChallengeId(),
        challengeService.getAllChallenges().get(0).getChallengeId());
    assertEquals(challengeDTOs.get(1).getChallengeId(),
        challengeService.getAllChallenges().get(1).getChallengeId());

    // Usernames are equal
    assertEquals(challengeDTOs.get(0).getUsername(),
        challengeService.getAllChallenges().get(0).getUsername());
    assertEquals(challengeDTOs.get(1).getUsername(),
        challengeService.getAllChallenges().get(1).getUsername());
  }

  @Test
  @DisplayName("Test createChallengeLog returns a challenge log")
  void testCreateChallengeLog() {
    UserDAO user1 = new UserDAO();
    user1.setUsername("JohnSmith12");

    ChallengeDAO challenge1 = new ChallengeDAO();
    challenge1.setChallengeId(1L);
    challenge1.setUserDAO(user1);
    challenge1.setActive(true);
    challenge1.setRecurringInterval(RecurringInterval.NONE);
    challenge1.setExpirationDate(LocalDateTime.of(2025, 1, 1, 1, 1, 1));
    challenge1.setChallengeDescription("description");
    challenge1.setChallengeTitle("title");
    challenge1.setGoalSum(100L);
    challenge1.setCurrentSum(50L);
    challenge1.setStartDate(LocalDateTime.of(2025, 1, 1, 1, 1, 1));
    challenge1.setTheme(null);

    ChallengeLogDAO challengeLogDAO = new ChallengeLogDAO();
    challengeLogDAO.setChallengeId(challenge1.getChallengeId());
    challengeLogDAO.setChallengeTitle(challenge1.getChallengeTitle());
    challengeLogDAO.setChallengeDescription(challenge1.getChallengeDescription());
    challengeLogDAO.setGoalSum(challenge1.getGoalSum());
    challengeLogDAO.setChallengeAchievedSum(challenge1.getCurrentSum());
    challengeLogDAO.setCompletionDate(LocalDateTime.now());
    challengeLogDAO.setUserDAO(user1);

    when(challengeRepository.findChallengeDAOByChallengeId(1L)).thenReturn(challenge1);
    when(challengeRepository.save(challenge1)).thenReturn(challenge1);

    assertEquals(challengeLogDAO.getChallengeId(),
        challengeService.createChallengeLog(challenge1).getChallengeId());
  }

  @Test
  @DisplayName("Test activateChallenge returns an activated challenge")
  void testActivateChallenge() {
    UserDAO user1 = new UserDAO();
    user1.setUsername("JohnSmith12");

    ChallengeDAO challenge1 = new ChallengeDAO();
    challenge1.setChallengeId(1L);
    challenge1.setUserDAO(user1);
    challenge1.setActive(false);
    challenge1.setRecurringInterval(RecurringInterval.NONE);

    when(challengeRepository.findChallengeDAOByChallengeId(1L)).thenReturn(challenge1);
    when(challengeRepository.save(challenge1)).thenReturn(challenge1);

    assertTrue(challengeService.activateChallenge(1L).isActive());
  }

  @Test
  @DisplayName("Test completeChallenge completes a challenge")
  void testCompleteChallenge() {
    UserDAO user1 = new UserDAO();
    user1.setUsername("JohnSmith12");

    ChallengeDAO challenge1 = new ChallengeDAO();
    challenge1.setChallengeId(1L);
    challenge1.setUserDAO(user1);
    challenge1.setActive(true);
    challenge1.setRecurringInterval(RecurringInterval.NONE);

    ChallengeLogDAO challengeLogDAO = new ChallengeLogDAO();
    challengeLogDAO.setChallengeId(challenge1.getChallengeId());
    challengeLogDAO.setChallengeTitle(challenge1.getChallengeTitle());
    challengeLogDAO.setChallengeDescription(challenge1.getChallengeDescription());
    challengeLogDAO.setGoalSum(challenge1.getGoalSum());
    challengeLogDAO.setChallengeAchievedSum(challenge1.getCurrentSum());
    challengeLogDAO.setCompletionDate(LocalDateTime.now());
    challengeLogDAO.setUserDAO(user1);

    when(challengeRepository.findChallengeDAOByChallengeId(1L)).thenReturn(challenge1);
    when(challengeRepository.save(challenge1)).thenReturn(challenge1);

    assertEquals(challengeLogDAO.getChallengeId(),
        challengeService.completeChallenge(1L).getChallengeId());
  }

  @Test
  @DisplayName("Test moveChallengeToLog deletes a challenge")
  void testDeleteChallenge() {
    UserDAO user1 = new UserDAO();
    user1.setUsername("JohnSmith12");

    ChallengeDAO challenge1 = new ChallengeDAO();
    challenge1.setChallengeId(1L);
    challenge1.setUserDAO(user1);
    challenge1.setActive(true);
    challenge1.setRecurringInterval(RecurringInterval.NONE);

    when(challengeRepository.findChallengeDAOByChallengeId(1L)).thenReturn(challenge1);

    challengeService.moveChallengeToLog(1L);

    verify(challengeRepository, times(1)).delete(challenge1);
  }

  @Test
  @DisplayName("Test getChallengesByUsername returns a list of challenges")
  void testGetChallengesByUsername() {
    UserDAO user1 = new UserDAO();
    user1.setUsername("JohnSmith12");

    Pageable pageable = PageRequest.of(0, 2, Sort.by("expirationDate").descending());

    ChallengeDAO challenge1 = new ChallengeDAO();
    challenge1.setChallengeId(1L);
    challenge1.setUserDAO(user1);
    challenge1.setActive(true);
    challenge1.setRecurringInterval(RecurringInterval.NONE);

    ChallengeDAO challenge2 = new ChallengeDAO();
    challenge2.setChallengeId(2L);
    challenge2.setUserDAO(user1);
    challenge2.setActive(false);
    challenge2.setRecurringInterval(RecurringInterval.NONE);

    List<ChallengeDAO> challenges = List.of(challenge1, challenge2);
    List<ChallengeDTO> challengeDTOs = List.of(ChallengeMapper.toDTO(challenge1),
        ChallengeMapper.toDTO(challenge2));

    when(challengeRepository.findChallengeDAOSByUserDAO_Username("JohnSmith12",
        pageable)).thenReturn(challenges);

    assertEquals(challengeDTOs.get(0).getChallengeId(),
        challengeService.getChallengesByUsername("JohnSmith12", 0, 2).get(0).getChallengeId());
  }

  @Test
  @DisplayName("Test hasChallengeTimeElapsed deletes a challenge")
  void testHasChallengeTimeElapsed() {
    UserDAO user1 = new UserDAO();
    user1.setUsername("JohnSmith12");

    ChallengeDAO challenge1 = new ChallengeDAO();
    challenge1.setChallengeId(1L);
    challenge1.setUserDAO(user1);
    challenge1.setActive(true);
    challenge1.setRecurringInterval(RecurringInterval.NONE);
    challenge1.setExpirationDate(LocalDateTime.of(2023, 1, 1, 1, 1, 1));

    List<ChallengeDAO> challenges = List.of(challenge1);

    when(challengeRepository.findChallengeDAOSByUserDAO_Username("JohnSmith12")).thenReturn(
        challenges);

    challengeService.hasChallengeTimeElapsed(challenges);

    verify(challengeRepository, times(1)).delete(challenge1);
  }

  @Test
  @DisplayName("Test hasChallengeTimeElapsed does not delete a challenge")
  void testHasChallengeTimeElapsedDoesNotDelete() {
    UserDAO user1 = new UserDAO();
    user1.setUsername("JohnSmith12");

    ChallengeDAO challenge1 = new ChallengeDAO();
    challenge1.setChallengeId(1L);
    challenge1.setUserDAO(user1);
    challenge1.setActive(true);
    challenge1.setRecurringInterval(RecurringInterval.NONE);
    challenge1.setExpirationDate(LocalDateTime.of(2025, 1, 1, 1, 1, 1));

    List<ChallengeDAO> challenges = List.of(challenge1);

    when(challengeRepository.findChallengeDAOSByUserDAO_Username("JohnSmith12")).thenReturn(
        challenges);

    challengeService.hasChallengeTimeElapsed(challenges);

    verify(challengeRepository, times(0)).delete(challenge1);
  }

  @Test
  @DisplayName("Test getActiveChallenges returns a list of active challenges")
  void testGetActiveChallenges() {
    UserDAO user1 = new UserDAO();
    user1.setUsername("JohnSmith12");

    ChallengeDAO challenge1 = new ChallengeDAO();
    challenge1.setChallengeId(1L);
    challenge1.setUserDAO(user1);
    challenge1.setActive(true);
    challenge1.setRecurringInterval(RecurringInterval.NONE);

    ChallengeDAO challenge2 = new ChallengeDAO();
    challenge2.setChallengeId(2L);
    challenge2.setUserDAO(user1);
    challenge2.setActive(true);
    challenge2.setRecurringInterval(RecurringInterval.NONE);

    ChallengeDAO challenge3 = new ChallengeDAO();
    challenge3.setChallengeId(3L);
    challenge3.setUserDAO(user1);
    challenge3.setActive(false);
    challenge3.setRecurringInterval(RecurringInterval.NONE);

    List<ChallengeDAO> challenges = List.of(challenge1, challenge2, challenge3);
    List<ChallengeDTO> challengeDTOs = List.of(ChallengeMapper.toDTO(challenge1),
        ChallengeMapper.toDTO(challenge2));

    Pageable pageable = PageRequest.of(0, 2, Sort.by("expirationDate").ascending());

    when(challengeRepository.findChallengeDAOSByUserDAO_UsernameAndActive("JohnSmith12", true,
        pageable)).thenReturn(new PageImpl<>(List.of(challenge1, challenge2), pageable, 2));

    assertEquals(challengeDTOs.get(0).getChallengeId(),
        challengeService.getActiveChallenges("JohnSmith12", pageable).getContent().get(0)
            .getChallengeId());
  }

  @Test
  @DisplayName("Test getInactiveChallenges returns a list of inactive challenges")
  void testGetInactiveChallenges() {
    UserDAO user1 = new UserDAO();
    user1.setUsername("JohnSmith12");

    ChallengeDAO challenge1 = new ChallengeDAO();
    challenge1.setChallengeId(1L);
    challenge1.setUserDAO(user1);
    challenge1.setActive(false);
    challenge1.setRecurringInterval(RecurringInterval.NONE);

    ChallengeDAO challenge2 = new ChallengeDAO();
    challenge2.setChallengeId(2L);
    challenge2.setUserDAO(user1);
    challenge2.setActive(false);
    challenge2.setRecurringInterval(RecurringInterval.NONE);

    ChallengeDAO challenge3 = new ChallengeDAO();
    challenge3.setChallengeId(3L);
    challenge3.setUserDAO(user1);
    challenge3.setActive(true);
    challenge3.setRecurringInterval(RecurringInterval.NONE);

    List<ChallengeDAO> challenges = List.of(challenge1, challenge2, challenge3);

    Pageable pageable = PageRequest.of(0, 2, Sort.by("expirationDate").descending());

    when(challengeRepository.findChallengeDAOSByUserDAO_UsernameAndActive("JohnSmith12", false,
        pageable)).thenReturn(new PageImpl<>(List.of(challenge1, challenge2), pageable, 2));

    assertEquals(challenge1.getChallengeId(),
        challengeService.getInactiveChallenges("JohnSmith12", pageable).getContent().get(0)
            .getChallengeId());
  }


  /*
  // TODO: Fix these tests
  @Test
  void getChallengesByUsername_ReturnsChallenges() {

    // Arrange
    UserDAO user1 = new UserDAO();
    user1.setUsername("JohnSmith12");

    UserDAO user2 = new UserDAO();
    user2.setUsername("JaneDoe123");

    ChallengeDAO challenge1 = new ChallengeDAO();
    challenge1.setChallengeId(1L);
    challenge1.setUserDAO(user1);
    challenge1.setActive(true);
    challenge1.setRecurringInterval(RecurringInterval.NONE);
    challenge1.setExpirationDate(LocalDateTime.of(2025, 1, 1, 1, 1,1));

    ChallengeDAO challenge2 = new ChallengeDAO();
    challenge2.setChallengeId(2L);
    challenge2.setUserDAO(user1);
    challenge2.setActive(false);
    challenge2.setRecurringInterval(RecurringInterval.NONE);
    challenge2.setExpirationDate(LocalDateTime.of(2025, 1, 1, 1, 1,1));

    List<ChallengeDAO> challenges = List.of(challenge1, challenge2);
    List<ChallengeDTO> challengeDTOs = List.of(ChallengeMapper.toDTO(challenge1), ChallengeMapper.toDTO(challenge2));

    ChallengeDAO challenge3 = new ChallengeDAO();
    challenge3.setChallengeId(3L);
    challenge3.setUserDAO(user2);
    challenge3.setActive(true);
    challenge3.setRecurringInterval(RecurringInterval.NONE);
    challenge3.setExpirationDate(LocalDateTime.of(2025, 1, 1, 1, 1,1));

    ChallengeDAO challenge4 = new ChallengeDAO();
    challenge4.setChallengeId(4L);
    challenge4.setUserDAO(user2);
    challenge4.setActive(false);
    challenge4.setRecurringInterval(RecurringInterval.NONE);
    challenge4.setExpirationDate(LocalDateTime.of(2025, 1, 1, 1, 1,1));

    List<ChallengeDAO> challenges2 = List.of(challenge3, challenge4);
    List<ChallengeDTO> challengeDTOs2 = List.of(ChallengeMapper.toDTO(challenge3), ChallengeMapper.toDTO(challenge4));

    List<ChallengeDAO> allChallenges = List.of(challenge1, challenge2, challenge3, challenge4);

    when(challengeRepository.findAll()).thenReturn(allChallenges);
    when(challengeRepository.findChallengeDAOSByUserDAO_Username("JohnSmith12")).thenReturn(List.of(challenge1, challenge2));
    when(challengeRepository.findChallengeDAOSByUserDAO_Username("JaneDoe123")).thenReturn(List.of(challenge3, challenge4));
    when(challengeRepository.findChallengeDAOSByUserDAO_UsernameAndActive("JohnSmith12", true, PageRequest.of(0, 2))).thenReturn(List.of(challenge1));
    when(challengeRepository.findChallengeDAOSByUserDAO_UsernameAndActive("JohnSmith12", false, PageRequest.of(0, 2))).thenReturn(List.of(challenge2));
    when(challengeRepository.findChallengeDAOSByUserDAO_UsernameAndActive("JaneDoe123", true, PageRequest.of(0, 2))).thenReturn(List.of(challenge3));
    when(challengeRepository.findChallengeDAOSByUserDAO_UsernameAndActive("JaneDoe123", false, PageRequest.of(0, 2))).thenReturn(List.of(challenge4));

    // Act
    List<ChallengeDTO> resultActive1 = challengeService.getActiveChallenges("JohnSmith12", 0, 2);
    List<ChallengeDTO> resultActive2 = challengeService.getActiveChallenges("JaneDoe123", 0, 2);
    List<ChallengeDTO> resultInactive1 = challengeService.getChallengesByUsername("JohnSmith12", 0, 2);
    List<ChallengeDTO> resultInactive2 = challengeService.getChallengesByUsername("JaneDoe123", 0, 2);

    // Assert
    assertEquals(challengeDTOs.get(0).getChallengeId(), resultActive1.get(0).getChallengeId());
    assertEquals(challengeDTOs.get(1).getChallengeId(), resultInactive1.get(1).getChallengeId());
    assertEquals(challengeDTOs2.get(0).getChallengeId(), resultActive2.get(0).getChallengeId());
    assertEquals(challengeDTOs2.get(1).getChallengeId(), resultInactive2.get(1).getChallengeId());
  }


  @Test
  @DisplayName("Test createChallenge returns a challenge")
  @WithUserDetails("JohnSmith12")
  void testCreateChallenge() {
    UserDAO user1 = new UserDAO();
    user1.setUsername("JohnSmith12");
    user1.setPassword("password");
    user1.setEmail("john.smith@gmail.com");
    user1.setFirstName("John");
    user1.setLastName("Smith");
    user1.setBirthDate(LocalDate.of(1999, 1, 1));

    // Arrange
    ChallengeDAO challengeDAO = new ChallengeDAO();
    challengeDAO.setChallengeId(1L);
    challengeDAO.setUserDAO(user1);
    challengeDAO.setActive(true);
    challengeDAO.setRecurringInterval(RecurringInterval.NONE);

    ChallengeDTO challengeDTO = ChallengeMapper.toDTO(challengeDAO);
    challengeDTO.setUsername("JohnSmith12");
    when(challengeRepository.save(challengeDAO)).thenReturn(challengeDAO);

    //when(CurrentUserService.getCurrentUsername()).thenReturn("JohnSmith12");
    when(challengeService.createChallenge(challengeDTO)).thenReturn(challengeDAO);


    assertEquals(challengeDAO, challengeService.createChallenge(challengeDTO));
  }
  */
}
