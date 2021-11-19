package agh.ics.oop;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class RectangularMap extends AbstractWorldMap implements IWorldMap, IPositionChangedObserver{
    public RectangularMap(int width,int height){
        this.lowerLeft = new Vector2d(0,0);
        this.upperRight = new Vector2d(width -1, height -1);
    }
    public Vector2d[] getCorrners(){return new Vector2d[] {lowerLeft,upperRight};}
    @Override
    public boolean canMoveTo(Vector2d position) {
            if(animalsHashMap.containsKey(position)){
                return false;
            }
        return position.follows(this.lowerLeft) && position.precedes(this.upperRight);
    }
}
