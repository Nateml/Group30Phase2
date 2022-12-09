package graphvis.group30;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;

public class GraphView extends Pane {
    private final MyGraph graph;
    private final BorderPane anchorPane = new BorderPane(); 

    public GraphView(MyGraph graph) {
        this.graph = graph;
        this.getChildren().add(anchorPane);
        this.setPrefSize(Frontend.WIDTH, Frontend.HEIGHT);
        this.anchorPane.prefWidthProperty().bind(this.widthProperty());
        this.anchorPane.prefHeightProperty().bind(this.heightProperty());
        addChildren();
    }

    public void update() {
        for (EdgeVisual edge : graph.getEdges()) {
            edge.getLine().setStroke(Color.BLACK);
            edge.getLine().setStrokeWidth(3);
        }
        for (VertexVisual vertex : graph.getVertices()) {
            if (Frontend.gameController.gamemode == 3 && Frontend.vertexOrder.get(0).equals(vertex))  {
                vertex.getCircle().setStrokeWidth(4);
                vertex.getCircle().setStroke(Color.BLACK);
                vertex.getCircle().setStrokeType(StrokeType.OUTSIDE);
            }
        }
    }

    private void addChildren() {
        System.out.println();
        for (EdgeVisual edge : graph.getEdges()) {
            edge.getLine().setStartX(edge.getStartVertex().getSimBody().getPosition().x);
            edge.getLine().setStartY(edge.getStartVertex().getSimBody().getPosition().y);
            edge.getLine().setEndX(edge.getEndVertex().getSimBody().getPosition().x);
            edge.getLine().setEndY(edge.getEndVertex().getSimBody().getPosition().y);
            if (Frontend.currentVertex == edge.getStartVertex()||Frontend.currentVertex == edge.getEndVertex()) {
                edge.getLine().setStroke(Color.RED);
            } else {
                edge.getLine().setStroke(Color.BLACK);
            }
            edge.getLine().setStrokeWidth(3);
            anchorPane.getChildren().add(edge.getLine());
        }
        for (VertexVisual vertex : graph.getVertices()) {
            vertex.getCircle().setCenterX(vertex.getSimBody().getPosition().x);
            vertex.getCircle().setCenterY(vertex.getSimBody().getPosition().y);
            vertex.getCircle().setFill(Color.ORANGE);
            vertex.getCircle().setRadius(15);
            vertex.getCircle().setVisible(true);
            vertex.getLabel().setX(vertex.getCircle().getCenterX());
            vertex.getLabel().setY(vertex.getCircle().getCenterY());
            if (Frontend.gameController.gamemode == 3 && Frontend.vertexOrder.get(0).equals(vertex))  {
                vertex.getCircle().setStrokeWidth(4);
                vertex.getCircle().setStroke(Color.BLACK);
                vertex.getCircle().setStrokeType(StrokeType.OUTSIDE);
            }
            anchorPane.getChildren().addAll(vertex.getCircle(), vertex.getLabel());
        }

    }

    public BorderPane getAnchorPane() {
        return anchorPane;
    }
}
