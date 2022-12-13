package graphvis.group30;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.Random;

public class Graph {
    private Vertex[] vertices;
    private int k;
    private int upperBoundK;
    private int lowerBoundK;
    private Vertex[][] vertexColouring;
    private int[][] adjMatrix;
    private HashMap<Integer, Vertex> vertexIDs;
    private int numEdges;
    private int numVertices;

    /**
     * Creates a graph object.
     * @param v the vertex array of the graph
     * @param numEdges the number of edges in the graph
     */
    public Graph(Vertex[] v, int numEdges) {
        this.vertices = v;
        this.numEdges = numEdges;
        this.numVertices = v.length;
        vertexIDs = new HashMap<>();

        for (Vertex vertex : v) {
            vertexIDs.put(vertex.i, vertex);
        }
    }

    /**
     * Returns a <code>Vertex</code> object from its id.
     * @param id the id of the vertex.
     * @return the <code>Vertex</code> with the given id.
     */
    public Vertex getVertexFromID(int id) {
        return vertexIDs.get(id);
    }

    /**
     * Creates a random graph with the given number of vertices and edges.
     * @param numVertices the number of edges that the graph must have
     * @param numEdges the number of edges that the graph must have
     * @return a random <code>Graph</code> object
     */
    public static Graph createRandomGraph(int numVertices, int numEdges) {
        RandomGraph randomGraph = new RandomGraph(numVertices, numEdges);
        Vertex[] v = randomGraph.getVertexArray();
       return new Graph(v, numEdges);
    }

    /**
     * Creates a <code>Graph</code> object from the given file.
     * @param filename the graph file
     * @return the <code>Graph</code> object
     * @throws FileNotFoundException
     * @throws UnconnectedVerticesException
     */
    public static Graph createGraphFromFile(File filename) throws FileNotFoundException, UnconnectedVerticesException{
        ArrayList<Vertex> vertices = new ArrayList<Vertex>();
        int numVertices = 0;
        int numEdges = 0;
        Scanner fileScanner = new Scanner(filename);
        int lineNumber = 1;
        while (fileScanner.hasNext()) {
            if (lineNumber == 1) {
                numVertices = Integer.parseInt(fileScanner.nextLine().split(" = ")[1]);
            } else if (lineNumber == 2) {
                numEdges = Integer.parseInt(fileScanner.nextLine().split(" = ")[1]);
            } else {
                String line = fileScanner.nextLine();
                int vertex1ID = Integer.parseInt(line.split(" ")[0]);
                int vertex2ID = Integer.parseInt(line.split(" ")[1]);
                Vertex vertex1 = new Vertex(0, vertex1ID);
                Vertex vertex2 = new Vertex(0, vertex2ID);
                boolean containsVertex1 = false;
                boolean containsVertex2 = false;
                for (Vertex vertex : vertices) {
                    if (vertex.equals(vertex1)) {
                        containsVertex1 = true;
                        vertex1 = vertex;
                    } else if (vertex.equals(vertex2)) {
                        containsVertex2 = true;
                        vertex2 = vertex;
                    }
                    if (containsVertex1 && containsVertex2) break;
                }
                vertex1.add_neighbour(vertex2);
                vertex2.add_neighbour(vertex1);
                if (!containsVertex1) {
                    vertices.add(vertex1);
                }
                if (!containsVertex2) {
                    vertices.add(vertex2);
                }

            }
            lineNumber++;
        }
        fileScanner.close();
        
        if (vertices.size() != numVertices) throw new UnconnectedVerticesException();

        Vertex[] v = vertices.toArray(new Vertex[0]);
        return new Graph(v, numEdges);

    }

    public int bruteForceChromaticNumber() {
        if (k != 0) return k;

        LowerBound lb = new LowerBound();
        this.lowerBoundK = lb.getLowerBound(vertices, 0);
        BruteForce bf = new BruteForce();
        this.k = bf.chromaticNumber(vertices, lowerBoundK);

        return k;
    }

    public int upperBoundColouring() {
        if (upperBoundK != 0) return upperBoundK;

        HybridGeneticAlgorithm hga = new HybridGeneticAlgorithm(vertices);
        upperBoundK = hga.upperBound(vertices.length, 100);

        return upperBoundK;
    }

    public int lowerBoundColouring() {
        if (lowerBoundK != 0) return lowerBoundK;

        LowerBound lb = new LowerBound();
        lowerBoundK = lb.getLowerBound(vertices, 0);

        return lowerBoundK;
    }


    public boolean isLegalColouring(Vertex[][] colouredVertices) {
        for (int i = 0; i < colouredVertices.length; i++) {
            for (int j = 0; j < colouredVertices[0].length; j++) {
                int[] neighbour = new int[colouredVertices[i][j].getNeighboursAsIntArray().length]; 
                for (int k = 0; k < neighbour.length; k++) {
                    if(neighbour[k] == colouredVertices[i][j].identification()){
                        return false; 
                    } 
                }
            }
            
        }
        return true; 
        
    }
    

    public Vertex[] randomOrdering() {
        Vertex[] random = new Vertex[vertices.length]; 
        Random rand = new Random();
        for (int i = 0; i<vertices.length; i++ ) {
            int randomIndex = rand.nextInt(random.length);
            if(random[randomIndex]==null){
                random[randomIndex] = vertices[i]; 
            } else{
                i--;
            }
            
           
        }
        return random; 
    }

    public Vertex[][] getVertexColouring() {
        return vertexColouring;
    }


    public int getNumVertices() {
        return numVertices;
    }

    public int getNumEdges() {
        return numEdges;
    }

    public Vertex[] getVertices() {
        return vertices;
    }


}
