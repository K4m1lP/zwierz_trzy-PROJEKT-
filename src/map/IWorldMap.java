package map;
import adds.Vector2d;
import objects.Animal;
import objects.Genes;

import java.util.LinkedList;
import java.util.List;
public interface IWorldMap {
    void place(Animal animal);
    void addGrass(int how);
    Object objectAt(Vector2d position);
    Vector2d fixedPosition(Vector2d position);
    List<List<Animal>> feedAnimal();
    List<List<Animal>> makeLove();
    void removeAnimal(Animal animal);
    Vector2d getBornField(Vector2d copulationPos);
    LinkedList<Animal> getAnimalsPos();
    LinkedList<Vector2d> getGrassPos();
    Genes getGenesAtPos(Vector2d pos);
    Animal getAnAtPos(Vector2d pos);
}
