package App;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Product;

import java.io.IOException;
import java.util.List;

import static App.BackendController.backend;

public class ProductItem extends AnchorPane {

    @FXML private ImageView productImage;
    @FXML private Label lblName;
    @FXML private Label lblPrice;
    @FXML private ImageView imgFavorite;
    //@FXML private Spinner spinnerCount;
    @FXML private Label labelCount;
    @FXML private Button buttonIncrease;
    @FXML private Button buttonDecrease;

    ProductItemController pic;
    Product product;


    public ProductItem(Product product) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ProductItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        this.pic = new ProductItemController(this);
        this.product = product;

        List<Product> favorite = backend.favorites();
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        // lägga till favoriter
        imgFavorite.setImage(new Image(ProductItem.class.getResourceAsStream("resources/imat/images/" + (backend.isFavorite(product) ? "favorite.png":"favorite_empty.png"))));
        imgFavorite.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (backend.isFavorite(product)) {
                    backend.removeFavorite(product);
                    imgFavorite.setImage(new Image(ProductItem.class.getResourceAsStream("resources/imat/images/favorite_empty.png")));
                } else {
                    backend.addFavorite(product.getProductId());
                    imgFavorite.setImage(new Image(ProductItem.class.getResourceAsStream("resources/imat/images/favorite.png")));
                }
            }
        });

        // hämta och sätta produkt properties till relaterande element
        productImage.setImage(new Image(ProductItem.class.getResourceAsStream("resources/imat/images/" + product.getImageName())));
        lblName.setText(product.getName());
        lblPrice.setText(product.getPrice() + " " + product.getUnit());
        labelCount.setText("1");

        /*SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0, 1);
        spinnerCount.setValueFactory(valueFactory);
        spinnerCount.setEditable(true);
        spinnerCount.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);*/

        buttonDecrease.setOnAction(e -> pic.decreaseCount());

        buttonIncrease.setOnAction(e -> pic.increaseCount());
    }

    public Label getLabelCount() {
        return labelCount;
    }

    @FXML
    private void addToCartPressed() {
        BackendController.addToCart(product, Integer.valueOf(labelCount.getText()));
    }
}
