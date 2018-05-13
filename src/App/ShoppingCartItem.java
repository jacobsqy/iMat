package App;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;

public class ShoppingCartItem extends AnchorPane {
    @FXML private ImageView productImage;
    @FXML private Label productNameLabel;
    @FXML private Spinner<Integer> amountSpinner;
    @FXML private Label priceLabel;
    @FXML private Label totalPriceLabel;

    private ShoppingItem shoppingItem;

    public ShoppingCartItem(ShoppingItem shoppingItem) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ShoppingCartItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        productImage.setImage(new Image("resources/imat/images/" + shoppingItem.getProduct().getImageName()));
        productNameLabel.setText(shoppingItem.getProduct().getName());
        updatePrice();

        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, (int) shoppingItem.getAmount(), 1);
        amountSpinner.setValueFactory(valueFactory);
        amountSpinner.setEditable(true);
        amountSpinner.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);

        this.shoppingItem = shoppingItem;
    }

    private void updatePrice() {
        priceLabel.setText(String.valueOf(shoppingItem.getProduct().getPrice()));
        totalPriceLabel.setText(String.valueOf(shoppingItem.getTotal()));
    }

    @FXML private void deleteButtonPressed() {
        BackendController.removeFromCart(shoppingItem);
    }

    @FXML private void spinnerPressed() {
        shoppingItem.setAmount(amountSpinner.getValue());
        updatePrice();
    }
}
