package graphvis.group30;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.scene.paint.Color;

public class Game {
    private int gamemode; 
    private int numberOfEdges; 
    private int numberOfVertices; 
    private Vertex[][] vertexcolouring = new Vertex[0][0]; 
    private VertexVisual[][] col = new VertexVisual[0][0];
    private Vertex currentVertex; 
    private int progress = 0; 
    private int currentChromaticNumber; 
    private Graph graphForGame;  
    private int oldCurrentChromaticNumber = 0;
    private int numVerticesColoured = 0;
    private ArrayList<Vertex> inGameRandomOrder;
    
     
    /** 
     * Sets the gamemode of the game.
     * @param i the gamemode
     */
    public void setGamemode(int i){
        gamemode = i; 
    }

    /**
     * @return the current gamemode
     */
    public int getGamemode() {
        return gamemode;
    }

    public int getProgress() {
        return progress;
    }

    /**
     * @return the number of vertices in the graph
     */
    public int getNumberOfVertices() {
        return numberOfVertices;
    }

    /**
     * @return the number of edges in the graph
     */
    public int getNumberOfEdges() {
        return numberOfEdges;
    }

    /**
     * @return the number of vertices in the graph that have been coloured
     */
    public int getNumVerticesColoured() {
        return numVerticesColoured;
    }

    /**
     * @return the current graph of the game
     */
    public Graph getGraph() {
        return graphForGame;
    }

