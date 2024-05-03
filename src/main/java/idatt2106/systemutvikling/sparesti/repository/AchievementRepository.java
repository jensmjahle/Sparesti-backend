package idatt2106.systemutvikling.sparesti.repository;

import idatt2106.systemutvikling.sparesti.dao.AchievementDAO;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository for the AchievementDAO entity.
 */
@Repository
public interface AchievementRepository extends JpaRepository<AchievementDAO, String> {

  /**
   * Method to find achievement by id
   *
   * @param id the id of a certain achievement
   * @return the achievement
   */
  AchievementDAO findAchievementDAOByAchievementId(long id);

  /**
   * Method to find all achievements tied to a user
   *
   * @param username the username of the user
   * @return a list of achievements that belong to the user with the given username
   */
  @Query(value = "SELECT a.* " +
      "FROM achievementdao a " +
      "JOIN user_achievements ua ON a.achievement_id = ua.achievement_id " +
      "JOIN userdao u ON ua.username = u.username " +
      "WHERE u.username = :username", nativeQuery = true)
  List<AchievementDAO> findAchievementDAOByUsername(@Param("username") String username);

  /**
   * Method to find an achievement based off of title
   *
   * @param title the title of the achievement
   * @return the achievement
   */
  AchievementDAO findAchievementDAOByAchievementTitle(String title);

  /**
   * Method to find achievement based off of title
   *
   * @param description the description of the achievement
   * @return the achievement
   */
  AchievementDAO findAchievementDAOByAchievementDescription(String description);
}
