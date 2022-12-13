package graphvis.group30;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.*;

import java.io.IOException;
import java.util.HashMap;

public class MyGraph {
    private static VertexVisual[] vertices;
    private EdgeVisual[] edges;
    private GraphVisSim visSim;
    private double width, height;
    private HashMap<Integer, VertexVisual> vertexMap;
    private HashMap<Circle, VertexVisual> circleToVertexMap; // this hashmap is useful for retrieving the vertex object from it's circle object in the circle's mouse click event handler.

    /**
     * @param vertices The vertex representation of the graph
     * @param width The width of the area in which the graph will be displayed
     * @param height The height of the area in which the graph will be displayed
     */
    public MyGraph(Vertex[] vertices, double width, double height) {
        vertexMap = new HashMap<>();
        circleToVertexMap = new HashMap<>();
        this.vertices = new VertexVisual[vertices.length];

        // populate vertexMap and circleToVertexMap
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

        this.width = width;
        this.height = height;
        initializeVertices();
        edges = createEdgeList();
    }

    /**
     * Creates an array of EdgeVisual objects which represents the edges between connected vertices
     * @return the edge array which has been created.
     */
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

        return edges;
    }

    /**
     * Handles a mouse click event on a vertex.
     * @param t
     */
    private void vertexOnMouseClicked(MouseEvent t) {
        if (!Frontend.isPaused) {
            for (VertexVisual v : vertices) {
                v.getCircle().setStrokeWidth(2);  
            }
           

            VertexVisual v = circleToVertexMap.get((Circle)t.getSource()); // get vertex object from mouse event
            Frontend.currentVertex = v; 

            // do nothing if the user does not click on the next vertex in the random ordering in gamemode 3
            if (Frontend.gameController.getGamemode() == 3 && !v.equals(Frontend.vertexOrder.get(0))) {
                try {
                    Frontend.setRoot("gamescene"); // refresh game scene
                } catch (IOException e) {
                    e.printStackTrace();
                } 
                return;
            }

            // keep track of the colours currently being used to color the graph
            if (!Frontend.usedColors.contains(Frontend.colorPicker.getValue())) {
                Frontend.usedColors.add(Frontend.colorPicker.getValue());
            }

            Frontend.gameController.changeColour(v, Frontend.usedColors.indexOf(Frontend.colorPicker.getValue())); // colour the vertex
            boolean legallyColoured = Frontend.gameController.isLegalColouring(); // check if graph is legally coloured 
            if (legallyColoured && Frontend.gameController.allVerticesColoured()){
                // conditions for the game to end:
                switch (Frontend.gameController.getGamemode()) {
                    case 1:
                        if (Frontend.gameController.getProgress() == Frontend.gameController.bruteForceChromaticNumber()) {
                            Frontend.timer.stop();
                            try {
                                Frontend.resultLabel1.setText("in " + Frontend.seconds + " seconds!");
                                Frontend.resultLabel.setText("");
                                Frontend.setRoot("GameFinishedScene");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Circle circle = (Circle)t.getSource();
                            circle.setFill(Frontend.colorPicker.getValue());
                        }
                        break;
                    case 2:
                        Frontend.timer.stop();
                        Frontend.resultLabel1.setText("with " + Frontend.seconds + " seconds left!");
                        break;
                    case 3:
                        Frontend.timer.stop();
                        Frontend.resultLabel1.setText("in " + Frontend.seconds + " seconds!");
                        break;
                }
                if (Frontend.gameController.getGamemode() != 1) {
                    String text = "You used " + Frontend.gameController.getProgress() + " colours, ";
                    if (Frontend.gameController.getProgress() == Frontend.gameController.bruteForceChromaticNumber()) {
                        text += "which is the least amount of colours you could have used!";
                    } else {
                        text += "but you could have coloured with " + Frontend.gameController.bruteForceChromaticNumber() +  " colours";
                    }
                    Frontend.resultLabel.setText(text);
                    try {
                        Frontend.timer.stop();
                        Frontend.setRoot("GameFinishedScene");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                Circle circle = (Circle)t.getSource();
                circle.setFill(Frontend.colorPicker.getValue()); // set display colour of the vertex
                if (Frontend.gameController.getGamemode() == 3) {
                    // stop the game as soon as the user incorrectly colours a vertex, or there are no more vertices left to colour
                    if (!Frontend.gameController.isLegalColouring() || Frontend.vertexOrder.size() == 1) {
                        Frontend.timer.stop();
                        try {
                            Frontend.setRoot("failedscene");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Frontend.vertexOrder.remove(0);
                        try {
                            Frontend.setRoot("gamescene"); // refresh game scene so that the next vertex in the random ordering is outlined
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                }
            }
    }

    /**
     * Sets initial position of the vertices, add the vertices to the visualisation simulation and sets the mouse clicked event handler for the vertices.
     */
    public void initializeVertices() {
        visSim = new GraphVisSim(width, height);
        for (VertexVisual vertex : vertices) {
            double randX = Math.random() * width;
            double randY = Math.random() * height;
            vertex.getSimBody().setPosition(new Vector(randX, randY));
            vertex.getCircle().setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    vertexOnMouseClicked(event);
                }
            });

            visSim.addSimBody(vertex);
        }
    }

    /**
     * Runs the force-directed algorithm responsible for the layout of the graph.
     */
    public void simulate() {
        boolean isSimFinished = visSim.run();
        while (!isSimFinished) {
            isSimFinished = visSim.run();
        }
    }

    /**
     * @return the vertex array
     */
    public VertexVisual[] getVertices() {
        return vertices;
    }

    /**
     * @return the edges array
     */
    public EdgeVisual[] getEdges() {
        return edges;
    }
}

