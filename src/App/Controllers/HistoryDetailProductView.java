package App.Controllers;

import App.BackendController;
import App.ProductItem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

import se.chalmers.cse.dat216.project.ShoppingItem;

import static App.MainWindow.main;

public class HistoryDetailProductView extends AnchorPane {

    @FXML private ImageView productImage;
    @FXML private Label productName;
    @FXML private Label productUnit;
    @FXML private Label totalPrice;
    @FXML private TextField txtCount;

    private ShoppingItem shoppingItem;
    private HistoryDetailView parentView;

    public HistoryDetailProductView(ShoppingItem shoppingItem) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../HistoryViews/HistoryDetailProductView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.shoppingItem = shoppingItem;

        productImage.setImage(new Image(ProductItem.class.getResourceAsStream("resources/imat/images/" + shoppingItem.getProduct().getImageName())));
        productName.setText(shoppingItem.getProduct().getName());
        productUnit.setText(shoppingItem.getProduct().getUnitSuffix());
        totalPrice.setText(String.valueOf((int)shoppingItem.getTotal()) + " kr");
        txtCount.setText(String.valueOf((int) shoppingItem.getAmount()));
        txtCount.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent arg0) {

                TextField tx = (TextField) arg0.getSource();
                if (tx.getText().length() >= 2) {
                    arg0.consume();
                }
            }

        });
        txtCount.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*") || (!newValue.isEmpty() && newValue.compareTo("1") < 0)) {
                    txtCount.setText(oldValue);
                }
                else if (newValue.isEmpty()){}
                else {
                    shoppingItem.setAmount(Integer.parseInt(newValue));
                    updateInfo();
                }
            }
        });
    }

    private void updateInfo() {
        totalPrice.setText(String.valueOf((int)shoppingItem.getTotal()) + " kr");
        txtCount.setText(String.valueOf((int) shoppingItem.getAmount()));
    }

    public void setParentView(HistoryDetailView parentView) {
        this.parentView = parentView;
    }

    @FXML
    private void increaseButtonPressed() {
        if (shoppingItem.getAmount() >= 99) return;
        shoppingItem.setAmount(shoppingItem.getAmount() + 1);
        updateInfo();
    }

    @FXML
    private void decreaseButtonPressed() {
        if (shoppingItem.getAmount() <= 1) return;
        shoppingItem.setAmount(shoppingItem.getAmount() - 1);
        updateInfo();
    }

    @FXML
    private void addProduct() {
        BackendController.addToCart(shoppingItem.getProduct(), Integer.valueOf(txtCount.getText()));
        main.get(0).updateInfo();
        main.get(0).showProductAddedToShoppingCartInfo(shoppingItem.getProduct(), Integer.valueOf(txtCount.getText()));
    }

}
