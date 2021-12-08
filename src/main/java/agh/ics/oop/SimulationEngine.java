package agh.ics.oop;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class SimulationEngine implements IEngine {
    private final IWorldMap map;
    int animalsNum;
    int moveEnergy;
    Vector2d lowerLeft;
    Vector2d upperRight;
    Integer startEnergy;
    private final ArrayList<Animal> animalsList = new ArrayList<Animal>();
    public SimulationEngine(IWorldMap map, int animalsNum, Integer startEnergy, Integer moveEnergy){
        this.moveEnergy = moveEnergy;
        this.animalsNum = animalsNum;
        this.startEnergy = startEnergy;
        this.map = map;
        Vector2d[] corrners = map.getCorrners();
        lowerLeft = corrners[0];
        upperRight = corrners[1];
        for(int i = 0; i < animalsNum; i++) {
            Animal animal = new Animal(map, new Vector2d((int)(Math.random()*upperRight.x), (int)(Math.random()* upperRight.y)), new ArrayList<>(),startEnergy, startEnergy/2, moveEnergy);
            map.place(animal);
            animalsList.add(animal);
        }
    }



    @Override
    public void run(){
        removeDeadAnimals();
        for (Animal animal : animalsList){
            animal.move();
            animal.eat();
            animal.dieIfNoEnergy();
        }
    }
    public Vector2d getAnimalPos(int n){return animalsList.get(n).getPosition();}
    private void removeDeadAnimals(){
        animalsList.removeIf(animal -> !animal.isAlive());
        map.removeDeadAnimals();
    }
}
