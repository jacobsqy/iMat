package App.Controllers;

import App.BackendController;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static App.Controllers.HistoryView.historyViews;
import static App.MainWindow.updateInfoLabels;

public class HistoryItem extends AnchorPane {

    @FXML private Label time;
    @FXML private Label day;
    @FXML private Label month;
    @FXML private Label year;
    @FXML private Label product;
    @FXML private Label price;
    @FXML private Button showProduct;
    @FXML private Button addAllProduct;

    private Order order;
    private int totalPrice;

    private String nameofmonth[] = {"Jan", "Feb", "Mar", "Apr", "Maj", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    public HistoryItem(Order order, int totalPrice){

        this.order = order;
        this.totalPrice = totalPrice;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../HistoryViews/HistoryItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        String pattern = "yyyy;M;dd;HH;mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String[] date = simpleDateFormat.format(order.getDate()).split(";");

        time.setText(date[3] + ":" + date[4]);
        day.setText(date[2]);
        month.setText(nameofmonth[Integer.parseInt(date[1])]);
        year.setText(date[0]);
        int orderSize = order.getItems().size();
        product.setText(orderSize + (orderSize > 1 ? " produkter" : " produkt"));
        price.setText(totalPrice + " kr.");

        showProduct.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                FXMLLoader loader = new FXMLLoader();
                Node node = null;
                loader.setLocation(getClass().getResource("../HistoryViews/HistoryDetailView.fxml"));
                try {
                    node = (Node)loader.load();
                    AnchorPane.setTopAnchor(node, 0.0);
                    AnchorPane.setRightAnchor(node, 0.0);
                    AnchorPane.setLeftAnchor(node, 0.0);
                    AnchorPane.setBottomAnchor(node, 0.0);
                } catch (IOException exception) {
                    throw new RuntimeException(exception);
                }
                HistoryDetailView historyDetailView = loader.getController();
                historyDetailView.updateList(order, totalPrice);
                historyDetailView.setCompenets();

                historyViews.get(0).getChildren().clear();
                historyViews.get(0).getChildren().setAll(node);
            }
        });

        addAllProduct.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                for (ShoppingItem shoppingItem:order.getItems()) {
                    BackendController.addToCart(shoppingItem.getProduct(),(int) shoppingItem.getAmount());
                }
                updateInfoLabels.get(0).setText(new DecimalFormat("#.##").format((BackendController.getTotalProductAmount())));
                updateInfoLabels.get(1).setText(new DecimalFormat("#.##").format((BackendController.getTotalPrice())));
            }
        });

    }

}
