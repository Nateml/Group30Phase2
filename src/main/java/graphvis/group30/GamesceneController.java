package graphvis.group30;

import java.io.IOException;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;


public class GamesceneController {
    VertexVisual currentVertex;
    Color[] color; 
    Color currentColor; 
    int currentColorID = -1;
    //static CountTimer timer;

    @FXML Pane paneGraph;
    @FXML ColorPicker colorPicker;
    @FXML Label timerLabel;
    @FXML Label lblGraphColoured;

    public void initialize() {
        timerLabel.setText(Frontend.seconds + "");
        Frontend.colorPicker = colorPicker;
        if (Frontend.timerLabel == null) {
            Frontend.timerLabel = timerLabel;
        } else {
            Frontend.timerLabel.setText(Frontend.seconds + "");
        }
        Frontend.lblGraphColoured = lblGraphColoured;
        if (Frontend.timer == null) {
            createTimer();
        } else {
            Frontend.timer.stop();
            Frontend.timerLabel = timerLabel;
            createTimer();
        }
        if (Frontend.graphView == null) {
            Frontend.graphView = new GraphView(Frontend.graph);
            //timer.runTimer();
        } else {
            Frontend.graphView.update();
        }
        paneGraph.getChildren().add(Frontend.graphView.getAnchorPane());
        //timer.setPause(false);
        /* 
        paneGraph.setOnKeyPressed((KeyEvent e) -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                System.out.println("Escape clicked");
                try {
                    btnPauseClicked();

                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                };
            }
        });
        */
    }

    public static void setLabel() {
        Frontend.timerLabel.setText(Frontend.seconds+ "");
    }

    public void createTimer() {
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(
            new KeyFrame(Duration.seconds(1),
            new EventHandler() {
                @Override
                public void handle(Event event) {
                    if (!Frontend.isPaused) {
                        System.out.println("running");
                        switch(Frontend.gameController.gamemode) {
                            case 1:
                                Frontend.seconds++;
                                break;
                            case 2:
                                Frontend.seconds--;
                                break;
                            case 3:
                                Frontend.seconds++;
                        }
                        timerLabel.setText(Frontend.seconds + "");
                        Frontend.timerLabel.setText(Frontend.seconds + "");
                        if (Frontend.seconds <= 0) {
                            timeline.stop();
                            try {
                                Frontend.setRoot("failedscene");
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
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

    /* 

    public static void initializeTimer(){
        switch(Frontend.gameController.gamemode){
            case 1 :
                timer = new CountTimer(0,0,false);
                break;
            case 2:
                timer = new CountTimer(0, 0, true); // get the time from the method
                break;
            case 3:
                timer = new CountTimer(0,0,false);
                break;
            }
        }

        */

    public void btnPauseClicked() throws IOException {
        //Frontend.gameController.pause();
        Frontend.isPaused = true;
        Frontend.setRoot("pausescene");
    }

    public void btnHintClicked() {
        String hint = Frontend.gameController.getHint();
        Alert hintDisplay = new Alert(AlertType.INFORMATION);
        hintDisplay.setTitle(null);
        hintDisplay.setHeaderText("Hint");
        hintDisplay.setContentText(hint);
        hintDisplay.show();
    }
    
    public void btnSelectColourClicked() {
       /* 
        
        currentColor = Color.ORANGE; //needs to match the input of the color picker  
        boolean colorNotUsed = true; 
        for (int i = 0; i < color.length; i++) {
            if (color[i]==currentColor) {
                currentColorID=i; 
                colorNotUsed = false; 
            } 
        }
        if (colorNotUsed) {
            Color[] newColor = new Color[color.length + 1];
        for(int i = 0; i < color.length ; i++){
            newColor[i] = color[i];
        }
        newColor[color.length] = currentColor;
        color = newColor; 
        currentColorID = newColor.length; 
        }
        }
        

        
    
        public void btnSlectVertexClicked(){
        if (Frontend.gameController.gamemode == 1 || Frontend.gameController.gamemode == 2) {
            Frontend.gameController.setCurrentVertex(Frontend.currentVertex);
            if(currentColorID==-1){
            String error = "Select a color"; 
            Alert errorDisplay = new Alert(AlertType.INFORMATION);
            errorDisplay.setTitle(null);
            errorDisplay.setHeaderText("No color selected");
            errorDisplay.setContentText(error);
            } else {
                for (int i = 0; i < color.length; i++) {
                    if (color[i]==currentColor) {
                    Frontend.gameController.changeColour(Frontend.currentVertex, currentColorID); 
                    Frontend.currentVertex.setColour(color[i]); 
                    }
                }   
            }
        } else {
              
            String error = "You can not slect which vertex you want to color in this gamemode, please select the color you wish the highlighted vertex to have";
            Alert errorDisplay = new Alert(AlertType.INFORMATION);
            errorDisplay.setTitle(null);
            errorDisplay.setHeaderText("GameMode 3");
            errorDisplay.setContentText(error);
        }
           
        */
    }    
}


