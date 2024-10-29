package pro.sky.team2.bank_service.service;

import org.springframework.stereotype.Service;
import pro.sky.team2.bank_service.dto.RecommendationForUserDTO;
import pro.sky.team2.bank_service.mapper.RecommendationMapper;
import pro.sky.team2.bank_service.model.Recommendation;
import pro.sky.team2.bank_service.repository.RecommendationsRepository;
import pro.sky.team2.bank_service.rulesets.RecommendationRuleSet;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    private final RecommendationsRepository recommendationsRepository;

    private final RecommendationRuleSet ruleSet;

    public RecommendationService(RecommendationsRepository recommendationsRepository, RecommendationRuleSet ruleSet) {
        this.recommendationsRepository = recommendationsRepository;
        this.ruleSet = ruleSet;
    }

    public Set<RecommendationForUserDTO> recommend(UUID userID) {
        List<Recommendation> recommendations = recommendationsRepository.findAll();
        HashSet<RecommendationForUserDTO> result = recommendations.stream()
                .filter(recommendation -> ruleSet.checkRuleMatching(recommendation, userID))
                .map(RecommendationMapper::mapToUserDTO).collect(Collectors.toCollection(HashSet::new));
        return result;
    }
}
