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

    boolean field1Active;
    boolean field2Active;
    boolean field3Active;
    boolean field4Active;
    boolean field5Active;


    String xLabel;
    String yLabel;
    public ChartMaintainer(String xLabel, String yLabel,boolean field1Active,boolean field2Active,boolean field3Active,boolean field4Active,boolean field5Active){
        this.field1Active = field1Active;
        this.field2Active = field2Active;
        this.field3Active = field3Active;
        this.field4Active = field4Active;
        this.field5Active = field5Active;
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

        lineChart.setCreateSymbols(false);
        if (field1Active) {lineChart.getData().add(grass);}
        if(field2Active){lineChart.getData().add(animals);}
        if(field3Active){lineChart.getData().add(avgEnergy);}
        if(field4Active){lineChart.getData().add(avgLifeLiength);}
        if(field5Active){lineChart.getData().add(avgChildrenNum);};
    }
    public LineChart createChart(){
        return lineChart;
    }

    @Override
    public void stepMade(Integer epoch, Float grasNumber, Float animalsNumber, Float avgEnergy, Float avgChildrenNum, Float avgLifeLength) {
        Platform.runLater(() -> {
            this.grass.getData().add(new XYChart.Data(epoch, grasNumber));
            this.animals.getData().add(new XYChart.Data(epoch, animalsNumber));
            this.avgEnergy.getData().add(new XYChart.Data(epoch, avgEnergy));
            this.avgLifeLiength.getData().add(new XYChart.Data(epoch, avgLifeLength));
            this.avgChildrenNum.getData().add(new XYChart.Data(epoch, avgChildrenNum));
        });
    }
}
