package agh.ics.oop;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

abstract class AbstractWorldMap implements IWorldMap, IPositionChangedObserver {
    protected final ConcurrentHashMap<Vector2d, CopyOnWriteArrayList<Animal>> animalsHashMap = new ConcurrentHashMap<>();
    protected final Map<Vector2d, Grass> grassHashMap = new HashMap<>();
    protected final List<IMapObserver> observers = new ArrayList<IMapObserver>();
    protected Vector2d lowerLeft;
    protected Vector2d upperRight;
    protected Integer mapWidth;
    protected Integer mapHeiht;
    protected Integer jungleRatio;
    protected Integer startEnergy;
    protected Integer moveEnergy;
    protected Integer plantEnergy;
    protected Vector2d jungleLowerLeft;
    protected Vector2d jungleUpperRight;


    public AbstractWorldMap(int width, int height, Integer plantEnergy, Integer jungleRatio) {
        this.mapWidth = width;
        this.mapHeiht = height;
        this.jungleRatio = jungleRatio;
        this.plantEnergy = plantEnergy;
        this.lowerLeft = new Vector2d(0, 0);
        this.upperRight = new Vector2d(width - 1, height - 1);
        this.jungleLowerLeft = new Vector2d((int) (width / 2 - (width / 2) * Math.sqrt(((double) jungleRatio / 100))), (int) (height / 2 - (height / 2) * Math.sqrt((double) jungleRatio / 100)));
        this.jungleUpperRight = new Vector2d((int) (width / 2 + (width / 2) * Math.sqrt(((double) jungleRatio / 100))), (int) (height / 2 + (height / 2) * Math.sqrt((double) jungleRatio / 100)));
//        System.out.println(lowerLeft);
//        System.out.println(upperRight);
//        System.out.println(jungleLowerLeft);
//        System.out.println(jungleUpperRight);
        for (int i = lowerLeft.x; i <= upperRight.x; i++) {
            for (int j = lowerLeft.y; j <= upperRight.y; j++) {
                animalsHashMap.put(new Vector2d(i, j), new CopyOnWriteArrayList<Animal>());
            }
        }
        //random grass generation
//        ArrayList<Integer> possiblePositions = new ArrayList<Integer>();
//        for (int i = 0; i< (upperRight.x+1) * (upperRight.y+1); i++){
//            possiblePositions.add(i);
//        }
//        Collections.shuffle(possiblePositions);
//        List<Integer> grasPositions = possiblePositions.subList(0,grasNum);
//        grasPositions.forEach((pos) -> grassHashMap.put(new Vector2d(pos/(upperRight.y+1), pos % (upperRight.y+1)),new Grass(new Vector2d(pos/upperRight.y, pos % (upperRight.y)+1))));
//        new Grass(new Vector2d(pos / upperRight.y,pos % (upperRight.y+1))
    }

    public abstract Vector2d[] getCorrners();

    public Vector2d[] getJungleCorners() {
        return new Vector2d[]{this.jungleLowerLeft, this.jungleUpperRight};
    }


    public boolean isOccupied(Vector2d position) {
        return (animalsHashMap.containsKey(position) && animalsHashMap.get(position).size() > 0) || grassHashMap.containsKey(position);
    }

    public boolean isGrassy(Vector2d position) {
        return grassHashMap.containsKey(position);
    }

    public boolean place(Animal animal) {
        if (canMoveTo(animal.getPosition())) {

            animalsHashMap.get(animal.getPosition()).add(animal);
            return true;
        } else throw new IllegalArgumentException("position " + animal.getPosition() + " is not available");
    }


    public Object objectAt(Vector2d position) {
        if (animalsHashMap.containsKey(position) && animalsHashMap.get(position).size() >= 2) {
            return getStrongestAnimals(position)[0];
        }
        if (animalsHashMap.containsKey(position) && animalsHashMap.get(position).size() == 1) {
            return animalsHashMap.get(position).get(0);
        }
        if (grassHashMap.containsKey(position)) {
            return grassHashMap.get(position);
        }
        return null;
    }

