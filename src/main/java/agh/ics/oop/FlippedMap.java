package agh.ics.oop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FlippedMap extends AbstractWorldMap implements IWorldMap{
    int grasNum;
    private Integer plantEnergy;
    public FlippedMap(int width,int height, int grasNum, Integer plantEnergy){
        this.plantEnergy = plantEnergy;
        this.grasNum = grasNum;
        this.lowerLeft = new Vector2d(0,0);
        this.upperRight = new Vector2d(width -1, height -1);
        for (int i = lowerLeft.x; i <= upperRight.x; i++){
            for (int j = lowerLeft.y; j <= upperRight.y; j++){
                animalsHashMap.put(new Vector2d(i,j),new ArrayList<Animal>());
            }
        }
        //random grass generation
        ArrayList<Integer> possiblePositions = new ArrayList<Integer>();
        for (int i = 0; i< (upperRight.x+1) * (upperRight.y+1); i++){
            possiblePositions.add(i);
        }
        Collections.shuffle(possiblePositions);
        List<Integer> grasPositions = possiblePositions.subList(0,grasNum);
        grasPositions.forEach((pos) -> grassHashMap.put(new Vector2d(pos/(upperRight.y+1), pos % (upperRight.y+1)),new Grass(new Vector2d(pos/upperRight.y, pos % (upperRight.y)+1))));
//        new Grass(new Vector2d(pos / upperRight.y,pos % (upperRight.y+1))
    }
    @Override
    public Vector2d[] getCorrners() {return new Vector2d[] {lowerLeft,upperRight};}

    @Override
    public boolean canMoveTo(Vector2d position) {
        return true;
    }

}
