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

    @RequestMapping(value = "/index", method = RequestMethod.POST)
    public void uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        fileProcessService.save(file);
    }
}
