package com.example.frontend;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FontSizeController {
    @FXML
    private Spinner<Integer> fontSpinner;
    @FXML
    private VBox vBox;
    @FXML
    private Text text;
    @FXML
    private AnchorPane root;
    @FXML
    private Integer currentsize;

    private MainController mainController;

    @FXML private void initialize() {

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 42);
        valueFactory.setValue(MainController.fontSize);
        fontSpinner.setValueFactory(valueFactory);
        currentsize = 13;
        changeVBox();
        fontSpinner.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observableValue, Integer integer, Integer t1) {
                int size = fontSpinner.getValue();
                mainController.SetFontSize(size);
                MainController.fontSize = size;
                root.setStyle("-fx-font-size: " + size);
                if (currentsize > size)
                {
                    root.setMaxSize(root.getPrefHeight() - 20,root.getPrefWidth() - 20);
                }
                else
                {
                    root.setMaxSize(root.getPrefHeight() + 20,root.getPrefWidth() + 20);
                }
                currentsize = size;

            }
        });
    }



    @FXML public void CloseAction(ActionEvent event)
    {
        Stage window = (Stage) vBox.getScene().getWindow();
        window.close();
    }

    private void changeVBox()
    {
        vBox.setSpacing(8);
    }
    public void setMain(MainController mainController)
    {
        this.mainController = mainController;
    }
}
