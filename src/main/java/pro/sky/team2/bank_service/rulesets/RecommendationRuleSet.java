package pro.sky.team2.bank_service.rulesets;

import pro.sky.team2.bank_service.model.Recommendation;

import java.util.Optional;
import java.util.UUID;

public interface RecommendationRuleSet {
    //интерфейс получает id пользователя и возвращает объект рекомендации или null
    Optional<Recommendation> checkRuleMatching(UUID userID);
}