package idatt2106.systemutvikling.sparesti.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ChallengeTheme {
  LIMIT_DINING_OUT("Begrens spising ute"),
  LIMIT_COFFEE_SHOP("Begrens kaffekjøp"),
  LIMIT_TAKEOUT_FOOD("Begrens takeout mat"),
  BRING_LUNCH_TO_WORK("Ta med lunsj på jobb"),
  LIMIT_ALCOHOL("Begrens alkoholkjøp"),
  LIMIT_NIGHT_OUT_SPENDING("Begrens utgiftene på byen"),
  LIMIT_NEW_CLOTHES("Begrens kjøp av nye klær"),
  LIMIT_ONLINE_SHOPPING("Begrens netthandel"),
  REDUCE_IMPULSE_BUYING("Reduser impulskjøp"),
  SHOP_SECOND_HAND("Handle brukt"),
  REDUCE_TRANSPORTATION_COSTS("Reduser transportkostnader"),
  REDUCE_ELECTRICITY_BILL("Reduser strømregning"),
  REDUCE_WATER_BILL("Reduser vannregning"),
  REDUCE_GAS_BILL("Reduser gassregning"),
  MEAL_PLANNING("Planlegg måltider"),
  BUY_IN_BULK("Kjøp i bulk"),
  REDUCE_ENTERTAINMENT_COSTS("Reduser underholdningskostnader"),
  LIMIT_GAMING_SPENDING("Begrens spillkjøp"),
  LIMIT_SUBSCRIPTIONS("Begrens abonnementer"),
  REDUCE_PERSONAL_CARE_COSTS("Reduser personlig pleie"),
  REDUCE_PET_COSTS("Reduser dyrekostnader"),
  REDUCE_HOLIDAY_COSTS("Reduser feriekostnader"),
  REDUCE_GIFT_COSTS("Reduser gavekostnader"),
  LIMIT_HOBBY_SPENDING("Begrens hobbyutgifter"),
  LIMIT_LOTTERY_TICKETS("Begrens lotterikjøp"),
  LIMIT_TOBACCO("Kjøp mindre tobakk"),
  LIMIT_SNACKS("Begrens snacks kjøp"),
  LIMIT_VEHICLE_COSTS("Begrens kjøretøykostnader"),
  REDUCE_CHILD_CARE_COSTS("Reduser barneomsorgskostnader"),
  REDUCE_TECHNOLOGY_COSTS("Reduser teknologikostnader"),
  ;

  private final String standardMessage;

}
