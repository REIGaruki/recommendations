package pro.sky.team2.bank_service.service;

import org.springframework.stereotype.Service;
import pro.sky.team2.bank_service.model.Recommendation;
import pro.sky.team2.bank_service.model.Rule;
import pro.sky.team2.bank_service.repository.RecommendationsRepository;
import pro.sky.team2.bank_service.repository.RulesRepository;

import java.util.List;
import java.util.UUID;

@Service
public class RuleService {

    private final RecommendationsRepository recommendationsRepository;

    private final RulesRepository ruleRepository;

    public RuleService(RecommendationsRepository recommendationsRepository, RulesRepository ruleRepository) {
        this.recommendationsRepository = recommendationsRepository;
        this.ruleRepository = ruleRepository;
    }

    public List<Recommendation> getAll() {
        return recommendationsRepository.findAll();
    }

    public Recommendation createRecommendation(String name, String text, List<Rule> rules) {
        Recommendation recommendation = new Recommendation(name, text, rules);
        recommendationsRepository.save(recommendation);
        for (Rule rule : rules){
            rule.setRecommendation(recommendation);
            ruleRepository.save(rule);
        }
        return recommendation;
    }

    public void deleteRecommendation(UUID recommendationId) {
        recommendationsRepository.deleteById(recommendationId);
    }
}
