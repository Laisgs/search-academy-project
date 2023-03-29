package co.empathy.academy.search.components;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.json.JsonData;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import co.empathy.academy.search.entities.Film;
import co.empathy.academy.search.exceptions.BulkIndexException;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@Component
public class SearchEngineImpl implements SearchEngine{
    private ElasticsearchClient client;

    private void createClient(){
        // Create the low-level client
        RestClient restClient = RestClient.builder(
                new HttpHost("elasticsearch", 9200)).build();

        // Create the transport with a Jackson mapper
        ElasticsearchTransport transport = new RestClientTransport(
                restClient, new JacksonJsonpMapper());

        // And create the API client
        client = new ElasticsearchClient(transport);
    }

    public String getClusterName(){
        createClient();
        try {
            return client.cluster().state().valueBody().toJson().asJsonObject().getString("cluster_name");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void bulkIndexFilms(Collection<Film> films) throws IOException {
        createClient();
        BulkRequest.Builder request = new BulkRequest.Builder();

        films.forEach(film -> request.operations(op -> op
                .index(i -> i
                        .index("imdbFilms")
                        .id(film.getId())
                        .document(film))));

        BulkResponse bulkResponse = client.bulk(request.build());

        if (bulkResponse.errors()) {
            throw new BulkIndexException("Error indexing bulk");
        }
    }


}
