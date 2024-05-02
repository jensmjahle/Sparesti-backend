package idatt2106.systemutvikling.sparesti.controller;

import idatt2106.systemutvikling.sparesti.dto.ChallengeDTO;
import idatt2106.systemutvikling.sparesti.dto.MilestoneDTO;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ChallengeController.class)
@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class ChallengeControllerTests {

    public static final String TEST_USERNAME = "Test user";
    public static final String TEST_USERNAME_OTHER = "Not " + TEST_USERNAME;

    private static final ChallengeDTO TEST_CHALLENGE = new ChallengeDTO(
        99L,
            TEST_USERNAME,
        "Test",
        "This is a test challenge",
        100L,
        0L,
        LocalDateTime.now(),
        LocalDateTime.now().plusDays(3),
        0,
        true
    );

    private static final MilestoneDTO TEST_MILESTONE = new MilestoneDTO(
            1L,
            TEST_USERNAME,
            "Test milestone",
            "A milestone for testing",
            100L,
            0L,
            "image.jpg",
            LocalDateTime.now(),
            LocalDateTime.now().plusDays(3)
            );

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ChallengeService challengeService;

    @MockBean
    private MilestoneService milestoneService;


    @Test
    public void moveChallengeToLog_okWhenUserOwnsChallenge() throws Exception {
        try (MockedStatic<CurrentUserService> utilities = Mockito.mockStatic(CurrentUserService.class)) {
            // Copy the default values used for testing
            final ChallengeDTO challenge = TEST_CHALLENGE.toBuilder().build();
            final Long challengeId = challenge.getChallengeId();

            // Set the currently logged-in user to the owner of the challenge and milestone
            utilities.when(CurrentUserService::getCurrentUsername).thenReturn(TEST_USERNAME);

            // Stub the service call
            doNothing().when(challengeService).moveChallengeToLog(challengeId);

            // Set return values of service layer functions
            given(challengeService.getChallenge(challengeId)).willReturn(challenge);

            // Http request path
            final String URI = "/user/challenge/delete/" + challengeId;

            // Perform http request
            mvc.perform(delete(URI)).andExpect(status().is2xxSuccessful());
        }
    }


    @Test
    public void completeChallenge_okWhenUserOwnsBothMilestoneAndChallenge() throws Exception {
        try (MockedStatic<CurrentUserService> utilities = Mockito.mockStatic(CurrentUserService.class)) {
            // Copy the default values used for testing
            final ChallengeDTO challenge = TEST_CHALLENGE.toBuilder().build();
            final MilestoneDTO milestone = TEST_MILESTONE.toBuilder().build();
            final Long challengeId = challenge.getChallengeId();
            final Long milestoneId = milestone.getMilestoneId();

            // Set the currently logged-in user to the owner of the challenge and milestone
            utilities.when(CurrentUserService::getCurrentUsername).thenReturn(TEST_USERNAME);

            // Stub the service call
            doNothing().when(challengeService).completeChallengeForCurrentUser(challengeId, milestoneId);

            // Set return values of service layer functions
            given(challengeService.getChallenge(challengeId)).willReturn(challenge);
            given(milestoneService.getMilestoneDTOById(milestoneId)).willReturn(milestone);

            // Http request path
            final String URI = String.format("/user/challenge/complete?challengeId=%d&milestoneId=%d",
                    challengeId,
                    milestoneId);

            // Perform http request
            mvc.perform(post(URI)).andExpect(status().isOk());
        }
    }

    @Test
    public void completeChallenge_badRequestWhenUserDoesNotOwnChallenge() throws Exception {
        try (MockedStatic<CurrentUserService> utilities = Mockito.mockStatic(CurrentUserService.class)) {
            // Copy the default values used for testing
            final ChallengeDTO challenge = TEST_CHALLENGE.toBuilder().username("Other user").build();
            final MilestoneDTO milestone = TEST_MILESTONE.toBuilder().build();
            final Long challengeId = challenge.getChallengeId();
            final Long milestoneId = milestone.getMilestoneId();

            // Set the currently logged-in user to the owner of the challenge and milestone
            utilities.when(CurrentUserService::getCurrentUsername).thenReturn(TEST_USERNAME);

            // Stub the service call
            doNothing().when(challengeService).completeChallengeForCurrentUser(challengeId, milestoneId);

            // Set return values of service layer functions
            given(challengeService.getChallenge(challengeId)).willReturn(challenge);
            given(milestoneService.getMilestoneDTOById(milestoneId)).willReturn(milestone);

            // Http request path
            final String URI = String.format("/user/challenge/complete?challengeId=%d&milestoneId=%d",
                    challengeId,
                    milestoneId);

            // Perform http request
            mvc.perform(post(URI)).andExpect(status().isBadRequest());
        }
    }

    @Test
    public void completeChallenge_badRequestWhenUserDoesNotOwnMilestone() throws Exception {
        try (MockedStatic<CurrentUserService> utilities = Mockito.mockStatic(CurrentUserService.class)) {
            // Copy the default values used for testing
            final ChallengeDTO challenge = TEST_CHALLENGE.toBuilder().build();
            final MilestoneDTO milestone = TEST_MILESTONE.toBuilder().username(TEST_USERNAME_OTHER).build();
            final Long challengeId = challenge.getChallengeId();
            final Long milestoneId = milestone.getMilestoneId();

            // Set the currently logged-in user to the owner of the challenge and milestone
            utilities.when(CurrentUserService::getCurrentUsername).thenReturn(TEST_USERNAME);

            // Stub the service call
            doNothing().when(challengeService).completeChallengeForCurrentUser(challengeId, milestoneId);

            // Set return values of service layer functions
            given(challengeService.getChallenge(challengeId)).willReturn(challenge);
            given(milestoneService.getMilestoneDTOById(milestoneId)).willReturn(milestone);

            // Http request path
            final String URI = String.format("/user/challenge/complete?challengeId=%d&milestoneId=%d",
                    challengeId,
                    milestoneId);

            // Perform http request
            mvc.perform(post(URI)).andExpect(status().isBadRequest());
        }
    }

    @Test
    public void moveChallengeToLog_badRequestWhenUserDoesNotOwnChallenge() throws Exception {
        try (MockedStatic<CurrentUserService> utilities = Mockito.mockStatic(CurrentUserService.class)) {
            // Copy the default values used for testing
            final ChallengeDTO challenge = TEST_CHALLENGE.toBuilder().username(TEST_USERNAME_OTHER).build();
            final Long challengeId = challenge.getChallengeId();

            // Set the currently logged-in user to the owner of the challenge and milestone
            utilities.when(CurrentUserService::getCurrentUsername).thenReturn(TEST_USERNAME);

            // Stub the service call
            doNothing().when(challengeService).moveChallengeToLog(challengeId);

            // Set return values of service layer functions
            given(challengeService.getChallenge(challengeId)).willReturn(challenge);

            // Http request path
            final String URI = "/user/challenge/delete/" + challengeId;

            // Perform http request
            mvc.perform(delete(URI)).andExpect(status().is4xxClientError());
        }
    }

}
