package graphvis.group30;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class popUp {

    public void display(String message){

        Stage popUp = new Stage();
        StackPane layout = new StackPane();
        Scene startingScene = new Scene(layout, 200, 200);
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

