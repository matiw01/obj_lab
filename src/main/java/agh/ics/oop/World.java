package agh.ics.oop;


import java.util.ArrayList;

public class World {
    
    public static void main(String[] args) {
        try {
            String[] stringDirections = {"f", "b", "r", "l", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"};
            ArrayList<MoveDirection> directions = new OptionsParser().parse(stringDirections);
            IWorldMap map = new GrassField(10);
            Vector2d[] positions = {  new Vector2d(1,1), new Vector2d(10,10)};
            IEngine engine = new SimulationEngine(directions, map, positions);
            engine.run();
        }
        catch (IllegalArgumentException ex){
            System.out.println(ex);
            System.exit(0);
        }
    }
}