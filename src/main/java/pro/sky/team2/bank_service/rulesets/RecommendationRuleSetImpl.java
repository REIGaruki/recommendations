package pro.sky.team2.bank_service.rulesets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import pro.sky.team2.bank_service.model.Recommendation;
import pro.sky.team2.bank_service.model.Rule;
import pro.sky.team2.bank_service.repository.TransactionsRepository;

import java.util.List;
import java.util.UUID;

@Component
public class RecommendationRuleSetImpl implements RecommendationRuleSet{

    private final TransactionsRepository transactionsRepository;

    @Autowired
    CacheManager cacheManager;

    public RecommendationRuleSetImpl(TransactionsRepository transactionsRepository) {
        this.transactionsRepository = transactionsRepository;
    }

    public boolean checkRuleMatching(Recommendation recommendation, UUID userId) {
        boolean result = true;
            List<Rule> rules = recommendation.getRules();
            for (Rule rule : rules) {
                result = result && checkRule(rule, userId);
            }
        return result;
    }

    @Cacheable(value = "check_rule",  key = "{#userId, #rule.id}")
    private boolean checkRule(Rule rule, UUID userId) {
        if (cacheManager.getCache("check_rule").get("{#userId, #rule.id}") != null) {
            return Boolean.TRUE.equals(cacheManager.getCache("check_rule").get("{#userId, #recommendation.id}"));
        }
        boolean result;
        String query = rule.getQuery();
        List<String> arguments = rule.getArguments();
        result = switch (query) {
            case "USER_OF" -> transactionsRepository.checkUserOf(userId, arguments, 0);
            case "ACTIVE_USER_OF" -> transactionsRepository.checkUserOf(userId, arguments, 5);
            case "TRANSACTION_SUM_COMPARE" -> transactionsRepository.checkTransactionSumCompare(userId, arguments);
            case "TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW" ->
                    transactionsRepository.checkTransactionSumCompareDepositWithdraw(userId, arguments);
            default -> false;
        };
        return rule.isNegate() != result;
    }
}
