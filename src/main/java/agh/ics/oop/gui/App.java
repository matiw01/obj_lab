package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class App extends Application {
    private GrassField map;
    private Vector2d[] positions;
    private IEngine engine;
    private Vector2d lowerLeft;
    private Vector2d upperRight;

    @Override
    public void start(Stage primaryStage) throws Exception {
        List<String> parameters = getParameters().getRaw();
        GridPane grid = new GridPane();
        grid.getColumnConstraints().add(new ColumnConstraints(20));
        Label axisLabel = new Label("y\\x");
        grid.add(axisLabel,0,0);
        GridPane.setHalignment(axisLabel, HPos.CENTER);
        for (int i = lowerLeft.x; i<=upperRight.x; i++){
            Label label = new Label(Integer.toString(i));
            grid.add(label,i-lowerLeft.x+1,0);
            grid.getColumnConstraints().add(new ColumnConstraints(20));
            GridPane.setHalignment(label, HPos.CENTER);

        }
        grid.getRowConstraints().add(new RowConstraints(20));
        for (int i = upperRight.y; i>= lowerLeft.y; i--){
            Label label = new Label(Integer.toString(i));
            grid.add(label,0,upperRight.y-i+1);
            grid.getRowConstraints().add(new RowConstraints(20));
            GridPane.setHalignment(label, HPos.CENTER);
        }
        for (int i = lowerLeft.x; i<= upperRight.x; i++){
            for (int j = lowerLeft.y; j<= upperRight.y; j++){
                if(map.isOccupied(new Vector2d(i,j))){
                    Object object = map.objectAt(new Vector2d(i,j));
                    Label label = new Label(object.toString());
                    grid.add(label,i+1-lowerLeft.x,upperRight.y-j+1);
                    GridPane.setHalignment(label, HPos.CENTER);
                }
            }
        }
        grid.setGridLinesVisible(true);
        Scene scene = new Scene(grid, 400, 400);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
    @Override
    public void init(){
        List<String> parameters = getParameters().getRaw();
        try {
            String[] stringDirections = new String[parameters.size()];
            parameters.toArray(stringDirections);
            ArrayList<MoveDirection> directions = new OptionsParser().parse(stringDirections);
            map = new GrassField(10);
            positions = new Vector2d[]{new Vector2d(2, 5), new Vector2d(3, 4)};
            engine = new SimulationEngine(directions, map, positions);
            engine.run();
            Vector2d[] corners = map.getCorrners();
            lowerLeft = corners[0];
            upperRight = corners[1];

        }
        catch (IllegalArgumentException ex){
            System.out.println(ex);
            System.exit(0);
        }
    }
}
