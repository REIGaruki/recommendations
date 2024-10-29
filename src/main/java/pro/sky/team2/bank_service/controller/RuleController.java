package pro.sky.team2.bank_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.team2.bank_service.dto.RecommendationDTO;
import pro.sky.team2.bank_service.dto.RecommendationListDTO;
import pro.sky.team2.bank_service.dto.RecommendationStatsDTO;
import pro.sky.team2.bank_service.service.RuleService;

import java.util.*;

@RestController
@RequestMapping("/rule")
public class RuleController {

    private final RuleService service;

    public RuleController(RuleService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<RecommendationListDTO> getRules() {
        RecommendationListDTO data = service.getAll();
        return ResponseEntity.ok(data);
    }

    @PostMapping
    public ResponseEntity<RecommendationDTO> createRulesOfRecommendation(
            @RequestBody RecommendationDTO recommendationDTO) {
        RecommendationDTO response = service.createRecommendation(recommendationDTO).orElse(null);
        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{recommendationId}")
    public ResponseEntity<Object> deleteRecommendation(@PathVariable UUID recommendationId) {
        service.deleteRecommendation(recommendationId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/stats")
    public Set<RecommendationStatsDTO> getStats() {
        return service.getStats();
    }

}
