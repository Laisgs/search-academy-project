package co.empathy.academy.search.components;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;
import co.elastic.clients.json.JsonData;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class QueryComponentImpl implements QueryComponent {
    @Override
    public Query should(List<Query> queries){
        return BoolQuery.of(b->b.should(queries))._toQuery();
    }
    @Override
    public Query must(List<Query> queries){
        return BoolQuery.of(b->b.must(queries))._toQuery();
    }

    @Override
    public Query term(String value, String field) {
        Query termQuery = TermQuery.of(t -> t
                .value(value)
                .field(field))._toQuery();

        return termQuery;
    }

    @Override
    public List<Query> terms(String[] values, String field) {
        List<Query> termQueries = Arrays.stream(values)
                .map(value -> term(value, field))
                .collect(Collectors.toList());

        return termQueries;
    }

    @Override
    public Query rangeInt(String field, Integer min, Integer max) {
        return RangeQuery.of(r -> r
                .field(field)
                .gte(JsonData.of(min))
                .lte(JsonData.of(max)))._toQuery();
    }
}
