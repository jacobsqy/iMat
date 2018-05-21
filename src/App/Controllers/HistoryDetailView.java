package App.Controllers;

import App.BackendController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static App.Controllers.HistoryView.historyViews;
import static App.MainWindow.updateInfoLabels;

public class HistoryDetailView {

    @FXML private Label time;
    @FXML private Label day;
    @FXML private Label month;
    @FXML private Label year;
    @FXML private Label product;
    @FXML private Label price;
    @FXML private FlowPane historyProductFlowPane;

    private String nameofmonth[] = {"Jan", "Feb", "Mar", "Apr", "Maj", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    private Order order;
    private int totalPrice;

    public void initialize() {}

    public void updateList(Order order, int totalPrice) {
        this.order = order;
        this.totalPrice = totalPrice;
        historyProductFlowPane.getChildren().clear();
        for (ShoppingItem item:order.getItems()) {
            HistoryDetailProductView historyDetailItem = new HistoryDetailProductView(item);
            historyProductFlowPane.getChildren().add(historyDetailItem);
        }
    }

    public void setCompenets() {

        String pattern = "yyyy;M;dd;HH;mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String[] date = simpleDateFormat.format(order.getDate()).split(";");

        time.setText(date[3] + ":" + date[4]);
        day.setText(date[2]);
        month.setText(nameofmonth[Integer.parseInt(date[1])]);
        year.setText(date[0]);

        int orderSize = order.getItems().size();
        product.setText(orderSize + (orderSize > 1 ? " produkter": " produkt"));
        price.setText(this.totalPrice + " kr.");
    }
    @FXML
    public void backButtonPressed() {
        historyViews.get(0).getChildren().clear();
        historyViews.get(0).getChildren().setAll(historyViews.get(1));
    }

    @FXML
    public void addAllProduct() {
        for (ShoppingItem shoppingItem:this.order.getItems()) {
            BackendController.addToCart(shoppingItem.getProduct(),(int) shoppingItem.getAmount());
        }
        updateInfoLabels.get(0).setText(new DecimalFormat("#.##").format((BackendController.getTotalProductAmount())));
        updateInfoLabels.get(1).setText(new DecimalFormat("#.##").format((BackendController.getTotalPrice())));
    }
}
