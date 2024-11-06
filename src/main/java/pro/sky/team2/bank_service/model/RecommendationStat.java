package pro.sky.team2.bank_service.model;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Entity
public class RecommendationStat {

    @Id
    UUID id;

    @OneToOne
    @MapsId("id")
    private Recommendation recommendation;

    private int counter;

    public RecommendationStat() {
    }

    public Recommendation getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(Recommendation recommendation) {
        this.recommendation = recommendation;
        this.id = recommendation.getId();
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecommendationStat that = (RecommendationStat) o;
        return counter == that.counter && Objects.equals(recommendation, that.recommendation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recommendation, counter);
    }

    @Override
    public String toString() {
        return "RecommendationStat{" +
                "recommendation=" + recommendation.getId() +
                ", counter=" + counter +
                '}';
    }
}
