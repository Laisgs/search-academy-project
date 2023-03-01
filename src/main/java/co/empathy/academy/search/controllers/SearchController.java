package co.empathy.academy.search.controllers;

import co.empathy.academy.search.components.SearchEngineImpl;
import co.empathy.academy.search.services.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class SearchController {

    @Autowired
    private SearchService searchService;
    @GetMapping("/search")
    public String search(@RequestParam String query){
        return searchService.search(query);
    }
}
