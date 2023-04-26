package co.empathy.academy.search.services;

import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.json.JsonData;
import co.empathy.academy.search.components.QueryComponent;
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
    @Autowired
    private SearchEngine searchEngine;
    @Autowired
    private QueryComponent queryComponent;
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

        filters.add(queryComponent.rangeInt("startYear", 2022, Integer.MAX_VALUE));
        return filters;
    }

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
            List<Query> genreQueries = queryComponent.terms(genresArray, "genres");
            filters.add(queryComponent.must(genreQueries));
        }

        if (types.isPresent()) {
            String[] typesArray = types.get().split(",");
            List<Query> typeQueries = queryComponent.terms(typesArray, "type");
            filters.add(queryComponent.should(typeQueries));
        }

        if (maxYear.isPresent() || minYear.isPresent()) {
            filters.add(queryComponent.rangeInt("startYear", minYear.isPresent() ? minYear.get() : 0,
                    maxYear.isPresent() ? maxYear.get() : Integer.MAX_VALUE));
        }

        if (maxRuntime.isPresent() || minRuntime.isPresent()) {
            filters.add(queryComponent.rangeInt("runtimeMinutes", minRuntime.isPresent() ? minRuntime.get() : 0,
                    maxRuntime.isPresent() ? maxRuntime.get() : Integer.MAX_VALUE));
        }

        return filters;
    }
}
