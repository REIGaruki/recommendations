package pro.sky.team2.bank_service.service;

import org.springframework.stereotype.Service;
import pro.sky.team2.bank_service.model.Recommendation;
import pro.sky.team2.bank_service.rulesets.RecommendationRuleSet;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    private final List<RecommendationRuleSet> ruleSets;

    public RecommendationService(List<RecommendationRuleSet> ruleSets) {
        this.ruleSets = ruleSets;
    }

    public List<Recommendation> recommend(UUID userID) {
        return ruleSets.stream().filter(ruleSet -> ruleSet.checkRuleMatching(userID).isPresent())
                .map(ruleSet -> ruleSet.checkRuleMatching(userID).get())
                .collect(Collectors.toList());
    }
}
