module com.example.universitymanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.universitymanagementsystem to javafx.fxml;
    exports com.example.universitymanagementsystem;
}