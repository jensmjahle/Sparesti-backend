package idatt2106.systemutvikling.sparesti.controller;

import idatt2106.systemutvikling.sparesti.dao.MilestoneDAO;
import idatt2106.systemutvikling.sparesti.dto.MilestoneDTO;
import idatt2106.systemutvikling.sparesti.service.MilestoneLogService;
import idatt2106.systemutvikling.sparesti.service.MilestoneService;
import idatt2106.systemutvikling.sparesti.utils.ResponseEntityExceptionHandler;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/milestoneLog")
@EnableAutoConfiguration
@CrossOrigin(origins = "*")
public class MilestoneLogController {

  Logger logger = Logger.getLogger(TokenController.class.getName());

  MilestoneLogService milestoneLogService;

  public MilestoneLogController(MilestoneLogService milestoneLogService) {
    this.milestoneLogService = milestoneLogService;
  }

  @GetMapping("/user")
  public ResponseEntity<List<MilestoneDTO>> getUserMilestones(
      @RequestHeader("Authorization") String token) {
    logger.info("Received request to get user milestones.");
    try {
      return ResponseEntity.ok(milestoneLogService.getMilestoneLogsByUsername(token));
    } catch (Exception e) {
      logger.severe("Failed to get user milestones.");
      return ResponseEntityExceptionHandler.handleException(e);
    }
  }

  @PostMapping("/id")
  public ResponseEntity<MilestoneDTO> getMilestoneById(@RequestHeader("Authorization") String token,
      @RequestBody Long milestoneId) {
    logger.info("Received request to get milestone by id.");
    try {
      return ResponseEntity.ok(milestoneLogService.getMilestoneLogById(token, milestoneId));
    } catch (Exception e) {
      logger.severe("Failed to get milestone by id.");
      return ResponseEntityExceptionHandler.handleException(e);
    }
  }

}
