package idatt2106.systemutvikling.sparesti.mapper;

import idatt2106.systemutvikling.sparesti.dao.TransactionCategoryDAO;
import idatt2106.systemutvikling.sparesti.enums.TransactionCategory;
import lombok.NonNull;

public class TransactionCategoryMapper {

    public static TransactionCategory toModel(@NonNull TransactionCategoryDAO dao) {
        return TransactionCategory.valueOf(dao.getTransactionCategory().getCategory());
    }
}
