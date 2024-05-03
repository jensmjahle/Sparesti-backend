package idatt2106.systemutvikling.sparesti.dao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Access Object for Milestone
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "milestone")
public class MilestoneDAO {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long milestoneId;

  @ManyToOne
  @JoinColumn(name = "username", referencedColumnName = "username")
  private UserDAO userDAO;
  private String milestoneTitle;
  private String milestoneDescription;
  private Long milestoneGoalSum;
  private Long milestoneCurrentSum;

  @Lob
  @Column(length = 100000)
  private byte[] milestoneImage;
  private LocalDateTime deadlineDate;
  private LocalDateTime startDate;
}
