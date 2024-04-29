package idatt2106.systemutvikling.sparesti.repository;

import idatt2106.systemutvikling.sparesti.dao.AchievementDAO;
import idatt2106.systemutvikling.sparesti.dao.ManualSavingDAO;
import idatt2106.systemutvikling.sparesti.dao.MilestoneDAO;
import idatt2106.systemutvikling.sparesti.dao.UserDAO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class UserDAOTests {

    private static final UserDAO TEST_USER_1 = new UserDAO();
    private static final AchievementDAO TEST_ACHIEVEMENT = new AchievementDAO();
    private static final MilestoneDAO TEST_MILESTONE = new MilestoneDAO();
    private static final ManualSavingDAO TEST_MANUAL_SAVING = new ManualSavingDAO();



    @Autowired
    private UserRepository dbUser;

    @Autowired
    private AchievementRepository dbAchievement;

    @Autowired
    private MilestoneRepository dbMilestone;

    @Autowired
    private ManualSavingRepository dbSaving;



    @BeforeAll
    public static void setup() {
        TEST_ACHIEVEMENT.setAchievementTitle("Test achievement");
        TEST_ACHIEVEMENT.setAchievementDescription("An achievement for use in testing");
        TEST_ACHIEVEMENT.setBadge("Badge_image.jpg".getBytes());

        TEST_USER_1.setUsername("testUser1");
        TEST_USER_1.setPassword("password");

        TEST_MILESTONE.setMilestoneTitle("Test Milestone");
        TEST_MILESTONE.setMilestoneTitle("Test Milestone Description");
        TEST_MILESTONE.setMilestoneGoalSum(100L);
        TEST_MILESTONE.setMilestoneCurrentSum(0L);

        TEST_MANUAL_SAVING.setAmount(100L);
        TEST_MANUAL_SAVING.setTimeOfTransfer(new Date());
        TEST_MANUAL_SAVING.setMilestoneId(1L);
    }

    @BeforeEach
    public void cleanRepositories() {
        dbUser.deleteAll();
        dbAchievement.deleteAll();
        dbMilestone.deleteAll();
        dbSaving.deleteAll();
    }



    @Test
    public void whenDeleteUser_deletesRelationsToAchievements() {
        // Arrange
        AchievementDAO achievement = dbAchievement.save(TEST_ACHIEVEMENT);
        UserDAO user = dbUser.save(TEST_USER_1);
        user.getAchievements().add(achievement);
        user = dbUser.save(user);

        // Act
        dbUser.delete(user);

        // Assert
        assertNull(dbUser.findByUsername(user.getUsername()));
        user.getAchievements().clear();
        user = dbUser.save(user);
        assertTrue(user.getAchievements().isEmpty());
    }

    @Test
    public void whenDeleteUser_doesNotDeleteActualAchievements() {
        // Arrange
        AchievementDAO expected = dbAchievement.save(TEST_ACHIEVEMENT);
        UserDAO user = dbUser.save(TEST_USER_1);
        user.getAchievements().add(expected);
        user = dbUser.save(user);

        // Act
        dbUser.delete(user);

        // Assert
        AchievementDAO actual = dbAchievement.findAchievementDAOByAchievementId(expected.getAchievementId());
        assertNotNull(actual);
        assertEquals(expected.getAchievementId(), actual.getAchievementId());
    }

    @Test
    public void whenSaveUser_connectsToAchievements() {
        // Arrange
        AchievementDAO expected = dbAchievement.save(TEST_ACHIEVEMENT);
        UserDAO user = dbUser.save(TEST_USER_1);

        // Act
        user.getAchievements().add(expected);
        user = dbUser.save(user);

        // Assert
        UserDAO actualUser = dbUser.findByUsername(user.getUsername());
        assertNotNull(actualUser);
        AchievementDAO actual = actualUser.getAchievements().get(0);
        assertEquals(expected, actual);
    }

    @Test
    public void deleteUserByUsername_actuallyDeletesTheCorrectUser() {
        // Arrange
        UserDAO user1 = dbUser.save(TEST_USER_1);
        UserDAO user2 = TEST_USER_1;
        user2.setUsername("Test user 2");
        user2 = dbUser.save(user2);

        // Act
        dbUser.deleteByUsername(user1.getUsername());

        // Assert
        assertNull(dbUser.findByUsername(user1.getUsername()));
        assertNotNull(dbUser.findByUsername(user2.getUsername()));
    }



    // @Test
    // public void deleteUserByUsername_deletesAllRelatedManualSavings() {
    //     // Arrange
    //     UserDAO user = dbUser.save(TEST_USER_1);
    //     user.getManualSavings().add(TEST_MANUAL_SAVING);
    //     user = dbUser.save(TEST_USER_1);
    //     // Act
    //     String username = user.getUsername();
    //     dbUser.delete(user);
    //     // Assert
    //     // UserDAO userAway = dbUser.findByUsername(username);
    //     // assertNull(userAway);
    //     List<ManualSavingDAO> actual = dbSaving.findByUser_UsernameAndTimeOfTransferAfter(username, new Date(0));
    //     assertTrue(actual.isEmpty());
    // }
}
