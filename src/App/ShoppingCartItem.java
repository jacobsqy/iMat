package App;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;
import java.text.DecimalFormat;

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

        priceLabel.setText(new DecimalFormat("#.##").format(shoppingItem.getProduct().getPrice()));
        totalPriceLabel.setText(new DecimalFormat("#.##").format((shoppingItem.getTotal())));
        labelCount.setText(new DecimalFormat("#.##").format(shoppingItem.getAmount()));
    }

    public void setParentView(ShoppingCartView parentView) {
        this.parentView = parentView;
    }

    private void updateInfo() {
        priceLabel.setText(new DecimalFormat("#.##").format((shoppingItem.getProduct().getPrice())));
        totalPriceLabel.setText(new DecimalFormat("#.##").format((shoppingItem.getTotal())));
        labelCount.setText(new DecimalFormat("#.##").format((shoppingItem.getAmount())));
        parentView.updateInfo();
    }

    @FXML private void deleteButtonPressed() {
        BackendController.removeFromCart(shoppingItem);
        parentView.updateList();
        updateInfo();
    }

    @FXML private void increaseButtonPressed() {
        if (shoppingItem.getAmount() >= 99) return;
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
