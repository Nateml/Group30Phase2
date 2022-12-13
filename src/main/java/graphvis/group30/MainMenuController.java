package graphvis.group30;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;


public class MainMenuController {
    
    /* 
    @FXML
    public void initialize() {

    }
    */

    @FXML
    /**
     * Allows user to input a graph from a text file and then calls the appropriate methods to create the graph.
     * Sends user to gamemode selection screen.
     * @throws IOException
     */
    public void btnInputGraphFromFileClicked() throws IOException {
        // create file chooser
        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));

        // filter only text files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt");
        chooser.getExtensionFilters().add(extFilter);

        File graphFile = chooser.showOpenDialog(null); // show file chooser

        if (graphFile == null) return; // exit the method if the user does not select a graph

        // show an alert to make the user confirm that they want to use the selected file
        Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
        confirmationAlert.setTitle(null);
        confirmationAlert.setHeaderText("Confirm");
        confirmationAlert.setContentText("Are you sure you want to input this graph file?");
        Optional<ButtonType> confirmationResult = confirmationAlert.showAndWait();
        if (confirmationResult.get() == ButtonType.CANCEL) return; // exit the method if the user click cancel

        // create graph
        Frontend.gameController.setGraphFromFile(graphFile);
        Frontend.graph = new MyGraph(Frontend.gameController.graphForGame.vertices, Frontend.GRAPH_WIDTH, Frontend.GRAPH_HEIGHT);
        Frontend.graph.simulate();

        Frontend.setRoot("gamemode_select"); // send to gamemode selection screen
    }

    @FXML
    /*
     * Sends the user to the random graph input scene.
     */
    public void btnCreateRandomGraphClicked() throws IOException{
        Frontend.setRoot("random_graph_input");
    }

    /*
     * Quits the program.
     */
    public void btnQuitClicked() {
        System.exit(0);
    }

    /*
     * Opens the settings menu.
     */
    public void btnSettingsClicked() throws IOException {
        Frontend.setRoot("settingsscene");
    }
}
