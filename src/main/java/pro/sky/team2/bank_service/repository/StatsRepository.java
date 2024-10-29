package pro.sky.team2.bank_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.team2.bank_service.model.RecommendationStat;

import java.util.UUID;

@Repository
public interface StatsRepository extends JpaRepository<RecommendationStat, UUID> {
}
