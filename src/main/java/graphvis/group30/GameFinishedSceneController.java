package graphvis.group30;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class GameFinishedSceneController {

    @FXML Label labelTimeFinished;
    @FXML Button toHomeButton;
    @FXML Label labelResult;

    public void initialize(){
        labelTimeFinished.setText(Frontend.timerLabel.getText() + "");
        Frontend.timerLabel = labelTimeFinished;
        labelResult.setText(Frontend.resultLabel.getText());
        Frontend.resultLabel = labelResult;
    }

    public void toHomeButtonClicked(){
        try {
            Frontend.resetGame();
            Frontend.setRoot("MainMenu");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
