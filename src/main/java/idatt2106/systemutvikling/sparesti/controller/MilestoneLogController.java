package idatt2106.systemutvikling.sparesti.controller;

import idatt2106.systemutvikling.sparesti.dao.MilestoneDAO;
import idatt2106.systemutvikling.sparesti.dto.MilestoneDTO;
import idatt2106.systemutvikling.sparesti.service.MilestoneLogService;
import idatt2106.systemutvikling.sparesti.service.MilestoneService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
  public ResponseEntity<Page<MilestoneDTO>> getUserMilestones
          (@RequestHeader("Authorization") String token, Pageable pageable) {
    logger.info("Received request to get user milestones.");
    return ResponseEntity.ok(milestoneLogService.getMilestoneLogsByUsername(token, pageable));
  }

  @PostMapping("/id")
  public ResponseEntity<MilestoneDTO> getMilestoneById(@RequestHeader("Authorization") String token, @RequestBody Long milestoneId) {
    logger.info("Received request to get milestone by id.");
    return ResponseEntity.ok(milestoneLogService.getMilestoneLogById(token, milestoneId));
  }

}
