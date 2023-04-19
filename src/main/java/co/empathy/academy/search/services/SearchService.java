package co.empathy.academy.search.services;

import co.empathy.academy.search.entities.Film;
import org.springframework.stereotype.Service;

import java.util.List;

public interface SearchService {
    List<Film> search(String query);
}
