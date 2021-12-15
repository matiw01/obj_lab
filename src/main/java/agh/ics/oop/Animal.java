package agh.ics.oop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Animal implements IMapElement, Comparable{
    Integer age = 0;
    private Integer childrenNumber = 0;
    Integer descendantsNumber = 0;
    Integer moveEnergy;
    private boolean alive;
    private int energy;
    private final int procreareEnergy;
    private final List<Integer> geontype;
    private MapDirection direction;
    private Vector2d position;
    private final IWorldMap map;
    private final ArrayList<IPositionChangedObserver> observers = new ArrayList<>();
    public Animal(IWorldMap map,Vector2d initialPosition,List<Integer> genotype, int energy, int procreareEnergy, Integer moveEnergy){
        this.moveEnergy = moveEnergy;
        this.alive = true;
        this.position = initialPosition;
        this.map = map;
        this.geontype = genotype;
        this.direction = MapDirection.values()[(int)(Math.random()*8)];
        this.addObserver((IPositionChangedObserver) map);
        this.energy = energy;
        this.procreareEnergy = procreareEnergy;
        if (genotype.size() == 0){
            for (int i = 0; i < 32; i++){
                genotype.add((int)(Math.random() * (8)));
            }
            Collections.sort(genotype);
        }
    }

    public void move(){
//        System.out.println(energy);
        this.energy -= moveEnergy;
        int gen = geontype.get((int) (Math.random() * 32));
        MoveDirection direction = MoveDirection.values()[gen];
        switch (direction){
            case FORWARD -> go(this.direction);
            case FOWARD_RIGHT -> this.direction = this.direction.changeDirection(1);
            case RIGHT -> this.direction = this.direction.changeDirection(2);
            case BACKWARD_RIGHT -> this.direction = this.direction.changeDirection(3);
            case BACKWARD -> this.direction = this.direction.changeDirection(4);
            case BACKWAD_LEFT -> this.direction = this.direction.changeDirection(5);
            case LEFT -> this.direction = this.direction.changeDirection(6);
            case FORWARD_LEFT -> this.direction = this.direction.changeDirection(7);
        }
    }
    public void go(MapDirection direction){
        Vector2d moveVector = direction.toUnitVector();
        Vector2d newPosition = this.position.add(moveVector);
        Vector2d[] corrners = map.getCorrners();
        Vector2d lowerLeft = corrners[0];
        Vector2d upperRight = corrners[1];
        if(map.canMoveTo(newPosition)){
            newPosition = validateMove(newPosition, lowerLeft, upperRight);
            for (IPositionChangedObserver observer : observers){
                observer.positionChanged(this.position,newPosition,this);
            }
            this.position = newPosition;
        }
    }
    public void eat(){
        if (map.isGrassy(position)){
            map.removeGrass(position);
            energy += map.getPlantEnergy();
        }
    }
    public void dieIfNoEnergy(){
        if (energy <= 0){
            this.alive = false;
        }
    }
    public boolean isAt(Vector2d position){return this.position.equals(position);}
    public Vector2d getPosition() {return this.position;}
    public MapDirection getDirection(){return this.direction;}
    public Integer getEnergy(){return this.energy;}
    public Integer getChildrenNumber(){return this.childrenNumber;}
    public Integer getAge(){return this.age;}
    public Integer getDescendantsNumber(){return this.descendantsNumber;}
    public  List<Integer> getGeontype(){return this.geontype;}
    public Integer getProcreateEnergy(){return this.procreareEnergy;}
    public boolean isAlive(){return this.alive;}
    public String toString(){return ""+getDirection();}
    void addObserver(IPositionChangedObserver observer){observers.add(observer);}
    void removeObserver(IPositionChangedObserver observer){observers.remove(observer);}
    public boolean goesOutsiedeMap(Vector2d newPosition, Vector2d lowerLeft, Vector2d upperRight){return newPosition.x == upperRight.x+1 || newPosition.x == lowerLeft.x-1 || newPosition.y == lowerLeft.y-1 || newPosition.y == upperRight.x+1;}
    private Vector2d validateMove(Vector2d newPosition, Vector2d lowerLeft, Vector2d upperRight) {
        if (goesOutsiedeMap(newPosition, lowerLeft, upperRight)) {
            if (newPosition.x > upperRight.x){
                newPosition = new Vector2d(0, newPosition.y);
            }
            if (newPosition.x < lowerLeft.x){
                newPosition = new Vector2d(upperRight.x, newPosition.y);
            }
            if (newPosition.y > upperRight.y){
                newPosition = new Vector2d(newPosition.x, 0);
            }
            if (newPosition.y < lowerLeft.y){
                newPosition = new Vector2d(newPosition.x, upperRight.y);
            }
        }
        return newPosition;
    }
    public Animal procreate(Animal other){
//        System.out.println("genotyp rodzica 1");
//        System.out.println(this.getGeontype());
//        System.out.println("energia rodzica 1");
//        System.out.println(this.getEnergy());
//        System.out.println("genotyp rodzica 2");
//        System.out.println(other.getGeontype());
//        System.out.println("energia rodzica 2");
//        System.out.println(other.getEnergy());

        List<Integer> newBornGentype = new ArrayList<>();
        newBornGentype.addAll(this.geontype.subList(0,this.energy/(this.energy+other.getEnergy())+1));
        newBornGentype.addAll(this.geontype.subList(this.energy/(this.energy+other.getEnergy())+1,32));
//        System.out.println("genotyp dziecka");
//        System.out.println(newBornGentype);
//        System.out.println("energia dziecka");
//        System.out.println((this.energy+other.getEnergy())/4);
//        System.out.println("-----------------------------------");
        this.energy -= this.energy/4;
        other.energy -= other.energy/4;
        this.childrenNumber += 1;
        other.childrenNumber += 1;
        return new Animal(this.map, this.position, newBornGentype,(this.energy+other.getEnergy())/4, this.procreareEnergy, this.moveEnergy);
    }

    @Override
    public int compareTo(Object o) {
        Animal other = (Animal) o;
        int cmp = 0;
        if (this.getEnergy() > other.getEnergy()){
            cmp = 1;
        }
        if (this.getEnergy() < other.getEnergy()){
            return -1;
        }
        return cmp;
    }
}
