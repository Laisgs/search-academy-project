package co.empathy.academy.search.services;

import co.empathy.academy.search.entities.ContractEntity;
import co.empathy.academy.search.entities.Film;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface SearchService {
    ContractEntity search(String query, double minRating);
    ContractEntity filteredSearch(Optional<String> genres, Optional<String> types,
                                  Optional<Integer> maxYear, Optional<Integer> minYear,
                                  Optional<Integer> maxRuntime, Optional<Integer> minRuntime);
    ContractEntity trendings();
}
