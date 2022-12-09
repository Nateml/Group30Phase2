package graphvis.group30;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//import javax.swing.plaf.ProgressBarUI;
public class Game {
    public int gamemode; 
    public int numberOfEdges; 
    public int numberOfVertices; 
    public int timeLimit; 
    public Vertex[][] vertexcolouring = new Vertex[0][0]; 
    public Vertex current; 
    public int progress = 0; 
    public int chromaticNumber; 
    public int currentChromaticNumber; 
    public Graph graphForGame;  
    public String messageWhenDone; 
    public int oldprogress = 0; //these are useful to make the hints more dynamic
    public int oldCurrentChromaticNumber = 0;
    boolean isGameRunning = false;
    public Vertex[] inGameRandomOrder; 
    
     
    public void setGamemode(int i){
        gamemode = i; 
    }

    public Vertex getVertexFromID(int i) {
        return graphForGame.getVertexFromID(i);
    }

    public ArrayList<Vertex> getRandomOrdering() {
        List<Vertex> randomOrderingList = Arrays.asList(graphForGame.randomOrdering());
        return new ArrayList<>(randomOrderingList);
    }

    public void setGraph(int vertices, int edges, int time){
        numberOfEdges = edges; 
        numberOfVertices = vertices; 
        if(time != 0){
            timeLimit = time; 
        }
        //Graph play = new Graph(numberOfVertices);
        graphForGame = Graph.createRandomGraph(numberOfVertices, numberOfEdges);  
    }

    public void setGraphFromFile(File filename) throws FileNotFoundException {
        graphForGame = Graph.createGraphFromFile(filename);
    }

    public void changeColour(Vertex a, int color){
        System.out.println("color " + color);
        a = (Vertex)a;
        int currentColor = getColor(a); // this will check whether or not we can assign a value or if we have to change it. 

        // create array list
        ArrayList<ArrayList<Vertex>> vertexColouringList = new ArrayList<>();
        for (int i = 0; i < vertexcolouring.length; i++) {
            ArrayList<Vertex> vertexList = new ArrayList<>();
            for (int j = 0; j < vertexcolouring[i].length; j++) {
               vertexList.add(vertexcolouring[i][j]) ;
            }
            vertexColouringList.add(vertexList);
        }

        if (currentColor == -1) {
            progress++;
        } else {
            // remove vertex from old color class
            for (int i = 0; i < vertexColouringList.size(); i++) {
                for (int j = 0; j < vertexColouringList.get(i).size(); j++) {
                    if (vertexColouringList.get(i).get(j).equals(a)) {
                        vertexColouringList.get(i).remove(j);
                    }
                }
            }
        }

        if (vertexColouringList.size() <= color) {
            vertexColouringList.add(new ArrayList<Vertex>());
        }

        vertexColouringList.get(color).add(a);

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

        for (int i = 0; i < vertexcolouring.length; i++) {
            System.out.print("[");
            for (int j = 0; j < vertexcolouring[i].length; j++) {
               System.out.print(vertexcolouring[i][j]+", "); 
            }
            System.out.print("], ");
        }
        System.out.println();
        /* 

        if (vertexcolouring.length <= color) { // expands array to make space for a new color
            Vertex[][] newVertexColouring = new Vertex[color+1][];
            System.arraycopy(vertexcolouring, 0, newVertexColouring, 0, vertexcolouring.length);
            vertexcolouring = newVertexColouring;
        }

        vertexcolouring[color][vertexcolouring[color].length] = a;
        if (currentColor == -1) {
            
            // keep count of how many vertices have been used
            progress++;
        } else {
            // remove vertex from old color class
            for (int i = 0; i < vertexcolouring[0].length; i++) {
                if(vertexcolouring[currentColor][i] == a){
                    vertexcolouring[currentColor][i] = null; 
                }
            } 
        }
        */
        
    }

    public boolean isGameRunning() {
        return isGameRunning;
    }

    public int colorsBeingUsed(){
        // I think this should just return vertexcolouring.length? - Nate
        // but would that work if somebody uses a color, but then stop using that color and swtiches it to a new one? 
        int test = 0; 
        for (int i = 0; i < vertexcolouring.length; i++) {
            for (int j = 0; j < vertexcolouring[i].length; j++) {
                if(getColor(vertexcolouring[i][j]) >= 0){ //not sure if this actually works, as i dont know if null = 0 but, if they arent equal this should wotk fine
                    test++; 
                    break; 
                }
            }}
            currentChromaticNumber = test; 
            return test;
    }

