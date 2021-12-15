package agh.ics.oop.gui;

import agh.ics.oop.IEngineObserver;
import javafx.application.Platform;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class ChartMaintainer implements IEngineObserver {
    LineChart<Integer, Integer> lineChart;
    XYChart.Series<Integer,Integer> grass;
    XYChart.Series<Integer,Integer> animals;
    XYChart.Series<Integer,Integer> avgEnergy;
    XYChart.Series<Integer,Integer> avgLifeLiength;
    XYChart.Series<Integer,Integer> avgChildrenNum;

    String xLabel;
    String yLabel;
    public ChartMaintainer(String xLabel, String yLabel, Integer startGrasNum, Integer startAnimalsNum){
        this.xLabel = xLabel;
        this.yLabel = yLabel;
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel(xLabel);

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel(yLabel);

        this.grass = new XYChart.Series();
        this.grass.setName("Grass");
        this.animals = new XYChart.Series();
        this.animals.setName("Animals");
        this.avgEnergy = new XYChart.Series<>();
        this.avgEnergy.setName("average energy");
        this.avgLifeLiength = new XYChart.Series<>();
        this.avgLifeLiength.setName("average life length");
        this.avgChildrenNum = new XYChart.Series<>();
        this.avgChildrenNum.setName("average children number");

        this.lineChart = new LineChart(xAxis, yAxis);

        lineChart.getData().add(grass);
        lineChart.getData().add(animals);
        lineChart.getData().add(avgEnergy);
        lineChart.getData().add(avgLifeLiength);
        lineChart.getData().add(avgChildrenNum);
    }
    public LineChart createChart(){
        return lineChart;
    }

    @Override
    public void stepMade(Integer epoch, Integer grasNumber, Integer animalsNumber, float avgEnergy, float avgChildrenNum, float avgLifeLength) {
        Platform.runLater(() -> {
            this.grass.getData().add(new XYChart.Data(epoch, grasNumber));
            this.animals.getData().add(new XYChart.Data(epoch, animalsNumber));
            this.avgEnergy.getData().add(new XYChart.Data(epoch, avgEnergy));
            this.avgLifeLiength.getData().add(new XYChart.Data(epoch, avgLifeLength));
            this.avgChildrenNum.getData().add(new XYChart.Data(epoch, avgChildrenNum));
        });
    }
}
