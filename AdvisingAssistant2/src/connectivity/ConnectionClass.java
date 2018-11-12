/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connectivity;

import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.DriverManager;
/**
 *
 * @author xINSANITYx
 */
public class ConnectionClass {
    public Connection connection;
    
    public Connection getConnection()
    {
        String dbName="u291983648_ynyb";
        String user="u291983648_ynyb";
        String pass="advising";
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url="jdbc:mysql://sql171.main-hosting.eu/"+dbName;
            connection=DriverManager.getConnection(url,user,pass);
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return connection;
    }
}
