package dados_auxiliares;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

import siar.JavaConection;
import siar.Marcacoes;

public class getfill_meals_cod {
	
	public static Connection conn_ref_cod= null;
	public static PreparedStatement pstref_cod= null;
	public static ResultSet rs_ref_cod = null;
	
	public static void Busca_Ref_cod()
	{
		conn_ref_cod = JavaConection.ConnecrDb(); 
		try{
	         String sql="select * from siar.siar_refeicao where Desc_refeicao=?";
	         conn_ref_cod.prepareStatement(sql);
	         pstref_cod=conn_ref_cod.prepareStatement(sql ,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
	         pstref_cod.setString(1, (String) Marcacoes.jcomborefeicao.getSelectedItem());
             rs_ref_cod=pstref_cod.executeQuery();
	            while(rs_ref_cod.next())
	            {
	            	Marcacoes.jcodref.setText(rs_ref_cod.getString("Cod_Refeicao"));
	            }   
	            dados_auxiliares.get_ementa.get_ref(Marcacoes.dt_ref.getDate(),Marcacoes.jcodref.getText(),Marcacoes.jcodprato.getText());  
	       }
		   
	       catch(Exception e3)
	       {
	       JOptionPane.showMessageDialog(null, "Error at list on table of mealse codes!"+e3);
	       }
	}

}
