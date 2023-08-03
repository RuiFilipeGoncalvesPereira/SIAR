package siar;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class  GetName 
{
	static Connection conn_utilizador = null;
	static ResultSet rs = null;
	static PreparedStatement pst = null; 

	public static String GETNAME(int num_mec) 
	{
		conn_utilizador = JavaConection.ConnecrDb();  
		String name = null;
		  try 
		  {
	      String sql="select nome_utilizador from siar.siar_utilizadores where Num_Mecanog='"+num_mec+"'";
	      conn_utilizador.prepareStatement(sql);
          pst=conn_utilizador.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
          rs=pst.executeQuery();
	      while(rs.next())
	      	{
	    	  name=rs.getString(1);
	        }
	      }
	   catch (SQLException ex)
		  {
		   JOptionPane.showMessageDialog(null, ex);
		   
	      }
	  return name;
	}

}
