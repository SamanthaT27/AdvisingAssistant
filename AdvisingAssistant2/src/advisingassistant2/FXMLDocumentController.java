/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advisingassistant2;

import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Sammy
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Button openLogin;
      @FXML
    private TextField UsernameBox;

    @FXML
    private PasswordField PasswordBox;

    @FXML
    private Text UsernameLabel;

    @FXML
    private Text PasswordLabel;

    @FXML
    private Text WelcomeLabel;

    @FXML
    private Button resgisterButton;

    @FXML
    void handleButtonAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("loginPage.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOpacity(1);
            stage.setTitle("Login Page");
            stage.setScene(new Scene(root1,450,450));
            stage.show();
        } catch (Exception e) {
            System.out.println("Can't load Login Window");
        }

    }
     @FXML
    void openRegistration(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader1 = new FXMLLoader(getClass().getResource("registerPage.fxml"));
            Parent root2 = (Parent) fxmlLoader1.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOpacity(1);
            stage.setTitle("Registration Page");
            stage.setScene(new Scene(root2,450,450));
            stage.show();
        } catch (Exception ex) {
            System.out.println("Can't load Register Window");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
