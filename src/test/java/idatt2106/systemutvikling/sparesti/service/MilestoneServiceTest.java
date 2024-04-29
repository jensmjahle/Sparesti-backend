package idatt2106.systemutvikling.sparesti.service;

import idatt2106.systemutvikling.sparesti.dao.MilestoneDAO;
import idatt2106.systemutvikling.sparesti.dao.UserDAO;
import idatt2106.systemutvikling.sparesti.dto.MilestoneDTO;
import idatt2106.systemutvikling.sparesti.mapper.MilestoneMapper;
import idatt2106.systemutvikling.sparesti.repository.MilestoneRepository;
import idatt2106.systemutvikling.sparesti.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MilestoneServiceTest {

  @Mock
  private MilestoneRepository milestoneRepository;

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private MilestoneService milestoneService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    milestoneRepository = Mockito.mock(MilestoneRepository.class);
    ReflectionTestUtils.setField(milestoneService, "milestoneRepository", milestoneRepository);
  }

  @Test
  @DisplayName("Test getActiveMilestonesDTOsByUsernamePaginated returns a page of MilestoneDTOs")
  public void testGetActiveMilestonesDTOsByUsernamePaginated() {

    Pageable pageable = PageRequest.of(0, 2);

    UserDAO user1 = new UserDAO();
    user1.setUsername("JohnSmith12");

    MilestoneDAO milestoneDAO1 = new MilestoneDAO();
    milestoneDAO1.setMilestoneId(1L);
    milestoneDAO1.setMilestoneTitle("Milestone Title 1");
    milestoneDAO1.setStartDate(LocalDateTime.of(2024, 1, 1, 12, 0, 0 ));
    milestoneDAO1.setDeadlineDate(LocalDateTime.of(2025, 1, 1, 12, 0, 0 ));
    milestoneDAO1.setUserDAO(user1);

    MilestoneDAO milestoneDAO2 = new MilestoneDAO();
    milestoneDAO2.setMilestoneId(2L);
    milestoneDAO2.setMilestoneTitle("Milestone Title 2");
    milestoneDAO2.setStartDate(LocalDateTime.of(2024, 1, 1, 12, 0, 0 ));
    milestoneDAO2.setDeadlineDate(LocalDateTime.of(2025, 1, 1, 12, 0, 0 ));
    milestoneDAO2.setUserDAO(user1);

    List<MilestoneDTO> milestoneDTOS = List.of(MilestoneMapper.toDTO(milestoneDAO1), MilestoneMapper.toDTO(milestoneDAO2));

    when(milestoneRepository.findMilestoneDAOByUserDAO_Username(user1.getUsername(), pageable)).thenReturn(new PageImpl<>(List.of(milestoneDAO1, milestoneDAO2), pageable, 2));

    assertEquals(milestoneDTOS.get(0).getMilestoneId(), milestoneService.getActiveMilestonesDTOsByUsernamePaginated("JohnSmith12", pageable).getContent().get(0).getMilestoneId());
    assertEquals(milestoneDTOS.get(0).getMilestoneTitle(), milestoneService.getActiveMilestonesDTOsByUsernamePaginated("JohnSmith12", pageable).getContent().get(0).getMilestoneTitle());

    assertEquals(milestoneDTOS.get(1).getMilestoneId(), milestoneService.getActiveMilestonesDTOsByUsernamePaginated("JohnSmith12", pageable).getContent().get(1).getMilestoneId());
    assertEquals(milestoneDTOS.get(1).getMilestoneTitle(), milestoneService.getActiveMilestonesDTOsByUsernamePaginated("JohnSmith12", pageable).getContent().get(1).getMilestoneTitle());
  }

  @Test
  @DisplayName("Test getActiveMilestonesDTOsByUsername returns a list of MilestoneDTOs")
  public void testGetActiveMilestonesDTOsByUsername() {
    UserDAO user1 = new UserDAO();
    user1.setUsername("JohnSmith12");

    MilestoneDAO milestoneDAO1 = new MilestoneDAO();
    milestoneDAO1.setMilestoneId(1L);
    milestoneDAO1.setMilestoneTitle("Milestone Title 1");
    milestoneDAO1.setStartDate(LocalDateTime.of(2024, 1, 1, 12, 0, 0 ));
    milestoneDAO1.setDeadlineDate(LocalDateTime.of(2025, 1, 1, 12, 0, 0 ));
    milestoneDAO1.setUserDAO(user1);

    MilestoneDAO milestoneDAO2 = new MilestoneDAO();
    milestoneDAO2.setMilestoneId(2L);
    milestoneDAO2.setMilestoneTitle("Milestone Title 2");
    milestoneDAO2.setStartDate(LocalDateTime.of(2024, 1, 1, 12, 0, 0 ));
    milestoneDAO2.setDeadlineDate(LocalDateTime.of(2025, 1, 1, 12, 0, 0 ));
    milestoneDAO2.setUserDAO(user1);

    List<MilestoneDTO> milestoneDTOS = List.of(MilestoneMapper.toDTO(milestoneDAO1), MilestoneMapper.toDTO(milestoneDAO2));

    when(milestoneRepository.findMilestoneDAOByUserDAO_Username(user1.getUsername())).thenReturn(List.of(milestoneDAO1, milestoneDAO2));

    assertEquals(milestoneDTOS.get(0).getMilestoneId(), milestoneService.getActiveMilestonesDTOsByUsername("JohnSmith12").get(0).getMilestoneId());
    assertEquals(milestoneDTOS.get(0).getMilestoneTitle(), milestoneService.getActiveMilestonesDTOsByUsername("JohnSmith12").get(0).getMilestoneTitle());

    assertEquals(milestoneDTOS.get(1).getMilestoneId(), milestoneService.getActiveMilestonesDTOsByUsername("JohnSmith12").get(1).getMilestoneId());
    assertEquals(milestoneDTOS.get(1).getMilestoneTitle(), milestoneService.getActiveMilestonesDTOsByUsername("JohnSmith12").get(1).getMilestoneTitle());
  }

  @Test
  @DisplayName("Test completeMilestone")
  public void testCompleteMilestone() {
    UserDAO user1 = new UserDAO();
    user1.setUsername("JohnSmith12");

    MilestoneDAO milestoneDAO1 = new MilestoneDAO();
    milestoneDAO1.setMilestoneId(1L);
    milestoneDAO1.setMilestoneTitle("Milestone Title 1");
    milestoneDAO1.setStartDate(LocalDateTime.of(2024, 1, 1, 12, 0, 0 ));
    milestoneDAO1.setDeadlineDate(LocalDateTime.of(2025, 1, 1, 12, 0, 0 ));
    milestoneDAO1.setUserDAO(user1);

    when(milestoneRepository.findMilestoneDAOByMilestoneIdAndUserDAO_Username(milestoneDAO1.getMilestoneId(), user1.getUsername())).thenReturn(milestoneDAO1);

    milestoneService.completeMilestone(user1.getUsername(), milestoneDAO1.getMilestoneId());

    verify(milestoneRepository, times(0)).delete(milestoneDAO1);
  }

  @Test
  @DisplayName("Test createMilestoneDTO returns a milestoneDTO")
  public void testCreateMilestoneDTO() {
    UserDAO user1 = new UserDAO();
    user1.setUsername("JohnSmith12");

    MilestoneDAO milestoneDAO1 = new MilestoneDAO();
    milestoneDAO1.setMilestoneId(1L);
    milestoneDAO1.setMilestoneTitle("Milestone Title 1");
    milestoneDAO1.setStartDate(LocalDateTime.of(2024, 1, 1, 12, 0, 0 ));
    milestoneDAO1.setDeadlineDate(LocalDateTime.of(2025, 1, 1, 12, 0, 0 ));
    milestoneDAO1.setUserDAO(user1);

    MilestoneDTO milestoneDTO1 = MilestoneMapper.toDTO(milestoneDAO1);

    when(userRepository.findByUsername(user1.getUsername())).thenReturn(user1);
    //when(milestoneRepository.save(milestoneDAO1)).thenReturn(milestoneDAO1);

    assertEquals(milestoneDTO1.getMilestoneId(), milestoneService.createMilestoneDTO(user1.getUsername(), milestoneDTO1).getMilestoneId());
    assertEquals(milestoneDTO1.getMilestoneTitle(), milestoneService.createMilestoneDTO(user1.getUsername(), milestoneDTO1).getMilestoneTitle());
  }

  @Test
  @DisplayName("Test getMilestoneDTOById returns a milestoneDTO")
  public void testGetMilestoneDTOById() {
    UserDAO user1 = new UserDAO();
    user1.setUsername("JohnSmith12");

    MilestoneDAO milestoneDAO1 = new MilestoneDAO();
    milestoneDAO1.setMilestoneId(1L);
    milestoneDAO1.setMilestoneTitle("Milestone Title 1");
    milestoneDAO1.setStartDate(LocalDateTime.of(2024, 1, 1, 12, 0, 0 ));
    milestoneDAO1.setDeadlineDate(LocalDateTime.of(2025, 1, 1, 12, 0, 0 ));
    milestoneDAO1.setUserDAO(user1);

    when(milestoneRepository.findMilestoneDAOByMilestoneId(milestoneDAO1.getMilestoneId())).thenReturn(milestoneDAO1);

    assertEquals(milestoneDAO1.getMilestoneId(), milestoneService.getMilestoneDTOById(milestoneDAO1.getMilestoneId()).getMilestoneId());
    assertEquals(milestoneDAO1.getMilestoneTitle(), milestoneService.getMilestoneDTOById(milestoneDAO1.getMilestoneId()).getMilestoneTitle());
  }

  @Test
  @DisplayName("Test updateMilestoneDTO returns a milestoneDTO")
  public void testUpdateMilestoneDTO() {
    UserDAO user1 = new UserDAO();
    user1.setUsername("JohnSmith12");

    MilestoneDAO milestoneDAO1 = new MilestoneDAO();
    milestoneDAO1.setMilestoneId(1L);
    milestoneDAO1.setMilestoneTitle("Milestone Title 1");
    milestoneDAO1.setStartDate(LocalDateTime.of(2024, 1, 1, 12, 0, 0 ));
    milestoneDAO1.setDeadlineDate(LocalDateTime.of(2025, 1, 1, 12, 0, 0 ));
    milestoneDAO1.setUserDAO(user1);
    milestoneDAO1.setMilestoneCurrentSum(0L);
    milestoneDAO1.setMilestoneGoalSum(200L);

    MilestoneDTO milestoneDTO1 = new MilestoneDTO();
    milestoneDTO1.setMilestoneId(1L);
    milestoneDTO1.setMilestoneTitle("Milestone Title 1");
    milestoneDTO1.setStartDate(LocalDateTime.of(2024, 1, 1, 12, 0, 0 ));
    milestoneDTO1.setDeadlineDate(LocalDateTime.of(2025, 1, 1, 12, 0, 0 ));
    milestoneDTO1.setMilestoneCurrentSum(100L);
    milestoneDTO1.setMilestoneGoalSum(200L);
    milestoneDTO1.setUsername(user1.getUsername());


    when(milestoneRepository.findMilestoneDAOByMilestoneId(milestoneDAO1.getMilestoneId())).thenReturn(milestoneDAO1);

    assertEquals(milestoneDTO1.getMilestoneId(), milestoneService.updateMilestoneDTO(user1.getUsername(), milestoneDTO1).getMilestoneId());
    assertEquals(milestoneDTO1.getMilestoneTitle(), milestoneService.updateMilestoneDTO(user1.getUsername(), milestoneDTO1).getMilestoneTitle());
  }

  @Test
  @DisplayName("Test editMilestone returns a milestoneDTO")
  public void testEditMilestone() {
    UserDAO user1 = new UserDAO();
    user1.setUsername("JohnSmith12");

    MilestoneDAO milestoneDAO1 = new MilestoneDAO();
    milestoneDAO1.setMilestoneId(1L);
    milestoneDAO1.setMilestoneTitle("Milestone Title 1");
    milestoneDAO1.setStartDate(LocalDateTime.of(2024, 1, 1, 12, 0, 0 ));
    milestoneDAO1.setDeadlineDate(LocalDateTime.of(2025, 1, 1, 12, 0, 0 ));
    milestoneDAO1.setUserDAO(user1);
    milestoneDAO1.setMilestoneCurrentSum(0L);
    milestoneDAO1.setMilestoneGoalSum(100L);
    milestoneDAO1.setMilestoneDescription("Milestone Description 1");

    MilestoneDTO milestoneDTO1 = new MilestoneDTO();
    milestoneDTO1.setMilestoneId(1L);
    milestoneDTO1.setMilestoneTitle("Milestone Title 2");
    milestoneDTO1.setStartDate(LocalDateTime.of(2023, 1, 1, 12, 0, 0 ));
    milestoneDTO1.setDeadlineDate(LocalDateTime.of(2024, 1, 1, 12, 0, 0 ));
    milestoneDTO1.setMilestoneCurrentSum(100L);
    milestoneDTO1.setMilestoneGoalSum(200L);
    milestoneDTO1.setUsername(user1.getUsername());
    milestoneDTO1.setMilestoneImage("data:image/jpeg;base64,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duZxGebzxMIxObOv3ou3/BV/IrnXD2XhNoeufYjbOZgctHUxxTaOkYWvGibgNMkgGu2+0rv09uqiji1PJbb11rWamLlrOMRG/wBfea8VOXcAW5TrdZuRbC6H4ND4AQMviiQDQ09+m2+KlQV84yCTa9ht2b+4gsnmF1YS3gmm74QPTKOQtpKgg2IglII2ghjtaYy69BEVaq1E+NT2wrwI77Du2/Fef6H1bjzHO1lR0tkcxVu38XGN3r70MVq3dCQsZSsDkG22H017fm76uEkjvq9sRi3T6IfLNsznXXfS7pW1WEKBAgyQJdAXQV2x+ICrnF72nlHV5t2tUl+mIu1b+l9X0Vczpbc4/wBtPZDwcbBJ26v61LViHVxpiBxnbr1avlTin2iQczmqonF/7tp+d+KsdBG6r1fN5XhVObFufPPYle673iChAqAQCAQV04odl8UgH+Uj8dUII6eSCNljq6mpBa3N57WUPwWHwGoGTxRxth8HwpvipUFejUG99XDq29VBZPif3Xwr+fL1NjD9aCRaqn02PZe2m1zeyCPrRnbr4lcVdUxPsRY3NXUAm1RFYjbZ1+xb61X06Kaear3PaTwpsTEZt1e2HhygzdPpad87qhjgwC40SL3cGixvvkLGrRYpmrPNGfY69Dwio1V+mzFuYz0582THXDMw9GFAEiJmcQJCydzkNpaaOA0xeYwRpccDQbknZom21WNrWW6bcUznMfrreV13BurVaiq99pjPRjPzdAZ227tGe6jrfqLZy631T+vW5Z4JVeV+H6tnpqggFtIb31gy2Furobesk62joif165YeCsxO+78P1KzOo0a30pHUlB1ddgUxrbePvRPu74RPBWr/AG3fh+sljzsxfrU0g6jmne4Ak6210Z9kd5PBO70XI9kvNUZ2RfmKW4t+tJY36gae+tdWvpifu0z693e20cEt337u/wA0fVphzsv16VKw71pC3s3abpGvp6afe2VcEqP9t2fXH1gwcSrDLLJLYN45I99hrsXOLrX3bXXDczVM19cvUaexFq3TbznERHsjDz8dPB2NvVWG9s4kE44eDsalG9PEh3cksp3UL3vbG15e0NsTogWN9xdFjU/ZZ3Zzj3Z71ZtPZVOuopomqYxOes5352JbG1NGDuXc4gdUWF+yujl/XT7/AKKeOCdrO+5OPRB+ZF40+spWzSNa1xc4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");
    milestoneDTO1.setMilestoneDescription("New description");

    when(milestoneRepository.findMilestoneDAOByMilestoneId(milestoneDAO1.getMilestoneId())).thenReturn(milestoneDAO1);
    when(userRepository.findByUsername(user1.getUsername())).thenReturn(user1);

    assertEquals(milestoneDTO1.getMilestoneId(), milestoneService.editMilestone(user1.getUsername(), milestoneDTO1).getMilestoneId());
    assertEquals(milestoneDTO1.getMilestoneTitle(), milestoneService.editMilestone(user1.getUsername(), milestoneDTO1).getMilestoneTitle());
  }

  @Test
  @DisplayName("Test editMilestone returns null when user is not the owner of the milestone")
  public void testEditMilestoneReturnsNullWhenUserIsNotOwner() {
    UserDAO user1 = new UserDAO();
    user1.setUsername("JohnSmith12");

    UserDAO user2 = new UserDAO();
    user2.setUsername("JohnSmith13");

    MilestoneDAO milestoneDAO1 = new MilestoneDAO();
    milestoneDAO1.setMilestoneId(1L);
    milestoneDAO1.setMilestoneTitle("Title");
    milestoneDAO1.setMilestoneDescription("Description");
    milestoneDAO1.setUserDAO(user1);


    MilestoneDTO milestoneDTO1 = new MilestoneDTO();
    milestoneDTO1.setMilestoneId(1L);
    milestoneDTO1.setMilestoneTitle("New title");
    milestoneDTO1.setMilestoneDescription("New description");
    milestoneDTO1.setUsername("JohnSmith13");

    when(milestoneRepository.findMilestoneDAOByMilestoneId(milestoneDAO1.getMilestoneId())).thenReturn(milestoneDAO1);
    when(userRepository.findByUsername(user1.getUsername())).thenReturn(user1);
    when(userRepository.findByUsername(user2.getUsername())).thenReturn(user2);

    assertNull(milestoneService.editMilestone(user2.getUsername(), milestoneDTO1));
  }

  @Test
  @DisplayName("Test editMilestone returns unchanged milestoneDTO")
