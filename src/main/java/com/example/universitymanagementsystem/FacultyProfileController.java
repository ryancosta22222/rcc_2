package com.example.universitymanagementsystem;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.Optional;

public class FacultyProfileController {

    @FXML private Label nameLabel;
    @FXML private Label degreeLabel;
    @FXML private Label researchLabel;
    @FXML private Label emailLabel;
    @FXML private Label officeLabel;
    @FXML private Label coursesLabel;
    @FXML private ImageView profilePhotoView;

    @FXML private TextField newPasswordField;
    @FXML private Button updatePasswordButton;
    @FXML private Button uploadPhotoButton;

    private FacultyManagementController.Faculty myFaculty;

    @FXML
    private void initialize(){
        // Get the logged-in user (assuming role FACULTY and username equals faculty email)
        User currentUser = Session.getInstance().getUser();
        if(currentUser != null && currentUser.getRole().equalsIgnoreCase("FACULTY")){
            Optional<FacultyManagementController.Faculty> optFaculty = FacultyManagementController.getFacultyList().stream()
                    .filter(f -> f.getEmail().equalsIgnoreCase(currentUser.getUsername()))
                    .findFirst();
            if(optFaculty.isPresent()){
                myFaculty = optFaculty.get();
                nameLabel.setText(myFaculty.getName());
                degreeLabel.setText(myFaculty.getDegree());
                researchLabel.setText(myFaculty.getResearchInterest());
                emailLabel.setText(myFaculty.getEmail());
                officeLabel.setText(myFaculty.getOfficeLocation());
                coursesLabel.setText(myFaculty.getCoursesOffered());
                // Load default profile photo from resources (/images/default_faculty.png)
                String photoPath = getClass().getResource("/images/default_faculty.png").toExternalForm();
                profilePhotoView.setImage(new Image(photoPath));
            } else {
                nameLabel.setText("Faculty profile not found.");
            }
        }
    }

    @FXML
    private void handleUpdatePassword(){
        String newPass = newPasswordField.getText().trim();
        if(newPass.isEmpty()){
            showAlert(Alert.AlertType.WARNING, "Input Error", "Please enter a new password.");
            return;
        }
        // In a real application, update the password in the user database.
        showAlert(Alert.AlertType.INFORMATION, "Password Updated", "Your password has been updated.");
        newPasswordField.clear();
    }

    @FXML
    private void handleUploadPhoto(){
        // In a real application, use a FileChooser to update the profile photo.
        showAlert(Alert.AlertType.INFORMATION, "Upload Photo", "Profile photo upload functionality not implemented in demo.");
    }

    private void showAlert(Alert.AlertType type, String title, String message){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
