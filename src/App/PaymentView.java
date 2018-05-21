package App;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import se.chalmers.cse.dat216.project.Customer;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.ShoppingItem;

//import javax.xml.soap.Text;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

    @FXML private TextField firstNameTextField;
    @FXML private TextField lastNameTextField;
    @FXML private TextField addressTextField;
    @FXML private TextField postCodeTextField;
    @FXML private TextField postAddressTextField;

    @FXML private TextField creditCardNameTextField;
    @FXML private TextField creditCardFirstNumberTextField;
    @FXML private TextField creditCardSecondNumberTextField;
    @FXML private TextField creditCardThirdNumberTextField;
    @FXML private TextField creditCardForthNumberTextField;
    @FXML private TextField creditCardCVCTextField;
    @FXML private ChoiceBox creditCardMonthTextField;
    @FXML private ChoiceBox creditCardYearTextField;

    @FXML private TextArea addressTextArea;
    @FXML private TextArea creditCardTextArea;
    @FXML private TextArea cartTextArea;

    @FXML private CheckBox saveCheckBox;
    @FXML private CheckBox saveCreditCheckBox;

    @FXML private Label firstPageWarningLabel;
    @FXML private Label secondPageWarningLabel;

    private List<AnchorPane> anchorPaneList = new ArrayList<AnchorPane>();
    private int anchorPaneListIndex;

    private List<TextField> firstPage = new ArrayList<>();
    private List<TextField> secondPage = new ArrayList<>();

    private MainWindow parentController;
    //h
    public void initialize() {
        fillAnchorPaneList();
        fillTextFieldList();
        setCustomer();
        focus(0);
        firstPageWarningLabel.setVisible(false);
        secondPageWarningLabel.setVisible(false);

        creditCardMonthTextField.getItems().addAll("01", "02", "03", "04", "05","06", "07", "08","09","10","11","12");

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        creditCardYearTextField.getItems().addAll(currentYear,currentYear+1,currentYear+2,currentYear+3,currentYear+4);


        //Checking CVC
        creditCardCVCTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                creditCardCVCTextField.setText(newValue.replaceAll("[^\\d]", ""));
            }
            if(newValue.length() >= 3){
                creditCardMonthTextField.requestFocus();
            }
            if(newValue.length() > 3){
                creditCardCVCTextField.setText(oldValue);
            }
        });


            creditCardFirstNumberTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("\\d*")) {
                    creditCardFirstNumberTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }

                if(newValue.length() >= 4){
                    creditCardSecondNumberTextField.requestFocus();
                }

                if(newValue.length() > 4){
                    creditCardFirstNumberTextField.setText(oldValue);
                }

            });

        creditCardSecondNumberTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                creditCardSecondNumberTextField.setText(newValue.replaceAll("[^\\d]", ""));
            }
            if(newValue.length() >= 4){
                creditCardThirdNumberTextField.requestFocus();
            }

            if(newValue.length() > 4){
                creditCardSecondNumberTextField.setText(oldValue);
            }
        });

        creditCardThirdNumberTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                creditCardThirdNumberTextField.setText(newValue.replaceAll("[^\\d]", ""));
            }
            if(newValue.length() >= 4){
                creditCardForthNumberTextField.requestFocus();
            }

            if(newValue.length() > 4){
                creditCardThirdNumberTextField.setText(oldValue);
            }
        });

        creditCardForthNumberTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                creditCardForthNumberTextField.setText(newValue.replaceAll("[^\\d]", ""));
            }
            if(newValue.length() >= 4){
                creditCardCVCTextField.requestFocus();
            }

            if(newValue.length() > 4){
                creditCardForthNumberTextField.setText(oldValue);
            }
        });

    }

    private void setCustomer(){
        Customer c = BackendController.getCustomer();

        firstNameTextField.setText(c.getFirstName());
        lastNameTextField.setText(c.getLastName());
        postAddressTextField.setText(c.getPostAddress());
        postCodeTextField.setText(c.getPostCode());
        addressTextField.setText(c.getAddress());

        creditCardNameTextField.setText(c.getPhoneNumber());
        /*creditCardFirstNumberTextField.setText(c.getMobilePhoneNumber().substring(0,3));
        creditCardSecondNumberTextField.setText(c.getMobilePhoneNumber().substring(4,7));
        creditCardThirdNumberTextField.setText(c.getMobilePhoneNumber().substring(8,11));
        creditCardForthNumberTextField.setText(c.getMobilePhoneNumber().substring(12,15));
        creditCardMonthTextField.getSelectionModel().select(Integer.parseInt(c.getEmail().substring(0,1)));
        creditCardYearTextField.getSelectionModel().select(Integer.parseInt(c.getEmail().substring(1,2)));*/
    }

    private void fillTextFieldList() {
        firstPage.add(firstNameTextField);
        firstPage.add(lastNameTextField);
        firstPage.add(addressTextField);
        firstPage.add(postAddressTextField);
        firstPage.add(postCodeTextField);

        secondPage.add(creditCardCVCTextField);
        secondPage.add(creditCardNameTextField);
        secondPage.add(creditCardFirstNumberTextField);
        secondPage.add(creditCardSecondNumberTextField);
        secondPage.add(creditCardThirdNumberTextField);
        secondPage.add(creditCardForthNumberTextField);
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
            purchase();
        }

        if(saveCheckBox.isSelected()) {
            saveAddressData();
        }else {
            //Clear customer
        }

        if(saveCreditCheckBox.isSelected()) {
            saveCreditCardData();
        }else{
            //Clear customer
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

        if(i == 2){
            setConfirmPage();
        }
    }

    public void setParentController(MainWindow parentController) {
        this.parentController = parentController;
    }

    private void saveAddressData() {
        Customer c = BackendController.getCustomer();
        //First screen
        c.setFirstName(firstNameTextField.getText());
        c.setLastName(lastNameTextField.getText());
        c.setAddress(addressTextField.getText());
        c.setPostCode(postCodeTextField.getText());
        c.setPostAddress(postAddressTextField.getText());
    }

    private void saveCreditCardData() {
        Customer c = BackendController.getCustomer();

        /*c.setMobilePhoneNumber(creditCardFirstNumberTextField.getText() + creditCardSecondNumberTextField.getText() +
                creditCardThirdNumberTextField.getText() + creditCardForthNumberTextField.getText());*/
        c.setPhoneNumber(creditCardNameTextField.getText());
        //c.setEmail(Integer.toString(creditCardMonthTextField.getSelectionModel().getSelectedIndex() + creditCardYearTextField.getSelectionModel().getSelectedIndex()));
        //Second screen

    }

    @FXML public void goBackButtonPressed() {
        focusPrevious();
    }

    @FXML public void goForwardButtonPressed() {
        if(checkTextFields()){
            focusNext();
        }
    }

    boolean checkTextFields(){
        boolean bool = true;
        switch (anchorPaneListIndex){
            case 0:
                firstPageWarningLabel.setVisible(false);

                for (TextField text : firstPage) {
                    if(text.getText().isEmpty()){
                        firstPageWarningLabel.setVisible(true);
                        text.setStyle("-fx-text-box-border: red ; -fx-focus-color: red ;");
                        bool = false;
                    }else{
                        text.setStyle("");
                    }
                }
                return bool;
            case 1:
                secondPageWarningLabel.setVisible(false);

                for (TextField text : secondPage) {
                    if(text.getText().isEmpty()){
                        secondPageWarningLabel.setVisible(true);
                        text.setStyle("-fx-text-box-border: red ; -fx-focus-color: red ;");
                        bool = false;
                    } else{
                        text.setStyle("");
                    }
                }
                if(creditCardYearTextField.getSelectionModel().isEmpty()) {
                    secondPageWarningLabel.setVisible(true);
                    creditCardYearTextField.setStyle("-fx-border-color: red");
                    bool = false;
                } else{
                    creditCardYearTextField.setStyle("");
                }

                if(creditCardMonthTextField.getSelectionModel().isEmpty()){
                    secondPageWarningLabel.setVisible(true);
                    creditCardMonthTextField.setStyle("-fx-border-color: red");
                    bool = false;
                } else{
                   creditCardMonthTextField.setStyle("");
                }

                default: return bool;
        }
    }

    @FXML public void setConfirmPage(){
        Customer c = BackendController.getCustomer();
        addressTextArea.clear();
        creditCardTextArea.clear();
        cartTextArea.clear();


        addressTextArea.appendText(firstNameTextField.getText()+ "\n" + lastNameTextField.getText() + "\n" + addressTextField.getText() + "\n" + postCodeTextField.getText() + "\n" + postAddressTextField.getText());
        creditCardTextArea.appendText(creditCardNameTextField.getText() + "\n" + creditCardFirstNumberTextField.getText() + creditCardSecondNumberTextField.getText() + creditCardThirdNumberTextField.getText() + creditCardForthNumberTextField.getText() + "\n" + creditCardMonthTextField.getSelectionModel().getSelectedItem().toString() + " " +  creditCardYearTextField.getSelectionModel().getSelectedItem().toString());

        for (ShoppingItem product: backend.getShoppingCart().getItems()) {
            cartTextArea.appendText(product.getProduct().getName() + " " + product.getAmount() + product.getProduct().getUnitSuffix() + "\n");
        }
        cartTextArea.appendText("\nTotalt pris: " + Double.toString(backend.getShoppingCart().getTotal()) + "kr");
    }

    @FXML public void purchase(){
        Order order = new Order();
        order.setItems(backend.getShoppingCart().getItems());

        backend.placeOrder(); //sparar historik och rensar shopingcart
        backend.shutDown(); // sparar alla data

        focus(0);

        parentController.orderComplete();
    }





}
