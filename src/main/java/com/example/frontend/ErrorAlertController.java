package com.example.frontend;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ErrorAlertController {


    @FXML
    private Text reportText;
    @FXML
    private AnchorPane root;

    MainController mainController;

    public void setFontSize(Integer size)
    {
        root.setStyle("-fx-font-size: " + size);
    }

    public void setReportError(String reportMesssage)
    {
        reportText.setText(reportMesssage);
    }

    public void close(ActionEvent event)
    {
        Stage window = (Stage) reportText.getScene().getWindow();
        mainController.BackToNormalColor();
        mainController.stopError();
        window.close();
    }

    public void setMain(MainController mainController) {
        this.mainController = mainController;
    }
}
