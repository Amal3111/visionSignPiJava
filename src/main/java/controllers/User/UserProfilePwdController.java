package controllers.User;

import io.github.palexdev.materialfx.controls.MFXPasswordField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import services.User.UserService;
import entities.User;

import java.io.IOException;
import java.sql.SQLException;

public class UserProfilePwdController {
    @FXML
    private MFXPasswordField newPwdTF1;

    @FXML
    private MFXPasswordField newPwdTF2;

    @FXML
    private MFXPasswordField oldPwdTF;
    @FXML
    private Label userTF;
private final UserService userService=new UserService();
@FXML
void initialize(){
    userTF.setText(userService.getCurrent().getUsername());
}
    @FXML
    void modifier(ActionEvent event) {
        String validationError = validateInputs();
        User user=userService.getCurrent();

        try{

            if (oldPwdTF.getText().equals(user.getPassword()) || user.getPassword().equals(" ")){
                if(newPwdTF1.getText().equals(newPwdTF2.getText())){
                    if (validationError.isEmpty()){
                        user.setPassword(newPwdTF2.getText());
                    userService.updatePassword(user);
                    showAlertSuccess("Success", "Password Changed", "Password changed successfully.");
                    }else{showAlertError("Validation Error","",validationError);}

                }

                else
                    showAlertError("Error", "Password Mismatch", "Passwords don't match.");
            }
            else
                showAlertError("Error", "Incorrect Password", "Wrong password.");
        }catch(SQLException e){
            showAlertError("Error","" , e.getMessage());
        }

    }
    @FXML
    void profileButton(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/UserProfile.fxml"));
            Parent profileRoot = loader.load();
            ScrollPane profileScrollPane=new ScrollPane(profileRoot);
            profileScrollPane.setFitToWidth(true);
            profileScrollPane.setFitToHeight(true);
            newPwdTF1.getScene().setRoot(profileScrollPane);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }}
    @FXML
    void privacyButton(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/UserProfilePrivacy.fxml"));
            Parent privacyRoot = loader.load();
            ScrollPane privacyScrollPane=new ScrollPane(privacyRoot);
            privacyScrollPane.setFitToWidth(true);
            privacyScrollPane.setFitToHeight(true);
            newPwdTF1.getScene().setRoot(privacyScrollPane);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }}

    private void showAlertError(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private void showAlertSuccess(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }


    private String validateInputs() {
        StringBuilder validationError = new StringBuilder();
        String password = newPwdTF1.getText();
        if (password.length() < 8 ||
                !password.matches(".*[a-z].*") ||  // At least one lowercase letter
                !password.matches(".*[A-Z].*") ||  // At least one uppercase letter
                !password.matches(".*\\d.*")) {    // At least one digit
            validationError.append("Password should have at least 8 characters, including at least one uppercase letter, one lowercase letter, and one number.\n");
    }
        return validationError.toString();

}}

