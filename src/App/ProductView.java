package App;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static App.BackendController.backend;

public class ProductView {

    @FXML private FlowPane productListFlowPane;
    @FXML private VBox vBox;
    @FXML private Button favButton;

    public static List<Product> productItems = new ArrayList<Product>();
    public static Map<String, ProductItem> productMap = new HashMap<String, ProductItem>();

    @FXML
    public void initialize() {

        // Applicationen startas med favoriter som standart
        favButton.setStyle("-fx-background-color: black;\n" +
                "    -fx-text-fill: white;\n" +
                "    -fx-border-radius: 0, 0;\n" +
                "    -fx-border-insets: 1 1 1 1, 0;");
        productListFlowPane.getChildren().clear();
        for (Product product : backend.favorites()) {
            ProductItem productItem = new ProductItem(product);
            productListFlowPane.getChildren().add(productItem);
        }

        // laddar all produkter till map
        for (Product product : backend.getProducts()) {
            ProductItem productItem = new ProductItem(product);
            productMap.put(product.getName(), productItem);
        }

        // hämtar alla knappar och sätter listener, så att valda produkter visas på vyn
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

    /**
     * uppdaterar produkt vyn beroende på varor som ligger i map
     */
    public void updateList() {
        productListFlowPane.getChildren().clear();
        for (Product product : productItems) {
            productListFlowPane.getChildren().add(productMap.get(product.getName()));
        }
    }
}
