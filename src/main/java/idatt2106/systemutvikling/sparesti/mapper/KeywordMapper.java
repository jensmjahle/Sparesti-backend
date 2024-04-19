package idatt2106.systemutvikling.sparesti.mapper;

import idatt2106.systemutvikling.sparesti.enums.TransactionCategory;
import java.util.HashMap;
import java.util.Map;

public class KeywordMapper {
private static final Map<String, TransactionCategory> keywordCategoryMap = new HashMap<>();

static {
    //GROCERIES
    keywordCategoryMap.put("groceries", TransactionCategory.GROCERIES);
    keywordCategoryMap.put("rema 1000", TransactionCategory.GROCERIES);

    //RESTAURANT_AND_CAFE
    keywordCategoryMap.put("restaurant", TransactionCategory.RESTAURANT_AND_CAFE);
    keywordCategoryMap.put("cafe", TransactionCategory.RESTAURANT_AND_CAFE);

    //TRANSPORTATION
    keywordCategoryMap.put("transportation", TransactionCategory.TRANSPORTATION);

    //SHOPPING
    keywordCategoryMap.put("shopping", TransactionCategory.SHOPPING);

    //ENTERTAINMENT
    keywordCategoryMap.put("entertainment", TransactionCategory.ENTERTAINMENT);

    //INCOME
    keywordCategoryMap.put("income", TransactionCategory.INCOME);

    //LOAN_DONATIONS
    keywordCategoryMap.put("loan", TransactionCategory.LOAN_DONATIONS);
    keywordCategoryMap.put("donation", TransactionCategory.LOAN_DONATIONS);
}

public static TransactionCategory getCategory(String keyword) {
    return keywordCategoryMap.getOrDefault(keyword.toLowerCase(), TransactionCategory.NOT_CATEGORIZED);

}
}

