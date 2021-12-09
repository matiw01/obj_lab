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
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Thread.sleep;

public class App extends Application {
    private Integer mapWidth;
    private Integer mapHeiht;
    private Integer jungleRatio;
    private Integer numberOfAnimals;
    private Integer startEnergy;
    private Integer moveEnergy;
    private Integer plantEnergy;
    private IWorldMap map;
    private Vector2d[] positions;
    private SimulationEngine engine;
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
        TextField tf4 = new TextField("10");
        TextField tf5 = new TextField("50");
        TextField tf6 = new TextField("10");
        TextField tf7 = new TextField("50");
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

        pane.getChildren().addAll(l1, tf1, l2, tf2, l3, tf3, l4, tf4, l5, tf5, l6, tf6, l7, tf7);
        // call on the labels and text fields to be placed into the window

        VBox vBox = new VBox();
        Button startButton = new Button("Start");
        // create a box to put the button in and name the button

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
        map = new RectangularMap(mapWidth, mapHeiht, 50, plantEnergy);
        engine = new SimulationEngine(map, numberOfAnimals, startEnergy, moveEnergy);
        map.addObserver((IMapObserver) engine);
        Vector2d[] corrners = map.getCorrners();
        lowerLeft = corrners[0];
        upperRight = corrners[1];

        GridPane grid = new GridPane();
        AtomicBoolean running = new AtomicBoolean();
        GridCreator gridCreator = new GridCreator(map, grid, engine, running);
        gridCreator.createGrid(true);

        Scene scene = new Scene(grid, 1500, 1000);
        simulationStage.setScene(scene);
        simulationStage.show();


    }
}
