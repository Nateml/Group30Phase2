package graphvis.group30;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class VertexVisual extends Vertex {
    private SimBody body;
    private Color colour;
    private Circle c;
    private Text label;

    final private int RADIUS = 15; // the radius of the circle which the vertex is displayed as

    /**
     * Creates a <code>VertexVisual</code> object.
     * @param saturationDegree the saturation degree of the vertex
     * @param i the number/id of the vertex
     */
    public VertexVisual(int saturationDegree, int i) {
        super(saturationDegree, i);
        body = new SimBody(); // used for the force-directed algorithm
        colour = Color.WHITE; // default color of the vertex
        c = new Circle(i); 

        // create a label which displays the vertex's id
        label = new Text(i + "");
        label.setMouseTransparent(true);

        // set position and display parameters of the circle to be displayed
        c.setCenterX(body.getPosition().x);
        c.setCenterY(body.getPosition().y);
        c.setRadius(RADIUS);
        c.setFill(colour);
    }


    /**
     * Sets the colour of the vertex.
     * @param colour the colour the vertex will use when being displayed.
     */
    public void setColour(Color colour) {
        this.colour = colour;
    }

    /**
     * @return the colour of the vertex
     */
    public Color getColour() {
        return colour;
    }

    
    /** 
     * @return the physics body of the vertex
     */
    public SimBody getSimBody() {
        return body;
    }
    
    /** 
     * @return the circle that the vertex is displayed by
     */
    public Circle getCircle() {
        return c;
    }

    
    /** 
     * @return the label that displays the vertex's id
     */
    public Text getLabel() {
        return label;
    }

    /**
     * Updates the display settings of the vertex's display components
     */
    public void updateDisplay() {
        // set position of the circle
        c.setCenterX(body.getPosition().x);
        c.setCenterY(body.getPosition().y);

        // set display parameters of the circle
        c.setRadius(RADIUS);
        c.setFill(Color.ORANGE);
        c.setVisible(true);

        // set position of the label
        label.setX(c.getCenterX());
        label.setY(c.getCenterY());
    }
    
    /** 
     * Adds the vertex's circle and label to the pane <code>p</code>.
     * @param p the pane to display the vertex on
     */
    public void display(Pane p) {
        label = new Text(c.getCenterX(), c.getCenterY(), i + "");
        p.getChildren().addAll(c, label);
    }

}
