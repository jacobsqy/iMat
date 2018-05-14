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
                ProductItem productItem = new ProductItem(product, dataHandler);
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

                        for (Node node: vBox.getChildren()) {
                            if (node instanceof Button) {
                                ((Button) node).setStyle(null);
                            }
                        }

                        ((Button) node).setStyle("-fx-background-color: black;\n" +
                                "    -fx-text-fill: white;\n" +
                                "    -fx-border-radius: 0, 0;\n" +
                                "    -fx-border-insets: 1 1 1 1, 0;");

                        String catagory = ((Button) event.getSource()).getText();

                        ProductCategory temp = null;

                        List<Product> categoryList = new ArrayList<Product>();

                        switch (catagory) {
                            case "Baljväxter":
                                categoryList.addAll(dataHandler.getProducts(ProductCategory.POD));
                                break;
                            case "Bröd":
                                categoryList.addAll(dataHandler.getProducts(ProductCategory.BREAD));
                                break;
                            case "Bär":
                                categoryList.addAll(dataHandler.getProducts(ProductCategory.BERRY));
                                break;
                            case "Frukter":
                                categoryList.addAll(dataHandler.getProducts(ProductCategory.CITRUS_FRUIT));
                                categoryList.addAll(dataHandler.getProducts(ProductCategory.EXOTIC_FRUIT));
                                categoryList.addAll(dataHandler.getProducts(ProductCategory.ROOT_VEGETABLE));
                                categoryList.addAll(dataHandler.getProducts(ProductCategory.FRUIT));
                                categoryList.addAll(dataHandler.getProducts(ProductCategory.MELONS));
                                break;
                            case "Grönsakser & Örtkryddor":
                                categoryList.addAll(dataHandler.getProducts(ProductCategory.VEGETABLE_FRUIT));
                                categoryList.addAll(dataHandler.getProducts(ProductCategory.CABBAGE));
                                categoryList.addAll(dataHandler.getProducts(ProductCategory.HERB));
                                break;
                            case "Pasta, Potatis & Ris":
                                categoryList.addAll(dataHandler.getProducts(ProductCategory.PASTA));
                                categoryList.addAll(dataHandler.getProducts(ProductCategory.POTATO_RICE));
                                break;
                            case "Drycker":
                                categoryList.addAll(dataHandler.getProducts(ProductCategory.HOT_DRINKS));
                                categoryList.addAll(dataHandler.getProducts(ProductCategory.COLD_DRINKS));
                                break;
                            case "Fisk & Kött":
                                categoryList.addAll(dataHandler.getProducts(ProductCategory.FISH));
                                categoryList.addAll(dataHandler.getProducts(ProductCategory.MEAT));
                                break;
                            case "Mejeriprodukter":
                                categoryList.addAll(dataHandler.getProducts(ProductCategory.DAIRIES));
                                break;
                            case "Mjöl, Socker, Salt":
                                categoryList.addAll(dataHandler.getProducts(ProductCategory.FLOUR_SUGAR_SALT));
                                break;
                            case "Nötter och frön":
                                categoryList.addAll(dataHandler.getProducts(ProductCategory.NUTS_AND_SEEDS));
                                break;
                            case "Sötsaker":
                                categoryList.addAll(dataHandler.getProducts(ProductCategory.SWEET));
                                break;
                        }


                        productListFlowPane.getChildren().clear();

                        // example
                        for (Product product : categoryList) {

                            ProductItem productItem = new ProductItem(product, dataHandler);
                            productListFlowPane.getChildren().add(productItem);
                        }
                    }
                });
            }
        }
    }



}
