package App;


import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import static App.BackendController.backend;
import static App.ProductView.productList;

public class MainWindow {
    @FXML private TextField txtSearch;
    @FXML private ChoiceBox choiceBox;
    @FXML private Button shoppingCardButton;
    @FXML private Button helpButton;
    @FXML private Button continueToShopButton;
    @FXML Label amountOfProducts, totalPrice;

    @FXML private AnchorPane contentPane;
    @FXML private AnchorPane productView;
    @FXML private ProductView productViewController;
    @FXML private AnchorPane shoppingView;
    @FXML private AnchorPane helpView;
    @FXML private ShoppingCartView shoppingViewController;

    public void initialize() {

        Platform.runLater(() -> txtSearch.requestFocus());

        // autocomplete sök, det som skrivs i textfield visas på en dropdown
        txtSearch.textProperty().addListener(((observable, oldValue, newValue) -> {

            choiceBox.getItems().clear();
            if(!newValue.isEmpty()) {
                for(String item:BackendController.getProductNames()) {
                    if(item.toLowerCase().startsWith(newValue.toLowerCase())) {
                        choiceBox.getItems().add(item);
                        productList.addAll(backend.findProducts(item));
                    }
                }
                choiceBox.show();
            }
            System.out.println(productList);
            productViewController.updateList();
        }));
        choiceBox.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            if (choiceBox.getItems().size() > 0) {
                txtSearch.setText(newValue.toString());
                txtSearch.end();
            }

        }));

        // knappen till varukorg/försätt handla visas eller döljs
        shoppingCardButton.setVisible(true);
        continueToShopButton.setVisible(false);

        shoppingCardButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                contentPane.getChildren().setAll(shoppingView);
                continueToShopButton.setVisible(true);
                shoppingCardButton.setVisible(false);
                txtSearch.setVisible(false);
            }
        });
        continueToShopButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                contentPane.getChildren().setAll(productView);
                shoppingCardButton.setVisible(true);
                continueToShopButton.setVisible(false);
                helpButton.setDisable(false);
                txtSearch.setVisible(true);
            }
        });
        helpButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                contentPane.getChildren().setAll(helpView);
                continueToShopButton.setVisible(true);
                shoppingCardButton.setVisible(false);
                helpButton.setDisable(true);
                txtSearch.setVisible(false);
            }
        });

        contentPane.getChildren().setAll(productView);

    }

}
