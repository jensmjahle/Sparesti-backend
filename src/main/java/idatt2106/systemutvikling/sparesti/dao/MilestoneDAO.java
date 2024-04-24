package idatt2106.systemutvikling.sparesti.dao;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "milestone")
public class MilestoneDAO {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long milestoneId;
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "username", referencedColumnName = "username")
  private UserDAO userDAO;
  private String milestoneTitle;
  private String milestoneDescription;
  private Long milestoneGoalSum;
  private Long milestoneCurrentSum;
  @Lob
  private byte[] milestoneImage;
  private LocalDateTime deadlineDate;
  private LocalDateTime startDate;

}
