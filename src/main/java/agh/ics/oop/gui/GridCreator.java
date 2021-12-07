package agh.ics.oop.gui;

import agh.ics.oop.IWorldMap;
import agh.ics.oop.Vector2d;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class GridCreator {
    Vector2d lowerLeft;
    Vector2d upperRight;
    IWorldMap map;
    int columnWidth = 25;
    int rowWidth = 25;
    public GridCreator(IWorldMap map){
        this.map = map;
        Vector2d[] corrners = map.getCorrners();
        this.lowerLeft = corrners[0];
        this.upperRight = corrners[1];

    }
    public GridPane createGrid(){
        GridPane grid = new GridPane();
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
                    Label label = new Label(object.toString());
                    grid.add(label,i+1-lowerLeft.x,upperRight.y-j+1);
                    GridPane.setHalignment(label, HPos.CENTER);
                }
            }
        }
        grid.setGridLinesVisible(true);
        return grid;
    }
}
