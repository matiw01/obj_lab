package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class MapDirecionTest {
    @Test
    public void testNext() {
        assertEquals(MapDirection.WEST.changeDirection(1), MapDirection.NORTH_WEST);
        assertEquals(MapDirection.NORTH.changeDirection(2), MapDirection.EAST);
        assertEquals(MapDirection.EAST.changeDirection(3), MapDirection.SOUTH_WEST);
        assertEquals(MapDirection.SOUTH.changeDirection(4), MapDirection.NORTH);
        assertEquals(MapDirection.NORTH_WEST.changeDirection(5),MapDirection.SOUTH);
    }
    @Test
    public void testPrevious() {
        assertEquals(MapDirection.WEST.previous(), MapDirection.SOUTH_WEST);
        assertEquals(MapDirection.NORTH.previous(), MapDirection.NORTH_WEST);
        assertEquals(MapDirection.EAST.previous(), MapDirection.NORTH_EAST);
        assertEquals(MapDirection.SOUTH.previous(), MapDirection.SOUTH_EAST);
    }

}
