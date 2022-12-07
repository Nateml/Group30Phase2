package graphvis.group30;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;


public class EdgeVisual implements Visual{
    private VertexVisual v1, v2;
    final Color colour = Color.BLACK;
    final int width = 3;
    Line line = new Line();

    public EdgeVisual(VertexVisual v1, VertexVisual v2) {
        this.v1 = v1;
        this.v2 = v2;
    }


    public boolean equals(EdgeVisual e) {
        if (e.getStartVertex().equals(v1) && e.getEndVertex().equals(v2)) return true;
        if (e.getStartVertex().equals(v2) && e.getEndVertex().equals(v1)) return true;
        return false;
    }

    public VertexVisual getStartVertex() {
        return v1;
    }

    public VertexVisual getEndVertex() {
        return v2;
    }

    public double getGradient() {
        double gradient = (v1.getSimBody().getPosition().y - v2.getSimBody().getPosition().y) / (v1.getSimBody().getPosition().x - v2.getSimBody().getPosition().x);
        return gradient;
    }

    public double getLength() {
        double length = Math.sqrt(Math.pow(v1.getSimBody().getPosition().y-v2.getSimBody().getPosition().y, 2) + Math.pow(v1.getSimBody().getPosition().x-v2.getSimBody().getPosition().x,2));
        return length;
    }

    public void updateDisplay() {
        line.setStartX(v1.getSimBody().getPosition().x);
        line.setStartY(v1.getSimBody().getPosition().y);
        line.setEndX(v2.getSimBody().getPosition().x);
        line.setEndY(v2.getSimBody().getPosition().y);
        line.setStroke(colour);
        line.setStrokeWidth(5);
    }

    public Line getLine() {
        return line;
    }

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
