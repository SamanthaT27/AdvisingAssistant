/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LoggedIn;

import advisingassistant2.FXMLDocumentController;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Sammy
 */
public class LoggedInController implements Initializable {

    @FXML
    private Text names;
    @FXML
    private Tab Home_tab;
    @FXML
    private Tab GenCore_tab;
    @FXML
    private Tab Resource_tab;
    @FXML
    private Tab Major_tab;
    @FXML
    private Tab Transcript_tab;
    @FXML
    private Tab Recommendation_tab;
    @FXML
    private Tab Schedule_tab;
    @FXML
    private Text Date_txt;
  
    


    @FXML
    void Home_tab_action(ActionEvent event) {
       
    }
    
        /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        String user = FXMLDocumentController.getVariable();//Calls method from FXMLDocumentController getVariable
        names.setText("Welcome, "+user);//outputs the message welcome,user; user being the values that are given in the text field 
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyy HH:mm:ss");
        Date date = new Date();
        Date_txt.setText("Today's date is: " + dateFormat.format(date));        
    }        
    
}
