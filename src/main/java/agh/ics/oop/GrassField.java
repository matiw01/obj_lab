package agh.ics.oop;
import java.lang.Math;
import java.util.ArrayList;

public class GrassField extends AbstractWorldMap implements IWorldMap, IPositionChangedObserver{
    public GrassField(int grasNum){
        lowerLeft = new Vector2d((int)Math.floor(Math.sqrt(10*grasNum)),(int)Math.floor(Math.sqrt(10*grasNum)));
        upperRight = new Vector2d(0,0);
        int howManyMore = grasNum;
        int numOfCells = (int)(Math.pow(Math.floor(Math.sqrt(10*grasNum)),2));
        int lenOfRow = (int)(Math.floor(Math.sqrt(10*grasNum)));

        for(int i=0; i<Math.floor(Math.sqrt(10*grasNum)); i++){
            for(int j=0; j<Math.floor(Math.sqrt(10*grasNum)); j++){
                if(Math.random()<1/(float)grasNum && howManyMore>0){
                        Vector2d vector = new Vector2d(i,j);
                        grassHashMap.put(vector,new Grass(vector));
                        howManyMore--;
                    if (vector.x > upperRight.x || vector.y > upperRight.y){
                        upperRight = upperRight.upperRight(vector);
                        lowerLeft = lowerLeft.lowerLeft(vector);
                    }
                }
                else if(howManyMore>=numOfCells-i*lenOfRow-j && howManyMore>0){
                    Vector2d vector = new Vector2d(i,j);
                    grassHashMap.put(vector,new Grass(vector));
                    howManyMore--;
                    if (vector.x > upperRight.x || vector.y > upperRight.y){
                        upperRight = upperRight.upperRight(vector);
                        lowerLeft = lowerLeft.lowerLeft(vector);
                    }
                }
            }
        }
    }
    public Vector2d[] getCorrners(){
        return new Vector2d[]{lowerLeft, upperRight};
    }
    @Override
    public boolean canMoveTo(Vector2d position) {
        return !animalsHashMap.containsKey(position);
    }

}
