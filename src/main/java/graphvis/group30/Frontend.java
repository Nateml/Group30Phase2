package graphvis.group30;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// this stage includes the main menu and scenes upto the actual game

public class Frontend extends Application{

    Scene inputGraphScene;
    Scene selectGraphScene;
    @FXML Button btnCreateRandomGraph;
    @FXML Button btnInputGraphFromFile;
    static GraphVisSim visSim;
    private static Scene newScene;
    final static double WIDTH=1200;
    final static double HEIGHT=800;
    static VertexVisual[] vertices;
    static EdgeVisual[] edges;
    static Pane graphPane;
    static MyGraph graph;
    static Game gameController;
    static int color; 
    final static double GRAPH_WIDTH=1200;
    final static double GRAPH_HEIGHT=700;
    public static final String GameController = null;
    static String currentScene;
    static GraphView graphView = null;
    static ColorPicker colorPicker;
    static List<Color> usedColors = new ArrayList<>();
    static Label lblGraphColoured;
    static List<Vertex> vertexOrder = new ArrayList<>();
    public static Vertex currentVertex; 

    public static void main(String[] args){
        visSim = new GraphVisSim(WIDTH, HEIGHT);
        gameController = new Game();
        color = 0; 
        graphPane = new Pane();
        launch(args);
    }

    @Override
    public void start(Stage mainStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("mainmenu.fxml"));
        newScene = new Scene(root,WIDTH,HEIGHT);
        mainStage.setScene(newScene);

        mainStage.setTitle("Graph Coloring");
        mainStage.show();

        //Frontend.vertices = createVertexList();
    }

    public static void setRoot(String fxml) throws IOException{
        newScene.setRoot(loadFXML(fxml));

        // add key event listener to game scene and pause scene
        currentScene = fxml;
        if (currentScene.equals("gamescene") || currentScene.equals("pausescene")) {
                newScene.setOnKeyPressed((KeyEvent e) -> {
                if (e.getCode() == KeyCode.ESCAPE)
                    try {
                        if (currentScene.equals("gamescene")) {
                            // change scene
                            newScene.setRoot(loadFXML("pausescene"));
                            currentScene = "pausescene";
                        }
                        else if (currentScene.equals("pausescene")) {
                            // change scene
                            newScene.setRoot(loadFXML("gamescene"));
                            currentScene = "gamescene";
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
            });
        }
    }

    public static Parent loadFXML(String fxml) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(Frontend.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static VertexVisual[] createVertexList() {
        VertexVisual v1 = new VertexVisual(0, 1);
        VertexVisual v2 = new VertexVisual(0, 2);
        VertexVisual v3 = new VertexVisual(0, 3);
        VertexVisual v4 = new VertexVisual(0, 4);
        VertexVisual v5 = new VertexVisual(0, 5);
        VertexVisual v6 = new VertexVisual(0, 6);
        VertexVisual v7 = new VertexVisual(0, 7);
        VertexVisual v8 = new VertexVisual(0, 8);
        VertexVisual v9 = new VertexVisual(0, 9);
        VertexVisual v10 = new VertexVisual(0, 10);
        VertexVisual v11 = new VertexVisual(0, 11);
        VertexVisual v12 = new VertexVisual(0, 12);
        VertexVisual v13 = new VertexVisual(0, 13);

        v1.add_neighbour(v4);
        v1.add_neighbour(v5);
        v1.add_neighbour(v8);

        v2.add_neighbour(v3);
        v2.add_neighbour(v7);

        v3.add_neighbour(v2);
        v3.add_neighbour(v5);
        
        v4.add_neighbour(v5);
        v4.add_neighbour(v1);

        v5.add_neighbour(v1);
        v5.add_neighbour(v4);
        v5.add_neighbour(v6);
        v5.add_neighbour(v8);

        v6.add_neighbour(v5);

        v7.add_neighbour(v2);

        v8.add_neighbour(v5);
        v8.add_neighbour(v1);
        v8.add_neighbour(v9);

        v9.add_neighbour(v8);
        v9.add_neighbour(v10);

        v10.add_neighbour(v9);
        v10.add_neighbour(v11);

        v11.add_neighbour(v10);
        v11.add_neighbour(v13);

        v12.add_neighbour(v13);

        v13.add_neighbour(v11);
        v13.add_neighbour(v12);

        VertexVisual[] vertices = {v1,v2,v3,v4,v5,v6,v7,v8,v9,v10, v11, v12, v13};
        return vertices;
    }

}

