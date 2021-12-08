package agh.ics.oop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Animal implements IMapElement{
    Integer moveEnergy;
    private boolean alive;
    private int energy;
    private int procreareEnergy;
    private List<Integer> geontype;
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
}
