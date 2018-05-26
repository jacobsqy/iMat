package App;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;
import java.text.DecimalFormat;

public class ShoppingCartItem extends AnchorPane {
    @FXML private ImageView productImage;
    @FXML private Label productNameLabel;
    @FXML private Label priceLabel;
    @FXML private Label totalPriceLabel;
    @FXML private TextField textFieldCount;
    @FXML private Button buttonDecrease;
    @FXML private Button buttonIncrease;
    private ShoppingCartView parentView;

    private ShoppingItem shoppingItem;

    public ShoppingCartItem(ShoppingItem shoppingItem) {
        this.shoppingItem = shoppingItem;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ShoppingCartItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        productImage.setImage(new Image(ProductItem.class.getResourceAsStream("resources/imat/images/" + shoppingItem.getProduct().getImageName())));
        productNameLabel.setText(shoppingItem.getProduct().getName());

        priceLabel.setText(new DecimalFormat("#.##").format(shoppingItem.getProduct().getPrice()) + " kr");
        totalPriceLabel.setText(new DecimalFormat("#.##").format((shoppingItem.getTotal())) + " kr");
        textFieldCount.setText(new DecimalFormat("#.##").format(shoppingItem.getAmount()));
        textFieldCount.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {


            @Override
            public void handle(KeyEvent arg0) {

                if (textFieldCount.getText().length() >= 2) {
                    arg0.consume();
                }
            }

        });

        textFieldCount.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*") || (!newValue.isEmpty() && newValue.compareTo("1") < 0)) {
                    textFieldCount.setText(oldValue);
                }
                else if (newValue.isEmpty()){}
                else {
                    shoppingItem.setAmount(Integer.parseInt(newValue));
                    updateInfo();
                }
            }
        });
        textFieldCount.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (oldValue && textFieldCount.getText().isEmpty()) {
                    textFieldCount.setText("1");
                }
            }
        });
    }

    public void setParentView(ShoppingCartView parentView) {
        this.parentView = parentView;
    }

    private void updateInfo() {
        priceLabel.setText(new DecimalFormat("#.##").format((shoppingItem.getProduct().getPrice())) + " kr");
        totalPriceLabel.setText(new DecimalFormat("#.##").format((shoppingItem.getTotal())) + " kr");
        textFieldCount.setText(new DecimalFormat("#.##").format((shoppingItem.getAmount())));
        parentView.updateInfo();
    }

    @FXML private void deleteButtonPressed() {
        BackendController.removeFromCart(shoppingItem);
        parentView.updateList();
        updateInfo();
    }

    @FXML private void increaseButtonPressed() {
        if (Integer.valueOf(textFieldCount.getText()) >= 99) return;
        shoppingItem.setAmount(shoppingItem.getAmount() + 1);
        parentView.updateList();
        updateInfo();
    }

    @FXML private void decreaseButtonPressed() {
        if (shoppingItem.getAmount() <= 1) return;
        shoppingItem.setAmount(shoppingItem.getAmount() - 1);
        parentView.updateList();
        updateInfo();
    }
}
