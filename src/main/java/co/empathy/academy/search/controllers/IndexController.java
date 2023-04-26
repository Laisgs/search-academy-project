package co.empathy.academy.search.controllers;

import co.empathy.academy.search.services.FileProcessService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class IndexController {

    @Autowired
    private FileProcessService fileProcessService;

    @Operation(summary = "Indexes imdb data in the index")
    @Parameter(name = "titleBasics", description = "Title basics file containing the basics to be indexed", required = true)
    @Parameter(name = "ratings", description = "Ratings file containing the rating to be indexed", required = true)
    @Parameter(name = "akas", description = "Akas file containing the akas to be indexed", required = true)
    @Parameter(name = "crew", description = "Crew file containing the directors to be indexed", required = true)
    @Parameter(name = "principals", description = "Principals file containing the principals to be indexed", required = true)
    @Parameter(name = "episodes", description = "Episodes file containing the episodes to be indexed", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Data accepted for indexing")
    })
    @RequestMapping(value = "/index", method = RequestMethod.POST)
    public void uploadFiles(@RequestParam("akas") MultipartFile akas,
                            @RequestParam("titleBasics") MultipartFile titleBasics,
                            @RequestParam("crew") MultipartFile crew,
                            @RequestParam("ratings") MultipartFile ratings,
                            @RequestParam("episodes") MultipartFile episodes,
                            @RequestParam("principals") MultipartFile principals) throws IOException {
        fileProcessService.save(akas, titleBasics, ratings, crew, episodes, principals);
    }
}
