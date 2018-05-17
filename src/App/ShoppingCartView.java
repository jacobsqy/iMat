package App;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;
import sun.applet.Main;

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
    private MainWindow parentController;
    //private Map<Integer, ShoppingCartItem> shoppingCartItemMap;
    //private List<Integer> productIdList = new LinkedList<>();
    //TODO



    public void initialize() {
        // add all shoppingCartItems to a Map
        /*for (ShoppingItem shoppingItem : backend.getShoppingCart().getItems()) {
            ShoppingCartItem shoppingCartItem = new ShoppingCartItem(shoppingItem);
            shoppingCartItemMap.put(shoppingItem.getProduct().getProductId(), shoppingCartItem);
            productIdList.add(shoppingItem.getProduct().getProductId());
        }*/
        updateList();
    }

    public void updateList() {
        List<ShoppingCartItem> shoppingCartItemList = new ArrayList<>();
        for (ShoppingItem shoppingItem : backend.getShoppingCart().getItems()) {
            ShoppingCartItem shoppingCartItem = new ShoppingCartItem(shoppingItem);
            shoppingCartItem.setParentView(this);
            shoppingCartItemList.add(shoppingCartItem);
            //shoppingCartItemMap.put(shoppingItem.getProduct().getProductId(), shoppingCartItem);
            //productIdList.add(shoppingItem.getProduct().getProductId());
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
        // TODO
        // change the current viwe to the payment view
        parentController.changeToPaymentView();
    }

    @FXML private void emptyShoppingCartButtonPressed() {
        BackendController.emptyShoppingCart();
        updateList();
    }
}
