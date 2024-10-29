package pro.sky.team2.bank_service.service;

import org.springframework.stereotype.Service;
import pro.sky.team2.bank_service.dto.RecommendationDTO;
import pro.sky.team2.bank_service.dto.RecommendationListDTO;
import pro.sky.team2.bank_service.dto.RecommendationStatsDTO;
import pro.sky.team2.bank_service.exception.WrongArgumentException;
import pro.sky.team2.bank_service.mapper.RecommendationListMapper;
import pro.sky.team2.bank_service.mapper.RecommendationMapper;
import pro.sky.team2.bank_service.model.Recommendation;
import pro.sky.team2.bank_service.model.Rule;
import pro.sky.team2.bank_service.repository.ArgumentsRepository;
import pro.sky.team2.bank_service.repository.RecommendationsRepository;
import pro.sky.team2.bank_service.repository.RulesRepository;
import pro.sky.team2.bank_service.repository.StatsRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RuleService {

    private final RecommendationsRepository recommendationsRepository;

    private final RulesRepository ruleRepository;

    private final StatsRepository statsRepository;

    public RuleService(RecommendationsRepository recommendationsRepository, RulesRepository ruleRepository, ArgumentsRepository argumentsRepository, StatsRepository statsRepository) {
        this.recommendationsRepository = recommendationsRepository;
        this.ruleRepository = ruleRepository;
        this.statsRepository = statsRepository;
    }

    public RecommendationListDTO getAll() {
        List<Recommendation> recommendations = recommendationsRepository.findAll();
        return RecommendationListMapper.mapToDTO(recommendations);
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
        if (recommendationsRepository.findById(recommendationId).isPresent()) {
        recommendationsRepository.deleteById(recommendationId);
        }
    }

    public Set<RecommendationStatsDTO> getStats() {
        return statsRepository.findAll().stream()
                .map(RecommendationMapper::mapToStatsDTO)
                .collect(Collectors.toSet());
    }

    protected boolean checkQuery(Rule rule) throws NumberFormatException{
        try {
            String query = rule.getQuery();
            if (!List.of(ArgumentsRepository.QUERIES).contains(query)) {
                throw new WrongArgumentException("Wrong query");
            } else if (!List.of(ArgumentsRepository.PRODUCT_TYPES).contains(rule.getArguments().get(0))) {
                throw new WrongArgumentException("Wrong product type");
            } else if (query.equals("TRANSACTION_SUM_COMPARE")) {
                if (!List.of(ArgumentsRepository.TRANSACTION_TYPES).contains(rule.getArguments().get(1))) {
                    throw new WrongArgumentException("Wrong transaction");
                } else if (!List.of(ArgumentsRepository.RELATION_OPERATORS).contains(rule.getArguments().get(2))) {
                    throw new WrongArgumentException("Wrong operator");
                } else if (Integer.parseInt(rule.getArguments().get(3)) < 0) {
                    throw new WrongArgumentException("Wrong number");
                }
            } else if (query.equals("TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW")) {
                if (!List.of(ArgumentsRepository.RELATION_OPERATORS).contains(rule.getArguments().get(1))) {
                    throw new WrongArgumentException("Wrong operator");
                }
            }
        } catch (NumberFormatException | WrongArgumentException numberFormatException) {
            return false;
        }
        return true;
    }


}
