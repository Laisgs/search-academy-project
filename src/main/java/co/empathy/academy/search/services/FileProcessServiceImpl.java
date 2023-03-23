package co.empathy.academy.search.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileProcessServiceImpl implements FileProcessService{
    private static final int NAMEBASISC = 0;
    private static final int TITLEAKAS = 1;
    private static final int TITLEBASICS = 2;
    private static final int TITLECREW = 3;
    private static final int TITLEEPISODE = 4;
    private static final int TITLEPRINCIPALS = 5;
    private static final int TITLERATINGS = 6;

    private File tmpFile;
    @Override
    public void save(MultipartFile file) throws IOException {
        writeToTemporalFile2(file);
        readData();
        tmpFile.delete();
    }

    private void writeToTemporalFile(MultipartFile file) throws IOException {
        tmpFile = File.createTempFile("IMDB-Search-App", ".tmp");
        //FileOutputStream writer = new FileOutputStream(tmpFile);
        BufferedWriter writer = new BufferedWriter(new FileWriter(tmpFile));
        String toWrite;
        //InputStream fileData = file.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));


        while ((toWrite =reader.readLine()) != null){
            writer.write(toWrite);
        }

        writer.close();
    }

    private void writeToTemporalFile2(MultipartFile file) throws IOException {
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

    private void readData(){
        BufferedReader reader;
        try{
            String line;
            String[] lineData;
            List<String[]> list = new ArrayList<>();

            reader = new BufferedReader(new FileReader(tmpFile));

            int dataSetType = getDataSetType(line = reader.readLine());

            while ((line = reader.readLine()) != null){
                lineData = line.split("\t");



                //list.add(lineData);
                System.out.println(lineData[0]);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private int getDataSetType(String firstLine) {
        String[] columsNames = firstLine.split("\t");

        switch (columsNames[0]){
            case "primaryName":
                return NAMEBASISC;
            case "titleId":
                return TITLEAKAS;
            case "titleType":
                return TITLEBASICS;
            case "directors":
                return TITLECREW;
            case "parentTconst":
                return TITLEEPISODE;
            case "primaryName":
                return TITLEPRINCIPALS;
            case "primaryName":
                return TITLERATINGS;
        }
    }
}
