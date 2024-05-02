package idatt2106.systemutvikling.sparesti.controller;

import idatt2106.systemutvikling.sparesti.dto.MilestoneDTO;
import idatt2106.systemutvikling.sparesti.service.CurrentUserService;
import idatt2106.systemutvikling.sparesti.service.JWTService;
import idatt2106.systemutvikling.sparesti.service.MilestoneLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

/**
 * Controller for handling milestone logs.
 */
@RestController
@RequestMapping(value = "/milestoneLog")
@EnableAutoConfiguration
@CrossOrigin(origins = "*")
public class MilestoneLogController {

  Logger logger = Logger.getLogger(MilestoneLogController.class.getName());
  MilestoneLogService milestoneLogService;
  JWTService jwtService;

  public MilestoneLogController(MilestoneLogService milestoneLogService, JWTService jwtService) {
    this.milestoneLogService = milestoneLogService;
    this.jwtService = jwtService;
  }

  @Operation(
          summary = "Get user milestones",
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
          description = "The pageable object",
          content = {
                  @Content(mediaType = "application/json",
                          schema = @Schema(implementation = Pageable.class)
                  )
          }
  )
  @GetMapping("/user")
  public ResponseEntity<Page<MilestoneDTO>> getUserMilestones(Pageable pageable) {
    String username = CurrentUserService.getCurrentUsername();
    logger.info("Received request to get user milestones.");
    return ResponseEntity.ok(milestoneLogService.getMilestoneLogsByUsernamePaginated(username, pageable));
  }

  @Operation(
          summary = "Get milestone by id",
          description = "Get a milestone by its id"
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
          name = "milestoneLogId",
          description = "The id of the milestone",
          content = {
                  @Content(mediaType = "application/json",
                          schema = @Schema(implementation = Long.class)
                  )
          }
  )
  @GetMapping("/id")
  public ResponseEntity<MilestoneDTO> getMilestoneLogById(@RequestBody Long milestoneLogId) {
    logger.info("Received request to get milestone by id.");
    return ResponseEntity.ok(milestoneLogService.getMilestoneLogById(milestoneLogId));
  }

}
