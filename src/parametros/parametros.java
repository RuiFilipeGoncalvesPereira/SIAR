package parametros;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;

import Entities.Parametro;
import Model.TheModel;
import Query.MyQuery;
import siar.Administrador;

public class parametros {
	
	public static String ad_parametro;
	public static void prenche_parametros()
	{
		MyQuery mq = new MyQuery();
		ArrayList<Parametro> list = mq.Mostra_Parametros();
		String[] columnName = {"Código","Valor","Descrição do Parâmetro"}; 
		Object [][] rows = new Object[list.size()][3];
		for(int i = 0; i < list.size(); i++)
			{
				rows[i][0] = list.get(i).getCod_param();
				rows[i][1] = list.get(i).getValor_param();
				rows[i][2] = list.get(i).getDesc_param();
			}
		TheModel model = new TheModel(rows, columnName);
		Administrador.table_7.setModel(model);
		Administrador.table_7.setRowHeight(30);
		Administrador.table_7.getColumnModel().getColumn(0).setPreferredWidth(25);
		Administrador.table_7.getColumnModel().getColumn(1).setPreferredWidth(70);
		Administrador.table_7.getColumnModel().getColumn(2).setPreferredWidth(385);
		Administrador.table_7.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		Administrador.table_7.getTableHeader().setReorderingAllowed(false);
	}
	public static void Insere_parametros() throws SQLException
	{
		Connection conn_param = null;
		@SuppressWarnings("unused")
		ResultSet rs_param = null;
		PreparedStatement pst_rs_param = null;
		conn_param = siar.JavaConection.ConnecrDb(); 
		if((Administrador.Val_Par.getText().length()==0))
    	{
    	 JOptionPane.showMessageDialog(null,"Deve Introduzir o Valor do Parâmetro!");
    	}
    	if((Administrador.Desc_Par.getText().length()==0))
    	{
    	 JOptionPane.showMessageDialog(null,"Deve Introduzir a Descrição do Parâmetro!");
    	}
    	else
    	{	
			String sql="insert into siar.siar_parametros(Cod_Parametro,Valor_Parametro,Descricao_Parametro)values(?,?,?)"; 
			pst_rs_param=conn_param.prepareStatement(sql);
	        atua_seq_parametro();
	        pst_rs_param.setString(1, ad_parametro);
	        pst_rs_param.setString(2,Administrador.Val_Par.getText());
	        pst_rs_param.setString(3,Administrador.Desc_Par.getText());
	        pst_rs_param.executeQuery();
	        JOptionPane.showMessageDialog(null,"Dados Inseridos com Sucesso!");
	        Administrador.Val_Par.setText(null);
	        Administrador.Val_Par.setText(null);
    	}
    }  
	public static void atua_seq_parametro()
    {
		Connection conn_param = null;
		ResultSet rs_param = null;
		PreparedStatement pst_rs_param = null;
		conn_param = siar.JavaConection.ConnecrDb(); 
		try
	      {
	    	 String sql="select siar.SIAR_S_PARAMETRO.nextval as seguinte_param from dual";
	    	 pst_rs_param = conn_param.prepareStatement(sql); 
	    	 rs_param=pst_rs_param.executeQuery();
	           if(rs_param.next())
	            {
	        	   ad_parametro =rs_param.getString("seguinte_param");
	            }
	      }
         catch(Exception e)
         {
        	 JOptionPane.showMessageDialog(null, "Erro ao atualizar Sequência Parâmetro!"+e);
         }
      }
	public static void seleciona_linha_param()
	{
		int row = Administrador.table_7.getSelectedRow();
		Connection conn_param = null;
		ResultSet rs_param = null;
		PreparedStatement pst_rs_param = null;
		conn_param = siar.JavaConection.ConnecrDb(); 
		if (row >= 0)	
		{	
		try
		 {
					String clica_tabela =(Administrador.table_7.getModel().getValueAt(row, 0).toString());
					String clica_valor =(Administrador.table_7.getModel().getValueAt(row, 1)).toString();
					String clica_desc =(Administrador.table_7.getModel().getValueAt(row, 2)).toString();
		            String conta="select siar.siar_parametros.cod_parametro Codigo,siar.siar_parametros.valor_parametro valor,siar.siar_parametros.descricao_parametro descricao from siar.siar_parametros where siar.siar_parametros.cod_parametro='"+clica_tabela+"'";
		            pst_rs_param=conn_param.prepareStatement(conta);
		            rs_param =pst_rs_param.executeQuery();
			           if(rs_param.next())
			            {
  			        	    String ad1 =rs_param.getString("Codigo");
  			        	    Administrador.txt_cod_aux.setText(ad1);
  			        	    String ad2 =clica_valor;
  			        	    Administrador.val_aux.setText(ad2);
  			        	    String ad3 =clica_desc;
  			        	    Administrador.desc_aux.setText(ad3);
			            }
			}
			catch(Exception e)
			{
				Administrador.txt_cod_aux.setText(null);
				Administrador.val_aux.setText(null);
				Administrador.desc_aux.setText(null);
			}
          }
	}
	public static void remove_parametro()
	{
		Connection conn_param = null;
		@SuppressWarnings("unused")
		ResultSet rs_param = null;
		PreparedStatement pst_rs_param = null;
		conn_param = siar.JavaConection.ConnecrDb(); 
    	if((Administrador.txt_cod_aux.getText().length()==0) || (Administrador.val_aux.getText().length()==0) || (Administrador.desc_aux.getText().length()==0))
    	{
    	 JOptionPane.showMessageDialog(null,"Nenhum Registo Selecionado!");
    	}
     	else
    	{
	        int p = JOptionPane.showConfirmDialog(null, "Deseja Realmente Remover o Parâmetro!","Parâmetro Removido!",JOptionPane.YES_NO_OPTION);
	        if(p==0)
	        {
		          try{
			        	String sql="delete from siar.siar_parametros where cod_parametro='"+Administrador.txt_cod_aux.getText()+"'"; 
			        	pst_rs_param=conn_param.prepareStatement(sql);
			        	pst_rs_param.executeQuery();
			          }
				       catch(Exception e2)
				      {
				           JOptionPane.showMessageDialog(null,e2); 
				      }
		               prenche_parametros();
		               seleciona_linha_param();
	        	}	       
	        } 
    } 
	public static void update_param()
    {
		Connection conn_param = null;
		@SuppressWarnings("unused")
		ResultSet rs_param = null;
		PreparedStatement pst_rs_param = null;
		conn_param = siar.JavaConection.ConnecrDb(); 
		if((Administrador.txt_cod_aux.getText().length()==0)||(Administrador.val_aux.getText().length()==0)||(Administrador.desc_aux.getText().length()==0))
    	{
    	 JOptionPane.showMessageDialog(null,"Nenhum Registo Selecionado!");
    	}
    	else
    	{
			try
		      {
				String sql="update siar.siar_parametros set valor_parametro='"+Administrador.val_aux.getText()+"', descricao_parametro='"+Administrador.desc_aux.getText()+"' where cod_parametro='"+Administrador.txt_cod_aux.getText()+"'";
		    	 pst_rs_param = conn_param.prepareStatement(sql); 
		    	 rs_param=pst_rs_param.executeQuery();
		    	 prenche_parametros();
		      }
	         catch(Exception e)
	         {
	        	 JOptionPane.showMessageDialog(null, "Erro ao alterar Parâmetro!"+e);
	         }
    	}
      }

}
