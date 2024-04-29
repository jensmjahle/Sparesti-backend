package idatt2106.systemutvikling.sparesti.service;

import idatt2106.systemutvikling.sparesti.dao.MilestoneLogDAO;
import idatt2106.systemutvikling.sparesti.dao.UserDAO;
import idatt2106.systemutvikling.sparesti.dto.MilestoneDTO;
import idatt2106.systemutvikling.sparesti.repository.MilestoneLogRepository;
import idatt2106.systemutvikling.sparesti.repository.MilestoneRepository;
import idatt2106.systemutvikling.sparesti.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MilestoneLogServiceTest {

  @Mock
  private MilestoneRepository milestoneRepository;

  @Mock
  private UserRepository userRepository;

  @Mock
  private MilestoneLogRepository milestoneLogRepository;

  @Mock
  private JWTService jwtService;

  @InjectMocks
  private MilestoneLogService milestoneLogService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testCompleteMilestone() {
    MilestoneLogDAO milestoneLogDAO = new MilestoneLogDAO();
    milestoneLogService.completeMilestone(milestoneLogDAO);
    verify(milestoneLogRepository, times(1)).save(milestoneLogDAO);
  }

  @Test
  public void testGetMilestoneLogsByUsernamePaginated() {
    String username = "testUser";
    Pageable pageable = PageRequest.of(0, 5);
    MilestoneLogDAO milestoneLogDAO = new MilestoneLogDAO();
    milestoneLogDAO.setUserDAO(new UserDAO()); // Ensure UserDAO is not null
    when(milestoneLogRepository.findMilestoneLogDAOByUserDAO_Username(username, pageable))
            .thenReturn(new PageImpl<>(Collections.singletonList(milestoneLogDAO), pageable, 1));

    Page<MilestoneDTO> result = milestoneLogService.getMilestoneLogsByUsernamePaginated(username, pageable);

    assertNotNull(result);
    assertEquals(1, result.getTotalElements());
  }

  @Test
  public void testGetMilestoneLogsByUsername() {
    String username = "testUser";
    MilestoneLogDAO milestoneLogDAO = new MilestoneLogDAO();
    milestoneLogDAO.setUserDAO(new UserDAO()); // Ensure UserDAO is not null
    when(milestoneLogRepository.findMilestoneLogDAOByUserDAO_Username(username))
            .thenReturn(Collections.singletonList(milestoneLogDAO));

    List<MilestoneDTO> result = milestoneLogService.getMilestoneLogsByUsername(username);

    assertNotNull(result);
    assertEquals(1, result.size());
  }

  @Test
  public void testGetMilestoneLogById() {
    long id = 1L;
    MilestoneLogDAO milestoneLogDAO = new MilestoneLogDAO();
    milestoneLogDAO.setUserDAO(new UserDAO()); // Ensure UserDAO is not null
    when(milestoneLogRepository.findMilestoneLogDAOByMilestoneId(id))
            .thenReturn(milestoneLogDAO);

    MilestoneDTO result = milestoneLogService.getMilestoneLogById(id);

    assertNotNull(result);
  }
}