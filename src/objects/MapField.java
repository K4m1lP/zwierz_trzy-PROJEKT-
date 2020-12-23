package objects;
import adds.Vector2d;
import java.util.*;
import java.util.stream.Collectors;
public class MapField {
    private final LinkedList<Animal> listOfAnimals = new LinkedList<>();
    private final Vector2d position;
    Comparator<Animal> energyComparator = Comparator.comparing(Animal::getEnergy).reversed();
    public MapField(Vector2d position) {
        this.position = position;
    }
    public void addAnimal(Animal animal){
        this.listOfAnimals.add(animal);
    }
    public boolean removeAnimal(Animal animal){
        this.listOfAnimals.remove(animal);
        return this.listOfAnimals.isEmpty();
    }
    public List<Animal> getAnimalsToFeed(){
        this.listOfAnimals.sort(energyComparator);
        int h = this.listOfAnimals.get(0).getEnergy();
        return this.listOfAnimals.stream().filter(animal -> animal.getEnergy() == h).collect(Collectors.toList());
    }
    public List<Animal> getAnimalsToSex(){
        this.listOfAnimals.sort(energyComparator);
        if(isLoveHere() && listOfAnimals.get(0).canSex() && listOfAnimals.get(1).canSex())
            return Arrays.asList(listOfAnimals.get(0), listOfAnimals.get(1));
        return Collections.emptyList();
    }
    public boolean isLoveHere(){ return this.listOfAnimals.size()>=2; }
    public Vector2d getPosition(){ return this.position; }
    public Animal getAnimal(){
        this.listOfAnimals.sort(energyComparator);
        return listOfAnimals.getFirst();
    }

}
