package graphvis.group30;

import java.io.IOException;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class GamemodeSelectController {
    @FXML Pane paneGraph;


    public void initialize() {
        GraphView graphView = new GraphView(Frontend.graph);
        paneGraph.getChildren().add(graphView.getAnchorPane());
    }

    public void btnGamemode1Clicked() throws IOException {
        // gamemode 1 backend
        Frontend.gameController.setGamemode(1);
        Frontend.setRoot("gamescene");
        Frontend.graph.simulate();
    }

    public void btnGamemode2Clicked() throws IOException {
        // gamemode 2 backend
        Frontend.gameController.setGamemode(2);
        Frontend.setRoot("gamescene");
        
    }

    public void btnGamemode3Clicked() throws IOException {
        // gamemode 3 backend
        Frontend.gameController.setGamemode(3);
        Frontend.setRoot("gamescene");
    }

    public void btnBackClicked() throws IOException {
        Frontend.setRoot("mainmenu");
    }

    /* 
    public void setPane(Pane p) {
        paneGraph.getChildren().addAll(p.getChildren());
        System.out.println(paneGraph.getChildren().toString());
        System.out.println();
    }
    */
}
