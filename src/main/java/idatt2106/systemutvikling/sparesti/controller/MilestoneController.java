package idatt2106.systemutvikling.sparesti.controller;

import idatt2106.systemutvikling.sparesti.dao.MilestoneDAO;
import idatt2106.systemutvikling.sparesti.dto.MilestoneDTO;
import idatt2106.systemutvikling.sparesti.service.MilestoneService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/milestone")
@EnableAutoConfiguration
@CrossOrigin(origins = "*")
public class MilestoneController {

  Logger logger = Logger.getLogger(TokenController.class.getName());

  MilestoneService milestoneService;

  public MilestoneController(MilestoneService milestoneService) {
    this.milestoneService = milestoneService;
  }

  @GetMapping("/user")
  public ResponseEntity<List<MilestoneDTO>> getUserMilestones(@RequestHeader("Authorization") String token) {
    logger.info("Received request to get user milestones.");
  return ResponseEntity.ok(milestoneService.getActiveMilestonesDTOsByUsername(token));
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

}
