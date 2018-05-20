package App.Controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.List;

public class HistoryView {

    @FXML private AnchorPane contentPane;
    @FXML private AnchorPane historyProductView;
    @FXML private AnchorPane historyDetailView;

    public static List<AnchorPane> historyViews = new ArrayList<AnchorPane>();
    public void initialize() {

        historyViews.add(contentPane);
        historyViews.add(historyProductView);
        historyViews.add(historyDetailView);

        contentPane.getChildren().setAll(historyProductView);

    }
}
