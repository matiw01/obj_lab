package agh.ics.oop;

import com.sun.source.tree.BreakTree;

public class Vector2d {
    public int x;
    public int y;
    public Vector2d(int x, int y){
            this.x = x;
            this.y = y;
    }

    public String toString(){
        return new String("("+x+","+y+")") ;
    }

}
