module com.example.stokal {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.github.albfernandez.javadbf;


    opens com.example.stokal to javafx.fxml;
    exports com.example.stokal;
}