package idatt2106.systemutvikling.sparesti.dao;

import jakarta.persistence.CascadeType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MilestoneDAO {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "milestoneIdGenerator")
  @SequenceGenerator(name = "milestoneIdGenerator", sequenceName = "milestone_sequence", allocationSize = 1)
  private Long milestoneId;
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "username", referencedColumnName = "username")
  private UserDAO username;
  private String milestoneTitle;
  private String milestoneDescription;
  private Long milestoneGoalSum;
  private Long milestoneCurrentSum;
  @Lob
  private byte[] milestoneImage;
  private LocalDateTime deadlineDate;
  private LocalDateTime startDate;

}
