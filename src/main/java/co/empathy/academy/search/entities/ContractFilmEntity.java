package co.empathy.academy.search.entities;

import java.util.ArrayList;
import java.util.List;

public class ContractFilmEntity {
    public String id;
    public String tconst;
    public String titleType;
    public String primaryTitle;
    public String originalTitle;
    public boolean isAdult = false;
    public int startYear;
    public int endYear;
    public int runtimeMinutes;
    public List<String> genres = new ArrayList<>();
    public double averageRating;
    public int numVotes;
    public List<Aka> akas = new ArrayList<>();
}
