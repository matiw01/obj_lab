package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.util.concurrent.atomic.AtomicBoolean;

public class GridCreator implements IEngineObserver {

    Image animalImg = new Image("floppa in the bath.jpg");
    Image grassImg = new Image("grass.jpg");
    AtomicBoolean running;
    SimulationEngine engine;
    GridPane grid;
    Vector2d lowerLeft;
    Vector2d upperRight;
    IWorldMap map;
    int columnWidth;
    int rowWidth;
    TabelMaintainer tabelMaintainer;
    public GridCreator(IWorldMap map,TabelMaintainer tabelMaintainer, SimulationEngine engine, AtomicBoolean running){
        this.grid = new GridPane();
        this.tabelMaintainer = tabelMaintainer;
        this.running = running;
        this.engine = engine;
        this.map = map;
        this.columnWidth = 500/Math.max(map.getMapWidth(), map.getMapHeight());
        this.rowWidth = 500/Math.max(map.getMapWidth(), map.getMapHeight());
        Vector2d[] corrners = map.getCorrners();
        this.lowerLeft = corrners[0];
        this.upperRight = corrners[1];



    }

    public GridPane createGrid(){return this.grid;}
    public void updateGrid(Integer epoch){
        grid.setGridLinesVisible(false);
        grid.getColumnConstraints().clear();
        grid.getRowConstraints().clear();
        grid.getChildren().clear();
        grid.setGridLinesVisible(true);
        //toggleButton grup
        ToggleGroup toggleFloppaGroup = new ToggleGroup();
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
                        ImageView iv = new ImageView();
                        iv.setImage(animalImg);
                        iv.setFitHeight(rowWidth);
                        iv.setFitWidth(columnWidth);
                        ColorAdjust colorAdjust = new ColorAdjust();
                        colorAdjust.setBrightness(Math.max(1f-((float)((Animal) object).getEnergy()/((float)((Animal) object).getProcreateEnergy()*2)),0));
                        iv.setEffect(colorAdjust);
                        if (((Animal) object).isFollowed()){
                            ColorAdjust colorAdjust1 = new ColorAdjust();
                            colorAdjust1.setContrast(0.4);
                            colorAdjust1.setHue(-0.05);
                            colorAdjust1.setBrightness(0.4);
                            colorAdjust1.setSaturation(0.8);
                            iv.setEffect(colorAdjust1);
                        }
                        grid.add(iv,i+1-lowerLeft.x,upperRight.y-j+1);
                        //add floppa button alowing to show info about floppa
                        if(!this.engine.getShouldRun()) {
                            ToggleButton toggleFloppaButton = new ToggleButton();
                            toggleFloppaButton.setToggleGroup(toggleFloppaGroup);
                            toggleFloppaButton.setBackground(null);
                            toggleFloppaButton.setPrefWidth(columnWidth);
                            toggleFloppaButton.setPrefHeight(rowWidth);
                            grid.add(toggleFloppaButton, i + 1 - lowerLeft.x, upperRight.y - j + 1);
                            if (((Animal) object).isDomininat() && !((Animal) object).isFollowed()){
                                ColorAdjust colorAdjust2 = new ColorAdjust();
                                colorAdjust2.setContrast(0.2);
                                colorAdjust2.setHue(-0.05);
                                colorAdjust2.setBrightness(0.4);
                                colorAdjust2.setSaturation(0.2);
                                iv.setEffect(colorAdjust2);
                            }
                            toggleFloppaButton.setSelected(((Animal) object).isFollowed());
                            toggleFloppaButton.setOnAction(event -> {
                                if (toggleFloppaButton.isSelected()) {
                                    tabelMaintainer.setFollowedAnimal((Animal) object);
                                    tabelMaintainer.updateTable();
                                }
                                else {
                                    tabelMaintainer.removeFollowedAnimal();
                                    tabelMaintainer.updateTable();
                                }
                                this.updateGrid(epoch);
                            });
                        }
                    }
                    else {
                        ImageView iv = new ImageView();
                        iv.setImage(grassImg);
                        iv.setFitHeight(rowWidth);
                        iv.setFitWidth(columnWidth);
                        Label label = new Label(object.toString());
                        grid.add(iv, i + 1 - lowerLeft.x, upperRight.y - j + 1);
                        GridPane.setHalignment(label, HPos.CENTER);
                    }
                }
            }
        }
//        grid.add(new Label("grass number"),11,6);
//        grid.add(new Label(map.getGrassNum().toString()),11,7);
//        grid.add(new Label("animals number"),11,8);
//        grid.add(new Label(engine.getAnimalsNum().toString()),11,9);
//        grid.add(new Label("epoch"), 11, 10);
//        grid.add(new Label(epoch.toString()),11,11);
        grid.setGridLinesVisible(true);
    }

    @Override
    public void stepMade(Integer epoch, Float grasNumber, Float animalsNumber, Float avgEnergy, Float avgChildrenNum, Float avgLifeLength) {
       Platform.runLater(() -> {updateGrid(epoch);});
    }
}