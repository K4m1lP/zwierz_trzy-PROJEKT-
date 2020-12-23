package DataValidation;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class StartGetData extends Application {
    @Override
    public void init(){
        System.out.println("Loading");
    }
    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../DataValidation/getData.fxml"));
        stage.setTitle("Pobierz dane");
        stage.setScene(new Scene(root));
        stage.show();
    }
    @Override
    public void stop(){
        System.out.println("Zakonczenie pracy");
    }
}
