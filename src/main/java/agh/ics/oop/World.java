package agh.ics.oop;


import java.util.Arrays;

public class World {
    
    public static void main(String[] args) {
//        Animal animal = new Animal();
//        animal.move(MoveDirection.RIGHT);
//        animal.move(MoveDirection.FORWARD);
//        animal.move(MoveDirection.FORWARD);
//        animal.move(MoveDirection.FORWARD);
//        System.out.println(animal);
        String[] directions = {"f","backward","right","false"};
        OptionsParser parser = new OptionsParser();
        MoveDirection[] moveDirectons = parser.parse(directions);
        System.out.println(Arrays.toString(moveDirectons));

    }


}
