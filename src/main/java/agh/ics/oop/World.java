package agh.ics.oop;


public class World {
    
    public static void main(String[] args) {
        String[] stringDirections = {"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"};
        MoveDirection[] directions = new OptionsParser().parse(stringDirections);
        IWorldMap map = new RectangularMap(10, 5);
        Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,4) };
        IEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();
        System.out.println(map);
    }
}


//        String[] directions = {"f","backward","right","false","f"};
//        OptionsParser parser = new OptionsParser();
//        MoveDirection[] moveDirectons = parser.parse(directions);
//        for(MoveDirection direction : moveDirectons){
//            animal.move(direction);
//        }