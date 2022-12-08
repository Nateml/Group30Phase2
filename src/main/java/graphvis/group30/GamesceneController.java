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
        newHint.display(hint, 300, 300);
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
            Frontend.gameController.setCurrentVertex(currentVertex);
        if(currentColorID==-1){
            String error = "Select a color to color this vertex"; 
            popUp newError = new popUp();
            newError.display(error, 200, 200); 
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

