package graphvis.group30;

import java.util.ArrayList;

public class Vertex {
    /*
     * This class represents a vertex of the graph.
     * It stores the saturation degree of the vertex and its number.
     * Create a vertex by writing: Vertex v = new Vertex(<saturation_degree>, <i>)
     */
    int saturationDegree;
    int i;
    ArrayList<Vertex> neighbours = new ArrayList<>();
    
    public Vertex(int saturationDegree, int i) {
        this.saturationDegree = saturationDegree;
        this.i = i;
    }

    // adds a vertex as a neighbour to this vertex
    public void add_neighbour(Vertex neighbour) {
        this.saturationDegree++;
        neighbours.add(neighbour);
    }

    public Vertex[] getNeighboursAsVertexArray() {
        Vertex[] neighbourArray = new Vertex[neighbours.size()];
        for (int i = 0; i < neighbours.size(); i++) {
            neighbourArray[i] = neighbours.get(i);
        }
        return neighbourArray;
    }

    public int[] getNeighboursAsIntArray(){
        int[] neighbourArray = new int[neighbours.size()];
        ArrayList<Integer> neighboursAsInts = new ArrayList<>();
        for (Vertex v : neighbours) {
            neighboursAsInts.add(v.i);
        }
        neighbourArray = neighboursAsInts.stream().mapToInt(i -> i).toArray();
        return neighbourArray;
    }    

    public boolean equals(Vertex v) {
        if (v.i == i) return true;
        return false;
    }

    public int identification() {
        return i;
    }

    @Override
    public String toString() {
        return "" + i;
    }



}
