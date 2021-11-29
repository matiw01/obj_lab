package agh.ics.oop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

abstract class AbstractWorldMap implements IWorldMap{
    protected final ArrayList<Animal> animalsList = new ArrayList<Animal>();
    protected final Map<Vector2d, Animal> animalsHashMap = new HashMap<>();
    protected final Map<Vector2d, Grass> grassHashMap = new HashMap<>();
    protected Vector2d lowerLeft;
    protected Vector2d upperRight;
    public String toString(){
        MapVisualizer mapVisualizer = new MapVisualizer(this);
        Vector2d[] verticies = getCorrners();
        return mapVisualizer.draw(verticies[0],verticies[1]);
    }

    protected abstract Vector2d[] getCorrners();
    protected Vector2d getAnimalInListPos(int n){return animalsList.get(n).getPosition();}

    public boolean isOccupied(Vector2d position) {
        return animalsHashMap.containsKey(position) || grassHashMap.containsKey(position);
    }

    public boolean place(Animal animal) {
        if (canMoveTo(animal.getPosition())){
            animalsList.add(animal);
            animalsHashMap.put(animal.getPosition(),animal);
            upperRight = upperRight.upperRight(animal.getPosition());
            lowerLeft = lowerLeft.lowerLeft(animal.getPosition());
            return true;
        }
        else throw new IllegalArgumentException("position " + animal.getPosition() + " is already taken");
    }

    public Object objectAt(Vector2d position) {
        if (animalsHashMap.containsKey(position)){
            return animalsHashMap.get(position);
        }
        if (grassHashMap.containsKey(position)){
            return grassHashMap.get(position);
        }
        return null;
    }
    //Observer method
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        Animal animal = animalsHashMap.get(oldPosition);
        animalsHashMap.remove(oldPosition);
        animalsHashMap.put(newPosition,animal);
        upperRight = upperRight.upperRight(newPosition);
        lowerLeft = lowerLeft.lowerLeft(newPosition);
    }

}
