package pro.sky.team2.bank_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.team2.bank_service.model.Rule;

import java.util.UUID;

@Repository
public interface RulesRepository extends JpaRepository<Rule, UUID> {
}