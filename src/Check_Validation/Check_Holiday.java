package Check_Validation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import siar.JavaConection;

public class Check_Holiday 
{
	Connection conn_utilizador = null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	Connection conn_feriado = null;
	ResultSet rs_feriado = null;
	PreparedStatement pst_feriado = null;
	public String check_holiday(int val)
	{
		conn_utilizador = JavaConection.ConnecrDb();  
		String valor = String.valueOf('0');
		  try 
		  {
	      String sql="select valor_parametro from siar.siar_parametros where cod_parametro='"+val+"'";
	      conn_utilizador.prepareStatement(sql);
          pst=conn_utilizador.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
          rs=pst.executeQuery();
	      while(rs.next())
	      	{
	    	  valor=rs.getString(1);
	        }
	      }
	   catch (SQLException ex)
		  {
		   JOptionPane.showMessageDialog(null, ex);
	      }
  
	  return valor;
	}
	public String check_Feriado(int feriado)
	{
		conn_feriado = JavaConection.ConnecrDb();  
		String valor = String.valueOf('0');
		  try 
		  {
	      String sql="select dta_feriado from siar.siar_feriado where cod_feriado='"+feriado+"'";
	      conn_feriado.prepareStatement(sql);
	      pst_feriado=conn_feriado.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
	      rs_feriado=pst_feriado.executeQuery();
	      while(rs_feriado.next())
	      	{
	    	  valor=rs_feriado.getString(1);
	        }
	      }
	   catch (SQLException ex)
		  {
		   JOptionPane.showMessageDialog(null, ex);
	      }
  
	  return valor;
	  }
	
	
}
