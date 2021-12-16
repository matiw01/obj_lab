package agh.ics.oop.gui;

import agh.ics.oop.IEngineObserver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StatisticsHandler implements IEngineObserver{
    List<String[]> statistics = new ArrayList<>();
    public StatisticsHandler(){
    }
    public String convertToCSV(String[] statistics) {
        return String.join(",", statistics);
    }

    public void saveIntoFile(String fileName) throws IOException {
            try {
                Path newFilePath = Paths.get(fileName);
                Files.createFile(newFilePath);

            }
            catch (FileAlreadyExistsException ex){
                File csvOutputfile = new File(fileName);
                csvOutputfile.delete();
                Path newFilePath = Paths.get(fileName);
                Files.createFile(newFilePath);
            }
            File csvOutputFile = new File(fileName);
            try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
                statistics.stream()
                        .map(this::convertToCSV)
                        .forEach(pw::println);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

    }

    @Override
    public void stepMade(Integer epoch, Integer grasNumber, Integer animalsNumber, float avgEnergy, float avgChildrenNum, float avgLifeLength) {
        statistics.add(new String[]{grasNumber.toString(), animalsNumber.toString(), ((Float) avgEnergy).toString(), ((Float) avgChildrenNum).toString(),((Float) avgLifeLength).toString()});
    }
}
