package parametros;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;

import siar.Administrador;
import siar.Dominio;
import siar.MyQuery;
import siar.TheModel;

public class dominios {
	
	public static void prenche_dominios()
	{
		MyQuery mq = new MyQuery();
		ArrayList<Dominio> list = mq.Mostra_Dominio();
		String[] columnName = {"Valor","Dominio","Descrição do Dominio"}; 
		Object [][] rows = new Object[list.size()][3];
		for(int i = 0; i < list.size(); i++)
			{
				rows[i][0] = list.get(i).getValor();
				rows[i][1] = list.get(i).getDominio();
				rows[i][2] = list.get(i).getDesc_dom();
			}
		TheModel model = new TheModel(rows, columnName);
		Administrador.table_6.setModel(model);
		Administrador.table_6.setRowHeight(30);
		Administrador.table_6.getColumnModel().getColumn(0).setPreferredWidth(55);
		Administrador.table_6.getColumnModel().getColumn(1).setPreferredWidth(70);
		Administrador.table_6.getColumnModel().getColumn(2).setPreferredWidth(325);
		Administrador.table_6.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		Administrador.table_6.getTableHeader().setReorderingAllowed(false);
	}
	public static void Insere_dominios() throws SQLException
	{
		Connection conn_param = null;
		@SuppressWarnings("unused")
		ResultSet rs_param = null;
		PreparedStatement pst_rs_param = null;
		conn_param = siar.JavaConection.ConnecrDb(); 
		if((Administrador.Dom.getText().length()==0))
    	{
    	 JOptionPane.showMessageDialog(null,"Deve Introduzir o Dominio!");
    	}
    	if((Administrador.Desc_dom.getText().length()==0))
    	{
    	 JOptionPane.showMessageDialog(null,"Deve Introduzir a Descrição do Dominio!");
    	}
    	if((Administrador.Val.getText().length()==0))
    	{
    	 JOptionPane.showMessageDialog(null,"Deve Introduzir o Valor do Dominio!");
    	}
    	else
    	{	
			String sql="insert into siar.siar_dominios(Valor,Dominio,Desc_Dominio)values(?,?,?)"; 
			pst_rs_param=conn_param.prepareStatement(sql);
	        pst_rs_param.setString(1,Administrador.Val.getText());
	        pst_rs_param.setString(2,Administrador.Dom.getText());
	        pst_rs_param.setString(3,Administrador.Desc_dom.getText());
	        pst_rs_param.executeQuery();
	        JOptionPane.showMessageDialog(null,"Dados Inseridos com Sucesso!");
	        Administrador.Val.setText(null);
	        Administrador.Dom.setText(null);
	        Administrador.Desc_dom.setText(null);
    	}
    }
	public static void seleciona_linha_dom()
	{
		int row = Administrador.table_6.getSelectedRow();
		Connection conn_param = null;
		ResultSet rs_param = null;
		PreparedStatement pst_rs_param = null;
		conn_param = siar.JavaConection.ConnecrDb(); 
		if (row >= 0)	
		{	
		try
		 {
					String clica_valor  =(Administrador.table_6.getModel().getValueAt(row, 0).toString());
					String clica_dom =(Administrador.table_6.getModel().getValueAt(row, 1)).toString();
					String clica_desc =(Administrador.table_6.getModel().getValueAt(row, 2)).toString();
		            String conta="select siar.siar_dominios.valor val,siar.siar_dominios.dominio dom,siar.siar_dominios.desc_dominio desc_ FROM siar.siar_dominios where siar.siar_dominios.valor='"+clica_valor+"'";
		            pst_rs_param=conn_param.prepareStatement(conta);
		            rs_param =pst_rs_param.executeQuery();
			           if(rs_param.next())
			            {
  			        	    String ad1 =clica_valor;
  			        	    Administrador.dom_aux.setText(ad1);
  			        	    String ad2 =clica_dom;
  			        	    Administrador.valor_dom_aux.setText(ad2);
  			        	    String ad3 =clica_desc;
  			        	    Administrador.desc_aux_dom.setText(ad3);
			            }
			}
			catch(Exception e)
			{
				Administrador.dom_aux.setText(null);
				Administrador.valor_dom_aux.setText(null);
				Administrador.desc_aux_dom.setText(null);
			}
          }
	}
	public static void remove_dominio()
	{
		Connection conn_param = null;
		@SuppressWarnings("unused")
		ResultSet rs_param = null;
		PreparedStatement pst_rs_param = null;
		conn_param = siar.JavaConection.ConnecrDb(); 
    	if((Administrador.dom_aux.getText().length()==0) || (Administrador.valor_dom_aux.getText().length()==0) || (Administrador.desc_aux_dom.getText().length()==0))
    	{
    	 JOptionPane.showMessageDialog(null,"Nenhum Registo Selecionado!");
    	}
     	else
    	{
	        int p = JOptionPane.showConfirmDialog(null, "Deseja Realmente Remover o Dominio!","Dominio Removido!",JOptionPane.YES_NO_OPTION);
	        if(p==0)
	        {
		          try{
			        	String sql="delete from siar.siar_dominios where valor='"+Administrador.dom_aux.getText()+"'"; 
			        	pst_rs_param=conn_param.prepareStatement(sql);
			        	pst_rs_param.executeQuery();
			          }
				       catch(Exception e2)
				      {
				           JOptionPane.showMessageDialog(null,e2); 
				      }
					prenche_dominios();
					seleciona_linha_dom();
	        	}	       
	        } 
    } 
	public static void update_dom()
    {
		Connection conn_param = null;
		@SuppressWarnings("unused")
		ResultSet rs_param = null;
		PreparedStatement pst_rs_param = null;
		conn_param = siar.JavaConection.ConnecrDb(); 
		if((Administrador.dom_aux.getText().length()==0) || (Administrador.valor_dom_aux.getText().length()==0) || (Administrador.desc_aux_dom.getText().length()==0))
    	{
    	 JOptionPane.showMessageDialog(null,"Nenhum Registo Selecionado!");
    	}
    	else
    	{
			try
		      {
				String sql="update siar.siar_dominios set valor='"+Administrador.dom_aux.getText()+"',  dominio='"+Administrador.valor_dom_aux.getText()+"', desc_dominio='"+Administrador.desc_aux_dom.getText()+"' where valor='"+Administrador.dom_aux.getText()+"'";
		    	 pst_rs_param = conn_param.prepareStatement(sql); 
		    	 rs_param=pst_rs_param.executeQuery();
		    	 prenche_dominios();
		      }
	         catch(Exception e)
	         {
	        	 JOptionPane.showMessageDialog(null, "Erro ao alterar dominio!"+e);
	         }
    	}
      }

}
