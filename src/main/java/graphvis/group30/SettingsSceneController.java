package graphvis.group30;

import java.io.IOException;
import java.net.URISyntaxException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.paint.Color;

public class SettingsSceneController {
    @FXML Slider sliderVolume;
    @FXML ImageView imageVolume;
    @FXML ImageView rewindIcon;
    @FXML ImageView playIcon;
    @FXML ImageView forwardIcon;
    @FXML Button backButton;

    public void initialize() {
        sliderVolume.setValue(Frontend.mediaPlayer.getVolume());
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

    public void onVolumeSliderChanged() {
        Frontend.mediaPlayer.setVolume(sliderVolume.getValue());
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

    public void onBackButtonClicked() throws IOException {
        Frontend.setRoot("mainmenu");
    }

    public void onPlayMouseEntered() {
        playIcon.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");
    }

    public void onPlayMouseExited() {
        playIcon.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 0, 0, 0, 0);");
    }

    public void onRewindMouseEntered() {
        rewindIcon.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");

    }

    public void onRewindMouseExited() {
        rewindIcon.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 0, 0, 0, 0);");
    }

    public void onForwardMouseEntered() {
        forwardIcon.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");
    }

    public void onForwardMouseExited() {
        forwardIcon.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 0, 0, 0, 0);");
    }

    public void onPlayMouseClicked() {
        System.out.println("current media player index: " + Frontend.mediaPlayers.indexOf(Frontend.mediaPlayer));
        if (Frontend.mediaPlayer.getStatus().equals(Status.PLAYING)) {
            Frontend.mediaPlayer.pause();
            try {
                playIcon.setImage(new Image(getClass().getResource("play_track_icon.png").toURI().toString()));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            Frontend.mediaPlayer.play();
            try {
                playIcon.setImage(new Image(getClass().getResource("pause_icon.png").toURI().toString()));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    public void onRewindMouseClicked() {
        int previousTrackIndex = Frontend.mediaPlayers.indexOf(Frontend.mediaPlayer) - 1;
        System.out.println("current media player index: " + Frontend.mediaPlayers.indexOf(Frontend.mediaPlayer));
        System.out.println(previousTrackIndex);
        if (previousTrackIndex < 0) {
            System.out.println("testinggg");
            Frontend.mediaPlayer.seek(Frontend.mediaPlayer.getStartTime());
        } else {
            Frontend.mediaPlayer.stop();
            Frontend.mediaPlayer = Frontend.mediaPlayers.get(previousTrackIndex);
            Frontend.mediaPlayer.seek(Frontend.mediaPlayer.getStartTime());
            Frontend.mediaPlayer.setVolume(Frontend.mediaPlayers.get(previousTrackIndex+1).getVolume());
        }
    }

    public void onForwardMouseClicked() {
        Frontend.mediaPlayer.seek(Frontend.mediaPlayer.getStopTime());
    }
}
