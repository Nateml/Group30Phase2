package graphvis.group30;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;

public class GraphView extends Pane {
    private final MyGraph graph;
    private final AnchorPane anchorPane = new AnchorPane(); // the display pane

    /**
     * @param graph the graph model to create a viewer for.
     */
    public GraphView(MyGraph graph) {
        this.graph = graph;
        this.getChildren().add(anchorPane);
        this.setPrefSize(Frontend.WIDTH, Frontend.HEIGHT);
        this.anchorPane.prefWidth(1200);
        this.anchorPane.setMinWidth(1200);
        this.anchorPane.setMaxWidth(1200);
        this.anchorPane.prefHeight(700);
        this.anchorPane.setMinHeight(700);
        this.anchorPane.setMaxHeight(700);
        addChildren();
    }

    /**
     * Updates the display of the graph components (vertices and edges)
     */
    public void update() {
        for (EdgeVisual edge : graph.getEdges()) {
            if (Frontend.gameController.getGamemode() == 3 && (Frontend.vertexOrder.get(0).equals(edge.getStartVertex()) || Frontend.vertexOrder.get(0).equals(edge.getEndVertex()))) {
                edge.getLine().setStroke(Color.RED); // change colour of edges of the current vertex in gamemode 3
            } else {
                edge.getLine().setStroke(Color.BLACK);
            }
            edge.getLine().setStrokeWidth(2);
        }
        for (VertexVisual vertex : graph.getVertices()) {
            vertex.getCircle().setStrokeWidth(2);
            if (Frontend.gameController.getGamemode() == 3 && Frontend.vertexOrder.get(0).equals(vertex))  {
                vertex.getCircle().setStrokeWidth(4);
                Frontend.currentVertex = vertex; 
            }
            vertex.getCircle().setStroke(Color.BLACK);
            vertex.getCircle().setStrokeType(StrokeType.OUTSIDE);
        }
    }

    /**
     * Adds the graph components (verticed and edges) to the graph view's display pane.
     */
    private void addChildren() {
        for (EdgeVisual edge : graph.getEdges()) {
            edge.getLine().setStartX(edge.getStartVertex().getSimBody().getPosition().x);
            edge.getLine().setStartY(edge.getStartVertex().getSimBody().getPosition().y);
            edge.getLine().setEndX(edge.getEndVertex().getSimBody().getPosition().x);
            edge.getLine().setEndY(edge.getEndVertex().getSimBody().getPosition().y);
            if (Frontend.gameController.getGamemode() == 3 && (Frontend.vertexOrder.get(0).equals(edge.getStartVertex())||Frontend.vertexOrder.get(0).equals(edge.getEndVertex()))) {
                edge.getLine().setStroke(Color.RED); // change colour of the edges of the current vertex in gamemode 3
            } else {
                edge.getLine().setStroke(Color.BLACK);
            }
            edge.getLine().setStrokeWidth(2);
            anchorPane.getChildren().add(edge.getLine()); // add edge to display pane
        }
        for (VertexVisual vertex : graph.getVertices()) {
            // set position of circle
            vertex.getCircle().setCenterX(vertex.getSimBody().getPosition().x);
            vertex.getCircle().setCenterY(vertex.getSimBody().getPosition().y);

            // set display properties of circle
            vertex.getCircle().setFill(Color.WHITE);
            vertex.getCircle().setStrokeWidth(2);
            vertex.getCircle().setStroke(Color.BLACK);
            vertex.getCircle().setStrokeType(StrokeType.OUTSIDE);
            vertex.getCircle().setRadius(15);
            vertex.getCircle().setVisible(true);

            // set label of vertex
            vertex.getLabel().setX(vertex.getCircle().getCenterX());
            vertex.getLabel().setY(vertex.getCircle().getCenterY());

            // change display properties of current vertex in gamemode 3
            if (Frontend.gameController.getGamemode() == 3 && Frontend.vertexOrder.get(0).equals(vertex))  {
                vertex.getCircle().setStrokeWidth(5);
                vertex.getCircle().setStroke(Color.BLACK);
                vertex.getCircle().setStrokeType(StrokeType.OUTSIDE);
            }

            anchorPane.getChildren().addAll(vertex.getCircle()); // add vertex to display pane
        }
    }

    /**
     * 
     * @return the display pane
     */
    public AnchorPane getAnchorPane() {
        return anchorPane;
    }
}
