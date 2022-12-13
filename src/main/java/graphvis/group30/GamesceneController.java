package graphvis.group30;

import java.io.IOException;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import javafx.scene.control.Alert;


public class GamesceneController {
    @FXML Pane paneGraph;
    @FXML ColorPicker colorPicker;
    @FXML Label timerLabel;
    @FXML Label lblGraphColoured;

    /**
     * This method is called first when the game scene fxml is loaded.
     */
    public void initialize() {
        timerLabel.setText(Frontend.seconds + "");

        if (Frontend.colorPicker != null) {
            colorPicker.setValue(Frontend.colorPicker.getValue()); // this is so the value in the color picker does not reset to white every time the scene is reloaded
        }
        Frontend.colorPicker = colorPicker;

        // create timer for the game
        if (Frontend.timer == null) {
            createTimer();
        } else {
            Frontend.timer.stop(); // if there is already a timer, we need to stop the old one so that it does not continue to run
            createTimer();
        }

        // create the viewer object for the graph
        if (Frontend.graphView == null) {
            Frontend.graphView = new GraphView(Frontend.graph);
        } else {
            Frontend.graphView.update();
        }

        paneGraph.getChildren().add(Frontend.graphView.getAnchorPane()); // add graph view to pane
    }


    /**
     * Creates the timer for the game. 
     * In gamemode 1 and 3, the timer counts up. In gamemode 2, the timer counts down. 
     */
    public void createTimer() {
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(
            new KeyFrame(Duration.seconds(1),
                new EventHandler<>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (!Frontend.isPaused) { // only run timer if the game is not paused
                            switch(Frontend.gameController.gamemode) {
                                case 1:
                                case 3:
                                    Frontend.seconds++; // count up
                                    break;
                                case 2:
                                    Frontend.seconds--; // count down
                                    break;
                            }
                            timerLabel.setText(Frontend.seconds + ""); // update the label which displays the time in seconds
                            if (Frontend.seconds <= 0) {
                                timeline.stop();
                                try {
                                    Frontend.setRoot("failedscene");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                    }
                }
            )
        );
        timeline.playFromStart();
        Frontend.timer = timeline;
    }

    
    /** 
     * @throws IOException
     */
    public void btnPauseClicked() throws IOException {
        Frontend.isPaused = true;
        Frontend.setRoot("pausescene");
    }

    /**
     * 
     */
    public void btnHintClicked() {
        String hint = Frontend.gameController.getHint();
        Alert hintDisplay = new Alert(AlertType.INFORMATION);
        hintDisplay.setTitle(null);
        hintDisplay.setHeaderText("Hint");
        hintDisplay.getDialogPane().setContentText(hint);
        System.out.println(hint);
        hintDisplay.show();
    }
    
}


