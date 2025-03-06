package Game;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;

public class Loop extends Application{
    @FXML
    private GridPane grid;
    @FXML
    private Label label;

    public void start(Stage primaryStage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("../data/map.fxml"));

        primaryStage.setTitle("FarmMyFarm");

        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }

    public static void main(String[] args) {
        boolean loop = true;
        launch(args);

        while (loop) {

        }
    }
}
