package idatt2106.systemutvikling.sparesti.controller;

import idatt2106.systemutvikling.sparesti.dto.MilestoneDTO;
import idatt2106.systemutvikling.sparesti.service.CurrentUserService;
import idatt2106.systemutvikling.sparesti.service.JWTService;
import idatt2106.systemutvikling.sparesti.service.MilestoneLogService;
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

  /**
   * Method for getting the milestones for the current user.
   *
   * @param pageable The page to get
   * @return the milestones for the current user
   */
  @GetMapping("/user")
  public ResponseEntity<Page<MilestoneDTO>> getUserMilestones(Pageable pageable) {
    String username = CurrentUserService.getCurrentUsername();
    logger.info("Received request to get user milestones.");
    return ResponseEntity.ok(milestoneLogService.getMilestoneLogsByUsernamePaginated(username, pageable));
  }

  /**
   * Method for getting the milestones for the current user.
   *
   * @param milestoneLogId The id of the milestone to get
   * @return the milestone for the current user
   */
  @GetMapping("/id")
  public ResponseEntity<MilestoneDTO> getMilestoneLogById(@RequestBody Long milestoneLogId) {
    logger.info("Received request to get milestone by id.");
    return ResponseEntity.ok(milestoneLogService.getMilestoneLogById(milestoneLogId));
  }

}
