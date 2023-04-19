package co.empathy.academy.search.services;

import co.empathy.academy.search.entities.ContractEntity;
import co.empathy.academy.search.entities.Film;
import org.springframework.stereotype.Service;

import java.util.List;

public interface SearchService {
    ContractEntity search(String query);
}
