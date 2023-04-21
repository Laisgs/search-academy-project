package co.empathy.academy.search.services;

import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.json.JsonData;
import co.empathy.academy.search.components.SearchEngine;
import co.empathy.academy.search.entities.ContractEntity;
import co.empathy.academy.search.entities.Film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SearchServiceImpl implements SearchService{
    //Esta clase asume más responsabilidades de las que creo que debería
    //Preferiría tener la creación de las queries en un componente a parte pero no me dió tiempo.
    @Autowired
    private SearchEngine searchEngine;
    @Override
    public ContractEntity search(String query, double minRating) {
        List<Film> films;
        ContractEntity result = new ContractEntity();
        try {
            films = searchEngine.searchFilms(query,minRating);
            films.forEach(f->result.hits.add(f.toContractEntity()));
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Take films form 2022 and above ordered by rating and number of votes
     * @return ContractEntity with the list of trendings films
     */
    @Override
    public ContractEntity trendings(){
        List<Film> films;
        ContractEntity result = new ContractEntity();


        try {
            films = searchEngine.performFilteredQuery(createTrendingsFilters());
            films.forEach(f->result.hits.add(f.toContractEntity()));
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Query> createTrendingsFilters(){
        List<Query> filters = new ArrayList<>();

        filters.add(rangeInt("startYear", 2022, Integer.MAX_VALUE));
        return filters;
    }

    /**
     * 
     * @param genres
     * @param types
     * @param maxYear
     * @param minYear
     * @param maxRuntime
     * @param minRuntime
     * @param title
     * @return
     */
    @Override
    public ContractEntity filteredSearch(Optional<String> genres, Optional<String> types,
                                         Optional<Integer> maxYear, Optional<Integer> minYear,
                                         Optional<Integer> maxRuntime, Optional<Integer> minRuntime
            , Optional<String> title) {
        List<Film> films;
        ContractEntity result = new ContractEntity();


        try {
            films = searchEngine.performFilteredQuery(createFilters(genres, types, maxYear
                    , minYear, maxRuntime, minRuntime,title));
            films.forEach(f->result.hits.add(f.toContractEntity()));
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Query> createFilters(Optional<String> genres, Optional<String> types,
                                      Optional<Integer> maxYear, Optional<Integer> minYear,
                                      Optional<Integer> maxRuntime, Optional<Integer> minRuntime, Optional<String> title){
        List<Query> filters = new ArrayList<>();

        if(title.isPresent()){
            Query byTitle = MatchQuery.of(r->r
                    .field("primaryTitle")
                    .query(title.get()))._toQuery();
            filters.add(byTitle);
        }

        if (genres.isPresent()) {
            String[] genresArray = genres.get().split(",");
            List<Query> genreQueries = terms(genresArray, "genres");
            filters.add(must(genreQueries));
        }

        if (types.isPresent()) {
            String[] typesArray = types.get().split(",");
            List<Query> typeQueries = terms(typesArray, "type");
            filters.add(should(typeQueries));
        }

        if (maxYear.isPresent() || minYear.isPresent()) {
            filters.add(rangeInt("startYear", minYear.isPresent() ? minYear.get() : 0,
                    maxYear.isPresent() ? maxYear.get() : Integer.MAX_VALUE));
        }

        if (maxRuntime.isPresent() || minRuntime.isPresent()) {
            filters.add(rangeInt("runtimeMinutes", minRuntime.isPresent() ? minRuntime.get() : 0,
                    maxRuntime.isPresent() ? maxRuntime.get() : Integer.MAX_VALUE));
        }

        return filters;
    }

    private Query should(List<Query> queries){
        return BoolQuery.of(b->b.should(queries))._toQuery();
    }

    private Query must(List<Query> queries){
        return BoolQuery.of(b->b.must(queries))._toQuery();
    }

    private Query term(String value, String field) {
        Query termQuery = TermQuery.of(t -> t
                .value(value)
                .field(field))._toQuery();

        return termQuery;
    }

    private List<Query> terms(String[] values, String field) {
        List<Query> termQueries = Arrays.stream(values)
                .map(value -> term(value, field))
                .collect(Collectors.toList());

        return termQueries;
    }

    private Query rangeInt(String field, Integer min, Integer max) {
        return RangeQuery.of(r -> r
                .field(field)
                .gte(JsonData.of(min))
                .lte(JsonData.of(max)))._toQuery();
    }
}
