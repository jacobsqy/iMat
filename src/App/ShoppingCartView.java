package App;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static App.BackendController.backend;

public class ShoppingCartView {
    @FXML private Label totalAmountLabel;
    @FXML private Label totalPriceLabel;
    @FXML private FlowPane flowPane;
    private Map<Integer, ShoppingCartItem> shoppingCartItemMap;
    private List<Integer> productIdList = new LinkedList<>();

    public void init() {
        // add all shoppingCartItems to a Map
        for (ShoppingItem shoppingItem : backend.getShoppingCart().getItems()) {
            ShoppingCartItem shoppingCartItem = new ShoppingCartItem(shoppingItem);
            System.out.println(shoppingItem.getProduct().getProductId() + ": " + shoppingCartItem);
            shoppingCartItemMap.put(shoppingItem.getProduct().getProductId(), shoppingCartItem);
            productIdList.add(shoppingItem.getProduct().getProductId());
        }
        updateList();
    }

    public void updateList() {
        flowPane.getChildren().clear();
        for (int i : productIdList) {
            flowPane.getChildren().add(shoppingCartItemMap.get(i));
        }
    }


    @FXML private void toPaymentButtonPressed() {
        // TODO
        // change the current viwe to the payment view
    }

    @FXML private void emptyShoppingCartButtonPressed() {
        BackendController.emptyShoppingCart();
    }
}
