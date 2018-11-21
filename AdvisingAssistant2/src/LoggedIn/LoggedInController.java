/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LoggedIn;

import advisingassistant2.FXMLDocumentController;
import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;
import connectivity.ConnectionClass;
import java.awt.Desktop;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.mail.*;
import static javax.mail.Message.RecipientType.TO;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Sammy
 */
public class LoggedInController implements Initializable {
ObservableList<String> credits=FXCollections.observableArrayList("1","2","3");
ObservableList<String> grades=FXCollections.observableArrayList("A","B","C","D","F");
ObservableList<String> status=FXCollections.observableArrayList("Completed","Incomplete","In Progress");
ObservableList<String> sub=FXCollections.observableArrayList("ANTH", "ARTS","ASTR","BIOL","CHEM","COMM","CSCI","DANC","ECON","ENGL","ENST","ENVR"
,"FILM","FREN","GEOL","HIST","HONR","INDS","MASC","MATH","MGMT","MUSI","PHIL","PHYS","POLS","PSCI","PSYC","QUMT"
,"SOCI","SPAN","THTF","WRLS","CRIJ");  
ObservableList<String> Semesters=FXCollections.observableArrayList("Fall 2017", "Spring 2018", "May 2018", "Summer I 2018", "Summer II 2018", "Fall 2018", "Spring 2019");


    @FXML
    private Button CompleteSub;
    @FXML
    private ChoiceBox<String> CompletedGrade;
     @FXML
    private ChoiceBox<String> CompletedCredit;
    @FXML
    private ChoiceBox<String> CompletedName;
    @FXML
    private ChoiceBox<String> CompletedStatus;
     @FXML
    private ChoiceBox<String> CompletedNum;
    @FXML
    private ChoiceBox<String> CompletedSemester;
    @FXML
    private ChoiceBox<String> CompletedSubject;
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
    private Hyperlink OnlineAppt;
    @FXML
    private Hyperlink MajorInfo;
    @FXML
    private Button SendEmail;
    @FXML
    private TextField ToField;
    @FXML
    private TextField EmailSubject;
    @FXML
    private TextArea EmailText;
    @FXML
    private TextField EmailPassword;
    @FXML
    private TextField SearchStudent;
    @FXML
    private Button DiscardEmail;
    @FXML
    private TextField FromField;
    @FXML
    private Button ExitButton;
    @FXML
    private TextArea NotesArea;
    @FXML
    private Button Save;
    @FXML
    private TextField userName;
    ConnectionClass connectionClass=new ConnectionClass();
    Connection connection=connectionClass.getConnection();
    Statement statement=connection.createStatement();

