package observer;
import adds.Vector2d;
import objects.Animal;
public interface IPositionChangeObserver {
    void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal);
}
