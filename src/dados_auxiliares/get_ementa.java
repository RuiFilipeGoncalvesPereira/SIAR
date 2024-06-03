package dados_auxiliares;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

import Data.Data_Read_Values;


public class get_ementa {
	public static SimpleDateFormat df2 = new SimpleDateFormat("dd-MM-yyyy");
	Data_Read_Values mostra_data;
	static PreparedStatement pstprato_rem = null;
	static Connection conn_rem  = null;
	static ResultSet rs_prato_rem = null;
    
	public static void get_ref(Date dta_ref, String codref, String codpr)
	{
		try
        {		
			conn_rem = siar.JavaConection.ConnecrDb();
			String dataref = df2.format(dta_ref);
			String cod_ref = codref;
			String cod_pra = codpr;
			String ementa="select desc_ementa from siar.siar_ementas where to_char(dta_Refeicao,'dd-mm-yyyy')='"+dataref+"' and Cod_Refeicao='"+cod_ref+"' and Cod_prato='"+cod_pra+"'";
			pstprato_rem=conn_rem.prepareStatement(ementa);
		    pstprato_rem=conn_rem.prepareStatement(ementa ,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		    rs_prato_rem=pstprato_rem.executeQuery();
            if(rs_prato_rem.next())
            {
               String prato = rs_prato_rem.getString("desc_ementa");
               siar.Marcacoes.ementa.setText(prato);
            } 
            else
            {
            	siar.Marcacoes.ementa.setText(null);
            }

        }
        catch(Exception e)
	     {
	            JOptionPane.showMessageDialog(null, "Erro ao Obter ementa!"+e);
	     }
    }
	public static void get_ref(String dta_ref, String codref, String codpr)
	{
		try
        {		
			conn_rem = siar.JavaConection.ConnecrDb();
			String dataref = dta_ref;
			String cod_ref = codref;
			String cod_pra = codpr;
			String ementa="select desc_ementa from siar.siar_ementas where to_char(dta_Refeicao,'dd/MM/yyyy')='"+dataref+"' and Cod_Refeicao='"+cod_ref+"' and Cod_prato='"+cod_pra+"'";
			pstprato_rem=conn_rem.prepareStatement(ementa);
		    pstprato_rem=conn_rem.prepareStatement(ementa ,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		    rs_prato_rem=pstprato_rem.executeQuery();
            if(rs_prato_rem.next())
            {
               String prato = rs_prato_rem.getString("desc_ementa");
               siar.Gestor_Cantina.txt_ementa_dia.setText(prato);
            } 
            else
            {
            	siar.Gestor_Cantina.txt_ementa_dia.setText(null);
            }

        }
        catch(Exception e)
	     {
	            JOptionPane.showMessageDialog(null, "Erro ao Obter Ementa!"+e);
	     }
    }


}
