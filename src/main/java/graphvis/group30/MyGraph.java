package graphvis.group30;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import java.io.IOException;
import java.util.HashMap;

public class MyGraph {
    VertexVisual[] vertices;
    EdgeVisual[] edges;
    GraphVisSim visSim;
    double width, height;
    HashMap<Integer, VertexVisual> vertexMap;
    HashMap<Circle, VertexVisual> circleToVertexMap;

    public MyGraph(Vertex[] vertices, double width, double height) {
        vertexMap = new HashMap<>();
        circleToVertexMap = new HashMap<>();
        this.vertices = new VertexVisual[vertices.length];
        for (int i = 0; i < vertices.length; i++) {
           VertexVisual vertexVisual = new VertexVisual(0, vertices[i].identification());
           vertexMap.put(vertices[i].identification(), vertexVisual);
           circleToVertexMap.put(vertexVisual.getCircle(), vertexVisual);
           this.vertices[i] = vertexVisual;
        }
        for (Vertex vertex : vertices) {
            VertexVisual vertexVisual = vertexMap.get(vertex.i);
            for (int neighbour : vertex.getNeighboursAsIntArray()) {
                vertexVisual.add_neighbour(vertexMap.get(neighbour));
            }
        }

        //visSim = new GraphVisSim(width, height);
        this.width = width;
        this.height = height;
        initializeVertices();
        edges = createEdgeList();
    }

    private EdgeVisual[] createEdgeList() {
        EdgeVisual[] edges = new EdgeVisual[0];
        for (VertexVisual v1 : vertices) {
            for (Vertex v2 : v1.getNeighboursAsVertexArray()) {
                if (v1.equals(v2)) continue;
                EdgeVisual edge = new EdgeVisual(v1, (VertexVisual)v2);
                boolean edgeExists = false;
                for (EdgeVisual checkEdge : edges) {
                    if (checkEdge.equals(edge)) {
                        edgeExists = true;
                        break;
                    }
                }

                if (!edgeExists) {
                    EdgeVisual[] newEdges = new EdgeVisual[edges.length+1];
                    System.arraycopy(edges, 0, newEdges, 0, edges.length);
                    newEdges[newEdges.length-1] = edge;
                    edges = newEdges;
                }
            }
        }

        Frontend.edges = edges;
        return edges;
    }

    public void initializeVertices() {
        visSim = new GraphVisSim(width, height);
        for (VertexVisual vertex : vertices) {
            //double randX = (Math.random() * (width/4)) + width/4;
            double randX = Math.random() * width;
            //double randY = (Math.random() * (height/4)) + height/4;
            double randY = Math.random() * height;
            //vertex.setPosition(new Vector((Math.random() * (scene.getWidth()-scene.getWidth()/2)) + scene.getWidth()/2, (Math.random() * (scene.getHeight()-scene.getHeight()/2))+scene.getHeight()/2));
            vertex.getSimBody().setPosition(new Vector(randX, randY));
            vertex.getCircle().setOnMouseClicked((t) -> {
                if (Frontend.gameController.isGameRunning()) {
                    VertexVisual v = circleToVertexMap.get((Circle)t.getSource());
                    Frontend.currentVertex = v; 
                    if (((Frontend.gameController.gamemode == 3 && Frontend.vertexOrder.get(0).equals(v))) || Frontend.gameController.gamemode != 3) {
                        System.out.println("colour: " + Frontend.colorPicker.getValue());
                        if (!Frontend.usedColors.contains(Frontend.colorPicker.getValue())) {
                            Frontend.usedColors.add(Frontend.colorPicker.getValue());
                        }
                        Frontend.gameController.changeColour(vertex, Frontend.usedColors.indexOf(Frontend.colorPicker.getValue()));
                        boolean legallyColoured = Frontend.gameController.isLegalColouring(Frontend.gameController.vertexcolouring);
                        if (legallyColoured){
                            Frontend.lblGraphColoured.setVisible(true);
                            // add a switch to check if the correct amount of colors are used for every gamemode
                            if(Frontend.usedColors.size() ==  Frontend.gameController.bruteForceChromaticNumber()){
                                Frontend.timer.stop();
                                if(Frontend.gameController.gamemode == 1 || Frontend.gameController.gamemode == 3){
                                    try {
                                        Frontend.timer.stop();
                                        Frontend.timerLabel.setText("In " + Frontend.seconds + " seconds!");
                                        Frontend.setRoot("GameFinishedScene");
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    Frontend.timer.stop();
                                    Frontend.timerLabel.setText("With " + Frontend.seconds + " seconds left!");
                                    try {
                                        Frontend.setRoot("GameFinishedScene");
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                        else {
                            Frontend.lblGraphColoured.setVisible(false);
                            System.out.println("legally coloured? " + legallyColoured);
                            Circle circle = (Circle)t.getSource();
                            circle.setFill(Frontend.colorPicker.getValue());
                            if (Frontend.gameController.gamemode == 3) {
                                Frontend.vertexOrder.remove(0);
                                /* 
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("gamescene.fxml"));
                                try {
                                    Parent root = loader.load();
                                } catch (IOException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                                GamesceneController gamesceneController = loader.getController();
                                gamesceneController.updateGraphView();
                                */
                                if (Frontend.vertexOrder.size() == 0 && !Frontend.gameController.isLegalColouring(Frontend.gameController.vertexcolouring)) {
                                    try {
                                        Frontend.graph = null;
                                        Frontend.setRoot("mainmenu");
                                    } catch (IOException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                } else{
                                    try {
                                        Frontend.setRoot("gamescene");
                                    } catch (IOException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                }
            });
            visSim.addSimBody(vertex);
        }
    }

    
    public void simulate() {
        //visSim = new GraphVisSim(width, height);
        boolean isSimFinished = visSim.run();
        while (!isSimFinished) {
            isSimFinished = visSim.run();
        }
    }

    public VertexVisual[] getVertices() {
        return vertices;
    }

    public EdgeVisual[] getEdges() {
        return edges;
    }
}

