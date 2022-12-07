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
    

    @FXML
    public void initialize() {

    }

    @FXML
    public void btnInputGraphFromFileClicked() throws IOException {
        System.out.println("input button clicked");

        // create file chooser
        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));

        // filter only text files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt");
        chooser.getExtensionFilters().add(extFilter);

        File graphFile = chooser.showOpenDialog(null);

        if (graphFile == null) return;
        Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
        confirmationAlert.setTitle(null);
        confirmationAlert.setHeaderText("Confirm");
        confirmationAlert.setContentText("Are you sure you want to input this graph file?");
        Optional<ButtonType> confirmationResult = confirmationAlert.showAndWait();
        if (confirmationResult.get() == ButtonType.CANCEL) return;
        // Game.createGraphFromFile();
        Frontend.setRoot("gamemode_select");
    }

    @FXML
    public void btnCreateRandomGraphClicked() throws IOException{
        System.out.println("random button clicked");
        Frontend.setRoot("random_graph_input");
    }

    public void btnQuitClicked() {
        System.exit(0);
    }
}
