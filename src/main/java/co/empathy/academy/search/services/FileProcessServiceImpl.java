package co.empathy.academy.search.services;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileProcessServiceImpl implements FileProcessService{
    private File tmpFile;
    @Override
    @Async
    public void save(MultipartFile file) throws IOException {
        readData(file);
    }

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

        while ((line = reader.readLine()) != null){
            lineData = line.split("\t");

            //System.out.println(lineData[0]);
        }
    }

    private void readTitleCrew(BufferedReader reader) throws IOException {
        int linesReaded = 0;
        String line;
        String[] lineData;

        while ((line = reader.readLine()) != null){
            lineData = line.split("\t");

            //System.out.println(lineData[0]);
        }
    }

    private void readTitleEpisode(BufferedReader reader) throws IOException {
        int linesReaded = 0;
        String line;
        String[] lineData;

        while ((line = reader.readLine()) != null){
            lineData = line.split("\t");

            //System.out.println(lineData[0]);
        }
    }

    private void readTitlePrincipals(BufferedReader reader) throws IOException {
        int linesReaded = 0;
        String line;
        String[] lineData;

        while ((line = reader.readLine()) != null){
            lineData = line.split("\t");

            //System.out.println(lineData[0]);
        }
    }

    private void readTitleRatings(BufferedReader reader) throws IOException {
        int linesReaded = 0;
        String line;
        String[] lineData;

        while ((line = reader.readLine()) != null){
            lineData = line.split("\t");

            //System.out.println(lineData[0]);
        }
    }
}
