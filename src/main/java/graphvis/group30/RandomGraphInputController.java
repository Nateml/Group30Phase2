package graphvis.group30;

import java.io.IOException;
import java.util.Optional;

import com.fxgraph.graph.Graph;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
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

        Game game = new Game();
        game.setGraph(numEdges, numVertices, 0);
       
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
        Frontend.setRoot("mainmenu");
    }
}
