package co.empathy.academy.search.services;

import org.springframework.web.multipart.MultipartFile;

public interface FileProcessService {
    void save(MultipartFile file);
}
