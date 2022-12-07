package graphvis.group30;
import java.util.concurrent.TimeUnit;

//import javax.swing.plaf.ProgressBarUI;
public class Game {
    public int gamemode; 
    public int numberOfEdges; 
    public int numberOfVertices; 
    public int timeLimit; 
    public Vertex[][] vertexcolouring = new Vertex[0][0]; 
    public int progress = 0; 
    public int chromaticNumber; 
    public int currentChromaticNumber; 
    public Graph graphForGame;  
    public String messageWhenDone; 
    public int input; // this will change to the correct data type, but I dont know how to use the input of the game yet so I use this as a place holder
    
     
    public void setGamemode(int i){
        gamemode = i; 
    }

    public Vertex getVertexFromID(int i) {
        return graphForGame.getVertexFromID(i);
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

    public void changeColour(Vertex a, int color){
        int currentColor = getColor(a); // this will check whether or not we can assign a value or if we have to change it. 

        if (vertexcolouring.length <= color) { // expands array to make space for a new color
            Vertex[][] newVertexColouring = new Vertex[color][];
            for (int i = 0; i < vertexcolouring.length; i++) {
                newVertexColouring[i] = new Vertex[vertexcolouring[i].length+1];
                System.arraycopy(vertexcolouring[i], 0, newVertexColouring[i], 0, vertexcolouring[i].length);
            }
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
        
    }

    public int colorsBeingUsed(){
        // I think this should just return vertexcolouring.length? - Nate
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
            for (int j = 0; j < vertexcolouring[0].length; j++) {
               if(vertexcolouring[i][j].equals(V)){
                    return i; 
               } 
            } 
        }
        return -1; // a value of negative -1 would mean a unnasigned color 
    }
    public boolean isLegalColouring(Vertex[][] colouredVertices) { // loops through the vertices to check if the curretn coloring is legal. 
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
        String message = ""; 
        if(gamemode == 1){
            long startTime = System.currentTimeMillis(); // gamemode 1 requires us to keep track of the time as they need to finish as fast as possible
            
           
            
           
            if(progress == numberOfVertices && isLegalColouring(vertexcolouring) && colorsBeingUsed() == chromaticNumber) {
                long endTime = System.currentTimeMillis();
                long timeElapsed = endTime - startTime;
            }
     
        }
        if(gamemode == 2){

        }
        if(gamemode == 3){
            Vertex[] inGameRandomOrder = graphForGame.randomOrdering(); //should provide the random order
            for (int i = 0; i < inGameRandomOrder.length; i++) { // then all they would do is go through them one by one 
                changeColour(inGameRandomOrder[i], input);  
            }
            
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
}
