package idatt2106.systemutvikling.sparesti.controller;

import idatt2106.systemutvikling.sparesti.dao.ManualSavingDAO;
import idatt2106.systemutvikling.sparesti.dao.MilestoneDAO;
import idatt2106.systemutvikling.sparesti.dto.ManualSavingDTO;
import idatt2106.systemutvikling.sparesti.dto.MilestoneDTO;
import idatt2106.systemutvikling.sparesti.service.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/milestone")
@EnableAutoConfiguration
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class MilestoneController {

  private final Logger logger = Logger.getLogger(MilestoneController.class.getName());

  private final MilestoneService milestoneService;

  private final ManualSavingService manualSavingService;

  private final TransactionService transactionService;

  private final JWTService jwtService;


  @GetMapping("/user/paginated")
  public ResponseEntity<Page<MilestoneDTO>> getUserMilestonesPaginated(Pageable pageable) {
    logger.info("Received request to get paginated list of user milestones.");
    return ResponseEntity.ok(milestoneService.getActiveMilestonesDTOsByUsernamePaginated(CurrentUserService.getCurrentUsername(), pageable));
  }

  @GetMapping("/user")
  public ResponseEntity<List<MilestoneDTO>> getUserMilestones() {
    logger.info("Received request to get list of user milestones.");
    return ResponseEntity.ok().body(milestoneService.getActiveMilestonesDTOsByUsername(CurrentUserService.getCurrentUsername()));
  }

  @PostMapping("/create")
  public void createMilestone(@RequestBody MilestoneDTO milestoneDTO) {
    logger.info("Received request to create milestone.");
    milestoneService.createMilestoneDTO(CurrentUserService.getCurrentUsername(), milestoneDTO);
  }

  @GetMapping("/{id}")
  public ResponseEntity<MilestoneDTO> getMilestoneById(@PathVariable Long id) {
    logger.info("Received request to get milestone by id.");
    return ResponseEntity.ok(milestoneService.getMilestoneDTOById(id));
  }

  @PostMapping("/complete")
  public void completeMilestone(@RequestBody Long milestoneId) {
    logger.info("Received request to complete milestone.");
    milestoneService.completeMilestone(CurrentUserService.getCurrentUsername(), milestoneId);
  }

  @PostMapping("/update")
  public ResponseEntity<MilestoneDTO> updateMilestone(@RequestBody MilestoneDTO milestoneDTO) {
    logger.info("Received request to update milestone.");
    return ResponseEntity.ok(milestoneService.updateMilestoneDTO(CurrentUserService.getCurrentUsername(), milestoneDTO));
  }

  @PostMapping("/inject")
  public ResponseEntity<?> manualInjectionIntoMilestone(@RequestBody ManualSavingDTO dto) {

    // Create new record
    ManualSavingDAO manualSavingDAO = manualSavingService.registerNewManualSavingDAO(dto.getMilestoneId(), dto.getAmount());

    // If unsuccessful, return UNPROCESSABLE_ENTITY
    if (manualSavingDAO == null)
      return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Failed to register record for  manual saving");

    // Update milestone
    MilestoneDAO milestoneDAO = milestoneService.increaseMilestonesCurrentSum(dto.getMilestoneId(), dto.getAmount());

    // If unsuccessful, cleanup and return UNPROCESSABLE_ENTITY
    if (milestoneDAO == null) {
      manualSavingService.removeManualSavingEntry(manualSavingDAO); // Cleanup
      return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Failed to update milestone");
    }

    // Perform transaction
    boolean transactionSuccessful = transactionService.createSavingsTransferForCurrentUser(dto.getAmount());

    // If unsuccessful, cleanup and return UNPROCESSABLE_ENTITY
    if (!transactionSuccessful) {
      milestoneService.decreaseMilestonesCurrentSum(dto.getMilestoneId(), dto.getAmount()); // Cleanup
      manualSavingService.removeManualSavingEntry(manualSavingDAO); // Cleanup
      return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Failed to perform transaction");
    }

    logger.info("User performed manual saving towards milestone.");

    return ResponseEntity.ok().build();
  }
}
