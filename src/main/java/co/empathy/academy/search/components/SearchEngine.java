package co.empathy.academy.search.components;

import co.elastic.clients.json.JsonData;
import co.empathy.academy.search.entities.Film;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public interface SearchEngine {
    String getClusterName();
    void bulkIndexFilms(Collection<Film> films) throws IOException;

    void createIndex() throws IOException;

    public List<Film> searchFilms(String title, double minRating) throws IOException;
}
