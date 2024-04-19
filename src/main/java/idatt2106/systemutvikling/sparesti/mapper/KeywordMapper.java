package idatt2106.systemutvikling.sparesti.mapper;

import idatt2106.systemutvikling.sparesti.enums.TransactionCategory;
import java.util.HashMap;
import java.util.Map;

public class KeywordMapper {
private static final Map<String, TransactionCategory> keywordCategoryMap = new HashMap<>();

static {
    //GROCERIES
    keywordCategoryMap.put("dagligvare", TransactionCategory.GROCERIES);
    keywordCategoryMap.put("matvarebutikk", TransactionCategory.GROCERIES);
    keywordCategoryMap.put("matbutikk", TransactionCategory.GROCERIES);
    keywordCategoryMap.put("rema 1000", TransactionCategory.GROCERIES);
    keywordCategoryMap.put("kiwi", TransactionCategory.GROCERIES);
    keywordCategoryMap.put("meny", TransactionCategory.GROCERIES);
    keywordCategoryMap.put("coop", TransactionCategory.GROCERIES);
    keywordCategoryMap.put("bunnpris", TransactionCategory.GROCERIES);
    keywordCategoryMap.put("joker", TransactionCategory.GROCERIES);
    keywordCategoryMap.put("spar", TransactionCategory.GROCERIES);
    keywordCategoryMap.put("extra", TransactionCategory.GROCERIES);
    keywordCategoryMap.put("matkroken", TransactionCategory.GROCERIES);

    //RESTAURANT_AND_CAFE
    keywordCategoryMap.put("cafe", TransactionCategory.RESTAURANT_AND_CAFE);
    keywordCategoryMap.put("spisested", TransactionCategory.RESTAURANT_AND_CAFE);
    keywordCategoryMap.put("matservering", TransactionCategory.RESTAURANT_AND_CAFE);
    keywordCategoryMap.put("kafeteria", TransactionCategory.RESTAURANT_AND_CAFE);
    keywordCategoryMap.put("restaurant", TransactionCategory.RESTAURANT_AND_CAFE);
    keywordCategoryMap.put("kafé", TransactionCategory.RESTAURANT_AND_CAFE);
    keywordCategoryMap.put("mcdonald's", TransactionCategory.RESTAURANT_AND_CAFE);
    keywordCategoryMap.put("burger king", TransactionCategory.RESTAURANT_AND_CAFE);
    keywordCategoryMap.put("starbucks", TransactionCategory.RESTAURANT_AND_CAFE);
    keywordCategoryMap.put("espresso house", TransactionCategory.RESTAURANT_AND_CAFE);
    keywordCategoryMap.put("peppes pizza", TransactionCategory.RESTAURANT_AND_CAFE);

    //TRANSPORTATION
    keywordCategoryMap.put("transportation", TransactionCategory.TRANSPORTATION);
    keywordCategoryMap.put("transport", TransactionCategory.TRANSPORTATION);
    keywordCategoryMap.put("bus", TransactionCategory.TRANSPORTATION);
    keywordCategoryMap.put("vy", TransactionCategory.TRANSPORTATION);
    keywordCategoryMap.put("tog", TransactionCategory.TRANSPORTATION);
    keywordCategoryMap.put("atb", TransactionCategory.TRANSPORTATION);
    keywordCategoryMap.put("flyreise", TransactionCategory.TRANSPORTATION);
    keywordCategoryMap.put("bilutleie", TransactionCategory.TRANSPORTATION);
    keywordCategoryMap.put("bensinstasjon", TransactionCategory.TRANSPORTATION);
    keywordCategoryMap.put("parkering", TransactionCategory.TRANSPORTATION);
    keywordCategoryMap.put("taxi", TransactionCategory.TRANSPORTATION);
    keywordCategoryMap.put("uber", TransactionCategory.TRANSPORTATION);
    keywordCategoryMap.put("flyselskap", TransactionCategory.TRANSPORTATION);
    keywordCategoryMap.put("billett", TransactionCategory.TRANSPORTATION);
    keywordCategoryMap.put("togstasjon", TransactionCategory.TRANSPORTATION);
    keywordCategoryMap.put("flyplass", TransactionCategory.TRANSPORTATION);
    keywordCategoryMap.put("voi", TransactionCategory.TRANSPORTATION);
    keywordCategoryMap.put("bysykkel", TransactionCategory.TRANSPORTATION);
    keywordCategoryMap.put("ryde", TransactionCategory.TRANSPORTATION);
    keywordCategoryMap.put("tier", TransactionCategory.TRANSPORTATION);

    //SHOPPING
    keywordCategoryMap.put("shopping", TransactionCategory.SHOPPING);
    keywordCategoryMap.put("handle", TransactionCategory.SHOPPING);
    keywordCategoryMap.put("kjøpesenter", TransactionCategory.SHOPPING);
    keywordCategoryMap.put("klær", TransactionCategory.SHOPPING);
    keywordCategoryMap.put("elektronikk", TransactionCategory.SHOPPING);
    keywordCategoryMap.put("møbler", TransactionCategory.SHOPPING);
    keywordCategoryMap.put("leketøy", TransactionCategory.SHOPPING);
    keywordCategoryMap.put("apotek", TransactionCategory.SHOPPING);
    keywordCategoryMap.put("sportsbutikk", TransactionCategory.SHOPPING);
    keywordCategoryMap.put("bokhandel", TransactionCategory.SHOPPING);
    keywordCategoryMap.put("gullsmed", TransactionCategory.SHOPPING);

    //ENTERTAINMENT
    keywordCategoryMap.put("entertainment", TransactionCategory.ENTERTAINMENT);
    keywordCategoryMap.put("kino", TransactionCategory.ENTERTAINMENT);
    keywordCategoryMap.put("netflix", TransactionCategory.ENTERTAINMENT);
    keywordCategoryMap.put("fornøyelsespark", TransactionCategory.ENTERTAINMENT);
    keywordCategoryMap.put("konsert", TransactionCategory.ENTERTAINMENT);
    keywordCategoryMap.put("teater", TransactionCategory.ENTERTAINMENT);
    keywordCategoryMap.put("museum", TransactionCategory.ENTERTAINMENT);
    keywordCategoryMap.put("idrettsarrangement", TransactionCategory.ENTERTAINMENT);
    keywordCategoryMap.put("festival", TransactionCategory.ENTERTAINMENT);
    keywordCategoryMap.put("fornøyelsessenter", TransactionCategory.ENTERTAINMENT);
    keywordCategoryMap.put("dyrepark", TransactionCategory.ENTERTAINMENT);
    keywordCategoryMap.put("bowling", TransactionCategory.ENTERTAINMENT);
    keywordCategoryMap.put("lekeland", TransactionCategory.ENTERTAINMENT);
    keywordCategoryMap.put("spill", TransactionCategory.ENTERTAINMENT);
    keywordCategoryMap.put("bingo", TransactionCategory.ENTERTAINMENT);
    keywordCategoryMap.put("casino", TransactionCategory.ENTERTAINMENT);

    //INCOME
    keywordCategoryMap.put("income", TransactionCategory.INCOME);
    keywordCategoryMap.put("lønn", TransactionCategory.INCOME);
    keywordCategoryMap.put("lønning", TransactionCategory.INCOME);
    keywordCategoryMap.put("utbetaling", TransactionCategory.INCOME);
    keywordCategoryMap.put("overføring", TransactionCategory.INCOME);
    keywordCategoryMap.put("honorar", TransactionCategory.INCOME);
    keywordCategoryMap.put("stipend", TransactionCategory.INCOME);
    keywordCategoryMap.put("refusjon", TransactionCategory.INCOME);
    keywordCategoryMap.put("tilbakebetaling", TransactionCategory.INCOME);
    keywordCategoryMap.put("renter", TransactionCategory.INCOME);

    //LOAN_DONATIONS
    keywordCategoryMap.put("loan", TransactionCategory.LOAN_DONATIONS);
    keywordCategoryMap.put("donation", TransactionCategory.LOAN_DONATIONS);
    keywordCategoryMap.put("lån", TransactionCategory.LOAN_DONATIONS);
    keywordCategoryMap.put("forbrukslån", TransactionCategory.LOAN_DONATIONS);
    keywordCategoryMap.put("boliglån", TransactionCategory.LOAN_DONATIONS);
    keywordCategoryMap.put("donasjon", TransactionCategory.LOAN_DONATIONS);
    keywordCategoryMap.put("veldedighet", TransactionCategory.LOAN_DONATIONS);
    keywordCategoryMap.put("gave", TransactionCategory.LOAN_DONATIONS);
    keywordCategoryMap.put("støtte", TransactionCategory.LOAN_DONATIONS);
    keywordCategoryMap.put("bidrag", TransactionCategory.LOAN_DONATIONS);
    keywordCategoryMap.put("sponsing", TransactionCategory.LOAN_DONATIONS);
    keywordCategoryMap.put("skjønnsmidler", TransactionCategory.LOAN_DONATIONS);
}

public static TransactionCategory getCategory(String keyword) {
    return keywordCategoryMap.getOrDefault(keyword.toLowerCase(), TransactionCategory.NOT_CATEGORIZED);

}
}

