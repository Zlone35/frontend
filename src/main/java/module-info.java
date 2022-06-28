module com.example.frontend {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.validation;
    requires org.apache.commons.io;
    requires org.apache.commons.lang3;
    requires org.eclipse.jgit;
    requires lombok;


    opens com.example.frontend to javafx.fxml;
    exports com.example.frontend;
}