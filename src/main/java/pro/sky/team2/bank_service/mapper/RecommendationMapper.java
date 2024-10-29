package pro.sky.team2.bank_service.mapper;

import pro.sky.team2.bank_service.dto.RecommendationDTO;
import pro.sky.team2.bank_service.dto.RecommendationForUserDTO;
import pro.sky.team2.bank_service.model.Recommendation;

public class RecommendationMapper {

    public static RecommendationForUserDTO mapToUserDTO(Recommendation recommendation) {
        RecommendationForUserDTO recommendationForUserDTO = new RecommendationForUserDTO();
        recommendationForUserDTO.setProductId(recommendation.getProductId());
        recommendationForUserDTO.setName(recommendation.getName());
        recommendationForUserDTO.setText(recommendation.getText());
        return recommendationForUserDTO;
    }

    public static RecommendationDTO mapToDTO(Recommendation recommendation) {
        RecommendationDTO recommendationDTO = new RecommendationDTO();
        recommendationDTO.setId(recommendation.getId());
        recommendationDTO.setName(recommendation.getName());
        recommendationDTO.setText(recommendation.getText());
        recommendationDTO.setRules(recommendation.getRules());
        recommendationDTO.setProductId(recommendation.getProductId());
        return recommendationDTO;
    }

    public static Recommendation mapFromDTO(RecommendationDTO recommendationDTO) {
        Recommendation recommendation = new Recommendation();
        recommendation.setId(recommendationDTO.getId());
        recommendation.setName(recommendationDTO.getName());
        recommendation.setText(recommendationDTO.getText());
        recommendation.setRules(recommendationDTO.getRules());
        recommendation.setProductId(recommendationDTO.getProductId());
        return recommendation;
    }
}
