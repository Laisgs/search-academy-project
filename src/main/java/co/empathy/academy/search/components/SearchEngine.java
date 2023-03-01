package co.empathy.academy.search.components;

import co.elastic.clients.json.JsonData;
import org.springframework.stereotype.Component;

public interface SearchEngine {
    JsonData getState();
}
