package idatt2106.systemutvikling.sparesti.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import idatt2106.systemutvikling.sparesti.dao.ChallengeDAO;
import idatt2106.systemutvikling.sparesti.dao.ChallengeLogDAO;
import idatt2106.systemutvikling.sparesti.dao.MilestoneDAO;
import idatt2106.systemutvikling.sparesti.dao.UserDAO;
import idatt2106.systemutvikling.sparesti.dto.ChallengeDTO;
import idatt2106.systemutvikling.sparesti.enums.RecurringInterval;
import idatt2106.systemutvikling.sparesti.mapper.ChallengeMapper;
import idatt2106.systemutvikling.sparesti.mockBank.repository.TransactionRepository;
import idatt2106.systemutvikling.sparesti.repository.ChallengeLogRepository;
import idatt2106.systemutvikling.sparesti.repository.ChallengeRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import idatt2106.systemutvikling.sparesti.repository.MilestoneRepository;
import idatt2106.systemutvikling.sparesti.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;


@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@DataJpaTest
public class ChallengeServiceTest {

  @MockBean
  private ChallengeRepository challengeRepository;

  @MockBean
  private ChallengeLogRepository challengeLogRepository;

  @MockBean
  private MilestoneRepository milestoneRepository;

  @MockBean
  private UserRepository userRepository;


  @InjectMocks
  private ChallengeService challengeService;

  @InjectMocks
  private MilestoneService milestoneService;

  @MockBean
  private TransactionService transactionService;

  @InjectMocks
  private UserService userService;


  @BeforeEach
  public void setup() {
    ReflectionTestUtils.setField(challengeService, "challengeRepository", challengeRepository);
    ReflectionTestUtils.setField(challengeService, "challengeLogRepository", challengeLogRepository);
    ReflectionTestUtils.setField(challengeService, "dbMilestone", milestoneRepository);
    ReflectionTestUtils.setField(challengeService, "milestoneService", milestoneService);
    ReflectionTestUtils.setField(challengeService, "transactionService", transactionService);
    ReflectionTestUtils.setField(milestoneService, "milestoneRepository", milestoneRepository);
  }

  @Test
  @DisplayName("Verify that ChallengeService::createChallenge cannot register challenges on other users")
  public void createChallenge_CannotRegisterChallengesOnOtherUsers() {
    String loggedInUsername = "Original user";
    String targetUsername = "Other user";

    ChallengeDTO dto = new ChallengeDTO(
            451L,
            targetUsername,
            "Tricked you >:D",
            "This is a challenge from \"Other user\"",
            100_000_000L,
            0L,
            LocalDateTime.now(),
            LocalDateTime.now().plusDays(2),
            0,
            true
    );

    try (MockedStatic<CurrentUserService> utilities = mockStatic(CurrentUserService.class)) {
      utilities.when(CurrentUserService::getCurrentUsername).thenReturn(loggedInUsername);
      challengeService.createChallenge(dto);
    }

    ArgumentCaptor<ChallengeDAO> argumentCaptor = ArgumentCaptor.forClass(ChallengeDAO.class);
    verify(challengeRepository, times(1)).save(argumentCaptor.capture());

    ChallengeDAO argument = argumentCaptor.getValue();
    assertNotEquals(targetUsername, argument.getUserDAO().getUsername());
    assertEquals(loggedInUsername, argument.getUserDAO().getUsername());
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
  @DisplayName("Test archiveActiveChallenge completes a challenge")
  void testArchiveActiveChallenge() {
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

    given(challengeRepository.findChallengeDAOByChallengeId(challenge1.getChallengeId())).willReturn(challenge1);
    ArgumentCaptor<ChallengeLogDAO> challengeLogDAOCaptor = ArgumentCaptor.forClass(ChallengeLogDAO.class);

    // Act
    challengeService.archiveActiveChallenge(challenge1.getChallengeId());
    verify(challengeLogRepository, times(1)).save(challengeLogDAOCaptor.capture());

    // Assert
    assertEquals(challenge1.getChallengeId(), challengeLogDAOCaptor.getValue().getChallengeId());
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

    Pageable pageable = PageRequest.of(0, 2, Sort.by("expirationDate").descending());

    when(challengeRepository.findChallengeDAOSByUserDAO_UsernameAndActive("JohnSmith12", false,
            pageable)).thenReturn(new PageImpl<>(List.of(challenge1, challenge2), pageable, 2));

    assertEquals(challenge1.getChallengeId(),
            challengeService.getInactiveChallenges("JohnSmith12", pageable).getContent().get(0)
                    .getChallengeId());
  }

  @Test
  @DisplayName("Test createChallenge returns a challenge")
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
    challengeDAO.setStartDate(LocalDateTime.now());

    ChallengeDTO challengeDTO = ChallengeMapper.toDTO(challengeDAO);
    challengeDTO.setUsername("JohnSmith12");

    try (MockedStatic<CurrentUserService> currentUserServiceMockedStatic = mockStatic(CurrentUserService.class)) {
      currentUserServiceMockedStatic.when(CurrentUserService::getCurrentUsername).thenReturn(user1.getUsername());
      when(challengeRepository.save(any(ChallengeDAO.class))).thenReturn(challengeDAO);

      assertEquals(challengeDAO, challengeService.createChallenge(ChallengeMapper.toDTO(challengeDAO)));
    }
  }

  @Test
  @DisplayName("Test completeChallengeForCurrentUser returns a challenge")
  void testCompleteChallengeForCurrentUser() {
    UserDAO user1 = new UserDAO();
    user1.setUsername("JohnSmith12");
    user1.setPassword("password");
    user1.setEmail("john.smith@gmail.com");
    user1.setFirstName("John");
    user1.setLastName("Smith");
    user1.setBirthDate(LocalDate.of(1999, 1, 1));

    ChallengeDAO challenge1 = new ChallengeDAO();
    challenge1.setChallengeId(1L);
    challenge1.setUserDAO(user1);
    challenge1.setActive(true);
    challenge1.setRecurringInterval(RecurringInterval.NONE);
    challenge1.setCurrentSum(100L);
    challenge1.setGoalSum(100L);

    MilestoneDAO milestone1 = new MilestoneDAO();
    milestone1.setMilestoneId(1L);
    milestone1.setMilestoneCurrentSum(100L);
    milestone1.setMilestoneGoalSum(500L);

    try (MockedStatic<CurrentUserService> currentUserServiceMockedStatic = mockStatic(CurrentUserService.class)) {
      currentUserServiceMockedStatic.when(CurrentUserService::getCurrentUsername).thenReturn(user1.getUsername());

      given(challengeRepository.findByChallengeIdAndUserDAO_Username(challenge1.getChallengeId(), user1.getUsername())).willReturn(challenge1);

      given(challengeRepository.findChallengeDAOByChallengeId(challenge1.getChallengeId())).willReturn(challenge1);

      when(challengeRepository.save(challenge1)).thenReturn(challenge1);

      when(milestoneRepository.findMilestoneDAOByMilestoneIdAndUserDAO_Username(milestone1.getMilestoneId(), user1.getUsername())).thenReturn(milestone1);

      when(milestoneRepository.save(milestone1)).thenReturn(milestone1);

      given(userRepository.findByUsername(user1.getUsername())).willReturn(user1);

      given(transactionService.createSavingsTransferForCurrentUser(100L)).willReturn(true);

      challengeService.completeChallengeForCurrentUser(1L, 1L);

      verify(challengeRepository, times(1)).delete(challenge1);
    }
  }
}
