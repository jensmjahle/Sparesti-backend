package idatt2106.systemutvikling.sparesti.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import idatt2106.systemutvikling.sparesti.dao.ChallengeDAO;
import idatt2106.systemutvikling.sparesti.dao.UserDAO;
import idatt2106.systemutvikling.sparesti.dto.ChallengeDTO;
import idatt2106.systemutvikling.sparesti.dto.MilestoneDTO;
import idatt2106.systemutvikling.sparesti.mapper.ChallengeMapper;
import idatt2106.systemutvikling.sparesti.repository.ChallengeLogRepository;
import idatt2106.systemutvikling.sparesti.repository.ChallengeRepository;
import idatt2106.systemutvikling.sparesti.service.ChallengeService;
import idatt2106.systemutvikling.sparesti.service.CurrentUserService;
import idatt2106.systemutvikling.sparesti.service.MilestoneService;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        false
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



    private static final ObjectMapper mapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();

    private static MockedStatic<CurrentUserService> utilities;

    @BeforeAll
    public static void beforeAll() {

        utilities = Mockito.mockStatic(CurrentUserService.class);
    }

    @AfterAll
    public static void afterAll() {
        utilities.close();
    }

    private static void setUser(String username) {
        utilities.when(CurrentUserService::getCurrentUsername).thenReturn(username);
    }



    // ### getActiveChallenges ###

    @Test
    public void getActiveChallenges_okWhenInvalidValues() throws Exception {
        int page = -1;
        int size = -1;

        // Setup pageable object for use in response generation
        Pageable pageable = Pageable.ofSize(20).withPage(0);

        // Set authenticated username
        setUser(TEST_USERNAME);

        // Set return value of service layer
        given(challengeService.getActiveChallenges(TEST_USERNAME, pageable)).willReturn(null);

        // Http request path
        final String URI = String.format("/user/challenge/paginated/active?page=%d&size=%d", page, size);

        // Perform http request
        mvc.perform(get(URI)).andExpect(status().isOk());

        // Verify that the service layer function was called
        verify(challengeService).getActiveChallenges(TEST_USERNAME, pageable);
    }



    // ### getInactiveChallenges ###

    @Test
    public void getInactiveChallenges_okWhenInvalidValues() throws Exception {
        int page = -1;
        int size = -1;

        // Setup pageable object for use in response generation
        Pageable pageable = Pageable.ofSize(20).withPage(0);

        // Set authenticated username
        setUser(TEST_USERNAME);

        // Set return value of service layer
        given(challengeService.getInactiveChallenges(TEST_USERNAME, pageable)).willReturn(null);

        // Http request path
        final String URI = String.format("/user/challenge/paginated/inactive?page=%d&size=%d", page, size);

        // Perform http request
        mvc.perform(get(URI)).andExpect(status().isOk());

        // Verify that the service layer function was called
        verify(challengeService).getInactiveChallenges(TEST_USERNAME, pageable);
    }



    // ### createChallenge ###

    @Test
    public void createChallenge_returnCreated() throws Exception {
        // Copy the default values used for testing
        final ChallengeDTO challenge = TEST_CHALLENGE.toBuilder().build();
        final String challengeJson = mapper.writeValueAsString(challenge);

        // Set authenticated username
        setUser(TEST_USERNAME);

        // Set return value of service layer
        given(challengeService.createChallenge(any(ChallengeDTO.class))).willReturn(ChallengeMapper.toDAO(challenge));

        // Perform http request
        mvc.perform(post("/user/challenge/create")
                        .content(challengeJson)
                        .contentType("application/json"))
                .andExpect(status().isCreated());
    }



    // ### getChallenge ###

    @Test
    public void getChallenge_forbiddenWhenChallengeIsNotOwnedByUser() throws Exception {
        // Copy the default values used for testing
        final ChallengeDTO challenge = TEST_CHALLENGE.toBuilder()
                .username(TEST_USERNAME_OTHER) // Set owner to another user
                .build();
        final Long challengeId = challenge.getChallengeId();

        // Set the currently logged-in user to the owner of the challenge and milestone
        setUser(TEST_USERNAME);

        // Set return values of service layer functions
        given(challengeService.getChallenge(challengeId)).willReturn(challenge);

        // Http request path
        final String URI = "/user/challenge/" + challengeId;

        // Perform http request
        mvc.perform(get(URI)).andExpect(status().isForbidden());
    }

    @Test
    public void getChallenge_successWhenChallengeIsOwnedByUser() throws Exception {
        // Copy the default values used for testing
        final ChallengeDTO challenge = TEST_CHALLENGE.toBuilder().build();
        final Long challengeId = challenge.getChallengeId();

        // Set the currently logged-in user to the owner of the challenge and milestone
        setUser(challenge.getUsername());

        // Set return values of service layer functions
        given(challengeService.getChallenge(challengeId)).willReturn(challenge);

        // Http request path
        final String URI = "/user/challenge/" + challengeId;

        // Perform http request
        mvc.perform(get(URI)).andExpect(status().is2xxSuccessful());
    }



    // ### activateChallenge ###

    @Test
    public void activateChallenge_conflictWhenChallengeIsActive() throws Exception {
        // Copy the default values used for testing
        final ChallengeDTO challenge = TEST_CHALLENGE.toBuilder()
                .isActive(true) // Set challenge to ACTIVE
                .build();
        final ChallengeDAO challengeDAO = ChallengeMapper.toDAO(challenge);
        final Long challengeId = challenge.getChallengeId();

        // Set the currently logged-in user to the owner of the challenge and milestone
        setUser(challenge.getUsername());

        // Set return values of service layer functions
        given(challengeService.getChallenge(challengeId)).willReturn(challenge);
        given(challengeService.activateChallenge(challengeId)).willReturn(challengeDAO);

        // Http request path
        final String URI = "/user/challenge/activate/" + challengeId;

        // Perform http request
        mvc.perform(post(URI)).andExpect(status().isConflict());
    }

    @Test
    public void activateChallenge_forbiddenWhenChallengeIsNotOwnedByUser() throws Exception {
        // Copy the default values used for testing
        final ChallengeDTO challenge = TEST_CHALLENGE.toBuilder()
                .username(TEST_USERNAME_OTHER) // Set owner of challenge to another user
                .build();
        final ChallengeDAO challengeDAO = ChallengeMapper.toDAO(challenge);
        final Long challengeId = challenge.getChallengeId();

        // Set the currently logged-in user to the owner of the challenge and milestone
        setUser(TEST_USERNAME);

        // Set return values of service layer functions
        given(challengeService.getChallenge(challengeId)).willReturn(challenge);
        given(challengeService.activateChallenge(challengeId)).willReturn(challengeDAO);

        // Http request path
        final String URI = "/user/challenge/activate/" + challengeId;

        // Perform http request
        mvc.perform(post(URI)).andExpect(status().isForbidden());
    }

    @Test
    public void activateChallenge_isOkWhen_challengeIsOwnedByUser_andChallengeIsNotActive() throws Exception {
        // Copy the default values used for testing
        final ChallengeDTO challenge = TEST_CHALLENGE.toBuilder().build();
        final ChallengeDAO challengeDAO = ChallengeMapper.toDAO(challenge);
        final Long challengeId = challenge.getChallengeId();

        // Set the currently logged-in user to the owner of the challenge and milestone
        setUser(challenge.getUsername());

        // Set return values of service layer functions
        given(challengeService.getChallenge(challengeId)).willReturn(challenge);
        given(challengeService.activateChallenge(challengeId)).willReturn(challengeDAO);

        // Http request path
        final String URI = "/user/challenge/activate/" + challengeId;

        // Perform http request
        mvc.perform(post(URI)).andExpect(status().isOk());
    }



    // ### completeChallenge ###

    @Test
    public void completeChallenge_okWhenUserOwnsBothMilestoneAndChallenge() throws Exception {
        // Copy the default values used for testing
        final ChallengeDTO challenge = TEST_CHALLENGE.toBuilder().build();
        final MilestoneDTO milestone = TEST_MILESTONE.toBuilder().build();
        final Long challengeId = challenge.getChallengeId();
        final Long milestoneId = milestone.getMilestoneId();

        // Set the currently logged-in user to the owner of the challenge and milestone
        setUser(challenge.getUsername());

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

    @Test
    public void completeChallenge_forbiddenWhenUserDoesNotOwnChallenge() throws Exception {
        // Copy the default values used for testing
        final ChallengeDTO challenge = TEST_CHALLENGE.toBuilder().username(TEST_USERNAME_OTHER).build();
        final MilestoneDTO milestone = TEST_MILESTONE.toBuilder().build();
        final Long challengeId = challenge.getChallengeId();
        final Long milestoneId = milestone.getMilestoneId();

        // Set the currently logged-in user to the owner of the challenge and milestone
        setUser(TEST_USERNAME);

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
        mvc.perform(post(URI)).andExpect(status().isForbidden());
    }

    @Test
    public void completeChallenge_forbiddenWhenUserDoesNotOwnMilestone() throws Exception {
        // Copy the default values used for testing
        final ChallengeDTO challenge = TEST_CHALLENGE.toBuilder().build();
        final MilestoneDTO milestone = TEST_MILESTONE.toBuilder().username(TEST_USERNAME_OTHER).build();
        final Long challengeId = challenge.getChallengeId();
        final Long milestoneId = milestone.getMilestoneId();

        // Set the currently logged-in user to the owner of the challenge and milestone
        setUser(challenge.getUsername());

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
        mvc.perform(post(URI)).andExpect(status().isForbidden());
    }



    // ##### moveChallengeToLog ####

    @Test
    public void moveChallengeToLog_okWhenUserOwnsChallenge() throws Exception {
        // Copy the default values used for testing
        final ChallengeDTO challenge = TEST_CHALLENGE.toBuilder().build();
        final Long challengeId = challenge.getChallengeId();

        // Set the currently logged-in user to the owner of the challenge and milestone
        setUser(challenge.getUsername());

        // Stub the service call
        doNothing().when(challengeService).moveChallengeToLog(challengeId);

        // Set return values of service layer functions
        given(challengeService.getChallenge(challengeId)).willReturn(challenge);

        // Http request path
        final String URI = "/user/challenge/delete/" + challengeId;

        // Perform http request
        mvc.perform(delete(URI)).andExpect(status().is2xxSuccessful());
    }

    @Test
    public void moveChallengeToLog_forbiddenWhenUserDoesNotOwnChallenge() throws Exception {
        // Copy the default values used for testing
        final ChallengeDTO challenge = TEST_CHALLENGE.toBuilder().username(TEST_USERNAME_OTHER).build();
        final Long challengeId = challenge.getChallengeId();

        // Set the currently logged-in user to the owner of the challenge and milestone
        setUser(TEST_USERNAME);

        // Stub the service call
        doNothing().when(challengeService).moveChallengeToLog(challengeId);

        // Set return values of service layer functions
        given(challengeService.getChallenge(challengeId)).willReturn(challenge);

        // Http request path
        final String URI = "/user/challenge/delete/" + challengeId;

        // Perform http request
        mvc.perform(delete(URI)).andExpect(status().isForbidden());
    }
}
