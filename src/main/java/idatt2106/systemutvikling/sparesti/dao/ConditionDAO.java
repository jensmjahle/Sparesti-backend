package idatt2106.systemutvikling.sparesti.dao;

import idatt2106.systemutvikling.sparesti.enums.ConditionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "condition")
public class ConditionDAO {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long conditionId;
@ManyToOne (cascade = CascadeType.ALL)
@JoinColumn(name = "achievementId", referencedColumnName = "achievementId")
private AchievementDAO achievementDAO;
private Long quantity;
  @Enumerated(EnumType.STRING)
private ConditionType conditionType;
}
