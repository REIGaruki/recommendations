package pro.sky.team2.bank_service.rulesets;

import org.springframework.stereotype.Component;
import pro.sky.team2.bank_service.model.Recommendation;
import pro.sky.team2.bank_service.repository.RecommendationsRepository;
import pro.sky.team2.bank_service.repository.TransactionsRepository;

import java.util.Optional;
import java.util.UUID;

@Component
public class Invest500RuleSet implements RecommendationRuleSet{

    private final TransactionsRepository transactionsRepository;

    private final RecommendationsRepository recommendationsRepository;

    public Invest500RuleSet(TransactionsRepository transactionsRepository,
                            RecommendationsRepository recommendationsRepository) {
        this.transactionsRepository = transactionsRepository;
        this.recommendationsRepository = recommendationsRepository;
    }

    private final int MINIMAL_SAVING_DEPOSIT_AMOUNT = 1000;

    @Override
    public Optional<Recommendation> checkRuleMatching(UUID userID) {
        if (checkRuleOne(userID) && checkRuleTwo(userID) && checkRuleThree(userID)) {
            //Если выполняются все 3 условия, возвращаем рекомендацию "Invest 500"
            return Optional.ofNullable(recommendationsRepository.findById(UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a")));
        } else {
            //Если хотя бы одно не выполняется, возвращаем null
            return Optional.empty();
        }
    }

    //Пользователь использует как минимум один продукт с типом DEBIT.
    private boolean checkRuleOne(UUID userID) {
        //Получаем количество транзакций типа DEBIT по UUID пользователя
        return transactionsRepository.getDebitTransactionsCount(userID) > 0;
    }

    //Пользователь не использует продукты с типом INVEST.
    private boolean checkRuleTwo(UUID userID) {
        //Получаем количество транзакций типа INVEST по UUID пользователя
        return transactionsRepository.getInvestTransactionsCount(userID) == 0;
    }

    //Сумма пополнений продуктов с типом SAVING больше чем MINIMAL_SAVING_DEPOSIT_AMOUNT ₽.
    private boolean checkRuleThree(UUID userID) {
        //Получаем сумму пополнений продуктов с типом SAVING
        return transactionsRepository.getSavingDepositTransactionsAmount(userID) > MINIMAL_SAVING_DEPOSIT_AMOUNT;
    }
}
