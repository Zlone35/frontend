module com.example.frontend {
    requires javafx.controls;
    requires javafx.fxml;
<<<<<<< HEAD
    requires java.validation;
    requires org.apache.commons.io;
    requires org.apache.commons.lang3;
    requires org.eclipse.jgit;
    requires lombok;
=======
    requires java.logging;
    requires org.fxmisc.richtext;
>>>>>>> 134475e (push after the appointment)


    opens com.example.frontend to javafx.fxml;
    exports com.example.frontend;
    exports com.example.frontend.myide;
    opens com.example.frontend.myide to javafx.fxml;
}