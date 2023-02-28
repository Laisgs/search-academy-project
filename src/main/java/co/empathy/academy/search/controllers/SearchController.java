package co.empathy.academy.search.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
public class SearchController {
    @GetMapping("/search")
    public String getId(@RequestParam(defaultValue="LAIS") String id){

        return id;
    }
}
