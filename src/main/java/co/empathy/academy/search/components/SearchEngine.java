package co.empathy.academy.search.components;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.json.JsonData;
import co.empathy.academy.search.entities.Film;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public interface SearchEngine {
    String getClusterName();

    /**
     * Index a collection of films
     *
     * @param films Films to index
     * @throws IOException
     */
    void bulkIndexFilms(Collection<Film> films) throws IOException;

    /**
     *
     * @throws IOException
     */
    void createIndex() throws IOException;

    /**
     * Search films given a title and a min rating
     *
     * @param title Title of the film
     * @param minRating Min rating that the film must have
     * @return
     * @throws IOException
     */
    List<Film> searchFilms(String title, double minRating) throws IOException;

    /**
     * Perform a query that must past the given filters
     *
     * @param filters
     * @return
     * @throws IOException
     */
    List<Film> performFilteredQuery(List<Query> filters) throws IOException;
}
