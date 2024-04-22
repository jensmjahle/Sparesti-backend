package idatt2106.systemutvikling.sparesti.mapper;

import idatt2106.systemutvikling.sparesti.dao.TransactionCategoryDAO;
import idatt2106.systemutvikling.sparesti.enums.TransactionCategory;

public class TransactionCategoryMapper {

    public static TransactionCategory toModel(TransactionCategoryDAO dao) {
        return TransactionCategory.valueOf(dao.getTransactionCategory().getCategory());
    }
}
