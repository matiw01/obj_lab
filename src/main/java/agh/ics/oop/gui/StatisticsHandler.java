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
    List<Float[]> statistics = new ArrayList<>();
    public StatisticsHandler(){
    }
    public String convertToCSV(String[] statistics) {
        return String.join(",", statistics);
    }

    public void saveIntoFile(String fileName) throws IOException {
        Float avgGrasNum = summaryOfPos(0);
        Float avgAnimalsNum = summaryOfPos(1);
        Float avgSummaryEnergy = summaryOfPos(2);
        Float avgSummaryChildrenNum = summaryOfPos(3);
        Float avgSummaryLifeLength = summaryOfPos(4);

        List<String[]> stringStatistics = new ArrayList<>();
        for (Float[] stat : statistics){
            String[] stringStat = new String[stat.length];
            for (int i = 0; i < stat.length; i++){
                stringStat[i] = stat[i].toString();
            }
            stringStatistics.add(stringStat);
        }
        stringStatistics.add(new String[]{avgGrasNum.toString() , avgAnimalsNum.toString() , avgSummaryEnergy.toString() , avgSummaryChildrenNum.toString() , avgSummaryLifeLength.toString() });
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
            stringStatistics.stream()
                    .map(this::convertToCSV)
                    .forEach(pw::println);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stepMade(Integer epoch, Float grasNumber, Float animalsNumber, Float avgEnergy, Float avgChildrenNum, Float avgLifeLength) {
        statistics.add(new Float[]{(Float) grasNumber, animalsNumber, avgEnergy, avgChildrenNum,avgLifeLength});
    }

    private Float summaryOfPos(int n){
        Float num = 0f;
        Float sum = 0f;
        for (Float[] stat : statistics){
            num += 1;
            sum += stat[n];
        }
        return sum/num;
    }


}

