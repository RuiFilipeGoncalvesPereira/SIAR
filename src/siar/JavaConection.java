package siar;
import java.sql.*;
import javax.swing.*;
public class JavaConection 
{
    Connection conn = null;
    public static Connection ConnecrDb()
    {
        
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","SYSTEM","123456"); 
            //JOptionPane.showMessageDialog(null,"Conexão Estabelecida com Sucesso!");
            return conn;
           } 
           catch(Exception e)
             {
               JOptionPane.showMessageDialog(null,e);
               return null;
             } 
     }
}