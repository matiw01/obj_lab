package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Thread.sleep;

public class App extends Application {
    boolean flippedMagicStrategy;
    boolean rectangularMagicStrategy;
    private Integer mapWidth;
    private Integer mapHeiht;
    private Integer jungleRatio;
    private Integer numberOfAnimals;
    private Integer startEnergy;
    private Integer moveEnergy;
    private Integer plantEnergy;
    private FlippedMap flippedMap;
    private RectangularMap rectangularMap;
    private Vector2d[] positions;
    private SimulationEngine flippedEngine;
    private SimulationEngine rectangularEngine;
    private Vector2d lowerLeft;
    private Vector2d upperRight;
    AtomicBoolean flippedRunning;
    AtomicBoolean rectangularRunning;

    @Override
    public void start(Stage primaryStage) throws InterruptedException {

        FlowPane pane = new FlowPane(Orientation.VERTICAL);
        pane.setHgap(5);
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(0, 0, 0, 0)); // set top, right, bottom, left
        // this allows input fields to be placed in the window

        TextField tf1 = new TextField("10");
        TextField tf2 = new TextField("10");
        TextField tf3 = new TextField("10");
        TextField tf4 = new TextField("30");
        TextField tf5 = new TextField("80");
        TextField tf6 = new TextField("6");
        TextField tf7 = new TextField("150");
        // these are the individual fields for input, you can set the default text

        // keeps user from changing this text field

        tf1.setPrefColumnCount(14);
        tf2.setPrefColumnCount(14);
        tf3.setPrefColumnCount(14);
        tf4.setPrefColumnCount(14);
        tf5.setPrefColumnCount(14);
        tf6.setPrefColumnCount(14);
        tf7.setPrefColumnCount(14);
        // this sets the number of spaces in the text fields

        Label l1 = new Label("Map Width");
        Label l2 = new Label("Map Height");
        Label l3 = new Label("Jungle Ratio");
        Label l4 = new Label("Number of Animals");
        Label l5 = new Label("Start Energy");
        Label l6 = new Label("Move Energy");
        Label l7 = new Label("Plant Energy");
        // these labels are set and used before the text fields as show below

        // create a box to put the button in and name the button
        CheckBox flippedCheckBox = new CheckBox("Chose magic strategy for flipped map");
        CheckBox rectangularCheckBox = new CheckBox("Chose magic strategy for rectangular map");
//        HBox checkBoxHBox  = new HBox(flippedCheckBox, rectangularCheckBox);
        pane.getChildren().addAll(l1, tf1, l2, tf2, l3, tf3, l4, tf4, l5, tf5, l6, tf6, l7, tf7, flippedCheckBox, rectangularCheckBox);
        // call on the labels and text fields to be placed into the window

        VBox vBox = new VBox();
        Button startButton = new Button("Start");


        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(startButton);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(pane);
        borderPane.setBottom(vBox);
        BorderPane.setAlignment(vBox, Pos.CENTER);

        Scene scene = new Scene(borderPane, 500, 500);
        primaryStage.setTitle("Getting Started");
        primaryStage.setScene(scene);
        primaryStage.show();


