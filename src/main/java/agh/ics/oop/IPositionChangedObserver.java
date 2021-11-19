package agh.ics.oop;

public interface IPositionChangedObserver {
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition);
}
