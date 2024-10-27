package pro.sky.team2.bank_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.team2.bank_service.dto.RecommendationDTO;
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
    public ResponseEntity<Map<String, List<RecommendationDTO>>> getRules() {
        List<RecommendationDTO> data = service.getAll();
        Map<String, List<RecommendationDTO>> response = new HashMap<>();
        response.put("data", data);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<RecommendationDTO> createRulesOfRecommendation(
            @RequestBody RecommendationDTO recommendationDTO) {
        ResponseEntity<RecommendationDTO> responseEntity;
        if (service.createRecommendation(recommendationDTO).isPresent()) {
            responseEntity = ResponseEntity.ok(service.createRecommendation(recommendationDTO).get());
        } else {
           responseEntity = ResponseEntity.badRequest().build();
        }
        return responseEntity;
    }

    @DeleteMapping("/{recommendationId}")
    public ResponseEntity deleteRecommendation(@PathVariable UUID recommendationId) {
        service.deleteRecommendation(recommendationId);
        return ResponseEntity.noContent().build();
    }

}
