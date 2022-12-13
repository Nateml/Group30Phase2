package graphvis.group30;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class GameFinishedSceneController {

    @FXML Label labelTimeFinished;
    @FXML Button toHomeButton;
    @FXML Label labelResult;

    /**
     * This method is run as soon as the GameFinishedSceneController is created (which is whenever the fxml file for the scene is loaded)
     */
    public void initialize(){
        labelTimeFinished.setText(Frontend.resultLabel1.getText() + "");
        //Frontend. = labelTimeFinished;
        labelResult.setText(Frontend.resultLabel.getText());
        Frontend.resultLabel = labelResult;
    }

    /**
     * Resets the game and changes the scene to the main menu scene
     */
    public void toHomeButtonClicked(){
        try {
            Frontend.resetGame();
            Frontend.setRoot("MainMenu");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
