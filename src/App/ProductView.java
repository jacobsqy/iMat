package App;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.Product;

import java.net.URL;
import java.util.*;

import static App.BackendController.backend;

public class ProductView implements Initializable {
    @FXML private ChoiceBox choiceBox;
    @FXML private FlowPane productListFlowPane;
    @FXML private VBox vBox;
    private TextField txtSearch;

    private List<Product> productItems = new ArrayList<Product>();
    private Map<String, ProductItem> productMap = new HashMap<String, ProductItem>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("ProductView.initialize()");
        Platform.runLater(() ->txtSearch.requestFocus());

        for (Product product : backend.getProducts()) {
            ProductItem productItem = new ProductItem(product);
            productMap.put(product.getName(), productItem);
        }

        txtSearch.textProperty().addListener(((observable, oldValue, newValue) -> {

            productItems.clear();

            choiceBox.getItems().clear();
            if(!newValue.isEmpty()) {
                for(String item:BackendController.getProductNames()) {
                    if(item.toLowerCase().startsWith(newValue.toLowerCase())) {
                        choiceBox.getItems().add(item);
                        productItems.addAll(backend.findProducts(item));
                    }
                }
                choiceBox.show();
            }
            updateList();
        }));
        choiceBox.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            if (choiceBox.getItems().size() > 0) {
                txtSearch.setText(newValue.toString());
                txtSearch.end();
            }

        }));

        // will work on later, probably in another class, (this is for demonstration)
        for (Node node: vBox.getChildren()) {
            if (node instanceof Button) {
                ((Button) node).setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        for (Node node: vBox.getChildren()) {
                            if (node instanceof Button) {
                                ((Button) node).setStyle(null);
                            }
                        }

                        ((Button) node).setStyle("-fx-background-color: black;\n" +
                                "    -fx-text-fill: white;\n" +
                                "    -fx-border-radius: 0, 0;\n" +
                                "    -fx-border-insets: 1 1 1 1, 0;");

                        productItems = BackendController.getProductByCategory(((Button) event.getSource()).getText());
                        updateList();
                    }
                });
            }
        }
    }

    private void updateList() {
        productListFlowPane.getChildren().clear();
        for (Product product : productItems) {
            productListFlowPane.getChildren().add(productMap.get(product.getName()));
        }
    }
}