    public int getColor(Vertex V){ // finds the color row in which the vertex is placed
        for (int i = 0; i < vertexcolouring.length; i++) {
            for (int j = 0; j < vertexcolouring[i].length; j++) {
               if(vertexcolouring[i][j].equals(V)){
                    return i; 
               } 
            } 
        }
        return -1; // a value of negative -1 would mean a unnasigned color 
    }
    public boolean isLegalColouring(Vertex[][] colouredVertices) { // loops through the vertices to check if the curretn coloring is legal. 
        int numVerticesColoured = 0;
        for (int i = 0; i < colouredVertices.length; i++) {
            for (int j = 0; j < colouredVertices[i].length; j++) {
                numVerticesColoured++;
                int[] neighbours = colouredVertices[i][j].getNeighboursAsIntArray();
                for (int k = 0; k < neighbours.length; k++) {
                    for (int k2 = 0; k2 < colouredVertices[i].length; k2++) {
                        if (colouredVertices[i][j].equals(colouredVertices[i][k2])) continue;
                        if (neighbours[k]== colouredVertices[i][k2].identification()) return false;
                    }
                }
            }
        }
        System.out.println("number of vertices coloured: " + numVerticesColoured);
        if (numVerticesColoured < numberOfVertices) return false;
        return true; 
        
    }
    public int[][] conflicingVertices(Vertex[][] colouredVertices){ // creates a grid of ones and zeros where 1 represents that therer are conflicting vertices
        int[][] listofconflicVerticeVertices = new int[numberOfVertices][numberOfVertices]; 
        for (int i = 0; i < colouredVertices.length; i++) {
            for (int j = 0; j < colouredVertices[0].length; j++) {
                int[] neighbour = new int[colouredVertices[i][j].getNeighboursAsIntArray().length]; 
                for (int k = 0; k < neighbour.length; k++) {
                    if(neighbour[k] == colouredVertices[i][j].identification()){
                        listofconflicVerticeVertices[colouredVertices[i][j].identification()][neighbour[k]] = 1;  
                    }
                }
            }
        }
        return listofconflicVerticeVertices; 
    }

    
    public void startGame(){
        isGameRunning = true;
        String message = ""; 
        if(gamemode == 1){
           
     
        }
        if(gamemode == 2){

        }
        if(gamemode == 3){
            inGameRandomOrder = graphForGame.randomOrdering(); //should provide the random order
            
           
            
            if(isLegalColouring(vertexcolouring)&& currentChromaticNumber == chromaticNumber){
               message = "WOW! Well done! you have a correct coloring!"; 
            } else if(isLegalColouring(vertexcolouring)){
               message = "Well done! you have a legal coloring, but you used too many colors, the correct chromatic number is " + chromaticNumber + "while you used " + currentChromaticNumber;
            } else{
               message = "Unfortunately you do not have a legal coloring";
            }
            messageWhenDone = message; 
        }
    }

    public void pause() {
        isGameRunning = false;
    }

    public void resume() {
        isGameRunning = true;
    }

    public String getHint(){
        String hint = " "; 
         
        if (gamemode==1 || gamemode==2) {
           if (!isLegalColouring(vertexcolouring)) {
                int[][] tempArray = conflicingVertices(vertexcolouring); 
                for (int i = 0; i < tempArray.length; i++) {
                    for (int j = 0; j < tempArray.length; j++) {
                        if (tempArray[i][j]==1) {
                            hint = "Two connected vertices have the same color, vertex " + i + " and " + j + " are both the same color, switch one of these two to a new color.";  
                            return hint; 
                        }
                        
                    }
                }
           } 
           if (progress<numberOfVertices) {
             hint = "Your coloring is incomplete right now "; 
             for (int i = 0; i < vertexcolouring.length; i++) {
                for (int j = 0; j < vertexcolouring[0].length; j++) {
                    if(getColor(vertexcolouring[i][j])==-1){
                        int color = canAdd(vertexcolouring, vertexcolouring[i][j].getNeighboursAsVertexArray());
                        hint+= "vertex " + j + "is not coloured but can be assigned " + color + "."; 
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
                     hint += "vertex " + testcolouring[color][i] + " can be added to the " + addHere + "color."; 
                     return hint; 
                   }
                        
                }
                } else {
                    hint = "You are using too many colors! Try and find colors you can change, if you need more help press the hint button again"; 
                    oldCurrentChromaticNumber = currentChromaticNumber; 
            
                    return hint; 
                }
            
           
            
            
        }if (gamemode==3) {
            // the only hint they can receive is about the vertex they need to color right now, considering they cant go back and change the other values. 
             // this needs to be different but as of right now dont know how to let the current vertex be equal to the vertext they need for game 3
            Vertex[] neighbours = current.getNeighboursAsVertexArray();
            hint = "The neighbours of this vertex are ";  
            boolean hello = false; 
            for (int i = 0; i < neighbours.length; i++) {
               if (getColor(neighbours[i])!=-1) {
                hello = true; 
            }
                if (i==neighbours.length-1) {
                    hint += "and Vertex " + neighbours[i] + " with " + getColor(neighbours[i]) + " color.";  
                } else {
                    hint += "Vertex " + neighbours[i] + " with " + getColor(neighbours[i]) + " color ";  
                }
                }
                if (hello) {
                    hint += "We can ingore the vertices which we have not colored yet.";   
                }
                hint += "We know that this vertex cant have the same color as its neighbours, so try and color this vertex with a color you have used but does not violate this rule"; 
                return hint;
            

        }
        return hint; 
    }
    public void setCurrentVertex(Vertex V){
        current = V; 
    }
    public int canAdd(Vertex[][] current, Vertex[] neighbours){
        Vertex[][] coloring = current; 
        Vertex[] neigh = neighbours; 
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
    public int leastUsedColor(){
        
        int leastUsed = 100;  
        int least = 0;   
        for (int i = 0; i < vertexcolouring.length; i++) {
            int used = 0;
            for (int j = 0; j < vertexcolouring[0].length; j++) {
                
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
