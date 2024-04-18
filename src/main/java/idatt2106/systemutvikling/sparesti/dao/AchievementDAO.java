package idatt2106.systemutvikling.sparesti.dao;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class AchievementDAO {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long achievementId;
private String achievementTitle;
private String achievementDescription;
@Lob
private byte[] badge;

}
