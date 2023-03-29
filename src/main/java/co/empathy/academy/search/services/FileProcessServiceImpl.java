package co.empathy.academy.search.services;

import co.empathy.academy.search.components.SearchEngine;
import co.empathy.academy.search.entities.Episode;
import co.empathy.academy.search.entities.Film;
import co.empathy.academy.search.entities.Title;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class FileProcessServiceImpl implements FileProcessService{
    @Autowired
    private SearchEngine searchEngine;
    private boolean nameBasics;
    private boolean titleAkas;
    private boolean titleBasics;
    private boolean titleCrew;
    private boolean titleEpisode;
    private boolean titlePrincipals;
    private boolean titleRatings;

    private ConcurrentHashMap<String, Film> films = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Episode> episodes = new ConcurrentHashMap<>();
    private HashMap<String, List<Title>> titles = new HashMap<>();

    private void sendToElastic(){
        //films.forEach((key, value) -> System.out.println(value.toString()));
        System.out.println("E5");
        if(titleAkas && titleBasics
                && titleCrew && titleRatings){
            System.out.println("E6");
            titles.forEach((key, value) -> films.get(key).addTitles(value));

            try {
                searchEngine.bulkIndexFilms(films.values());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            films.clear();
            reset();
        }
    }

    private void sendToElastic2(){
        films.forEach((key, value) -> System.out.println(value.toString()));
        if(nameBasics && titleAkas && titleBasics
                && titleCrew && titleEpisode
                && titlePrincipals && titleRatings){
            //send
            titles.forEach((key, value) -> films.get(key).addTitles(value));
            reset();
        }

        films.clear();
    }

    private void reset(){
        titleBasics = false;
        nameBasics = false;
        titleAkas = false;
        titleCrew = false;
        titleEpisode = false;
        titlePrincipals = false;
        titleRatings = false;
    }

    @Override
    @Async
    public void save(MultipartFile file) throws IOException {
        readData(file);
    }

    /*
    private void writeToTemporalFile(MultipartFile file) throws IOException {
        tmpFile = File.createTempFile("IMDB-Search-App", ".tmp");
        //FileOutputStream writer = new FileOutputStream(tmpFile);
        BufferedWriter writer = new BufferedWriter(new FileWriter(tmpFile));
        int toWrite;
        InputStream fileData = file.getInputStream();

        while ((toWrite = fileData.read()) != -1){
            writer.write(toWrite);
        }

        writer.close();
    }
     */

    private void readData(MultipartFile file){
        BufferedReader reader;

        try{
            String line;

            reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
            line = reader.readLine();

            if(line != null) {
                String[] columsNames = line.split("\t");

                if (columsNames[1].equals("primaryName"))
                    readNameBasics(reader);
                else if (columsNames[1].equals("directors"))
                    readTitleCrew(reader);
                else if (columsNames[0].equals("titleId"))
                    readTitleAkas(reader);
                else if (columsNames[1].equals("titleType"))
                    readTitleBasics(reader);
                else if (columsNames[1].equals("parentTconst"))
                    readTitleEpisode(reader);
                else if (columsNames[2].equals("numVotes"))
                    readTitleRatings(reader);
                else if (columsNames[4].equals("job"))
                    readTitlePrincipals(reader);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void readNameBasics(BufferedReader reader) throws IOException {
        int linesReaded = 0;
        String line;
        String[] lineData;

        while ((line = reader.readLine()) != null){
            lineData = line.split("\t");

            //System.out.println(lineData[0]);
        }
    }

    private void readTitleAkas(BufferedReader reader) throws IOException {
        int linesReaded = 0;
        String line;
        String[] lineData;
        List<Title> titlesList;
        String lastId = ".";


        while ((line = reader.readLine()) != null){

            lineData = line.split("\t");

            if (titles.containsKey(lineData[0])) {
                titlesList = titles.get(lineData[0]);
                titlesList.add(creatTitleAkas(lineData));
            } else {
                titlesList = new ArrayList<>();
                titlesList.add(creatTitleAkas(lineData));
                titles.put(lineData[0], titlesList);
            }

            if (!lineData[0].equals(lastId)){
                linesReaded++;
            }

            lastId = lineData[0];
        }

        titleAkas = true;
        System.out.println("E1");
        sendToElastic();
    }

    private void readTitleBasics(BufferedReader reader) throws IOException {
        int linesReaded = 0;
        String line;
        String[] lineData;
        Film currentFilm;
        String lastId = ".";

        while ((line = reader.readLine()) != null){
            lineData = line.split("\t");

            if (!(Integer.parseInt(lineData[4]) == 1)) {
                if (films.containsKey(lineData[0])) {
                    currentFilm = films.get(lineData[0]);
                    addDataTitleBasics(currentFilm, lineData);
                } else {
                    currentFilm = new Film();
                    addDataTitleBasics(currentFilm, lineData);

                    films.put(lineData[0], currentFilm);
                }
            }

            if (!lineData[0].equals(lastId)){
                linesReaded++;
            }

            lastId = lineData[0];

            if(linesReaded >= 1000000){
                titleBasics = true;

                linesReaded = 0;
            }
        }

        titleBasics = true;
        System.out.println("E2");
        sendToElastic();
    }

    private void readTitleCrew(BufferedReader reader) throws IOException {
        int linesReaded = 0;
        String line;
        String[] lineData;
        Film currentFilm;
        String lastId = ".";

        while ((line = reader.readLine()) != null){
            lineData = line.split("\t");

                if (films.containsKey(lineData[0])) {
                    currentFilm = films.get(lineData[0]);
                    addDataTitleCrew(currentFilm, lineData);
                } else {
                    currentFilm = new Film();
                    addDataTitleCrew(currentFilm, lineData);
                    films.put(lineData[0], currentFilm);
                }

            if (!lineData[0].equals(lastId)){
                linesReaded++;
            }

            lastId = lineData[0];
        }

        titleCrew = true;
        System.out.println("E3");
        sendToElastic();
    }

    private void readTitleEpisode(BufferedReader reader) throws IOException {
        int linesReaded = 0;
        String line;
        String[] lineData;
        Episode currentEpisode;
        String lastId = ".";

        while ((line = reader.readLine()) != null){
            lineData = line.split("\t");

            if (episodes.containsKey(lineData[0])) {
                currentEpisode = episodes.get(lineData[0]);
                addDataTitleEpisode(currentEpisode, lineData);
            } else {
                currentEpisode = new Episode();
                addDataTitleEpisode(currentEpisode, lineData);
                episodes.put(lineData[0], currentEpisode);
            }

            if (!lineData[0].equals(lastId)){
                linesReaded++;
            }

            lastId = lineData[0];
        }

        titleEpisode = true;

    }

    private void readTitlePrincipals(BufferedReader reader) throws IOException {
        int linesReaded = 0;
        String line;
        String[] lineData;
        Film currentFilm;
        String lastId = ".";

        while ((line = reader.readLine()) != null){
            lineData = line.split("\t");

            if (films.containsKey(lineData[0])) {
                currentFilm = films.get(lineData[0]);
                addDataTitlePrincipals(currentFilm, lineData);
            } else {
                currentFilm = new Film();
                addDataTitlePrincipals(currentFilm, lineData);
                films.put(lineData[0], currentFilm);
            }

            if (!lineData[0].equals(lastId)){
                linesReaded++;
            }

            lastId = lineData[0];
        }

        titlePrincipals = true;

    }

    private void readTitleRatings(BufferedReader reader) throws IOException {
        int linesReaded = 0;
        String line;
        String[] lineData;
        Film currentFilm;
        String lastId = ".";

        while ((line = reader.readLine()) != null){
            lineData = line.split("\t");

            if (films.containsKey(lineData[0])) {
                currentFilm = films.get(lineData[0]);
                addDataTitleRatings(currentFilm, lineData);
            } else {
                currentFilm = new Film();
                addDataTitleRatings(currentFilm, lineData);
                films.put(lineData[0], currentFilm);
            }

            if (!lineData[0].equals(lastId)){
                linesReaded++;
            }

            lastId = lineData[0];
        }

        titleRatings = true;
        System.out.println("E4");
        sendToElastic();
    }

    private void addDataTitleCrew(Film currentFilm, String[] lineData){
        String[] directors;
        String[] writers;

        if (!lineData[1].equals("\\N")) {
            directors = lineData[1].split(",");

            for (int i=0; i<directors.length; i++){
                currentFilm.addDirectorId(directors[i]);
            }
        }

        if (!lineData[2].equals("\\N")) {
            writers = lineData[2].split(",");

            for (int i=0; i<writers.length; i++){
                currentFilm.addWriterId(writers[i]);
            }
        }
    }

    private void addDataTitleBasics(Film currentFilm, String[] lineData){
        currentFilm.setType(lineData[1]);
        currentFilm.setPrimaryTitle(lineData[2]);
        currentFilm.setOriginalTitle(lineData[3]);

        if (!lineData[5].equals("\\N")) {
            currentFilm.setStartYear(Integer.parseInt(lineData[5]));
        }

        if (!lineData[6].equals("\\N")) {
            currentFilm.setEndYear(Integer.parseInt(lineData[6]));
        }

        if (!lineData[7].equals("\\N")) {
            currentFilm.setRuntimeMinutes(Integer.parseInt(lineData[7]));
        }

        lineData = lineData[8].split(",");

        for (int i=0; i<lineData.length; i++){
            currentFilm.addGenre(lineData[i]);
        }
    }

    private void addDataTitleEpisode(Episode currentEpisode, String[] lineData){
        currentEpisode.setId(lineData[0]);
        currentEpisode.setParentId(lineData[1]);

        if (!lineData[2].equals("\\N")) {
            currentEpisode.setSeasonNumber(Integer.parseInt(lineData[2]));
        }
        if (!lineData[3].equals("\\N")) {
            currentEpisode.setEpisodeNumber(Integer.parseInt(lineData[3]));
        }
    }

    private void addDataTitlePrincipals(Film currentFilm, String[] lineData){

    }

    private void addDataTitleRatings(Film currentFilm, String[] lineData){
        currentFilm.setAverageRating(Double.parseDouble(lineData[1]));
        currentFilm.setNumberOfVotes(Integer.parseInt(lineData[2]));
    }

    private Title creatTitleAkas(String[] lineData) {
        Title actualTitle = new Title();
        actualTitle.setId(lineData[0]);
        actualTitle.setTitle(lineData[2]);
        if (!lineData[3].equals("\\N")) {
            actualTitle.setRegion(lineData[3]);
        }
        if (!lineData[4].equals("\\N")) {
            actualTitle.setLanguage(lineData[4]);
        }

        if (!lineData[5].equals("\\N")) {
            String[] types = lineData[5].split(",");

            for (int i=0; i<types.length; i++){
                actualTitle.addType(types[i]);
            }
        }

        if (!lineData[6].equals("\\N")) {
            String[] atributes = lineData[6].split(",");

            for (int i=0; i<atributes.length; i++){
                actualTitle.addAttribute(atributes[i]);
            }
        }

        if(lineData[7].equals("1")){
            actualTitle.setIsOriginalTitle();
        }


        return actualTitle;
    }
}