public void testEditMilestoneReturnsUnchangedMilestoneDTO() {
    UserDAO user1 = new UserDAO();
    user1.setUsername("JohnSmith12");

    MilestoneDAO milestoneDAO1 = new MilestoneDAO();
    milestoneDAO1.setMilestoneId(1L);
    milestoneDAO1.setMilestoneTitle("Title");
    milestoneDAO1.setMilestoneDescription("Description");
    milestoneDAO1.setMilestoneImage(null);
    milestoneDAO1.setMilestoneCurrentSum(0L);
    milestoneDAO1.setMilestoneGoalSum(100L);
    milestoneDAO1.setStartDate(LocalDateTime.of(2021, 5, 1, 0, 0, 0));
    milestoneDAO1.setDeadlineDate(LocalDateTime.of(2021, 6, 1, 0, 0, 0));
    milestoneDAO1.setUserDAO(user1);

    MilestoneDTO milestoneDTO1 = new MilestoneDTO();
    milestoneDTO1.setMilestoneId(1L);
    milestoneDTO1.setMilestoneTitle(null);
    milestoneDTO1.setMilestoneDescription(null);
    milestoneDTO1.setMilestoneImage(null);
    milestoneDTO1.setMilestoneCurrentSum(null);
    milestoneDTO1.setMilestoneGoalSum(null);
    milestoneDTO1.setStartDate(null);
    milestoneDTO1.setDeadlineDate(null);
    milestoneDTO1.setUsername("JohnSmith12");

    when(milestoneRepository.findMilestoneDAOByMilestoneId(milestoneDAO1.getMilestoneId())).thenReturn(milestoneDAO1);
    when(userRepository.findByUsername(user1.getUsername())).thenReturn(user1);

    assertEquals(milestoneDAO1.getMilestoneTitle(), milestoneService.editMilestone(user1.getUsername(), milestoneDTO1).getMilestoneTitle());
    assertEquals(milestoneDAO1.getMilestoneDescription(), milestoneService.editMilestone(user1.getUsername(), milestoneDTO1).getMilestoneDescription());
    assertEquals(milestoneDAO1.getMilestoneCurrentSum(), milestoneService.editMilestone(user1.getUsername(), milestoneDTO1).getMilestoneCurrentSum());
    assertEquals(milestoneDAO1.getMilestoneGoalSum(), milestoneService.editMilestone(user1.getUsername(), milestoneDTO1).getMilestoneGoalSum());
    assertEquals(milestoneDAO1.getStartDate(), milestoneService.editMilestone(user1.getUsername(), milestoneDTO1).getStartDate());
    assertEquals(milestoneDAO1.getDeadlineDate(), milestoneService.editMilestone(user1.getUsername(), milestoneDTO1).getDeadlineDate());
  }

  @Test
  @DisplayName("Test editMilestone returns null when error occurs")
  public void testEditMilestoneReturnsNullWhenErrorOccurs() {
    UserDAO user1 = new UserDAO();
    user1.setUsername("JohnSmith12");

    UserDAO user2 = new UserDAO();
    user2.setUsername("JohnSmith13");

    MilestoneDAO milestoneDAO1 = new MilestoneDAO();
    milestoneDAO1.setMilestoneId(1L);
    milestoneDAO1.setMilestoneTitle("Title");
    milestoneDAO1.setMilestoneDescription("Description");
    milestoneDAO1.setUserDAO(user1);

    MilestoneDTO milestoneDTO1 = new MilestoneDTO();
    milestoneDTO1.setMilestoneId(1L);
    milestoneDTO1.setMilestoneTitle("New title");
    milestoneDTO1.setMilestoneDescription("New description");
    milestoneDTO1.setUsername("JohnSmith12");

    when(milestoneRepository.findMilestoneDAOByMilestoneId(milestoneDAO1.getMilestoneId())).thenReturn(milestoneDAO1);
    when(userRepository.findByUsername(user1.getUsername())).thenReturn(user1);

    assertNull(milestoneService.editMilestone(user2.getUsername(), milestoneDTO1));
  }

  @Test
  @DisplayName("Test increaseMilestoneCurrentSum returns updated milestoneDTO")
  public void testIncreaseMilestoneCurrentSumReturnsUpdatedMilestoneDTO() {
    UserDAO user1 = new UserDAO();
    user1.setUsername("JohnSmith12");

    MilestoneDAO milestoneDAO1 = new MilestoneDAO();
    milestoneDAO1.setMilestoneId(1L);
    milestoneDAO1.setMilestoneTitle("Title");
    milestoneDAO1.setMilestoneDescription("Description");
    milestoneDAO1.setMilestoneCurrentSum(0L);
    milestoneDAO1.setMilestoneGoalSum(100L);
    milestoneDAO1.setUserDAO(user1);

    MilestoneDTO milestoneDTO1 = new MilestoneDTO();
    milestoneDTO1.setMilestoneId(1L);
    milestoneDTO1.setMilestoneTitle("Title");
    milestoneDTO1.setMilestoneDescription("Description");
    milestoneDTO1.setMilestoneCurrentSum(50L);
    milestoneDTO1.setMilestoneGoalSum(100L);
    milestoneDTO1.setUsername("JohnSmith12");

    when(milestoneRepository.findMilestoneDAOByMilestoneId(milestoneDAO1.getMilestoneId())).thenReturn(milestoneDAO1);
    when(userRepository.findByUsername(user1.getUsername())).thenReturn(user1);
    when(milestoneRepository.save(milestoneDAO1)).thenReturn(milestoneDAO1);

    assertEquals(milestoneDTO1.getMilestoneCurrentSum(), milestoneService.increaseMilestonesCurrentSum(milestoneDAO1.getMilestoneId(), 50L).getMilestoneCurrentSum());
  }

  @Test
  @DisplayName("Test increaseMilestoneCurrentSum returns null when milestone is not found")
  public void testIncreaseMilestoneCurrentSumReturnsNullWhenMilestoneIsNotFound() {
    UserDAO user1 = new UserDAO();
    user1.setUsername("JohnSmith12");

    MilestoneDAO milestoneDAO1 = new MilestoneDAO();
    milestoneDAO1.setMilestoneId(1L);
    milestoneDAO1.setMilestoneTitle("Title");
    milestoneDAO1.setMilestoneDescription("Description");
    milestoneDAO1.setMilestoneCurrentSum(0L);
    milestoneDAO1.setMilestoneGoalSum(100L);
    milestoneDAO1.setUserDAO(user1);

    when(milestoneRepository.findMilestoneDAOByMilestoneId(milestoneDAO1.getMilestoneId())).thenReturn(null);

    assertNull(milestoneService.increaseMilestonesCurrentSum(milestoneDAO1.getMilestoneId(), 50L));
  }

  @Test
  @DisplayName("Test decreaseMilestoneCurrentSum returns updated milestoneDTO")
  public void testDecreaseMilestoneCurrentSumReturnsUpdatedMilestoneDTO() {
    UserDAO user1 = new UserDAO();
    user1.setUsername("JohnSmith12");

    MilestoneDAO milestoneDAO1 = new MilestoneDAO();
    milestoneDAO1.setMilestoneId(1L);
    milestoneDAO1.setMilestoneTitle("Title");
    milestoneDAO1.setMilestoneDescription("Description");
    milestoneDAO1.setMilestoneCurrentSum(100L);
    milestoneDAO1.setMilestoneGoalSum(100L);
    milestoneDAO1.setUserDAO(user1);

    MilestoneDTO milestoneDTO1 = new MilestoneDTO();
    milestoneDTO1.setMilestoneId(1L);
    milestoneDTO1.setMilestoneTitle("Title");
    milestoneDTO1.setMilestoneDescription("Description");
    milestoneDTO1.setMilestoneCurrentSum(50L);
    milestoneDTO1.setMilestoneGoalSum(100L);
    milestoneDTO1.setUsername("JohnSmith12");

    when(milestoneRepository.findMilestoneDAOByMilestoneId(milestoneDAO1.getMilestoneId())).thenReturn(milestoneDAO1);
    when(userRepository.findByUsername(user1.getUsername())).thenReturn(user1);
    when(milestoneRepository.save(milestoneDAO1)).thenReturn(milestoneDAO1);

    assertEquals(milestoneDTO1.getMilestoneCurrentSum(), milestoneService.decreaseMilestonesCurrentSum(milestoneDAO1.getMilestoneId(), 50L).getMilestoneCurrentSum());
  }
}
