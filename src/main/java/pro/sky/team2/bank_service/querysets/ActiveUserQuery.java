package pro.sky.team2.bank_service.querysets;

import pro.sky.team2.bank_service.exception.WrongArgumentException;
import pro.sky.team2.bank_service.repository.ArgumentsRepository;
import pro.sky.team2.bank_service.repository.TransactionsRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ActiveUserQuery implements QuerySet{

    private final TransactionsRepository transactionsRepository;

    public ActiveUserQuery(TransactionsRepository transactionsRepository) {
        this.transactionsRepository = transactionsRepository;
    }

    public String getQueryType() {
        return "ACTIVE_USER_OF";
    }

    @Override
    public boolean checkArguments(List<String> arguments) {
        if (arguments.size() != 1) {
            throw new WrongArgumentException("Wrong number of arguments");
        } else if (!List.of(ArgumentsRepository.PRODUCT_TYPES).contains(arguments.get(0))) {
            throw new WrongArgumentException("Wrong product type");
        } else {
            return true;
        }
    }

    @Override
    public boolean checkRule(List<String> arguments, UUID userId) {
        return transactionsRepository.checkActiveUserOf(userId, arguments);
    }
}
