package agh.ics.oop;

import java.util.ArrayList;

public class RectangularMap implements IWorldMap{
    private final int width;
    private final int height;
    private final ArrayList<Animal> animalsList = new ArrayList<Animal>();
    public RectangularMap(int width,int height){
        this.width = width;
        this.height = height;
    }
    public ArrayList<Animal> getAnimalsList(){return this.animalsList;}
    public void addAnimal(Animal animal){animalsList.add(animal);}
    public String toString(){
        MapVisualizer mapVisualizer = new MapVisualizer(this);
        Vector2d lowerLeft = new Vector2d(0,0);
        Vector2d upperRight = new Vector2d(this.getWidth(),this.getHeight());
        return mapVisualizer.draw(lowerLeft,upperRight);
    }
    @Override
    public boolean canMoveTo(Vector2d position) {
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
        addAnimal(animal);
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
    @Override
    public int getWidth(){return this.width;}
    @Override
    public int getHeight(){return this.height;}
}
