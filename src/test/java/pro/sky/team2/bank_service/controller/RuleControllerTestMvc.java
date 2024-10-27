package pro.sky.team2.bank_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import pro.sky.team2.bank_service.dto.RecommendationDTO;
import pro.sky.team2.bank_service.dto.RecommendationListDTO;
import pro.sky.team2.bank_service.mapper.RecommendationListMapper;
import pro.sky.team2.bank_service.mapper.RecommendationMapper;
import pro.sky.team2.bank_service.model.Recommendation;
import pro.sky.team2.bank_service.model.Rule;
import pro.sky.team2.bank_service.service.RuleService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(RuleController.class)
public class RuleControllerTestMvc {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RuleService service;

    private final UUID RECOMMENDATION_ID = UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f");

    private final String RECOMMENDATION_NAME = "Name";

    private final String RECOMMENDATION_TEXT = "Description text";

    private final List<Rule> RULES = new ArrayList<>(List.of(
            new Rule("USER_OF", List.of("DEBIT"), false),
            new Rule("USER_OF", List.of("INVEST"), true)
    ));

    @Test
    @DisplayName("Should get all recommendations")
    void getTest() throws Exception {
        List<Recommendation> expected = new ArrayList<>();
        Recommendation rec1 = new Recommendation(RECOMMENDATION_NAME, RECOMMENDATION_TEXT, RULES);
        Recommendation rec2 = new Recommendation(RECOMMENDATION_NAME, RECOMMENDATION_TEXT, RULES);
        rec1.setId(UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a"));
        rec2.setId(UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f"));
        rec1.setProductId(UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a"));
        rec2.setProductId(UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f"));
        expected.add(rec1);
        expected.add(rec2);
        RecommendationListDTO actual = RecommendationListMapper.mapToDTO(expected);
        when(service.getAll()).thenReturn(actual);
        ResultActions perform = mockMvc.perform(get("/rule")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(expected)));
        perform.andExpect(jsonPath("data[0].id").exists())
                .andExpect(jsonPath("data[0].product_name").value(expected.get(0).getName()))
                .andExpect(jsonPath("data[0].product_text").value(expected.get(0).getText()))
                .andExpect(jsonPath("data[0].product_id").value(expected.get(0).getProductId().toString()))
                .andExpect(jsonPath("data[0].rule[0].query").value(expected.get(0).getRules().get(0).getQuery()))
                .andExpect(jsonPath("data[0].rule[0].arguments[0]").value(expected.get(0).getRules().get(0).getArguments().get(0)))
                .andExpect(jsonPath("data[0].rule[0].negate").value(expected.get(0).getRules().get(0).isNegate()))
                .andDo(print());
    }
}