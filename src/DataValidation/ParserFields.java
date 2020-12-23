package DataValidation;

public class ParserFields {
    private int mapWidth = 20;
    public int getMapWidth() {
        return mapWidth;
    }
    public void setMapWidth(int mapWidth) throws Exception {
        if(mapWidth > 0 && mapWidth <= 1000) this.mapWidth = mapWidth;
        else throw new IllegalArgumentException("Nieprawidlowa szerokość mapy (0,1000>");
    }
    private int mapHeight = 20;
    public int getMapHeight() { return mapHeight; }
    public void setMapHeight(int mapHeight) throws Exception {
        if(mapHeight > 0 && mapHeight <= 1000) this.mapHeight = mapHeight;
        else throw new IllegalArgumentException("Nieprawidlowa wysokość mapy (0,1000>");
    }
    private int proportion = 25;
    public int getProportion() { return proportion; }
    public void setProportion(int proportion) {
        if(proportion > 0 && proportion < 100) this.proportion = proportion;
        else throw new IllegalArgumentException("Nieprawidlowa proporcja dżungli (0,100)");
    }
    private int grassEnergy = 2;
    public int getGrassEnergy() { return grassEnergy; }
    public void setGrassEnergy(int grassEnergy) {
        if(grassEnergy > 0 && grassEnergy <= 10000) this.grassEnergy = grassEnergy;
        else throw new IllegalArgumentException("Nieprawidlowa energia z trawy (0,1000>");
    }
    private int costOfDay = 1;
    public int getCostOfDay() { return costOfDay; }
    public void setCostOfDay(int costOfDay) {
        if(costOfDay > 0 && costOfDay <= 400) this.costOfDay = costOfDay;
        else throw new IllegalArgumentException("Nieprawidlowy koszt dnia (0,400>");
    }
    private int animalsStartEnergy = 5;
    public int getAnimalsStartEnergy() { return animalsStartEnergy; }
    public void setAnimalsStartEnergy(int animalsStartEnergy) {
        if(animalsStartEnergy > 0 && animalsStartEnergy <= 1000) this.animalsStartEnergy = animalsStartEnergy;
        else throw new IllegalArgumentException("Nieprawidlowa energia początkowa (0,1000>");
    }
    private int numOfSpawnedAnimals = 2;
    public int getNumOfSpawnedAnimals() { return numOfSpawnedAnimals; }
    public void setNumOfSpawnedAnimals(int numOfSpawnedAnimals) {
        if(numOfSpawnedAnimals > 0 && numOfSpawnedAnimals <= 10000) this.numOfSpawnedAnimals = numOfSpawnedAnimals;
        else throw new IllegalArgumentException("Nieprawidlowa liczba zwierzat (0,1000>");
    }
    private double delay = 0.5;
    public double getDelay() { return delay; }
    public void setDelay(double delay) {
        if(delay >= 0.1 && delay <= 2) this.delay = delay;
        else throw new IllegalArgumentException("Nieprawidlowe opoznienie <0.1,2>");
    }

    private int numOfDailyGrass = 2;
    public int getNumOfDailyGrass() { return numOfDailyGrass; }
    public void setNumOfDailyGrass(int numOfDailyGrass) {
        if(numOfDailyGrass > 0 && numOfDailyGrass <= 100) this.numOfDailyGrass = numOfDailyGrass;
        else throw new IllegalArgumentException("Nieprawidlowa ilosc rosnacej trawy (0,100>");
    }
}
