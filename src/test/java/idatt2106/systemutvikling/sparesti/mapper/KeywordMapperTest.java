package idatt2106.systemutvikling.sparesti.mapper;


import idatt2106.systemutvikling.sparesti.enums.TransactionCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class KeywordMapperTest {

  @Test
  @DisplayName("Maps to 'Groceries' category correctly")
  void testGetCategory_Groceries() {
    // Arrange
    String keyword = "dagligvare";

    // Act
    TransactionCategory category = KeywordMapper.getCategory(keyword);

    // Assert
    assertEquals(TransactionCategory.GROCERIES, category);
  }

  @Test
  @DisplayName("Maps to 'Restaurant and Cafe' category correctly")
  void testGetCategory_RestaurantAndCafe() {
    // Arrange
    String keyword = "cafe";

    // Act
    TransactionCategory category = KeywordMapper.getCategory(keyword);

    // Assert
    assertEquals(TransactionCategory.RESTAURANT_AND_CAFE, category);
  }

  @Test
  @DisplayName("Maps to 'Transportation' category correctly")
  void testGetCategory_Transportation() {
    // Arrange
    String keyword = "bus";

    // Act
    TransactionCategory category = KeywordMapper.getCategory(keyword);

    // Assert
    assertEquals(TransactionCategory.TRANSPORTATION, category);
  }

  @Test
  @DisplayName("Maps to 'Shopping' category correctly")
  void testGetCategory_Shopping() {
    // Arrange
    String keyword = "shopping";

    // Act
    TransactionCategory category = KeywordMapper.getCategory(keyword);

    // Assert
    assertEquals(TransactionCategory.SHOPPING, category);
  }

  @Test
  @DisplayName("Maps to 'Entertainment' category correctly")
  void testGetCategory_Entertainment() {
    // Arrange
    String keyword = "kino";

    // Act
    TransactionCategory category = KeywordMapper.getCategory(keyword);

    // Assert
    assertEquals(TransactionCategory.ENTERTAINMENT, category);
  }

  @Test
  @DisplayName("Maps to 'Income' category correctly")
  void testGetCategory_Income() {
    // Arrange
    String keyword = "lønn";

    // Act
    TransactionCategory category = KeywordMapper.getCategory(keyword);

    // Assert
    assertEquals(TransactionCategory.INCOME, category);
  }

  @Test
  @DisplayName("Maps to 'Loan and Donations' category correctly")
  void testGetCategory_LoanAndDonations() {
    // Arrange
    String keyword = "loan";

    // Act
    TransactionCategory category = KeywordMapper.getCategory(keyword);

    // Assert
    assertEquals(TransactionCategory.LOAN_DONATIONS, category);
  }

  @Test
  @DisplayName("Maps to 'Other' category correctly")
  void testGetCategory_NotCategorized() {
    // Arrange
    String keyword = "unknown";

    // Act
    TransactionCategory category = KeywordMapper.getCategory(keyword);

    // Assert
    assertEquals(TransactionCategory.NOT_CATEGORIZED, category);
  }
}
