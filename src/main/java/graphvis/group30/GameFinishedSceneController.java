package graphvis.group30;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class GameFinishedSceneController {

    @FXML Label TimerLabel;
    @FXML Button toHomeButton;

    public void initialize(){
        Frontend.timerLabel = TimerLabel;
    }

    public void toHomeButtonClicked(){
        try {
            Frontend.setRoot("MainMenu");
        } catch (IOException e) {}
    }
    
}
