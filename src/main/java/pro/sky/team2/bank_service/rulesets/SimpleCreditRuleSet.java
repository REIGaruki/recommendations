package pro.sky.team2.bank_service.rulesets;

import org.springframework.stereotype.Component;
import pro.sky.team2.bank_service.model.Recommendation;
import pro.sky.team2.bank_service.repository.RecommendationsRepository;
import pro.sky.team2.bank_service.repository.TransactionsRepository;

import java.util.Optional;
import java.util.UUID;

@Component
public class SimpleCreditRuleSet implements RecommendationRuleSet{

    private final TransactionsRepository transactionsRepository;

    private final RecommendationsRepository recommendationsRepository;

    public SimpleCreditRuleSet(TransactionsRepository transactionsRepository,
                               RecommendationsRepository recommendationsRepository) {
        this.transactionsRepository = transactionsRepository;
        this.recommendationsRepository = recommendationsRepository;
    }

    private final int MINIMAL_DEBIT_WITHDRAW_AMOUNT = 100_000;

    @Override
    public Optional<Recommendation> checkRuleMatching(UUID userID) {
        if (checkRuleOne(userID) && checkRuleTwo(userID) && checkRuleThree(userID)) {
            //Если выполняются все 3 условия, возвращаем рекомендацию "Простой кредит"
            return Optional.ofNullable(recommendationsRepository.findById(UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f")));
        } else {
            //Если хотя бы одно не выполняется, возвращаем null
            return Optional.empty();
        }
    }

    //Пользователь не использует продукты с типом CREDIT.
    private boolean checkRuleOne(UUID userID) {
        //Получаем количество транзакций типа CREDIT по UUID пользователя
        return transactionsRepository.getCreditTransactionsCount(userID) == 0;
    }

    //Сумма пополнений по всем продуктам типа DEBIT больше, чем сумма трат по всем продуктам типа DEBIT.
    private boolean checkRuleTwo(UUID userID) {
        //Получаем сумму операций пополнений типа DEBIT
        return transactionsRepository.getDebitDepositTransactionAmount(userID) >
        //Получаем сумму операций трат типа DEBIT
        transactionsRepository.getDebitWithdrawTransactionAmount(userID);
    }

    //Сумма трат по всем продуктам типа DEBIT больше, чем MINIMAL_DEBIT_WITHDRAW_AMOUNT ₽.
    private boolean checkRuleThree(UUID userID) {
        //Получаем сумму операций трат типа DEBIT
        return transactionsRepository.getDebitWithdrawTransactionAmount(userID) > MINIMAL_DEBIT_WITHDRAW_AMOUNT;
    }
}
