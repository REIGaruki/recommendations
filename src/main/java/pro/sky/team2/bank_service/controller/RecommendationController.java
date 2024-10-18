package pro.sky.team2.bank_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.team2.bank_service.model.Recommendation;
import pro.sky.team2.bank_service.service.RecommendationService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("recommendation")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }


    @GetMapping("{id}")
    public ResponseEntity<Map<UUID,List<Recommendation>>> getRecomendations(@PathVariable UUID id) {
        Map<UUID,List<Recommendation>> recommendations = new HashMap<>();
        recommendations.put(id, recommendationService.recommend(id));
        return ResponseEntity.ok(recommendations);
    }

    }
