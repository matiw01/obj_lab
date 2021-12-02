package agh.ics.oop;

import java.sql.SQLOutput;
import java.util.*;
import java.util.function.Function;

public class MapBoundary implements IPositionChangedObserver{
    private final ArrayList<Animal> animalsListX = new ArrayList<Animal>();
    private final ArrayList<Animal> animalsListY = new ArrayList<>();

    private int binarySearchX(ArrayList<Animal> animalsListX, Animal animal) {
        int low = 0;
        int high = animalsListX.size() - 1;
        Integer positionX = animal.getPosition().x;
        Integer positionY = animal.getPosition().y;
        while (low <= high) {
            int mid = (low + high) / 2;
            Integer midValX = animalsListX.get(mid).getPosition().x;
            Integer midValY = animalsListX.get(mid).getPosition().y;
            int cmp = 0;
            if(positionX > midValX){ cmp = 1;}
            else if (positionX < midValX){ cmp = -1;}
            else if (positionX.equals(midValX) && positionY < midValY){ cmp = -1;}
            else if (positionX.equals(midValX) && positionY.equals(midValY)){throw new IllegalArgumentException("position " + animal.getPosition() + " is not available");}
            else if (positionX.equals(midValX) && positionY > midValY){ cmp = 1;}
            if (cmp > 0)
                low = mid + 1;
            else if (cmp < 0)
                high = mid - 1;
            else
                return mid;
        }
        return low;
    }
    private int binarySearchY(ArrayList<Animal> animalsListY,  Animal animal) {
        int low = 0;
        int high = animalsListY.size() - 1;
        Integer positionX = animal.getPosition().x;
        Integer positionY = animal.getPosition().y;
        while (low <= high) {
            int mid = (low + high) / 2;
            Integer midValX = animalsListY.get(mid).getPosition().x;
            Integer midValY = animalsListY.get(mid).getPosition().y;
            int cmp = 0;
            if(positionY > midValY){ cmp = 1;}
            else if (positionY < midValY){ cmp = -1;}
            else if (positionY.equals(midValY) && positionX < midValX){ cmp = -1;}
            else if (positionY.equals(midValY) && positionX.equals(midValX)){throw new IllegalArgumentException("position " + animal.getPosition() + " is not available");}
            else if (positionY.equals(midValY) && positionX > midValX){ cmp = 1;}
            if (cmp == 0) {throw new IllegalArgumentException("not good");}
            if (cmp > 0)
                low = mid + 1;
            else if (cmp < 0)
                high = mid - 1;
        }
        return low;
    }

    public void addAnimal(Animal animal){
        int animalX = binarySearchX(animalsListX, animal);
        animalsListX.add(animalX, animal);
        int animalY = binarySearchY(animalsListY, animal);
        animalsListY.add(animalY, animal);
        System.out.println(animalsListX);
        System.out.println(animalsListY);
    }
    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        int idX = 0;
        while (!animalsListX.get(idX).getPosition().equals(oldPosition)){
            idX++;
        }
        Animal animal = animalsListX.get(idX);
        animalsListX.remove(idX);
        int idY = 0;
        while (!animalsListY.get(idY).getPosition().equals(oldPosition) ){
            idY++;
        }
        animalsListX.remove(idY);
        addAnimal(animal);
    }
    public Vector2d getUpperRight(){
//        System.out.println(new Vector2d(animalsListX.get(animalsListX.size()-1).getPosition().x,animalsListY.get(animalsListX.size()-1).getPosition().y));
        return new Vector2d(animalsListX.get(animalsListX.size()-1).getPosition().x,animalsListY.get(animalsListX.size()-1).getPosition().y);}
    public Vector2d getLowerLeft(){
//        System.out.println(new Vector2d(animalsListX.get(0).getPosition().x,animalsListY.get(0).getPosition().y));
        return new Vector2d(animalsListX.get(0).getPosition().x,animalsListY.get(0).getPosition().y);}
}
