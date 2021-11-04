package agh.ics.oop;

public enum MapDirection {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    public MapDirection next() {
        MapDirection[] mapDirections = values();
        int direcionNumber = this.ordinal() + 1;
        return mapDirections[direcionNumber % mapDirections.length];
    }

    public MapDirection previous() {
        MapDirection[] mapDirections = values();
        int direcionNumber = this.ordinal() - 1;
        if(direcionNumber < 0){direcionNumber = direcionNumber+mapDirections.length;}
        return mapDirections[direcionNumber];
    }

    public Vector2d toUnitVector() {
        return switch (this) {
            case NORTH -> new Vector2d(0, 1);
            case EAST -> new Vector2d(1, 0);
            case SOUTH -> new Vector2d(0, -1);
            case WEST -> new Vector2d(-1, 0);
        };
    }
    public String toString(){
        return switch (this){
            case NORTH ->  "N";
            case EAST -> "E";
            case SOUTH -> "S";
            case WEST -> "W";
        };
    }
}
