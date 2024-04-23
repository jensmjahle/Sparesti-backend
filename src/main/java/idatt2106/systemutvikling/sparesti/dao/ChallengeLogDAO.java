package idatt2106.systemutvikling.sparesti.dao;

import idatt2106.systemutvikling.sparesti.enums.ChallengeTheme;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ChallengeLogDAO {

  @Id
  private Long challengeId;
  @ManyToOne(cascade = CascadeType.ALL)
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
