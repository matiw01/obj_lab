package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GrassFieldTest {
    private final GrassField grassField = new GrassField(10);

    @Test
    public void prepareSimulationTest(){
        grassField.place(new Animal(grassField,new Vector2d(-1,-1)));
        grassField.place(new Animal(grassField,new Vector2d(8,9)));
        grassField.place(new Animal(grassField,new Vector2d(9,9)));
        assertArrayEquals(new Vector2d[] {new Vector2d(-1,-1), new Vector2d(9,9)},grassField.getCorrners());
    }
}

