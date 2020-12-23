package Vis;
import DataValidation.DataController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
public class Simulator {
    private final Timeline timeline;
    private final DataController controller;
    public Simulator(DataController controller,double delay){
        this.controller = controller;
        this.timeline = new Timeline(new KeyFrame(Duration.millis((int)(delay*1000)), this::doStep));
        this.timeline.setCycleCount(Timeline.INDEFINITE);
    }
    private void doStep(javafx.event.ActionEvent actionEvent) {
        this.controller.nextDay();
        this.controller.createGrille();
    }
    public void start(){
        this.timeline.play();
    }
    public void stop(){
        this.timeline.stop();
    }
}
