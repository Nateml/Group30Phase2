package graphvis.group30;

import java.io.IOException;
import java.net.URISyntaxException;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaPlayer.Status;

public class PauseSceneController {

    @FXML
    /*
     * This method is called first when the fxml for the pause scene is loaded.
     */
    public void initialize() {
        //paneGraph.getChildren().addAll(Frontend.graphView.getAnchorPane()); // add graph to display pane
        Group contentGroup = new Group(Frontend.graphView.getAnchorPane());
        paneGraph.setVbarPolicy(ScrollBarPolicy.NEVER);
        contentGroup.setLayoutX(1200);
        contentGroup.setLayoutY(700);
        paneGraph.setContent(contentGroup);
        paneGraph.setHvalue(GamesceneController.hVal);
        paneGraph.setVvalue(GamesceneController.vVal);

        // blur the graph display pane
        GaussianBlur gb = new GaussianBlur();
        gb.setRadius(30);
        paneGraph.setEffect(gb);


        sliderVolume.setValue(Frontend.mediaPlayer.getVolume()); // set volume slide

        // set the icons:
        try {
            imageVolume.setImage(new Image(getClass().getResource("volume_icon.png").toURI().toString(), 50, 50, true, true)); // set icon for the volume slider

            Image homeImage = new Image(getClass().getResource("home.png").toURI().toString());
            Image playImage = new Image(getClass().getResource("play.png").toURI().toString());

            ImageView homeImageView = new ImageView(homeImage);
            ImageView playImageView = new ImageView(playImage);

            homeImageView.setFitHeight(80);
            homeImageView.setFitWidth(70);
            playImageView.setFitHeight(80);
            playImageView.setFitWidth(70);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        // set the pause/play soundtrack icon depending on whether or not the soundtrack is playing or paused.
        if (Frontend.mediaPlayer.getStatus().equals(Status.PLAYING)) {
            // set to pause icon if soundtrack is playing
            try {
                playTrackIcon.setImage(new Image(getClass().getResource("pause_icon.png").toURI().toString()));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            // set to play icon if soundtrack is paused
            try {
                playTrackIcon.setImage(new Image(getClass().getResource("play_track_icon.png").toURI().toString()));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        try {
            // set the icon for volume slider to the muted icon if the volume is muted
            if (sliderVolume.getValue() == 0) {
                imageVolume.setImage(new Image(getClass().getResource("mute_icon.png").toURI().toString()));
            } else {
                imageVolume.setImage(new Image(getClass().getResource("volume_icon.png").toURI().toString()));
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /* 
    public void btnToggleSoundClicked() {
        if (Frontend.mediaPlayer.getVolume() == 1) Frontend.mediaPlayer.setVolume(0);
        else Frontend.mediaPlayer.setVolume(1);
    }
    */

    /**
     * Resets the game and sends the user to the main menu.
     * @throws IOException
     */
    public void btnExitClicked() throws IOException {
        GamesceneController.scale = null;
        Frontend.resetGame();
        Frontend.setRoot("mainmenu");
    }

    /**
     * Exits the pause scene and returns to the game scene.
     * @throws IOException
     */
    public void btnContinueClicked() throws IOException {
        Frontend.isPaused = false;
        Frontend.setRoot("gamescene");
    }

    /**
     * Sets the volume of the media player and sets the volume icon dependent on whether or not the volume is muted.
     */
    public void onVolumeSliderChanged() {
        Frontend.mediaPlayer.setVolume(sliderVolume.getValue()); // set volume of media player

        if (sliderVolume.getValue() == 0) { // muted icon if volume is zero
            try {
                Image mutedImage = new Image(getClass().getResource("mute_icon.png").toURI().toString());
                imageVolume.setImage(mutedImage);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } else { // regular volume icon if volume is not zero
            try {
                Image volumeImage = new Image(getClass().getResource("volume_icon.png").toURI().toString());
                imageVolume.setImage(volumeImage);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Adds a dropshadow to the home icon when the mouse cursor enters its bounds.
     */
    public void onHomeMouseEntered() {
        homeIcon.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");
    }

    /**
     * Removes the dropshadow from the home icon when the mouse cursor leaves its bounds.
     */
    public void onHomeMouseExited() {
        homeIcon.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 0, 0, 0, 0);");
    }

    /**
     * Adds a dropshadow to the play icon when the mouse cursor enters its bounds.
     */
    public void onPlayMouseEntered() {
        playIcon.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");
    }

    /**
     * Removes the dropshadow from the play icon when the mouse cursor leaves its bounds.
     */
    public void onPlayMouseExited() {
        playIcon.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 0, 0, 0, 0);");
    }

    /**
     * Adds a dropshadow to the rewind icon when the mouse cursor enters its bounds.
     */
    public void onRewindMouseEntered() {
        rewindIcon.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");

    }

    /**
     * Removes the dropshadow from the rewind icon when the mouse cursor leaves its bounds.
     */
    public void onRewindMouseExited() {
        rewindIcon.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 0, 0, 0, 0);");
    }

    /**
     * Adds a dropshadow to the forward icon when the mouse cursor enters its bounds.
     */
    public void onForwardMouseEntered() {
        forwardIcon.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");
    }

    /**
     * Removes the dropshadow from the forward icon when the mouse cursor leaves its bounds.
     */
    public void onForwardMouseExited() {
        forwardIcon.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 0, 0, 0, 0);");
    }

    /**
     * Plays the soundtrack if the media player is paused, pauses the soundtrack if the media player is playing.
     */
    public void onPlayTrackMouseClicked() {
        if (Frontend.mediaPlayer.getStatus().equals(Status.PLAYING)) { // if soundtrack is playing
            Frontend.mediaPlayer.pause(); // play soundtrack
            try {
                playTrackIcon.setImage(new Image(getClass().getResource("play_track_icon.png").toURI().toString())); // change icon
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } else { // if soundtrack is not playing
            Frontend.mediaPlayer.play(); // play soundtrack
            try {
                playTrackIcon.setImage(new Image(getClass().getResource("pause_icon.png").toURI().toString())); // change icon
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Plays the previous track by changing the current media player to the media player one index earlier in the media player list.
     */
    public void onRewindMouseClicked() {
        int previousTrackIndex = Frontend.mediaPlayers.indexOf(Frontend.mediaPlayer) - 1; 
        if (previousTrackIndex < 0) {
            Frontend.mediaPlayer.seek(Frontend.mediaPlayer.getStartTime()); // return to start of track if the current media player is the first media player in the list
        } else {
            Frontend.mediaPlayer.stop(); // stop the media player so that it does not keep playing while we play the previous media player
            Frontend.mediaPlayer = Frontend.mediaPlayers.get(previousTrackIndex); // gets previous media player
            Frontend.mediaPlayer.seek(Frontend.mediaPlayer.getStartTime()); // plays media player from beginning of the track
            Frontend.mediaPlayer.setVolume(Frontend.mediaPlayers.get(previousTrackIndex+1).getVolume()); // sets volume of media player to the volume that the user has set using the volume slider
        }
    }

    /**
     * Plays the next track by seeking to the end of the current media player's track, which results in the next media player in the list being played.
     */
    public void onForwardMouseClicked() {
        Frontend.mediaPlayer.seek(Frontend.mediaPlayer.getStopTime());
    }
    
    /**
     * Adds a dropshadow to the play soundtrack icon when the mouse cursor enters its bounds.
     */
    public void onPlayTrackMouseEntered() {
        playTrackIcon.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");
    }

    /**
     * Removes the dropshadow from the play soundtrack icon when the mouse cursor leaves its bounds.
     */
    public void onPlayTrackMouseExited() {
        playTrackIcon.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 0, 0, 0, 0);");
    }

    // fxml components:
    @FXML ScrollPane paneGraph;
    @FXML Slider sliderVolume;
    @FXML Label lblVolume;
    @FXML Button btnVolume;
    @FXML Button btnExit;
    @FXML Button btnPlay;
    @FXML ImageView imageVolume;
    @FXML ImageView playIcon;
    @FXML ImageView homeIcon;
    @FXML ImageView rewindIcon;
    @FXML ImageView forwardIcon;
    @FXML ImageView playTrackIcon;

}
