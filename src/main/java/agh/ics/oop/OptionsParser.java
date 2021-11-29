package agh.ics.oop;


import java.util.ArrayList;

public class OptionsParser {
    public ArrayList<MoveDirection> parse(String[] stringDirections){
        int inputLen = stringDirections.length;
//        MoveDirection[] moveDirections = new MoveDirection[inputLen];
        ArrayList<MoveDirection> moveDirections = new ArrayList<MoveDirection>();
        int j = 0;
        for (String stringDirection : stringDirections) {
            MoveDirection direction = switch (stringDirection) {
                case "f", "forward" -> MoveDirection.FORWARD;
                case "b", "backward" -> MoveDirection.BACKWARD;
                case "r", "right" -> MoveDirection.RIGHT;
                case "l", "left" -> MoveDirection.LEFT;
                default -> throw new IllegalArgumentException(stringDirection + " is an illegal argument");
            };
            moveDirections.add(direction);
        }
        return moveDirections;
    }
}
