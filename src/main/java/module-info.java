module com.example.frontend {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.validation;
    requires org.apache.commons.io;
    requires org.apache.commons.lang3;
    requires org.eclipse.jgit;
    requires lombok;
    requires java.logging;
    requires org.fxmisc.richtext;
    requires java.desktop;
    requires org.controlsfx.controls;
    requires terminalfx;

    opens com.example.frontend to javafx.fxml;
    exports com.example.frontend;
    exports com.example.frontend.myide;
    opens com.example.frontend.myide to javafx.fxml;
}