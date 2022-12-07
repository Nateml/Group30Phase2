package graphvis.group30;

public class MyGraph {
    VertexVisual[] vertices;
    EdgeVisual[] edges;
    GraphVisSim visSim;
    double width, height;

    public MyGraph(VertexVisual[] vertices, double width, double height) {
        this.vertices = vertices;
        visSim = new GraphVisSim(width, height);
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

    private void initializeVertices() {
        for (VertexVisual vertex : vertices) {
            //double randX = (Math.random() * (width/4)) + width/4;
            double randX = Math.random() * width;
            //double randY = (Math.random() * (height/4)) + height/4;
            double randY = Math.random() * height;
            //vertex.setPosition(new Vector((Math.random() * (scene.getWidth()-scene.getWidth()/2)) + scene.getWidth()/2, (Math.random() * (scene.getHeight()-scene.getHeight()/2))+scene.getHeight()/2));
            vertex.getSimBody().setPosition(new Vector(randX, randY));
            vertex.getCircle().setOnMouseClicked((t) -> {
                System.out.println(vertex.getSimBody().getVelocity().getLength());
            });
            visSim.addSimBody(vertex);
        }
    }

    public void simulate() {
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
