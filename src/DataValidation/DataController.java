package DataValidation;
import Run.SimulationEngine;
import adds.Vector2d;
import com.google.gson.Gson;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import map.IWorldMap;
import map.WorldMap;
import Vis.Simulator;
import objects.Animal;
import java.io.*;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
public class DataController {
    private IWorldMap map = null;
    private VBox root = new VBox();
    private SimulationEngine engine = null;
    private Simulator sim = null;
    private Stage stage = new Stage();
    private Group group = new Group();
    private double sizeOfOneSquare;
    private double clickedX = -1.0;
    private double clickedY = -1.0;
    @FXML
    public AnchorPane statistic = null;
    @FXML
    public TextField heightField = null;
    @FXML
    public TextField widthField = null;
    @FXML
    public TextField proportionField = null;
    @FXML
    public TextField grassEnergyField = null;
    @FXML
    public TextField costOfDayField = null;
    @FXML
    public TextField howManyAnimalsField = null;
    @FXML
    public TextField startEnergyField = null;
    @FXML
    public TextField howManyGrassesDailyField = null;
    @FXML
    public Slider delayField = null;
    @FXML
    public CheckBox doesJsonCheck = null;
    @FXML
    public CheckBox doesTwoMapsCheck = null;
    @FXML
    public TextField srcOfJsonFile = null;
    private ParserFields setOfData;
    @FXML
    public Label numOfAnimals = null;
    @FXML
    public Label energyAVGOfAliveAnimals = null;
    @FXML
    public Label AVGOfDeadAnimal = null;
    @FXML
    public Label AVGOfChildrenNumber = null;
    @FXML
    public Label mostOftenGenes = null;
    @FXML
    public Label numOfGrass = null;
    private Input data;
    private AnchorPane myView;
    public void startChecking() throws InterruptedException {
        try{
            ifDownloaded();
        } catch (Exception e){
            e.printStackTrace();
            long l = 1;
            TimeUnit.SECONDS.sleep(l);
            System.exit(1);
        }
        setOfData = new ParserFields();
        try{
            getData();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    private void getData() throws Exception {
        if(!doesJsonCheck.isSelected() && !doesTwoMapsCheck.isSelected()){
            try{
                setOfData.setMapHeight(Integer.parseInt(heightField.getText()));
                setOfData.setMapWidth(Integer.parseInt(widthField.getText()));
                setOfData.setProportion(Integer.parseInt(proportionField.getText()));
                setOfData.setGrassEnergy(Integer.parseInt(grassEnergyField.getText()));
                setOfData.setCostOfDay(Integer.parseInt(costOfDayField.getText()));
                setOfData.setNumOfSpawnedAnimals(Integer.parseInt(howManyAnimalsField.getText()));
                setOfData.setAnimalsStartEnergy(Integer.parseInt(startEnergyField.getText()));
                setOfData.setNumOfDailyGrass(Integer.parseInt(howManyGrassesDailyField.getText()));
                setOfData.setDelay(delayField.getValue());
                this.sizeOfOneSquare = 524.0/Math.max(setOfData.getMapWidth(),setOfData.getMapHeight());
                startVisualization();
            }catch (Exception e){
                e.printStackTrace();
            }
        } else if(doesJsonCheck.isSelected() && !doesTwoMapsCheck.isSelected()){
            if(this.srcOfJsonFile.getText().length()==0) System.out.println("Podaj plik");
            try {
                Gson gson = new Gson();
                try (Reader reader = new FileReader(this.srcOfJsonFile.getText())) {
                    this.data = gson.fromJson(reader, Input.class);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(1);
                }
                setOfData.setMapHeight(data.mapHeight);
                setOfData.setMapWidth(data.mapWidth);
                setOfData.setProportion(data.proportion);
                setOfData.setGrassEnergy(data.grassEnergy);
                setOfData.setCostOfDay(data.costOfDay);
                setOfData.setNumOfSpawnedAnimals(data.numOfSpawnedAnimals);
                setOfData.setAnimalsStartEnergy(data.animalsStartEnergy);
                setOfData.setNumOfDailyGrass(data.numOfDailyGrass);
                setOfData.setDelay(data.delay);
                this.sizeOfOneSquare = 524.0/Math.max(setOfData.getMapWidth(),setOfData.getMapHeight());
                startVisualization();
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        } else if(doesTwoMapsCheck.isSelected()){
            Stage g = new Stage();
            g.setTitle("OPCJA 2 MAP JESZCZE NIE DZIALA");
            g.setWidth(600);
            g.setHeight(100);
            g.show();
        }
    }
    private void ifDownloaded() throws Exception{
        IllegalAccessError ex = new IllegalAccessError("Błąd ładowania ...");
        if(doesJsonCheck == null) throw ex;
        if(!doesJsonCheck.isSelected()){
            if (heightField == null) throw ex;
            if (widthField == null) throw ex;
            if (proportionField == null) throw ex;
            if (grassEnergyField == null) throw ex;
            if (costOfDayField == null) throw ex;
            if (howManyAnimalsField == null) throw ex;
            if (startEnergyField == null) throw ex;
            if (howManyGrassesDailyField == null) throw ex;
        }else if(doesJsonCheck.isSelected()){
            if (srcOfJsonFile == null) throw ex;
        }
    }
    private void startVisualization() throws IOException {
        this.map = new WorldMap(setOfData);
        this.engine = new SimulationEngine(this.map,setOfData);
        stage.setTitle("Wizualizacja");
        stage.setFullScreen(true);
        stage.setOnCloseRequest(this::cons);
        myView = FXMLLoader.load(getClass().getResource("../Vis/sample.fxml"));
        this.sim = new Simulator(this,setOfData.getDelay());
        root.getChildren().addAll(group);
        root.getChildren().addAll(myView);
        stage.setScene(new Scene(root));
        stage.show();

        this.sim.start();
        this.numOfAnimals = (Label) myView.lookup("#numOfAnimals");
        this.energyAVGOfAliveAnimals = (Label) myView.lookup("#energyAVGOfAliveAnimals");
        this.AVGOfDeadAnimal = (Label) myView.lookup("#AVGOfDeadAnimal");
        this.AVGOfChildrenNumber = (Label) myView.lookup("#AVGOfChildrenNumber");
        this.mostOftenGenes = (Label) myView.lookup("#mostOftenGenes");
        this.numOfGrass = (Label) myView.lookup("#numOfGrass");
        myView.lookup("#butStop").setOnMouseClicked(this::stopThis);
        myView.lookup("#butStart").setOnMouseClicked(this::continueThis);
        myView.lookup("#saveToFile").setOnMouseClicked(this::saveToFile);
        myView.lookup("#count").setOnMouseClicked(this::count);
    }
    private void cons(Event e){
        root = new VBox();
        engine = null;
        stage = new Stage();
        group = new Group();
    }
    public void nextDay(){
        if(engine != null && !engine.makeDay()) sim.stop();
        if(engine != null && numOfAnimals != null){
            this.numOfAnimals.setText("" + engine.getNumOfAliveAnimal());
        }
        if(engine != null && energyAVGOfAliveAnimals != null && !Double.isNaN(engine.getEnergyAVGOfAliveAnimals())){
            this.energyAVGOfAliveAnimals.setText("" + engine.getEnergyAVGOfAliveAnimals());
        }
        if(engine != null && AVGOfDeadAnimal != null && !Double.isNaN(engine.getAVGOfDeadAnimal())){
            this.AVGOfDeadAnimal.setText("" + engine.getAVGOfDeadAnimal());
        }
        if(engine != null && AVGOfChildrenNumber != null && !Double.isNaN(engine.getAVGOfChildrenNumber())){
            this.AVGOfChildrenNumber.setText("" + engine.getAVGOfChildrenNumber());
        }
        if(engine != null && mostOftenGenes != null && engine.mostOftenGenes() != null){
            this.mostOftenGenes.setText("" + engine.mostOftenGenes());
        }
        if(engine != null && numOfGrass != null){
            this.numOfGrass.setText("" + engine.getNumOfGrass());
        }
    }
    public void createGrille(){
        group.getChildren().clear();
        LinkedList<Animal> animalsPos = this.map.getAnimalsPos();
        if(animalsPos==null || animalsPos.size()==0) {
            sim.stop();
            return;
        }
        LinkedList<Vector2d> grassPos = this.map.getGrassPos();
        Canvas canvas = new Canvas(524, 524);
        GraphicsContext graphics_context = canvas.getGraphicsContext2D();
        int x1,y1;
        for (Animal a : animalsPos) {
            x1 = a.getPosition().getX();
            y1 = a.getPosition().getY();
            if(a.getEnergy() > a.getStartEnergy()){
                graphics_context.setFill(Color.RED);
            } else if(a.canSex()){
                graphics_context.setFill(Color.BLUE);
            } else {
                graphics_context.setFill(Color.AQUA);
            }
            if(a.getGenes().equals(engine.getMostOftenGenes())){
                graphics_context.setFill(Color.BLACK);
            }
            graphics_context.fillOval(x1*sizeOfOneSquare,y1* sizeOfOneSquare, sizeOfOneSquare,sizeOfOneSquare);
        }
        for (Vector2d vector2d : grassPos) {
            x1 = vector2d.getX();
            y1 = vector2d.getY();
            graphics_context.setFill(Color.GREEN);
            graphics_context.fillRect(x1*sizeOfOneSquare,y1* sizeOfOneSquare, sizeOfOneSquare,sizeOfOneSquare);
        }
        canvas.setOnMouseClicked(event -> {
            this.clickedX = event.getX();
            this.clickedY = event.getY();
            changeProperties();
        });
        group.getChildren().addAll(canvas);
    }
    public void continueThis(javafx.scene.input.MouseEvent e){
        this.sim.start();
        myView.lookup("#paneID").setOpacity(0);

    }
    public void stopThis(javafx.scene.input.MouseEvent e){
        repairStat();
        this.sim.stop();
        myView.lookup("#paneID").setOpacity(1);
    }
    private void changeProperties(){
        if(myView.lookup("#paneID").getOpacity() == 1){
            int xV = (int)(clickedX/sizeOfOneSquare);
            int yV = (int)(clickedY/sizeOfOneSquare);
            if(map.getGenesAtPos(new Vector2d(xV,yV)) == null) return;
            ((Label)myView.lookup("#chosenGenes")).setText("" + map.getGenesAtPos(new Vector2d(xV,yV)));
        }
    }
    private void repairStat(){
        ((Label)myView.lookup("#chosenGenes")).setText("[]");
    }
    private void saveToFile(javafx.scene.input.MouseEvent e){
        try {
            FileWriter myWriter = new FileWriter("data.txt");
            myWriter.write("Liczba żyjących zwierząt: " + engine.getNumOfAliveAnimal() +
                               " Srednia energia: " + engine.getEnergyAVGOfAliveAnimals() +
                               " Srednia dlugosc zycia: " + engine.getAVGOfDeadAnimal() +
                               " Srednia liczba dzieci: " + engine.getAVGOfChildrenNumber() +
                               " Dominujacy genotyp: " + engine.getMostOftenGenes().toString() +
                    " Ile trawy: " + engine.getNumOfGrass());
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException ex) {
            System.out.println("An error occurred.");
            ex.printStackTrace();
        }

    }
    private void count(javafx.scene.input.MouseEvent e){
        int xV = (int)(clickedX/sizeOfOneSquare);
        int yV = (int)(clickedY/sizeOfOneSquare);
        if(map.getAnAtPos(new Vector2d(xV,yV)) == null) return;
        if(((TextArea) myView.lookup("#nEpok")).getText().equals("n")) return;
        ((Label)myView.lookup("#childrenPastN")).setText("" + map.getAnAtPos(new Vector2d(xV,yV)).howManyChildOfChild(Integer.parseInt(((TextArea)myView.lookup("#nEpok")).getText()),new LinkedList<>()));
    }
}
