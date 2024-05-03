package idatt2106.systemutvikling.sparesti.dao;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Access Object for Achievement
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "achievement")
public class AchievementDAO {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Setter
  private Long achievementId;

  @Setter
  private String achievementTitle;

  @Setter
  private String achievementDescription;

  @Lob
  @Setter
  private byte[] badge;

  @ManyToMany(mappedBy = "achievements", fetch = FetchType.LAZY)
  private List<UserDAO> users;
}
