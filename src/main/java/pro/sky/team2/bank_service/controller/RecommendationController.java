package pro.sky.team2.bank_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.team2.bank_service.model.Recommendation;
import pro.sky.team2.bank_service.service.RecommendationService;

import java.util.*;

@RestController
@RequestMapping("recommendation")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }


    @GetMapping("{id}")
    public ResponseEntity<Map<UUID,Set<Recommendation>>> getRecomendations(@PathVariable UUID id) {
        Map<UUID, Set<Recommendation>> recommendations = new HashMap<>();
        recommendations.put(id, recommendationService.recommend(id));
        return ResponseEntity.ok(recommendations);
    }

    }
