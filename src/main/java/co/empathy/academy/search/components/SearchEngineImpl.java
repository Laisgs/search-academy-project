package co.empathy.academy.search.components;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.empathy.academy.search.config.ElasticSearchClientConfiguration;
import co.empathy.academy.search.entities.Film;
import co.empathy.academy.search.entities.Prueba;
import co.empathy.academy.search.exceptions.BulkIndexException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class SearchEngineImpl implements SearchEngine{
    @Autowired
    private ElasticSearchClientConfiguration elasticConfig;
    private String indexName = "imdb_films";

    public String getClusterName(){
        try {
            return elasticConfig.getCLient().cluster().state().valueBody().toJson().asJsonObject().getString("cluster_name");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void bulkIndexFilms(Collection<Film> films) throws IOException {
        BulkRequest.Builder bulkBuilder = new BulkRequest.Builder();

        films.forEach(film ->
                bulkBuilder.operations(op -> op
                .index(idx -> idx
                        .index(indexName)
                        .id(film.getId())
                        .document(film))));

        BulkResponse result = elasticConfig.getCLient().bulk(bulkBuilder.build());

        if (result.errors()) {
            throw new BulkIndexException("Error indexing bulk");
        }
    }

    @Override
    public void createIndex() throws IOException {
        try{
            elasticConfig.getCLient().indices().delete(client -> client.index(indexName));
        }catch (Exception ex){

        }

        elasticConfig.getCLient().indices().create(client -> client.index(indexName));
    }

    /*
    public void bulkIndexActuFilms(Collection<Film> films) throws IOException {
        BulkRequest.Builder bulkBuilder = new BulkRequest.Builder();

        films.forEach(film ->
                bulkBuilder.operations(op -> op
                        .update(idx -> idx
                                .index("imdb_films")
                                .id(film.getId())
                                .document(film))));

        BulkResponse result = elasticConfig.getCLient().bulk(bulkBuilder.build());

        System.out.println(result);

        if (result.errors()) {
            throw new BulkIndexException("Error indexing bulk");
        }
    }

     */
}
