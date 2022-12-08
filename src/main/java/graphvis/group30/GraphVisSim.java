package graphvis.group30;

import java.util.ArrayList;


public class GraphVisSim {
    private double temp = 0.5;
    private ArrayList<VertexVisual> vertices;
    private double width, height;
    SimBody centerBody = new SimBody();

    public GraphVisSim(double width, double height) {
        vertices = new ArrayList<>();
        this.width = width;
        this.height = height;
        centerBody.setPosition(new Vector(width/2, height/2));
    }

    public void addSimBody(VertexVisual v) {
        vertices.add(v);
    }

    public boolean run() { 
        for (VertexVisual v1 : vertices) {
            v1.getSimBody().addForce(Vector.mult(SpringForce.getSpringForceBetween(v1.getSimBody(), centerBody),4));
            for (VertexVisual v2 : vertices) {
                if (v1.equals(v2)) continue;

                boolean isNeighbour = false;
                for (Vertex neighbour : v1.getNeighboursAsVertexArray()) {
                    //neighbour = (VertexVisual2) neighbour;
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

        boolean allNodesStatic = true;
        for (VertexVisual v : vertices) {
            Vector prevPosition = new Vector(v.getSimBody().getPosition().x, v.getSimBody().getPosition().y);
            v.getSimBody().update(temp); 
            //temp *= 0.99999;
            //if (temp > 0.00001) temp -= 0.00001;
            temp -= 0.000005;
            Vector currentPosition = v.getSimBody().getPosition();
            Vector diffPosition = Vector.subtract(prevPosition, currentPosition);
            //System.out.println(diffPosition.getLength());
            /* 
            if (Math.abs(diffPosition.x) != 0 || Math.abs(diffPosition.y) != 0) {
                allNodesStatic = false;
            }
            */
            //System.out.println(diffPosition.getLength());
            if (diffPosition.getLength() > 0.00000001) {
                allNodesStatic = false;
            }
            v.getSimBody().checkEdges(width, height);
            //v.display(pane);
        }
        if (allNodesStatic) return true;
        return false;
    }

    public void freeze() {

    }

}
