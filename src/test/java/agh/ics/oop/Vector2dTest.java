package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
        assertNotEquals(new Vector2d(1, 1), new Vector2d(1, -1));
        assertNotEquals(new Vector2d(-2, -3), new Vector2d(-2, -4));
        assertNotEquals(new Vector2d(-5,-7).equals(new Vector2d(-5,-7)),true);
        assertNotEquals(new Vector2d(2,-3).equals(new Vector2d(2,-3)),true);
    }
    @Test
    public void testPreceds(){
        Vector2d compareVector = new Vector2d(2,3);
        assertTrue(compareVector.precedes(new Vector2d(3, 3)));
        assertFalse(compareVector.precedes(new Vector2d(0, 3)));
        assertFalse(compareVector.precedes(new Vector2d(0, 0)));
        assertFalse(compareVector.precedes(new Vector2d(2, 0)));
        assertTrue(compareVector.precedes(new Vector2d(2, 4)));
        assertFalse(compareVector.precedes(new Vector2d(-4, -4)));
    }
    @Test
    public void testFollows(){
        Vector2d compareVector = new Vector2d(2,3);
        assertFalse(compareVector.follows(new Vector2d(3, 3)));
        assertTrue(compareVector.follows(new Vector2d(0, 3)));
        assertTrue(compareVector.follows(new Vector2d(0, 0)));
        assertTrue(compareVector.follows(new Vector2d(2, 0)));
        assertFalse(compareVector.follows(new Vector2d(2, 4)));
        assertTrue(compareVector.follows(new Vector2d(-4, -4)));
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
        assertEquals(new Vector2d(1,1).subtract(new Vector2d(-1,-1)),new Vector2d(2,2));
        assertEquals(new Vector2d(2,3).subtract(new Vector2d(2,3)),new Vector2d(0,0));
        assertEquals(new Vector2d(3,5).subtract(new Vector2d(-2,1)),new Vector2d(5,4));
        assertEquals(new Vector2d(2,4).subtract(new Vector2d(4,2)),new Vector2d(-2,2));
    }
    @Test
    public void testOpposite(){
        assertEquals(new Vector2d(2,3).opposite(),new Vector2d(-2,-3));
        assertEquals(new Vector2d(3,-4).opposite(),new Vector2d(-3,4));
        assertEquals(new Vector2d(-5,7).opposite(),new Vector2d(5,-7));
        assertEquals(new Vector2d(-1,-6).opposite(),new Vector2d(1,6));
    }

}
