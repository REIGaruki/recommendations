package pro.sky.team2.bank_service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.team2.bank_service.dto.RecommendationDTO;
import pro.sky.team2.bank_service.dto.RecommendationListDTO;
import pro.sky.team2.bank_service.dto.RecommendationStatsDTO;
import pro.sky.team2.bank_service.exception.WrongArgumentException;
import pro.sky.team2.bank_service.mapper.RecommendationListMapper;
import pro.sky.team2.bank_service.mapper.RecommendationMapper;
import pro.sky.team2.bank_service.model.Recommendation;
import pro.sky.team2.bank_service.model.RecommendationStat;
import pro.sky.team2.bank_service.model.Rule;
import pro.sky.team2.bank_service.querysets.QuerySet;
import pro.sky.team2.bank_service.repository.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RuleService {

    private final Logger logger = LoggerFactory.getLogger(RuleService.class);

    private final RecommendationsRepository recommendationsRepository;

    private final RulesRepository ruleRepository;

    private final StatsRepository statsRepository;

    private final List<QuerySet> querySets;

    private final QueryTypesRepository queryTypesRepository;

    public RuleService(RecommendationsRepository recommendationsRepository, RulesRepository ruleRepository, StatsRepository statsRepository, List<QuerySet> querySets, QueryTypesRepository queryTypesRepository) {
        this.recommendationsRepository = recommendationsRepository;
        this.ruleRepository = ruleRepository;
        this.statsRepository = statsRepository;
        this.querySets = querySets;
        this.queryTypesRepository = queryTypesRepository;
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
            System.out.println(recommendation);
            RecommendationStat stat = new RecommendationStat();
            stat.setRecommendation(recommendation);
            statsRepository.save(stat);
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

    protected boolean checkQuery(Rule rule) {
        try {
            if (!queryTypesRepository.getQueryTypes().contains(rule.getQuery())) {
                throw new WrongArgumentException("Wrong query");
            } else {
                for (QuerySet querySet : querySets) {
                    if (rule.getQuery().equals(querySet.getQueryType())) {
                        return querySet.checkArguments(rule.getArguments());
                    }
                }
            }
        } catch (NumberFormatException | WrongArgumentException exception) {
            logger.error(exception.toString());
            return false;
        }
        return true;
    }

}
