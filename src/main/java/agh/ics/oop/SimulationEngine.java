package agh.ics.oop;



public class SimulationEngine implements IEngine {
    private final MoveDirection[] moves;
    private final IWorldMap map;
    private final Vector2d[] animalStartPositions;
    public SimulationEngine(MoveDirection[] moves, IWorldMap map, Vector2d[] animalStartPositions){
        this.moves = moves;
        this.map = map;
        this.animalStartPositions = animalStartPositions;
        for(Vector2d animalStartingPosition : animalStartPositions) {
            Animal animal = new Animal(map,animalStartingPosition);
            map.place(animal);
        }
    }

    @Override
    public void run() {
        int animalsLen = animalStartPositions.length;
        int movesLen = moves.length;
//        System.out.println(map.getAnimalsList().get(1));
        for(int i = 0; i < movesLen; i++){
            Animal animal = map.getAnimalsList().get(i%animalsLen);
            animal.move(moves[i]);
        }
    }

    public IWorldMap getMap() {
        return map;
    }
}
