/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Register;

import connectivity.ConnectionClass;
import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
    @FXML
    private Button backButton;
    @FXML
    private PasswordField Password_Conformation;

    

    
    
   
    
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
           //if StudentRadioButton is selected staff buttons are disabled
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
            //if StaffRadioButton is selected student buttons are disabled
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
        //Action on click connects to database and adds user; Faculty are added if they are on the Faculty list
        ConnectionClass connectionClass=new ConnectionClass();//used to connect to the database
        Connection connection = connectionClass.getConnection();
        Statement statement = connection.createStatement();//statement is used to send sql statements to the database
        
         String fname=First_Name_TxtBx.getText();//gets first name
         String lname=Last_name_TxtBx.getText();//gets last name
         String pass = null; //creates the password text that will be passed on to the databse
         String FirstPassword = Password_Txtbx.getText(); //gets the password
         String SecondPassword = Password_Conformation.getText();//gets the password to confirm and compare
         
        try{
            if(!(FirstPassword.equals(SecondPassword)))
           {
              Alert alert=new Alert(Alert.AlertType.ERROR);//there are any empty spaces
              alert.setHeaderText(null);
                alert.setContentText("There may be an empty field or your password do not match.");
               alert.showAndWait(); 
           }
        }  
        catch(Exception t)
       {           
                 
            pass = hashPass(Password_Txtbx.getText());//hashes the password to be stored in the database
         
        if (StaffRadioButton.isSelected())//if Staff is selected
        {
           try{
            String Ffid=Fid_Txtbx.getText();
            int fid = Integer.parseInt(Fid_Txtbx.getText());
            String dept = Dep_MenuButton.getSelectionModel().getSelectedItem();//gets department
            String fn = fname.substring(0, 1);//grabs first intial from first name
            String ln = lname.substring(0,1);//grabs first initial from last name
            String fuser = fn+ln+Ffid;//username is created by adding first initials of both first name and last name with the id 
            
            //sql statement executed to find the id to be compared for faculty verification
            String sqlSelect="Select fid FROM FacultyList Where fname = '"+fname+"' AND lname='"+lname+"';";
            ResultSet FID=statement.executeQuery(sqlSelect);
            String DBfid;
            if (FID.next())//Checks to make sure there is a matching fid
           {
               DBfid = FID.getString(1);//gives string value of the ID to DBfid
                int dfid=Integer.parseInt(DBfid);//converts the string to and int prior to comparison

                if (dfid==fid)//compares ID found in the DB to what was entered
               {
                    try{
                        //inserts a new row into the faculty table containing the login information
                    String fsql = "Insert into Faculty Values ('"+fuser+"','"+pass+"','"+fname+"','"+lname+"','"+fid+"','"+dept+"');";
                    statement.executeUpdate(fsql);
                    }
                    catch(Exception e)
                    {
                        Alert alert=new Alert(Alert.AlertType.ERROR);//If the user already exists, it appears
                        alert.setHeaderText(null);
                        alert.setContentText("Faculty user exists!");
                        alert.showAndWait();
                    }
                }
                else
                {
                    Alert alert=new Alert(Alert.AlertType.ERROR);//If the user id does not match faculty member, it appears
                    alert.setHeaderText(null);
                    alert.setContentText("Faculty IDs do not match!");
                    alert.showAndWait();
                }
           }
            else
            {
                Alert alert=new Alert(Alert.AlertType.ERROR);//Appears of user is not found on the faculty list used to verify who are the professors
                alert.setHeaderText(null);
                alert.setContentText("Not a registered Faculty Member!");
                alert.showAndWait();
            }
           }
           catch (Exception e)
           {
                Alert alert=new Alert(Alert.AlertType.ERROR);//If there are empty spaces, it appears
                alert.setHeaderText(null);
                alert.setContentText("Fill in empty spaces!");
                alert.showAndWait();
           }
            
        }
        else
        {
           try{//makes sure there isn't a null value
                String major = M_MenuButton.getSelectionModel().getSelectedItem().toString();//grabs the major of the student
                String Ssid = ID_TxtBx.getText();//gets ID number as a string
                int sid;
           try{
           sid = Integer.parseInt(ID_TxtBx.getText());//turns text in id textfield to an int and stores it into sid
           String f = fname.substring(0, 1);//gets first initial of lastname
            String l = lname.substring(0, 1);//gets first initial of last name
           String suser = f+l+Ssid;//creates username by adding first initials of the first and last names to the id number
           try{
           //Inserts a new row into the student table
           String Ssql = "Insert into Student Values ('"+suser+"','"+pass+"','"+fname+"','"+lname+"','"+major+"',"+sid+")";
           statement.executeUpdate(Ssql);
           //a table is created for each student user to store all classes taken/registered for
           String newTable="Create Table "+suser+"(course Varchar(4), courseNum Varchar(4), courseName Varchar(100), credit int, grade CHAR(1), stat VARCHAR(12));";
           statement.executeUpdate(newTable);
           }
           catch(Exception e)
           {
               Alert alert=new Alert(Alert.AlertType.ERROR);//If the user already exists in the database; it depends on the id number
                alert.setHeaderText(null);
                alert.setContentText("User exists!");
                alert.showAndWait();
           }
           }
           catch(Exception e)
           {
               Alert alert=new Alert(Alert.AlertType.ERROR);//If the ID is entered as a char or string
                alert.setHeaderText(null);
                alert.setContentText("Enter a number as the ID!");
                alert.showAndWait();
           }
           
           }
           catch(Exception e)
           {
               Alert alert=new Alert(Alert.AlertType.ERROR);//there are any empty spaces
                alert.setHeaderText(null);
                alert.setContentText("Fill in empty spaces!");
                alert.showAndWait();        
          
   }
   }
  }
}
    
   @FXML
    void goBack(ActionEvent event) throws IOException {
        ((Node)event.getSource()).getScene().getWindow().hide();//this is to be able to load to the new page and hide the previous page
            loadWindow("/advisingassistant2/FXMLDocument.fxml","Login Page");
    }
    private void loadWindow(String location,String title) throws IOException{//method to be able to open a new window, can be used multiple times 
        Parent root=FXMLLoader.load(getClass().getResource(location));
        Scene scene = new Scene(root);
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(scene);
        stage.setTitle(title);
        stage.setMaxWidth(1024);
        stage.setMaxHeight(768);
        stage.setResizable(false);
        stage.show();
    }

    private String hashPass(String p) throws NoSuchAlgorithmException
    {
        //This hashes the password using SHA-256
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] digest=md.digest(p.getBytes(StandardCharsets.UTF_8));
        String pass = DatatypeConverter.printHexBinary(digest).toLowerCase();
        return pass;
    }
}
