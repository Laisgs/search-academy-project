package co.empathy.academy.search.controllers;

import co.empathy.academy.search.entities.ContractEntity;
import co.empathy.academy.search.services.SearchService;
import co.empathy.academy.search.users.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/search")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SearchController {

    @Autowired
    private SearchService searchService;
    @GetMapping
    public ResponseEntity<ContractEntity> search(@RequestParam String query){
        return new ResponseEntity<>(searchService.search(query, 5.0), HttpStatus.OK);
    }

    @GetMapping("/trendings")
    public ResponseEntity<ContractEntity> trendings(){
        return new ResponseEntity<>(searchService.trendings(), HttpStatus.OK);
    }

    @GetMapping("/filters")
    public ResponseEntity<ContractEntity> searchFilter(@RequestParam("genres") Optional<String> genres,
                                                       @RequestParam("type") Optional<String> type,
                                                       @RequestParam("maxYear") Optional<Integer> maxYear,
                                                       @RequestParam("minYear") Optional<Integer> minYear,
                                                       @RequestParam("maxMinutes") Optional<Integer> maxMinutes,
                                                       @RequestParam("minMinutes") Optional<Integer> minMinutes,
                                                       @RequestParam("title") Optional<String> title){
        return new ResponseEntity<>(searchService.filteredSearch(genres, type, maxYear, minYear
                , maxMinutes, minMinutes, title), HttpStatus.OK);
    }
}
