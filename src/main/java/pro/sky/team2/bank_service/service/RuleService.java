package pro.sky.team2.bank_service.service;

import org.springframework.stereotype.Service;
import pro.sky.team2.bank_service.dto.RecommendationDTO;
import pro.sky.team2.bank_service.dto.RecommendationListDTO;
import pro.sky.team2.bank_service.mapper.RecommendationListMapper;
import pro.sky.team2.bank_service.mapper.RecommendationMapper;
import pro.sky.team2.bank_service.model.Recommendation;
import pro.sky.team2.bank_service.model.Rule;
import pro.sky.team2.bank_service.repository.RecommendationsRepository;
import pro.sky.team2.bank_service.repository.RulesRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RuleService {

    private final RecommendationsRepository recommendationsRepository;

    private final RulesRepository ruleRepository;

    public RuleService(RecommendationsRepository recommendationsRepository, RulesRepository ruleRepository) {
        this.recommendationsRepository = recommendationsRepository;
        this.ruleRepository = ruleRepository;
    }

    public RecommendationListDTO getAll() {
        List<Recommendation> recommendations = recommendationsRepository.findAll();
        RecommendationListDTO recommendationDTOs = RecommendationListMapper.mapToDTO(recommendations);
        return recommendationDTOs;
    }

    public Optional<RecommendationDTO> createRecommendation(RecommendationDTO recommendationDTO) {
        Recommendation recommendation = RecommendationMapper.mapFromDTO(recommendationDTO);
        List<Rule> rules = recommendation.getRules();
        recommendation= recommendationsRepository.save(recommendation);
        for (Rule rule : rules){
            rule.setRecommendation(recommendation);
            ruleRepository.save(rule);
        }
        recommendation.setRules(rules);
        return Optional.of(RecommendationMapper.mapToDTO(recommendation));
    }

    public void deleteRecommendation(UUID recommendationId) {
        recommendationsRepository.deleteById(recommendationId);
    }


}
