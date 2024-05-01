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

  /**
   * Method for getting the milestones for the current user.
   *
   * @param pageable The page to get
   * @return the milestones for the current user
   */
  @GetMapping("/user/paginated")
  public ResponseEntity<Page<MilestoneDTO>> getUserMilestonesPaginated(Pageable pageable) {
    logger.info("Received request to get paginated list of user milestones.");
    return ResponseEntity.ok(milestoneService.getActiveMilestonesDTOsByUsernamePaginated(CurrentUserService.getCurrentUsername(), pageable));
  }

  /**
   * Method for getting the milestones for the current user.
   *
   * @return the milestones for the current user
   */
  @GetMapping("/user")
  public ResponseEntity<List<MilestoneDTO>> getUserMilestones() {
    logger.info("Received request to get list of user milestones.");
    return ResponseEntity.ok().body(milestoneService.getActiveMilestonesDTOsByUsername(CurrentUserService.getCurrentUsername()));
  }

  /**
   * Method for creating a milestone.
   *
   * @param milestoneDTO the milestone to create
   */
  @PostMapping("/create")
  public void createMilestone(@RequestBody MilestoneDTO milestoneDTO) {
    logger.info("Received request to create milestone.");
    milestoneService.createMilestoneDTO(CurrentUserService.getCurrentUsername(), milestoneDTO);
  }

  /**
   * Method for getting a milestone by id.
   *
   * @param id the id of the milestone to get
   * @return the milestone
   */
  @GetMapping("/{id}")
  public ResponseEntity<MilestoneDTO> getMilestoneById(@PathVariable Long id) {
    logger.info("Received request to get milestone by id.");
    return ResponseEntity.ok(milestoneService.getMilestoneDTOById(id));
  }

  /**
   * Method for completing a milestone.
   *
   * @param milestoneId the id of the milestone to complete
   */
  @PostMapping("/complete")
  public void completeMilestone(@RequestBody Long milestoneId) {
    logger.info("Received request to complete milestone.");
    milestoneService.completeMilestone(CurrentUserService.getCurrentUsername(), milestoneId);
  }

  /**
   * Method to update a milestone.
   * @param milestoneDTO the milestone to update
   * @return the updated milestone
   */
  @PostMapping("/update")
  public ResponseEntity<MilestoneDTO> updateMilestone(@RequestBody MilestoneDTO milestoneDTO) {
    logger.info("Received request to update milestone.");
    return ResponseEntity.ok(milestoneService.updateMilestoneDTO(CurrentUserService.getCurrentUsername(), milestoneDTO));
  }

  /**
   * Method to edit a milestone.
   *
   * @param milestoneDTO the milestone to edit
   * @return the edited milestone
   */
  @PutMapping("/edit")
  public ResponseEntity<MilestoneDTO> editMilestone(@RequestBody MilestoneDTO milestoneDTO){
    logger.info("Received request to edit milestone");
    return ResponseEntity.ok(milestoneService.editMilestone(CurrentUserService.getCurrentUsername(), milestoneDTO));
  }

  /**
  * Method to delete a milestone by the id of the milestone.
  *
  * @param id the id of the milstone
  */
  @DeleteMapping("/delete/{id}")
  public void deleteMilestone(@PathVariable Long id) {
    logger.info("Received request to delete milestone.");
    milestoneService.deleteMilestone(CurrentUserService.getCurrentUsername(), id);
  }

  /**
   * Method to manually inject money into a milestone. This will create a new manual saving record, update the milestone and perform a transaction.
   * If any of these steps fail, the changes will be rolled back. The user will be notified of the failure.
   * If successful, the user will be notified of the success.
   *
   * @param dto the manual saving dto
   * @return response entity
   */
  @PostMapping("/inject")
  public ResponseEntity<?> manualInjectionIntoMilestone(@RequestBody ManualSavingDTO dto) {

    // Create new record
    ManualSavingDAO manualSavingDAO = manualSavingService.registerNewManualSavingDAO(dto.getMilestoneId(), dto.getAmount(), CurrentUserService.getCurrentUsername());

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
