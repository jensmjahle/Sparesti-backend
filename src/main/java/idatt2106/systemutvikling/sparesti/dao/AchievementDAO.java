package idatt2106.systemutvikling.sparesti.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "achievement")
public class AchievementDAO {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long achievementId;
private String achievementTitle;
private String achievementDescription;
@Lob
private byte[] badge;

}
