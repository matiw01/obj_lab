package agh.ics.oop;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SimulationEngineTest {
    @Test
    public void RunTest(){
        SimulationEngine engine = new SimulationEngine(new MoveDirection[] {MoveDirection.RIGHT,MoveDirection.FORWARD,MoveDirection.LEFT,MoveDirection.FORWARD,MoveDirection.BACKWARD,MoveDirection.FORWARD,MoveDirection.FORWARD},new RectangularMap(2,2),new Vector2d[] {new Vector2d(0,2), new Vector2d(2,1), new Vector2d(1,0)});
        engine.run();
        assertEquals(engine.getMap().getAnimalsList().get(0).getPosition(),new Vector2d(2,2));
        assertEquals(engine.getMap().getAnimalsList().get(1).getPosition(),new Vector2d(2,1));
        assertEquals(engine.getMap().getAnimalsList().get(2).getPosition(),new Vector2d(0,0));
    }

}
