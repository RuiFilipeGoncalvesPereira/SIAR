package parametros;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import Data.Data;
import siar.Ementas;
import siar.Gestor_Cantina;
import siar.MyQuery;
import siar.TheModel;

public class ementas {
	
	static String conta_ement;
	static SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
	
	public static void prenche_ementas()
	{
		MyQuery mq = new MyQuery();
		ArrayList<Ementas> list = mq.Mostra_Ementas();
		String[] columnName = {"Data","Refeicao","Prato","cod_refeicao","Cod_prato"};//,"Ementa" 
		Object [][] rows = new Object[list.size()][5];
		for(int i = 0; i < list.size(); i++)
			{
				rows[i][0] = list.get(i).getDta_desativo();
				rows[i][1] = list.get(i).getRef();
				rows[i][2] = list.get(i).getPr();
				//rows[i][3] = list.get(i).getDescricao();
				rows[i][3] = list.get(i).getCod_ref();
				rows[i][4] = list.get(i).getCod_prato();
			}
		TheModel model = new TheModel(rows, columnName);
		Gestor_Cantina.table_can.setModel(model);
		Gestor_Cantina.table_can.setRowHeight(30);
		Gestor_Cantina.table_can.getColumnModel().getColumn(0).setPreferredWidth(75);
		Gestor_Cantina.table_can.getColumnModel().getColumn(1).setPreferredWidth(55);
		Gestor_Cantina.table_can.getColumnModel().getColumn(2).setPreferredWidth(55);
		Gestor_Cantina.table_can.getColumnModel().getColumn(3).setMaxWidth(0);
		Gestor_Cantina.table_can.getColumnModel().getColumn(3).setMinWidth(0);
		Gestor_Cantina.table_can.getColumnModel().getColumn(3).setPreferredWidth(0);
		Gestor_Cantina.table_can.getColumnModel().getColumn(4).setMaxWidth(0);
		Gestor_Cantina.table_can.getColumnModel().getColumn(4).setMinWidth(0);
		Gestor_Cantina.table_can.getColumnModel().getColumn(4).setPreferredWidth(0);
		Gestor_Cantina.table_can.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		Gestor_Cantina.table_can.getTableHeader().setReorderingAllowed(false);
	}
	public static void Insere_ementas() throws SQLException
	{
		Connection conn_param = null;
		@SuppressWarnings("unused")
		ResultSet rs_param = null;
		PreparedStatement pst_rs_param = null;
		conn_param = siar.JavaConection.ConnecrDb(); 
		
		String dataref = df2.format(Gestor_Cantina.dt_ementa.getDate());
		conta_ement="select count(*) from siar.siar_ementas where to_char(dta_Refeicao,'dd/mm/yyyy')='"+dataref+"' and Cod_Refeicao='"+Gestor_Cantina.txt_ref.getText()+"' and Cod_Prato='"+Gestor_Cantina.txt_prato.getText()+"'";
		pst_rs_param=conn_param.prepareStatement(conta_ement);
		pst_rs_param=conn_param.prepareStatement(conta_ement ,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		rs_param=pst_rs_param.executeQuery();
		
		rs_param.next();
        int conta_ement = rs_param.getInt(1);
		
		if(((JTextField)Gestor_Cantina.dt_ementa.getDateEditor().getUiComponent()).getText().isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Deve Inserir a Data da Refeição!");
            ((JTextField)Gestor_Cantina.dt_ementa.getDateEditor().getUiComponent()).requestFocus();
            return;
        }
    	if((Gestor_Cantina.txt_ref.getText().length()==0))
    	{
    	 JOptionPane.showMessageDialog(null,"Deve Introduzir o Código da Refeição!");
    	}
    	if((Gestor_Cantina.txt_prato.getText().length()==0))
    	{
    	 JOptionPane.showMessageDialog(null,"Deve Introduzir o Código do Prato!");
    	}
    	if((Gestor_Cantina.txt_ementa.getText().length()==0))
    	{
    		Gestor_Cantina.txt_ementa.requestFocus();
    		JOptionPane.showMessageDialog(null,"Deve Introduzir a Ementa");
    	}
    	else
    	{	
          if (conta_ement >  0) 
            {	
        	 JOptionPane.showMessageDialog(null,"This Menu has already been Registed!"); 
             return;
            } 
          else {	
    		String sql="insert into siar.siar_ementas(Dta_Refeicao,cod_refeicao,cod_prato,desc_ementa)values(?,?,?,?)"; 
			pst_rs_param=conn_param.prepareStatement(sql);
	        pst_rs_param.setDate(1,Data.convertUtilDateToSqlDate(Gestor_Cantina.dt_ementa.getDate()));
	        pst_rs_param.setString(2,Gestor_Cantina.txt_ref.getText());
	        pst_rs_param.setString(3,Gestor_Cantina.txt_prato.getText());
	        pst_rs_param.setString(4,Gestor_Cantina.txt_ementa.getText());
	        pst_rs_param.executeQuery();
	        JOptionPane.showMessageDialog(null,"Ementa Inserida com Sucesso!");
	        Gestor_Cantina.txt_ref.setText(null);
          }  
    	}
    }
	public static void seleciona_linha_ementa()
	{
		
		Connection conn_prato_cod = null;
		ResultSet rs_prato_cod = null;
		PreparedStatement pst_rs_prato_cod = null;
		conn_prato_cod = siar.JavaConection.ConnecrDb();
		int row = Gestor_Cantina.table_can.getSelectedRow();
		if (row >= 0)	
		{	
		try
		 {
					String dta_reg =(Gestor_Cantina.table_can.getModel().getValueAt(row, 0)).toString();
					String cod_ref =(Gestor_Cantina.table_can.getModel().getValueAt(row, 3)).toString();
					String cod_pr = (Gestor_Cantina.table_can.getModel().getValueAt(row, 4)).toString();
		            String conta="select dta_refeicao, cod_refeicao, cod_prato from siar.siar_ementas where to_char(siar.siar_ementas.dta_refeicao,'dd/MM/yyyy')='"+dta_reg+"'"
		            		+ " and siar.siar_ementas.cod_refeicao='"+cod_ref+"' and siar.siar_ementas.cod_prato='"+cod_pr+"'";
		            pst_rs_prato_cod=conn_prato_cod.prepareStatement(conta);
		            rs_prato_cod =pst_rs_prato_cod.executeQuery();
			           if(rs_prato_cod.next())
			            {
  			        	    String ad1 =dta_reg;
  			        	    Gestor_Cantina.dt_ementa_aux.setText(ad1);
  			        	    String ad2 =cod_ref;
  			        	    Gestor_Cantina.cod_ref_aux.setText(ad2);
  			        	    String ad3 =cod_pr;
  			        	    Gestor_Cantina.cod_pr_aux.setText(ad3);
			            }
			}
			catch(Exception e)
			{
				JOptionPane.showMessageDialog(null,"erro! " +e);
				Gestor_Cantina.dt_ementa_aux.setText(null);
				Gestor_Cantina.cod_ref_aux.setText(null);
				Gestor_Cantina.cod_pr_aux.setText(null);
			}
          }
	}

}
