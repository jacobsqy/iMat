package App.Controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;

import static App.Controllers.HistoryView.historyViews;

public class HistoryDetailView {

    @FXML private FlowPane historyProductFlowPane;

    public void initialize() {

        historyProductFlowPane.getChildren().clear();
        for (int i = 0; i < 10; i++) {
            HistoryDetailProductView historyDetailItem = new HistoryDetailProductView();
            historyProductFlowPane.getChildren().add(historyDetailItem);
        }
    }

    @FXML
    public void backButtonPressed() {
        historyViews.get(0).getChildren().clear();
        historyViews.get(0).getChildren().setAll(historyViews.get(1));
    }
}
