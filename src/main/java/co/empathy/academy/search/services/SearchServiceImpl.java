package co.empathy.academy.search.services;

import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.empathy.academy.search.components.SearchEngine;
import co.empathy.academy.search.entities.Film;
import org.elasticsearch.action.search.SearchRequest;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService{
    @Autowired
    private SearchEngine searchEngine;
    @Override
    public List<Film> search(String query) {
        try {
            return searchEngine.searchFilms(query,5.0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
