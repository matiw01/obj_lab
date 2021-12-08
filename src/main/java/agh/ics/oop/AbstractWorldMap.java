package agh.ics.oop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract class AbstractWorldMap implements IWorldMap, IPositionChangedObserver{
    protected final ArrayList<Animal> animalsList = new ArrayList<Animal>();
    protected final Map<Vector2d, List<Animal>> animalsHashMap = new HashMap<>();
    protected final Map<Vector2d, Grass> grassHashMap = new HashMap<>();
    protected Vector2d lowerLeft;
    protected Vector2d upperRight;
    private Integer mapWidth;
    private Integer mapHeiht;
    private Integer jungleRatio;
    private Integer numberOfAnimals;
    private Integer startEnergy;
    private Integer moveEnergy;
    private Integer plantEnergy;


    public abstract Vector2d[] getCorrners();
    protected Vector2d getAnimalInListPos(int n){return animalsList.get(n).getPosition();}

    public boolean isOccupied(Vector2d position) {
        return (animalsHashMap.containsKey(position) && animalsHashMap.get(position).size() > 0) || grassHashMap.containsKey(position);
    }

    public boolean isGrassy(Vector2d position){
        return grassHashMap.containsKey(position);
    }

    public boolean place(Animal animal) {
        if (canMoveTo(animal.getPosition())){
            animalsList.add(animal);
            animalsHashMap.get(animal.getPosition()).add(animal);
            upperRight = upperRight.upperRight(animal.getPosition());
            lowerLeft = lowerLeft.lowerLeft(animal.getPosition());
            return true;
        }
        else throw new IllegalArgumentException("position " + animal.getPosition() + " is not available");
    }



    public Object objectAt(Vector2d position) {
        if (animalsHashMap.containsKey(position) && animalsHashMap.get(position).size()>0){
            List<Animal> animalsList = animalsHashMap.get(position);
            Animal strongestAnimal = animalsList.get(0);
            for (Animal animal : animalsList){
                if (strongestAnimal.getEnergy() > animal.getEnergy()){
                    strongestAnimal = animal;
                }
            }
            return strongestAnimal;
        }
        if (grassHashMap.containsKey(position)){
            return grassHashMap.get(position);
        }
        return null;
    }

    public void removeDeadAnimals(){
        for (int i = lowerLeft.x; i <= upperRight.x; i++){
            for (int j = lowerLeft.y; j <= upperRight.y; j++){
                if (animalsHashMap.containsKey(new Vector2d(i,j))){
                    animalsHashMap.get(new Vector2d(i,j)).removeIf(animal -> !animal.isAlive());
                }
            }
        }
    }
    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal) {
        List<Animal> animalsOnPos = animalsHashMap.get(oldPosition);
        animalsOnPos.remove(animal);
        List<Animal> newAnimalsOnPos = animalsHashMap.get(newPosition);
        newAnimalsOnPos.add(animal);
    }

    public void addGras(Grass grass){grassHashMap.put(grass.getPosition(), grass);}
    public void removeGrass(Vector2d position){grassHashMap.remove(position);};

    public Integer getMapWidth(){return this.mapWidth;}
    public Integer getMapHeight(){return this.mapHeiht;}
    public Integer getJungleRatio(){return this.jungleRatio;}
    public Integer getNumberOfAnimals(){return this.numberOfAnimals;}
    public Integer getStartEnergy(){return this.startEnergy;}
    public Integer getMoveEnergy(){return this.moveEnergy;}
    public Integer getPlantEnergy(){return this.plantEnergy;}
}
