package parametros;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import Data.Data;
import Entities.Refeicao;
import siar.Administrador;
import siar.JavaConection;
import siar.MyQuery;
import siar.TheModel;


public class refeicoes {
	
	
	public static String ad_refeicao;
	
	public static void prencher_Refeicoes()
	{
		MyQuery mq = new MyQuery();
		ArrayList<Refeicao> list = mq.Mostra_Refeicoes();
		String[] columnName = {"Código","Descrição da Refeição","Data de Registo"}; 
		Object [][] rows = new Object[list.size()][3];
		for(int i = 0; i < list.size(); i++)
			{
				rows[i][0] = list.get(i).getCod_refeicao();
				rows[i][1] = list.get(i).getDesc_refeicao();
				rows[i][2] = list.get(i).getDta_reg();
			}
		TheModel model = new TheModel(rows, columnName);
		Administrador.table_3.setModel(model);
		Administrador.table_3.setRowHeight(30);
		Administrador.table_3.getColumnModel().getColumn(0).setPreferredWidth(45);
		Administrador.table_3.getColumnModel().getColumn(1).setPreferredWidth(140);
		Administrador.table_3.getColumnModel().getColumn(2).setPreferredWidth(110);
		Administrador.table_3.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		Administrador.table_3.getTableHeader().setReorderingAllowed(false);
	}
	public static void Insere_Refeicao() throws SQLException 
	{ 
		Connection  conn_conref_ins  = null;
		@SuppressWarnings("unused")
		PreparedStatement pst_insref = null;
		conn_conref_ins = siar.JavaConection.ConnecrDb();
		if(((JTextField)Administrador.DC_ref.getDateEditor().getUiComponent()).getText().isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Deve Inserir a Data da Refeição!");
            ((JTextField)Administrador.DC_ref.getDateEditor().getUiComponent()).requestFocus();
            return;
        }
    	if((Administrador.Desc_Ref.getText().length()==0))
    	{
    	 JOptionPane.showMessageDialog(null,"Deve Introduzir a Descrição da Refeição!");
    	}
    	else
    	{	
    		String sql="insert into siar.siar_refeicao(cod_refeicao,desc_refeicao,dta_registo)values(?,?,?)"; 
			pst_insref=conn_conref_ins.prepareStatement(sql);
	        atua_seq_refeicao();
	        pst_insref.setString(1,ad_refeicao);
	        pst_insref.setString(2,Administrador.Desc_Ref.getText());
	        pst_insref.setDate(3,Data.convertUtilDateToSqlDate(Administrador.DC_ref.getDate()));
	        pst_insref.executeQuery();
	        JOptionPane.showMessageDialog(null,"Refeição Inserida com Sucesso!");
	        Administrador.DC_ref.setDate(null);
	        Administrador.Desc_Ref.setText(null);
    	}
    }  
	public static void atua_seq_refeicao()
    {
		PreparedStatement pst_seqref = null;
		Connection conn_seqref = null;
		ResultSet resseqref = null;
        conn_seqref = JavaConection.ConnecrDb();
		try
	      {
	    	 String sql="select siar.SIAR_S_refeicao.nextval as seguinte_ref from dual";
	    	 pst_seqref = conn_seqref.prepareStatement(sql); 
	    	 resseqref=pst_seqref.executeQuery();
	           if(resseqref.next())
	            {
	        	   ad_refeicao =resseqref.getString("seguinte_ref");
	            }
	      }
         catch(Exception e)
         {
        	 JOptionPane.showMessageDialog(null, "Erro ao atualizar Sequ�ncia refeicao!"+e);
         }
      }
	public static void seleciona_linha_ref()
	{
		PreparedStatement pst_sel_ref = null;
		Connection conn_sel_ref = null;
		ResultSet rs_sel_ref = null;
		conn_sel_ref = JavaConection.ConnecrDb();
		int row = Administrador.table_3.getSelectedRow();
		if (row >= 0)	
		{	
		try
		 {
					String clica_tabela =(Administrador.table_3.getModel().getValueAt(row, 0)).toString();
					String clica_nome =(Administrador.table_3.getModel().getValueAt(row, 1)).toString();
					String dta_reg = (Administrador.table_3.getModel().getValueAt(row, 2)).toString();
		            String conta="select cod_refeicao, desc_refeicao, dta_registo from siar.siar_refeicao where siar.siar_refeicao.cod_refeicao='"+clica_tabela+"'";
		            pst_sel_ref=conn_sel_ref.prepareStatement(conta);
					rs_sel_ref =pst_sel_ref.executeQuery();
			           if(rs_sel_ref.next())
			            {
  			        	    String ad1 =rs_sel_ref.getString("cod_refeicao");
  			        	    Administrador.cod_aux.setText(ad1);
  			        	    String ad2 =clica_nome;
  			        	    Administrador.ref_aux.setText(ad2);
  			        	    String ad3 =dta_reg;
  			        	    Administrador.dta_ref_aux.setText(ad3);
			            }
			}
			catch(Exception e)
			{
				Administrador.cod_aux.setText(null);
				Administrador.ref_aux.setText(null);
				Administrador.dta_ref_aux.setText(null);
			}
          }
	}
	public static void remove_refeicao()
	{
		/*String des_horalimite = CH.check_holiday(41);*/
		Connection  conn_conref  = null;
		PreparedStatement pstatu_ref = null;
		conn_conref = JavaConection.ConnecrDb();
    	if((Administrador.cod_aux.getText().length()==0) || (Administrador.ref_aux.getText().length()==0) || (Administrador.dta_ref_aux.getText().length()==0))
    	{
    	 JOptionPane.showMessageDialog(null,"Nenhum Registo Selecionado!");
    	}
     	else
    	{
	        int p = JOptionPane.showConfirmDialog(null, "Deseja Realmente Remover a Refeição!","Refeição Removida!",JOptionPane.YES_NO_OPTION);
	        if(p==0)
	        {
		          try{
			        	String sql="delete from siar.siar_refeicao where cod_refeicao='"+Administrador.cod_aux.getText()+"'"; 
			        	pstatu_ref=conn_conref.prepareStatement(sql);
			        	pstatu_ref.executeQuery();
			          }
				       catch(Exception e2)
				      {
				           JOptionPane.showMessageDialog(null,e2); 
				      }
		                prencher_Refeicoes();
					   seleciona_linha_ref();
	        	    }	       
	    } 
    }   
}
