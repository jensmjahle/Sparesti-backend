package idatt2106.systemutvikling.sparesti.dao;

import idatt2106.systemutvikling.sparesti.enums.ChallengeTheme;
import idatt2106.systemutvikling.sparesti.enums.RecurringInterval;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Access Object for Challenge
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "challenge")
public class ChallengeDAO {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long challengeId;
  @ManyToOne
  @JoinColumn(name = "username", referencedColumnName = "username")
  private UserDAO userDAO;
  private String challengeTitle;
  private String challengeDescription;
  private Long goalSum;
  private Long currentSum;
  private LocalDateTime startDate;
  private LocalDateTime expirationDate;
  @Enumerated(EnumType.STRING)
  private RecurringInterval recurringInterval;
  private boolean active;
  private ChallengeTheme theme;

}
