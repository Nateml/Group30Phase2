package graphvis.group30;

import java.io.IOException;
import java.net.URISyntaxException;

import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SettingsSceneController {
    @FXML Slider sliderVolume;
    @FXML ImageView imageVolume;

    public void initialize() {
        sliderVolume.setValue(Frontend.mediaPlayer.getVolume());
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
}
