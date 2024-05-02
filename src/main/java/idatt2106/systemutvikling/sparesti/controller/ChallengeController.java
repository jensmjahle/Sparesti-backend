package idatt2106.systemutvikling.sparesti.controller;

import idatt2106.systemutvikling.sparesti.dao.MilestoneDAO;
import idatt2106.systemutvikling.sparesti.dto.ChallengeDTO;
import idatt2106.systemutvikling.sparesti.mapper.ChallengeMapper;
import idatt2106.systemutvikling.sparesti.service.ChallengeService;
import idatt2106.systemutvikling.sparesti.service.CurrentUserService;
import idatt2106.systemutvikling.sparesti.service.MilestoneService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

/**
 * Controller for handling challenges.
 */
@RestController
@RequestMapping("/user/challenge")
@AllArgsConstructor
public class ChallengeController {

  private final ChallengeService challengeService;
  private final MilestoneService milestoneService;
  private final Logger logger = Logger.getLogger(ChallengeController.class.getName());

  /**
   * Method for getting active challenges.
   *
   * @param pageable The page to get
   * @return the active challenges
   */
  @GetMapping("/paginated/active")
  @ResponseBody
  public ResponseEntity<Page<ChallengeDTO>> getActiveChallenges(Pageable pageable) {
    if (pageable == null || pageable.getPageNumber() < 0 || pageable.getPageSize() < 0) {
      throw new IllegalArgumentException("Pageable is not valid");
    }
    return ResponseEntity.ok().body(
        challengeService.getActiveChallenges(CurrentUserService.getCurrentUsername(), pageable));
  }

  /**
   * Method for getting inactive challenges.
   *
   * @param pageable The page to get
   * @return the inactive challenges
   */
  @GetMapping("/paginated/inactive")
  @ResponseBody
  public ResponseEntity<Page<ChallengeDTO>> getInactiveChallenges(Pageable pageable) {
    if (pageable == null || pageable.getPageNumber() < 0 || pageable.getPageSize() < 0) {
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.ok().body(
        challengeService.getInactiveChallenges(CurrentUserService.getCurrentUsername(), pageable));
  }

  /**
   * Method for getting a challenge.
   *
   * @param challengeId The id of the challenge to get
   * @return the challenge
   */
  @GetMapping("/{challengeId}")
  @ResponseBody
  public ResponseEntity<ChallengeDTO> getChallenge(@PathVariable Long challengeId) {
    if (challengeId == null) {
      return ResponseEntity.badRequest().build();
    }

    if (!challengeService.getChallenge(challengeId).getUsername()
        .equals(CurrentUserService.getCurrentUsername())) {
      return ResponseEntity.badRequest().body(challengeService.getChallenge(challengeId));
    }

    return ResponseEntity.ok().body(challengeService.getChallenge(challengeId));
  }

  /**
   * Method for creating a challenge.
   *
   * @param challengeDTO the challenge to create
   */
  @PostMapping("/create")
  @ResponseBody
  public ResponseEntity<ChallengeDTO> createChallenge(@RequestBody ChallengeDTO challengeDTO) {
    if (challengeDTO == null) {
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ChallengeMapper.toDTO(challengeService.createChallenge(challengeDTO)));
  }

  /**
   * Method for activating a challenge.
   *
   * @param challengeId The id of the challenge to activate
   * @return the activated challenge
   */
  @PostMapping("/activate/{challengeId}")
  @ResponseBody
  public ResponseEntity<ChallengeDTO> activateChallenge(@PathVariable @NonNull Long challengeId) {

    // Verify that the challenge is inactive
    if (challengeService.getChallenge(challengeId).isActive())
      return ResponseEntity.badRequest().body(challengeService.getChallenge(challengeId));

    //
    if (!challengeService.getChallenge(challengeId).getUsername()
        .equals(CurrentUserService.getCurrentUsername()))
      return ResponseEntity.badRequest().body(challengeService.getChallenge(challengeId));

    return ResponseEntity.ok()
        .body(ChallengeMapper.toDTO(challengeService.activateChallenge(challengeId)));
  }

  /**
   * Method for completing a challenge.
   *
   * @param challengeId The id of the challenge to complete
   * @param milestoneId The id of the milestone to complete
   * @return the completed challenge
   */
  @PostMapping("/complete")
  @ResponseBody
  public ResponseEntity<String> completeChallenge(@RequestParam("challengeId") @NonNull Long challengeId,
                                                  @RequestParam("milestoneId") @NonNull Long milestoneId) {

    // Verify ownership of the requested challenge
    if (!challengeService.getChallenge(challengeId).getUsername()
        .equals(CurrentUserService.getCurrentUsername()))
      return ResponseEntity.badRequest().body("You are not the owner of this challenge");

    // Verify ownership of the requested milestone
    if (!milestoneService.getMilestoneDTOById(milestoneId).getUsername()
        .equals(CurrentUserService.getCurrentUsername()))
      return ResponseEntity.badRequest().body("You are not the owner of this milestone");

    // Perform requested operation
    challengeService.completeChallengeForCurrentUser(challengeId, milestoneId);

    // Return 200 OK
    return ResponseEntity.ok().body("Challenge completed");
  }

  /**
   * Method for deleting a challenge.
   *
   * @param challengeId The id of the challenge to delete
   * @return the deleted challenge
   */
  @DeleteMapping("/delete/{challengeId}")
  @ResponseBody
  public ResponseEntity<String> moveChallengeToLog(@PathVariable @NonNull Long challengeId) {
    // Verify ownership of the challenge
    if (!challengeService.getChallenge(challengeId).getUsername().equals(CurrentUserService.getCurrentUsername()))
      return ResponseEntity.badRequest().body("You are not the owner of this challenge");

    // Perform the service layer function
    challengeService.moveChallengeToLog(challengeId);

    return ResponseEntity.ok().body("Challenge deleted");
  }
}
