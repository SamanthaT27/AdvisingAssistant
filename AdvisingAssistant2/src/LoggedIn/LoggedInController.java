/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LoggedIn;

import advisingassistant2.FXMLDocumentController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Sammy
 */
public class LoggedInController implements Initializable {

    @FXML
    private Text names;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        String user = FXMLDocumentController.getVariable();//Calls method from FXMLDocumentController getVariable
        names.setText("Welcome, "+user);//outputs the message welcome,user; user being the values that are given in the text field 
    }    
    
}
