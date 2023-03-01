package co.empathy.academy.search.services;

import co.empathy.academy.search.components.SearchEngine;
import co.empathy.academy.search.components.SearchEngineImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class SearchServiceImpl implements SearchService{
    @Autowired
    private SearchEngine searchEngine;
    @Override
    public String search(String query) {
        return "Query: " + query + " ClusterName: " + searchEngine.getState().toJson().asJsonObject().getString("cluster_name");
    }
}
