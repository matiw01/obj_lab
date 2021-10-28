package agh.ics.oop;


public class OptionsParser {
    public MoveDirection[] parse(String[] stringDirections){
        int inputLen = stringDirections.length;
        MoveDirection[] moveDirections = new MoveDirection[inputLen];
        int j = 0;
        for (String stringDirection : stringDirections) {
            MoveDirection direction = switch (stringDirection) {
                case "f", "forward" -> MoveDirection.FORWARD;
                case "b", "backward" -> MoveDirection.BACKWARD;
                case "r", "right" -> MoveDirection.RIGHT;
                case "l", "left" -> MoveDirection.LEFT;
                default -> null;
            };
            moveDirections[j] = direction;
            if (direction != null) {
                j++;
            }
        }
        MoveDirection[] resultMoveDirections = new MoveDirection[j];
        for(int i = 0; i<j; i++){
            if(moveDirections[i] != null){
                resultMoveDirections[i] = moveDirections[i];
            }
        }
        return resultMoveDirections;
    }
}
