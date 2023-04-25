package co.empathy.academy.search.services;

import co.empathy.academy.search.entities.ContractEntity;
import co.empathy.academy.search.entities.Film;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface SearchService {
    /**
     * Perform query with the given title
     *
     * @param query Title of the film
     * @param minRating Min average ratting that the film must have
     * @return
     */
    ContractEntity search(String query, double minRating);
    /**
     * Perform a filtered search
     *
     * @param genres Genres split by "," that films must have
     * @param types Types split by "," that films must have
     * @param maxYear Superior limit if the interval of time when a film where release
     * @param minYear Inferior limit if the interval of time when a film where release
     * @param maxRuntime Max duration of the film in minutes
     * @param minRuntime Min duration of the film in minutes
     * @param title Title of the film
     * @return
     */
    ContractEntity filteredSearch(Optional<String> genres, Optional<String> types,
                                  Optional<Integer> maxYear, Optional<Integer> minYear,
                                  Optional<Integer> maxRuntime, Optional<Integer> minRuntime, Optional<String> title);
    /**
     * Take films form 2022 and above ordered by rating and number of votes
     * @return ContractEntity with the list of trendings films
     */
    ContractEntity trendings();
}
