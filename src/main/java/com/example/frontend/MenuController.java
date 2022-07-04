package com.example.frontend;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MenuController {


    @FXML
    Button OpenDirectoryButton;

    public void OpenDirectory(ActionEvent event) throws Exception {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File workingDir = directoryChooser.showDialog(null);
        if (workingDir == null)
        {
            return;
        }

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main.fxml"));
        Stage window = (Stage) OpenDirectoryButton.getScene().getWindow();
        window.setScene(new Scene(fxmlLoader.load(), 600, 400));
        MainController shesh = (MainController) fxmlLoader.getController();
        shesh.setWorkingDirectory(workingDir);
        window.setResizable(true);
        window.setMaximized(true);
        shesh.makeMyTree();
    }
}
