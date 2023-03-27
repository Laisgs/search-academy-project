package co.empathy.academy.search.services;

import co.empathy.academy.search.entities.Film;
import co.empathy.academy.search.users.entities.User;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.springframework.cglib.core.Local;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class FileProcessServiceImpl implements FileProcessService{
    private boolean nameBasics;
    private boolean titleAkas;
    private boolean titleBasics;
    private boolean titleCrew;
    private boolean titleEpisode;
    private boolean titlePrincipals;
    private boolean titleRatings;

    private ConcurrentHashMap<String, Film> films = new ConcurrentHashMap<>();

    private void sendToElastic(){
        if(nameBasics && titleAkas && titleBasics
                && titleCrew && titleEpisode
                && titlePrincipals && titleRatings){
            //send
        }
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

        while ((line = reader.readLine()) != null){
            lineData = line.split("\t");

            //System.out.println(lineData[0]);
        }
    }

    private void readTitleBasics(BufferedReader reader) throws IOException {
        int linesReaded = 0;
        String line;
        String[] lineData;
        Film currentFilm;

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

            linesReaded++;
        }

        titleBasics = true;

        sendToElastic();
    }

    private void readTitleCrew(BufferedReader reader) throws IOException {
        int linesReaded = 0;
        String line;
        String[] lineData;
        Film currentFilm;

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

            linesReaded++;
        }

        films.forEach((key, value) -> System.out.println(value.toString()));

        titleCrew = true;
        sendToElastic();
    }

    private void readTitleEpisode(BufferedReader reader) throws IOException {
        int linesReaded = 0;
        String line;
        String[] lineData;
        Film currentFilm;

        while ((line = reader.readLine()) != null){
            lineData = line.split("\t");

            if (films.containsKey(lineData[0])) {
                currentFilm = films.get(lineData[0]);
                addDataTitleEpisode(currentFilm, lineData);
            } else {
                currentFilm = new Film();
                addDataTitleEpisode(currentFilm, lineData);
                films.put(lineData[0], currentFilm);
            }

            linesReaded++;
        }

        films.forEach((key, value) -> System.out.println(value.toString()));

        titleEpisode = true;
        sendToElastic();
    }

    private void readTitlePrincipals(BufferedReader reader) throws IOException {
        int linesReaded = 0;
        String line;
        String[] lineData;
        Film currentFilm;

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

            linesReaded++;
        }

        films.forEach((key, value) -> System.out.println(value.toString()));

        titlePrincipals = true;
        sendToElastic();
    }

    private void readTitleRatings(BufferedReader reader) throws IOException {
        int linesReaded = 0;
        String line;
        String[] lineData;
        Film currentFilm;

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

            linesReaded++;
        }

        films.forEach((key, value) -> System.out.println(value.toString()));

        titleRatings = true;
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
        currentFilm.setStartYear(Integer.parseInt(lineData[5]));

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

    private void addDataTitleEpisode(Film currentFilm, String[] lineData){

    }

    private void addDataTitlePrincipals(Film currentFilm, String[] lineData){

    }

    private void addDataTitleRatings(Film currentFilm, String[] lineData){
        currentFilm.setAverageRating(Double.parseDouble(lineData[1]));
        currentFilm.setNumberOfVotes(Integer.parseInt(lineData[2]));
    }
}
