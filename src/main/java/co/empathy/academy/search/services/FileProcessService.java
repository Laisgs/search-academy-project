package co.empathy.academy.search.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileProcessService {
    void save(MultipartFile titleAkas, MultipartFile basics, MultipartFile ratings, MultipartFile crew, MultipartFile episodes, MultipartFile principals) throws IOException;
    void test();
}
