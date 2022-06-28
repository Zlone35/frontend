package com.example.frontend;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MenuController {

    public List<File> selectedFiles = new ArrayList<>();
    @FXML
    private MenuItem openFileButton;

    public void openFileButtonAction(ActionEvent event)
    {
        FileChooser fc = new FileChooser();
        File file = fc.showOpenDialog(null);
        if (file != null)
        {
            selectedFiles.add(file);
        }
        else
        {
            System.out.println("Shesh");
        }
        for (File f : selectedFiles
             ) {
            System.out.println(f.getName());
        }
    }
}
