package com.example.frontend;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class ErrorController {
    @FXML
    private Button errorButton;
    @FXML
    private AnchorPane terminalArea;
    @FXML
    private AnchorPane debugArea;
    @FXML
    private AnchorPane treeArea;
    @FXML
    private AnchorPane codeArea1;
    @FXML
    private AnchorPane codeArea2;

    public static boolean errButton = false;

    public void BackToNormalColor()
    {
        codeArea1.setStyle("-fx-background-color: #6E43A7;");
        terminalArea.setStyle("-fx-background-color: #362152;");
        debugArea.setStyle("-fx-background-color: #472E68;");
        codeArea2.setStyle("-fx-background-color:  #6E43A7;");
        treeArea.setStyle("-fx-background-color:  #623B95;");
    }

    public void ErrorColor()
    {
        codeArea1.setStyle("-fx-background-color: #357AB7;");
        terminalArea.setStyle("-fx-background-color: #096A09;");
        debugArea.setStyle("-fx-background-color: #096A09;");
        codeArea2.setStyle("-fx-background-color: #357AB7;");
        treeArea.setStyle("-fx-background-color: #DC143C;");
    }

    Timeline err = new Timeline(new KeyFrame(Duration.millis(500),
            new EventHandler<ActionEvent>() {
                private int i = 0;
                @Override
                public void handle(ActionEvent event) {
                    if (i % 2 == 0) {
                        ErrorColor();
                    }
                    else
                    {
                        BackToNormalColor();
                    }
                    i++;
                }
            }));


    public void errorButtonOnAction(ActionEvent event)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR,"Compilation error", ButtonType.CLOSE);
        alert.setTitle("Rapport de compilation");
        alert.setHeaderText("Erreur de compilation");
        alert.setContentText("nom de l'erreur");
        err.setCycleCount(Timeline.INDEFINITE);
        err.play();
        errButton = !errButton;
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.CLOSE)
        {
            err.stop();
            BackToNormalColor();
        }

    }

}
