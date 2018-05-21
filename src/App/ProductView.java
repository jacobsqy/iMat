package App;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static App.BackendController.backend;
import static App.MainWindow.updatextSearchText;

public class ProductView {

    private MainWindow parentController;

    @FXML private SplitPane defautView;

    @FXML private FlowPane productListFlowPane;
    @FXML private VBox vBox;
    @FXML private Button favButton;

    @FXML private AnchorPane lightBox;
    @FXML private AnchorPane moreInfo;
    @FXML private AnchorPane firstTimeView;
    @FXML private AnchorPane confirmationAnchorPane;

    public static List<Product> productList = new ArrayList<Product>();
    public static Map<String, ProductItem> productMap = new HashMap<String, ProductItem>();

    public static ArrayList<Product> favoriteList = new ArrayList<Product>();
    public static ObservableList observableList = FXCollections.observableList(favoriteList);

    public void initialize() {

        // laddar all produkter till map
        for (Product product : backend.getProducts()) {
            ProductItem productItem = new ProductItem(product);
            productItem.setParentView(this);
            productMap.put(product.getName(), productItem);
            if (backend.isFavorite(product)) {
                favoriteList.add(product);
            }
        }

        // Applicationen startas med favoriter som standart
        favButton.setStyle("-fx-background-color: black;\n" +
                "    -fx-text-fill: white;\n" +
                "    -fx-border-radius: 0, 0;\n" +
                "    -fx-border-insets: 1 1 1 1, 0;");

        updateFavoriteList();

        observableList.addListener(new ListChangeListener() {
            @Override
            public void onChanged(Change c) {
                updateFavoriteList();
            }
        });

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

                        String btn = ((Button) node).getText();
                        productList = BackendController.getProductByCategory(btn);
                        updateList();
                    }
                });
            }
        }

        moreInfo.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                mouseTrap(mouseEvent);
            }
        });
        firstTimeView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                mouseTrap(mouseEvent);
            }
        });
        confirmationAnchorPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                mouseTrap(mouseEvent);
            }
        });

        deactivateLightBox();
        if (BackendController.isFirstRun()) {
            changeToFirstTimeView();
        } else {
            changeToDefaultView();
        }
    }

    /**
     * uppdaterar produkt vyn beroende på varor som ligger i map
     */
    public void updateList() {
        productListFlowPane.getChildren().clear();
        for (Product product : productList) {
            productListFlowPane.getChildren().add(productMap.get(product.getName()));
            if(!backend.isFavorite(product)) productMap.get(product.getName()).setImageToUnFav();
        }
    }

    /**
     * uppdaterar produkt vyn via sök
     * @param str tar sök text som parameter
     */
    public void updateSearch(String str) {
        productListFlowPane.getChildren().clear();

        for (Product product : backend.findProducts(str)) {
            productListFlowPane.getChildren().add(productMap.get(product.getName()));
            if(!backend.isFavorite(product)) productMap.get(product.getName()).setImageToUnFav();
        }
    }

    /**
     * tar bort och updaterar favorit item från vyn om favorite knapp stylat med svart bakgrund
     */
    private void updateFavoriteList() {
        if (favButton.getStyle().contains("black")) {
            productListFlowPane.getChildren().clear();
            for (Product product : favoriteList) {
                ProductItem productItem = new ProductItem(product);
                productItem.setParentView(this);
                productListFlowPane.getChildren().add(productItem);
            }
        }
    }

    public void setParentController(MainWindow parentController) {
        this.parentController = parentController;
    }

    public void updateInfo() {
        parentController.updateInfo();
    }

    public MainWindow getParentController() {
        return parentController;
    }

    private void deactivateLightBox() {
        lightBox.toBack();
        moreInfo.toBack();
        firstTimeView.toBack();
        confirmationAnchorPane.toBack();
        //updatextSearchText.get(0).setDisable(false);
    }

    public void changeToMoreInfo() {
        lightBox.toFront();
        moreInfo.toFront();
    }

    public void changeToFirstTimeView() {
        lightBox.toFront();
        firstTimeView.toFront();
    }

    public void changeToDefaultView() {
        deactivateLightBox();
        defautView.toFront();
    }

    public void changeToConfirmationView() {
        lightBox.toFront();
        confirmationAnchorPane.toFront();
    }

    private void mouseTrap(Event event){
        event.consume();
    }
}
