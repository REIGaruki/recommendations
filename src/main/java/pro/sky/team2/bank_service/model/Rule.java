package pro.sky.team2.bank_service.model;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "rule")
public class Rule {

    private String query;

    private String arguments;

    private boolean negate;

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "recommendation_id")
    private Recommendation recommendation;

    public Rule() {
    }

    public String getQuery() {
        return query;
    }

    public String getArguments() {
        return arguments;
    }

    public boolean isNegate() {
        return negate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rule rule = (Rule) o;
        return negate == rule.negate && Objects.equals(query, rule.query) && Objects.equals(arguments, rule.arguments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(query, arguments, negate);
    }
}