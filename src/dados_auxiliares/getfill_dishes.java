package dados_auxiliares;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

import siar.JavaConection;
import siar.Marcacoes;

public class getfill_dishes {
	public static Connection conn_prato= null;
	public static PreparedStatement pstprato = null;	
	public static ResultSet rs_prato = null;
	
	public static void FillPrato()
	{
		conn_prato = JavaConection.ConnecrDb(); 
		try
        {
            String sql_prato = "select * from siar.siar_prato";
            conn_prato.prepareStatement(sql_prato); 
            pstprato=conn_prato.prepareStatement(sql_prato ,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs_prato=pstprato.executeQuery();
            while(rs_prato.next())
            {
               String prato = rs_prato.getString("Desc_Prato");
               Marcacoes.jcomboprato.addItem(prato);
            }  
        }
        catch(Exception e)
	     {
	            JOptionPane.showMessageDialog(null, "Erro ao Listar na Tabela1!"+e);
	     }
    }
}
