package pro.sky.team2.bank_service.service;

import org.springframework.stereotype.Service;
import pro.sky.team2.bank_service.model.Recommendation;
import pro.sky.team2.bank_service.model.Rule;
import pro.sky.team2.bank_service.repository.RecommendationsRepository;

import java.util.List;
import java.util.UUID;

@Service
public class RuleService {

    private final RecommendationsRepository recommendationsRepository;

    public RuleService(RecommendationsRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;
    }

    public List<Recommendation> getAll() {
        return recommendationsRepository.findAll();
    }

    public Recommendation createRecommendation(String name, String text, List<Rule> rules) {
        Recommendation recommendation = new Recommendation(name, text, rules);
        return recommendationsRepository.save(recommendation);
    }

    public void deleteRecommendation(UUID recommendationId) {
        recommendationsRepository.deleteById(recommendationId);
    }
}
