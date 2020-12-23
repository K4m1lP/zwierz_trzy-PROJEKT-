package objects;
import adds.MapDirection;
import adds.Vector2d;
import map.IWorldMap;
import observer.IPositionChangeObserver;
import java.util.ArrayList;
import java.util.LinkedList;

public class Animal{
    private final ArrayList<IPositionChangeObserver> listOfObservers = new ArrayList<>();
    private final LinkedList<Animal> children = new LinkedList<>();
    private final IWorldMap map;
    private final Genes genes;
    private final int startEnergy;
    private MapDirection orientation;
    private int energy;
    private Vector2d position;
    private int howManyChildren;
    private int age;
    public Animal(IWorldMap map, Vector2d position, int energy){ this(map,position,energy,new Genes()); }
    public Animal(IWorldMap map, Vector2d position, int energy,Genes genes){
        this.energy = energy;
        this.startEnergy = energy;
        this.genes = genes;
        this.map = map;
        this.position = position;
        this.orientation = MapDirection.NORTH.setTo((int)(Math.random()*7));
        this.age=0;
        this.howManyChildren=0;
    }
    public void move(){
        this.orientation = this.orientation.setTo(genes.getRandomDecision());
        Vector2d currPos = this.position;
        this.position = map.fixedPosition(this.position.add(this.orientation.toUnitVector()));
        this.positionChanged(currPos,this.position,this);
    }
    public void addObserver(IPositionChangeObserver observer){ this.listOfObservers.add(observer); }
    public void positionChanged(Vector2d oldPosition,Vector2d newPosition, Animal animal){
        for(IPositionChangeObserver x:this.listOfObservers)
            x.positionChanged(oldPosition, newPosition, animal);
    }
    public void removeObserver(IPositionChangeObserver x){ this.listOfObservers.remove(x); }
    public void decrementEnergy(int x){ this.energy-=x; }
    public int getEnergy(){ return this.energy; }
    public Vector2d getPosition(){ return this.position; }
    public void addEnergy(int val){ this.energy+=val; }
    public Animal copulation(Animal mother) {
        int childEnergy = (int) ((0.25 * mother.energy) + (this.energy * 0.25));
        mother.decrementEnergy((int)(0.25 * mother.energy));
        this.decrementEnergy((int)(0.25 * this.energy));
        Animal child = new Animal(map, map.getBornField(mother.getPosition()), childEnergy, this.genes.getChildGenes(mother.genes));
        map.place(child);
        this.howManyChildren++;
        this.children.add(child);
        return child;
    }
    public boolean canSex(){ return this.energy > (int)(0.5*this.startEnergy); }
    public boolean isDead(){
        return this.energy<=0;
    }
    public void addDay(){ this.age++; }
    public int getAge(){ return this.age; }
    public int getHowManyChildren(){ return this.howManyChildren; }
    public Genes getGenes(){
        return this.genes;
    }
    public int getStartEnergy(){
        return this.startEnergy;
    }
    public int howManyChildOfChild(int n, LinkedList<Animal> present){
        if(howManyChildren == 0 || n == 0) return 0;
        else {
            int res = howManyChildren;
            for(Animal an: children){
                if(!present.contains(an)){
                    present.add(an);
                    res+=an.howManyChildOfChild(n-1,present);
                }
            }
            return res+1;
        }
    }
}
//Program powinien umożliwiać uzyskanie statystyki (jak w punkcie 4) po określonej
// liczbie epok w formie pliku tekstowego. Statystyki powinny stanowić uśrednienie
// wartości z poszczególnych epok.
