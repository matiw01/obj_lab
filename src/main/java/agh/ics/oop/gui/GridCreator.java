package agh.ics.oop.gui;

import agh.ics.oop.Animal;
import agh.ics.oop.IEngine;
import agh.ics.oop.IWorldMap;
import agh.ics.oop.Vector2d;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.concurrent.atomic.AtomicBoolean;

public class GridCreator {
    AtomicBoolean running;
    IEngine engine;
    GridPane grid;
    Vector2d lowerLeft;
    Vector2d upperRight;
    IWorldMap map;
    int columnWidth = 50;
    int rowWidth = 50;
    public GridCreator(IWorldMap map, GridPane grid, IEngine engine,AtomicBoolean running){
        this.running = running;
        this.engine = engine;
        this.grid = grid;
        this.map = map;
        Vector2d[] corrners = map.getCorrners();
        this.lowerLeft = corrners[0];
        this.upperRight = corrners[1];

    }
    public void createGrid(boolean start){
        ToggleButton toggleButton = new ToggleButton("Start");
        toggleButton.setSelected(!start);
        grid.add(toggleButton,900,10,100,100);
        //button starting or stoping simulation
        //animals make one move after stoping dont know why??
        toggleButton.setOnAction(event -> {
            running.set(toggleButton.isSelected());
            try{
                if( running.get()){
                    Thread t = new Thread(() -> {
                        while (running.get()) {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException ex) {
                                System.out.println(ex);
                            }

                            Platform.runLater(() -> {
                                engine.run();
                                grid.setGridLinesVisible(false);
                                grid.getColumnConstraints().clear();
                                grid.getRowConstraints().clear();
                                grid.getChildren().clear();
                                grid.setGridLinesVisible(true);
                                this.createGrid(!running.get());
                            });
                        }
                    });
                    t.setDaemon(true);
                    t.start();
                }
            }
            catch (Exception ex){
                System.out.println(ex.toString());
            }
        });
        //map labeling
        grid.getColumnConstraints().add(new ColumnConstraints(columnWidth));
        Label axisLabel = new Label("y\\x");
        grid.add(axisLabel,0,0);
        GridPane.setHalignment(axisLabel, HPos.CENTER);
        for (int i = lowerLeft.x; i<=upperRight.x; i++){
            Label label = new Label(Integer.toString(i));
            grid.add(label,i-lowerLeft.x+1,0);
            grid.getColumnConstraints().add(new ColumnConstraints(columnWidth));
            GridPane.setHalignment(label, HPos.CENTER);

        }
        grid.getRowConstraints().add(new RowConstraints(rowWidth));
        for (int i = upperRight.y; i>= lowerLeft.y; i--){
            Label label = new Label(Integer.toString(i));
            grid.add(label,0,upperRight.y-i+1);
            grid.getRowConstraints().add(new RowConstraints(rowWidth));
            GridPane.setHalignment(label, HPos.CENTER);
        }
        for (int i = lowerLeft.x; i<= upperRight.x; i++){
            for (int j = lowerLeft.y; j<= upperRight.y; j++){
                if(map.isOccupied(new Vector2d(i,j))){
                    Object object = map.objectAt(new Vector2d(i,j));
                    if (object instanceof Animal){
                        Image img = new Image("floppa1.jpg");
                        ImageView iv = new ImageView();
                        iv.setImage(img);
                        iv.setFitHeight(rowWidth);
                        iv.setFitWidth(columnWidth);
                        grid.add(iv,i+1-lowerLeft.x,upperRight.y-j+1);
                    }
                    else {
                        Image img = new Image("grass.jpg");
                        ImageView iv = new ImageView();
                        iv.setImage(img);
                        iv.setFitHeight(rowWidth);
                        iv.setFitWidth(columnWidth);
                        Label label = new Label(object.toString());
                        grid.add(iv, i + 1 - lowerLeft.x, upperRight.y - j + 1);
                        GridPane.setHalignment(label, HPos.CENTER);
                    }
                }
            }
        }
        grid.setGridLinesVisible(true);
    }
}
