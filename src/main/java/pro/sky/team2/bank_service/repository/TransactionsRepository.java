package pro.sky.team2.bank_service.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class TransactionsRepository {

    private final JdbcTemplate jdbcTemplate;

    public TransactionsRepository(@Qualifier("recommendationsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean checkUserOf(UUID userId, List<String> arguments) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(
                "SELECT CAST (CASE WHEN (SELECT COUNT(*) FROM transactions t JOIN products p ON t.product_id=p.id WHERE p.type = ? AND t.user_id = ?) > ? THEN 1 ELSE 0 END AS BIT)",
                boolean.class,
                arguments.get(0),
                userId,
                0
        ));
    }

    public boolean checkActiveUserOf(UUID userId, List<String> arguments) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(
                "SELECT CAST (CASE WHEN (SELECT COUNT(*) FROM transactions t JOIN products p ON t.product_id=p.id WHERE p.type = ? AND t.user_id = ?) > ? THEN 1 ELSE 0 END AS BIT)",
                boolean.class,
                arguments.get(0),
                userId,
                5
        ));
    }

    public boolean checkTransactionSumCompare(UUID userId, List<String> arguments) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(
                "SELECT CAST (CASE WHEN (SELECT SUM(amount) FROM transactions t JOIN products p ON t.product_id=p.id WHERE p.type = ? AND t.type = ? AND t.user_id = ?) " + arguments.get(2) + " ? THEN 1 ELSE 0 END AS BIT)",
                boolean.class,
                arguments.get(0),
                arguments.get(1),
                userId,
                Integer.valueOf(arguments.get(3))
        ));
    }

    public boolean checkTransactionSumCompareDepositWithdraw(UUID userId, List<String> arguments) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(
                "SELECT CAST (CASE WHEN (SELECT SUM(amount) FROM transactions t " +
                        "JOIN products p ON t.product_id=p.id WHERE p.type = ? AND t.type = 'DEPOSIT' " +
                        "AND t.user_id = ?) " + arguments.get(1) + "(SELECT SUM(amount) FROM transactions" +
                        " t JOIN products p ON t.product_id=p.id WHERE p.type = ? AND t.type = " +
                        "'WITHDRAW' AND t.user_id = ?) THEN 1 ELSE 0 END AS BIT)",
                boolean.class,
                arguments.get(0),
                userId,
                arguments.get(0),
                userId
        ));
    }

    public List<String> getUserInfoByName(String userName) {
        return jdbcTemplate.query("SELECT id, first_name, last_name FROM users u WHERE u.username = ?", (ResultSet rs) -> {
                    List<String> result = new ArrayList<>();
                    rs.next();
                    result.add(rs.getString("id"));
                    result.add(rs.getString("first_name"));
                    result.add(rs.getString("last_name"));
                    if (rs.next()) {
                        return new ArrayList<>();
                    } else {
                        return result;
                    }
                },
                userName
        );
    }

}