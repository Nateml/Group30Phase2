package graphvis.group30;

import java.util.ArrayList;

public class RandomGraph {
    int numVer;
    int numEdg;
    int x;
    int [][]adjMatrix;
    Vertex[] vertexArray;


    public static void main(String[] args) {
       new RandomGraph(8,13);
    }

    public RandomGraph(int numVertices, int numEdges){
        numVer = numVertices;       //works with G(800,5000)
        x = numEdges;               //edges (numVer-1<=x<= ((numVer)*(numVer-1)/2!))
        numEdg = x - (numVer-1);
        adjMatrix = new int[numVer][numVer];
        createRandomGraph(numVer, numEdg, adjMatrix);
        ArrayList<ArrayList<Integer>> adjListArray = convert(adjMatrix);
    }
    public static void createRandomGraph(int numVer, int numEdg, int[][] adjMatrix){
        for(int row=0 ;row <numVer; row++){
            for(int columm= 1; columm< numVer; columm++ ){
                if((row -1 == columm)||(row + 1 == columm)){   //creates a path between all the vertices
                    adjMatrix[row][columm] =1;
                    adjMatrix[columm][row] = 1;
                }
            }
        }

        ArrayList<Integer[]> matrix0 = new ArrayList<Integer[]>();
        //add 0´s to the list
        for (int i = 1; i < adjMatrix.length; i++) {
            for (int j = 0; j < i; ++j) {
                if (adjMatrix[i][j] == 0) {
                    Integer[] x = {i, j};
                    matrix0.add(x);
                }
            }
        }
        int counter=0;
        triangle(numVer, numEdg, matrix0, adjMatrix, counter);
    }
    public static void triangle(int numVer, int numEdg, ArrayList<Integer[]> matrix0, int[][] adjMatrix, int counter){
        if(numEdg-counter == 0){
            for (int i = 0; i < adjMatrix.length; i++) {
                for(int j = 0; j < adjMatrix[0].length; j++) {
                    System.out.print(adjMatrix[i][j] + " ");
                }
                System.out.println("");
            }
            int counter2=0;
            for (int i = 0; i < adjMatrix.length; i++) {
                for(int j = 0; j < adjMatrix[0].length; j++) {
                    if(adjMatrix[i][j]==1){
                        counter2++;

                    }
                }
            }
            System.out.println(counter2/2);
        }
        else {
            double w = (Math.random()*(matrix0.size()));
            int randomNum = (int)w;
            Integer[] str = matrix0.get(randomNum);
            int row= str[0];
            int column = str[1];
            adjMatrix[column][row] = 1;
            adjMatrix[row][column] = 1;
            matrix0.remove(randomNum);
            counter++;
            triangle(numVer, numEdg, matrix0, adjMatrix, counter);
        }
    }

    public ArrayList<ArrayList<Integer>> convert(int[][] a) {       // create an adjacency List from the adjMatrix
        a = adjMatrix;
        int l = a[0].length;
        ArrayList<ArrayList<Integer>> adjListArray = new ArrayList<ArrayList<Integer>>(l);

        // Create a new list for each vertex such that adjacent nodes can be stored
        for (int i = 0; i < l; i++) {
            adjListArray.add(new ArrayList<Integer>());
        }

        for (int i = 0; i < a[0].length; i++) {
            for (int j = 0; j < a.length; j++) {
                if (a[i][j] == 1) {
                    adjListArray.get(i).add(j);
                }
            }
        }
        for (int v = 0; v < adjListArray.size(); v++) {
            System.out.print(v);
            for (Integer u : adjListArray.get(v)) {
                System.out.print(" -> " + u);
            }
            System.out.println();
        }

        //createx the Array of Vertex objects
        vertexArray = new Vertex[numVer];
            for(int i=0; i<numVer; i++){
                vertexArray[i] = new Vertex(getSaturationDegree(adjMatrix[i]), i);
                System.out.println(getSaturationDegree(adjMatrix[i])); // prints the saturation degree of each vertex
            }
            for (int i = 0; i < numVer; i++) {
                for (int j = 0; j < numVer; j++) {
                    if (i == j) continue;
                    if (adjMatrix[i][j] == 1) {
                        vertexArray[i].add_neighbour(vertexArray[j]);
                    }
                }
            }

        return adjListArray;
    }

    public Vertex[] getVertexArray() {
        return vertexArray;
    }

    // get the saturation degree of the Vertex object
    public int getSaturationDegree(int[] a){
        int degree = 0;
        for(int i=0; i<a.length; i++){
            if (a[i]==1){
                degree++;
            }
        }
        return degree;
    }

}
