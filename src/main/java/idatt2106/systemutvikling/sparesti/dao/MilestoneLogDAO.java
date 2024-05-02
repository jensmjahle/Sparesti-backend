package idatt2106.systemutvikling.sparesti.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

/**
 * Data Access Object for MilestoneLog
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "milestone_log")
public class MilestoneLogDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long milestoneId;

    @ManyToOne
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
