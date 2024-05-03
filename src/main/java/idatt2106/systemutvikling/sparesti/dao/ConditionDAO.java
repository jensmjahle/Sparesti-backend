package idatt2106.systemutvikling.sparesti.dao;

import idatt2106.systemutvikling.sparesti.enums.ConditionType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Access Object for Condition
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "conditions")
public class ConditionDAO {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long conditionId;
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "achievementId", referencedColumnName = "achievementId")
  private AchievementDAO achievementDAO;
  private Long quantity;
  @Enumerated(EnumType.STRING)
  private ConditionType conditionType;
}
