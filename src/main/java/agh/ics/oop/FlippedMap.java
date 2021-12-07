package agh.ics.oop;

import java.util.ArrayList;

public class FlippedMap extends AbstractWorldMap implements IWorldMap{
    public FlippedMap(int width,int height){
        this.lowerLeft = new Vector2d(0,0);
        this.upperRight = new Vector2d(width -1, height -1);
        for (int i = lowerLeft.x; i <= upperRight.x; i++){
            for (int j = lowerLeft.y; j <= upperRight.y; j++){
                animalsHashMap.put(new Vector2d(i,j),new ArrayList<Animal>());
            }
        }
    }
    @Override
    public Vector2d[] getCorrners() {return new Vector2d[] {lowerLeft,upperRight};}

    @Override
    public boolean canMoveTo(Vector2d position) {
        return true;
    }

}
