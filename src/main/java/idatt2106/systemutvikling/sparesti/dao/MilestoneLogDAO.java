package idatt2106.systemutvikling.sparesti.dao;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class MilestoneLogDAO {
  @Id
private Long milestoneId;
@ManyToOne (cascade = CascadeType.ALL)
@JoinColumn(name = "username", referencedColumnName = "username")
private UserDAO userDAO;
private String milestoneTitle;
private String milestoneDescription;
private Long milestoneGoalSum;
private Long milestoneAchievedSum;
@Lob
private byte[] milestoneImage;
private LocalDateTime completionDate;

}
