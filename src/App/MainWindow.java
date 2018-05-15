package App;


import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.util.Optional;

import static App.BackendController.backend;
import static App.ProductView.productItems;

public class MainWindow {
    @FXML private TextField txtSearch;
    @FXML private ChoiceBox choiceBox;
    @FXML private Button shoppingCardButton;
    @FXML private Button continueToShopButton;
    @FXML Label amountOfProducts, totalPrice;

    @FXML private AnchorPane contentPane;
    @FXML private AnchorPane productView;
    @FXML private ProductView productViewController;
    @FXML private AnchorPane shoppingView;
    @FXML private ShoppingCartView shoppingViewController;

    public void initialize() {

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
            productViewController.updateList();
        }));
        choiceBox.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            if (choiceBox.getItems().size() > 0) {
                txtSearch.setText(newValue.toString());
                txtSearch.end();
            }

        }));

        shoppingCardButton.setVisible(true);
        continueToShopButton.setVisible(false);

        shoppingCardButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                toggleView();
                continueToShopButton.setVisible(true);
                shoppingCardButton.setVisible(false);
                txtSearch.setVisible(false);
            }
        });
        continueToShopButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                toggleView();
                shoppingCardButton.setVisible(true);
                continueToShopButton.setVisible(false);
                txtSearch.setVisible(true);
            }
        });
        showView(productView);

    }

    private void showView(Node view) {
        contentPane.getChildren().setAll(view);
    }

    private Optional<Node> getActiveView() {
        ObservableList<Node> children = contentPane.getChildren();
        return children.isEmpty() ? Optional.empty() : Optional.of(children.get(0));
    }

    private void toggleView() {
        getActiveView().ifPresent(view -> {
            if (view.equals(productView)) showView(shoppingView);
            if (view.equals(shoppingView)) showView(productView);
        });
    }
}
