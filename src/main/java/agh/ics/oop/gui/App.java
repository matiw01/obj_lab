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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Thread.sleep;

public class App extends Application {
    boolean magicStrategy;
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
        CheckBox checkBox = new CheckBox("Chose magic strategy");
        pane.getChildren().addAll(l1, tf1, l2, tf2, l3, tf3, l4, tf4, l5, tf5, l6, tf6, l7, tf7, checkBox);
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
            magicStrategy = checkBox.isSelected();
            if (mapWidth == null || mapHeiht == null || jungleRatio == null || numberOfAnimals == null || startEnergy == null || plantEnergy == null || moveEnergy == null) {
                throw new IllegalArgumentException("You have to put in all arguments");
            }
            primaryStage.close();
            try {
                Simulation();
            } catch (InterruptedException ex) {
                System.out.println(ex.toString());
                System.exit(0);
            }
        });


    }

    private void Simulation() throws InterruptedException {
        Stage simulationStage = new Stage();
        flippedMap = new FlippedMap(mapWidth, mapHeiht, plantEnergy, jungleRatio);
        rectangularMap = new RectangularMap(mapWidth, mapHeiht, plantEnergy, jungleRatio);
        flippedEngine = new SimulationEngine(flippedMap, numberOfAnimals, startEnergy, moveEnergy, magicStrategy);
        rectangularEngine = new SimulationEngine(rectangularMap, numberOfAnimals, startEnergy, moveEnergy, magicStrategy);
        flippedMap.addObserver((IMapObserver) flippedEngine);
        rectangularMap.addObserver((IMapObserver) rectangularEngine);
        Vector2d[] corrners = rectangularMap.getCorrners();
        lowerLeft = corrners[0];
        upperRight = corrners[1];

        GridPane grid1 = new GridPane();
        GridPane grid2 = new GridPane();
        //flipped chart
        ChartMaintainer flippedChartManitainer = new ChartMaintainer("epoh", "",0,numberOfAnimals);
        LineChart<Integer, Integer> flippedLineChart = flippedChartManitainer.createChart();
        flippedEngine.addObserver(flippedChartManitainer);

        //fliped floppa info
        TabelMaintainer flipedTableMaintainer = new TabelMaintainer();
        TableView flippedTable =flipedTableMaintainer.createTable();
        flippedEngine.addObserver(flipedTableMaintainer);

        //rectangular chart
        ChartMaintainer rectangularChartMaintainer = new ChartMaintainer("epoh","",0,numberOfAnimals);
        LineChart<Integer, Integer> rectanularLineChart = rectangularChartMaintainer.createChart();
        rectangularEngine.addObserver(rectangularChartMaintainer);

        //rectangular floppa info
        TabelMaintainer recangularTableMaintainer = new TabelMaintainer();
        TableView rectangularTable = recangularTableMaintainer.createTable();
        rectangularEngine.addObserver(recangularTableMaintainer);

        VBox flipedVBox = new VBox(grid1, flippedTable,  flippedLineChart);
        VBox rectangularVBox = new VBox(grid2, rectangularTable, rectanularLineChart);

        HBox hBox = new HBox(flipedVBox, rectangularVBox);
        AtomicBoolean flippefRunning = new AtomicBoolean();
        AtomicBoolean rectangularRunning = new AtomicBoolean();
        GridCreator gridflippedCreator = new GridCreator(flippedMap, flipedTableMaintainer, grid1, flippedEngine, flippefRunning);
        GridCreator gridrectangularCreator = new GridCreator(rectangularMap, recangularTableMaintainer , grid2, rectangularEngine, rectangularRunning);
        gridflippedCreator.createGrid(true);
        gridrectangularCreator.createGrid(true);
        Scene scene = new Scene(hBox, 1500, 1000);
        simulationStage.setScene(scene);
        simulationStage.setFullScreen(true);
        simulationStage.show();


    }
}