        startButton.setOnAction(event -> {
            mapWidth = Integer.valueOf(tf1.getText());
            mapHeiht = Integer.valueOf(tf2.getText());
            jungleRatio = Integer.valueOf(tf3.getText());
            numberOfAnimals = Integer.valueOf(tf4.getText());
            startEnergy = Integer.valueOf(tf5.getText());
            moveEnergy = Integer.valueOf(tf6.getText());
            plantEnergy = Integer.valueOf(tf7.getText());
            flippedMagicStrategy = flippedCheckBox.isSelected();
            rectangularMagicStrategy = rectangularCheckBox.isSelected();
            if (mapWidth == null || mapHeiht == null || jungleRatio == null || numberOfAnimals == null || startEnergy == null || plantEnergy == null || moveEnergy == null) {
                throw new IllegalArgumentException("You have to put in all arguments");
            }
            primaryStage.close();
            try {
                simulation();
            } catch (InterruptedException | IOException ex) {
                System.out.println(ex.toString());
                System.exit(0);
            }
        });


    }

    private void simulation() throws InterruptedException, IOException {
        Stage simulationStage = new Stage();
        flippedMap = new FlippedMap(mapWidth, mapHeiht, plantEnergy, jungleRatio);
        rectangularMap = new RectangularMap(mapWidth, mapHeiht, plantEnergy, jungleRatio);
        flippedEngine = new SimulationEngine(flippedMap, numberOfAnimals, startEnergy, moveEnergy, flippedMagicStrategy);
        rectangularEngine = new SimulationEngine(rectangularMap, numberOfAnimals, startEnergy, moveEnergy, rectangularMagicStrategy);
        flippedMap.addObserver((IMapObserver) flippedEngine);
        rectangularMap.addObserver((IMapObserver) rectangularEngine);
        Vector2d[] corrners = rectangularMap.getCorrners();
        lowerLeft = corrners[0];
        upperRight = corrners[1];

//        GridPane grid1 = new GridPane();
//        GridPane grid2 = new GridPane();
        //flipped chart
        ChartMaintainer flippedChartManitainer1 = new ChartMaintainer("epoh", "",true,true,false,true,true);
        LineChart<Integer, Integer> flippedLineChart1 = flippedChartManitainer1.createChart();
        ChartMaintainer flippedChartManitainer2 = new ChartMaintainer("epoh", "",false,false,true,false,false);
        LineChart<Integer, Integer> flippedLineChart2 = flippedChartManitainer2.createChart();
        flippedEngine.addObserver(flippedChartManitainer1);
        flippedEngine.addObserver(flippedChartManitainer2);

        //fliped floppa info
        TabelMaintainer flipedTableMaintainer = new TabelMaintainer();
        TableView flippedTable =flipedTableMaintainer.createTable();
        flippedEngine.addObserver(flipedTableMaintainer);

        //rectangular chart
        ChartMaintainer rectangularChartMaintainer = new ChartMaintainer("epoh","",true,true,false,true,true);
        LineChart<Integer, Integer> rectanularLineChart1 = rectangularChartMaintainer.createChart();
        ChartMaintainer rectangularChartManitainer2 = new ChartMaintainer("epoh", "",false,false,true,false,false);
        LineChart<Integer, Integer> rectangularLineChart2 = rectangularChartManitainer2.createChart();
        rectangularEngine.addObserver(rectangularChartMaintainer);
        rectangularEngine.addObserver(rectangularChartManitainer2);


        //rectangular floppa info
        TabelMaintainer recangularTableMaintainer = new TabelMaintainer();
        TableView rectangularTable = recangularTableMaintainer.createTable();
        rectangularEngine.addObserver(recangularTableMaintainer);

        //statistics handler
        StatisticsHandler flippedStatisticsHandler = new StatisticsHandler();
        StatisticsHandler rectangularStatisticsHandler = new StatisticsHandler();
        flippedEngine.addObserver((IEngineObserver) flippedStatisticsHandler);
        rectangularEngine.addObserver((IEngineObserver) rectangularStatisticsHandler);
        //buttons for saveing statistics
        Button flippedStatisticsButton = new Button("Save Statistics");
        flippedStatisticsButton.setOnAction(event -> {
            try {
                flippedStatisticsHandler.saveIntoFile("statistics1");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        Button rectangularStatisticsButton = new Button("Save Statistics");
        rectangularStatisticsButton.setOnAction(event -> {
            try {
                rectangularStatisticsHandler.saveIntoFile("statistics2");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        flippedRunning = new AtomicBoolean();
        rectangularRunning = new AtomicBoolean();
        Thread flippedThread = new Thread((Runnable) flippedEngine);
        flippedThread.start();
        Thread rectangularThread = new Thread((Runnable) rectangularEngine);
        rectangularThread.start();
        GridCreator flippedgridCreator = new GridCreator(flippedMap, flipedTableMaintainer, flippedEngine, flippedRunning);
        GridCreator rectangularGridCreator = new GridCreator(rectangularMap, recangularTableMaintainer, rectangularEngine, rectangularRunning);
        GridPane flippedGrid = flippedgridCreator.createGrid();
        GridPane rectangularGrid = rectangularGridCreator.createGrid();
        //togle button starting and stoping flipedMap
        ToggleButton flippedToggleButon = new ToggleButton("start");
        flippedToggleButon.setOnAction(event -> {
            flippedRunning.set(flippedToggleButon.isSelected());
            flippedEngine.setShouldRun(flippedToggleButon.isSelected());
            if (!flippedToggleButon.isSelected()){
                flippedgridCreator.updateGrid(flippedEngine.getEpoch());
            }
        });
        //togle button starting and stoping rectangularMap
        ToggleButton rectangularToggleButon = new ToggleButton("start");
        rectangularToggleButon.setOnAction(event -> {
            rectangularRunning.set(rectangularToggleButon.isSelected());
            rectangularEngine.setShouldRun(rectangularToggleButon.isSelected());
            if (!rectangularToggleButon.isSelected()){
                rectangularGridCreator.updateGrid(rectangularEngine.getEpoch());
            }
        });
        HBox flippedControlButtons = new HBox(flippedStatisticsButton, flippedToggleButon);

        HBox recangularControlButtons = new HBox(rectangularStatisticsButton, rectangularToggleButon);
        flippedgridCreator.updateGrid(0);
        rectangularGridCreator.updateGrid(0);
        flippedEngine.addObserver(flippedgridCreator);
        rectangularEngine.addObserver(rectangularGridCreator);
        HBox flippedChartBox = new HBox(flippedLineChart1,flippedLineChart2);
        flippedChartBox.setMaxWidth(1000);
        VBox flipedVBox = new VBox(flippedGrid, flippedControlButtons, flippedTable,  flippedChartBox);

        HBox rectangularChartBox = new HBox(rectanularLineChart1,rectangularLineChart2);
        rectangularChartBox.setMaxWidth(1000);

        VBox rectangularVBox = new VBox(rectangularGrid, recangularControlButtons, rectangularTable, rectangularChartBox);
        HBox hBox = new HBox(flipedVBox, rectangularVBox);
        Scene scene = new Scene(hBox, 1500, 1000);
        simulationStage.setScene(scene);
        simulationStage.setFullScreen(true);
        simulationStage.show();


    }
}
