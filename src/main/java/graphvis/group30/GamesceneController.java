package graphvis.group30;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;


public class GamesceneController {
    VertexVisual currentVertex;
    Color[] color; 
    Color currentColor; 
    int currentColorID = -1; 

    @FXML Pane paneGraph;
    @FXML ColorPicker colorPicker;
    @FXML Label lblGraphColoured;

    public void initialize() {
        if (Frontend.graphView == null) {
            Frontend.graphView = new GraphView(Frontend.graph);
        } else {
            Frontend.graphView.update();
        }
        paneGraph.getChildren().add(Frontend.graphView.getAnchorPane());
        Frontend.colorPicker = colorPicker;
        Frontend.lblGraphColoured = lblGraphColoured;
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

    public void updateGraphView() {
        /* 
        paneGraph.getChildren().clear();
        Frontend.graphView.update();
        paneGraph.getChildren().add(Frontend.graphView.getAnchorPane());
        System.out.println();
        */
        //paneGraph.getChildren().add(Frontend.graphView.getAnchorPane());
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

