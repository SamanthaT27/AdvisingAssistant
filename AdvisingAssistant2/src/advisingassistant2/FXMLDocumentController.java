/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advisingassistant2;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Sammy
 */
public class FXMLDocumentController implements Initializable {
    static ObservableList list=FXCollections.observableArrayList();
    String username="Samantha";
    String password="123";
    
    static String uname="";

    @FXML
    private TextField user;
    @FXML
    private PasswordField pass;
    @FXML
    private Button loginUser;
 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    public static String getVariable(){
        return uname;
    }

    @FXML
    private void loginUser(ActionEvent event) throws IOException {
    String name=user.getText();
    String passw=pass.getText();
    if(name.isEmpty()|| passw.isEmpty()){
        Alert alert=new Alert(AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText("Fill all required fields!");
        alert.showAndWait();
    }else{
        if(name.equals(username)&&passw.equals(password)){
            FXMLDocumentController.uname=name;
            //code for the next page
            ((Node)event.getSource()).getScene().getWindow().hide();
            loadWindow("/LoggedIn/loggedIn.fxml","Logged In");
        }
        else{
        Alert alert=new Alert(AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText("Username or Password is incorrect!");
        alert.showAndWait();
        }
    }
    }
    private void loadWindow(String location,String title) throws IOException{
        Parent root=FXMLLoader.load(getClass().getResource(location));
        Scene scene = new Scene(root);
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
    }
    

}
