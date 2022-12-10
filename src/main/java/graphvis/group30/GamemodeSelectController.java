package graphvis.group30;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class GamemodeSelectController {
    @FXML Pane paneGraph;


    public void initialize() {
        GraphView graphView = new GraphView(Frontend.graph);
        paneGraph.getChildren().add(graphView.getAnchorPane());
        //Frontend.graph.simulate();
        System.out.println();
    }

    public void btnGamemode1Clicked() throws IOException {
        // gamemode 1 backend
        Frontend.gameController.bruteForceChromaticNumber();
        Frontend.gameController.setGamemode(1);
        Frontend.gameController.startGame();
        Frontend.setRoot("gamescene");
        Frontend.isPaused = false;
        Frontend.seconds = 0;
        //GamesceneController.initializeTimer();
    }

    public void btnGamemode2Clicked() throws IOException {
        // gamemode 2 backend
        Frontend.seconds = (int) Math.pow(Frontend.gameController.numberOfVertices, 1.2) * 3;
        Frontend.gameController.setGamemode(2);
        Frontend.gameController.startGame();
        Frontend.setRoot("gamescene");
        Frontend.isPaused = false;
        //GamesceneController.initializeTimer();
    }

    public void btnGamemode3Clicked() throws IOException {
        // gamemode 3 backend
        Frontend.seconds = 0;
        Frontend.gameController.setGamemode(3);
        Frontend.gameController.startGame();
        Frontend.vertexOrder = Frontend.gameController.getRandomOrdering();
        System.out.println("Vertex order: " + Frontend.vertexOrder.toString());
        //GamesceneController.initializeTimer();
        Frontend.isPaused = false;
        Frontend.setRoot("gamescene");
    }

    public void btnBackClicked() throws IOException {
        Frontend.gameController.pause();
        Frontend.isPaused = true;
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
