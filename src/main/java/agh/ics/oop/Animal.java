package agh.ics.oop;

import java.util.ArrayList;

public class Animal implements IMapElement{
    private MapDirection direction = MapDirection.NORTH;
    private Vector2d position;
    private final IWorldMap map;
    private final ArrayList<IPositionChangedObserver> observers = new ArrayList<>();
    public Animal(IWorldMap map,Vector2d initialPosition){
        this.position = initialPosition;
        this.map = map;
        this.addObserver((IPositionChangedObserver) map);
    }

    public void move(MoveDirection direction){
        if(direction == MoveDirection.RIGHT){
          this.direction = this.direction.next();
          go(this.direction);
        }
        else if(direction == MoveDirection.LEFT){
            this.direction = this.direction.previous();
            go(this.direction);
        }
        else if(direction == MoveDirection.FORWARD){
            go(this.direction);
        }
        else if(direction == MoveDirection.BACKWARD){
            go(this.direction.next().next());
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
//    public String toString(){return this.getDirection().toString();}
    public String toString(){return ""+getDirection();}
    void addObserver(IPositionChangedObserver observer){observers.add(observer);}
    void removeObserver(IPositionChangedObserver observer){observers.remove(observer);}

}
