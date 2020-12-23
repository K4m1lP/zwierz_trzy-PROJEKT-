package map;
import DataValidation.ParserFields;
import adds.Vector2d;
import objects.Animal;
import objects.Genes;
import objects.MapField;
import observer.IPositionChangeObserver;
import java.util.*;
public class WorldMap implements IWorldMap, IPositionChangeObserver {
    private final HashMap<Vector2d, MapField> animals = new HashMap<>();
    private final HashMap<Vector2d, Vector2d> grasses = new HashMap<>();
    private final Vector2d RIGHT;
    private final Vector2d jungleRight;
    private final Vector2d jungleLeft = new Vector2d(0,0);
    public WorldMap(ParserFields data){
        this.RIGHT = new Vector2d(data.getMapHeight(),data.getMapWidth());
        this.jungleRight = new Vector2d((int)(data.getMapWidth()*Math.sqrt((double)data.getProportion()/100)),(int)(data.getMapHeight()*Math.sqrt((double)data.getProportion()/100)));
    }
    public Vector2d getBornField(Vector2d copulationPos){
        int[] dx = {0,1,1,1,0,-1,-1,-1};
        int[] dy = {1,1,0,-1,-1,-1,0,1};
        int xInit = copulationPos.getX();
        int yInit = copulationPos.getY();
        for(int i=0;i<8;i++){
            Vector2d testV = fixedPosition(new Vector2d(xInit+dx[i],yInit+dy[i]));
            if(this.animals.get(testV)==null) return testV;
        }
        return fixedPosition(new Vector2d(xInit+dx[(int)(Math.random()*7)],yInit+dy[(int)(Math.random()*7)]));
    }
    @Override
    public void addGrass(int how) {
        for(int i =0;i<how;i++){
            Vector2d x = getGrassInJungle();
            if(x != null) this.grasses.put(x,x);
            Vector2d y = getPosOfGrassOutOfJungle();
            if(y != null) this.grasses.put(y,y);
        }
    }
    @Override
    public Object objectAt(Vector2d position) {
        if(animals.get(position) == null && grasses.get(position) == null){
            return null;
        } else {
            if(animals.get(position) != null)  return animals.get(position);
            if(grasses.get(position) != null) return grasses.get(position);
        }
        return null;
    }
    @Override
    public Vector2d fixedPosition(Vector2d position) {
        return new Vector2d((position.getX() + RIGHT.getX()) % RIGHT.getX(),(position.getY() + RIGHT.getY()) % RIGHT.getY());
    }
    @Override
    public List<List<Animal>> feedAnimal() {
        List<List<Animal>> hungry = new ArrayList<>();
        animals.values().forEach(mapField -> {
            if(this.grasses.get(mapField.getPosition()) != null){
                hungry.add(mapField.getAnimalsToFeed());
                this.grasses.remove(mapField.getPosition());
            }
        });
        return hungry;
    }
    @Override
    public List<List<Animal>> makeLove() {
        List<List<Animal>> couples = new ArrayList<>();
        animals.values().forEach(mapField -> couples.add(mapField.getAnimalsToSex()));
        return couples;
    }
    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition,Animal animal) {
        if(this.animals.get(oldPosition).removeAnimal(animal)){
            this.animals.remove(oldPosition);
        }
        animal.removeObserver(this);
        place(animal);
    }
    public LinkedList<Animal> getAnimalsPos(){
        LinkedList<Animal> res = new LinkedList<>();
        for(Object x: this.animals.keySet().toArray()){
            res.add(this.animals.get((Vector2d)x).getAnimal());
        }
        return res;
    }
    public LinkedList<Vector2d> getGrassPos(){
        LinkedList<Vector2d> res = new LinkedList<>();
        this.grasses.forEach((vector2d, vector2d2) -> res.add(new Vector2d(vector2d.getX(),vector2d.getY())));
        return res;
    }
    public void place(Animal animal){
        animal.addObserver(this);
        if(!this.animals.containsKey(animal.getPosition())) {
            MapField x = new MapField(animal.getPosition());
            x.addAnimal(animal);
            this.animals.put(animal.getPosition(),x);
        } else {
            this.animals.get(animal.getPosition()).addAnimal(animal);
        }
    }
    public void removeAnimal(Animal animal){
        if(this.animals.get(animal.getPosition()).removeAnimal(animal)){
            this.animals.remove(animal.getPosition());
        }
    }
    private Vector2d getRandVec(int xMin,int xMax,int yMin,int yMax){
        int x = (int)(Math.random()*(xMax-xMin+1))+xMin;
        int y = (int)(Math.random()*(yMax-yMin+1))+yMin;
        return new Vector2d(x,y);
    }
    private Vector2d getRandVec(){
        int x = (int)(Math.random()*(RIGHT.getX()));
        int y = (int)(Math.random()*(RIGHT.getY()));
        return new Vector2d(x,y);
    }
    private boolean inJungle(Vector2d pos){ return pos.precedes(this.jungleRight) && pos.follows(this.jungleLeft); }
    private Vector2d getGrassInJungle(){
        Vector2d result = getRandVec(jungleLeft.getX(),jungleRight.getX(),jungleLeft.getY(),jungleRight.getY());
        int i=0;
        while(objectAt(result)!=null && i<((jungleRight.getX()-jungleLeft.getX())*(jungleRight.getY()-jungleLeft.getY()))){
            result = getRandVec(jungleLeft.getX(),jungleRight.getX(),jungleLeft.getY(),jungleRight.getY());
            i++;
        }
        if(objectAt(result)==null) return result;
        if((jungleRight.getX()-jungleLeft.getX())*(jungleRight.getY()-jungleLeft.getY())==i){
            for(int k=jungleLeft.getY();k<=jungleRight.getY();k++){
                for(int l=jungleLeft.getX();l<=jungleRight.getX();l++){
                    if(objectAt(new Vector2d(l,k))==null){
                        return new Vector2d(l,k);
                    }
                }
            }
        }
        return null;
    }
    private Vector2d getPosOfGrassOutOfJungle(){
        Vector2d result = getRandVec();
        int i = 0;
        while (inJungle(result) && i<RIGHT.getY()*RIGHT.getY()){
            result = getRandVec();
            i++;
        }
        if(!inJungle(result)){
            return result;
        }
        if(i==RIGHT.getY()*RIGHT.getY()){
            for(int k=0;k<=RIGHT.getY();k++){
                for(int l=0;l<=RIGHT.getX();l++){
                    Vector2d v = new Vector2d(l,k);
                    if(objectAt(v)==null && !inJungle(v)){
                        return new Vector2d(l,k);
                    }
                }
            }
        }
        return null;
    }
    public Genes getGenesAtPos(Vector2d pos){
        return this.animals.get(pos).getAnimal().getGenes();
    }
    public Animal getAnAtPos(Vector2d pos){
        if(this.animals.get(pos) == null) return null;
        return this.animals.get(pos).getAnimal();
    }
}
