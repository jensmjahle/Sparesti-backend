package idatt2106.systemutvikling.sparesti.controller;

import idatt2106.systemutvikling.sparesti.dto.ChallengeDTO;
import idatt2106.systemutvikling.sparesti.mapper.ChallengeMapper;
import idatt2106.systemutvikling.sparesti.service.ChallengeService;
import idatt2106.systemutvikling.sparesti.service.CurrentUserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/challenge")
@AllArgsConstructor
public class ChallengeController {

  private final ChallengeService challengeService;

  @GetMapping("/paginated/active")
  @ResponseBody
  public ResponseEntity<List<ChallengeDTO>> getActiveChallenges(Pageable pageable) {
    if (pageable == null || pageable.getPageNumber() < 0 || pageable.getPageSize() < 0){
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.ok().body(challengeService.getActiveChallenges(CurrentUserService.getCurrentUsername(), pageable.getPageNumber(), pageable.getPageSize()));
  }

  @GetMapping("/paginated/inactive")
  @ResponseBody
  public ResponseEntity<List<ChallengeDTO>> getInactiveChallenges(Pageable pageable) {
    if (pageable == null || pageable.getPageNumber() < 0 || pageable.getPageSize() < 0) {
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.ok().body(challengeService.getInactiveChallenges(CurrentUserService.getCurrentUsername(), pageable.getPageNumber(), pageable.getPageSize()));
  }

  @GetMapping("/{challengeId}")
  @ResponseBody
  public ResponseEntity<ChallengeDTO> getChallenge(@PathVariable Long challengeId) {
    if (challengeId == null) {
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.ok().body(challengeService.getChallenge(challengeId));
  }

  @PostMapping("/create")
  @ResponseBody
  public ResponseEntity<ChallengeDTO> createChallenge(@RequestBody ChallengeDTO challengeDTO) {
    if (challengeDTO == null) {
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.status(HttpStatus.CREATED).body(ChallengeMapper.toDTO(challengeService.createChallenge(challengeDTO)));
  }

  @PostMapping("/activate/{challengeId}")
  @ResponseBody
  public ResponseEntity<ChallengeDTO> activateChallenge(@PathVariable Long challengeId) {
    if (challengeId == null) {
      return ResponseEntity.badRequest().build();
    }

    if (challengeService.getChallenge(challengeId).isActive()) {
      return ResponseEntity.badRequest().body(challengeService.getChallenge(challengeId));
    }

    if (!challengeService.getChallenge(challengeId).getUsername().equals(CurrentUserService.getCurrentUsername())) {
      return ResponseEntity.badRequest().body(challengeService.getChallenge(challengeId));
    }

    return ResponseEntity.ok().body(ChallengeMapper.toDTO(challengeService.activateChallenge(challengeId)));
  }

  @PostMapping("/complete/{challengeId}")
  @ResponseBody
  public ResponseEntity<String> completeChallenge(@PathVariable Long challengeId) {
    if (challengeId == null) {
      return ResponseEntity.badRequest().build();
    }

    if (!challengeService.getChallenge(challengeId).getUsername().equals(CurrentUserService.getCurrentUsername())) {
      return ResponseEntity.badRequest().body("You are not the owner of this challenge");
    }

    challengeService.completeChallenge(challengeId);

    return ResponseEntity.ok().body("Challenge completed");
  }

  @DeleteMapping("/delete/{challengeId}")
  @ResponseBody
  public ResponseEntity<String> deleteChallenge(@PathVariable Long challengeId) {
    if (challengeId == null) {
      return ResponseEntity.badRequest().build();
    }

    if (!challengeService.getChallenge(challengeId).getUsername().equals(CurrentUserService.getCurrentUsername())) {
      return ResponseEntity.badRequest().body("You are not the owner of this challenge");
    }

    challengeService.deleteChallenge(challengeId);

    return ResponseEntity.ok().body("Challenge deleted");
  }
}
