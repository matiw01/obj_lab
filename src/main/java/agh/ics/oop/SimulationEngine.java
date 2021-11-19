package agh.ics.oop;


import java.util.ArrayList;

public class SimulationEngine implements IEngine {
    private final MoveDirection[] moves;
    private final IWorldMap map;
    int animalsLen;
    private final ArrayList<Animal> animalsList = new ArrayList<Animal>();
    public SimulationEngine(MoveDirection[] moves, IWorldMap map, Vector2d[] animalStartPositions){
        this.moves = moves;
        this.map = map;
        this.animalsLen = animalStartPositions.length;
        for(Vector2d animalStartingPosition : animalStartPositions) {
            Animal animal = new Animal(map,animalStartingPosition);
            map.place(animal);
            animalsList.add(animal);
        }
    }

    @Override
    public void run(){
        int movesLen = moves.length;
        for(int i = 0; i < movesLen; i++){
            Animal animal = animalsList.get(i%animalsLen);
            animal.move(moves[i]);
            System.out.println(map);
        }
    }
    public Vector2d getAnimalPos(int n){return animalsList.get(n).getPosition();}
}
