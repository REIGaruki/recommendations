package pro.sky.team2.bank_service.rulesets;

import org.springframework.stereotype.Component;
import pro.sky.team2.bank_service.model.Recommendation;
import pro.sky.team2.bank_service.repository.RecommendationsRepository;
import pro.sky.team2.bank_service.repository.TransactionsRepository;

import java.util.Optional;
import java.util.UUID;

@Component
public class TopSavingRuleSet implements RecommendationRuleSet{

    private final TransactionsRepository transactionsRepository;

    private final RecommendationsRepository recommendationsRepository;

    public TopSavingRuleSet(TransactionsRepository transactionsRepository,
                            RecommendationsRepository recommendationsRepository) {
        this.transactionsRepository = transactionsRepository;
        this.recommendationsRepository = recommendationsRepository;
    }

    private final int MINIMAL_DEBIT_DEPOSIT_AMOUNT = 50_000;

    private final int MINIMAL_SAVING_DEPOSIT_AMOUNT = 50_000;

    @Override
    public Optional<Recommendation> checkRuleMatching(UUID userID) {
        if (checkRuleOne(userID) && checkRuleTwo(userID) && checkRuleThree(userID)) {
            //Если выполняются все 3 условия, возвращаем рекомендацию "Top saving"
            return Optional.ofNullable(recommendationsRepository.findById(UUID.fromString("59efc529-2fff-41af-baff-90ccd7402925")));
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

    /*Сумма пополнений по всем продуктам типа DEBIT больше или равна MINIMAL_DEBIT_DEPOSIT_AMOUNT ₽ ИЛИ
        Сумма пополнений по всем продуктам типа SAVING больше или равна MINIMAL_SAVING_DEPOSIT_AMOUNT ₽.*/
    private boolean checkRuleTwo(UUID userID) {
        //Получаем сумму пополнений по всем продуктам типа DEBIT
        return transactionsRepository.getDebitDepositTransactionAmount(userID) >= MINIMAL_DEBIT_DEPOSIT_AMOUNT ||
        //Получаем сумму пополнений по всем продуктам типа SAVING
        transactionsRepository.getSavingDepositTransactionsAmount(userID) >= MINIMAL_SAVING_DEPOSIT_AMOUNT;
    }

    //Сумма пополнений по всем продуктам типа DEBIT больше, чем сумма трат по всем продуктам типа DEBIT.
    private boolean checkRuleThree(UUID userID) {
        //Получаем сумму пополнений по всем продуктам типа DEBIT
        return transactionsRepository.getDebitDepositTransactionAmount(userID) >
        //Получаем сумму операций трат типа DEBIT
        transactionsRepository.getDebitWithdrawTransactionAmount(userID);
    }
}
