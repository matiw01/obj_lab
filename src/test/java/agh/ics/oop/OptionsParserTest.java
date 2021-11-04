package agh.ics.oop;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import java.util.SplittableRandom;


public class OptionsParserTest {
    private final String[] stringDirections1 = new String[]{"f", "b", "c"};
    private final String[] stringDirections2 = new String[]{"forward", "right","backward", "jsbj"};
    private final String[] stringDirections3 = new String[]{"r", "l", "sjfjfb","left"};
    private final String[] stringDirections4 = new String[]{"r","f","f","l","f","f"};
    private final OptionsParser parser = new OptionsParser();
    private Animal doSteps(String[] stringDirections){
        Animal animal = new Animal(new RectangularMap(4,4),new Vector2d(2,2));
        MoveDirection[] moveDirections = parser.parse(stringDirections);
        for (MoveDirection direction : moveDirections) {
            animal.move(direction);
        }
        return animal;
    }
    @Test
    public void parseTest(){
        assertArrayEquals(parser.parse(stringDirections1), new MoveDirection[] {MoveDirection.FORWARD,MoveDirection.BACKWARD});
        assertArrayEquals(parser.parse(stringDirections2), new MoveDirection[] {MoveDirection.FORWARD,MoveDirection.RIGHT,MoveDirection.BACKWARD});
        assertArrayEquals(parser.parse(stringDirections3), new MoveDirection[] {MoveDirection.RIGHT,MoveDirection.LEFT,MoveDirection.LEFT});
        assertArrayEquals(parser.parse(stringDirections4), new MoveDirection[] {MoveDirection.RIGHT,MoveDirection.FORWARD,MoveDirection.FORWARD,MoveDirection.LEFT,MoveDirection.FORWARD,MoveDirection.FORWARD});
    }
    @Test
    public void directionAfterMoveTest(){
        Animal animal = new Animal(new RectangularMap(4,4),new Vector2d(2,2));
        animal.move(MoveDirection.RIGHT);
        assertEquals(animal.getDirection(),MapDirection.EAST);
        animal.move(MoveDirection.FORWARD);
        assertEquals(animal.getDirection(),MapDirection.EAST);
        animal.move(MoveDirection.BACKWARD);
        assertEquals(animal.getDirection(),MapDirection.EAST);
        animal.move(MoveDirection.RIGHT);
        assertEquals(animal.getDirection(),MapDirection.SOUTH);
        animal.move(MoveDirection.LEFT);
        assertEquals(animal.getDirection(),MapDirection.EAST);
    }
    @Test
    public void positionAfterMoveTest(){
        Animal animal = new Animal(new RectangularMap(4,4),new Vector2d(2,2));
        animal.move(MoveDirection.RIGHT);
        assertEquals(animal.getPosition(),new Vector2d(3,2));
        animal.move(MoveDirection.FORWARD);
        assertEquals(animal.getPosition(),new Vector2d(4,2));
        animal.move(MoveDirection.BACKWARD);
        assertEquals(animal.getPosition(),new Vector2d(3,2));
        animal.move(MoveDirection.RIGHT);
        assertEquals(animal.getPosition(),new Vector2d(3,1));
        animal.move(MoveDirection.LEFT);
        assertEquals(animal.getPosition(),new Vector2d(4,1));
    }
    @Test
    public void integrateTest(){
        Animal animal1 = doSteps(stringDirections1);
        assertEquals(animal1.getPosition(),new Vector2d(2,2));
        Animal animal2 = doSteps(stringDirections2);
        assertEquals(animal2.getPosition(),new Vector2d(2,3));
        Animal animal3 = doSteps(stringDirections3);
        assertEquals(animal3.getPosition(),new Vector2d(2,3));
        Animal animal4 = doSteps(stringDirections4);
        assertEquals(animal4.getPosition(), new Vector2d(4,4));

    }
}
