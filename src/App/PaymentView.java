package App;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.awt.*;

public class PaymentView extends AnchorPane {

    @FXML private AnchorPane addressInfoPane;
    @FXML private AnchorPane cardInfoPane;
    @FXML private AnchorPane confirmPane;

    @FXML private Button goBackAddressButton;
    @FXML private Button nextStepAddressButton;
    @FXML private Button nextStepCardButton;
    @FXML private Button goBackCardButton;
    @FXML private Button confirmButton;
    @FXML private Button backToShoppingCartButton;

}

