package App.Controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;

public class HistoryProductView {

    @FXML private FlowPane historyFlowPane;
    //public static List<HistoryItem> historyItemList = new ArrayList<HistoryItem>();

    public void initialize() {

        historyFlowPane.getChildren().clear();
        for (int i = 0; i < 10; i++) {
            HistoryItem historyItem = new HistoryItem();
            //historyItemList.add(historyItem);
            historyFlowPane.getChildren().add(historyItem);
        }
    }

}
