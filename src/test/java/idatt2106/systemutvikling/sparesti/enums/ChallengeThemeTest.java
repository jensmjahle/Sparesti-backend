package idatt2106.systemutvikling.sparesti.enums;

import org.junit.jupiter.api.Test;

import static idatt2106.systemutvikling.sparesti.enums.ChallengeTheme.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class ChallengeThemeTest {

  @Test
  void testGetAllThemesReturnsCorrectValues() {
    List<ChallengeTheme> allThemes = ChallengeTheme.getAllThemes();
    assertNotNull(allThemes);
    assertFalse(allThemes.contains(BASE_GROCERIES));
    assertFalse(allThemes.contains(BASE_RESTAURANT_AND_CAFE));
    assertFalse(allThemes.contains(BASE_TRANSPORTATION));
    assertFalse(allThemes.contains(BASE_SHOPPING));
    assertFalse(allThemes.contains(BASE_ENTERTAINMENT));
    assertFalse(allThemes.contains(BASE_OTHER));

  }

  @Test
  void testGetAllThemes() {
    List<ChallengeTheme> allThemes = ChallengeTheme.getAllThemes();
    assertNotNull(allThemes);
    assertEquals(30, allThemes.size());
  }

  @Test
  void testGetAllThemesFromCategory() {
    List<ChallengeTheme> groceryThemes = ChallengeTheme.getAllThemesFromCategory(TransactionCategory.GROCERIES);
    assertNotNull(groceryThemes);
    assertEquals(4, groceryThemes.size());
  }

  @Test
  void testGetBaseTheme() {
    ChallengeTheme groceriesBaseTheme = ChallengeTheme.getBaseTheme(TransactionCategory.GROCERIES);
    assertEquals(BASE_GROCERIES, groceriesBaseTheme);

    ChallengeTheme entertainmentBaseTheme = ChallengeTheme.getBaseTheme(TransactionCategory.ENTERTAINMENT);
    assertEquals(ChallengeTheme.BASE_ENTERTAINMENT, entertainmentBaseTheme);

    ChallengeTheme unknownCategoryBaseTheme = ChallengeTheme.getBaseTheme(TransactionCategory.NOT_CATEGORIZED);
    assertEquals(ChallengeTheme.BASE_OTHER, unknownCategoryBaseTheme);
  }
}
