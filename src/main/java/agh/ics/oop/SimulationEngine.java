package agh.ics.oop;


import java.util.ArrayList;

public class SimulationEngine implements IEngine {
    private final MoveDirection[] moves;
    private final IWorldMap map;
    private final Vector2d[] animalStartPositions;
    private final ArrayList<Animal> animalsList = new ArrayList<Animal>();
    public SimulationEngine(MoveDirection[] moves, IWorldMap map, Vector2d[] animalStartPositions){
        this.moves = moves;
        this.map = map;
        this.animalStartPositions = animalStartPositions;
        for(Vector2d animalStartingPosition : animalStartPositions) {
            Animal animal = new Animal(map,animalStartingPosition);
            map.place(animal);
            animalsList.add(animal);
        }
    }

    @Override
    public void run() {
        int animalsLen = animalStartPositions.length;
        int movesLen = moves.length;
//        System.out.println(map.getAnimalsList().get(1));
        for(int i = 0; i < movesLen; i++){
            Animal animal = animalsList.get(i%animalsLen);
            animal.move(moves[i]);
        }
        for(Animal animal : animalsList){
            System.out.println(animal);
        }
    }

//
//    public IWorldMap getMap() {
//        return map;
//    }
}
