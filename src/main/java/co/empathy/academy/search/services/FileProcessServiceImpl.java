package co.empathy.academy.search.services;

import co.empathy.academy.search.components.SearchEngine;
import co.empathy.academy.search.entities.Episode;
import co.empathy.academy.search.entities.Film;
import co.empathy.academy.search.entities.Title;
import co.empathy.academy.search.entities.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class FileProcessServiceImpl implements FileProcessService {
    private BufferedReader akasReader;
    private BufferedReader titleBasicsReader;
    private BufferedReader ratingsReader;
    private BufferedReader crewReader;
    private BufferedReader episodeReader;
    private BufferedReader principalsReader;

    private int bulkSize = 40000;
    private int markSize = 100000;


    @Autowired
    private SearchEngine searchEngine;

    private void sendToElastic(HashMap<String, Film> films) {
        try {
            searchEngine.bulkIndexFilms(films.values());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Async
    public void save(MultipartFile titleAkas, MultipartFile basics, MultipartFile ratings, MultipartFile crew, MultipartFile episodes, MultipartFile principals) throws IOException {
        titleBasicsReader = new BufferedReader(new InputStreamReader(basics.getInputStream()));
        akasReader = new BufferedReader(new InputStreamReader(titleAkas.getInputStream()));
        ratingsReader = new BufferedReader(new InputStreamReader(ratings.getInputStream()));
        crewReader = new BufferedReader(new InputStreamReader(crew.getInputStream()));
        //episodeReader = new BufferedReader(new InputStreamReader(episodes.getInputStream()));
        principalsReader = new BufferedReader(new InputStreamReader(principals.getInputStream()));
        titleBasicsReader.readLine();
        akasReader.readLine();
        ratingsReader.readLine();
        crewReader.readLine();
        //episodeReader.readLine();
        principalsReader.readLine();

        searchEngine.createIndex();

        long inicio = System.currentTimeMillis();
        readTitleBasics();
        long fin = System.currentTimeMillis();
        double tiempo = ((fin - inicio)/1000.0);

        System.out.println(tiempo +" segundos");
    }

    private void readTitleBasics() throws IOException {
        int linesReaded = 0;
        String line;
        String[] lineData;
        Film currentFilm;
        HashMap<String, Film> films = new HashMap<>();
        String lastId = ".";

        while ((line = titleBasicsReader.readLine()) != null){
            lineData = line.split("\t");

            if (!(Integer.parseInt(lineData[4]) == 1)) {
                currentFilm = new Film();
                addDataTitleBasics(currentFilm, lineData);
                films.put(lineData[0], currentFilm);
            }

            if (!lineData[0].equals(lastId)){
                linesReaded++;
            }

            lastId = lineData[0];

            if(linesReaded >= bulkSize){
                readTitleRatings(films);
                readTitleCrew(films);

                readTitleAkas(lastId).forEach((key, value) -> {
                    try {
                        films.get(key).addTitles(value);
                    }catch (NullPointerException ex){
                        //System.out.println(key);
                    }
                });

                readTitlePrincipals(lastId).forEach((key, value) -> {
                    try {
                        films.get(key).addWorks(value);
                    }catch (NullPointerException ex){
                        //System.out.println(key);
                    }
                });

                sendToElastic(films);
                films.clear();
                reset();
                linesReaded = 0;
            }
        }

        readTitleRatings(films);
        readTitleCrew(films);

        readTitleAkas(lastId).forEach((key, value) -> {
            try {
                films.get(key).addTitles(value);
            }catch (NullPointerException ex){
                //System.out.println(key);
            }
        });

        readTitlePrincipals(lastId).forEach((key, value) -> {
            try {
                films.get(key).addWorks(value);
            }catch (NullPointerException ex){
                //System.out.println(key);
            }
        });

        sendToElastic(films);
        films.clear();
        reset();
        linesReaded = 0;

        System.out.println("FIN");
    }

    private void reset() throws IOException {
        ratingsReader.reset();
        crewReader.reset();
        //akasReader.reset();
    }

    private HashMap<String, List<Title>> readTitleAkas(String lastId) throws IOException {
        String line;
        String[] lineData;
        List<Title> titlesList;
        HashMap<String, List<Title>> titles = new HashMap<>();

        boolean last = false;

        while ((line = akasReader.readLine()) != null){
            lineData = line.split("\t");


            if (lastId.equals(lineData[0]))
                last=true;
            if(last && !(lastId.equals(lineData[0]))
                    || Integer.parseInt(lastId.substring(2))
                    <
                    Integer.parseInt(lineData[0].substring(2))){
                akasReader.mark(markSize);
                return titles;
            }

            if (titles.containsKey(lineData[0])) {
                titlesList = titles.get(lineData[0]);
                titlesList.add(creatTitleAkas(lineData));
            } else {
                titlesList = new ArrayList<>();
                titlesList.add(creatTitleAkas(lineData));
                titles.put(lineData[0], titlesList);
            }

        }
        akasReader.mark(markSize);
        return titles;
    }

    private HashMap<String, List<Work>> readTitlePrincipals(String lastId) throws IOException {
        int linesReaded = 0;
        String line;
        String[] lineData;
        List<Work> workList;
        HashMap<String, List<Work>> works = new HashMap<>();

        boolean last = false;

        while ((line = principalsReader.readLine()) != null){
            lineData = line.split("\t");

            if (lastId.equals(lineData[0]))
                last=true;
            if(last && !(lastId.equals(lineData[0]))
                    || Integer.parseInt(lastId.substring(2))
                    <
                    Integer.parseInt(lineData[0].substring(2))){
                akasReader.mark(markSize);
                return works;
            }

            if (works.containsKey(lineData[0])) {
                workList = works.get(lineData[0]);
                workList.add(createTitlePrincipals(lineData));
            } else {
                workList = new ArrayList<>();
                workList.add(createTitlePrincipals(lineData));
                works.put(lineData[0], workList);
            }

        }
        principalsReader.mark(markSize);
        return works;
    }

    private void readTitleCrew(HashMap<String, Film> films) throws IOException {
        int linesReaded = 0;
        String line;
        String[] lineData;
        Film currentFilm;
        String lastId = ".";

        while ((line = crewReader.readLine()) != null){
            lineData = line.split("\t");

            if (films.containsKey(lineData[0])) {
                currentFilm = films.get(lineData[0]);
                addDataTitleCrew(currentFilm, lineData);

                if (!lineData[0].equals(lastId)){
                    linesReaded++;
                }

                lastId = lineData[0];
            }

            if(linesReaded == (bulkSize)){
                crewReader.mark(markSize);
            }
            if(linesReaded >= bulkSize){
                return;
            }
        }
        crewReader.mark(markSize);
    }

    private void readTitleRatings(HashMap<String, Film> films) throws IOException {
        int linesReaded = 0;
        String line;
        String[] lineData;
        Film currentFilm;
        String lastId = "tt00000";

        while ((line = ratingsReader.readLine()) != null){
            lineData = line.split("\t");

            if (films.containsKey(lineData[0]) &&
                    Integer.parseInt(lastId.substring(2))
                    <
                    Integer.parseInt(lineData[0].substring(2))) {
                currentFilm = films.get(lineData[0]);
                addDataTitleRatings(currentFilm, lineData);


                    linesReaded++;

                lastId = lineData[0];
            }

            if(linesReaded == bulkSize){
                ratingsReader.mark(markSize);
                return;
            }
        }

        ratingsReader.mark(markSize);
    }

    private void readTitleEpisode() throws IOException {
        int linesReaded = 0;
        String line;
        String[] lineData;
        Episode currentEpisode;
        String lastId = ".";
        HashMap<String, Episode> episodes = new HashMap<>();

        while ((line = episodeReader.readLine()) != null){
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
        currentFilm.setId(lineData[0]);
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

    private Work createTitlePrincipals(String[] lineData){
        Work actualWork = new Work();
        actualWork.setFilmId(lineData[0]);
        actualWork.setPersonId(lineData[2]);
        actualWork.setCategory(lineData[3]);

        if (!lineData[4].equals("\\N")) {
            actualWork.setJob(lineData[4]);
        }

        if (!lineData[5].equals("\\N")) {
            actualWork.setCharacters(lineData[5]);
        }

        return actualWork;
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
