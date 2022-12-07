package graphvis.group30;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;

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
                    System.out.println(vertex.getSimBody().getVelocity().getLength());
                    VertexVisual v = circleToVertexMap.get((Circle)t.getSource());
                    Frontend.gameController.changeColour(vertex, Frontend.color);
                    ((Circle)t.getSource()).setFill(Color.rgb(66, 135, 245));;
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
