package co.empathy.academy.search.controllers;

import co.empathy.academy.search.services.ElasticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class SearchController {
    @Autowired
    private ElasticService elastic;

    @GetMapping("/search")
    public String search(@RequestParam String query){

        //return "Query: " + query + " ClusterName: " + elastic.getState();
        elastic.getState();
        return "";
    }
}
