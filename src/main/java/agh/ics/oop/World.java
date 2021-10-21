package agh.ics.oop;


public class World {
    
    public static void main(String[] args) {

        Direction[] elements = convertToDirection(args);
        System.out.println("Start");
        run(elements);
        System.out.println("Stop");
    }

    private static Direction[] convertToDirection(String[] args) {
        Direction[] elements = new Direction[args.length];
        for(int i = 0; i< args.length; i++){
            if (args[i].equals("f"))
                elements[i] = Direction.FORWARD;
            else if ((args[i].equals("b")))
                elements[i] = Direction.BACKWARD;
            else if ((args[i].equals("r")))
                elements[i] = Direction.RIGHT;
            else if ((args[i].equals("l")))
                elements[i] = Direction.LEFT;
            else
                elements[i] = Direction.MISTAKE;
        }
        return elements;
    }

    private static void run(Direction[] elements) {
        int cnt = 0;
        for(Direction element : elements) {
            String message = switch (element) {
                case FORWARD -> "Do przodu";
                case BACKWARD -> "Do tyłu";
                case RIGHT -> "W prawo";
                case LEFT -> "W lewo";
                default -> null;
            };
            if(cnt==0){
                if(message != null){
                    System.out.print(message);
                    cnt+=1;
                }
            }
            else if(message != null)
                System.out.print(","+message);
        }
        System.out.print("\n");
    }
}
