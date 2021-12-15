package agh.ics.oop.gui;

import agh.ics.oop.IEngineObserver;
import javafx.application.Platform;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class ChartMaintainer implements IEngineObserver {
    LineChart<Integer, Integer> lineChart;
    XYChart.Series<Integer,Integer> dataSeries1;
    XYChart.Series<Integer,Integer> dataSeries2;
    String xLabel;
    String yLabel;
    public ChartMaintainer(String xLabel, String yLabel, Integer startGrasNum, Integer startAnimalsNum){
        this.xLabel = xLabel;
        this.yLabel = yLabel;
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel(xLabel);

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel(yLabel);

        this.dataSeries1 = new XYChart.Series();
        this.dataSeries1.setName("Grass");
        this.dataSeries2 = new XYChart.Series();
        this.dataSeries2.setName("Animals");

        this.lineChart = new LineChart(xAxis, yAxis);

        dataSeries1.getData().add(new XYChart.Data( 0, startGrasNum));
        dataSeries2.getData().add(new XYChart.Data( 0, startAnimalsNum));
        lineChart.getData().add(dataSeries1);
        lineChart.getData().add(dataSeries2);
    }
    public LineChart createChart(){
        return lineChart;
    }

    @Override
    public void stepMade(Integer epoch, Integer grasNumber, Integer animalsNumber) {
        Platform.runLater(() -> {
            dataSeries1.getData().add(new XYChart.Data(epoch, grasNumber));
            dataSeries2.getData().add(new XYChart.Data(epoch, animalsNumber));
        });
    }
}
