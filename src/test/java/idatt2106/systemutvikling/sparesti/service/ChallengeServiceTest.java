package idatt2106.systemutvikling.sparesti.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import idatt2106.systemutvikling.sparesti.dao.ChallengeDAO;
import idatt2106.systemutvikling.sparesti.repository.ChallengeRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ChallengeServiceTest {

  @Mock
  private ChallengeRepository challengeRepository;

  @InjectMocks
  private ChallengeService challengeService;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
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
}
