package agh.ics.oop;

import java.util.*;

abstract class AbstractWorldMap implements IWorldMap, IPositionChangedObserver{
    protected final ArrayList<Animal> animalsList = new ArrayList<Animal>();
    protected final Map<Vector2d, List<Animal>> animalsHashMap = new HashMap<>();
    protected final Map<Vector2d, Grass> grassHashMap = new HashMap<>();
    protected final List<IMapObserver> observers = new ArrayList<IMapObserver>();
    protected Vector2d lowerLeft;
    protected Vector2d upperRight;
    protected Integer mapWidth;
    protected Integer mapHeiht;
    protected Integer jungleRatio;
    protected Integer numberOfAnimals;
    protected Integer startEnergy;
    protected Integer moveEnergy;
    protected Integer plantEnergy;
    int grasNum;

    public AbstractWorldMap(int width,int height, int grasNum, Integer plantEnergy){
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
            return true;
        }
        else throw new IllegalArgumentException("position " + animal.getPosition() + " is not available");
    }



    public Object objectAt(Vector2d position) {
        if (animalsHashMap.containsKey(position) && animalsHashMap.get(position).size()>0){
            return getStrongestAnimals(position)[0];
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


    public void animalsProcreate(){
        for (int i = 0; i <= upperRight.x; i++){
            for (int j = 0; j <= upperRight.y; j++){
                if (animalsHashMap.get(new Vector2d(i,j)).size()>=2){
                    Animal[] animals = getStrongestAnimals(new Vector2d(i,j));
                    Animal newBorn = animals[0].procreate(animals[1]);
                    this.place(newBorn);
                    for(IMapObserver observer : observers){
                        observer.animalAdded(newBorn);
                    }
                }
            }
        }
    }

    private Animal[] getStrongestAnimals(Vector2d position){
//        List<Animal> animalsList = animalsHashMap.get(position);
//        Animal strongestAnimal = animalsList.get(0);
//        for (Animal animal : animalsList){
//            if (strongestAnimal.getEnergy() > animal.getEnergy()){
//
//                strongestAnimal = animal;
//
//            }
//        }
//        return strongestAnimal;
        Collections.sort(animalsHashMap.get(position));
        return new Animal[] {animalsList.get(animalsList.size()-1), animalsList.get(animalsList.size()-2)};
    }

    public void addObserver(IMapObserver observer){observers.add(observer);}

    public Integer getMapWidth(){return this.mapWidth;}
    public Integer getMapHeight(){return this.mapHeiht;}
    public Integer getJungleRatio(){return this.jungleRatio;}
    public Integer getNumberOfAnimals(){return this.numberOfAnimals;}
    public Integer getStartEnergy(){return this.startEnergy;}
    public Integer getMoveEnergy(){return this.moveEnergy;}
    public Integer getPlantEnergy(){return this.plantEnergy;}
    public Integer getGrassNum(){return grassHashMap.size();}
}
