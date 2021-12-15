package agh.ics.oop;


import javafx.application.Platform;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class SimulationEngine implements IEngine, IMapObserver {
    private final IWorldMap map;
    Integer epoch = 0;
    int animalsNum;
    int moveEnergy;
    Vector2d lowerLeft;
    Vector2d upperRight;
    Vector2d jungleLowerLeft;
    Vector2d jungleUpperRight;
    Integer startEnergy;
    private List<IEngineObserver> engineObservers;
    private final ArrayList<Animal> animalsList = new ArrayList<Animal>();
    public SimulationEngine(IWorldMap map, int animalsNum, Integer startEnergy, Integer moveEnergy){
        this.engineObservers = new ArrayList<IEngineObserver>();
        this.moveEnergy = moveEnergy;
        this.animalsNum = animalsNum;
        this.startEnergy = startEnergy;
        this.map = map;
        Vector2d[] corrners = map.getCorrners();
        lowerLeft = corrners[0];
        upperRight = corrners[1];
        Vector2d[] jungleCorners = map.getJungleCorners();
        this.jungleLowerLeft = jungleCorners[0];
        this.jungleUpperRight = jungleCorners[1];
        for(int i = 0; i < animalsNum; i++) {
            Animal animal = new Animal(map, new Vector2d((int)(Math.random()*(upperRight.x+1)), (int)(Math.random()*(upperRight.y+1))), new ArrayList<>(),startEnergy, startEnergy/2, moveEnergy);
            map.place(animal);
            animalsList.add(animal);
        }
    }



    @Override
    public void run(){
        epoch += 1;
        removeDeadAnimals();
        map.animalsEat();
        for (Animal animal : animalsList){
            animal.move();
            animal.dieIfNoEnergy(epoch);
        }
        map.animalsProcreate();
        map.addGras();
        for (IEngineObserver engineObserver : engineObservers)
        {
            engineObserver.stepMade(epoch, map.getGrassNum(), map.getNumberOfAnimals());
        }

    }

    public Vector2d getAnimalPos(int n){return animalsList.get(n).getPosition();}
    private void removeDeadAnimals(){
        animalsList.removeIf(animal -> !animal.isAlive());
        map.removeDeadAnimals();
    }
    public void addObserver(IEngineObserver engineObserver){
        this.engineObservers.add(engineObserver);
    }
    @Override
    public void animalAdded(Animal animal) {
        Platform.runLater(()->{animalsList.add(animal);});
    }
    public Integer getAnimalsNum(){return animalsList.size();}
}
