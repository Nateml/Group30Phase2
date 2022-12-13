package graphvis.group30;

import java.util.ArrayList;


public class GraphVisSim {
    private double temp = 0.5; // scalar for the velocity of the vertices
    private ArrayList<VertexVisual> vertices;
    private double width, height;
    private SimBody centerBody = new SimBody(); // an object to keep vertices situated around the center

    /**
     * 
     * @param width the width of the pane that the graph will be displayed on
     * @param height the height of the pane that the graph will be displayed on
     */
    public GraphVisSim(double width, double height) {
        vertices = new ArrayList<>();
        this.width = width;
        this.height = height;
        centerBody.setPosition(new Vector(width/2, height/2));
    }

    /**
     * Adds a vertex to the simulation.
     * @param v
     */
    public void addSimBody(VertexVisual v) {
        vertices.add(v);
    }

    /**
     * Runs the simulation for 1 iteration.
     * @return true if the simulation is complete (vertices are not moving), false if the simulation is not complete.
     */
    public boolean run() { 
        // add forces to the vertices
        for (VertexVisual v1 : vertices) {
            v1.getSimBody().addForce(Vector.mult(SpringForce.getSpringForceBetween(v1.getSimBody(), centerBody),2));
            for (VertexVisual v2 : vertices) {
                if (v1.equals(v2)) continue;

                boolean isNeighbour = false;
                for (Vertex neighbour : v1.getNeighboursAsVertexArray()) {
                    if (neighbour.equals(v2)) {
                        isNeighbour = true;
                        break;
                    }
                }

                if (isNeighbour) {
                    v1.getSimBody().addForce(SpringForce.getSpringForceBetween(v1.getSimBody(), v2.getSimBody()));
                }
                v2.getSimBody().addForce(ElectrostaticRepulsion.getElectrostaticBetween(v2.getSimBody(), v1.getSimBody()));
            }
        }

        // update position of vertices:
        boolean allNodesStatic = true;
        for (VertexVisual v : vertices) {
            Vector prevPosition = new Vector(v.getSimBody().getPosition().x, v.getSimBody().getPosition().y);
            v.getSimBody().update(temp); 

            temp -= 0.000005; // decrease temperature
            if (temp <= 0) temp = 0;

            // check if vertices are changing position
            Vector currentPosition = v.getSimBody().getPosition();
            Vector diffPosition = Vector.subtract(prevPosition, currentPosition);
            if (diffPosition.getLength() > 0.00000001) {
                allNodesStatic = false;
            }

            v.getSimBody().checkEdges(width, height); // dont let vertices travel beyond the width and height
        }

        if (allNodesStatic) return true;
        return false;
    }

}
