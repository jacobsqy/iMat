package App;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class ShoppingCartView {
    @FXML private Label totalAmountLabel;
    @FXML private Label totalPriceLabel;

    @FXML
    public void initialize() {

    }

    @FXML private void toPaymentButtonPressed() {
        // TODO
        // change the current viwe to the payment view
    }

    @FXML private void emptyShoppingCartButtonPressed() {
        BackendController.emptyShoppingCart();
    }
}
