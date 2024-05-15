package dados_auxiliares;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

import siar.Gestor_Cantina;

public class get_ref {
	
	static PreparedStatement pstref_cod = null;
	static Connection conn_ref_cod  = null;
	static ResultSet rs_ref_cod = null;
	static PreparedStatement pstref = null;
	static Connection conn_ref  = null;
	static ResultSet rs_ref = null;

	
	public static void FillReFeicao()
	{
		try
	    {
			conn_ref = siar.JavaConection.ConnecrDb();  
			String sqlref = "select * from siar.siar_refeicao";
	        conn_ref.prepareStatement(sqlref); 
	        pstref=conn_ref.prepareStatement(sqlref ,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
	        rs_ref=pstref.executeQuery();
	        while(rs_ref.next())
	        {
	           String refeicao = rs_ref.getString("Desc_Refeicao");
	           Gestor_Cantina.jcomborefeicao_can.addItem(refeicao);
	        }  
	    }
		catch(Exception e)
	   	{
		    JOptionPane.showMessageDialog(null, "Erro ao obter Refeição!"+e);
		}
    } 
	
	public static void Busca_Ref_cod() 
    {
		try{
			conn_ref_cod = siar.JavaConection.ConnecrDb();  
			String sql="select * from siar.siar_refeicao where Desc_refeicao=?";
	         conn_ref_cod.prepareStatement(sql);
	         pstref_cod=conn_ref_cod.prepareStatement(sql ,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
	         pstref_cod.setString(1, (String) Gestor_Cantina.jcomborefeicao_can.getSelectedItem());
             rs_ref_cod=pstref_cod.executeQuery();
	            while(rs_ref_cod.next())
	            {
	            	Gestor_Cantina.txt_ref.setText(rs_ref_cod.getString("Cod_Refeicao"));
	            }   
	       }
	       catch(Exception e3)
	       {
	       JOptionPane.showMessageDialog(null, "Erro ao Obter Refeição_2!"+e3);
	       }
    }

}
