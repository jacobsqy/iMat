package App.Controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.util.Comparator;
import java.util.List;


public class HistoryProductView {

    @FXML private FlowPane historyFlowPane;

    public void initialize() {}

    public void updateList(List<Order> orders) {

        orders.sort(new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                return o2.getDate().compareTo(o1.getDate());
            }
        });

        historyFlowPane.getChildren().clear();
        boolean even = false;
        for (Order order: orders) {
            int totalPrice = 0;
            for (ShoppingItem item:order.getItems()){
                totalPrice += item.getTotal();
            }
            HistoryItem historyItem = new HistoryItem(order, totalPrice);
            historyItem.setParentView(this);
            if (even) {
                historyItem.setStyle("-fx-background-color: #ddd");
                even = false;
            } else {
                even = true;
            }
            historyFlowPane.getChildren().add(historyItem);
        }
    }

}
