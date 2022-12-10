package graphvis.group30;

import java.io.IOException;

/**
 * FailedSceneController
 */
public class FailedSceneController {

    public void btnHomeClicked() throws IOException{
        Frontend.resetGame();
        Frontend.setRoot("mainmenu");
    }    
}