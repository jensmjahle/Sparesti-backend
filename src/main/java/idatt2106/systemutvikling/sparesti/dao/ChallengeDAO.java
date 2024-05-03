package idatt2106.systemutvikling.sparesti.dao;

import idatt2106.systemutvikling.sparesti.enums.ChallengeTheme;
import idatt2106.systemutvikling.sparesti.enums.RecurringInterval;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Access Object for Challenge
 */
@Getter
@Setter
@AllArgsConstructor
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
