package App.Controllers;

import App.BackendController;
import App.ProductItem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

import se.chalmers.cse.dat216.project.ShoppingItem;

import static App.MainWindow.main;

public class HistoryDetailProductView extends AnchorPane {

    @FXML private ImageView productImage;
    @FXML private Label productName;
    @FXML private Label productUnit;
    @FXML private Label totalPrice;
    @FXML private TextField txtCount;

    private ShoppingItem shoppingItem;
    private HistoryDetailView parentView;
    private int count = 0;

    public HistoryDetailProductView(ShoppingItem shoppingItem) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../HistoryViews/HistoryDetailProductView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.shoppingItem = shoppingItem;

        count = (int) shoppingItem.getAmount();

        productImage.setImage(new Image(ProductItem.class.getResourceAsStream("resources/imat/images/" + shoppingItem.getProduct().getImageName())));
        productName.setText(shoppingItem.getProduct().getName());
        productUnit.setText(shoppingItem.getProduct().getUnitSuffix());
        totalPrice.setText(String.valueOf((int)shoppingItem.getTotal()) + " kr");
        txtCount.setText(String.valueOf((int) shoppingItem.getAmount()));
        txtCount.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent arg0) {

                if (txtCount.getText().length() >= 2) {
                    arg0.consume();
                }
            }

        });
        txtCount.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                if (!newValue.matches("\\d*") || (!newValue.isEmpty() && newValue.compareTo("1") < 0)) {
                    txtCount.setText(String.valueOf(count));
                }
                else if (newValue.isEmpty()) {}
                else {
                    txtCount.setText(newValue);
                    count = Integer.parseInt(newValue);
                }
                updateInfo();
            }
        });

        txtCount.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (oldValue && txtCount.getText().isEmpty()) {
                    txtCount.setText("1");
                }
            }
        });

    }

    private void updateInfo() {
        totalPrice.setText(((count * (int) shoppingItem.getProduct().getPrice()) + " kr"));
    }

    public void setParentView(HistoryDetailView parentView) {
        this.parentView = parentView;
    }

    @FXML
    private void increaseButtonPressed() {
        if (count < 99) {
            count += 1;
            txtCount.setText(Integer.toString(count));
            updateInfo();
        }
    }

    @FXML
    private void decreaseButtonPressed() {
        if (count > 1) {
            count -= 1;
            txtCount.setText(Integer.toString(count));
            updateInfo();
        }
    }

    @FXML
    private void addProduct() {
        if (!txtCount.getText().isEmpty()) {
            BackendController.addToCart(shoppingItem.getProduct(), Integer.valueOf(txtCount.getText()));
            main.get(0).updateInfo();
            main.get(0).showProductAddedToShoppingCartInfo(shoppingItem.getProduct(), Integer.valueOf(txtCount.getText()));
        }

    }

}
