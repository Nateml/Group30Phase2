package graphvis.group30;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class PauseSceneController {

    @FXML Pane paneGraph;
    @FXML Slider sliderVolume;
    @FXML Label lblVolume;
    @FXML Button btnVolume;
    @FXML Button btnExit;
    @FXML Button btnPlay;
    @FXML ImageView imageVolume;
    @FXML ImageView playIcon;
    @FXML ImageView homeIcon;

    @FXML
    public void initialize() {
        //GraphView graphView = new GraphView(Frontend.graph);
        paneGraph.getChildren().addAll(Frontend.graphView.getAnchorPane());
        GaussianBlur gb = new GaussianBlur();
        gb.setRadius(30);
        paneGraph.setEffect(gb);

        try {
            imageVolume.setImage(new Image(getClass().getResource("volume_icon.png").toURI().toString(), 50, 50, true, true));
            Image homeImage = new Image(getClass().getResource("home.png").toURI().toString());
            Image playImage = new Image(getClass().getResource("play.png").toURI().toString());
            ImageView homeImageView = new ImageView(homeImage);
            ImageView playImageView = new ImageView(playImage);
            homeImageView.setFitHeight(80);
            homeImageView.setFitWidth(70);
            playImageView.setFitHeight(80);
            playImageView.setFitWidth(70);
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        /* 
        Image homeImage = new Image(getClass().getResource("home.png").getPath());
        Image playImage = new Image(getClass().getResource("play.png").getPath());
        ImageView homeImageView = new ImageView(homeImage);
        ImageView playImageView = new ImageView(playImage);
        homeImageView.setFitHeight(80);
        homeImageView.setFitWidth(70);
        playImageView.setFitHeight(80);
        playImageView.setFitWidth(70);
        btnExit.setGraphic(homeImageView);
        btnPlay.setGraphic(playImageView);
        */
    }

    public void btnToggleSoundClicked() {
        if (Frontend.mediaPlayer.getVolume() == 1) Frontend.mediaPlayer.setVolume(0);
        else Frontend.mediaPlayer.setVolume(1);
    }

    public void btnExitClicked() throws IOException {
        Frontend.resetGame();
        Frontend.setRoot("mainmenu");
    }

    public void btnContinueClicked() throws IOException {
        Frontend.isPaused = false;
        Frontend.setRoot("gamescene");
    }

    public void onVolumeSliderChanged() {
        Frontend.mediaPlayer.setVolume(sliderVolume.getValue());
        if (sliderVolume.getValue() == 0) {
            try {
                Image mutedImage = new Image(getClass().getResource("mute_icon.png").toURI().toString());
                imageVolume.setImage(mutedImage);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            try {
                Image volumeImage = new Image(getClass().getResource("volume_icon.png").toURI().toString());
                imageVolume.setImage(volumeImage);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    public void onHomeMouseEntered() {
        homeIcon.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");
    }

    public void onHomeMouseExited() {
        homeIcon.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 0, 0, 0, 0);");
    }

    public void onPlayMouseEntered() {
        playIcon.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");
    }

    public void onPlayMouseExited() {
        playIcon.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 0, 0, 0, 0);");
    }
}
