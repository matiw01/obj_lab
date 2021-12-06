package agh.ics.oop;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;


public class OptionsParserTest {
    private final String[] stringDirections1 = new String[]{"f", "b", "c"};
    private final String[] stringDirections2 = new String[]{"forward", "right","backward", "jsbj"};
    private final String[] stringDirections3 = new String[]{"r", "l", "sjfjfb","left"};
    private final String[] stringDirections4 = new String[]{"r","f","f","l","f","f"};
    private final OptionsParser parser = new OptionsParser();
    private Animal doSteps(String[] stringDirections){
        Animal animal = new Animal(new RectangularMap(4,4),new Vector2d(2,2));
        ArrayList<MoveDirection> moveDirections = parser.parse(stringDirections);
        for (MoveDirection direction : moveDirections){
            animal.move(direction);
        }
        return animal;
    }
    @Test
    public void parseTest(){
        Exception ex1 = assertThrows(IllegalArgumentException.class, () -> {parser.parse(stringDirections1);});
        assertEquals("c is an illegal argument", ex1.getMessage());
        Exception ex2 = assertThrows(IllegalArgumentException.class, () -> {parser.parse(stringDirections2);});
        assertEquals("jsbj is an illegal argument", ex2.getMessage());
        Exception ex3 = assertThrows(IllegalArgumentException.class, () -> {parser.parse(stringDirections3);});
        assertEquals("sjfjfb is an illegal argument", ex3.getMessage());
        assertEquals(Arrays.asList(MoveDirection.RIGHT,MoveDirection.FORWARD,MoveDirection.FORWARD,MoveDirection.LEFT,MoveDirection.FORWARD,MoveDirection.FORWARD),parser.parse(stringDirections4));
    }
    @Test
    public void directionAfterMoveTest(){
        Animal animal = new Animal(new RectangularMap(4,4),new Vector2d(2,2));
        animal.move(MoveDirection.RIGHT);
        assertEquals(animal.getDirection(),MapDirection.EAST);
        animal.move(MoveDirection.FORWARD);
        assertEquals(animal.getDirection(),MapDirection.EAST);
        animal.move(MoveDirection.BACKWARD);
        assertEquals(animal.getDirection(),MapDirection.WEST);
        animal.move(MoveDirection.RIGHT);
        assertEquals(animal.getDirection(),MapDirection.NORTH);
        animal.move(MoveDirection.FORWARD_LEFT);
        assertEquals(animal.getDirection(),MapDirection.NORTH_WEST);
    }
    @Test
    public void positionAfterMoveTest(){
        Animal animal = new Animal(new RectangularMap(4,4),new Vector2d(2,2));
        animal.move(MoveDirection.RIGHT);
        assertEquals(new Vector2d(2,2), animal.getPosition());
        animal.move(MoveDirection.FORWARD);
        assertEquals(new Vector2d(3,2), animal.getPosition());
        animal.move(MoveDirection.BACKWARD);
        assertEquals(new Vector2d(3,2), animal.getPosition());
        animal.move(MoveDirection.RIGHT);
        assertEquals(new Vector2d(3,2), animal.getPosition());
        animal.move(MoveDirection.FORWARD);
        assertEquals(new Vector2d(3,3), animal.getPosition());
    }
    @Test
    public void integrateTest(){
        Animal animal4 = doSteps(stringDirections4);
        assertEquals( new Vector2d(3,3),animal4.getPosition());

    }
}