    public void removeDeadAnimals() {
        for (int i = lowerLeft.x; i <= upperRight.x; i++) {
            for (int j = lowerLeft.y; j <= upperRight.y; j++) {
                if (animalsHashMap.containsKey(new Vector2d(i, j))) {
                    animalsHashMap.get(new Vector2d(i, j)).removeIf(animal -> !animal.isAlive());
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

    public void addGras() {
        List<Vector2d> steppePosList = new ArrayList<>();
        List<Vector2d> junglePosList = new ArrayList<>();
        for (int i = 0; i <= upperRight.x; i++) {
            for (int j = 0; j <= upperRight.y; j++) {
                Vector2d position = new Vector2d(i, j);
                if (!isOccupied(position)) {
                    if (position.follows(jungleLowerLeft) && position.precedes(jungleUpperRight)) {
                        junglePosList.add(position);
                    } else {
                        steppePosList.add(position);
                    }
                }
            }
        }
        if (steppePosList.size() > 0) {
//            System.out.println("steppe");
//            System.out.println(steppePosList);
            Vector2d steppeGrasPos = steppePosList.get((int) (Math.random() * steppePosList.size()));
            grassHashMap.put(steppeGrasPos, new Grass(steppeGrasPos));
        }
        if (junglePosList.size() > 0) {
//            System.out.println("jungle");
//            System.out.println(junglePosList);
            Vector2d jungleGrasPos = junglePosList.get((int) (Math.random() * junglePosList.size()));
            grassHashMap.put(jungleGrasPos, new Grass(jungleGrasPos));
        }
    }

    public void removeGrass(Vector2d position) {
        grassHashMap.remove(position);
    }

    ;


    public void animalsProcreate() {
        for (int i = 0; i <= upperRight.x; i++) {
            for (int j = 0; j <= upperRight.y; j++) {
                if (animalsHashMap.get(new Vector2d(i, j)).size() >= 2) {
                    Animal[] animals = getStrongestAnimals(new Vector2d(i, j));
                    if (animals[0].getEnergy() >= animals[0].getProcreateEnergy() && animals[1].getEnergy() >= animals[1].getProcreateEnergy()) {
                        Animal newBorn = animals[0].procreate(animals[1]);
                        this.place(newBorn);
                        for (IMapObserver observer : observers) {
                            observer.animalAdded(newBorn);
                        }
                    }
                }
            }
        }
    }

    public void animalsEat() {
        for (int i = 0; i <= upperRight.x; i++) {
            for (int j = 0; j <= upperRight.y; j++) {
                Vector2d position = new Vector2d(i, j);
                List<Animal> animalsList = animalsHashMap.get(position);
                if (animalsList.size() > 0) {
                    Collections.sort(animalsList);
                    animalsHashMap.get(position).get(animalsList.size() - 1).eat();
                }
            }
        }
    }

    private Animal[] getStrongestAnimals(Vector2d position) {
        List<Animal> animalsList = animalsHashMap.get(position);
        Collections.sort(animalsList);
        return new Animal[]{animalsList.get(animalsList.size() - 1), animalsList.get(animalsList.size() - 2)}; // a co z losowym rozstrzyganiem remisów?
    }

    public void addObserver(IMapObserver observer) {
        observers.add(observer);
    }


    public Integer getMapWidth() {
        return this.mapWidth;
    }

    public Integer getMapHeight() {
        return this.mapHeiht;
    }

    public Integer getJungleRatio() {
        return this.jungleRatio;
    }

    public Integer getNumberOfAnimals() {
        Integer numOfAnimals = 0;
        for (int i = 0; i <= upperRight.x; i++) {
            for (int j = 0; j <= upperRight.y; j++) {
                numOfAnimals += animalsHashMap.get(new Vector2d(i, j)).size();
            }
        }
        return numOfAnimals;
    }

    public Integer getStartEnergy() {
        return this.startEnergy;
    }

    public Integer getMoveEnergy() {
        return this.moveEnergy;
    }

    public Integer getPlantEnergy() {
        return this.plantEnergy;
    }

    public Integer getGrassNum() {
        return grassHashMap.size();
    }
}
