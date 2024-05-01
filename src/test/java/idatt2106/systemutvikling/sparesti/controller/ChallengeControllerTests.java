package idatt2106.systemutvikling.sparesti.controller;

import idatt2106.systemutvikling.sparesti.dto.ChallengeDTO;
import idatt2106.systemutvikling.sparesti.service.ChallengeService;
import idatt2106.systemutvikling.sparesti.service.CurrentUserService;
import idatt2106.systemutvikling.sparesti.service.MilestoneService;
import idatt2106.systemutvikling.sparesti.service.TransactionService;
import org.hibernate.grammars.hql.HqlParser;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.Date;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ChallengeController.class)
@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class ChallengeControllerTests {

  private static final ChallengeDTO TEST_CHALLENGE = new ChallengeDTO(
      99L,
      "Darth",
      "Test",
      "This is a test challenge",
      100L,
      0L,
      LocalDateTime.now(),
      LocalDateTime.now().plusDays(3),
      0,
      true
  );

  @Autowired
  private MockMvc mvc;

  @MockBean
  private ChallengeService challengeService;

  @MockBean
  private MilestoneService milestoneService;

  @Test
  public void deleteChallenge_ReturnOkWhenAllIsWell() throws Exception {
    final ChallengeDTO challenge = TEST_CHALLENGE.toBuilder().build();

    given(challengeService.getChallenge(challenge.getChallengeId())).willReturn(challenge);
    doNothing().when(challengeService).moveChallengeToLog(challenge.getChallengeId());

    try (MockedStatic<CurrentUserService> utilities = Mockito.mockStatic(
        CurrentUserService.class)) {
      utilities.when(CurrentUserService::getCurrentUsername).thenReturn(challenge.getUsername());
      mvc.perform(delete("/user/challenge/delete/" + challenge.getChallengeId()))
          .andExpect(status().isOk());
    }
  }
}
