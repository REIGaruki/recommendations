package pro.sky.team2.bank_service.rulesets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import pro.sky.team2.bank_service.model.Recommendation;
import pro.sky.team2.bank_service.model.Rule;
import pro.sky.team2.bank_service.querysets.QuerySet;
import pro.sky.team2.bank_service.repository.TransactionsRepository;

import java.util.List;
import java.util.UUID;

@Component
public class RecommendationRuleSetImpl implements RecommendationRuleSet{

    private final TransactionsRepository transactionsRepository;

    private final List<QuerySet> querySets;

    @Autowired
    CacheManager cacheManager;

    public RecommendationRuleSetImpl(TransactionsRepository transactionsRepository, List<QuerySet> querySets) {
        this.transactionsRepository = transactionsRepository;
        this.querySets = querySets;
    }

    @Cacheable(value = "check_rule",  key = "{#userId, #recommendation.id}")
    public boolean checkRuleMatching(Recommendation recommendation, UUID userId) {
        boolean result = true;
            for (Rule rule : recommendation.getRules()) {
                result = result && checkRule(rule, userId);
            }
        return result;
    }

    protected boolean checkRule(Rule rule, UUID userId) {
        for (QuerySet querySet : querySets) {
            if (querySet.getQueryType().equals(rule.getQuery())) {
                return querySet.checkRule(rule.getArguments(), userId) != rule.isNegate();
            }
        }
        return false;
    }
}
