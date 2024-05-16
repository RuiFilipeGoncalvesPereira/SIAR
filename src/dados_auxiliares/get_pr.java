package dados_auxiliares;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

import siar.Gestor_Cantina;

public class get_pr {
	
	static PreparedStatement P_prato_cod = null;
	static Connection conn_prato_cod  = null;
	static ResultSet rs_conn_prato_cod = null;
	static PreparedStatement pstprato = null;
	static Connection conn_prato  = null;
	static ResultSet rs_prato = null;
	
	
	public static void FillPrato()
	{
		try
        {
			conn_prato = siar.JavaConection.ConnecrDb(); 
			String sql_prato = "select * from siar.siar_prato";
            conn_prato.prepareStatement(sql_prato); 
            pstprato=conn_prato.prepareStatement(sql_prato ,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs_prato=pstprato.executeQuery();
            while(rs_prato.next())
            {
               String prato = rs_prato.getString("Desc_Prato");
               Gestor_Cantina.jcomboprato_can.addItem(prato);
            }  
        }
        catch(Exception e)
	     {
	            JOptionPane.showMessageDialog(null, "Erro ao Prencher a Lista de Pratos!"+e);
	     }
    }
	
	public static void Busca_Prato_Cod()
	{
		try{
			conn_prato_cod = siar.JavaConection.ConnecrDb();  
			String sql = "select * from siar.siar_prato where Desc_Prato=?";
	         P_prato_cod=conn_prato_cod.prepareStatement(sql);
	         P_prato_cod=conn_prato_cod.prepareStatement(sql ,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
	         P_prato_cod.setString(1,(String) Gestor_Cantina.jcomboprato_can.getSelectedItem());
	         rs_conn_prato_cod = P_prato_cod.executeQuery();
	            while(rs_conn_prato_cod.next())
	            {
	            	Gestor_Cantina.txt_prato.setText(rs_conn_prato_cod.getString("Cod_Prato"));
	            }   
	       }
	       catch(Exception e3)
	       {
	       JOptionPane.showMessageDialog(null, "Erro ao Listar Pratos!"+e3);
	       }
	}
	

}
