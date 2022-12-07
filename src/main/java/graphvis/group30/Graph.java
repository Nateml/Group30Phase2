package graphvis.group30;
import java.io.File;
import java.util.*;
import java.util.Random;

public class Graph {
    Vertex[] vertices;
    int k;
    int upperBoundK;
    int lowerBoundK;
    Vertex[][] vertexColouring;
    int[][] adjMatrix;

    public Graph(Vertex[] v) {
        this.vertices = v;

        // create adjency matrix
    }

    public static Graph createRandomGraph(int numVertices, int numEdges) {
       Vertex[] v = new Vertex[numVertices] ;

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
        for (int i =  vertices.length, j = 0; i > 0; i--, j++) {
            Random rand = new Random();
            int n = rand.nextInt(i);
            random[j] = vertices[n]; 
        }
        return random; 
    }

    public Vertex[][] getVertexColouring() {
        return vertexColouring;
    }




}
