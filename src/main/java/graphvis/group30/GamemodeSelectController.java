package graphvis.group30;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class GamemodeSelectController {
    @FXML Pane paneGraph;


    public void initialize() {
        GraphView graphView = new GraphView(Frontend.graph);
        paneGraph.getChildren().add(graphView.getAnchorPane());
        Frontend.graph.simulate();
        System.out.println();
    }

    public void btnGamemode1Clicked() throws IOException {
        // gamemode 1 backend
        Frontend.gameController.setGamemode(1);
        Frontend.gameController.startGame();
        Frontend.setRoot("gamescene");
    }

    public void btnGamemode2Clicked() throws IOException {
        // gamemode 2 backend
        Frontend.gameController.setGamemode(2);
        Frontend.gameController.startGame();
        Frontend.setRoot("gamescene");
        
    }

    public void btnGamemode3Clicked() throws IOException {
        // gamemode 3 backend
        Frontend.gameController.setGamemode(3);
        Frontend.gameController.startGame();
        Frontend.setRoot("gamescene");
    }

    public void btnBackClicked() throws IOException {
        Frontend.gameController.pause();
        Frontend.setRoot("mainmenu");
    }

    public void btnRegenerateGraphClicked() {
        Frontend.graph.initializeVertices();
        Frontend.graph.simulate();
        GraphView graphView = new GraphView(Frontend.graph);
        paneGraph.getChildren().addAll(graphView.getAnchorPane());
    }

    /* 
    public void setPane(Pane p) {
        paneGraph.getChildren().addAll(p.getChildren());
        System.out.println(paneGraph.getChildren().toString());
        System.out.println();
    }
    */
}
