package agh.ics.oop;
// dobrze by to podzielić na pakiety, nie tylko GUI w osobnym
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FlippedMap extends AbstractWorldMap implements IWorldMap { // Flipped, to chyba nie najlepsze słowo
    private Integer plantEnergy;    // to nie jest część wspólna dla wszystkich map?

    public FlippedMap(int width, int height, Integer plantEnergy, Integer jungleRatio) {
        super(width, height, plantEnergy, jungleRatio);
    }

    @Override
    public Vector2d[] getCorrners() {   // sporo ma Pan literówek
        return new Vector2d[]{lowerLeft, upperRight};
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return true;
    }

}
