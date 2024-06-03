package parametros;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import Data.Data_Read_Values;
import Entities.prato;

//package default;

import siar.Administrador;
import siar.MyQuery;
import siar.TheModel;


public class pratos {
	
	public static String ad_prato; 
	
	public static void prencher_pratos()
	{
		MyQuery mq = new MyQuery();
		ArrayList<prato> list = mq.Mostra_Pratos();
		String[] columnName = {"Código","Descrição do Prato","Data de Registo"}; 
		Object [][] rows = new Object[list.size()][3];
		for(int i = 0; i < list.size(); i++)
			{
				rows[i][0] = list.get(i).getCod_prato();
				rows[i][1] = list.get(i).getDesc_prato();
				rows[i][2] = list.get(i).getDta_reg();
			}
		TheModel model = new TheModel(rows, columnName);
		Administrador.table_4.setModel(model);
		Administrador.table_4.setRowHeight(30);
		Administrador.table_4.getColumnModel().getColumn(0).setPreferredWidth(45);
		Administrador.table_4.getColumnModel().getColumn(1).setPreferredWidth(140);
		Administrador.table_4.getColumnModel().getColumn(2).setPreferredWidth(110);
		Administrador.table_4.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		Administrador.table_4.getTableHeader().setReorderingAllowed(false);
	}
	
	public static void Insere_prato() throws SQLException
	{
		Connection conn_prato_cod = null;
		@SuppressWarnings("unused")
		PreparedStatement pst_rs_prato_cod = null;
		conn_prato_cod = siar.JavaConection.ConnecrDb(); 
		if(((JTextField)Administrador.DC_prato.getDateEditor().getUiComponent()).getText().isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Deve Inserir a Data de Registo!");
            ((JTextField)Administrador.DC_prato.getDateEditor().getUiComponent()).requestFocus();
            return;
        }
    	if((Administrador.Desc_Prato.getText().length()==0))
    	{
    	 JOptionPane.showMessageDialog(null,"Deve Introduzir a Descrição do Prato!");
    	}
    	else
    	{	
    		String sql="insert into siar.siar_prato(siar_prato.cod_prato,siar_prato.desc_prato,siar_prato.Dta_registo)values(?,?,?)"; 
    		pst_rs_prato_cod= conn_prato_cod.prepareStatement(sql);
    		atua_seq_prato();
	        pst_rs_prato_cod.setString(1,ad_prato);
	        pst_rs_prato_cod.setString(2,Administrador.Desc_Prato.getText());
	        pst_rs_prato_cod.setDate(3,Data_Read_Values.convertUtilDateToSqlDate(Administrador.DC_prato.getDate()));
	        pst_rs_prato_cod.executeQuery();
	        JOptionPane.showMessageDialog(null,"Prato Inserido com Sucesso!");
	        Administrador.DC_prato.setDate(null);
	        Administrador.Desc_Prato.setText(null);
    	}
    } 
	public static void atua_seq_prato()
    {
		Connection conn_prato_cod = null;
		ResultSet rs_prato_cod = null;
		PreparedStatement pst_rs_prato_cod = null;
		conn_prato_cod = siar.JavaConection.ConnecrDb();
		try
	      {
	    	 String sql="select siar.SIAR_S_prato.nextval as seguinte_prato from dual";
	    	 pst_rs_prato_cod = conn_prato_cod.prepareStatement(sql); 
	    	 rs_prato_cod=pst_rs_prato_cod.executeQuery();
	           if(rs_prato_cod.next())
	            {
	        	   ad_prato =rs_prato_cod.getString("seguinte_prato");
	            }
	      }
         catch(Exception e)
         {
        	 JOptionPane.showMessageDialog(null, "Erro ao atualizar Sequência prato!"+e);
         }
      }
	public static void remove_prato()
	{
		Connection conn_prato_cod = null;
		@SuppressWarnings("unused")
		ResultSet rs_prato_cod = null;
		PreparedStatement pst_rs_prato_cod = null;
		conn_prato_cod = siar.JavaConection.ConnecrDb();
    	if((Administrador.aux_cod_pr.getText().length()==0) || (Administrador.aux_desc_prato.getText().length()==0) || (Administrador.dta_reg_prato.getText().length()==0))
    	{
    	 JOptionPane.showMessageDialog(null,"Nenhum Registo Selecionado!");
    	}
     	else
    	{
	        int p = JOptionPane.showConfirmDialog(null, "Deseja Realmente Remover o Prato!","Prato Removida!",JOptionPane.YES_NO_OPTION);
	        if(p==0)
	        {
		          try{
			        	String sql="delete from siar.siar_prato where cod_prato='"+Administrador.aux_cod_pr.getText()+"'"; 
			        	pst_rs_prato_cod=conn_prato_cod.prepareStatement(sql);
			        	pst_rs_prato_cod.executeQuery();
			          }
				       catch(Exception e2)
				      {
				           JOptionPane.showMessageDialog(null,e2); 
				      }
		               prencher_pratos();
		               seleciona_linha_prat();
	        	}	       
	        } 
    } 
	public static void seleciona_linha_prat()
	{
		
		Connection conn_prato_cod = null;
		ResultSet rs_prato_cod = null;
		PreparedStatement pst_rs_prato_cod = null;
		conn_prato_cod = siar.JavaConection.ConnecrDb();
		int row = Administrador.table_4.getSelectedRow();
		if (row >= 0)	
		{	
		try
		 {
					String clica_tabela =(Administrador.table_4.getModel().getValueAt(row, 0)).toString();
					String clica_nome =(Administrador.table_4.getModel().getValueAt(row, 1)).toString();
					String dta_reg = (Administrador.table_4.getModel().getValueAt(row, 2)).toString();
		            String conta="select cod_prato, desc_prato, dta_registo from siar.siar_prato where siar.siar_prato.cod_prato='"+clica_tabela+"'";
		            pst_rs_prato_cod=conn_prato_cod.prepareStatement(conta);
		            rs_prato_cod =pst_rs_prato_cod.executeQuery();
			           if(rs_prato_cod.next())
			            {
  			        	    String ad1 =rs_prato_cod.getString("cod_prato");
  			        	    Administrador.aux_cod_pr.setText(ad1);
  			        	    String ad2 =clica_nome;
  			        	    Administrador.aux_desc_prato.setText(ad2);
  			        	    String ad3 =dta_reg;
  			        	    Administrador.dta_reg_prato.setText(ad3);
			            }
			}
			catch(Exception e)
			{
				Administrador.aux_cod_pr.setText(null);
				Administrador.aux_desc_prato.setText(null);
				Administrador.dta_reg_prato.setText(null);
			}
          }
	}
}
