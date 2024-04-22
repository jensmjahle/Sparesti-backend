package idatt2106.systemutvikling.sparesti.controller;

import idatt2106.systemutvikling.sparesti.dto.ChallengeDTO;
import idatt2106.systemutvikling.sparesti.mapper.ChallengeMapper;
import idatt2106.systemutvikling.sparesti.service.ChallengeService;
import idatt2106.systemutvikling.sparesti.service.CurrentUserService;
import lombok.AllArgsConstructor;
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
  public ResponseEntity<List<ChallengeDTO>> getActiveChallenges(@RequestParam("page") int page, @RequestParam("size") int size) {
    if (page < 0 || size < 0) {
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.ok().body(challengeService.getActiveChallenges(CurrentUserService.getCurrentUsername(), page, size));
  }

  @GetMapping("/paginated/inactive")
  @ResponseBody
  public ResponseEntity<List<ChallengeDTO>> getInactiveChallenges(@RequestParam("page") int page, @RequestParam("size") int size) {
    if (page < 0 || size < 0) {
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.ok().body(challengeService.getInactiveChallenges(CurrentUserService.getCurrentUsername(), page, size));
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
}
