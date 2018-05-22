package App;


import App.Controllers.HistoryProductView;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import se.chalmers.cse.dat216.project.Product;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static App.BackendController.backend;
import static App.Controllers.HistoryView.historyViews;

public class MainWindow {
    @FXML private TextField txtSearch;
    @FXML private ChoiceBox choiceBox;
    @FXML private Button shoppingCartButton;
    @FXML private Button helpButton;
    @FXML private Button continueToShopButton;
    @FXML private Button historyButton;
    @FXML private Label amountOfProducts, totalPrice;
    @FXML private javafx.scene.image.ImageView logoImageView;

    @FXML private AnchorPane contentPane;
    @FXML private AnchorPane productView;
    @FXML private ProductView productViewController;
    @FXML private AnchorPane shoppingView;
    @FXML private AnchorPane helpView;
    @FXML private AnchorPane paymentView;
    @FXML private PaymentView paymentViewController;
    @FXML private AnchorPane historyView;
    @FXML private ShoppingCartView shoppingViewController;
    @FXML private Label headlineLabel;

    @FXML private AnchorPane productAddedToShoppingCartInfo;
    @FXML private Label productAddedToShoppingCartInfoLabel;

    public static List<TextField> updatextSearchText = new ArrayList<TextField>();

    private FadeTransition ft;
    private FadeTransition reverseFadeTransition;

    public static List<MainWindow> main = new ArrayList<MainWindow>();

    public void initialize() {

        main.add(this);

        updatextSearchText.add(txtSearch);

        shoppingViewController.setParentController(this);
        productViewController.setParentController(this);
        paymentViewController.setParentController(this);
        updateInfo();
        
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
        headlineLabel.setVisible(false);

        shoppingCartButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                goToShoppingCart();
            }
        });

        continueToShopButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                continueToShop();
            }
        });

        helpButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                historyViews.get(0).getChildren().clear();
                historyViews.get(0).getChildren().setAll(historyViews.get(1));

                contentPane.getChildren().setAll(helpView);
                continueToShopButton.setVisible(true);
                shoppingCartButton.setVisible(false);
                helpButton.setDisable(true);
                historyButton.setDisable(false);
                txtSearch.setVisible(false);
                headlineLabel.setText("Hjälp");
                headlineLabel.setVisible(true);

            }
        });

        historyButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                contentPane.getChildren().setAll(historyView);
                updateOrderList();
                continueToShopButton.setVisible(true);
                shoppingCartButton.setVisible(false);
                helpButton.setDisable(false);
                historyButton.setDisable(true);
                txtSearch.setVisible(false);
                headlineLabel.setText("Historik");
                headlineLabel.setVisible(true);
            }
        });

        logoImageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                continueToShop();
            }
        });

        contentPane.getChildren().setAll(productView);

        initAnimation();
    }

    public void updateOrderList() {
        FXMLLoader loader = new FXMLLoader();
        Node node = null;
        loader.setLocation(getClass().getResource("HistoryViews/HistoryProductView.fxml"));
        try {
            node = (Node)loader.load();
            AnchorPane.setTopAnchor(node, 0.0);
            AnchorPane.setRightAnchor(node, 0.0);
            AnchorPane.setLeftAnchor(node, 0.0);
            AnchorPane.setBottomAnchor(node, 0.0);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        HistoryProductView historyDetailView = loader.getController();
        historyDetailView.updateList(backend.getOrders());

        historyViews.get(0).getChildren().clear();
        historyViews.get(0).getChildren().setAll(node);
    }

    private void initAnimation() {
        double delay = 2000;
        double duration = 1000;

        ft = new FadeTransition(Duration.millis(duration), productAddedToShoppingCartInfo);
        reverseFadeTransition = new FadeTransition(Duration.millis(duration), productAddedToShoppingCartInfo);


        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.setCycleCount(1);

        reverseFadeTransition.setFromValue(1.0);
        reverseFadeTransition.setToValue(0.0);
        reverseFadeTransition.setCycleCount(1);
        reverseFadeTransition.setDelay(Duration.millis(delay));

        ft.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                reverseFadeTransition.play();
            }
        });
    }

    private void continueToShop() {
        productViewController.changeToDefaultView();
        historyViews.get(0).getChildren().clear();
        historyViews.get(0).getChildren().setAll(historyViews.get(1));

        contentPane.getChildren().setAll(productView);
        shoppingCartButton.setVisible(true);
        continueToShopButton.setVisible(false);
        helpButton.setDisable(false);
        historyButton.setDisable(false);
        txtSearch.setVisible(true);
        headlineLabel.setVisible(false);
    }

    public void updateInfo() {
        amountOfProducts.setText(new DecimalFormat("#.##").format(BackendController.getTotalProductAmount()) + " st");
        totalPrice.setText(new DecimalFormat("#.##").format(BackendController.getTotalPrice()) + " kr");
    }

    public void changeToPaymentView(){
        if (BackendController.getShoppingItems().isEmpty()) return;
        contentPane.getChildren().setAll(paymentView);
        continueToShopButton.setVisible(true);
        shoppingCartButton.setVisible(false);
        helpButton.setDisable(true);
        txtSearch.setVisible(false);
        headlineLabel.setText("Betalning");
        headlineLabel.setVisible(true);
    }

    public void goToShoppingCart() {
        productViewController.changeToDefaultView();

        contentPane.getChildren().setAll(shoppingView);
        continueToShopButton.setVisible(true);
        shoppingCartButton.setVisible(false);
        txtSearch.setVisible(false);
        shoppingViewController.updateList();
        shoppingViewController.updateInfo();
        headlineLabel.setText("Varukorgen");
        headlineLabel.setVisible(true);
    }

    public void showProductAddedToShoppingCartInfo(Product product, int amount) {
        String info;
        if (amount == 1) {
            info =  amount + product.getUnitSuffix() + " " + product.getName() + " tillagd i varukorgen!";
        } else {
            info = amount + product.getUnitSuffix() + " " + product.getName() + " tillagda i varukorgen!";
        }
        productAddedToShoppingCartInfoLabel.setText(info);

        ft.play();
    }

    public void showProductAddedToShoppingCartInfo() {
        productAddedToShoppingCartInfoLabel.setText("Alla Produkter är tillagda i varukorgen!");
        ft.play();
    }

    public void orderComplete() {
        continueToShop();
        updateInfo();
        productViewController.changeToConfirmationView();
    }
}
