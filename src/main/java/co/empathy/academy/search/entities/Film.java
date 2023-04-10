package co.empathy.academy.search.entities;

import java.util.ArrayList;
import java.util.List;

public class Film {
    private String id;
    private List<Title> titles = new ArrayList<>();
    private List<Work> works = new ArrayList<>();
    private String type;
    private String primaryTitle;
    private String originalTitle;
    private int startYear;
    private int endYear;
    private int runtimeMinutes;
    private List<String> genres = new ArrayList<>();;
    private List<String> directorsIds = new ArrayList<>();;
    private List<String> writersIds = new ArrayList<>();;
    private double averageRating;
    private int numberOfVotes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void addTitles(List<Title> titles){
        this.titles.addAll(titles);
    }

    public void addWorks(List<Work> works){
        this.works.addAll(works);
    }

    public List<Work> getWorks() {
        return works;
    }

    public void addGenre(String genre){
        genres.add(genre);
    }

    public void addDirectorId(String id){
        directorsIds.add(id);
    }

    public void addWriterId(String id){
        writersIds.add(id);
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPrimaryTitle(String primaryTitle) {
        this.primaryTitle = primaryTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }

    public void setRuntimeMinutes(int runtimeMinutes) {
        this.runtimeMinutes = runtimeMinutes;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public void setNumberOfVotes(int numberOfVotes) {
        this.numberOfVotes = numberOfVotes;
    }

    @Override
    public String toString() {
        return "Film{" +
                "titles=" + titles +
                ", type='" + type + '\'' +
                ", primaryTitle='" + primaryTitle + '\'' +
                ", originalTitle='" + originalTitle + '\'' +
                ", startYear=" + startYear +
                ", endYear=" + endYear +
                ", runtimeMinutes=" + runtimeMinutes +
                ", genres=" + genres +
                ", directorsIds=" + directorsIds +
                ", writersIds=" + writersIds +
                ", averageRating=" + averageRating +
                ", numberOfVotes=" + numberOfVotes +
                '}';
    }

    public List<Title> getTitles() {
        return titles;
    }

    public String getType() {
        return type;
    }

    public String getPrimaryTitle() {
        return primaryTitle;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public int getStartYear() {
        return startYear;
    }

    public int getEndYear() {
        return endYear;
    }

    public int getRuntimeMinutes() {
        return runtimeMinutes;
    }

    public List<String> getGenres() {
        return genres;
    }

    public List<String> getDirectorsIds() {
        return directorsIds;
    }

    public List<String> getWritersIds() {
        return writersIds;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public int getNumberOfVotes() {
        return numberOfVotes;
    }
}
