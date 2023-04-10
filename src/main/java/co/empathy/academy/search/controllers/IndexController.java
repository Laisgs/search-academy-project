package co.empathy.academy.search.controllers;

import co.empathy.academy.search.services.FileProcessService;
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

    @RequestMapping(value = "/test")
    public void test(){
        fileProcessService.test();
    }

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
