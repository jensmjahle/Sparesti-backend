package idatt2106.systemutvikling.sparesti.service;

import idatt2106.systemutvikling.sparesti.dao.ChallengeLogDAO;
import idatt2106.systemutvikling.sparesti.enums.ChallengeTheme;
import idatt2106.systemutvikling.sparesti.enums.TransactionCategory;
import idatt2106.systemutvikling.sparesti.repository.ChallengeLogRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ChallengeLogServiceTest {

  @Mock
  private ChallengeLogRepository challengeLogRepository;
  @Mock
  private ChallengeLogService challengeLogService1;

  @InjectMocks
  private ChallengeLogService challengeLogService;

  @Test
  @DisplayName("Test getChallengeCompletionRate")
  public void testGetChallengeCompletionRate() {
    // Test data
    String username = "testUser";
    ChallengeLogDAO challengeLog1 = new ChallengeLogDAO();
    challengeLog1.setGoalSum(200L);
    challengeLog1.setChallengeAchievedSum(50L);
    ChallengeLogDAO challengeLog2 = new ChallengeLogDAO();
    challengeLog2.setGoalSum(200L);
    challengeLog2.setChallengeAchievedSum(150L);
    List<ChallengeLogDAO> challengeLogs = List.of(challengeLog1, challengeLog2);

    // Mocking
    when(challengeLogRepository.findChallengeLogDAOByUserDAO_Username(username)).thenReturn(
        challengeLogs);

    // Assertion
    assertEquals(0.5, challengeLogService.getChallengeCompletionRate(username));
  }

  @Test
  @DisplayName("getChallengeCompletionRate returns 0 when no challenge logs are found")
  public void testGetChallengeCompletionRateNoChallengeLogs() {
    // Test data
    String username = "testUser";
    List<ChallengeLogDAO> challengeLogs = List.of();

    // Mocking
    when(challengeLogRepository.findChallengeLogDAOByUserDAO_Username(username)).thenReturn(
        challengeLogs);

    // Assertion
    assertEquals(0, challengeLogService.getChallengeCompletionRate(username));
  }

  @Test
  @DisplayName("getChallengeCompletionRate throws exception when repository throws exception")
  public void testGetChallengeCompletionRateThrowsException() {
    // Test data
    String username = "testUser";

    // Mocking
    when(challengeLogRepository.findChallengeLogDAOByUserDAO_Username(username)).thenThrow(
        new RuntimeException());

    // Assertion
    assertThrows(RuntimeException.class,
        () -> challengeLogService.getChallengeCompletionRate(username));
  }

  @Test
  @DisplayName("Test getChallengeAcceptanceRate returns correct value")
  public void testGetChallengeAcceptanceRate() {
    // Test data
    String username = "testUser";
    ChallengeLogDAO challengeLog1 = new ChallengeLogDAO();
    challengeLog1.setAccepted(true);
    ChallengeLogDAO challengeLog2 = new ChallengeLogDAO();
    challengeLog2.setAccepted(false);
    List<ChallengeLogDAO> challengeLogs = List.of(challengeLog1, challengeLog2);

    // Mocking
    when(challengeLogRepository.findChallengeLogDAOByUserDAO_Username(username)).thenReturn(
        challengeLogs);

    // Assertion
    assertEquals(0.5, challengeLogService.getChallengeAcceptanceRate(username));
  }

  @Test
  @DisplayName("getChallengeAcceptanceRate returns 0 when no challenge logs are found")
  public void testGetChallengeAcceptanceRateNoChallengeLogs() {
    // Test data
    String username = "testUser";
    List<ChallengeLogDAO> challengeLogs = List.of();

    // Mocking
    when(challengeLogRepository.findChallengeLogDAOByUserDAO_Username(username)).thenReturn(
        challengeLogs);

    // Assertion
    assertEquals(0, challengeLogService.getChallengeAcceptanceRate(username));
  }

  @Test
  @DisplayName("getChallengeAcceptanceRate throws exception when repository throws exception")
  public void testGetChallengeAcceptanceRateThrowsException() {
    // Test data
    String username = "testUser";

    // Mocking
    when(challengeLogRepository.findChallengeLogDAOByUserDAO_Username(username)).thenThrow(
        new RuntimeException());

    // Assertion
    assertThrows(RuntimeException.class,
        () -> challengeLogService.getChallengeAcceptanceRate(username));
  }

  @Test
  @DisplayName("Test getChallengesByCompletionDateAfterAndUsername")
  public void testGetChallengesByCompletionDateAfterAndUsername() {
    // Test data
    String username = "testUser";
    LocalDateTime date = LocalDateTime.now();
    ChallengeLogDAO challengeLog1 = new ChallengeLogDAO();
    challengeLog1.setCompletionDate(date);
    ChallengeLogDAO challengeLog2 = new ChallengeLogDAO();
    challengeLog2.setCompletionDate(date);
    List<ChallengeLogDAO> challengeLogs = List.of(challengeLog1, challengeLog2);

    // Mocking
    when(challengeLogRepository.findChallengeLogDAOSByCompletionDateAfterAndUserDAO_Username(date,
        username)).thenReturn(
        challengeLogs);

    // Assertion
    assertEquals(challengeLogs,
        challengeLogService.getChallengesByCompletionDateAfterAndUsername(username, date));
  }

  @Test
  @DisplayName("getChallengesByCompletionDateAfterAndUsername returns empty list when no challenge logs are found")
  public void testGetChallengesByCompletionDateAfterAndUsernameNoChallengeLogs() {
    // Test data
    String username = "testUser";
    LocalDateTime date = LocalDateTime.now();
    List<ChallengeLogDAO> challengeLogs = List.of();

    // Mocking
    when(challengeLogRepository.findChallengeLogDAOByUserDAO_Username(username)).thenReturn(
        challengeLogs);

    // Assertion
    assertEquals(challengeLogs,
        challengeLogService.getChallengesByCompletionDateAfterAndUsername(username, date));
  }

  @Test
  @DisplayName("getChallengesByCompletionDateAfterAndUsername throws exception when repository throws exception")
  public void testGetChallengesByCompletionDateAfterAndUsernameThrowsException() {
    // Test data
    String username = "testUser";
    LocalDateTime date = LocalDateTime.now();

    // Mocking
    when(challengeLogRepository.findChallengeLogDAOSByCompletionDateAfterAndUserDAO_Username(date,
        username)).thenThrow(
        new RuntimeException());

    // Assertion
    assertThrows(RuntimeException.class,
        () -> challengeLogService.getChallengesByCompletionDateAfterAndUsername(username, date));
  }

  @Test
  @DisplayName("Test getChallengesByCategoryRatio returns correct map")
  public void testGetChallengesByCategoryRatio() {
    // Test data
    String username = "testUser";
    ChallengeLogDAO challengeLog1 = new ChallengeLogDAO();
    challengeLog1.setChallengeAchievedSum(200L);
    challengeLog1.setTheme(ChallengeTheme.BUY_IN_BULK);
    ChallengeLogDAO challengeLog2 = new ChallengeLogDAO();
    challengeLog2.setChallengeAchievedSum(100L);
    challengeLog2.setTheme(ChallengeTheme.LIMIT_SNACKS);
    ChallengeLogDAO challengeLog3 = new ChallengeLogDAO();
    challengeLog3.setChallengeAchievedSum(500L);
    challengeLog3.setTheme(ChallengeTheme.LIMIT_VEHICLE_COSTS);
    List<ChallengeLogDAO> challengeLogs = List.of(challengeLog1, challengeLog2, challengeLog3);
    Map<TransactionCategory, Double> expectedMap = new HashMap<>();
    expectedMap.put(TransactionCategory.GROCERIES, 0.6666666666666666);
    expectedMap.put(TransactionCategory.TRANSPORTATION, 0.3333333333333333);

    // Mocking
    when(challengeLogRepository.findChallengeLogDAOByUserDAO_Username(username)).thenReturn(
        challengeLogs);

    // Assertion
    assertEquals(expectedMap, challengeLogService.getChallengesByCategoryRatio(username));
  }

  @Test
  @DisplayName("getChallengesByCategoryRatio throws exception when repository throws exception")
  public void testGetChallengesByCategoryRatioThrowsException() {
    // Test data
    String username = "testUser";

    // Mocking
    when(challengeLogRepository.findChallengeLogDAOByUserDAO_Username(username)).thenThrow(
        new RuntimeException());

    // Assertion
    assertThrows(RuntimeException.class,
        () -> challengeLogService.getChallengesByCategoryRatio(username));
  }

  @Test
  @DisplayName("Test getChallengesByCategoryAcceptedRatio returns correct map")
  public void testGetChallengesByCategoryAcceptedRatio() {
    // Test data
    String username = "testUser";
    ChallengeLogDAO challengeLog1 = new ChallengeLogDAO();
    challengeLog1.setAccepted(true);
    challengeLog1.setTheme(ChallengeTheme.BUY_IN_BULK);
    ChallengeLogDAO challengeLog2 = new ChallengeLogDAO();
    challengeLog2.setAccepted(false);
    challengeLog2.setTheme(ChallengeTheme.LIMIT_SNACKS);
    ChallengeLogDAO challengeLog3 = new ChallengeLogDAO();
    challengeLog3.setAccepted(true);
    challengeLog3.setTheme(ChallengeTheme.LIMIT_VEHICLE_COSTS);
    List<ChallengeLogDAO> challengeLogs = List.of(challengeLog1, challengeLog2, challengeLog3);
    Map<TransactionCategory, Double> logByCategoryRatio = new HashMap<>();
    logByCategoryRatio.put(TransactionCategory.GROCERIES, 0.6666666666666666);
    logByCategoryRatio.put(TransactionCategory.TRANSPORTATION, 0.3333333333333333);
    Map<TransactionCategory, Double> expectedMap = new HashMap<>();
    expectedMap.put(TransactionCategory.GROCERIES, 0.5);
    expectedMap.put(TransactionCategory.TRANSPORTATION, 1.0);

    // Mocking
    when(challengeLogRepository.findChallengeLogDAOByUserDAO_Username(username)).thenReturn(
        challengeLogs);
    when(challengeLogService1.getChallengesByCategoryRatio(username)).thenReturn(
        logByCategoryRatio);
    // Assertion
    assertEquals(expectedMap, challengeLogService.getChallengesByCategoryAcceptedRatio(username));
  }

  @Test
  @DisplayName("getChallengesByCategoryAcceptedRatio throws exception when repository throws exception")
  public void testGetChallengesByCategoryAcceptedRatioThrowsException() {
    // Test data
    String username = "testUser";

    // Mocking
    when(challengeLogRepository.findChallengeLogDAOByUserDAO_Username(username)).thenThrow(
        new RuntimeException());

    // Assertion
    assertThrows(RuntimeException.class,
        () -> challengeLogService.getChallengesByCategoryAcceptedRatio(username));
  }

  @Test
  @DisplayName("Test getChallengesByThemeRatio returns correct map")
  public void testGetChallengesByThemeRatio() {
    // Test data
    String username = "testUser";
    ChallengeLogDAO challengeLog1 = new ChallengeLogDAO();
    challengeLog1.setChallengeAchievedSum(200L);
    challengeLog1.setTheme(ChallengeTheme.BUY_IN_BULK);
    ChallengeLogDAO challengeLog2 = new ChallengeLogDAO();
    challengeLog2.setChallengeAchievedSum(100L);
    challengeLog2.setTheme(ChallengeTheme.LIMIT_SNACKS);
    ChallengeLogDAO challengeLog3 = new ChallengeLogDAO();
    challengeLog3.setChallengeAchievedSum(500L);
    challengeLog3.setTheme(ChallengeTheme.LIMIT_VEHICLE_COSTS);
    ChallengeLogDAO challengeLog4 = new ChallengeLogDAO();
    challengeLog4.setChallengeAchievedSum(0L);
    challengeLog4.setTheme(ChallengeTheme.LIMIT_VEHICLE_COSTS);
    List<ChallengeLogDAO> challengeLogs = List.of(challengeLog1, challengeLog2, challengeLog3,
        challengeLog4);
    Map<ChallengeTheme, Double> expectedMap = new HashMap<>();
    expectedMap.put(ChallengeTheme.BUY_IN_BULK, 0.25);
    expectedMap.put(ChallengeTheme.LIMIT_SNACKS, 0.25);
    expectedMap.put(ChallengeTheme.LIMIT_VEHICLE_COSTS, 0.5);

    // Mocking
    when(challengeLogRepository.findChallengeLogDAOByUserDAO_Username(username)).thenReturn(
        challengeLogs);

    // Assertion
    assertEquals(expectedMap, challengeLogService.getChallengesByThemeRatio(username));
    assertTrue(challengeLogService.getChallengesByThemeRatio(username).size() == 3);
  }

  @Test
  @DisplayName("getChallengesByThemeRatio throws exception when repository throws exception")
  public void testGetChallengesByThemeRatioThrowsException() {
    // Test data
    String username = "testUser";

    // Mocking
    when(challengeLogRepository.findChallengeLogDAOByUserDAO_Username(username)).thenThrow(
        new RuntimeException());

    // Assertion
    assertThrows(RuntimeException.class,
        () -> challengeLogService.getChallengesByThemeRatio(username));
  }
}