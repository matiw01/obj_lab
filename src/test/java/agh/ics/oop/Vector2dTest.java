package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class Vector2dTest {

    @Test
    public void testToString(){
       assertEquals(new Vector2d(1,1).toString(),"(1,1)");
        assertEquals(new Vector2d(3,4).toString(),"(3,4)");
        assertEquals(new Vector2d(4,-2).toString(),"(4,-2)");
        assertEquals(new Vector2d(-2,-5).toString(),"(-2,-5)");
    }
    @Test
    public void testEquals(){
        assertEquals(new Vector2d(1,1).equals(new Vector2d(1,-1)),false);
        assertEquals(new Vector2d(-2,-3).equals(new Vector2d(-2,-4)),false);
        assertEquals(new Vector2d(-5,-7).equals(new Vector2d(-5,-7)),true);
        assertEquals(new Vector2d(2,-3).equals(new Vector2d(2,-3)),true);
    }
    @Test
    public void testPreceds(){
        Vector2d compareVector = new Vector2d(2,3);
        assertEquals(compareVector.precedes(new Vector2d(3,3)),true);
        assertEquals(compareVector.precedes(new Vector2d(0,3)),false);
        assertEquals(compareVector.precedes(new Vector2d(0,0)),false);
        assertEquals(compareVector.precedes(new Vector2d(2,0)),false);
        assertEquals(compareVector.precedes(new Vector2d(2,4)),true);
        assertEquals(compareVector.precedes(new Vector2d(-4,-4)),false);
    }
    @Test
    public void testFollows(){
        Vector2d compareVector = new Vector2d(2,3);
        assertEquals(compareVector.follows(new Vector2d(3,3)),false);
        assertEquals(compareVector.follows(new Vector2d(0,3)),true);
        assertEquals(compareVector.follows(new Vector2d(0,0)),true);
        assertEquals(compareVector.follows(new Vector2d(2,0)),true);
        assertEquals(compareVector.follows(new Vector2d(2,4)),false);
        assertEquals(compareVector.follows(new Vector2d(-4,-4)),true);
    }
    @Test
    public void testUpperRight(){
        assertEquals(new Vector2d(-1,-1).upperRight(new Vector2d(0,0)),new Vector2d(0,0));
        assertEquals(new Vector2d(0,2).upperRight(new Vector2d(2,2)),new Vector2d(2,2));
        assertEquals(new Vector2d(3,1).upperRight(new Vector2d(4,0)),new Vector2d(4,1));
        assertEquals(new Vector2d(2,-2).upperRight(new Vector2d(5,-3)),new Vector2d(5,-2));
        assertEquals(new Vector2d(-3,-3).upperRight(new Vector2d(-1,-5)),new Vector2d(-1,-3));
    }
    @Test
    public void testLowerLeft(){
        assertEquals(new Vector2d(-1,-1).lowerLeft(new Vector2d(0,0)),new Vector2d(-1,-1));
        assertEquals(new Vector2d(0,2).lowerLeft(new Vector2d(2,2)),new Vector2d(0,2));
        assertEquals(new Vector2d(3,1).lowerLeft(new Vector2d(4,0)),new Vector2d(3,0));
        assertEquals(new Vector2d(2,-2).lowerLeft(new Vector2d(5,-3)),new Vector2d(2,-3));
        assertEquals(new Vector2d(-3,-3).lowerLeft(new Vector2d(-1,-5)),new Vector2d(-3,-5));
    }
    @Test
    public void testAdd(){
        assertEquals(new Vector2d(1,1).add(new Vector2d(-1,-1)),new Vector2d(0,0));
        assertEquals(new Vector2d(2,3).add(new Vector2d(2,3)),new Vector2d(4,6));
        assertEquals(new Vector2d(3,5).add(new Vector2d(-2,1)),new Vector2d(1,6));
        assertEquals(new Vector2d(2,4).add(new Vector2d(4,2)),new Vector2d(6,6));
    }
    @Test
    public void testSubstract(){
        assertEquals(new Vector2d(1,1).substract(new Vector2d(-1,-1)),new Vector2d(2,2));
        assertEquals(new Vector2d(2,3).substract(new Vector2d(2,3)),new Vector2d(0,0));
        assertEquals(new Vector2d(3,5).substract(new Vector2d(-2,1)),new Vector2d(5,4));
        assertEquals(new Vector2d(2,4).substract(new Vector2d(4,2)),new Vector2d(-2,2));
    }
    @Test
    public void testOpposite(){
        assertEquals(new Vector2d(2,3).opposite(),new Vector2d(-2,-3));
        assertEquals(new Vector2d(3,-4).opposite(),new Vector2d(-3,4));
        assertEquals(new Vector2d(-5,7).opposite(),new Vector2d(5,-7));
        assertEquals(new Vector2d(-1,-6).opposite(),new Vector2d(1,6));
    }

}
