package App;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sun.applet.Main;

public class ImatAppMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("ProductView.fxml"));
        primaryStage.setTitle("IMat - Handla Enkelt & Snabbt");
        Scene scene = new Scene(root, 1370, 850);
        scene.getStylesheets().add(ProductView.class.getResource("StyleSheet.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
