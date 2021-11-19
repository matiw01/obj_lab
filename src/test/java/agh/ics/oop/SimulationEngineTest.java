package agh.ics.oop;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SimulationEngineTest {
    @Test
    public void RunTest1(){
        SimulationEngine engine = new SimulationEngine(new MoveDirection[] {MoveDirection.RIGHT,MoveDirection.FORWARD,MoveDirection.LEFT,MoveDirection.FORWARD,MoveDirection.BACKWARD,MoveDirection.FORWARD,MoveDirection.FORWARD},new RectangularMap(3,3),new Vector2d[] {new Vector2d(0,2), new Vector2d(2,1), new Vector2d(1,0)});
        engine.run();
        assertEquals(engine.getAnimalPos(0),new Vector2d(2,2));
        assertEquals(engine.getAnimalPos(1),new Vector2d(2,1));
        assertEquals(engine.getAnimalPos(2),new Vector2d(0,0));
    }
    @Test
    public void RunTest2(){
        GrassField grassField = new GrassField(10);
        MoveDirection[] moveDirections = {MoveDirection.BACKWARD,MoveDirection.FORWARD,MoveDirection.RIGHT,MoveDirection.BACKWARD,MoveDirection.FORWARD,MoveDirection.FORWARD,MoveDirection.BACKWARD,MoveDirection.FORWARD,MoveDirection.FORWARD};
        Vector2d[] anmialsStartingPos = {new Vector2d(-1,-1),new Vector2d(9,8),new Vector2d(9,9)};
        SimulationEngine engine1 = new SimulationEngine(moveDirections,grassField,anmialsStartingPos);
        engine1.run();
        assertEquals(new Vector2d(-1,-4),engine1.getAnimalPos(0));
        assertEquals(new Vector2d(9,10),engine1.getAnimalPos(1));
        assertEquals(new Vector2d(12,9),engine1.getAnimalPos(2));
    }
}