    /**
     * @return the exact chromatic number of the graph
     */
    public int bruteForceChromaticNumber() {
        return graphForGame.bruteForceChromaticNumber();
    }

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
     * @throws UnconnectedVerticesException
     */
    public void setGraphFromFile(File file) throws FileNotFoundException, UnconnectedVerticesException {
        graphForGame = Graph.createGraphFromFile(file);
        numberOfEdges = graphForGame.getNumEdges();
        numberOfVertices = graphForGame.getNumVertices();
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
            currentChromaticNumber++; 
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
                            for (int j2 = 0; j2 < Frontend.graph.getVertices().length; j2++) {
                                if (Frontend.graph.getVertices()[j2].identification()== i||Frontend.graph.getVertices()[j2].identification()== j) {
                                    Frontend.graph.getVertices()[j2].getCircle().setStrokeWidth(5);
                                }
                            }
                           
                            hint = "The two highlighted vertices have the same color even though they are connected, change the color of one of these.";  
                            return hint; 
                        }
                        
                    }
                }
           } 
           if (numVerticesColoured<numberOfVertices) {
          
             hint = "Your coloring is incomplete right now. "; 
               for (int j2 = 0; j2 < Frontend.graph.getVertices().length; j2++) {
                if (getColour(Frontend.graph.getVertices()[j2]) == -1) {
                    int color = canAdd(vertexcolouring, Frontend.graph.getVertices()[j2].getNeighboursAsVertexArray());
                    
                    if (color>=Frontend.usedColors.size()) {
                    Frontend.graph.getVertices()[j2].getCircle().setStrokeWidth(5);
                    Frontend.graph.getVertices()[j2].getCircle().setStroke(Color.GREY);
                    hint+= "The highlighted vertex can be colored a new color!";  
                    return hint; 
                    } else {
                    Frontend.graph.getVertices()[j2].getCircle().setStrokeWidth(5);
                    Frontend.graph.getVertices()[j2].getCircle().setStroke( Frontend.usedColors.get(color));
                    hint+= "The highlighted vertex can be colored the highlighted color";  
                    return hint; 
                    }
                }
             }   return hint; 

           }
           
           if (numVerticesColoured == numberOfVertices) {
                if (oldCurrentChromaticNumber==currentChromaticNumber) {//in this case the graph is legal but uses too many colors so this hint tries to switch the colors of all the vertices in the smalles color class 
                    hint = "You are using too many colors, ";
                    int color = leastUsedColor();  
                    
                   hint += "try to get rid of your smallest color class."; 
                   Vertex[][] testcolouring = new Vertex[vertexcolouring.length][vertexcolouring[0].length];
                   for (int i = 0; i < vertexcolouring.length; i++) {
                       for (int j = 0; j < vertexcolouring[i].length; j++) {
                           testcolouring[i][j] = vertexcolouring[i][j];
                       }} //creates new vertex[][]


                   
                     
                   
                     for (int j2 = 0; j2 < Frontend.graph.getVertices().length; j2++) {
                        for (int j = 0; j < testcolouring[color].length; j++) { 
                        if (Frontend.graph.getVertices()[j2] == testcolouring[color][j]) {
                        Frontend.graph.getVertices()[j2].getCircle().setStrokeWidth(5);
                     }}

                     
                     
                   }
                   hint += " The highlighted vertices can all be changed to different already used colors"; 
                   return hint;     
                } else {
                    hint = "You are using too many colors! Try and find colors you can change, if you need more help press the hint button again"; 
                    oldCurrentChromaticNumber = currentChromaticNumber; 
            
                    return hint; 
                }
                } 
            
           
            
            
        }if (gamemode==3) {
            // the only hint they can receive is about the vertex they need to color right now, considering they cant go back and change the other values. 
             // this needs to be different but as of right now dont know how to let the current vertex be equal to the vertext they need for game 3
            Vertex[] neighbours = Frontend.vertexOrder.get(0).getNeighboursAsVertexArray(); 
            
            int count = 0; 
            int count2 = 0; 
            for (int i = 0; i < neighbours.length; i++) {
                for (int j = 0; j <  Frontend.graph.getVertices().length; j++) {
                    if ( Frontend.graph.getVertices()[j].equals(neighbours[i])) {
                       
                        if (getColour(Frontend.graph.getVertices()[j])==-1) {
                            Frontend.graph.getVertices()[j].getCircle().setStrokeWidth(5);
                            Frontend.graph.getVertices()[j].getCircle().setStroke(Color.GREY);
                            if (count2==0) {
                                hint += " The uncoloured vertices which are connected have been highlighted with grey, you can ignore these when assigning a color. ";  
                                count2++;   
                            }
                           
                        }

                        if (getColour(Frontend.graph.getVertices()[j])!=-1) {
                            Frontend.graph.getVertices()[j].getCircle().setStrokeWidth(5);
                            Frontend.graph.getVertices()[j].getCircle().setStroke(Color.RED);
                            if(count==0){
                            hint += " The coloured vertices which are connected have been highlighted with red, the vertex can NOT have these colours, so take that into account when assigining a color."; 
                                count++; 
                        }
                    }
                        
                    }
                }
            }
            
         }return hint;
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
        Vertex[][] coloring = new Vertex[current.length][];
        for (int i = 0; i < current.length; i++) {
            Vertex[] colorClass = new Vertex[current[i].length];
            for (int j = 0; j < current[i].length; j++) {
                colorClass[j] = current[i][j];
            } 
            coloring[i] = colorClass;
        }
        Vertex[] neigh = new Vertex[neighbours.length]; 
        for (int i = 0; i < neigh.length; i++) {
            neigh[i] = neighbours[i]; 
        }
        
        for (int i = 0; i < coloring.length; i++) {
            boolean add = true; 
            for (int j = 0; j < coloring[i].length; j++) {
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
        return current.length; 
    }
    
    /**
     * @return the least used colour (i.e. the colour class with the least vertices)
     */
    public int leastUsedColor(){
        
        int leastUsed = 100;  
        int color = 0; 
         
        for (int i = 0; i < vertexcolouring.length; i++) {
            int used = 0;
            for (int j = 0; j < vertexcolouring[i].length; j++) {
                
                if (vertexcolouring[i][j]!=null) {
                    used++; 
                }
            }
            if (used != 0 && used<leastUsed) {
                leastUsed=used;
                color = i;  
            }
        }
        return color; 
    }
}
