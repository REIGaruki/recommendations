package pro.sky.team2.bank_service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import pro.sky.team2.bank_service.model.Rule;

import java.util.List;
import java.util.UUID;

public class RecommendationDTO {
    @JsonProperty("product_name")
    private String name;

    private UUID id;

    @JsonProperty("product_id")
    private UUID productId;

    @JsonProperty("product_text")
    private String text;

    @JsonProperty("rule")
    private List<Rule> rules;

    public RecommendationDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }
}
