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
            if (Frontend.gameController.gamemode == 3 && (Frontend.vertexOrder.get(0).equals(edge.getStartVertex()) || Frontend.vertexOrder.get(0).equals(edge.getEndVertex()))) {
                edge.getLine().setStroke(Color.RED);
            } else {
                edge.getLine().setStroke(Color.BLACK);
            }
            edge.getLine().setStrokeWidth(2);
        }
        for (VertexVisual vertex : graph.getVertices()) {
            vertex.getCircle().setStrokeWidth(2);
            if (Frontend.gameController.gamemode == 3 && Frontend.vertexOrder.get(0).equals(vertex))  {
                vertex.getCircle().setStrokeWidth(4);
                Frontend.currentVertex = vertex; 
            }
            vertex.getCircle().setStroke(Color.BLACK);
            vertex.getCircle().setStrokeType(StrokeType.OUTSIDE);
        }
    }

    private void addChildren() {
        System.out.println();
        for (EdgeVisual edge : graph.getEdges()) {
            edge.getLine().setStartX(edge.getStartVertex().getSimBody().getPosition().x);
            edge.getLine().setStartY(edge.getStartVertex().getSimBody().getPosition().y);
            edge.getLine().setEndX(edge.getEndVertex().getSimBody().getPosition().x);
            edge.getLine().setEndY(edge.getEndVertex().getSimBody().getPosition().y);
            if (Frontend.gameController.gamemode == 3 && (Frontend.vertexOrder.get(0).equals(edge.getStartVertex())||Frontend.vertexOrder.get(0).equals(edge.getEndVertex()))) {
                edge.getLine().setStroke(Color.RED);
            } else {
                edge.getLine().setStroke(Color.BLACK);
            }
            edge.getLine().setStrokeWidth(2);
            anchorPane.getChildren().add(edge.getLine());
        }
        for (VertexVisual vertex : graph.getVertices()) {
            vertex.getCircle().setCenterX(vertex.getSimBody().getPosition().x);
            vertex.getCircle().setCenterY(vertex.getSimBody().getPosition().y);
            vertex.getCircle().setFill(Color.WHITE);
            vertex.getCircle().setStrokeWidth(2);
            vertex.getCircle().setStroke(Color.BLACK);
            vertex.getCircle().setStrokeType(StrokeType.OUTSIDE);
            vertex.getCircle().setRadius(15);
            vertex.getCircle().setVisible(true);
            vertex.getLabel().setX(vertex.getCircle().getCenterX());
            vertex.getLabel().setY(vertex.getCircle().getCenterY());
            if (Frontend.gameController.gamemode == 3 && Frontend.vertexOrder.get(0).equals(vertex))  {
                vertex.getCircle().setStrokeWidth(5);
                vertex.getCircle().setStroke(Color.BLACK);
                vertex.getCircle().setStrokeType(StrokeType.OUTSIDE);
            }
            anchorPane.getChildren().addAll(vertex.getCircle());
        }

    }

    public BorderPane getAnchorPane() {
        return anchorPane;
    }
}
