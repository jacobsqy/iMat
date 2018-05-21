package App;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import se.chalmers.cse.dat216.project.CreditCard;
import se.chalmers.cse.dat216.project.Customer;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.ShoppingItem;

//import javax.xml.soap.Text;
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
    @FXML private TextField eMailTextField;

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

    @FXML private ToggleButton firstDateToggleButton;
    @FXML private ToggleButton secondDateToggleButton;
    @FXML private ToggleButton thirdDateToggleButton;

    @FXML private CheckBox saveCheckBox;
    @FXML private CheckBox saveCreditCheckBox;

    @FXML private Label firstPageWarningLabel;
    @FXML private Label secondPageWarningLabel;

    private List<AnchorPane> anchorPaneList = new ArrayList<AnchorPane>();
    private int anchorPaneListIndex;

    private List<TextField> firstPage = new ArrayList<>();
    private List<TextField> secondPage = new ArrayList<>();

    private MainWindow parentController;

    public void initialize() {
        fillAnchorPaneList();
        fillTextFieldList();
        setCustomer();
        focus(0);
        firstPageWarningLabel.setVisible(false);
        secondPageWarningLabel.setVisible(false);

        creditCardMonthTextField.getItems().addAll("01", "02", "03", "04", "05","06", "07", "08","09","10","11","12");

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        int currectDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        firstDateToggleButton.setText(Integer.toString(currectDay+1)+ "/" + Integer.toString(currentMonth+1));
        secondDateToggleButton.setText(Integer.toString(currectDay+2)+ "/" + Integer.toString(currentMonth+1));
        thirdDateToggleButton.setText(Integer.toString(currectDay+3)+ "/" + Integer.toString(currentMonth+1));

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


        postCodeTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.matches("\\d*")) {
                        postCodeTextField.setText(newValue.replaceAll("[^\\d]", ""));
                    }

                if(newValue.length() >= 5){
                postAddressTextField.requestFocus();
                }

                    if(newValue.length() > 5){
                        postCodeTextField.setText(oldValue);
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
        CreditCard creditCard = BackendController.getCreditCard();

        if(!c.getFirstName().isEmpty()) {
            firstNameTextField.setText(c.getFirstName());
            lastNameTextField.setText(c.getLastName());
            postAddressTextField.setText(c.getPostAddress());
            postCodeTextField.setText(c.getPostCode());
            addressTextField.setText(c.getAddress());
            eMailTextField.setText(c.getEmail());
        }

        if (!creditCard.getHoldersName().isEmpty()) {
            creditCardNameTextField.setText(creditCard.getHoldersName());
            creditCardFirstNumberTextField.setText(creditCard.getCardNumber().substring(0, 4));
            creditCardSecondNumberTextField.setText(creditCard.getCardNumber().substring(4, 8));
            creditCardThirdNumberTextField.setText(creditCard.getCardNumber().substring(8, 12));
            creditCardForthNumberTextField.setText(creditCard.getCardNumber().substring(12, 16));
            creditCardMonthTextField.getSelectionModel().select(creditCard.getValidMonth());
            creditCardYearTextField.getSelectionModel().select(creditCard.getValidYear());
        }
    }

    private void clearFields(){
        firstNameTextField.clear();
        lastNameTextField.clear();
        postAddressTextField.clear();
        postCodeTextField.clear();
        addressTextField.clear();
        firstDateToggleButton.setSelected(false);
        secondDateToggleButton.setSelected(false);
        thirdDateToggleButton.setSelected(false);

        creditCardNameTextField.clear();
        creditCardFirstNumberTextField.clear();
        creditCardSecondNumberTextField.clear();
        creditCardThirdNumberTextField.clear();
        creditCardForthNumberTextField.clear();
        creditCardCVCTextField.clear();
        creditCardMonthTextField.getSelectionModel().clearSelection();
        creditCardYearTextField.getSelectionModel().clearSelection();
    }

    private void fillTextFieldList() {
        firstPage.add(firstNameTextField);
        firstPage.add(lastNameTextField);
        firstPage.add(addressTextField);
        firstPage.add(postAddressTextField);
        firstPage.add(postCodeTextField);
        firstPage.add(eMailTextField);

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
            Customer c = BackendController.getCustomer();
            c.setEmail("");
            c.setPhoneNumber("");
            c.setAddress("");
            c.setFirstName("");
            c.setLastName("");
            c.setPostAddress("");
            c.setPostCode("");
        }

        if(saveCreditCheckBox.isSelected()) {
            saveCreditCardData();
        }else{
            CreditCard creditCard = BackendController.getCreditCard();
            creditCard.setHoldersName("");
            creditCard.setCardNumber("");
            creditCard.setValidMonth(0);
            creditCard.setValidYear(0);
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
        c.setEmail(eMailTextField.getText());
    }

    private void saveCreditCardData() {
        CreditCard creditCard = BackendController.getCreditCard();
        creditCard.setCardNumber(creditCardFirstNumberTextField.getText() + creditCardSecondNumberTextField.getText() +
                creditCardThirdNumberTextField.getText() + creditCardForthNumberTextField.getText());
        creditCard.setHoldersName( creditCardNameTextField.getText());
        creditCard.setValidMonth((creditCardMonthTextField.getSelectionModel().getSelectedIndex()));
        creditCard.setValidYear((creditCardYearTextField.getSelectionModel().getSelectedIndex()));

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

                if(!firstDateToggleButton.isSelected() && !secondDateToggleButton.isSelected() && !thirdDateToggleButton.isSelected()){
                    firstPageWarningLabel.setVisible(true);
                    firstDateToggleButton.setStyle("-fx-border-color: red");
                    secondDateToggleButton.setStyle("-fx-border-color: red");
                    thirdDateToggleButton.setStyle("-fx-border-color: red");
                    bool = false;
                } else{
                    firstDateToggleButton.setStyle("");
                    secondDateToggleButton.setStyle("");
                    thirdDateToggleButton.setStyle("");
                }
                return bool;
            case 1:
                secondPageWarningLabel.setVisible(false);

                for (TextField text : secondPage) {
                    if(text.getText().length() < 4){
                        secondPageWarningLabel.setVisible(true);
                        text.setStyle("-fx-text-box-border: red ; -fx-focus-color: red ;");
                        bool = false;
                    } else{
                        text.setStyle("");
                    }
                }

                if(creditCardCVCTextField.getText().length() < 3){
                    secondPageWarningLabel.setVisible(true);
                    creditCardCVCTextField.setStyle("-fx-text-box-border: red ; -fx-focus-color: red ;");
                    bool = false;
                } else{
                    creditCardCVCTextField.setStyle("");
                }

                if(creditCardNameTextField.getText().isEmpty()){
                    secondPageWarningLabel.setVisible(true);
                    creditCardNameTextField.setStyle("-fx-text-box-border: red ; -fx-focus-color: red ;");
                    bool = false;
                } else{
                    creditCardNameTextField.setStyle("");
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
        clearFields();
        setCustomer();

        backend.placeOrder(); //sparar historik och rensar shopingcart
        backend.shutDown(); // sparar alla data

        focus(0);

        parentController.orderComplete();
    }

    @FXML public void firstToggle(){
        secondDateToggleButton.setSelected(false);
        thirdDateToggleButton.setSelected(false);
    }

    @FXML public void secondToggle(){
        firstDateToggleButton.setSelected(false);
        thirdDateToggleButton.setSelected(false);
    }

    @FXML public void thirdToggle(){
        secondDateToggleButton.setSelected(false);
        firstDateToggleButton.setSelected(false);
    }





}
