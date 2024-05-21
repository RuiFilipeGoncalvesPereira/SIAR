package dados_auxiliares;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

import siar.JavaConection;
import siar.Marcacoes;

public class getfill_meals {
	public static Connection conn_ref= null;
	public static PreparedStatement pstref= null;
	public static ResultSet rs_ref = null;

	
	public static void FillReFeicao()
	{
		conn_ref = JavaConection.ConnecrDb(); 
		try
	    {
	        String sqlref = "select * from siar.siar_refeicao";
	        conn_ref.prepareStatement(sqlref); 
	        pstref=conn_ref.prepareStatement(sqlref ,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
	        rs_ref=pstref.executeQuery();
	        while(rs_ref.next())
	        {
	           String refeicao = rs_ref.getString("Desc_Refeicao");
	           Marcacoes.jcomborefeicao.addItem(refeicao);
	        }  
	    }
		    catch(Exception e)
	    	{   
		    	JOptionPane.showMessageDialog(null, e);
		        JOptionPane.showMessageDialog(null, "Erro at fill meals list");
		    }
	} 
}
