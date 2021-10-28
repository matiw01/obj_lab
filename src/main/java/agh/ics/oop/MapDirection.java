package agh.ics.oop;

public enum MapDirection {
    NORTH,
    EAST,
    SOUTH,
    WEST;
    private final static MapDirection[] mapDirections = values();
    public MapDirection next() {
        int direcionNumber = this.ordinal() + 1;
        return mapDirections[direcionNumber % mapDirections.length];
    }

    public MapDirection previous() {
        int direcionNumber = this.ordinal() - 1;
        if(direcionNumber < 0){direcionNumber = direcionNumber+mapDirections.length;}
        return mapDirections[direcionNumber];
    }

    public Vector2d toUnitVector() {
        return switch (this) {
            case NORTH -> new Vector2d(1, 0);
            case EAST -> new Vector2d(0, 1);
            case SOUTH -> new Vector2d(-1, 0);
            case WEST -> new Vector2d(0, -1);
        };
    }
//    public String toString(){return ""+mapDirections[ordinal()];}
}
