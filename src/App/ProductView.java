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

    public static List<Product> productItems = new ArrayList<Product>();
    public static Map<String, ProductItem> productMap = new HashMap<String, ProductItem>();

    @FXML
    public void initialize() {
        System.out.println("ProductView.initialize()");

        for (Product product : backend.getProducts()) {
            ProductItem productItem = new ProductItem(product);
            productMap.put(product.getName(), productItem);
        }
/*

*/
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

    public void updateList() {
        productListFlowPane.getChildren().clear();
        for (Product product : productItems) {
            productListFlowPane.getChildren().add(productMap.get(product.getName()));
        }
    }

    @FXML
    public void test(){
        System.out.println("test");
    }
}
