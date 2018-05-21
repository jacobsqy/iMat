package App;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static App.BackendController.backend;
import static App.MainWindow.updatextSearchText;

public class ImatAppMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        backend.resetFirstRun();
        Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        primaryStage.setTitle("IMat - Handla Enkelt & Snabbt");
        Scene scene = new Scene(root, 1370, 890);
        scene.getStylesheets().add(MainWindow.class.getResource("StyleSheet.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
        updatextSearchText.get(0).setDisable(true);
    }

    /**
     * sparar text filer innan programmet stängs
     * @throws Exception
     */
    @Override
    public void stop() throws Exception {
        backend.shutDown();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
