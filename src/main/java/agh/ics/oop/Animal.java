package agh.ics.oop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Animal implements IMapElement{
    private List<Integer> geontype;
    private MapDirection direction = MapDirection.NORTH;
    private Vector2d position;
    private final IWorldMap map;
    private final ArrayList<IPositionChangedObserver> observers = new ArrayList<>();
    public Animal(IWorldMap map,Vector2d initialPosition,List<Integer> genotype){
        this.position = initialPosition;
        this.map = map;
        this.addObserver((IPositionChangedObserver) map);
        if (genotype.size() == 0){
            for (int i = 0; i < 32; i++){
                genotype.add((int)(Math.random() * (32)));
            }
            Collections.sort(genotype);
        }
    }

    public void move(MoveDirection direction){
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
        if(map.canMoveTo(newPosition)){
            for (IPositionChangedObserver observer : observers){
                observer.positionChanged(this.position,newPosition);
            }
            this.position = newPosition;
        }
    }
    public boolean isAt(Vector2d position){return this.position.equals(position);}
    public Vector2d getPosition() {return this.position;}
    public MapDirection getDirection(){return this.direction;}
    public String toString(){return ""+getDirection();}
    void addObserver(IPositionChangedObserver observer){observers.add(observer);}
    void removeObserver(IPositionChangedObserver observer){observers.remove(observer);}

}
