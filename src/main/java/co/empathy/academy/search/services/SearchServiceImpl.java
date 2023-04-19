package co.empathy.academy.search.services;

import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.empathy.academy.search.components.SearchEngine;
import co.empathy.academy.search.entities.ContractEntity;
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
    public ContractEntity search(String query) {
        List<Film> films;
        ContractEntity result = new ContractEntity();
        try {
            films = searchEngine.searchFilms(query,5.0);
            films.forEach(f->result.hits.add(f.toContractEntity()));
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
