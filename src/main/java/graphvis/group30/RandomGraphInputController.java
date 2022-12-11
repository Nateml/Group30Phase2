package graphvis.group30;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RandomGraphInputController {

    Stage stage;
    Scene scene;
    Parent root;
   
    @FXML
    TextField txfEdges;
    @FXML
    TextField txfVertices;
    @FXML

    
    public void btnGenerateGraphClicked(ActionEvent event) throws IOException {
        // call generate graph

        // check integers
        int numEdges = 0;
        int numVertices = 0;
        try {
            numEdges = Integer.parseInt(txfEdges.getText());
            numVertices = Integer.parseInt(txfVertices.getText());
        } catch (NumberFormatException e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("");
            errorAlert.setContentText("Please input a valid number");
            errorAlert.showAndWait();
            return;
        }
        
        if (numEdges < 0 || numVertices < 0) {
            Alert negativeNumberAlert = new Alert(Alert.AlertType.ERROR);
            negativeNumberAlert.setTitle("Error");
            negativeNumberAlert.setHeaderText("");
            negativeNumberAlert.setContentText("Cannot create a graph with a negative number of vertices or edges");
            negativeNumberAlert.showAndWait();
            return;
        }

        int maxEdges = (numVertices * (numVertices-1))/2;
        int minEdges = numVertices - 1;
        if (numEdges > maxEdges) {
            txfEdges.setText(maxEdges + "");
            return;
        } else if (numEdges < minEdges) {
            txfEdges.setText(minEdges + "");
            return;
        }

        Frontend.gameController.setGraph(numVertices, numEdges, numVertices); 
        Frontend.graph = new MyGraph(Frontend.gameController.graphForGame.vertices, Frontend.GRAPH_WIDTH, Frontend.GRAPH_HEIGHT);
        Frontend.graph.simulate();
        /* 
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        */

        //FXMLLoader loader = new FXMLLoader(getClass().getResource("graph_pane.fxml"));
        //root = loader.load();
        //GraphPaneController controller = loader.getController();
        //controller.createRandomGraph(numVertices, numEdges);

        Frontend.setRoot("gamemode_select");

    }

    public void btnBackClicked(ActionEvent event) throws IOException {
        // switch scenes to main menu
        /* 
        Parent root = FXMLLoader.load(getClass().getResource("mainmenu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        */
        Frontend.resetGame();
        Frontend.setRoot("mainmenu");
    }

    public void txfEdgesOnKeyTyped() {
        if (txfEdges.getLength() == 0) return;
        try {
            int numEdges = Integer.parseInt(txfEdges.getText());
        } catch (NumberFormatException e) {
            txfEdges.setText(txfEdges.getText().substring(0, txfEdges.getText().length()-1));
        }
    }

    public void txfVerticesOnKeyTyped() {
        if (txfVertices.getLength() == 0) return;
        try {
            int numVertices = Integer.parseInt(txfVertices.getText());
        } catch (NumberFormatException e) {
            txfVertices.setText(txfVertices.getText().substring(0, txfVertices.getText().length()-1));
        }
    }
}
