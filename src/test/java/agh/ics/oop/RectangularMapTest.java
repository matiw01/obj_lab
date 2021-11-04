package agh.ics.oop;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class RectangularMapTest {
    public RectangularMap map = new RectangularMap(10,10);
    ArrayList<Animal> testArrayList = new ArrayList<Animal>();
    @Test
    public void addAnimalTest(){
        map.addAnimal(new Animal(map,new Vector2d(2,2)));
        map.addAnimal(new Animal(map,new Vector2d(3,4)));
        map.addAnimal(new Animal(map,new Vector2d(7,2)));
        testArrayList.add(new Animal(map,new Vector2d(2,2)));
        testArrayList.add(new Animal(map,new Vector2d(3,4)));
        testArrayList.add(new Animal(map,new Vector2d(7,2)));
        assertEquals(map.getAnimalsList().get(0).getPosition(),new Vector2d(2,2));
        assertEquals(map.getAnimalsList().get(1).getPosition(),new Vector2d(3,4));
        assertEquals(map.getAnimalsList().get(2).getPosition(),new Vector2d(7,2));
    }
    @Test
    public void canMoveToTest(){
        map.addAnimal(new Animal(map,new Vector2d(2,2)));
        map.addAnimal(new Animal(map,new Vector2d(3,4)));
        map.addAnimal(new Animal(map,new Vector2d(7,2)));
        assertTrue(map.canMoveTo(new Vector2d(2, 1)));
        assertTrue(map.canMoveTo(new Vector2d(10,10)));
        assertFalse(map.canMoveTo(new Vector2d(3,4)));
        assertFalse(map.canMoveTo(new Vector2d(7,2)));
    }
    @Test
    public void placeTest(){
        map.addAnimal(new Animal(map,new Vector2d(2,2)));
        map.addAnimal(new Animal(map,new Vector2d(3,4)));
        map.addAnimal(new Animal(map,new Vector2d(7,2)));
        assertTrue(map.place(new Animal(map,new Vector2d(2, 1))));
        assertTrue(map.place(new Animal(map, new Vector2d(10,10))));
        assertFalse(map.place(new Animal(map, new Vector2d(3,4))));
        assertFalse(map.place(new Animal(map, new Vector2d(7,2))));
    }
    @Test
    public void isOccupiedTest(){
        map.addAnimal(new Animal(map,new Vector2d(2,2)));
        map.addAnimal(new Animal(map,new Vector2d(3,4)));
        map.addAnimal(new Animal(map,new Vector2d(7,2)));
        assertFalse(map.isOccupied(new Vector2d(2, 1)));
        assertFalse(map.isOccupied( new Vector2d(10,1)));
        assertTrue(map.isOccupied( new Vector2d(3,4)));
        assertTrue(map.isOccupied( new Vector2d(7,2)));
    }
    @Test
    public void objectAtTest(){
        map.addAnimal(new Animal(map,new Vector2d(2,2)));
        map.addAnimal(new Animal(map,new Vector2d(3,4)));
        map.addAnimal(new Animal(map,new Vector2d(7,2)));
        assertNull(map.objectAt(new Vector2d(1, 1)));
        assertNotEquals(map.objectAt(new Vector2d(3,4)),null);
        assertNotEquals(map.objectAt(new Vector2d(7,2)),null);
        assertNotEquals(map.objectAt(new Vector2d(2,2)),null);
    }
}
