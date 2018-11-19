/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LoggedIn;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 *
 * @author Sammy
 */
public class LoggedIn extends Application {
    @FXML
    private Button Save;
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("loggedIn.fxml"));//must be the fxml file you are currently working on 
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setMaxWidth(1024);
        stage.setMaxHeight(768);
        stage.setResizable(false);
        stage.sizeToScene();
        stage.show();
        
     
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
