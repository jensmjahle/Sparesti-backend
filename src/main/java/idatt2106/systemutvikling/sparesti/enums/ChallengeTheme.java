package idatt2106.systemutvikling.sparesti.enums;


import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ChallengeTheme {
  BASE_GROCERIES("Reduser dagligvare forbruket", TransactionCategory.GROCERIES),
  BASE_RESTAURANT_AND_CAFE("Reduser spising ute", TransactionCategory.RESTAURANT_AND_CAFE),
  BASE_TRANSPORTATION("Reduser transportkostnader", TransactionCategory.TRANSPORTATION),
  BASE_SHOPPING("Reduser shopping", TransactionCategory.SHOPPING),
  BASE_ENTERTAINMENT("Reduser underholdningskostnader", TransactionCategory.ENTERTAINMENT),
  BASE_OTHER("Reduser andre kostnader", TransactionCategory.OTHER),

  LIMIT_DINING_OUT("Begrens spising ute", TransactionCategory.RESTAURANT_AND_CAFE),
  LIMIT_COFFEE_SHOP("Begrens kaffekjøp", TransactionCategory.RESTAURANT_AND_CAFE),
  LIMIT_TAKEOUT_FOOD("Begrens takeout mat", TransactionCategory.RESTAURANT_AND_CAFE),
  BRING_LUNCH_TO_WORK("Ta med lunsj på jobb", TransactionCategory.OTHER),
  LIMIT_ALCOHOL("Begrens alkoholkjøp", TransactionCategory.OTHER),
  LIMIT_NIGHT_OUT_SPENDING("Begrens utgiftene på byen", TransactionCategory.ENTERTAINMENT),
  LIMIT_NEW_CLOTHES("Begrens kjøp av nye klær", TransactionCategory.SHOPPING),
  LIMIT_ONLINE_SHOPPING("Begrens netthandel", TransactionCategory.SHOPPING),
  REDUCE_IMPULSE_BUYING("Reduser impulskjøp", TransactionCategory.SHOPPING),
  SHOP_SECOND_HAND("Handle brukt", TransactionCategory.SHOPPING),
  REDUCE_TRANSPORTATION_COSTS("Reduser transportkostnader", TransactionCategory.TRANSPORTATION),
  REDUCE_ELECTRICITY_BILL("Reduser strømregning", TransactionCategory.OTHER),
  REDUCE_WATER_BILL("Reduser vannregning", TransactionCategory.OTHER),
  REDUCE_GAS_BILL("Reduser gassregning", TransactionCategory.OTHER),
  MEAL_PLANNING("Planlegg måltider", TransactionCategory.GROCERIES),
  BUY_IN_BULK("Kjøp i bulk", TransactionCategory.GROCERIES),
  REDUCE_ENTERTAINMENT_COSTS("Reduser underholdningskostnader", TransactionCategory.ENTERTAINMENT),
  LIMIT_GAMING_SPENDING("Begrens spillkjøp", TransactionCategory.ENTERTAINMENT),
  LIMIT_SUBSCRIPTIONS("Begrens abonnementer", TransactionCategory.ENTERTAINMENT),
  REDUCE_PERSONAL_CARE_COSTS("Reduser personlig pleie", TransactionCategory.OTHER),
  REDUCE_PET_COSTS("Reduser dyrekostnader", TransactionCategory.OTHER),
  REDUCE_HOLIDAY_COSTS("Reduser feriekostnader", TransactionCategory.OTHER),
  REDUCE_GIFT_COSTS("Reduser gavekostnader", TransactionCategory.OTHER),
  LIMIT_HOBBY_SPENDING("Begrens hobbyutgifter", TransactionCategory.ENTERTAINMENT),
  LIMIT_LOTTERY_TICKETS("Begrens lotterikjøp", TransactionCategory.ENTERTAINMENT),
  LIMIT_TOBACCO("Kjøp mindre tobakk", TransactionCategory.OTHER),
  LIMIT_SNACKS("Begrens snacks kjøp", TransactionCategory.GROCERIES),
  LIMIT_VEHICLE_COSTS("Begrens kjøretøykostnader", TransactionCategory.TRANSPORTATION),
  REDUCE_CHILD_CARE_COSTS("Reduser barneomsorgskostnader", TransactionCategory.OTHER),
  REDUCE_TECHNOLOGY_COSTS("Reduser teknologikostnader", TransactionCategory.ENTERTAINMENT),
  ;

  private final String standardMessage;
  private final TransactionCategory expenseCategory;

  public static List<ChallengeTheme> getAllThemes() {
    List<ChallengeTheme> allThemes = new ArrayList<>();
    for (ChallengeTheme theme : ChallengeTheme.values()) {
      // Skip base themes
      if (!theme.equals(BASE_GROCERIES) && !theme.equals(BASE_RESTAURANT_AND_CAFE) &&
          !theme.equals(BASE_TRANSPORTATION) && !theme.equals(BASE_SHOPPING) &&
          !theme.equals(BASE_ENTERTAINMENT) && !theme.equals(BASE_OTHER)) {
        allThemes.add(theme);
      }
    }
    return allThemes;
  }

  public List<ChallengeTheme> getAllThemesFromCategory(TransactionCategory category) {
    return null;
  }

  public ChallengeTheme getBaseTheme(TransactionCategory category) {
    switch (category) {
      case GROCERIES -> {
        return BASE_GROCERIES;
      }
      case RESTAURANT_AND_CAFE -> {
        return BASE_RESTAURANT_AND_CAFE;
      }
      case TRANSPORTATION -> {
        return BASE_TRANSPORTATION;
      }
      case SHOPPING -> {
        return BASE_SHOPPING;
      }
      case ENTERTAINMENT -> {
        return BASE_ENTERTAINMENT;
      }
      default -> {
        return BASE_OTHER;
      }
    }
  }
}
