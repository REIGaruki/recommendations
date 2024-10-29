package pro.sky.team2.bank_service.service;

import org.springframework.stereotype.Service;
import pro.sky.team2.bank_service.dto.RecommendationForUserDTO;
import pro.sky.team2.bank_service.mapper.RecommendationMapper;
import pro.sky.team2.bank_service.model.Recommendation;
import pro.sky.team2.bank_service.model.RecommendationStat;
import pro.sky.team2.bank_service.repository.RecommendationsRepository;
import pro.sky.team2.bank_service.repository.StatsRepository;
import pro.sky.team2.bank_service.rulesets.RecommendationRuleSet;

import java.util.*;

@Service
public class RecommendationService {

    private final RecommendationsRepository recommendationsRepository;

    private final RecommendationRuleSet ruleSet;

    private final StatsRepository statsRepository;

    public RecommendationService(RecommendationsRepository recommendationsRepository, RecommendationRuleSet ruleSet, StatsRepository statsRepository) {
        this.recommendationsRepository = recommendationsRepository;
        this.ruleSet = ruleSet;
        this.statsRepository = statsRepository;
    }

    public Set<RecommendationForUserDTO> recommend(UUID userID) {
        HashSet<RecommendationForUserDTO> recommendationForUserDTOS = new HashSet<>();
        for (Recommendation recommendation : recommendationsRepository.findAll()) {
            if (ruleSet.checkRuleMatching(recommendation, userID)) {
                Optional<RecommendationStat> stat = statsRepository.findById(recommendation.getId());
                if (stat.isPresent()) {
                    RecommendationStat newStat = stat.get();
                    newStat.setCounter(newStat.getCounter() + 1);
                    statsRepository.save(newStat);
                }
                recommendationForUserDTOS.add(RecommendationMapper.mapToUserDTO(recommendation));
            }
        }
        return recommendationForUserDTOS;
    }
}
