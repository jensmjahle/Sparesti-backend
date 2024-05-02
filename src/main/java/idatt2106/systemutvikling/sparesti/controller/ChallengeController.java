package idatt2106.systemutvikling.sparesti.controller;

import idatt2106.systemutvikling.sparesti.dto.ChallengeDTO;
import idatt2106.systemutvikling.sparesti.mapper.ChallengeMapper;
import idatt2106.systemutvikling.sparesti.service.ChallengeService;
import idatt2106.systemutvikling.sparesti.service.CurrentUserService;
import idatt2106.systemutvikling.sparesti.service.MilestoneService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(
            summary = "Get all active challenges",
            description = "Get all active challenges for the current user"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Challenges found",
                    content = {
                            @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input for page number or page size",
                    content = @io.swagger.v3.oas.annotations.media.Content
            )}
    )
    @GetMapping("/paginated/active")
    @ResponseBody
    public ResponseEntity<Page<ChallengeDTO>> getActiveChallenges(@NonNull Pageable pageable) {

        // Parameter verification
        if (pageable.getPageNumber() < 0 || pageable.getPageSize() < 0)
            throw new IllegalArgumentException("Pageable is not valid");

        return ResponseEntity.ok().body(
                challengeService.getActiveChallenges(CurrentUserService.getCurrentUsername(), pageable));
    }

    @Operation(
            summary = "Get all inactive challenges",
            description = "Get all inactive challenges for the current user"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Challenges found",
                    content = {
                            @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input for page number or page size",
                    content = @io.swagger.v3.oas.annotations.media.Content
            )}
    )
    @Parameter(
            name = "pageable",
            description = "The pageable object containing page number and page size",
            content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")
            }
    )
    @GetMapping("/paginated/inactive")
    @ResponseBody
    public ResponseEntity<Page<ChallengeDTO>> getInactiveChallenges(@NonNull Pageable pageable) {

        // Parameter verification
        if (pageable.getPageNumber() < 0 || pageable.getPageSize() < 0)
            throw new IllegalArgumentException("Pageable is not valid");

        return ResponseEntity.ok().body(
                challengeService.getInactiveChallenges(CurrentUserService.getCurrentUsername(), pageable));
    }

    @Operation(
            summary = "Get challenge by id",
            description = "Get challenge by id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Challenge found",
                    content = {
                            @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input for challenge id",
                    content = @io.swagger.v3.oas.annotations.media.Content
            )}
    )
    @Parameter(
            name = "challengeId",
            description = "The id of the challenge",
            content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")
            }
    )
    @GetMapping("/{challengeId}")
    @ResponseBody
    public ResponseEntity<ChallengeDTO> getChallenge(@PathVariable @NonNull Long challengeId) {

        // Fetch challenge
        final ChallengeDTO challenge = challengeService.getChallenge(challengeId);

        // Verify ownership of the challenge
        if (!challenge.getUsername().equals(CurrentUserService.getCurrentUsername()))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        return ResponseEntity.ok().body(challengeService.getChallenge(challengeId));
    }

    @Operation(
            summary = "Create challenge",
            description = "Create a new challenge"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Challenge created",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ChallengeDTO.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input for challenge",
                    content = @Content
            )}
    )
    @Parameter(
            name = "challengeDTO",
            description = "The challenge to be created",
            content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ChallengeDTO.class))
            }
    )
    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<ChallengeDTO> createChallenge(@RequestBody @NonNull ChallengeDTO challengeDTO) {
      return ResponseEntity.status(HttpStatus.CREATED)
              .body(ChallengeMapper.toDTO(challengeService.createChallenge(challengeDTO)));
    }

    @Operation(
            summary = "Activate challenge",
            description = "Activate a challenge"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Challenge activated",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ChallengeDTO.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input for challenge id",
                    content = @io.swagger.v3.oas.annotations.media.Content
            )}
    )
    @Parameter(
            name = "challengeId",
            description = "The id of the challenge",
            content = {
                    @Content(mediaType = "application/json")
            }
    )
    @PostMapping("/activate/{challengeId}")
    @ResponseBody
    public ResponseEntity<ChallengeDTO> activateChallenge(@PathVariable @NonNull Long challengeId) {

        // Fetch challenge
        final ChallengeDTO challenge = challengeService.getChallenge(challengeId);

        // Verify ownership of the challenge
        if (!challenge.getUsername().equals(CurrentUserService.getCurrentUsername()))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        // Verify that the challenge is inactive
        if (challenge.isActive())
            return ResponseEntity.status(HttpStatus.CONFLICT).build();

        // Return 200 OK
        return ResponseEntity.ok().body(ChallengeMapper.toDTO(challengeService.activateChallenge(challengeId)));
    }

    @Operation(
            summary = "Complete challenge",
            description = "Complete a challenge and transfer money to milestone, and log the challenge"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Challenge completed",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input for challenge id or milestone id",
                    content = @io.swagger.v3.oas.annotations.media.Content
            )}
    )
    @Parameter(
            name = "challengeId",
            description = "The id of the challenge",
            content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Long.class)
                    )
            }
    )
    @PostMapping("/complete")
    @ResponseBody
    public ResponseEntity<String> completeChallenge(@RequestParam("challengeId") @NonNull Long challengeId,
                                                    @RequestParam("milestoneId") @NonNull Long milestoneId) {

      // Verify ownership of the requested challenge
      if (!challengeService.getChallenge(challengeId).getUsername()
          .equals(CurrentUserService.getCurrentUsername()))
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not the owner of this challenge");

      // Verify ownership of the requested milestone
      if (!milestoneService.getMilestoneDTOById(milestoneId).getUsername()
          .equals(CurrentUserService.getCurrentUsername()))
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not the owner of this milestone");

      // Perform requested operation
      challengeService.completeChallengeForCurrentUser(challengeId, milestoneId);

      // Return 200 OK
      return ResponseEntity.ok().body("Challenge completed");
    }

    @Operation(
            summary = "Delete challenge",
            description = "Delete a challenge"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Challenge deleted",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ChallengeDTO.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input for challenge id",
                    content = @Content
            )}
    )
    @Parameter(
            name = "challengeId",
            description = "The id of the challenge",
            content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ChallengeDTO.class)
                    )})
    @DeleteMapping("/delete/{challengeId}")
    @ResponseBody
    public ResponseEntity<String> moveChallengeToLog(@PathVariable @NonNull Long challengeId) {

      // Verify ownership of the challenge
      if (!challengeService.getChallenge(challengeId).getUsername().equals(CurrentUserService.getCurrentUsername()))
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not the owner of this challenge");

      // Perform the service layer function
      challengeService.moveChallengeToLog(challengeId);

      return ResponseEntity.ok().body("Challenge deleted");
    }
}
