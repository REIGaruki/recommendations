package pro.sky.team2.bank_service.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class TransactionsRepository {

    private final JdbcTemplate jdbcTemplate;

    public TransactionsRepository(@Qualifier("recommendationsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int getCreditTransactionsCount(UUID user) {
        Integer result = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM transactions t JOIN products p ON t.product_id=p.id WHERE p.type = 'CREDIT' AND t.user_id = ?",
                Integer.class,
                user
        );
        return result != null ? result : 0;
    }

    public int getInvestTransactionsCount(UUID user) {
        Integer result = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM transactions t JOIN products p ON t.product_id=p.id WHERE p.type = 'INVEST' AND t.user_id = ?",
                Integer.class,
                user
        );
        return result != null ? result : 0;
    }

    public int getDebitTransactionsCount(UUID user) {
        Integer result = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM transactions t JOIN products p ON t.product_id=p.id WHERE p.type = 'DEBIT' AND t.user_id = ?",
                Integer.class,
                user
        );
        return result != null ? result : 0;
    }

    public int getDebitDepositTransactionAmount(UUID user) {
        Integer result = jdbcTemplate.queryForObject(
                "SELECT SUM(amount) FROM transactions t JOIN products p ON t.product_id=p.id WHERE p.type = 'DEBIT' AND t.type = 'DEPOSIT' AND t.user_id = ?",
                Integer.class,
                user
        );
        return result != null ? result : 0;
    }

    public int getDebitWithdrawTransactionAmount(UUID user) {
        Integer result = jdbcTemplate.queryForObject(
                "SELECT SUM(amount) FROM transactions t JOIN products p ON t.product_id=p.id WHERE p.type = 'DEBIT' AND t.type = 'WITHDRAW' AND t.user_id = ?",
                Integer.class,
                user
        );
        return result != null ? result : 0;
    }

    public int getSavingDepositTransactionsAmount(UUID user) {
        Integer result = jdbcTemplate.queryForObject(
                "SELECT SUM(amount) FROM transactions t JOIN products p ON t.product_id=p.id WHERE p.type = 'SAVING' AND t.type = 'DEPOSIT' AND t.user_id = ?",
                Integer.class,
                user
        );
        return result != null ? result : 0;
    }

}