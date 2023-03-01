package co.empathy.academy.search.services;

import org.springframework.stereotype.Service;

@Service
public class SearchServiceDummy implements SearchService{
    @Override
    public String search(String query) {
        return "Hola";
    }
}
