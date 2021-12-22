package agh.ics.oop;


import javafx.application.Platform;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class SimulationEngine implements IEngine, IMapObserver, Runnable {
    private final IWorldMap map;
    Integer epoch = 0;
    int animalsNum;
    int moveEnergy;
    Vector2d lowerLeft;
    Vector2d upperRight;
    Vector2d jungleLowerLeft;
    Vector2d jungleUpperRight;
    Integer startEnergy;
    float avgLifeLenght = 0;
    float sumOfYearsLived = 0;
    float numOfDeadAnimals = 0;
    float avgEnergy;
    float avgChildrenNum = 0;
    boolean magicStrategy;
    int magicCounter = 3;
    boolean shouldRun = false;
    List<Integer[]> statistics = new ArrayList<>();
    private final List<IEngineObserver> engineObservers;
    private final List<IMagicEvolutionObserver> magicEvolutionObservers;
    private final List<IGenotypeObserver> genotypeObservers;
    private final ArrayList<Animal> animalsList = new ArrayList<Animal>();
    public SimulationEngine(IWorldMap map, int animalsNum, Integer startEnergy, Integer moveEnergy, boolean magicStrategy){
        this.avgEnergy = startEnergy;
        this.magicStrategy = magicStrategy;
        this.engineObservers = new ArrayList<>();
        this.magicEvolutionObservers = new ArrayList<>();
        this.genotypeObservers = new ArrayList<>();
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
            while (true) {
                if (shouldRun) {
                if (animalsList.size() == 5 && magicStrategy && magicCounter > 0){magicEvlution();}
                epoch += 1;
                removeDeadAnimals();
                map.animalsEat();
                for (Animal animal : animalsList) {
                    animal.move();
                    animal.dieIfNoEnergy(epoch);
                }
                map.animalsProcreate();
                map.addGras();
                for (IEngineObserver engineObserver : engineObservers) {
                    engineObserver.stepMade(epoch, (float) map.getGrassNum(), (float)map.getNumberOfAnimals() , getAvgEnergy(), getAvgChildrenNum(), avgLifeLenght);
                }
                for (IGenotypeObserver genotypeObserver : genotypeObservers){
                    genotypeObserver.dominantGenotypeUpdate(findDominantGenotype());
                }
                statistics.add(new Integer[]{map.getGrassNum(), map.getNumberOfAnimals()});
            }
                try {
                    Thread.sleep(30);
                }catch (InterruptedException ex){
                    System.out.println(ex);
                }

        }
    }
    public void setShouldRun(boolean shouldRun) {this.shouldRun = shouldRun;}

    public Vector2d getAnimalPos(int n){return animalsList.get(n).getPosition();}
    private void removeDeadAnimals(){
        List<Animal> removeList = new ArrayList<Animal>();
        for (Animal animal : animalsList){
            if (!animal.isAlive()){
                removeList.add(animal);
                numOfDeadAnimals += (float) 1;
                sumOfYearsLived += (float) animal.getAge();
            }
        }
        for (Animal animal : removeList){
            animalsList.remove(animal);
            if (animalsList.size() == 5 && magicCounter > 0 && magicStrategy){
                magicEvlution();
            }
        }
        map.removeDeadAnimals();
        if(numOfDeadAnimals != 0){
            avgLifeLenght = sumOfYearsLived/numOfDeadAnimals;
        }
    }
    public void addEngineObserver(IEngineObserver engineObserver){this.engineObservers.add(engineObserver);}
    public void addMagicEvolutionObserver(IMagicEvolutionObserver evolutionObserver){this.magicEvolutionObservers.add(evolutionObserver);}
    public void addGenotypeObserver(IGenotypeObserver observer){genotypeObservers.add(observer);}
    @Override
    public void animalAdded(Animal animal) {
        animalsList.add(animal);
        if (animalsList.size() == 5 && magicCounter > 0 && magicStrategy){
            magicEvlution();
        }
    }
    public boolean getShouldRun(){return this.shouldRun;}
    public Integer getAnimalsNum(){return animalsList.size();}
    private Float getAvgEnergy(){
        float animalsNum = 0;
        float animalsEnergy = 0;
        for (Animal animal : animalsList){
            animalsNum += 1;
            animalsEnergy += animal.getEnergy();
        }
        if (animalsNum>0) {
            return animalsEnergy / animalsNum;
        }
        return 0.0f;
    }
    private Float getAvgChildrenNum(){
        float animalsNum = 0;
        float animalsChildren = 0;
        for (Animal animal : animalsList){
            animalsNum += 1;
            animalsChildren += animal.allchildren;
        }
        if (animalsNum>0) {
            return animalsChildren / animalsNum;
        }
        return 0f;
    }
    public int getEpoch(){return this.epoch;}

    private void magicEvlution(){
        magicCounter -= 1;
        for (IMagicEvolutionObserver observer : magicEvolutionObservers){
            observer.magicEvolutionHappend(this.magicCounter);
        }
        List<Vector2d> freePositions = new ArrayList<>();
        for (int i = 0; i <= upperRight.x; i++) {
            for (int j = 0; j <= upperRight.y; j++) {
                Vector2d position = new Vector2d(i, j);
                if (!map.isOccupied(position)) {
                    freePositions.add(position);
                }
            }
        }
        Collections.shuffle(freePositions);
        for (int i = 0; i < 5; i++) {
            Animal animal = new Animal(map, freePositions.get(i), animalsList.get(i).getGenotype(), startEnergy, startEnergy / 2, moveEnergy);
            animalsList.add(animal);
            map.place(animal);
        }
    }
    private List<Integer> findDominantGenotype(){
        HashMap<List<Integer>, AtomicInteger> animalsGenotypes = new HashMap<List<Integer>, AtomicInteger>();
        for (Animal animal : animalsList){
            animalsGenotypes.put(animal.getGenotype(),new AtomicInteger(0));
        }
        for (Animal animal : animalsList){
            animalsGenotypes.get(animal.getGenotype()).incrementAndGet();
        }
        List<Integer> dominant = new ArrayList<>();
        AtomicInteger numberOfOccurance = new AtomicInteger(0);
        for (Animal animal : animalsList){
            if (animalsGenotypes.get(animal.getGenotype()).get() > numberOfOccurance.get()){
                numberOfOccurance.set(animalsGenotypes.get(animal.getGenotype()).get());
                dominant = animal.getGenotype();
            }
        }
        return dominant;
    }
    public void updateDominantAnimals(){
        List<Integer> dominant = findDominantGenotype();
        for (Animal animal : animalsList){
            animal.setDomininat(false);
            if (animal.getGenotype().equals(dominant)){
                animal.setDomininat(true);
            }
        }
    }
}
