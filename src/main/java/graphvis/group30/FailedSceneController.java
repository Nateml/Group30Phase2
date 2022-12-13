package graphvis.group30;

import java.io.IOException;

/**
 * FailedSceneController
 * This class is responsible for controlling the scene that is displayed when the user fails to colour the graph correctly
 */
public class FailedSceneController {

    /**
     * Returns the user to the main menu and resets the game.
     * @throws IOException
     */
    public void btnHomeClicked() throws IOException{
        Frontend.resetGame();
        Frontend.setRoot("mainmenu");
    }    
}