module labryinth {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.project to javafx.fxml;
    requires org.controlsfx.controls;
    requires java.sql;
    requires org.apache.logging.log4j;
    exports com.project;
    exports com.project.Models;
    opens com.project.Models to javafx.fxml;

}