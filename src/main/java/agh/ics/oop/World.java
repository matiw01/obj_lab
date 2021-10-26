package agh.ics.oop;


import java.util.Arrays;

public class World {
    
    public static void main(String[] args) {
        Animal animal = new Animal();
        String[] directions = {"f","backward","right","false","f"};
        OptionsParser parser = new OptionsParser();
        MoveDirection[] moveDirectons = parser.parse(directions);
        for(MoveDirection direction : moveDirectons){
            animal.move(direction);
        }
        System.out.println(animal);
    }


}
