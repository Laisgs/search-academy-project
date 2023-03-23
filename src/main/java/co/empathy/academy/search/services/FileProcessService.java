package co.empathy.academy.search.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileProcessService {
    void save(MultipartFile file) throws IOException;
}
