package graphvis.group30;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Game {
    public int gamemode; 
    public int numberOfEdges; 
    public int numberOfVertices; 
    public Vertex[][] vertexcolouring = new Vertex[0][0]; 
    private Vertex currentVertex; 
    public int progress = 0; 
    public int currentChromaticNumber; 
    public Graph graphForGame;  
    public int oldCurrentChromaticNumber = 0;
    private int numVerticesColoured = 0;
    public ArrayList<Vertex> inGameRandomOrder;
    
     
    /**
     * Sets the gamemode of the game.
     * @param i the gamemode
     */
    public void setGamemode(int i){
        gamemode = i; 
    }

    /**
     * @return the exact chromatic number of the graph
     */
    public int bruteForceChromaticNumber() {
        return graphForGame.bruteForceChromaticNumber();
    }

    /* 
    public Vertex getVertexFromID(int i) {
        return graphForGame.getVertexFromID(i);
    }
    */

    /**
     * @return the random ordering of the vertices for gamemode 3
     */
    public ArrayList<Vertex> getCurrentRandomOrdering() {
        return inGameRandomOrder;
    }

    /**
     * @return true if all the vertices of the graph have been coloured, else returns false.
     */
    public boolean allVerticesColoured() {
        return numVerticesColoured == numberOfVertices;
    }

    /**
     * @return an arraylist of vertices in a random order.
     */
    public ArrayList<Vertex> createNewRandomOrdering() {
        List<Vertex> randomOrderingList = Arrays.asList(graphForGame.randomOrdering());
        inGameRandomOrder = new ArrayList<>(randomOrderingList);
        return inGameRandomOrder;
    }

    /**
     * Creates a random Graph object and stores it in the Game object.
     * @param vertices the number of vertices in the graph
     * @param edges the number of edges in the graph
     */
    public void setGraph(int vertices, int edges){
        numberOfEdges = edges; 
        numberOfVertices = vertices; 
        graphForGame = Graph.createRandomGraph(numberOfVertices, numberOfEdges);
    }

    /**
     * Creates a Graph from the file and stores it in the Game object.
     * @param filename the graph file
     * @throws FileNotFoundException
     */
    public void setGraphFromFile(File file) throws FileNotFoundException {
        graphForGame = Graph.createGraphFromFile(file);
        numberOfEdges = graphForGame.numEdges;
        numberOfVertices = graphForGame.numVertices;
    }

    /**
     * Sets the colour of the vertex.
     * @param a the vertex to be coloured.
     * @param colour the colour of the vertex
     */
    public void changeColour(Vertex a, int colour){
        a = (Vertex)a;
        int currentColor = getColour(a); 

        // create array list from the vertexcolouring array
        ArrayList<ArrayList<Vertex>> vertexColouringList = new ArrayList<>();
        for (int i = 0; i < vertexcolouring.length; i++) {
            ArrayList<Vertex> vertexList = new ArrayList<>();
            for (int j = 0; j < vertexcolouring[i].length; j++) {
               vertexList.add(vertexcolouring[i][j]) ;
            }
            vertexColouringList.add(vertexList);
        }

        // increment the number of vertices coloured if the vertex is being coloured for the first time
        if (currentColor == -1) {
            numVerticesColoured++;
        } else {
            // remove vertex from old color class
            for (int i = 0; i < vertexColouringList.size(); i++) {
                for (int j = 0; j < vertexColouringList.get(i).size(); j++) {
                    if (vertexColouringList.get(i).get(j).equals(a)) {
                        vertexColouringList.get(i).remove(j);
                        if (vertexColouringList.get(i).size() == 0) {
                        }
                    }
                }
            }
        }

        // make space for a new colour class
        if (vertexColouringList.size() <= colour) {
            vertexColouringList.add(new ArrayList<Vertex>());
        }

        vertexColouringList.get(colour).add(a); // add vertex to colour class

        // convert array list to array
        Vertex[][] colourClasses = new Vertex[vertexColouringList.size()][];
        for (int i = 0; i < vertexColouringList.size(); i++) {
            Vertex[] colourClass = new Vertex[vertexColouringList.get(i).size()];
            for (int j = 0; j < vertexColouringList.get(i).size(); j++) {
               colourClass[j] = vertexColouringList.get(i).get(j); 
            }
            colourClasses[i] = colourClass;
        }

        vertexcolouring = colourClasses;


        // keep track of the amount of colours being used to colour the graph
        progress = 0;
        for (int i = 0; i < vertexcolouring.length; i++) {
            if (vertexcolouring[i].length > 0) progress++;
        }
    }

    /**
     * @param V the vertex for which to return the colour of.
     * @return the index of the colour class in which the vertex belongs.
     */
    public int getColour(Vertex V){ 
        // loop through the colour classes to find which colour class the vertex is in
        for (int i = 0; i < vertexcolouring.length; i++) {
            for (int j = 0; j < vertexcolouring[i].length; j++) {
               if(vertexcolouring[i][j].equals(V)){
                    return i; 
               } 
            } 
        }
        return -1; // a value of negative -1 would mean a unnasigned color 
    }

    /**
     * @return true if no vertices are in the same colour class as any of their neighbours.
     */
    public boolean isLegalColouring() { 
        // loops through the vertices to check if the current coloring is legal. 
        for (int i = 0; i < vertexcolouring.length; i++) {
            for (int j = 0; j < vertexcolouring[i].length; j++) {
                int[] neighbours = vertexcolouring[i][j].getNeighboursAsIntArray();
                for (int k = 0; k < neighbours.length; k++) {
                    for (int k2 = 0; k2 < vertexcolouring[i].length; k2++) {
                        if (neighbours[k]== vertexcolouring[i][k2].identification()) return false; // return false if the current vertex we are looping through in the colour class is equal to a neighbour of the vertex we are checking
                    }
                }
            }
        }
        return true; 
    }

    /**
     * @param colouredVertices the vertex colour classes
     * @return a matrix of 1s and 0s, where a 1 indicates there is a colour conflict between the vertices at that index.
     */
    private int[][] conflicingVertices(Vertex[][] colouredVertices){ 
        int[][] listofconflicVerticeVertices = new int[numberOfVertices][numberOfVertices]; 
        for (int i = 0; i < colouredVertices.length; i++) {
            for (int j = 0; j < colouredVertices[i].length; j++) {
                int[] neighbour = colouredVertices[i][j].getNeighboursAsIntArray();
                for (int k = 0; k < neighbour.length; k++) {
                    for (int k2 = 0; k2 < colouredVertices[i].length; k2++) {
                        if (neighbour[k] == colouredVertices[i][k2].identification()) {
                            listofconflicVerticeVertices[colouredVertices[i][j].identification()][neighbour[k]] = 1;
                        }
                    }
                }
            }
        }
        return listofconflicVerticeVertices; 
    }

    
    /**
     * @return a hint which provides helpful information to the user, the content of which depends on that game state.
     */
    public String getHint(){
        String hint = " "; 
         
        if (gamemode==1 || gamemode==2) {
           if (!isLegalColouring()) {
                int[][] tempArray = conflicingVertices(vertexcolouring); 
                for (int i = 0; i < tempArray.length; i++) {
                    for (int j = 0; j < tempArray[i].length; j++) {
                        if (i == j) continue;
                        if (tempArray[i][j]==1) {
                            hint = "Two connected vertices have the same color, \nvertex " + i + " and " + j + " are both the same color, \nswitch one of these two to a new color.";  
                            return hint; 
                        }
                        
                    }
                }
           } 
           if (progress<numberOfVertices) {
             hint = "Your coloring is incomplete right now "; 
             for (int i = 0; i < vertexcolouring.length; i++) {
                for (int j = 0; j < vertexcolouring[0].length; j++) {
                    if(getColour(vertexcolouring[i][j])==-1){
                        int color = canAdd(vertexcolouring, vertexcolouring[i][j].getNeighboursAsVertexArray());
                        hint+= "vertex " + j + "is not coloured \nbut can be assigned " + Frontend.usedColors.get(color) + "."; 
                    }
                }  
             }  

           }
           
           if (progress == numberOfVertices) {
                if (oldCurrentChromaticNumber==currentChromaticNumber) {//in this case the graph is legal but uses too many colors so this hint tries to switch the colors of all the vertices in the smalles color class 
                    hint = "You are using too many colors, ";
                    int color = leastUsedColor();  
                    hint += "try to get rid of your smallest color class"; 
                   Vertex[][] testcolouring = vertexcolouring;  
                   for (int i = 0; i < testcolouring[0].length; i++) {
                     Vertex [] neigh = testcolouring[color][i].getNeighboursAsVertexArray();
                     int addHere = canAdd(testcolouring, neigh); 
                     hint += "vertex " + testcolouring[color][i] + " can be added to the " + Frontend.usedColors.get(addHere) + "color."; 
                     return hint; 
                     
                   }
                        
                }
                } else {
                    hint = "You are using too many colors!\nTry and find colors you can change, if you need more help press the hint button again"; 
                    oldCurrentChromaticNumber = currentChromaticNumber; 
            
                    return hint; 
                }
            
           
            
            
        }if (gamemode==3) {
            // the only hint they can receive is about the vertex they need to color right now, considering they cant go back and change the other values. 
             // this needs to be different but as of right now dont know how to let the current vertex be equal to the vertext they need for game 3
            Vertex[] neighbours = currentVertex.getNeighboursAsVertexArray();
            hint = "The neighbours of this vertex are ";  
            boolean hello = false; 
            for (int i = 0; i < neighbours.length; i++) {
               if (getColour(neighbours[i])!=-1) {
                hello = true; 
            }
                if (i==neighbours.length-1) {
                    hint += "and Vertex " + neighbours[i] + " with " + getColour(neighbours[i]) + " color.";  
                } else {
                    hint += "Vertex " + neighbours[i] + " with " + getColour(neighbours[i]) + " color ";  
                }
                }
                if (hello) {
                    hint += "We can ingore the vertices which we have not colored yet.";   
                }
                hint += "/n We know that this vertex cant have the same color as its neighbours, so try and color this vertex with a color you have used but does not violate this rule"; 
                return hint;
            

        }
        return hint; 
    }
    
    /**
     * Sets the current vertex of gamemode 3.
     * @param V the next vertex in the vertex ordering of gamemode 3.
     */
    public void setCurrentVertex(Vertex V){
        currentVertex = V; 
    }

    /**
     * @param current the current graph colour classes
     * @param neighbours the neighbours of the vertex 
     * @return a colour class that a vertex can be assigned to.
     */
    public int canAdd(Vertex[][] current, Vertex[] neighbours){
        Vertex[][] coloring = new Vertex[current.length][current[0].length];
        for (int i = 0; i < coloring.length; i++) {
            for (int j = 0; j < coloring.length; j++) {
                coloring[i][j] = current[i][j];
                        
            } }
        Vertex[] neigh = new Vertex[neighbours.length]; 
        for (int i = 0; i < neigh.length; i++) {
            neigh[i] = neighbours[i]; 
        }
        
        for (int i = 0; i < coloring.length; i++) {
            boolean add = true; 
            for (int j = 0; j < coloring[0].length; j++) {
                for (int k = 0; k < neigh.length; k++) {
                    if (coloring[i][j]==neigh[k]) {
                        add = false; 
                    }
                }
                
            }
            if (add) {
                return i;  
            } 
        }
        return -1; 
    }
    
    /**
     * @return the least used colour (i.e. the colour class with the least vertices)
     */
    public int leastUsedColor(){
        
        int leastUsed = 0;  
        int least = 0;   
        for (int i = 0; i < vertexcolouring.length; i++) {
            int used = 0;
            for (int j = 0; j < vertexcolouring[i].length; j++) {
                
                if (vertexcolouring[i][j]!=null) {
                    used++; 
                }
            }
            if (used>leastUsed) {
                leastUsed=used; 
                least = i; 
            }
        }
        return least; 
    }
}
