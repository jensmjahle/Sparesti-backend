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
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ConditionDAO {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "conditionIdGenerator")
  @SequenceGenerator(name = "conditionIdGenerator", sequenceName = "condition_sequence", allocationSize = 1)
  private Long conditionId;
@ManyToOne (cascade = CascadeType.ALL)
@JoinColumn(name = "conditionId", referencedColumnName = "achievementId")
private AchievementDAO achievementDAO;
private Long quantity;
  @Enumerated(EnumType.STRING)
private ConditionType conditionType;
}
