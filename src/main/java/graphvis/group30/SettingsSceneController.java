package graphvis.group30;

import java.io.IOException;
import java.net.URISyntaxException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaPlayer.Status;

public class SettingsSceneController {

    /**
     * This method is called first when the fxml for the settings scene is loaded.
     */
    public void initialize() {
        sliderVolume.setValue(Frontend.mediaPlayer.getVolume()); // set volume slider to current soundtrack volume

        // change play/pause soundtrack icon depending on whether the soundtrack is playing or not
        if (Frontend.mediaPlayer.getStatus().equals(Status.PLAYING)) { 
            try {
                playIcon.setImage(new Image(getClass().getResource("pause_icon.png").toURI().toString()));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            try {
                playIcon.setImage(new Image(getClass().getResource("play_track_icon.png").toURI().toString()));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        // set volume icon
        try {
            if (sliderVolume.getValue() == 0) {
                imageVolume.setImage(new Image(getClass().getResource("mute_icon.png").toURI().toString())); // muted icon if volume is zero
            } else {
                imageVolume.setImage(new Image(getClass().getResource("volume_icon.png").toURI().toString())); // regular volume icon if volume is not zero
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * Changes the volume of the media player which plays the soundtrack.
     * This method is called whenever the volume slider is changed.
     */
    public void onVolumeSliderChanged() {
        Frontend.mediaPlayer.setVolume(sliderVolume.getValue()); // set volume of media player

        // change volume icon depending on whether or not the soundtrack is muted
        try {
            if (sliderVolume.getValue() == 0) {
                imageVolume.setImage(new Image(getClass().getResource("mute_icon.png").toURI().toString()));
            } else {
                imageVolume.setImage(new Image(getClass().getResource("volume_icon.png").toURI().toString()));
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * Exits the settings scene by sending the user back to the main menu.
     * @throws IOException
     */
    public void onBackButtonClicked() throws IOException {
        Frontend.setRoot("mainmenu");
    }

    /**
     * Adds a dropshadow to the play soundtrack icon when the mouse cursor enters its bounds.
     */
    public void onPlayMouseEntered() {
        playIcon.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");
    }

    /**
     * Removes the dropshadow from the play soundtrack icon when the mouse cursor leaves its bounds.
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
     * Plays/pauses the soundtrack.
     */
    public void onPlayMouseClicked() {
        if (Frontend.mediaPlayer.getStatus().equals(Status.PLAYING)) {
            Frontend.mediaPlayer.pause(); // pause soundtrack
            try {
                playIcon.setImage(new Image(getClass().getResource("play_track_icon.png").toURI().toString())); // sets play icon if user pauses the soundtrack
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            Frontend.mediaPlayer.play(); // plays soundtrack
            try {
                playIcon.setImage(new Image(getClass().getResource("pause_icon.png").toURI().toString())); // sets pause icon if the user plays the soundtrack
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
            Frontend.mediaPlayer.seek(Frontend.mediaPlayer.getStartTime()); // return to start of track if current media player is the first media player in the list
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

    // fxml components:
    @FXML Slider sliderVolume;
    @FXML ImageView imageVolume;
    @FXML ImageView rewindIcon;
    @FXML ImageView playIcon;
    @FXML ImageView forwardIcon;
    @FXML Button backButton;
}
