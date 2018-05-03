package App;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ProductCategory;

import java.net.URL;
import java.util.*;

public class AppController implements Initializable {
    @FXML private TextField txtSearch;
    @FXML private ChoiceBox choiceBox;
    @FXML private FlowPane productListFlowPane;
    @FXML private VBox vBox;

    // need of a new class which can handle all of searching data etc...
    private IMatDataHandler dataHandler = IMatDataHandler.getInstance();
    private ArrayList<String> products = new ProductBackedController().getProductNames();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        txtSearch.textProperty().addListener(((observable, oldValue, newValue) -> {

            // example
            productListFlowPane.getChildren().clear();

            for (Product product : dataHandler.findProducts(newValue)) {
                ProductItem productItem = new ProductItem(product);
                productListFlowPane.getChildren().add(productItem);
            }

            choiceBox.getItems().clear();
            if(!newValue.isEmpty()) {
                for(String item:products) {
                    if(item.toLowerCase().startsWith(newValue.toLowerCase()))
                        choiceBox.getItems().add(item);
                }
                choiceBox.show();
            }
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

                        String catagory = ((Button) event.getSource()).getText();
                        System.out.println(catagory);

                        ProductCategory temp = null;
                        switch (catagory) {
                            case "Baljväxter": temp = ProductCategory.POD; break;
                            case "Bröd": temp = ProductCategory.BREAD; break;
                            case "Bär": temp = ProductCategory.BERRY; break;
                            case "Citrosfrukter": temp = ProductCategory.CITRUS_FRUIT; break;
                            case "Drycker varma": temp = ProductCategory.HOT_DRINKS; break;
                            case "Drycker kalla": temp = ProductCategory.COLD_DRINKS; break;
                            case "Exotiska frukter": temp = ProductCategory.EXOTIC_FRUIT; break;
                            case "Fisk": temp = ProductCategory.FISH; break;
                            case "Grönsaksfrukter": temp = ProductCategory.VEGETABLE_FRUIT; break;
                            case "Kål": temp = ProductCategory.CABBAGE; break;
                            case "Kött": temp = ProductCategory.MEAT; break;
                            case "Mejeriprodukter": temp = ProductCategory.DAIRIES; break;
                            case "Meloner": temp = ProductCategory.MELONS; break;
                            case "Mjöl, Socker, Salt": temp = ProductCategory.FLOUR_SUGAR_SALT; break;
                            case "Nötter och frön": temp = ProductCategory.NUTS_AND_SEEDS; break;
                            case "Pasta": temp = ProductCategory.PASTA; break;
                            case "Potatis, ris": temp = ProductCategory.POTATO_RICE; break;
                            case "Rotfrukter": temp = ProductCategory.ROOT_VEGETABLE; break;
                            case "Stenfrukter": temp = ProductCategory.FRUIT; break;
                            case "Sötsaker": temp = ProductCategory.SWEET; break;
                            case "Örtkryddor": temp = ProductCategory.HERB; break;
                        }


                        productListFlowPane.getChildren().clear();

                        // example
                        for (Product product : dataHandler.getProducts(temp)) {

                            ProductItem productItem = new ProductItem(product);
                            productListFlowPane.getChildren().add(productItem);
                        }
                    }
                });
            }
        }

    }

}
