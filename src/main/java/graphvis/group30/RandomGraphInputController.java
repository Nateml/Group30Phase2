package graphvis.group30;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class RandomGraphInputController {

    /**
     * Generates a random graph from the number of vertices and edges inputed into the respective text fields.
     * @param event the generate graph button's action event
     * @throws IOException
     */
    public void btnGenerateGraphClicked(ActionEvent event) throws IOException {
        // input validation:
        int numEdges = 0;
        int numVertices = 0;
        try {
            numEdges = Integer.parseInt(txfEdges.getText());
            numVertices = Integer.parseInt(txfVertices.getText());
        } catch (NumberFormatException e) { // this is run if the user does not input a value which can be converted to an integer
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("");
            errorAlert.setContentText("Please input a valid number");
            errorAlert.showAndWait();
            return;
        }
        if (numEdges < 0 || numVertices < 0) { // negative numbers are invalid
            Alert negativeNumberAlert = new Alert(Alert.AlertType.ERROR);
            negativeNumberAlert.setTitle("Error");
            negativeNumberAlert.setHeaderText("");
            negativeNumberAlert.setContentText("Cannot create a graph with a negative number of vertices or edges");
            negativeNumberAlert.showAndWait();
            return;
        }

        // check if the inputed values are valid for an undirected, connected graph.
        int maxEdges = (numVertices * (numVertices-1))/2;
        int minEdges = numVertices - 1;
        if (numEdges > maxEdges) {
            txfEdges.setText(maxEdges + "");
            return;
        } else if (numEdges < minEdges) {
            txfEdges.setText(minEdges + "");
            return;
        }

        // create graph
        Frontend.gameController.setGraph(numVertices, numEdges); 
        Frontend.graph = new MyGraph(Frontend.gameController.getGraph().getVertices(), Frontend.GRAPH_WIDTH, Frontend.GRAPH_HEIGHT);
        Frontend.graph.simulate(); // force-directed algorithm

        Frontend.setRoot("gamemode_select"); // send user to the gamemode selection scene

    }

    /**
     * Resets the game and sends the user to the main menu.
     * @param event the back button's action event
     * @throws IOException
     */
    public void btnBackClicked(ActionEvent event) throws IOException {
        Frontend.resetGame();
        Frontend.setRoot("mainmenu");
    }

    /**
     * Check if the character inputted into the edges text field can be converted into an integer)
     * This method is called whenever a key is typed in the text field for the number of edges.
     */
    public void txfEdgesOnKeyTyped() {
        if (txfEdges.getLength() == 0) return; // do nothing if there are no characters in the text field (because a backspace can trigger this method)

        // try to cast the text to an integer:
        try {
            int numEdges = Integer.parseInt(txfEdges.getText());
        } catch (NumberFormatException e) {
            txfEdges.setText(txfEdges.getText().substring(0, txfEdges.getText().length()-1)); // remove the character from the text field
        }
    }

    /**
     * Checks if the characters inputed into the vertices text field can be converted into an integers.
     * This method is called whenever a key is typed in the text field for the number of vertices.
     */
    public void txfVerticesOnKeyTyped() {
        if (txfVertices.getLength() == 0) return; // do nothing if there are no characters in the text field (because a backspace can trigger this method)

        // try to cast the text to an integer:
        try {
            int numVertices = Integer.parseInt(txfVertices.getText());
        } catch (NumberFormatException e) {
            txfVertices.setText(txfVertices.getText().substring(0, txfVertices.getText().length()-1)); // remove the character from the text field
        }
    }

    // fxml components:
    @FXML TextField txfEdges;
    @FXML TextField txfVertices;
}
