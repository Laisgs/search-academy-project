package co.empathy.academy.search.components;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;

import java.util.List;

public interface QueryComponent {
    /**
     * A should query made with a list of queries
     *
     * @param queries List of queries use to make the new should query
     * @return Should query
     */
    Query should(List<Query> queries);

    /**
     * A must query made with a list of queries
     *
     * @param queries List of queries use to make the new must query
     * @return Must query
     */
    Query must(List<Query> queries);

    /**
     *Create a Query that match the given value to the given field
     *
     * @param value Value to match
     * @param field Field that must match
     * @return Query that match the given value to the given field
     */
    Query term(String value, String field);

    /**
     *Create a list of Query that match the given values to the given field
     *
     * @param values Values to match
     * @param field Field that must match
     * @return List of Query that match the given values to the given field
     */
    List<Query> terms(String[] values, String field);

    /**
     *Create a range Query given a field and numeric limits
     *
     * @param field Field that must be in thar range
     * @param min Lower limit of interval
     * @param max Upper limit of interval
     * @return Query
     */
    Query rangeInt(String field, Integer min, Integer max);
}
