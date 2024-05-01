package idatt2106.systemutvikling.sparesti.controller;

import idatt2106.systemutvikling.sparesti.dto.ChallengeDTO;
import idatt2106.systemutvikling.sparesti.repository.ChallengeLogRepository;
import idatt2106.systemutvikling.sparesti.repository.ChallengeRepository;
import idatt2106.systemutvikling.sparesti.service.ChallengeService;
import idatt2106.systemutvikling.sparesti.service.CurrentUserService;
import idatt2106.systemutvikling.sparesti.service.MilestoneService;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
    private ChallengeRepository challengeRepository;

    @MockBean
    private ChallengeLogRepository challengeLogRepository;

    @MockBean
    private MilestoneService milestoneService;



    //@Test
    //public void deleteChallenge_preventsUsersFromDeletingEachOthersChallenges() throws Exception {
    //    final ChallengeDTO challenge = TEST_CHALLENGE.toBuilder().build();
    //
    //    given(challengeService.getChallenge(challenge.getChallengeId())).willReturn(challenge);
    //    doNothing().when(challengeService).deleteChallenge(challenge.getChallengeId());
    //
    //    try (MockedStatic<CurrentUserService> utilities = Mockito.mockStatic(CurrentUserService.class)) {
    //        utilities.when(CurrentUserService::getCurrentUsername).thenReturn("Other user");
    //        mvc.perform(delete("/user/challenge/delete/" + challenge.getChallengeId()))
    //                .andExpect(status().is4xxClientError());
    //
    //        verify(challengeService, times(0)).deleteChallenge(challenge.getChallengeId());
    //    }
    //}


    //@Test
    //public void completeChallenge_preventsUsersFromCompletingEachOthersChallenges() throws Exception {
    //    final ChallengeDTO challenge = TEST_CHALLENGE.toBuilder().build();

    //    given(challengeService.getChallenge(challenge.getChallengeId())).willReturn(challenge);
    //    doNothing().when(challengeService).archiveActiveChallenge(challenge.getChallengeId());

    //    try (MockedStatic<CurrentUserService> utilities = Mockito.mockStatic(CurrentUserService.class)) {
    //        utilities.when(CurrentUserService::getCurrentUsername).thenReturn("Other user");
    //        mvc.perform(delete("/user/challenge/delete/" + challenge.getChallengeId()))
    //                .andExpect(status().is4xxClientError());

    //        verify(challengeRepository, times(0)).save(challenge.getChallengeId());
    //    }
    //}
}
