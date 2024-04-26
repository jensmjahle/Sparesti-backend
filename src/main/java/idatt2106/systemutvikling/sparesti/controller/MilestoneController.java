package idatt2106.systemutvikling.sparesti.controller;

import idatt2106.systemutvikling.sparesti.dao.ManualSavingDAO;
import idatt2106.systemutvikling.sparesti.dao.MilestoneDAO;
import idatt2106.systemutvikling.sparesti.dto.ManualSavingDTO;
import idatt2106.systemutvikling.sparesti.dto.MilestoneDTO;
import idatt2106.systemutvikling.sparesti.service.CurrentUserService;
import idatt2106.systemutvikling.sparesti.service.ManualSavingService;
import idatt2106.systemutvikling.sparesti.service.MilestoneService;
import idatt2106.systemutvikling.sparesti.service.TransactionService;
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

  private final Logger logger = Logger.getLogger(TokenController.class.getName());

  private final MilestoneService milestoneService;

  private final ManualSavingService manualSavingService;

  private final TransactionService transactionService;



  @GetMapping("/user")
  public ResponseEntity<Page<MilestoneDTO>> getUserMilestones(@RequestHeader("Authorization") String token, Pageable pageable) {
    logger.info("Received request to get user milestones.");
  return ResponseEntity.ok(milestoneService.getActiveMilestonesDTOsByUsername(token, pageable));
  }

  @PostMapping("/create")
  public void createMilestone(@RequestHeader("Authorization") String token, @RequestBody MilestoneDTO milestoneDTO) {
    logger.info("Received request to create milestone.");
    milestoneService.createMilestoneDTO(token, milestoneDTO);
  }

  @GetMapping("/id")
  public ResponseEntity<MilestoneDTO> getMilestoneById(@RequestHeader("Authorization") String token, @RequestBody Long milestoneId) {
    logger.info("Received request to get milestone by id.");
    return ResponseEntity.ok(milestoneService.getMilestoneDTOById(token, milestoneId));
  }

  @PostMapping("/complete")
  public void completeMilestone(@RequestHeader("Authorization") String token, @RequestBody Long milestoneId) {
    logger.info("Received request to complete milestone.");
    milestoneService.completeMilestone(token, milestoneId);
  }

  @PostMapping ("/update")
  public ResponseEntity<MilestoneDTO> updateMilestone(@RequestHeader("Authorization") String token, @RequestBody MilestoneDTO milestoneDTO) {
    logger.info("Received request to update milestone.");
    milestoneService.updateMilestoneDTO(token, milestoneDTO);
    return ResponseEntity.ok(milestoneDTO);
  }

  @PostMapping ("/inject")
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
