package pro.sky.team2.bank_service.service;

import org.springframework.stereotype.Service;
import pro.sky.team2.bank_service.dto.RecommendationDTO;
import pro.sky.team2.bank_service.dto.RecommendationListDTO;
import pro.sky.team2.bank_service.mapper.RecommendationListMapper;
import pro.sky.team2.bank_service.mapper.RecommendationMapper;
import pro.sky.team2.bank_service.model.Recommendation;
import pro.sky.team2.bank_service.model.Rule;
import pro.sky.team2.bank_service.repository.ArgumentsRepository;
import pro.sky.team2.bank_service.repository.RecommendationsRepository;
import pro.sky.team2.bank_service.repository.RulesRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RuleService {

    private final RecommendationsRepository recommendationsRepository;

    private final RulesRepository ruleRepository;

    private final ArgumentsRepository argumentsRepository;

    public RuleService(RecommendationsRepository recommendationsRepository, RulesRepository ruleRepository, ArgumentsRepository argumentsRepository) {
        this.recommendationsRepository = recommendationsRepository;
        this.ruleRepository = ruleRepository;
        this.argumentsRepository = argumentsRepository;
    }

    public RecommendationListDTO getAll() {
        List<Recommendation> recommendations = recommendationsRepository.findAll();
        RecommendationListDTO recommendationDTOs = RecommendationListMapper.mapToDTO(recommendations);
        return recommendationDTOs;
    }

    public Optional<RecommendationDTO> createRecommendation(RecommendationDTO recommendationDTO) {
        Recommendation recommendation = RecommendationMapper.mapFromDTO(recommendationDTO);
        Optional<RecommendationDTO> result;
        List<Rule> rules = recommendation.getRules();
        boolean allIsOk = true;
        for (Rule rule : rules) {
            allIsOk = allIsOk && checkQuery(rule);
        }
        if (allIsOk) {
            recommendation = recommendationsRepository.save(recommendation);
            for (Rule rule : rules) {
                rule.setRecommendation(recommendation);
                ruleRepository.save(rule);
            }
            recommendation.setRules(rules);
            result = Optional.of(RecommendationMapper.mapToDTO(recommendation));
        } else {
            result = Optional.empty();
        }
        return result;
    }

    public void deleteRecommendation(UUID recommendationId) {
        recommendationsRepository.deleteById(recommendationId);
    }

    private boolean checkQuery(Rule rule) {
        String query = rule.getQuery();
        if (!List.of(ArgumentsRepository.QUERIES).contains(query)) {
            return false;
        } else if (!List.of(ArgumentsRepository.PRODUCT_TYPES).contains(rule.getArguments().get(0))) {
            return false;
        } else if (query.equals("TRANSACTION_SUM_COMPARE")) {
            if (!List.of(ArgumentsRepository.TRANSACTION_TYPES).contains(rule.getArguments().get(1))) {
                return false;
            } else if (!List.of(ArgumentsRepository.RELATION_OPERATORS).contains(rule.getArguments().get(2))) {
                return false;
            } else if (Integer.parseInt(rule.getArguments().get(3)) < 0) {
                return false;
            }
        } else if (query.equals("TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW")) {
            if (!List.of(ArgumentsRepository.RELATION_OPERATORS).contains(rule.getArguments().get(1))) {
                return false;
            }
        }
        return true;
    }


}
