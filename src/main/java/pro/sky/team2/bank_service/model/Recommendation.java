package pro.sky.team2.bank_service.model;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;


import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "recommendation")
public class Recommendation {

    private String name;

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    private UUID productId;

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    private String text;

    @OneToMany(mappedBy = "recommendation")
    private List<Rule> rules;

    public Recommendation() {
    }

    public Recommendation(String name, String text, List<Rule> rules) {
        this.name = name;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recommendation that = (Recommendation) o;
        return Objects.equals(name, that.name) && Objects.equals(productId, that.productId) && Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, productId, text);
    }

    @Override
    public String toString() {
        return "Recommendation{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", productId=" + productId +
                ", text='" + text + '\'' +
                ", rules=" + rules +
                '}';
    }
}
