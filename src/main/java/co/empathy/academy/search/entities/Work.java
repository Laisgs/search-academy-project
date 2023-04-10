package co.empathy.academy.search.entities;

public class Work {
    private String filmId;
    private String personId;
    private String category;
    private String job;
    private String characters;

    public String getFilmId() {
        return filmId;
    }

    public void setFilmId(String filmId) {
        this.filmId = filmId;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getCharacters() {
        return characters;
    }

    public void setCharacters(String characters) {
        this.characters = characters;
    }

    @Override
    public String toString() {
        return "Work{" +
                "filmId='" + filmId + '\'' +
                ", personId='" + personId + '\'' +
                ", category='" + category + '\'' +
                ", job='" + job + '\'' +
                ", characters='" + characters + '\'' +
                '}';
    }
}
