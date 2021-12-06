package agh.ics.oop;


import java.util.ArrayList;
import java.util.List;

public class SimulationEngine implements IEngine {
    private final ArrayList<MoveDirection> moves;
    private final IWorldMap map;
    int animalsLen;
    private final ArrayList<Animal> animalsList = new ArrayList<Animal>();
    public SimulationEngine(ArrayList<MoveDirection> moves, IWorldMap map, Vector2d[] animalStartPositions){
        this.moves = moves;
        this.map = map;
        this.animalsLen = animalStartPositions.length;
        for(Vector2d animalStartingPosition : animalStartPositions) {
            Animal animal = new Animal(map, animalStartingPosition, new ArrayList<>());
            map.place(animal);
            animalsList.add(animal);
        }
    }

    @Override
    public void run(){
        int movesLen = moves.size();
        for(int i = 0; i < movesLen; i++){
            Animal animal = animalsList.get(i%animalsLen);
            animal.move(moves.get(i));
            System.out.println(map);
        }
    }
    public Vector2d getAnimalPos(int n){return animalsList.get(n).getPosition();}
}
