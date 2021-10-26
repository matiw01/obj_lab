package agh.ics.oop;


public class OptionsParser {
    public MoveDirection[] parse(String[] stringDirections){
        int inputLen = stringDirections.length;
        MoveDirection[] moveDirections = new MoveDirection[inputLen];
        int j = 0;
        for(int i=0; i<inputLen; i++){
            MoveDirection direction = switch(stringDirections[i]){
                case "f", "forward" -> MoveDirection.FORWARD;
                case "b","backward" -> MoveDirection.BACKWARD;
                case "r","right" -> MoveDirection.RIGHT;
                case "l","left" -> MoveDirection.LEFT;
                default -> null;
            };
            moveDirections[j] = direction;
            if(direction != null){
                j++;
            }
        }
        MoveDirection[] finalMoveDirections = new MoveDirection[j];
        for(int i = 0; i<j; i++){
            if(moveDirections[i] != null){
                finalMoveDirections[i] = moveDirections[i];
            }
        }
        return finalMoveDirections;
    }
}
