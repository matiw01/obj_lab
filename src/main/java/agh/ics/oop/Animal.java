package agh.ics.oop;

public class Animal {
    private MapDirection direction = MapDirection.NORTH;
    private Vector2d position = new Vector2d(2,2);

    public String toString(){return "Animal position "+this.position.toString() + ", orientacion " + this.direction;}
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
        if(newPosition.x<=4 && newPosition.x>=0 && newPosition.y<=4 && newPosition.y>= 0){
            this.position = newPosition;
        }
    }
    public boolean isAt(Vector2d position){return this.position.equals(position);}
    public Vector2d getPosition() {
        return this.position;
    }
    public MapDirection getDirection(){return this.direction;}
}
