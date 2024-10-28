package pro.sky.team2.bank_service.repository;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ArgumentsRepository {

    public static final List<String> QUERIES = new ArrayList<>(List.of("USER_OF", "ACTIVE_USER_OF", "TRANSACTION_SUM_COMPARE", "TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW"));

    public static final List<String> PRODUCT_TYPES = new ArrayList<>(List.of("DEBIT", "CREDIT", "INVEST", "SAVING"));

    public static final List<String> TRANSACTION_TYPES = new ArrayList<>(List.of("WITHDRAW", "DEPOSIT"));

    public static final List<String> RELATION_OPERATORS = new ArrayList<>(List.of(">", "<", ">=", "<=", "=="));

    public ArgumentsRepository() {
    }
}
