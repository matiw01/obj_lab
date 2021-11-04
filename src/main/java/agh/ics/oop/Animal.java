package agh.ics.oop;

public class Animal {
    private MapDirection direction = MapDirection.NORTH;
    private Vector2d position;
    private final IWorldMap map;
    public Animal(IWorldMap map,Vector2d initialPosition){
        this.position = initialPosition;
        this.map = map;
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
        if(newPosition.x<= map.getWidth() && newPosition.x>=0 && newPosition.y<= map.getHeight() && newPosition.y>= 0 && map.canMoveTo(newPosition)){
            this.position = newPosition;
        }
    }
    public boolean isAt(Vector2d position){return this.position.equals(position);}
    public Vector2d getPosition() {
        return this.position;
    }
    public MapDirection getDirection(){return this.direction;}
    public String toString(){return this.getDirection().toString();}
}
