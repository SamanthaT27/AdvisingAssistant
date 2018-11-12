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
    static ObservableList list=FXCollections.observableArrayList();//carries data like an arrya but observes changes and makes changes within the array
    String username="Samantha";//set values fro username and password-must be reworked for database use
    String password="123";
    
    static String uname=""; //makes a variable uname to be used to store the value from the text field for the next page to use 

    @FXML
    private TextField user;
    @FXML
    private PasswordField pass;
    @FXML
    private Button loginUser;
    @FXML
    private Button RegisterButton;
 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    public static String getVariable(){//method to get the username that is entered in the text field 
        return uname;
    }

    @FXML
    private void loginUser(ActionEvent event) throws IOException {//This is the login buttons event handler 
    String name=user.getText();//gets the username and password text and stores them for later use on verification
    String passw=pass.getText();
    if(name.isEmpty()|| passw.isEmpty()){//If the username and password are empty and error is made with the message all fields are required 
        Alert alert=new Alert(AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText("Fill all required fields!");
        alert.showAndWait();
    }else{
        if(name.equals(username)&&passw.equals(password)){//If the username and password are correct and match the set values 
            FXMLDocumentController.uname=name;//this is to be able to set uname to name and be seen on the next page 
            //code for the next page
            ((Node)event.getSource()).getScene().getWindow().hide();//this is to be able to load to the new page and hide the previous page
            loadWindow("/LoggedIn/loggedIn.fxml","Logged In");//calling method loadwindow with the relevant filenamelocation and filename.fxml and the title of the window
        }
        else{
        Alert alert=new Alert(AlertType.ERROR);//If the username and password are incorrect then an error appears
        alert.setHeaderText(null);
        alert.setContentText("Username or Password is incorrect!");
        alert.showAndWait();
        }
    }
    }
    
    @FXML
    void OpenRegister(ActionEvent event) throws IOException {
        ((Node)event.getSource()).getScene().getWindow().hide();//this is to be able to load to the new page and hide the previous page
            loadWindow("/Register/RegisterPage.fxml","Register");
            
            

    }
    
    private void loadWindow(String location,String title) throws IOException{//method to be able to open a new window, can be used multiple times 
        Parent root=FXMLLoader.load(getClass().getResource(location));
        Scene scene = new Scene(root);
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
    }
    

}
