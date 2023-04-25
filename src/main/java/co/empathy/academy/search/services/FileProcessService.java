package co.empathy.academy.search.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileProcessService {
    /**
     * Save in elasticsearch the given data
     *
     * @param titleAkas Data from akas dataset
     * @param basics Data from basics dataset
     * @param ratings Data from ratings dataset
     * @param crew Data from crew dataset
     * @param episodes Data from episodes dataset
     * @param principals Data from principals dataset
     * @throws IOException
     */
    void save(MultipartFile titleAkas, MultipartFile basics, MultipartFile ratings, MultipartFile crew, MultipartFile episodes, MultipartFile principals) throws IOException;
}
