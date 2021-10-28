package agh.ics.oop;

import com.sun.source.tree.BreakTree;

import java.util.Objects;

public class Vector2d {
    public final int x;
    public final int y;
    public Vector2d(final int x, final int y){
            this.x = x;
            this.y = y;
    }

    public String toString(){
        return ("("+this.x+","+this.y+")") ;
    }
    public boolean precedes(Vector2d other){return (this.x<=other.x && this.y<=other.y);}
    public boolean follows(Vector2d other){return (this.x>=other.x && this.y>=other.y);}
    public Vector2d upperRight(Vector2d other){
        int rightX = Math.max(this.x, other.x);
        int rightY = Math.max(this.y, other.y);
        return (new Vector2d(rightX,rightY));
    }
    public Vector2d lowerLeft(Vector2d other){
        int rightX = Math.min(this.x, other.x);
        int rightY = Math.min(this.y, other.y);
        return (new Vector2d(rightX,rightY));
    }
    public Vector2d add(Vector2d other){
        int newX = this.x+other.x;
        int newY = this.y+other.y;
        return (new Vector2d(newX,newY));
    }
    public Vector2d substract(Vector2d other){
        int newX = this.x-other.x;
        int newY = this.y-other.y;
        return (new Vector2d(newX,newY));
    }

    public boolean equals(Object other){
        if (this == other){return true;}
        if (!(other instanceof Vector2d vector)){return false;}
        return (this.x == vector.x && this.y == vector.y);
    }
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
    public Vector2d opposite(){
        return (new Vector2d(-1*this.x,-1*this.y));
    }
}
