package co.empathy.academy.search.controllers;

import co.empathy.academy.search.entities.ContractEntity;
import co.empathy.academy.search.services.SearchService;
import co.empathy.academy.search.users.entities.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Get films by title")
    @Parameter(name = "title", description = "Title of the film")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of movies found, it can be empty")
    })
    @GetMapping
    public ResponseEntity<ContractEntity> search(@RequestParam String query){
        return new ResponseEntity<>(searchService.search(query, 5.0), HttpStatus.OK);
    }

    @Operation(summary = "Get trending films")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of movies found, it can be empty")
    })
    @GetMapping("/trendings")
    public ResponseEntity<ContractEntity> trendings(){
        return new ResponseEntity<>(searchService.trendings(), HttpStatus.OK);
    }

    @Operation(summary = "Get films by a basic filters query")
    @Parameter(name = "genres", description = "Genres of the film. Can be multiple ones, separated by commas. It matches exactly")
    @Parameter(name = "type", description = "Type of the film. Can be multiple ones, separated by commas. It matches exactly")
    @Parameter(name = "maxYear", description = "Maximum year of the film")
    @Parameter(name = "minYear", description = "Minimum year of the film")
    @Parameter(name = "maxMinutes", description = "Maximum runtime minutes of the film")
    @Parameter(name = "minMinutes", description = "Minimum runtime minutes of the film")
    @Parameter(name = "title", description = "Title of the film")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of movies found, it can be empty")
    })
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
