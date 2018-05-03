package App;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Product;

import java.io.IOException;

public class ProductItem extends AnchorPane {

    @FXML private ImageView productImage;
    @FXML private Label lblName;
    @FXML private Label lblPrice;
    @FXML private Spinner spinnerCount;

    public ProductItem(Product product) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Product.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        productImage.setImage(new Image(ProductItem.class.getResourceAsStream("resources/imat/images/" + product.getImageName())));
        lblName.setText(product.getName());
        lblPrice.setText(product.getPrice() + " " + product.getUnit());

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0, 1);
        spinnerCount.setValueFactory(valueFactory);
        spinnerCount.setEditable(true);
    }
}
