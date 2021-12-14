package agh.ics.oop;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RectangularMap extends AbstractWorldMap{
    public RectangularMap(int width,int height, Integer plantEnergy, Integer jungleRatio){
        super(width,height, plantEnergy,jungleRatio);
    }
    public Vector2d[] getCorrners(){return new Vector2d[] {lowerLeft,upperRight};}
    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.follows(this.lowerLeft) && position.precedes(this.upperRight);
    }



}
