package pro.sky.team2.bank_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.team2.bank_service.service.RecommendationService;

import java.util.*;

@RestController
@RequestMapping("recommendation")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }


    @GetMapping("{user_id}")
    public ResponseEntity<Map<String, Object>> getRecomendations(@PathVariable("user_id") UUID userId) {
        Map<String, Object> recommendations = new HashMap<>();
        recommendations.put("user_id", userId);
        recommendations.put("recommendations", recommendationService.recommend(userId));
        return ResponseEntity.ok(recommendations);
    }

    }
