/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LoggedIn;

import advisingassistant2.FXMLDocumentController;
import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    @FXML
    private Button CompleteSub;
    @FXML
    private ChoiceBox<?> CompletedGrade;
     @FXML
    private ChoiceBox<?> CompletedCredit;
    @FXML
    private ChoiceBox<?> CompletedName;
    @FXML
    private ChoiceBox<?> CompletedStatus;
     @FXML
    private ChoiceBox<?> CompletedNum;
    @FXML
    private ChoiceBox<?> CompletedSemester;
    @FXML
    private ChoiceBox<?> CompletedSubject;
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
        CompletedCredit.setItems(FXCollections.observableArrayList('1','3','4'));
        CompletedGrade.setItems(FXCollections.observableArrayList('A','B','C','D','F'));
        CompletedStatus.setItems(FXCollections.observableArrayList("Completed","Incomplete", "In Progress"));

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
    void SubmitCompleted(ActionEvent event) {

    }
    
}
