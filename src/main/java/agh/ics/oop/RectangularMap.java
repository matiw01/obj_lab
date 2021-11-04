package agh.ics.oop;

import java.util.ArrayList;

public class RectangularMap implements IWorldMap{
    private final int width;
    private final int height;
    private final ArrayList<Animal> animalsList = new ArrayList<Animal>();
    private final Vector2d lowerLeft;
    private final Vector2d upperRight;
    public RectangularMap(int width,int height){
        this.width = width;
        this.height = height;
        this.lowerLeft = new Vector2d(0,0);
        this.upperRight = new Vector2d(this.width,this.height);
    }
//    public ArrayList<Animal> getAnimalsList(){return this.animalsList;}
    public String toString(){
        MapVisualizer mapVisualizer = new MapVisualizer(this);
        return mapVisualizer.draw(new Vector2d(0,0),new Vector2d(this.width-1,this.height-1));
    }
    @Override
    public boolean canMoveTo(Vector2d position) {
//        if(position.y<=this.height && position.x <= this.width && position.x>=0 && position.y>=0){
//            return false;
//        }
        if (position.follows(this.lowerLeft) && position.precedes(this.upperRight)){
            return false;
        }
        for (Animal placedAnimal : animalsList){
            if (placedAnimal.getPosition().equals(position)){
                return false;
            }
        }
        return true;
    }
    @Override
    public boolean place(Animal animal) {
        if (canMoveTo(animal.getPosition())){
            animalsList.add(animal);
        return true;
        }
        else return false;
    }
    @Override
    public boolean isOccupied(Vector2d position) {
        for (Animal animal : animalsList){
            if (animal.getPosition().equals(position)){
                return true;
            }
        }
        return false;
    }
    @Override
    public Object objectAt(Vector2d position) {
        for (Animal animal : animalsList){
            if (animal.getPosition().equals(position)){
                return animal;
            }
        }
        return null;
    }
//    @Override
//    public int getWidth(){return this.width;}
//    @Override
//    public int getHeight(){return this.height;}
}
