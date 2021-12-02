package agh.ics.oop;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class RectangularMapTest {
    private final RectangularMap map = new RectangularMap(10,10);
    ArrayList<Animal> testArrayList = new ArrayList<Animal>();
    @Test
    public void addAnimalTest(){
        map.place(new Animal(map,new Vector2d(2,2)));
        map.place(new Animal(map,new Vector2d(3,4)));
        map.place(new Animal(map,new Vector2d(7,2)));
        testArrayList.add(new Animal(map,new Vector2d(2,2)));
        testArrayList.add(new Animal(map,new Vector2d(3,4)));
        testArrayList.add(new Animal(map,new Vector2d(7,2)));
        assertEquals(map.getAnimalInListPos(0),new Vector2d(2,2));
        assertEquals(map.getAnimalInListPos(1),new Vector2d(3,4));
        assertEquals(map.getAnimalInListPos(2),new Vector2d(7,2));
    }
    @Test
    public void canMoveToTest(){
        map.place(new Animal(map,new Vector2d(2,2)));
        map.place(new Animal(map,new Vector2d(3,4)));
        map.place(new Animal(map,new Vector2d(7,2)));
        assertTrue(map.canMoveTo(new Vector2d(2, 1)));
        assertFalse(map.canMoveTo(new Vector2d(10,10)));
        assertFalse(map.canMoveTo(new Vector2d(3,4)));
        assertFalse(map.canMoveTo(new Vector2d(7,2)));
    }
    @Test
    public void placeTest(){
        map.place(new Animal(map,new Vector2d(2,2)));
        map.place(new Animal(map,new Vector2d(3,4)));
        map.place(new Animal(map,new Vector2d(7,2)));
        assertTrue(map.place(new Animal(map,new Vector2d(2, 1))));
        Exception ex1 = assertThrows(IllegalArgumentException.class,() -> {map.place(new Animal(map, new Vector2d(10,10)));});
        assertEquals("position (10,10) is not available", ex1.getMessage());
        Exception ex2 = assertThrows(IllegalArgumentException.class,() -> {map.place(new Animal(map, new Vector2d(3,4)));});
        assertEquals("position (3,4) is not available", ex2.getMessage());
        Exception ex3 = assertThrows(IllegalArgumentException.class,() -> {map.place(new Animal(map, new Vector2d(7,2)));});
        assertEquals("position (7,2) is not available", ex3.getMessage());
    }
    @Test
    public void isOccupiedTest(){
        map.place(new Animal(map,new Vector2d(2,2)));
        map.place(new Animal(map,new Vector2d(3,4)));
        map.place(new Animal(map,new Vector2d(7,2)));
        assertFalse(map.isOccupied(new Vector2d(2, 1)));
        assertFalse(map.isOccupied( new Vector2d(10,1)));
        assertTrue(map.isOccupied( new Vector2d(3,4)));
        assertTrue(map.isOccupied( new Vector2d(7,2)));
    }
    @Test
    public void objectAtTest(){
        map.place(new Animal(map,new Vector2d(2,2)));
        map.place(new Animal(map,new Vector2d(3,4)));
        map.place(new Animal(map,new Vector2d(7,2)));
        assertNull(map.objectAt(new Vector2d(1, 1)));
        assertNotEquals(map.objectAt(new Vector2d(3,4)),null);
        assertNotEquals(map.objectAt(new Vector2d(7,2)),null);
        assertNotEquals(map.objectAt(new Vector2d(2,2)),null);
    }
}
