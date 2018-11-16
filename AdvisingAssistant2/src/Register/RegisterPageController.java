/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Register;

import connectivity.ConnectionClass;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javax.xml.bind.DatatypeConverter;
/**
 * FXML Controller class
 *
 * @author Leo
 */

 
public class RegisterPageController implements Initializable {
    ObservableList<String> Major_list = FXCollections.observableArrayList("Computer Science", "Criminal Justice");
    ObservableList<String> Department_list = FXCollections.observableArrayList("Computer Science", "Criminal Justice");
    
    
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
    @FXML
    private TextField Fid_Txtbx;
    @FXML
    private ChoiceBox<String> M_MenuButton;
    @FXML
    private ChoiceBox<String> Dep_MenuButton;
    @FXML
    private PasswordField Password_Txtbx;
    

    
    
   
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        M_MenuButton.setItems(Major_list);
        Dep_MenuButton.setItems(Department_list);
        StudentRadioButton.setSelected(true);
        Fid_Txtbx.setDisable(true);
        Dep_MenuButton.setDisable(true);        
    }    

    @FXML
    private void StudentRadioAction(ActionEvent event) {
       if(StudentRadioButton.isArmed())
       {
           StaffRadioButton.setSelected(false);
           Fid_Txtbx.setDisable(true);
           Dep_MenuButton.setDisable(true);
          ID_TxtBx.setDisable(false);
           M_MenuButton.setDisable(false);
       }
       
    }

    @FXML
    private void StaffRadioAction(ActionEvent event) {
        if(StaffRadioButton.isArmed())
        {
            StudentRadioButton.setSelected(false);
            ID_TxtBx.setDisable(true);
            M_MenuButton.setDisable(true);
            Fid_Txtbx.setDisable(false);
            Dep_MenuButton.setDisable(false);
        }
        
    }
    
    @FXML
    private void SubmitAction(ActionEvent event) throws NoSuchAlgorithmException, SQLException
    {
        ConnectionClass connectionClass=new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        Statement statement = connection.createStatement();
        
         String fname=First_Name_TxtBx.getText();
         String lname=Last_name_TxtBx.getText();
         String pass = hashPass(Password_Txtbx.getText());
        
        if (StaffRadioButton.isSelected())
        {
           try{
            String Ffid=Fid_Txtbx.getText();
            int fid = Integer.parseInt(Fid_Txtbx.getText());
            String dept = "Criminal Justice"; //Dep_MenuButton.getSelectionModel().getSelectedItem().toString();
            String fn = fname.substring(0, 1);
            String ln = lname.substring(0,1);
            String fuser = fn+ln+Ffid;
            
            String sqlSelect="Select fid FROM FacultyList Where fname = '"+fname+"' AND lname='"+lname+"';";
            ResultSet FID=statement.executeQuery(sqlSelect);
            String DBfid;
            if (FID.next())//Checks to make sure there is a matching fid
           {
               DBfid = FID.getString(1);
                int dfid=Integer.parseInt(DBfid);

                if (dfid==fid)
               {
                    try{
                    String fsql = "Insert into Faculty Values ('"+fuser+"','"+pass+"','"+fname+"','"+lname+"','"+fid+"','"+dept+"');";
                    statement.executeUpdate(fsql);
                    }
                    catch(Exception e)
                    {
                        Alert alert=new Alert(Alert.AlertType.ERROR);//If the username and password are incorrect then an error appears
                        alert.setHeaderText(null);
                        alert.setContentText("Faculty user exists!");
                        alert.showAndWait();
                    }
                }
                else
                {
                    Alert alert=new Alert(Alert.AlertType.ERROR);//If the username and password are incorrect then an error appears
                    alert.setHeaderText(null);
                    alert.setContentText("Faculty IDs do not match!");
                    alert.showAndWait();
                }
           }
            else
            {
                Alert alert=new Alert(Alert.AlertType.ERROR);//If the username and password are incorrect then an error appears
                alert.setHeaderText(null);
                alert.setContentText("Not a registered Faculty Member!");
                alert.showAndWait();
            }
           }
           catch (Exception e)
           {
                Alert alert=new Alert(Alert.AlertType.ERROR);//If the username and password are incorrect then an error appears
                alert.setHeaderText(null);
                alert.setContentText("Fill in empty spaces!");
                alert.showAndWait();
           }
            
        }
        else
        {
           try{//makes sure there isn't a null value
               String major = "Computer Science";//M_MenuButton.getSelectionModel().getSelectedItem().toString();
           String Ssid = ID_TxtBx.getText();
          int sid;
           try{
           sid = Integer.parseInt(ID_TxtBx.getText());
           String f = fname.substring(0, 1);
            String l = lname.substring(0, 1);
           String suser = f+l+Ssid;
           try{
           String Ssql = "Insert into Student Values ('"+suser+"','"+pass+"','"+fname+"','"+lname+"','"+major+"',"+sid+")";
           statement.executeUpdate(Ssql);
           
           String newTable="Create Table "+suser+"(course Varchar(4), courseNum Varchar(4), courseName Varchar(30), credit int);";
           statement.executeUpdate(newTable);
           }
           catch(Exception e)
           {
               Alert alert=new Alert(Alert.AlertType.ERROR);//If the username and password are incorrect then an error appears
                alert.setHeaderText(null);
                alert.setContentText("User exists!");
                alert.showAndWait();
           }
           }
           catch(Exception e)
           {
               Alert alert=new Alert(Alert.AlertType.ERROR);//If the username and password are incorrect then an error appears
                alert.setHeaderText(null);
                alert.setContentText("Enter a number as the ID!");
                alert.showAndWait();
           }
           
           }
           catch(Exception e)
           {
               Alert alert=new Alert(Alert.AlertType.ERROR);//If the username and password are incorrect then an error appears
                alert.setHeaderText(null);
                alert.setContentText("Fill in empty spaces!");
                alert.showAndWait();
           }
    }
    }
    private String hashPass(String p) throws NoSuchAlgorithmException
    {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] digest=md.digest(p.getBytes(StandardCharsets.UTF_8));
        String pass = DatatypeConverter.printHexBinary(digest).toLowerCase();
        return pass;
    }
}
