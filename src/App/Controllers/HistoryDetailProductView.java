package App.Controllers;

import App.BackendController;
import App.MainWindow;
import App.ProductItem;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.ShoppingItem;
import java.io.IOException;
import java.text.DecimalFormat;

import static App.MainWindow.updateInfoLabels;

public class HistoryDetailProductView extends AnchorPane {

    @FXML private ImageView productImage;
    @FXML private Label productName;
    @FXML private Label productUnit;
    @FXML private Label totalPrice;
    @FXML private Label labelCount;

    private ShoppingItem shoppingItem;

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
        labelCount.setText(String.valueOf((int) shoppingItem.getAmount()));
        totalPrice.setText(String.valueOf((int)shoppingItem.getTotal()) + " kr");
    }

    private void updateInfo() {
        totalPrice.setText(String.valueOf((int)shoppingItem.getTotal()) + " kr");
        labelCount.setText(String.valueOf((int) shoppingItem.getAmount()));
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
        BackendController.addToCart(shoppingItem.getProduct(), Integer.valueOf(labelCount.getText()));
        updateInfoLabels.get(0).setText(new DecimalFormat("#.##").format((BackendController.getTotalProductAmount())));
        updateInfoLabels.get(1).setText(new DecimalFormat("#.##").format((BackendController.getTotalPrice())));
    }

}
