package pro.sky.team2.bank_service.querysets;

import java.util.List;
import java.util.UUID;

public interface QuerySet {

    //Метод делегирует создание sql-запроса репозиторию
    boolean checkRule(List<String> arguments, UUID userId);

    //При создани новой имплементации интерфейса необходимо добавить новый элемент в ArgumentsRepository.QUERIES
    //Метод должен возвращать это добавленное значение
    //return ArgumentsRepository.QUERIES[n];
    String getQueryType();

    //Метод проверяет соответствие аргументов заданному формату
    boolean checkArguments(List<String> arguments);

}
