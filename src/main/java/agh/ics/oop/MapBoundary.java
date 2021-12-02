package agh.ics.oop;

import java.sql.SQLOutput;
import java.util.*;
import java.util.function.Function;

public class MapBoundary implements IPositionChangedObserver{
    private final SortedSet<Vector2d> animalsSortedSetX = new TreeSet<Vector2d>(new Comparator<Vector2d>() {
        @Override
        public int compare(Vector2d vector1, Vector2d vector2) {
            int cmp = 0;
            if (vector1.x>vector2.x){ cmp = 1;}
            if (vector1.x == vector2.x && vector1.y > vector2.y){ cmp = 1;}
            if (vector1.x == vector2.x && vector1.y < vector2.y){ cmp = -1;}
            if (vector1.x < vector2.x){cmp = -1;}
            return cmp;
        }
    });
    private final SortedSet<Vector2d> animalsSortedSetY = new TreeSet<Vector2d>(new Comparator<Vector2d>() {
        @Override
        public int compare(Vector2d vector1, Vector2d vector2) {
            int cmp = 0;
            if (vector1.y>vector2.y){ cmp = 1;}
            if (vector1.y == vector2.y && vector1.x > vector2.x){ cmp = 1;}
            if (vector1.y == vector2.y && vector1.x < vector2.x){ cmp = -1;}
            if (vector1.y < vector2.y){cmp = -1;}
            return cmp;
        }
    });

    public void addAnimal(Animal animal){
        animalsSortedSetX.add(animal.getPosition());
        animalsSortedSetY.add(animal.getPosition());
    }
    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        animalsSortedSetX.remove(oldPosition);
        animalsSortedSetY.remove(oldPosition);
        animalsSortedSetX.add(newPosition);
        animalsSortedSetY.add(newPosition);
    }
    public Vector2d getUpperRight() {
        return new Vector2d(animalsSortedSetX.last().x, animalsSortedSetY.last().y);
    }
    public Vector2d getLowerLeft(){
        return new Vector2d(animalsSortedSetX.first().x, animalsSortedSetY.first().y);
        }
    }