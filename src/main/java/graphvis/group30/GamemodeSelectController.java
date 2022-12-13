package graphvis.group30;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;

public class GamemodeSelectController {
    @FXML ScrollPane paneGraph;


    /**
     * Creates the viewer object for the graph and adds it to the pane.
     * This method is called first whenever the fxml for the gamemode selection scene is loaded.
     */
    public void initialize() {
        GraphView graphView = new GraphView(Frontend.graph);
        //paneGraph.getChildren().add(graphView.getAnchorPane());
        
        paneGraph.setContent(graphView.getAnchorPane());
        //graphView.getAnchorPane().setScaleX(2);
        //graphView.getAnchorPane().setScaleY(2);

        //Frontend.graph.simulate();
        System.out.println();
    }

    /**
     * Starts gamemode 1
     * @throws IOException
     */
    public void btnGamemode1Clicked() throws IOException {
        Frontend.gameController.bruteForceChromaticNumber(); // run the brute force algorithm so the chromatic number gets stored in the gameController object for quick access later on
        Frontend.gameController.setGamemode(1);
        Frontend.isPaused = false;
        Frontend.seconds = 0; // start time
        Frontend.setRoot("gamescene"); // switch scenes
    }

    /**
     * Starts gamemode 2
     * @throws IOException
     */
    public void btnGamemode2Clicked() throws IOException {
        Frontend.seconds = (int) Math.pow(Frontend.gameController.numberOfVertices, 1.2) * 3; // formula for calculating time limit based on number of vertices
        Frontend.gameController.setGamemode(2);
        Frontend.isPaused = false;
        Frontend.setRoot("gamescene"); // switch scenes
    }

    /**
     * Starts gamemode 3
     * @throws IOException
     */
    public void btnGamemode3Clicked() throws IOException {
        Frontend.vertexOrder = Frontend.gameController.createNewRandomOrdering(); // create random order of vertices
        Frontend.gameController.setGamemode(3);
        Frontend.seconds = 0; // start time
        Frontend.isPaused = false;
        Frontend.setRoot("gamescene"); // switch scenes
    }

    /**
     * Reset game and switch to main menu scene
     * @throws IOException
     */
    public void btnBackClicked() throws IOException {
        Frontend.resetGame(); // reset game
        Frontend.setRoot("mainmenu"); // switch scene to main menu
    }

    /**
     * Reruns the force directed graph visualisation algorithm to create a new visualisation of the graph.
     */
    public void btnRegenerateGraphClicked() {
        Frontend.graph.initializeVertices();
        Frontend.graph.simulate(); // force directed algorithm reposition vertices
        GraphView graphView = new GraphView(Frontend.graph); // create new graph view
        //paneGraph.getChildren().addAll(graphView.getAnchorPane()); // add graph view to pane
        paneGraph.setContent(graphView.getAnchorPane());
    }
}
