package idatt2106.systemutvikling.sparesti.dao;

import idatt2106.systemutvikling.sparesti.enums.ChallengeTheme;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "challenge_log")
public class ChallengeLogDAO {

  @Id
  private Long challengeId;
  @ManyToOne
  @JoinColumn(name = "username", referencedColumnName = "username")
  private UserDAO userDAO;
  private String challengeTitle;
  private String challengeDescription;
  private Long goalSum;
  private Long challengeAchievedSum;
  private LocalDateTime completionDate;
  private ChallengeTheme theme;
  private boolean accepted;

}
