package idatt2106.systemutvikling.sparesti.controller;

import idatt2106.systemutvikling.sparesti.dao.ManualSavingDAO;
import idatt2106.systemutvikling.sparesti.dao.MilestoneDAO;
import idatt2106.systemutvikling.sparesti.dto.ManualSavingDTO;
import idatt2106.systemutvikling.sparesti.dto.MilestoneDTO;
import idatt2106.systemutvikling.sparesti.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

/**
 * Controller for handling milestones.
 */
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

  @Operation(
      summary = "Get all milestones",
      description = "Get all milestones for the current user"
  )
  @ApiResponse(
      responseCode = "200",
      description = "Milestones found",
      content = {
          @Content(mediaType = "application/json",
                  schema = @Schema(implementation = MilestoneDTO.class))
      }
  )
  @Parameter(
          name = "pageable",
          description = "Pageable object for pagination",
          required = true,
          content = {
              @Content(mediaType = "application/json",
                      schema = @Schema(implementation = Pageable.class))
          }
  )
  @GetMapping("/user/paginated")
  public ResponseEntity<Page<MilestoneDTO>> getUserMilestonesPaginated(Pageable pageable) {
    logger.info("Received request to get paginated list of user milestones.");
    return ResponseEntity.ok(milestoneService.getActiveMilestonesDTOsByUsernamePaginated(CurrentUserService.getCurrentUsername(), pageable));
  }

  @Operation(
      summary = "Get all milestones",
      description = "Get all milestones for the current user"
  )
  @ApiResponse(
      responseCode = "200",
      description = "Milestones found",
      content = {
          @Content(mediaType = "application/json",
                  schema = @Schema(implementation = MilestoneDTO.class))
      }
  )
  @GetMapping("/user")
  public ResponseEntity<List<MilestoneDTO>> getUserMilestones() {
    logger.info("Received request to get list of user milestones.");
    return ResponseEntity.ok().body(milestoneService.getActiveMilestonesDTOsByUsername(CurrentUserService.getCurrentUsername()));
  }

  @Operation(
      summary = "Get all milestones",
      description = "Get all milestones for the current user"
  )
  @ApiResponse(
      responseCode = "200",
      description = "Milestones found",
      content = {
          @Content(mediaType = "application/json",
                  schema = @Schema(implementation = MilestoneDTO.class))
      }
  )
  @Parameter(
      name = "pageable",
      description = "Pageable object for pagination",
      required = true,
      content = {
          @Content(mediaType = "application/json",
                  schema = @Schema(implementation = Pageable.class))
      }
  )
  @PostMapping("/create")
  public void createMilestone(@RequestBody MilestoneDTO milestoneDTO) {
    logger.info("Received request to create milestone.");
    milestoneService.createMilestoneDTO(CurrentUserService.getCurrentUsername(), milestoneDTO);
  }

  @Operation(
      summary = "Get milestone by id",
      description = "Get milestone by id"
  )
  @ApiResponse(
      responseCode = "200",
      description = "Milestone found",
      content = {
          @Content(mediaType = "application/json",
                  schema = @Schema(implementation = MilestoneDTO.class))
      }
  )
  @Parameter(
      name = "id",
      description = "Id of the milestone to get",
      required = true,
      content = {
          @Content(mediaType = "application/json",
                  schema = @Schema(implementation = Long.class))
      }
  )
  @GetMapping("/{id}")
  public ResponseEntity<MilestoneDTO> getMilestoneById(@PathVariable Long id) {
    logger.info("Received request to get milestone by id.");
    return ResponseEntity.ok(milestoneService.getMilestoneDTOById(id));
  }

  @Operation(
      summary = "Complete milestone",
      description = "Complete milestone"
  )
  @ApiResponse(
      responseCode = "200",
      description = "Milestone completed",
      content = {
          @Content(mediaType = "application/json",
                  schema = @Schema(implementation = MilestoneDTO.class))
      }
  )
  @Parameter(
      name = "milestoneId",
      description = "Id of the milestone to complete",
      required = true,
      content = {
          @Content(mediaType = "application/json",
                  schema = @Schema(implementation = Long.class))
      }
  )
  @PostMapping("/complete")
  public void completeMilestone(@RequestBody Long milestoneId) {
    logger.info("Received request to complete milestone.");
    milestoneService.completeMilestone(CurrentUserService.getCurrentUsername(), milestoneId);
  }

  @Operation(
      summary = "Update milestone",
      description = "Update milestone"
  )
  @ApiResponse(
      responseCode = "200",
      description = "Milestone updated",
      content = {
          @Content(mediaType = "application/json",
                  schema = @Schema(implementation = MilestoneDTO.class))
      }
  )
  @Parameter(
      name = "milestoneDTO",
      description = "Milestone to update",
      required = true,
      content = {
          @Content(mediaType = "application/json",
                  schema = @Schema(implementation = MilestoneDTO.class))
      }
  )
  @PostMapping("/update")
  public ResponseEntity<MilestoneDTO> updateMilestone(@RequestBody MilestoneDTO milestoneDTO) {
    logger.info("Received request to update milestone.");
    return ResponseEntity.ok(milestoneService.updateMilestoneDTO(CurrentUserService.getCurrentUsername(), milestoneDTO));
  }

  @Operation(
      summary = "Edit milestone",
      description = "Edit milestone"
  )
  @ApiResponse(
      responseCode = "200",
      description = "Milestone edited",
      content = {
          @Content(mediaType = "application/json",
                  schema = @Schema(implementation = MilestoneDTO.class))
      }
  )
  @Parameter(
      name = "milestoneDTO",
      description = "Milestone to edit",
      required = true,
      content = {
          @Content(mediaType = "application/json",
                  schema = @Schema(implementation = MilestoneDTO.class))
      }
  )
  @PutMapping("/edit")
  public ResponseEntity<MilestoneDTO> editMilestone(@RequestBody MilestoneDTO milestoneDTO){
    logger.info("Received request to edit milestone");
    return ResponseEntity.ok(milestoneService.editMilestone(CurrentUserService.getCurrentUsername(), milestoneDTO));
  }

  @Operation(
      summary = "Delete milestone",
      description = "Delete milestone with given id"
  )
  @ApiResponse(
      responseCode = "200",
      description = "Milestone deleted"
  )
  @Parameter(
      name = "id",
      description = "Id of the milestone to delete",
      required = true,
      content = {
          @Content(mediaType = "application/json",
                  schema = @Schema(implementation = Long.class))
      }
  )
  @DeleteMapping("/delete/{id}")
  public void deleteMilestone(@PathVariable Long id) {
    logger.info("Received request to delete milestone.");
    milestoneService.deleteMilestone(CurrentUserService.getCurrentUsername(), id);
  }


  @Operation(
      summary = "Inject manual saving into milestone",
      description = "Inject manual saving into milestone"
  )
  @ApiResponse(
      responseCode = "200",
      description = "Manual saving injected"
  )
  @Parameter(
      name = "dto",
      description = "Manual saving dto",
      required = true,
      content = {
          @Content(mediaType = "application/json",
                  schema = @Schema(implementation = ManualSavingDTO.class))
      }
  )
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
