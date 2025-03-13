package com.example.universitymanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class UserDashboardController {

    @FXML
    private void openCourseManagement(ActionEvent event) throws IOException {
        navigateTo(event, "/fxml/CourseManagement.fxml");
    }

    @FXML
    private void openEventManagement(ActionEvent event) throws IOException {
        System.out.println("openEventManagement triggered."); // Debug statement to verify the method is called
        navigateTo(event, "/fxml/UserEvents.fxml");
    }

    private void navigateTo(ActionEvent event, String fxmlFile) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
