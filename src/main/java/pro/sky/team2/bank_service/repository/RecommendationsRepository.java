package pro.sky.team2.bank_service.repository;

import org.springframework.stereotype.Repository;
import pro.sky.team2.bank_service.model.Recommendation;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class RecommendationsRepository {

    private final List<Recommendation> recommendations = new ArrayList<>(List.of(
            new Recommendation("Invest 500", UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a"), "Invest 500 description"),
            new Recommendation("Simple credit", UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f"), "Simple credit description"),
            new Recommendation("Top saving", UUID.fromString("59efc529-2fff-41af-baff-90ccd7402925"), "Top saving description")
    ));

    public RecommendationsRepository() {
    }

    public Recommendation findById(UUID id) {
        for (Recommendation recommendation : recommendations) {
            if (recommendation.getId().equals(id)) {
                return recommendation;
            }
        }
        return null;
    }
}
