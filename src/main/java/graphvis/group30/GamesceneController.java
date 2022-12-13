package graphvis.group30;

import java.io.IOException;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.transform.Scale;  


public class GamesceneController {
    static double currentZoom = 1;
    static double frameRatio = (double) 1200/700;
    static Scale scale;
    static double hVal =-1, vVal=-1;
    Group contentGroup;

    /**
     * This method is called first when the game scene fxml is loaded.
     */
    public void initialize() {

        // initialise scrollbar values
        if (hVal == -1) {
            hVal = 0.5;
        }
        if (vVal == -1) {
            vVal = 0.5;
        }
        paneGraph.setHvalue(hVal);
        paneGraph.setVvalue(vVal);

        // the following override the scroll event so that scrolling zooms instead of scrolling through the scroll pane
        paneGraph.addEventFilter(ScrollEvent.ANY, new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                onScroll(event);
                event.consume();
            }
        });
        
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

        // create a scale object to allow zooming of the graph 
        if (scale == null) {
            scale = new Scale();
            currentZoom = 1;
            scale.setX(currentZoom);
            scale.setY(currentZoom);
            Frontend.graphView.getAnchorPane().getTransforms().add(scale);
        }

        // add graph view to the graph pane
        contentGroup = new Group(Frontend.graphView.getAnchorPane());
        contentGroup.setLayoutX(1200);
        contentGroup.setLayoutY(700);
        paneGraph.setVbarPolicy(ScrollBarPolicy.NEVER);
        paneGraph.setContent(new Group(contentGroup));
        paneGraph.setFitToHeight(true);
        paneGraph.setFitToWidth(true);
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
                            switch(Frontend.gameController.getGamemode()) {
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
     * Pauses the game and displays the pause scene.
     * @throws IOException
     */
    public void btnPauseClicked() throws IOException {
        Frontend.isPaused = true;
        Frontend.setRoot("pausescene");
    }

    /**
     * Provides the user with a hint, the content of which depends on the game state
     */
    public void btnHintClicked() {
        String hint = Frontend.gameController.getHint();
        Text hintText = new Text(hint);
        Alert hintDisplay = new Alert(AlertType.INFORMATION);
        hintDisplay.setWidth(300);
        hintText.setWrappingWidth(hintDisplay.getWidth());
        hintDisplay.setTitle(null);
        hintDisplay.setHeaderText("Hint");
        hintDisplay.getDialogPane().setContent(hintText);
        hintDisplay.show();
    }
    
    /**
     * Zooms out of the graph
     */
    public void btnZoomOutClicked(){
        double oldHval = paneGraph.getHvalue();
        double oldVval = paneGraph.getVvalue();
        if(!(currentZoom <= 1)){ // prevents from zooming to far out
            currentZoom /= 1.2;
            scale.setX(currentZoom);
            scale.setY(currentZoom);
        }

        // reposition scrollbars 
        paneGraph.setHvalue(oldHval);
        paneGraph.setVvalue(oldVval);
    }

    /**
     * Zooms in on the graph
     */
    public void btnZoomInClicked(){
        double oldHval = paneGraph.getHvalue();
        double oldVval = paneGraph.getVvalue();

        currentZoom *= 1.2;
        scale.setX(currentZoom);
        scale.setY(currentZoom);

        // reposition scrollbars
        paneGraph.setHvalue(oldHval);
        paneGraph.setVvalue(oldVval);
    }

    /**
     * Scrolls in/out of the graph when the user scrolls on the graph pane.
     */
    public void onScroll(ScrollEvent event) {
        double oldHval = paneGraph.getHvalue();
        double oldVval = paneGraph.getVvalue();

        double deltaY = event.getDeltaY(); // to differentiate between scroll up and scroll down
        if (deltaY < 0) {
            if (!(currentZoom <= 1)) {
                currentZoom /= 1.05; // zoom out
            }
        } else {
                currentZoom *= 1.05; // zoom in
        }

        scale.setX(currentZoom);
        scale.setY(currentZoom);
        
        // reposition scrollbars
        paneGraph.setHvalue(oldHval);
        paneGraph.setVvalue(oldVval);

        event.consume();
    }

    /**
     * Records the position of the scrollbars when the user pans the graph pane.
     */
    public void onPaneGraphMouseReleased(MouseEvent event) {
        hVal = paneGraph.getHvalue();
        vVal = paneGraph.getVvalue();
    }


    // fxml components
    @FXML ScrollPane paneGraph;
    @FXML ColorPicker colorPicker;
    @FXML Label timerLabel;
    @FXML Label lblGraphColoured;
    @FXML AnchorPane rootPane;
}


