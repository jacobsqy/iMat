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
import se.chalmers.cse.dat216.project.CartEvent;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.ShoppingCartListener;

import javax.swing.text.html.ImageView;
import java.text.DecimalFormat;

public class MainWindow {
    @FXML private TextField txtSearch;
    @FXML private ChoiceBox choiceBox;
    @FXML private Button shoppingCartButton;
    @FXML private Button helpButton;
    @FXML private Button continueToShopButton;
    @FXML private Label amountOfProducts, totalPrice;

    @FXML private AnchorPane contentPane;
    @FXML private AnchorPane productView;
    @FXML private ProductView productViewController;
    @FXML private AnchorPane shoppingView;
    @FXML private AnchorPane helpView;
    @FXML private AnchorPane paymentView;
    @FXML private ShoppingCartView shoppingViewController;

    public void initialize() {

        shoppingViewController.setParentController(this);

        Platform.runLater(() -> txtSearch.requestFocus());

        // autocomplete sök, det som skrivs i textfield visas på en dropdown
        txtSearch.textProperty().addListener(((observable, oldValue, newValue) -> {

            productViewController.updateSearch(newValue);
            choiceBox.getItems().clear();
            if(!newValue.isEmpty()) {
                for(String item: BackendController.getProductNames()) {
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

        // knappen till varukorg/försätt handla visas eller döljs
        shoppingCartButton.setVisible(true);
        continueToShopButton.setVisible(false);

        shoppingCartButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                contentPane.getChildren().setAll(shoppingView);
                continueToShopButton.setVisible(true);
                shoppingCartButton.setVisible(false);
                txtSearch.setVisible(false);
                shoppingViewController.updateList();
                shoppingViewController.updateInfo();
            }
        });
        continueToShopButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                contentPane.getChildren().setAll(productView);
                shoppingCartButton.setVisible(true);
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
                shoppingCartButton.setVisible(false);
                helpButton.setDisable(true);
                txtSearch.setVisible(false);
            }
        });

        IMatDataHandler.getInstance().getShoppingCart().addShoppingCartListener(new ShoppingCartListener() {
            @Override
            public void shoppingCartChanged(CartEvent cartEvent) {
                System.out.println("wow");
                updateInfo();
            }
        });


        contentPane.getChildren().setAll(productView);

    }

    private void updateInfo() {
        System.out.println("super cool");
        amountOfProducts.setText(new DecimalFormat("#.##").format(BackendController.getTotalProductAmount()));
        totalPrice.setText(new DecimalFormat("#.##").format(BackendController.getTotalPrice()));
    }

    public void changeToPaymentView(){
        contentPane.getChildren().setAll(paymentView);
        continueToShopButton.setVisible(true);
        shoppingCartButton.setVisible(false);
        helpButton.setDisable(true);
        txtSearch.setVisible(false);
    }
}
