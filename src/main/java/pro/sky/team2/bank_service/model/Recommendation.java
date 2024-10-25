package pro.sky.team2.bank_service.model;

import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Recommendation {

    private String name;

    @Id
    private UUID id;

    private String text;

    private List<Rule> rules;

    public Recommendation(String name, UUID id, String text, List<Rule> rules) {
        this.name = name;
        this.id = id;
        this.text = text;
        this.rules = rules;
    }

    public String getName() {
        return name;
    }

    public UUID getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public List<Rule> getRules() {
        return rules;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recommendation that = (Recommendation) o;
        return Objects.equals(name, that.name) && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id);
    }

    @Override
    public String toString() {
        return "Recommendation{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", text='" + text + '\'' +
                ", rules=" + rules +
                '}';
    }
}
