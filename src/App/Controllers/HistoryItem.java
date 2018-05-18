package App.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

import static App.Controllers.HistoryView.historyViews;

public class HistoryItem extends AnchorPane {

    @FXML private Label day;
    @FXML private Label month;
    @FXML private Label year;
    @FXML private Label product;
    @FXML private Label price;
    @FXML private Button showProduct;
    @FXML private Button addProduct;

    public HistoryItem(){

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../HistoryViews/HistoryItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        /* TODO:
        * En sparade köplista ska hämtas från HistoryProduktView
        * Så att varorna ska placeras i kontenter
        */

        day.setText("21");
        month.setText("Oct");
        year.setText("2017");
        product.setText("3 varor");
        price.setText("62 kr");
    }

    @FXML public void showButtonPressed() {
        historyViews.get(0).getChildren().clear();
        historyViews.get(0).getChildren().setAll(historyViews.get(2));
    }
}
