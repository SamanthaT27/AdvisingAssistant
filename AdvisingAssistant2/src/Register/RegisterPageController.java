/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Register;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Leo
 */

 
public class RegisterPageController implements Initializable {

    @FXML
    private TextField First_Name_TxtBx;
    @FXML
    private TextField Last_name_TxtBx;
    @FXML
    private TextField ID_TxtBx;
    @FXML
    private RadioButton StudentRadioButton;
    @FXML
    private RadioButton StaffRadioButton;
    @FXML
    private Button Submit_button;
    @FXML
    private Text First_Name_Label;
    
   
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void StudentRadioAction(ActionEvent event) {
        
    }

    @FXML
    private void StaffRadioAction(ActionEvent event) {
    }
    
}
