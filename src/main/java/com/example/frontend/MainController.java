package com.example.frontend;

import com.kodedu.terminalfx.TerminalBuilder;
import com.kodedu.terminalfx.TerminalTab;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.*;
import javafx.util.Duration;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.StyleClassedTextArea;
import com.kodedu.terminalfx.config.TerminalConfig;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.stream.Stream;

public class MainController {
    private File workingDirectory;

    @FXML
    private AnchorPane root;
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
    @FXML
    private TreeView<String> Tree;
    @FXML
    private Tab debugTab;
    @FXML
    private Tab terminalTab;
    @FXML
    private TabPane tabTopLeft;

    public static boolean errButton = false;

    public static int fontSize;


    private long currentTime; //handle double click for arborescence Tree
    private List<String> allFiles;

    public void setWorkingDirectory(File dir)
    {
        this.workingDirectory = dir;
    }

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
        fc.setInitialDirectory(workingDirectory);
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Haskell files (*.hs)", "*.hs");
        fc.getExtensionFilters().add(extFilter);
        File file = fc.showOpenDialog(null);
        CreateFile(file);
    }

    public void openProjectButtonAction(ActionEvent event) throws IOException {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        workingDirectory = directoryChooser.showDialog(null);
        if (workingDirectory == null)
        {
            return;
        }
        makeMyTree();
        myFiles.getTabs().clear();
    }

    public void deleteCurrentFileButtonAction(ActionEvent event)
    {
        int currentFileIndex = myFiles.getSelectionModel().getSelectedIndex();
        if (currentFileIndex == -1)
        {
            return;
        }
        myFiles.getTabs().remove(currentFileIndex);
    }

    public void CreateFile(File file)
    {
        if (file != null && !allFiles.contains(file.getAbsolutePath()))
        {
            allFiles.add(file.getAbsolutePath());
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

            tab.setContent(anchorPane);
            myFiles.getTabs().add(tab);
            myFiles.getSelectionModel().select(tab);
        }
    }

    @FXML private void initialize() throws IOException {
        menuBar.setPrefWidth(anchorMenu.getPrefHeight());
        DraggingTabPaneSupport support = new DraggingTabPaneSupport();
        support.addSupport(myFiles);
        allFiles = new ArrayList<String>();
        fontSize = 13;
        createTerminal();
    }

    public void makeMyTree() throws IOException {
        if (workingDirectory != null)
        {
            Tree.setRoot(treeFile(workingDirectory));
        }

    }

    public TreeItem<String> treeFile(File f) throws IOException {

        TreeItem<String> t;
        if (f.isDirectory() && f.getName().charAt(0) != '.')
        {
            t = new TreeItem<>(f.getName(), new ImageView(new Image(getClass().getResource("folder-icon.png").toExternalForm())));
            Stream<File> listFiles = Stream.of(f.listFiles());
            if (listFiles == null)
            {
                return t;
            }
            listFiles.forEach(subFile -> {
                try {
                    t.getChildren().add(treeFile(subFile));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        else
        {
            t = new TreeItem<>(f.getName());
        }
        return t;
    }

    public void selectItem()
    {
        long lastTime = currentTime;
        long diff = 0;

        currentTime=System.currentTimeMillis();

        if(lastTime !=0 && currentTime!=0){
            diff=currentTime- lastTime;

            if (diff<=215) {
                TreeItem<String> selectItem = Tree.getSelectionModel().getSelectedItem();
                if (selectItem == null)
                {
                    return;
                }
                List<String> pathArray = new ArrayList<>();
                TreeItem<String> parent = selectItem.getParent();
                while(selectItem != parent && parent != null)
                {

                    pathArray.add(0,selectItem.getValue());
                    parent = parent.getParent();
                    selectItem = selectItem.getParent();
                }
                String pathToFile = workingDirectory.getAbsolutePath();
                for (int i = 0; i < pathArray.size(); i++)
                {
                    pathToFile += "/";
                    pathToFile += pathArray.get(i);
                }
                File myFile = new File(pathToFile);
                if (myFile.isFile())
                {
                    CreateFile(myFile);
                }
            }

        }
    }
    public void SetFontSize(Integer size)
    {

        root.setStyle("-fx-font-size: " + size);
    }

    @FXML
    public void QuitAction(ActionEvent event)
    {
        Platform.exit();
    }

    @FXML
    public void SetFont(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("font-size.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        FontSizeController controller = (FontSizeController) fxmlLoader.getController();
        controller.setMain(this);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Font size");
        stage.setScene(new Scene(root1));
        stage.show();
        stage.setResizable(false);
    }

    public void createTerminal()
    {

    }
}
