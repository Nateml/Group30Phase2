package graphvis.group30;
import java.util.*;
import java.util.Random;

public class Graph {
    Vertex[] vertices;
    int k;
    int upperBoundK;
    int lowerBoundK;
    Vertex[][] vertexColouring;
    int[][] adjMatrix;
    HashMap<Integer, Vertex> vertexIDs;

    public Graph(Vertex[] v) {
        this.vertices = v;
        vertexIDs = new HashMap<>();

        for (Vertex vertex : v) {
            vertexIDs.put(vertex.i, vertex);
        }

        // create adjency matrix
    }

    public Vertex getVertexFromID(int id) {
        return vertexIDs.get(id);
    }

    public static Graph createRandomGraph(int numVertices, int numEdges) {
        RandomGraph randomGraph = new RandomGraph(numVertices, numEdges);
        Vertex[] v = randomGraph.getVertexArray();
       return new Graph(v);
    }

    /* 
    public static Graph createGraphFromFile(File f) {
        
    }
    */

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




}
