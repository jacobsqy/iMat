package App;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
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
    @FXML private Label priceLabel;
    @FXML private Label totalPriceLabel;
    @FXML private Label labelCount;
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
        updateInfo();

    }

    public void setParentView(ShoppingCartView parentView) {
        this.parentView = parentView;
    }

    private void updateInfo() {
        priceLabel.setText(String.valueOf(shoppingItem.getProduct().getPrice()));
        totalPriceLabel.setText(String.valueOf(Math.round(shoppingItem.getTotal())));
        labelCount.setText(String.valueOf(Math.round(shoppingItem.getAmount())));
    }

    @FXML private void deleteButtonPressed() {
        BackendController.removeFromCart(shoppingItem);
        parentView.updateList();

    }

    @FXML private void increaseButtonPressed() {
        shoppingItem.setAmount(shoppingItem.getAmount() + 1);
        parentView.updateList();
        updateInfo();
    }

    @FXML private void decreaseButtonPressed() {
        shoppingItem.setAmount(shoppingItem.getAmount() - 1);
        parentView.updateList();
    }
}
