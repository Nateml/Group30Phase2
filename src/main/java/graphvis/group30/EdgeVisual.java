package graphvis.group30;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class EdgeVisual implements Visual{
    private VertexVisual v1, v2;
    final Color colour = Color.BLACK;
    final int width = 3;
    private Line line = new Line();

    /**
     * @param v1 the vertex that the edge starts at
     * @param v2 the vertex that the edge ends at
     */
    public EdgeVisual(VertexVisual v1, VertexVisual v2) {
        this.v1 = v1;
        this.v2 = v2;
    }

    /**
     * @param e
     * @return true if both edges share the same two vertices.
     */
    public boolean equals(EdgeVisual e) {
        if (e.getStartVertex().equals(v1) && e.getEndVertex().equals(v2)) return true;
        if (e.getStartVertex().equals(v2) && e.getEndVertex().equals(v1)) return true;
        return false;
    }

    /**
     * @return the vertex that the edge starts from.
     */
    public VertexVisual getStartVertex() {
        return v1;
    }

    /**
     * @return the vertex that the edge ends at.
     */
    public VertexVisual getEndVertex() {
        return v2;
    }


    /**
     * @return the slope of the edge
     */
    public double getGradient() {
        double gradient = (v1.getSimBody().getPosition().y - v2.getSimBody().getPosition().y) / (v1.getSimBody().getPosition().x - v2.getSimBody().getPosition().x);
        return gradient;
    }

    /**
     * @return the euclidean distance between the edge's start and end vertices.
     */
    public double getLength() {
        double length = Math.sqrt(Math.pow(v1.getSimBody().getPosition().y-v2.getSimBody().getPosition().y, 2) + Math.pow(v1.getSimBody().getPosition().x-v2.getSimBody().getPosition().x,2));
        return length;
    }

    /**
     * Sets the position and stroke of the edge.
     */
    public void updateDisplay() {
        line.setStartX(v1.getSimBody().getPosition().x);
        line.setStartY(v1.getSimBody().getPosition().y);
        line.setEndX(v2.getSimBody().getPosition().x);
        line.setEndY(v2.getSimBody().getPosition().y);
        line.setStroke(colour);
        line.setStrokeWidth(5);
    }

    /**
     * @return the edge's Line object.
     */
    public Line getLine() {
        return line;
    }

    /* 
     * Adds the Line object of the edge to the pane object.
     */
    public void display(Pane pane) {
        line.setStartX(v1.getSimBody().getPosition().x);
        line.setStartY(v1.getSimBody().getPosition().y);
        line.setEndX(v2.getSimBody().getPosition().x);
        line.setEndY(v2.getSimBody().getPosition().y);
        line.setStroke(colour);
        line.setStrokeWidth(5);
        pane.getChildren().add(line);
    }
}
