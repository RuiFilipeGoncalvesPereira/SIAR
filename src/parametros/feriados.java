package parametros;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import Entities.Feriado;
import Model.TheModel;
import Query.MyQuery;
import siar.Administrador;
import siar.JavaConection;

public class feriados {
	
	public static String ad_feriado; 
    //Inserir data na tabela dos feriados
	Date now = new Date(System.currentTimeMillis());
	static SimpleDateFormat df2 = new SimpleDateFormat("dd-MM-yyyy");
	String strDate = df2.format(now);
	
	public static void prencher_Feriados()
	{
		MyQuery mq = new MyQuery();
		ArrayList<Feriado> list = mq.Mostra_Feriados();
		String[] columnName = {"Código Feriado","Data do Feriado","Descrição do Feriado"}; 
		Object [][] rows = new Object[list.size()][3];
		for(int i = 0; i < list.size(); i++)
			{
				rows[i][0] = list.get(i).getCodf();
				rows[i][1] = list.get(i).getDta_feriado();
				rows[i][2] = list.get(i).getDesc_fe();
			}
		TheModel model = new TheModel(rows, columnName);
		Administrador.table_8.setModel(model);
		Administrador.table_8.setRowHeight(30);
		Administrador.table_8.getColumnModel().getColumn(0).setPreferredWidth(45);
		Administrador.table_8.getColumnModel().getColumn(1).setPreferredWidth(110);
		Administrador.table_8.getColumnModel().getColumn(2).setPreferredWidth(140);
		Administrador.table_8.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		Administrador.table_8.getTableHeader().setReorderingAllowed(false);
	}
	public static void atua_seq_feriado()
    {
		PreparedStatement pst_seqfe = null;
		Connection conn_seqfe = null;
		ResultSet resseqfe = null;
		conn_seqfe = JavaConection.ConnecrDb();
		try
	      {
	    	 String sql="select siar.SIAR_S_FERIADO.nextval as seguinte_fe from dual";
	    	 pst_seqfe = conn_seqfe.prepareStatement(sql); 
	    	 resseqfe=pst_seqfe.executeQuery();
	           if(resseqfe.next())
	            {
	        	   ad_feriado =resseqfe.getString("seguinte_fe");
	            }
	      }
         catch(Exception e)
         {
        	 JOptionPane.showMessageDialog(null, "Erro ao atualizar Sequência feriado!"+e);
         }
     }
	public static void Insere_Feriado() throws SQLException
	{
		PreparedStatement pst_insfe = null;
		Connection conn_confe  = null;
		conn_confe = JavaConection.ConnecrDb();
		if(((JTextField)Administrador.dta_feriado.getDateEditor().getUiComponent()).getText().isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Deve Inserir a Data do Feriado!");
            ((JTextField)Administrador.dta_feriado.getDateEditor().getUiComponent()).requestFocus();
            return;
        }
    	if((Administrador.desc_feriado.getText().length()==0))
    	{
    	 JOptionPane.showMessageDialog(null,"Deve Introduzir a descrição!");
    	}
    	else
    	{	
    		String dataobtida = df2.format(Administrador.dta_feriado.getDate());
    		String sql="insert into siar.siar_Feriado(cod_feriado,dta_feriado,desc_feriado)values(?,?,?)"; 
			pst_insfe =conn_confe.prepareStatement(sql);
	        atua_seq_feriado();
	        pst_insfe.setString(1,ad_feriado);
	        pst_insfe.setString(2,dataobtida);
	        pst_insfe.setString(3,Administrador.desc_feriado.getText());
	        pst_insfe.executeQuery();
	        JOptionPane.showMessageDialog(null,"Dados Inseridos com Sucesso!");
	        Administrador.dta_feriado.setDate(null);
	        Administrador.desc_feriado.setText(null);
    	}
    }  
	public static void atualiza_feriados()
	{
		/* int count = table_8.getRowCount();
		    Feriado Fe = new Feriado();
			String getCodf = Fe.getCodf();
			String getDta_feriado = Fe.getDta_feriado();
			String getDesc_fe = Fe.getDesc_fe();
		    String Dta_Fe[] = new String[count];Dta_Fe[i] = table_8.getValueAt(i,1).toString();
		   for (int i = 0; i<count; i++)
		    {
			     if(table_8.getValueAt(i, 0) != null) 
			     {	 
			        getCodf = table_8.getValueAt(i,0).toString();
			     }
			     else
			     {
			    	getCodf = null; 
			     }
			     if(table_8.getValueAt(i, 1) != null) 
			     {	 
				    getDta_feriado = table_8.getValueAt(i,1).toString();
			     }
			     else
			     {
			    	getDta_feriado = null; 
			     }
			     if(table_8.getValueAt(i, 2) != null) 
			     {	 
					getDesc_fe = table_8.getValueAt(i,2).toString();
			     }
			     else
			     {
			    	getDesc_fe = null; 
			     }*/
		PreparedStatement pstatu_fe = null;
		Connection conn_atu_fe  = null;
		conn_atu_fe = JavaConection.ConnecrDb();
		if((Administrador.Codfed.getText().length()==0)||(Administrador.Desc_Fed.getText().length()==0)||(Administrador.Dta_fed.getText().length()==0))
    	{
    	 JOptionPane.showMessageDialog(null,"Nenhum Registo Selecionado!");
    	}
    	else
    	{	
	            try 
	            {
	            	String sql="update siar.siar_feriado set siar.siar_feriado.Dta_Feriado='"+Administrador.Dta_fed.getText()+"',siar.siar_feriado.desc_feriado='"+Administrador.Desc_Fed.getText()+"' where to_char(siar.siar_feriado.cod_feriado)='"+Administrador.Codfed.getText()+"'";
	            	pstatu_fe=conn_atu_fe.prepareStatement(sql);
	            	pstatu_fe.execute();
		        } 
		         catch (Exception e)
		        {
		        	 JOptionPane.showMessageDialog(null, "Erro ao actualizar data de feriado!"+e);
		        }  
 		    }
            JOptionPane.showMessageDialog(null, "updated"); 
            prencher_Feriados();  
	}
	public static void seleciona_linha_fer()
	{
		PreparedStatement pst_insfe = null;
		Connection conn_confe  = null;
		ResultSet rsfe = null;
		conn_confe = JavaConection.ConnecrDb();
		int row = Administrador.table_8.getSelectedRow();
		if (row >= 0)	
		{	
		try
		 {
					String clica_tabela =(Administrador.table_8.getModel().getValueAt(row, 0)).toString();
					String clica_nome =(Administrador.table_8.getModel().getValueAt(row, 2)).toString();
					String dta_reg = (Administrador.table_8.getModel().getValueAt(row, 1)).toString();
		            String conta="select cod_feriado, Dta_Feriado, desc_feriado from siar.siar_feriado where siar.siar_feriado.cod_feriado='"+clica_tabela+"'";
		            pst_insfe=conn_confe.prepareStatement(conta);
		            rsfe =pst_insfe.executeQuery();
			           if(rsfe.next())
			            {
  			        	    String ad1 =rsfe.getString("cod_feriado");
  			        	    Administrador.Codfed.setText(ad1);
  			        	    String ad2 =dta_reg;
  			        	    Administrador.Dta_fed.setText(ad2);
  			        	    String ad3 =clica_nome;
  			        	    Administrador.Desc_Fed.setText(ad3);
			            }
			}
			catch(Exception e)
			{
				Administrador.Codfed.setText(null);
				Administrador.Dta_fed.setText(null);
				Administrador.Desc_Fed.setText(null);
			}
          }
	}

}
