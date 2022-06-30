package com.example.frontend;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.css.StyleClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.StyleClassedTextArea;
import org.fxmisc.richtext.StyledTextArea;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

import java.util.logging.*;
import static java.util.logging.Level.SEVERE;


public class MainController {
    private File loadedFileReference;
    private FileTime lastModifiedTime;
    @FXML
    private SplitPane rightSplitPane;
    @FXML
    private Button loadChangesButton;
    @FXML
    private Label statusMessage;
    @FXML
    private TextArea textArea;
    @FXML
    private AnchorPane terminalArea;
    @FXML
    private AnchorPane debugArea;
    @FXML
    private AnchorPane treeArea;
    @FXML
    private TabPane myFiles;
    @FXML
    private AnchorPane anchorMenu;
    @FXML
    private MenuBar menuBar;

    public static boolean errButton = false;

    public void BackToNormalColor()
    {
        terminalArea.setStyle("-fx-background-color: #93A007;");
        debugArea.setStyle("-fx-background-color:  #A3AF17;");
        myFiles.setStyle("-fx-background-color: #E7E73C#E7E73C;");
        treeArea.setStyle("-fx-background-color: #D9D029;");
        for (int i = 0; i < myFiles.getTabs().size();i++)
        {
            AnchorPane anchorPane = (AnchorPane) myFiles.getTabs().get(i).getContent();
            anchorPane.getChildren().get(0).setStyle("-fx-background-color: #E7E73C;");
        }
    }

    public void ErrorColor()
    {
        terminalArea.setStyle("-fx-background-color: #096A09;");
        debugArea.setStyle("-fx-background-color: #096A09;");
        myFiles.setStyle("-fx-background-color: #357AB7;");
        treeArea.setStyle("-fx-background-color: #DC143C;");
        for (int i = 0; i < myFiles.getTabs().size();i++)
        {
            AnchorPane anchorPane = (AnchorPane) myFiles.getTabs().get(i).getContent();
            anchorPane.getChildren().get(0).setStyle("-fx-background-color: #357AB7;");
        }
    }

    Timeline err = new Timeline(new KeyFrame(Duration.millis(150),
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

    /*
    MENU AREA
    */

    public List<Tab> selectedFiles = new ArrayList<>();

    public void openFileButtonAction(ActionEvent event)
    {
        FileChooser fc = new FileChooser();

        File file = fc.showOpenDialog(null);

        if (file != null)
        {
            Tab tab = new Tab(file.getName());
            tab.getStyleClass().add("tab-header-background");
            tab.getStyleClass().add("tab-label");
            tab.setStyle("-fx-background-color: #FAFA64");
            AnchorPane anchorPane = new AnchorPane();
            anchorPane.setStyle("-fx-background-color: #FFFFFF");
            StyleClassedTextArea styleClassedTextArea = new StyleClassedTextArea();
            anchorPane.getChildren().add(styleClassedTextArea);
            AnchorPane.setBottomAnchor(styleClassedTextArea, 0.0);
            AnchorPane.setLeftAnchor(styleClassedTextArea, 0.0);
            AnchorPane.setRightAnchor(styleClassedTextArea, 0.0);
            AnchorPane.setTopAnchor(styleClassedTextArea, 0.0);
            try {
                Scanner scanner = new Scanner(file);
                while(scanner.hasNextLine())
                {
                    styleClassedTextArea.appendText(scanner.nextLine() + "\n");
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            styleClassedTextArea.setParagraphGraphicFactory(LineNumberFactory.get(styleClassedTextArea));
            styleClassedTextArea.setStyle("-fx-background-color:  #E7E73C; -fx-font-family: 'Apple Braille';");
            styleClassedTextArea.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

            //anchorPane.setStyle("-fx-background-color: #635179#635179");
            tab.setContent(anchorPane);
            myFiles.getTabs().add(tab);

            //loadFileToTextArea(file);
        }
    }

    @FXML private void initialize()
    {
        menuBar.setPrefWidth(anchorMenu.getPrefHeight());
    }
}
