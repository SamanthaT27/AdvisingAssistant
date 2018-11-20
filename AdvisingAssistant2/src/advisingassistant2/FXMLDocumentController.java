/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advisingassistant2;

import connectivity.ConnectionClass;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author Sammy
 */
public class FXMLDocumentController implements Initializable {
    static ObservableList list=FXCollections.observableArrayList();//carries data like an arrya but observes changes and makes changes within the array
   
    
    static String uname=""; //makes a variable uname to be used to store the value from the text field for the next page to use 

    @FXML
    private TextField user;
    @FXML
    private PasswordField pass;
    @FXML
    private Button loginUser;
    @FXML
    private Button RegisterButton;
    @FXML
    private RadioButton Student_RadioButton;
    @FXML
    private RadioButton Faculty_RadioButton;
   

 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Student_RadioButton.setSelected(true);
    }
    public static String getVariable(){//method to get the username that is entered in the text field 
        return uname;
    }
    
    
       
    

    @FXML
    private void loginUser(ActionEvent event) throws IOException, SQLException, NoSuchAlgorithmException 
    {//This is the login buttons event handler 
    String name=user.getText();//gets the username and password text and stores them for later use on verification
    String passw=pass.getText();
    
    ConnectionClass connectionClass=new ConnectionClass();//connection to the database
        Connection connection = connectionClass.getConnection();
        Statement statement = connection.createStatement();//sttement is used to sent sql statements to remote db
        String logType;//When radio button to specify if the user is faculty/student the value is placed in logtype
    if(Faculty_RadioButton.isSelected())
    {
        logType="Faculty";
    }
    else
    {
        logType="Student";
    }
        
    if(name.isEmpty()|| passw.isEmpty()){//If the username and password are empty and error is made with the message all fields are required 
        Alert alert=new Alert(AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText("Fill all required fields!");
        alert.showAndWait();
    }else{
        //if username and password are not empty, sql statement is executed to search for username
        String sql1="Select Username From "+logType+" Where Username ='"+name+"';";
        ResultSet userName=statement.executeQuery(sql1);
        String UN;    
        if (userName.next())//if username exists
            {
                UN=userName.getString(1);
                //Select statement gets the password from DB
                String sql2="Select Password From "+logType+" Where Username ='" + name+"';";
                ResultSet P=statement.executeQuery(sql2);
                String pass;//will store DB hashed password in it  
                String attemptPass;//attempt pass will be storing the hashed password attempted at login
                if (P.next())
                {
                    pass=P.getString(1);
                    attemptPass=hashPass(passw);//password entered at log in is hashed prior to comparing to stored hashed password
            
                    if(name.equals(UN)&&pass.equals(attemptPass)){//If the username and password are correct and match the set values 
                    FXMLDocumentController.uname=name;//this is to be able to set uname to name and be seen on the next page 
                    //code for the next page
                    ((Node)event.getSource()).getScene().getWindow().hide();//this is to be able to load to the new page and hide the previous page
                    loadWindow("/LoggedIn/loggedIn.fxml","Logged In");//calling method loadwindow with the relevant filenamelocation and filename.fxml and the title of the window
        
                    }
                }
                else{
                    Alert alert=new Alert(AlertType.ERROR);//If the username and password are incorrect then an error appears
                    alert.setHeaderText(null);
                    alert.setContentText("Username or Password is incorrect!");
                    alert.showAndWait();
                    }
            }
        else{    Alert alert=new Alert(AlertType.ERROR);//if no username is found in the DB, error appears
        alert.setHeaderText(null);
        alert.setContentText("No username exists!");
        alert.showAndWait();}
        
        }
    }
    
     @FXML
    void Faculty_Login(ActionEvent event) {
        if(Faculty_RadioButton.isArmed())
        {
            Student_RadioButton.setSelected(false);
        }

    }

    @FXML
    void Student_Login(ActionEvent event) {
        if(Student_RadioButton.isArmed())
        {
            Faculty_RadioButton.setSelected(false);
        }

    }
    
  
    
    @FXML
    void OpenRegister(ActionEvent event) throws IOException {
        ((Node)event.getSource()).getScene().getWindow().hide();
            loadWindow("/Register/RegisterPage.fxml","Register");
            
           
            
            

    }
    
    static String type = null;
    
    public String getType(){
        if(Student_RadioButton.isArmed())
        {
            type = "student";
        }
        else if(Faculty_RadioButton.isArmed())
        {
            type = "faculty";
        }
        return type;
    }
    
    public static String User_type()
    {
        return type;
    }
    
    private void loadWindow(String location,String title) throws IOException{//method to be able to open a new window, can be used multiple times 
        Parent root=FXMLLoader.load(getClass().getResource(location));
        Scene scene = new Scene(root);
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(scene);
        stage.setTitle(title);
        stage.setMaxWidth(1150);
        stage.setMaxHeight(795);
        stage.setResizable(false);
        stage.show();
    }
    private String hashPass(String p) throws NoSuchAlgorithmException
    {
        //This function hashes the password using SHA-256
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] digest=md.digest(p.getBytes(StandardCharsets.UTF_8));
        String pass = DatatypeConverter.printHexBinary(digest).toLowerCase();
        return pass;
    }

}
