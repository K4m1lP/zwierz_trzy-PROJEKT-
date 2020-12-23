package DataValidation;


public class Input {
        public int mapWidth;
        public int mapHeight;
        public int proportion;
        public int grassEnergy;
        public int costOfDay;
        public int animalsStartEnergy;
        public int numOfSpawnedAnimals;
        public double delay;
        public int numOfDailyGrass;


    public Input(   int mapWidth,
            int mapHeight,
            int proportion,
            int grassEnergy,
            int costOfDay,
            int animalsStartEnergy,
            int numOfSpawnedAnimals,
            double delay,
            int numOfDailyGrass
    ) {
        this.animalsStartEnergy = animalsStartEnergy;
        this.costOfDay = costOfDay;
        this.numOfDailyGrass = numOfDailyGrass;
        this.delay = delay;
        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;
        this.proportion = proportion;
        this.grassEnergy = grassEnergy;
        this.numOfSpawnedAnimals = numOfSpawnedAnimals;
    }
}
