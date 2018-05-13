package App;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;
import java.util.List;

public class ProductItem extends AnchorPane {

    @FXML private ImageView productImage;
    @FXML private Label lblName;
    @FXML private Label lblPrice;
    @FXML private Spinner spinnerCount;
    @FXML private Product product;
    @FXML private ImageView imgFavorite;

    public ProductItem(Product product, IMatDataHandler dataHandler) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Product.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        List<Product> favorite = dataHandler.favorites();

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        imgFavorite.setImage(new Image(ProductItem.class.getResourceAsStream("resources/imat/images/" + (dataHandler.isFavorite(product) ? "favorite.png":"favorite_empty.png"))));
        imgFavorite.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (dataHandler.isFavorite(product)) {
                    dataHandler.removeFavorite(product);
                    imgFavorite.setImage(new Image(ProductItem.class.getResourceAsStream("resources/imat/images/favorite_empty.png")));
                } else {
                    dataHandler.addFavorite(product.getProductId());
                    imgFavorite.setImage(new Image(ProductItem.class.getResourceAsStream("resources/imat/images/favorite.png")));
                }
            }
        });

        productImage.setImage(new Image(ProductItem.class.getResourceAsStream("resources/imat/images/" + product.getImageName())));
        lblName.setText(product.getName());
        lblPrice.setText(product.getPrice() + " " + product.getUnit());

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1, 1);
        spinnerCount.setValueFactory(valueFactory);
        spinnerCount.setEditable(true);
        spinnerCount.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);

        this.product = product;
    }

    @FXML
    private void addToCartPressed() {
        BackendController.addToCart(product, (int) spinnerCount.getValue());
    }
}
