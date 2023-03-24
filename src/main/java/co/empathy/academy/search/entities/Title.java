package co.empathy.academy.search.entities;

import java.util.ArrayList;
import java.util.List;

public class Title {
    private String id;
    private String title;
    private String region;
    private String language;
    private List<String> types = new ArrayList<>();
    private List<String> attributes = new ArrayList<>();
    private boolean isOriginalTitle;

    public void addType(String type){
        types.add(type);
    }

    public void addAttribute(String attribute){
        attributes.add(attribute);
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setIsOriginalTitle() {
        isOriginalTitle = true;
    }
}
