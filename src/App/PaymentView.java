package App;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import se.chalmers.cse.dat216.project.Customer;

import java.util.ArrayList;
import java.util.List;

import static App.BackendController.backend;

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

    @FXML private TextField firstNameTextArea;
    @FXML private TextField lastNameTextArea;
    @FXML private TextField addressTextArea;
    @FXML private TextField postCodeTextArea;
    @FXML private TextField postAddressTextArea;

    private List<AnchorPane> anchorPaneList = new ArrayList<AnchorPane>();
    private int anchorPaneListIndex;

    private MainWindow parentController;

    public void initialize() {
        fillAnchorPaneList();
        focus(0);


        /********* READ ME ***********/

        /*Lägg dessa koder i bekräfta köp för att spara historik, kontakter etc. och rensa shopingcart

        Order order = new Order();
        order.setItems(backend.getShoppingCart().getItems());

        backend.placeOrder(); //sparar historik och rensar shopingcart
        backend.shutDown(); // sparar alla data

        updateInfoLabels.get(0).setText(new DecimalFormat("#.##").format((BackendController.getTotalProductAmount())));
        updateInfoLabels.get(1).setText(new DecimalFormat("#.##").format((BackendController.getTotalPrice())));


        */
        // Murat
        /* ********** END ************/


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
        saveData();
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

    private void saveData() {
        Customer c = BackendController.getCustomer();
        //First screen

        c.setFirstName(firstNameTextArea.getText());
        c.setLastName(lastNameTextArea.getText());
        c.setAddress(addressTextArea.getText());
        c.setPostCode(postCodeTextArea.getText());
        c.setPostAddress(postAddressTextArea.getText());

        //Second screen

    }

    @FXML public void goBackButtonPressed() {
        focusPrevious();
    }

    @FXML public void goForwardButtonPressed() {
        focusNext();
        Customer c = BackendController.getCustomer();

    }
}
