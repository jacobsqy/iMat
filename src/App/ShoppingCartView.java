package App;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class ShoppingCartView extends AnchorPane {
    @FXML private Label totalAmountLabel;
    @FXML private Label totalPriceLabel;

    @FXML private void continueToShopButtonPressed() {
        //TODO
        // change the current view to the previous one
    }

    @FXML private void toPaymentButtonPressed() {
        // TODO
        // change the current viwe to the payment view
    }

    @FXML private void emptyShoppingCartButtonPressed() {
        BackendController.emptyShoppingCart();
    }
}
