package co.empathy.academy.search.components;

import co.elastic.clients.elasticsearch._types.FieldSort;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.JsonData;
import co.empathy.academy.search.config.ElasticSearchClientConfiguration;
import co.empathy.academy.search.entities.Film;
import co.empathy.academy.search.exceptions.BulkIndexException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

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

        configure();
    }

    private void configure() throws IOException {
        elasticConfig.getCLient().indices().close( closeClient -> closeClient.index(indexName));

        InputStream analizer = getClass().getClassLoader().getResourceAsStream("myAnalyzer.json");
        elasticConfig.getCLient().indices().putSettings(putSet -> putSet.index(indexName).withJson(analizer));

        elasticConfig.getCLient().indices().open(openClient -> openClient.index(indexName));

        InputStream mapping = getClass().getClassLoader().getResourceAsStream("mapping.json");
        elasticConfig.getCLient().indices().putMapping( r -> r.index(indexName).withJson(mapping));
    }

    public List<Film> searchFilms(String title, double minRating) throws IOException {

        Query byTitle = MatchQuery.of(r->r
                .field("primaryTitle")
                .query(title))._toQuery();
        Query byRating = RangeQuery.of(r->r
                .field("averageRating").gt(JsonData.of(minRating)))._toQuery();

        SearchResponse<Film> response = elasticConfig.getCLient().search(s->s
                .index(indexName)
                .query(q->q
                        .bool(b->b
                                .must(byTitle)
                                .must(byRating))
                ).sort(so->so.field(FieldSort.of(f->f
                                .field("numberOfVotes")
                                .order(SortOrder.Desc)))),
                Film.class);
        return getFilms(response);
    }

    public List<Film> performFilteredQuery(List<Query> filters) throws IOException {
        Query query = BoolQuery.of(q->q.must(filters))._toQuery();
        SortOptions sort = new SortOptions.Builder()
                .field(f->f.field("numberOfVotes").order(SortOrder.Desc)).build();
        SearchResponse<Film> response = elasticConfig.getCLient().search(s->s
                .index(indexName)
                        .query(query)
                        .sort(sort)
                , Film.class);
        return getFilms(response);
    }

    private List<Film> getFilms(SearchResponse<Film> response){
        return response.hits().hits().stream().map(Hit::source).toList();
    }
}
