package App;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static App.BackendController.backend;

public class ShoppingCartView {
    @FXML private Label totalAmountLabel;
    @FXML private Label totalPriceLabel;
    @FXML private FlowPane flowPane;
    private MainWindow parentController;

    public void initialize() {
        updateList();
    }

    public void updateList() {
        boolean even = false;
        List<ShoppingCartItem> shoppingCartItemList = new ArrayList<>();
        for (ShoppingItem shoppingItem : backend.getShoppingCart().getItems()) {
            ShoppingCartItem shoppingCartItem = new ShoppingCartItem(shoppingItem);
            shoppingCartItem.setParentView(this);
            if (even) {
                shoppingCartItem.setStyle("-fx-background-color: #ddd");
                even = false;
            } else {
                even = true;
            }
            shoppingCartItemList.add(shoppingCartItem);
        }
        flowPane.getChildren().clear();
        for (ShoppingCartItem shoppingCartItem : shoppingCartItemList) {
            flowPane.getChildren().add(shoppingCartItem);
        }
    }

    public void setParentController(MainWindow parentController) {
        this.parentController = parentController;
    }

    @FXML private void toPaymentButtonPressed() {
        parentController.changeToPaymentView();
    }

    @FXML private void emptyShoppingCartButtonPressed() {
        BackendController.emptyShoppingCart();
        updateList();
        updateInfo();
    }

    @FXML public void updateInfo() {
        totalPriceLabel.setText(new DecimalFormat("#.##").format(BackendController.getTotalPrice()) + " kr");
        totalAmountLabel.setText(new DecimalFormat("#.##").format(BackendController.getTotalProductAmount()) + " st");
        parentController.updateInfo();
    }
}