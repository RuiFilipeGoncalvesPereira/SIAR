package dados_auxiliares;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

import siar.JavaConection;
import siar.Marcacoes;

public class getfill_dishes_cod {
	
	public static Connection conn_prato_cod= null;
	public static PreparedStatement P_prato_cod= null;
	public static ResultSet rs_conn_prato_cod = null;
	
	public static void Busca_Prato_Cod()
	{
		conn_prato_cod = JavaConection.ConnecrDb(); 
		try{
	         String sql = "select * from siar.siar_prato where Desc_Prato=?";
	         P_prato_cod=conn_prato_cod.prepareStatement(sql);
	         P_prato_cod=conn_prato_cod.prepareStatement(sql ,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
	         P_prato_cod.setString(1,(String) Marcacoes.jcomboprato.getSelectedItem());
	         rs_conn_prato_cod = P_prato_cod.executeQuery();
	            while(rs_conn_prato_cod.next())
	            {
	            	Marcacoes.jcodprato.setText(rs_conn_prato_cod.getString("Cod_Prato"));
	            }   
	            dados_auxiliares.get_ementa.get_ref(Marcacoes.dt_ref.getDate(),Marcacoes.jcodref.getText(),Marcacoes.jcodprato.getText());   
	       }
	       catch(Exception e3)
	       {
	       JOptionPane.showMessageDialog(null, "Erro at lits the dishes codes table!"+e3);
	       }
	}	

}
