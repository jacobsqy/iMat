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
import java.text.DecimalFormat;

import static App.BackendController.backend;
import static App.ProductView.favoriteList;
import static App.ProductView.observableList;

public class ProductItem extends AnchorPane {

    private ProductView parentView;

    @FXML private ImageView productImage;
    @FXML private Label lblName;
    @FXML private Label lblPrice;
    @FXML private ImageView imgFavorite;
    //@FXML private Spinner spinnerCount;
    @FXML private Label labelCount;
    @FXML private Button buttonIncrease;
    @FXML private Button buttonDecrease;

    private ProductItemController pic;
    private Product product;


    public ProductItem(Product product) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ProductItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        this.pic = new ProductItemController(this);
        this.product = product;

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
                    observableList.remove(product);
                } else {
                    backend.addFavorite(product.getProductId());
                    imgFavorite.setImage(new Image(ProductItem.class.getResourceAsStream("resources/imat/images/favorite.png")));
                    favoriteList.add(product);
                }
            }
        });

        // hämta och sätta produkt properties till relaterande element
        productImage.setImage(new Image(ProductItem.class.getResourceAsStream("resources/imat/images/" + product.getImageName())));
        lblName.setText(product.getName());
        lblPrice.setText(new DecimalFormat("#.##").format((product.getPrice())) + " " + product.getUnit());
        labelCount.setText("1");

        /*SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0, 1);
        spinnerCount.setValueFactory(valueFactory);
        spinnerCount.setEditable(true);
        spinnerCount.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);*/

        buttonDecrease.setOnAction(e -> pic.decreaseCount());

        buttonIncrease.setOnAction(e -> pic.increaseCount());
    }

    /**
     * unfov icon sätts (används även för att uppdatera fav. listan, det första som klickas kvar står)
     */
    public void setImageToUnFav() {
        imgFavorite.setImage(new Image(ProductItem.class.getResourceAsStream("resources/imat/images/favorite_empty.png")));
    }

    public void setImageToFav(){
        imgFavorite.setImage(new Image(ProductItem.class.getResourceAsStream("resources/imat/images/favorite.png")));
    }
    public Label getLabelCount() {
        return labelCount;
    }

    @FXML
    private void addToCartPressed() {
        BackendController.addToCart(product, Integer.valueOf(labelCount.getText()));
        parentView.updateInfo();
        parentView.getParentController().showProductAddedToShoppingCartInfo(product, Integer.valueOf(labelCount.getText()));
    }

    @FXML private void moreInfoPressed() {
        parentView.changeToMoreInfo(product,this);
    }

    public void setParentView(ProductView parentView) {
        this.parentView = parentView;
    }
}
