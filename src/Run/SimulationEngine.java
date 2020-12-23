package Run;
import DataValidation.ParserFields;
import adds.Vector2d;
import map.IWorldMap;
import objects.Animal;
import objects.Genes;

import java.util.LinkedList;
import java.util.List;
public class SimulationEngine {
    private final IWorldMap map;
    private final List<Animal> animals = new LinkedList<>();
    private final ParserFields data;
    private int sumOfAgesOfDeadAnimals;
    private int numberOfDeadAnimals;
    private Genes mostGenes;


    public SimulationEngine(IWorldMap map, ParserFields data){
        this.map = map;
        this.sumOfAgesOfDeadAnimals = 0;
        this.numberOfDeadAnimals = 0;
        this.data = data;
        this.mostGenes = mostOftenGenesGen();
        placeAdamEve();
    }
    public boolean makeDay(){
        if(!removeDead()) return false;

        animals.forEach(Animal::move);

        feedAnimals();

        makeLove();

        map.addGrass(data.getNumOfDailyGrass());

        this.animals.forEach(animal -> animal.decrementEnergy(data.getCostOfDay()));

        animals.forEach(Animal::addDay);
        this.mostGenes = mostOftenGenesGen();
        return true;
    }
    private void placeAdamEve(){
        for(int i=0;i<data.getNumOfSpawnedAnimals();i++){
            int x = (int) (Math.random() * data.getMapWidth());
            int y = (int) (Math.random() * data.getMapHeight());
            boolean flag = false;
            Vector2d v = new Vector2d(x,y);
            for(Animal an: animals){
                if (an.getPosition().equals(v)) {
                    flag = true;
                    break;
                }
            }
            while(flag){
                x = (int) (Math.random() * data.getMapWidth());
                y = (int) (Math.random() * data.getMapHeight());
                v = new Vector2d(x,y);
                flag = false;
                for(Animal an: animals){
                    if (an.getPosition().equals(v)) {
                        flag = true;
                        break;
                    }
                }
            }
            Animal newAnimal = new Animal(map,v,data.getAnimalsStartEnergy());
            this.animals.add(newAnimal);
            map.place(newAnimal);
        }
    }
    private boolean removeDead(){
        List<Animal> toDel = new LinkedList<>();
        for(Animal animal: animals){ if(animal.isDead()) toDel.add(animal); }
        for(Animal animal: toDel){
            this.sumOfAgesOfDeadAnimals += (animal.getAge()-1);
            this.numberOfDeadAnimals++;
            map.removeAnimal(animal);
            this.animals.remove(animal);
        }
        toDel.clear();
        return animals.size() != 0;
    }
    private void feedAnimals(){
        List<List<Animal>> hungry = map.feedAnimal();
        for(List<Animal> hungryAnimals : hungry){
            if(hungryAnimals.size() > 0){
                for(Animal hungryAnimal: hungryAnimals) hungryAnimal.addEnergy(data.getGrassEnergy()/hungryAnimals.size());
            }
        }
    }
    private void makeLove(){
        List<List<Animal>> couples = map.makeLove();
        for(List<Animal> couple : couples){
            if(couple.size()  == 2) this.animals.add(couple.get(0).copulation(couple.get(1)));
        }
    }
    public int getNumOfAliveAnimal(){ return this.animals.size(); }
    public int getNumOfGrass(){ return this.map.getGrassPos().size(); }
    public double getEnergyAVGOfAliveAnimals(){ return (double)getSumOfEnergy()/getNumOfAliveAnimal(); }
    private int getSumOfEnergy(){
        return animals.stream().mapToInt(Animal::getEnergy).sum();
    }
    public double getAVGOfDeadAnimal(){ return (double)sumOfAgesOfDeadAnimals/numberOfDeadAnimals; }
    public double getAVGOfChildrenNumber() {return (double)sumOfAllChildren()/(2*getNumOfAliveAnimal());}
    private int sumOfAllChildren(){ return animals.stream().mapToInt(Animal::getHowManyChildren).sum(); }
    public String mostOftenGenes(){
        int maks=0;
        Genes result = null;
        for(Animal an1: animals){
            int tmp = 0;
            for(Animal an2: animals){
                if(an1.getGenes().equals(an2.getGenes())) tmp++;
            }
            if(tmp>maks) {
                maks = tmp;
                result = an1.getGenes();
            }
        }
        if(result == null){
            return null;
        } else {
            return result.toString();
        }
    }
    public Genes mostOftenGenesGen(){
        int maks=0;
        Genes result = null;
        for(Animal an1: animals){
            int tmp = 0;
            for(Animal an2: animals){
                if(an1.getGenes().equals(an2.getGenes())) tmp++;
            }
            if(tmp>maks) {
                maks = tmp;
                result = an1.getGenes();
            }
        }
        return result;
    }
    public Genes getMostOftenGenes(){
        return this.mostGenes;
    }
}

