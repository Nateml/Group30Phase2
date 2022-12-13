package graphvis.group30;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class VertexVisual extends Vertex {
    private SimBody body;
    private Color colour;
    private Circle c;
    private Line line;
    private Text label;

    final private int RADIUS = 15;

    public VertexVisual(int saturationDegree, int i) {
        super(saturationDegree, i);
        body = new SimBody();
        colour = Color.ORANGE;
        c = new Circle(i);
        label = new Text(i + "");
        label.setMouseTransparent(true);
        line = new Line();
        c.setCenterX(body.getPosition().x);
        c.setCenterY(body.getPosition().y);
        c.setRadius(RADIUS);
        c.setFill(colour);
    }


    /**
     * this is a javadoc
     * @param colour
     */
    public void setColour(Color colour) {
        this.colour = colour;
    }

    
    /** 
     * @return SimBody
     */
    public SimBody getSimBody() {
        return body;
    }

    
    /** 
     * @return Circle
     */
    public Circle getCircle() {
        return c;
    }

    
    /** 
     * @return Text
     */
    public Text getLabel() {
        return label;
    }

    public void updateDisplay() {
        c.setCenterX(body.getPosition().x);
        c.setCenterY(body.getPosition().y);
        c.setRadius(RADIUS);
        c.setFill(Color.ORANGE);
        c.setVisible(true);
        label.setX(c.getCenterX());
        label.setY(c.getCenterY());

        line.setStartX(c.getCenterX());
        line.setStartY(c.getCenterY());

        line.setEndX(Vector.add(body.getPosition(), body.getNetForce()).x);
        line.setEndY(Vector.add(body.getPosition(), body.getNetForce()).y);
    }

    
    /** 
     * @param p
     */
    public void display(Pane p) {
        label = new Text(c.getCenterX(), c.getCenterY(), i + "");
        line = new Line();
        p.getChildren().addAll(c, label, line);
    }

}
