package graphvis.group30;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.text.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;


public class popUp {

    public void display(String message, int width, int height){

        Stage popUp = new Stage();
        StackPane layout = new StackPane();
        Scene startingScene = new Scene(layout, width, height);
        popUp.initModality(Modality.APPLICATION_MODAL);

        Label messageLabel = new Label(message);
        Button closeButton = new Button("ok");

        closeButton.setOnAction(e -> {
            popUp.close();
        });

        layout.getChildren().addAll(messageLabel);
        layout.getChildren().addAll(closeButton);

        popUp.setScene(startingScene);
        popUp.show();
    }
    
}

