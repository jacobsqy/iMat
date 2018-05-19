package App;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PaymentView extends AnchorPane {

    @FXML private StackPane stackPane;

    @FXML private AnchorPane addressInfoPane;
    @FXML private AnchorPane cardInfoPane;
    @FXML private AnchorPane confirmPane;

    @FXML private Button goBackAddressButton;
    @FXML private Button nextStepAddressButton;
    @FXML private Button nextStepCardButton;
    @FXML private Button goBackCardButton;
    @FXML private Button confirmButton;
    @FXML private Button backToShoppingCartButton;

    private List<AnchorPane> anchorPaneList = new ArrayList<AnchorPane>();
    private int anchorPaneListIndex;

    private MainWindow parentController;

    public void initialize() {
        fillAnchorPaneList();
        focus(0);
    }

    private void fillAnchorPaneList() {
        anchorPaneList.add(addressInfoPane);
        anchorPaneList.add(cardInfoPane);
        anchorPaneList.add(confirmPane);
    }

    private void focusNext() {
        if (anchorPaneListIndex < 2) {
            focus(anchorPaneListIndex + 1);
        } else {
            //TODO make the payment
        }
    }

    private void focusPrevious() {
        if (anchorPaneListIndex > 0) {
            focus(anchorPaneListIndex - 1);
        } else {
            parentController.goToShoppingCart();
        }
    }

    private void focus(int i) {
        anchorPaneListIndex = i;
        anchorPaneList.get(anchorPaneListIndex).toFront();
    }

    public void setParentController(MainWindow parentController) {
        this.parentController = parentController;
    }

    @FXML public void goBackButtonPressed() {
        focusPrevious();
    }

    @FXML public void goForwardButtonPressed() {
        focusNext();
    }
}
