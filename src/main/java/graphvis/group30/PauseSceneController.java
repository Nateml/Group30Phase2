package graphvis.group30;

import java.io.IOException;

public class PauseSceneController {
    public void btnEnableSoundClicked() {

    }

    public void btnExitClicked() throws IOException {
        Frontend.setRoot("mainmenu");
    }

    public void btnContinueClicked() throws IOException {
        Frontend.setRoot("gamescene");
    }
}
