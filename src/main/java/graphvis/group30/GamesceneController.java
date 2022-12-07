package graphvis.group30;

import java.io.IOException;

import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;


public class GamesceneController {
    VertexVisual currentVertex;
    Color[] color; 
    Color currentColor; 
    int currentColorID = -1; 
    public void btnPauseClicked() throws IOException {
        Frontend.setRoot("pausescene");
    }

    public void btnHintClicked() {
        String hint = Frontend.gameController.getHint();
        popUp newHint = new popUp();
        newHint.display(hint);
    }

    public void btnSelectColourClicked() {
        ColorPicker pick = new ColorPicker(); 
        
        currentColor = Color.ORANGE; //needs to match the input of the color picker  
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
    }    
    }