    public LoggedInController() throws SQLException {
        this.statement = connection.createStatement();
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
        //set the items into certain choiceboxes
        CompletedCredit.setItems(credits);
        CompletedGrade.setItems(grades);
        CompletedStatus.setItems(status);
        CompletedSubject.setItems(sub);
        CompletedSemester.setItems(Semesters);
        //set choicboxes to being disabled in order to ensure that all data is completed
        CompletedNum.setDisable(true);
        CompletedName.setDisable(true);
        CompletedCredit.setDisable(true);
        CompletedGrade.setDisable(true);
        CompletedStatus.setDisable(true);
        CompletedSemester.setDisable(true);
        CompleteSub.setDisable(true);
        
        String sub;
        //checks for anychanges in the values of the choiceboxes to allow the next to be available to be clicked
        
        CompletedSubject.getSelectionModel().selectedItemProperty().addListener((v,oldvalue,newvalue)->{
            try {
                SubjectSelected(newvalue);
            } catch (SQLException ex) {
               Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("SQL error!");
            alert.showAndWait();
            }
        });
      
    }        
     @FXML
    void GoToMajorInfo(ActionEvent event) throws URISyntaxException, IOException {
       Desktop.getDesktop().browse(new URI("https://www.utrgv.edu/advising/index.htm"));
        

    }
    @FXML
    void GoToOnlineAppt(ActionEvent event) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://www.utrgv.edu/en-us/academics/undergraduate/index.htm"));
    }
    @FXML
    void SearchStudentInfo(ActionEvent event) {
   
    }
        @FXML
    void DiscardEmail(ActionEvent event) {
      ToField.clear();
      FromField.clear();
      EmailPassword.clear();
      EmailSubject.clear();
      EmailText.clear();

    }

    @FXML
    void SendEmail(ActionEvent event) {
        final String From = FromField.getText();
        final String Pass= EmailPassword.getText();
        String To= ToField.getText();
        String Subject = EmailSubject.getText();
        String Message= EmailText.getText();
        
        Properties pros = new Properties(); 
        //SSL protocol port number is 465 
        pros.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        pros.put("mail.smtp.auth", "true");
        pros.put("mail.smtp.starttls.enable", "true");
        pros.put("mail.smtp.host", "outlook.office365.com");
        pros.put("mail.smtp.port", "587");
        
        
        Session session = Session.getDefaultInstance(pros,
                new javax.mail.Authenticator(){  
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication(){
                    return new PasswordAuthentication(From, Pass);
                    }
                }
                );
        try{
            //message header 
            Message message= new MimeMessage(session);
            message.setFrom(new InternetAddress(From));
            message.setRecipients(TO, InternetAddress.parse(To));
            message.setSubject(Subject);
            
            //code for set the text message
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(Message);
            Multipart multiPart = new MimeMultipart();
            multiPart.addBodyPart(messageBodyPart);
            
            message.setContent(multiPart);
            Transport.send(message);
            
            JOptionPane.showMessageDialog(null, "The Message Has Been Sent!");
            
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    @FXML
    void ExitButton(ActionEvent event) throws IOException {
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
        @FXML
    void SaveButton(ActionEvent event) throws IOException {
      //needs to be able to save the NotesArea TextArea 
    String userHomeFolder = System.getProperty("user.home");
    File textFile = new File(userHomeFolder, "Notes.txt");
    BufferedWriter out = new BufferedWriter(new FileWriter(textFile));
    out.write(NotesArea.getText());
    out.close();
        
        
    }
   @FXML
    void SubmitCompleted(ActionEvent event) throws SQLException 
    {
        try{
        String subject=CompletedSubject.getSelectionModel().getSelectedItem().toString();
        String num=CompletedNum.getSelectionModel().getSelectedItem().toString();
        String name=CompletedName.getSelectionModel().getSelectedItem().toString();
        int cred=Integer.parseInt(CompletedCredit.getSelectionModel().getSelectedItem());
        String grade="A";
        try{
        grade=CompletedGrade.getSelectionModel().getSelectedItem().toString();
        }
        catch(Exception e)
        {
                    System.out.println("Char error"); 
        }
        String stat=CompletedStatus.getSelectionModel().getSelectedItem().toString();
        //String semester=CompletedSemester.getSelectionModel().getSelectedItem();
        String user=userName.getText();
        String sql;
           // System.out.println("'"+user+" "+subject+"','"+num+"','"+name+"',"+cred+",'"+grade+"','"+stat+"'");
        sql= "Insert into "+user+" Values ('"+subject+"','"+num+"','"+name+"',"+cred+",'"+grade+"','"+stat+"');";
        
        statement.executeUpdate(sql);
        }
       catch(Exception e)
        {
          Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Error uploading class!");
            alert.showAndWait();          
                
        }
        
    }
    
    @FXML
    void SubjectSelected(String n) throws SQLException
    {
        String major=n;
        String CSCI_tab="CSCI_Plan";
        String CRIJ_tab="CriJus_Plan";
        String Gen_tab="GeneralCore";
        CompletedNum.setDisable(false);
        
        
        String csci="Select CourseID, CourseTitle FROM "+CSCI_tab+" Where Subject ='"+major+"';";
        String crij="Select CourseID,CourseTitle FROM "+CRIJ_tab+" Where Subject ='"+major+"';";
        String gen="Select CourseID,CourseTitle FROM "+Gen_tab+" Where Subject ='"+major+"';";
        
        ResultSet id;
        ResultSet gid;
        
        /*id=statement.executeQuery(gen);
       while (id.next())
       {
           System.out.println(id.getString(1));
           CompletedNum.getItems().add(id.getString(1));
       }*/
        
       if(major=="CRIJ")
        {
            id=statement.executeQuery(crij);
            
            while(id.next())
            {
               CompletedNum.getItems().add(id.getString(1));
               CompletedName.getItems().add(id.getString(2));
            }
        }
        else if (major=="CSCI")
        {
            id=statement.executeQuery(csci);
             while(id.next())
            {
                CompletedNum.getItems().add(id.getString(1));
                CompletedName.getItems().add(id.getString(2));
            }
        }
        else
        {
            gid=statement.executeQuery(gen);
             while(gid.next())
            {
               CompletedNum.getItems().add(gid.getString(1));
               CompletedName.getItems().add(gid.getString(2));
            }
             
        }
        CompletedNum.getSelectionModel().selectedItemProperty().addListener((v,oldvalue,newvalue)->CompletedName.setDisable(false));
        CompletedName.getSelectionModel().selectedItemProperty().addListener((v,oldvalue,newvalue)->CompletedCredit.setDisable(false));
        CompletedCredit.getSelectionModel().selectedItemProperty().addListener((v,oldvalue,newvalue)->CompletedGrade.setDisable(false));
        CompletedGrade.getSelectionModel().selectedItemProperty().addListener((v,oldvalue,newvalue)->CompletedStatus.setDisable(false));
        CompletedStatus.getSelectionModel().selectedItemProperty().addListener((v,oldvalue,newvalue)->CompletedSemester.setDisable(false));
        CompletedSemester.getSelectionModel().selectedItemProperty().addListener((v,oldvalue,newvalue)->CompleteSub.setDisable(false));
    }
    
    
}
