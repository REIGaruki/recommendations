package pro.sky.team2.bank_service.repository;

import org.springframework.stereotype.Repository;
import pro.sky.team2.bank_service.model.Recommendation;
import pro.sky.team2.bank_service.model.Rule;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class RecommendationsRepository {

    private final String INVEST_500_DESCRIPTION = "Откройте свой путь к успеху с индивидуальным инвестиционным " +
            "счетом (ИИС) от нашего банка! Воспользуйтесь налоговыми льготами и начните инвестировать с умом. " +
            "Пополните счет до конца года и получите выгоду в виде вычета на взнос в следующем налоговом периоде. " +
            "Не упустите возможность разнообразить свой портфель, снизить риски и следить за актуальными рыночными " +
            "тенденциями. Откройте ИИС сегодня и станьте ближе к финансовой независимости!";

    private final String TOP_SAVING_DESCRIPTION = "Откройте свою собственную «Копилку» с нашим банком! " +
            "«Копилка» — это уникальный банковский инструмент, который поможет вам легко и удобно накапливать " +
            "деньги на важные цели. Больше никаких забытых чеков и потерянных квитанций — всё под контролем!\n" +
            "\n" +
            "Преимущества «Копилки»:\n" +
            "\n" +
            "Накопление средств на конкретные цели. Установите лимит и срок накопления, и банк будет автоматически " +
            "переводить определенную сумму на ваш счет.\n" +
            "\n" +
            "Прозрачность и контроль. Отслеживайте свои доходы и расходы, контролируйте процесс накопления и " +
            "корректируйте стратегию при необходимости.\n" +
            "\n" +
            "Безопасность и надежность. Ваши средства находятся под защитой банка, а доступ к ним возможен только" +
            " через мобильное приложение или интернет-банкинг.\n" +
            "\n" +
            "Начните использовать «Копилку» уже сегодня и станьте ближе к своим финансовым целям!";

    private final String SIMPLE_CREDIT_DESCRIPTION = "Откройте мир выгодных кредитов с нами!\n" +
            "\n" +
            "Ищете способ быстро и без лишних хлопот получить нужную сумму? Тогда наш выгодный кредит — именно то, " +
            "что вам нужно! Мы предлагаем низкие процентные ставки, гибкие условия и индивидуальный подход к " +
            "каждому клиенту.\n" +
            "\n" +
            "Почему выбирают нас:\n" +
            "\n" +
            "Быстрое рассмотрение заявки. Мы ценим ваше время, поэтому процесс рассмотрения заявки занимает " +
            "всего несколько часов.\n" +
            "\n" +
            "Удобное оформление. Подать заявку на кредит можно онлайн на нашем сайте или в мобильном приложении.\n" +
            "\n" +
            "Широкий выбор кредитных продуктов. Мы предлагаем кредиты на различные цели: покупку недвижимости, " +
            "автомобиля, образование, лечение и многое другое.\n" +
            "\n" +
            "Не упустите возможность воспользоваться выгодными условиями кредитования от нашей компании!";

    private final Rule creditRuleOne = new Rule(
            "USER_OF",
            List.of(
                    "CREDIT"
            ),
            true
    );

    private final Rule creditRuleTwo = new Rule(
            "TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW",
            List.of(
                    "DEBIT",
                    ">"
            ),
            false
    );

    private final Rule creditRuleThree = new Rule(
            "TRANSACTION_SUM_COMPARE",
            List.of(
                    "DEBIT",
                    "DEPOSIT",
                    ">",
                    "100000"
            ),
            false
    );

    private final Rule investRuleOne = new Rule(
            "USER_OF",
            List.of(
                    "DEBIT"
            ),
            false
    );

    private final Rule investRuleTwo = new Rule(
            "USER_OF",
            List.of(
                    "INVEST"
            ),
            true
    );

    private final Rule investRuleThree = new Rule(
            "TRANSACTION_SUM_COMPARE",
            List.of(
                    "SAVING",
                    "DEPOSIT",
                    ">",
                    "1000"
            ),
            false
    );

    private final Rule savingRuleOne = new Rule(
            "USER_OF",
            List.of(
                    "DEBIT"
            ),
            false
    );

    private final Rule savingRuleTwo = new Rule(
            "TRANSACTION_SUM_COMPARE",
            List.of(
                    "DEBIT",
                    "DEPOSIT",
                    ">=",
                    "50000"
            ),
            false
    );

    private final Rule savingRuleTwoTwo = new Rule(
            "TRANSACTION_SUM_COMPARE",
            List.of(
                    "SAVING",
                    "DEPOSIT",
                    ">=",
                    "50000"
            ),
            false
    );

    private final Rule savingRuleThree = new Rule(
            "TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW",
            List.of(
                    "DEBIT",
                    ">"
            ),
            false
    );

    private final List<Recommendation> recommendations = new ArrayList<>(List.of(
            new Recommendation("Invest 500", UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a"), INVEST_500_DESCRIPTION, List.of(investRuleOne, investRuleTwo, investRuleThree)),
            new Recommendation("Simple credit", UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f"), SIMPLE_CREDIT_DESCRIPTION, List.of(creditRuleOne, creditRuleTwo, creditRuleThree)),
            new Recommendation("Top saving", UUID.fromString("59efc529-2fff-41af-baff-90ccd7402925"), TOP_SAVING_DESCRIPTION, List.of(savingRuleOne, savingRuleTwo, savingRuleThree)),
            new Recommendation("Top saving", UUID.fromString("59efc529-2fff-41af-baff-90ccd7402925"), TOP_SAVING_DESCRIPTION, List.of(savingRuleOne, savingRuleTwoTwo, savingRuleThree))
    ));

    public RecommendationsRepository() {
    }

    public Recommendation findById(UUID id) {
        return recommendations.stream()
                .filter(recommendation -> recommendation.getId().equals(id)).findFirst()
                .orElse(null);
    }

    public List<Recommendation> getAll() {
        return recommendations;
    }
}
