package pro.sky.team2.bank_service.repository;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import pro.sky.team2.bank_service.querysets.QuerySet;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class QueryTypesRepository {

    private final List<QuerySet> querySets;

    private final Set<String> queryTypes = new HashSet<>();


    public QueryTypesRepository(List<QuerySet> querySets) {
        this.querySets = querySets;
    }

    @PostConstruct
    private void init() {
        for (QuerySet querySet : querySets) {
            queryTypes.add(querySet.getQueryType());
        }
    }

    public Set<String> getQueryTypes() {
        return queryTypes;
    }

}
