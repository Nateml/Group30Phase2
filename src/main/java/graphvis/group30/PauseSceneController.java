package graphvis.group30;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.Pane;

public class PauseSceneController {

    @FXML Pane paneGraph;

    @FXML
    public void initialize() {
        //GraphView graphView = new GraphView(Frontend.graph);
        paneGraph.getChildren().addAll(Frontend.graphView.getAnchorPane());
        GaussianBlur gb = new GaussianBlur();
        paneGraph.setEffect(gb);
    }

    public void btnEnableSoundClicked() {

    }

    public void btnExitClicked() throws IOException {
        Frontend.graphView = null;
        Frontend.setRoot("mainmenu");
    }

    public void btnContinueClicked() throws IOException {
        Frontend.isPaused = false;
        Frontend.setRoot("gamescene");
    }
}
