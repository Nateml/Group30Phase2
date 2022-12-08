package graphvis.group30;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;


public class GamesceneController {
    VertexVisual currentVertex;
    Color[] color; 
    Color currentColor; 
    int currentColorID = -1; 

    @FXML Pane paneGraph;
    @FXML ColorPicker colorPicker;

    public void initialize() {
        if (Frontend.graphView == null) {
            Frontend.graphView = new GraphView(Frontend.graph);
        }
        paneGraph.getChildren().add(Frontend.graphView.getAnchorPane());
        Frontend.colorPicker = colorPicker;
        /* 
        paneGraph.setOnKeyPressed((KeyEvent e) -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                System.out.println("Escape clicked");
                try {
                    btnPauseClicked();

                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                };
            }
        });
        */
    }

    public void btnPauseClicked() throws IOException {
        Frontend.gameController.pause();
        Frontend.setRoot("pausescene");
    }

    public void btnHintClicked() {
        String hint = Frontend.gameController.getHint();
        popUp newHint = new popUp();
        newHint.display(hint);
    }
    
    public void btnSelectColourClicked() {
        
        
        currentColor = Color.ORANGE; //needs to match the input of the color picker  
        
        
        if (Frontend.gameController.gamemode == 3) {
            if(currentVertex!=vertexHighlighted){

            }else {
            boolean colorNotUsed = true; 
            for (int i = 0; i < color.length; i++) {
                if (color[i]==currentColor) {
                    currentColorID=i; 
                    colorNotUsed = false; 
                } 
            }
            if (colorNotUsed) {
                Color[] newColor = new Color[color.length + 1];
            for(int i = 0; i < color.length ; i++){
                newColor[i] = color[i];
            }
            newColor[color.length] = currentColor;
            color = newColor; 
            currentColorID = newColor.length; 
            }
            Frontend.GameController.inGameRandomOrder[0]; 
            
        }}
            boolean colorNotUsed = true; 
        for (int i = 0; i < color.length; i++) {
            if (color[i]==currentColor) {
                currentColorID=i; 
                colorNotUsed = false; 
            } 
        }
        if (colorNotUsed) {
            Color[] newColor = new Color[color.length + 1];
        for(int i = 0; i < color.length ; i++){
            newColor[i] = color[i];
        }
        newColor[color.length] = currentColor;
        color = newColor; 
        currentColorID = newColor.length; 
        }
        }
        

        
    
        public void btnSlectVertexClicked(){
        if (Frontend.gameController.gamemode == 1 || Frontend.gameController.gamemode == 2) {
            Frontend.gameController.setCurrentVertex(currentVertex);
            if(currentColorID==-1){
                String error = "Select a color to color this vertex"; 
                popUp newError = new popUp();
                newError.display(error); 
            } else {
                for (int i = 0; i < color.length; i++) {
                    if (color[i]==currentColor) {
                    Frontend.gameController.changeColour(currentVertex, currentColorID); 
                    currentVertex.setColour(currentColor); 
                    }
                }   
            }
        } else {
            String gameThree = "You can not slect which vertex you want to color in this gamemode, pls select the color you wish the highlighted vertex to have"; 
            popUp newHint = new popUp();
            newHint.display(gameThree);
        }
           
    }    
    }

