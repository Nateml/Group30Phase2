package graphvis.group30;

import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Frontend extends Application{

    public static void main(String[] args){
        gameController = new Game(); // create controller for the game

        createMediaPlayers();
        
        launch(args);
    }

    @Override
    public void start(Stage mainStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("mainmenu.fxml"));
        scene = new Scene(root,WIDTH,HEIGHT);
        mainStage.setScene(scene);

        mainStage.setTitle("Graph Coloring");
        mainStage.setResizable(false);
        mainStage.show();
    }

    /**
     * Creates media player objects for soundtrack
     */
    private static void createMediaPlayers() {
        String[] musicFiles = {
            "8bitcarti.mp3",
            "8bitcarti2.mp3",
            "8bitx.mp3",
            "8bitplasticbeach.mp3",
            "8bitghostbusters.mp3",
            "geometrydashmusic.mp3"
        };
        musicFilesList = Arrays.asList(musicFiles);
        Collections.shuffle(musicFilesList); // shuffle the order of the songs 

        mediaPlayers = new ArrayList<>();

        // add a media player object to mediaPlayers for each song in musicFiles
        for (String musicFile : musicFilesList) {
            mediaPlayers.add(new MediaPlayer(new Media(new File(musicFile).toURI().toString())));
        }

        // set each media player to play after the one before it
        for (int i = 0; i < mediaPlayers.size(); i++) {
            MediaPlayer player = mediaPlayers.get(i);
            MediaPlayer nextPlayer = mediaPlayers.get((i+1) % mediaPlayers.size());
            player.setOnEndOfMedia(new Runnable() {
                @Override
                public void run() {
                    nextPlayer.setVolume(player.getVolume());
                    Frontend.mediaPlayer = nextPlayer;
                    Frontend.mediaPlayer.seek(Frontend.mediaPlayer.getStartTime());
                    Frontend.mediaPlayer.play();
                }
            });
        }

        Frontend.mediaPlayer = mediaPlayers.get(0);
        Frontend.mediaPlayer.play();
    }


    /**
     * Changes the scene of the GUI.
     * @param fxml the name of the fxml file to load (exluding file extension)
     * @throws IOException
     */
    public static void setRoot(String fxml) throws IOException{
        scene.setRoot(loadFXML(fxml));

        // adds key event listener to the scene which only responds if the current scene is the game scene or the pause scene
        currentScene = fxml;
        if (currentScene.equals("gamescene") || currentScene.equals("pausescene")) {
                scene.setOnKeyPressed((KeyEvent e) -> {
                if (e.getCode() == KeyCode.ESCAPE) {
                    try {
                        if (currentScene.equals("gamescene")) {
                            // change scene
                            Frontend.isPaused = true;
                            scene.setRoot(loadFXML("pausescene"));
                            currentScene = "pausescene";
                        }
                        else if (currentScene.equals("pausescene")) {
                            // change scene
                            Frontend.isPaused = false;
                            scene.setRoot(loadFXML("gamescene"));
                            currentScene = "gamescene";
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            });
        }
    }

    /**
     * @param fxml
     * @return the loaded fxml scene
     * @throws IOException
     */
    public static Parent loadFXML(String fxml) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(Frontend.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * Resets game variables. Useful for when the user returns to main menu.
     */
    public static void resetGame() {
        if (timer != null) timer.stop();
        timer = null;
        isPaused = true;
        seconds = 0;
        timerLabel = null;
        vertexOrder.clear();
        usedColors.clear();
        graphView = null;
        gameController = new Game();
    }

    @FXML Button btnCreateRandomGraph;
    @FXML Button btnInputGraphFromFile;

    private static Scene scene;

    final static double WIDTH=1200;
    final static double HEIGHT=800;

    static MyGraph graph;
    static Game gameController;
    final static double GRAPH_WIDTH=1200;
    final static double GRAPH_HEIGHT=700;
    static String currentScene;
    static GraphView graphView = null;
    static ColorPicker colorPicker;
    static List<Color> usedColors = new ArrayList<>();
    static List<Vertex> vertexOrder = new ArrayList<>();
    public static VertexVisual currentVertex;
    public static Label timerLabel;
    public static Label resultLabel1 = new Label();
    public static int seconds = 0;
    public static boolean isPaused = true;
    public static Timeline timer = null;
    public static MediaPlayer mediaPlayer;
    public static List<MediaPlayer> mediaPlayers;
    public static Label resultLabel = new Label();
    public static List<String> musicFilesList;
    public static String musicFile;
}

