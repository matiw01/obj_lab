package agh.ics.oop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FlippedMap extends AbstractWorldMap implements IWorldMap{
    int grasNum;
    private Integer plantEnergy;
    public FlippedMap(int width,int height, int grasNum, Integer plantEnergy,Integer jungleRatio){
        super(width,height,grasNum, plantEnergy,jungleRatio);
    }
    @Override
    public Vector2d[] getCorrners() {return new Vector2d[] {lowerLeft,upperRight};}

    @Override
    public boolean canMoveTo(Vector2d position) {
        return true;
    }

}
