package co.empathy.academy.search.entities;

import java.util.ArrayList;
import java.util.List;

public class Person {
    private String name;
    private int birthYear;
    private int deathYear;
    private List<String> primaryProfessions = new ArrayList<>();
    private List<String> knownForTitles = new ArrayList<>();

    public void addProfession(String profession){
        primaryProfessions.add(profession);
    }

    public void addFilmId(String id){
        knownForTitles.add(id);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public void setDeathYear(int deathYear) {
        this.deathYear = deathYear;
    }
}
