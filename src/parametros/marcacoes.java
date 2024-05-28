package parametros;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import Check_Validation.Check_Holiday;
import siar.Data;
import siar.JavaConection;
import siar.Login;
import siar.Marcacao;
import siar.Marcacoes;
import siar.MyQuery_Marc;


public class marcacoes {
	
	public static Connection conn_mar= null;
	public static Connection conn_rem = null;
	public static Connection conn_prato_cod= null;
	public static Connection conn_prato= null;
	public static ResultSet rs_conn_mar = null;
	public static PreparedStatement pstconn_mar = null;
	public static SimpleDateFormat df2 = new SimpleDateFormat("dd-MM-yyyy");
	public static ResultSet rs_prato = null;
	public static PreparedStatement pstprato = null;
	public static PreparedStatement P_prato_cod= null;
	public static PreparedStatement pstprato_rem = null;
	public static ResultSet rs_prato_rem = null;
	public static Date now = new Date(System.currentTimeMillis());
	static String strDate = df2.format(now);
	public static JScrollPane scrollPane;
	static Check_Holiday CH = new Check_Holiday();
	static Data mostra_data;
	
	public static void prencher_marcacoes()
	{
		MyQuery_Marc mq = new MyQuery_Marc();
		ArrayList<Marcacao> list = mq.Mostra_Marcacoes();
		String[] columnName = {"Num. Mec.","Data da Refeição","Refeição","Prato","Data de Desat.","Data de Reg.","Verificada","Cod.Ref.","Cod.Pra."}; 
		Object [][] rows = new Object[list.size()][9];
		for(int i = 0; i < list.size(); i++)
			{
				rows[i][0] = list.get(i).getNum_mec();
				rows[i][1] = list.get(i).getDta_ref();
				rows[i][2] = list.get(i).getDes_ref();
				rows[i][3] = list.get(i).getDes_pr();
				rows[i][4] = list.get(i).getDta_des();
				rows[i][5] = list.get(i).getDta_reg();
				rows[i][6] = list.get(i).getVerec();
				rows[i][7] = list.get(i).getCod_ref();
				rows[i][8] = list.get(i).getCod_pr();
			}
		Model.Model_Schedules model = new Model.Model_Schedules(rows, columnName);
		Marcacoes.table.setModel(model);
		Marcacoes.table.setRowHeight(30);
		Marcacoes.table.getColumnModel().getColumn(0).setMinWidth(0);
		Marcacoes.table.getColumnModel().getColumn(0).setMaxWidth(0);
		Marcacoes.table.getColumnModel().getColumn(1).setPreferredWidth(140);
		Marcacoes.table.getColumnModel().getColumn(2).setPreferredWidth(110);
		Marcacoes.table.getColumnModel().getColumn(3).setPreferredWidth(100);
		Marcacoes.table.getColumnModel().getColumn(4).setMinWidth(0);
		Marcacoes.table.getColumnModel().getColumn(4).setMaxWidth(0);
		Marcacoes.table.getColumnModel().getColumn(5).setPreferredWidth(140);
		Marcacoes.table.getColumnModel().getColumn(6).setPreferredWidth(80);
		Marcacoes.table.getColumnModel().getColumn(7).setMinWidth(0);
		Marcacoes.table.getColumnModel().getColumn(7).setMaxWidth(0);
		Marcacoes.table.getColumnModel().getColumn(8).setMinWidth(0);
		Marcacoes.table.getColumnModel().getColumn(8).setMaxWidth(0);
		Marcacoes.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		Marcacoes.table.getTableHeader().setReorderingAllowed(false);
	}
	public static void prencher_marcacoes_Passadas()
	{
		MyQuery_Marc mq = new MyQuery_Marc();
		ArrayList<Marcacao> list = mq.Mostra_MarcacoesPassadas();
		String[] columnName = {"Num. Mec.","Data da Refeição","Refeição","Prato","Data de Desat.","Data de Reg.","Verificada","Cod.Ref.","Cod.Pra."}; 
		Object [][] rows = new Object[list.size()][9];
		for(int i = 0; i < list.size(); i++)
			{
				rows[i][0] = list.get(i).getNum_mec();
				rows[i][1] = list.get(i).getDta_ref();
				rows[i][2] = list.get(i).getDes_ref();
				rows[i][3] = list.get(i).getDes_pr();
				rows[i][4] = list.get(i).getDta_des(); 
				rows[i][5] = list.get(i).getDta_reg();
				rows[i][6] = list.get(i).getVerec();
				rows[i][7] = list.get(i).getCod_ref();
				rows[i][8] = list.get(i).getCod_pr();
			}
		Model.Model_Schedules model = new Model.Model_Schedules(rows, columnName);
		Marcacoes.table.setModel(model);
		Marcacoes.table.setRowHeight(30);
		Marcacoes.table.getColumnModel().getColumn(0).setMinWidth(0);
		Marcacoes.table.getColumnModel().getColumn(0).setMaxWidth(0);
		Marcacoes.table.getColumnModel().getColumn(1).setPreferredWidth(140);
		Marcacoes.table.getColumnModel().getColumn(2).setPreferredWidth(110);
		Marcacoes.table.getColumnModel().getColumn(3).setPreferredWidth(100);
		Marcacoes.table.getColumnModel().getColumn(4).setMinWidth(0);
		Marcacoes.table.getColumnModel().getColumn(4).setMaxWidth(0);
		Marcacoes.table.getColumnModel().getColumn(5).setPreferredWidth(140);
		Marcacoes.table.getColumnModel().getColumn(6).setPreferredWidth(80);
		Marcacoes.table.getColumnModel().getColumn(7).setMinWidth(0);
		Marcacoes.table.getColumnModel().getColumn(7).setMaxWidth(0);
		Marcacoes.table.getColumnModel().getColumn(8).setMinWidth(0);
		Marcacoes.table.getColumnModel().getColumn(8).setMaxWidth(0);
		Marcacoes.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		Marcacoes.table.getTableHeader().setReorderingAllowed(false);
	}
	public static void prencher_marcacoes_Desativadas()
	{
		MyQuery_Marc mq = new MyQuery_Marc();
		ArrayList<Marcacao> list = mq.Mostra_MarcacoesDesativdas();
		String[] columnName = {"Num. Mec.","Data da Refeição","Refeição","Prato","Data de Desat.","Dat.Reg.","Verificada","Cod.Ref.","Cod.Pra."}; 
		Object [][] rows = new Object[list.size()][9];
		for(int i = 0; i < list.size(); i++)
			{
				rows[i][0] = list.get(i).getNum_mec();
				rows[i][1] = list.get(i).getDta_ref();
				rows[i][2] = list.get(i).getDes_ref();
				rows[i][3] = list.get(i).getDes_pr();
				rows[i][4] = list.get(i).getDta_des();
				rows[i][5] = list.get(i).getDta_reg();
				rows[i][6] = list.get(i).getVerec();
				rows[i][7] = list.get(i).getCod_ref();
				rows[i][8] = list.get(i).getCod_pr();
			}
		Model.Model_Schedules model = new Model.Model_Schedules(rows, columnName);
		Marcacoes.table.setModel(model);
		Marcacoes.table.setRowHeight(30);
		Marcacoes.table.getColumnModel().getColumn(0).setMinWidth(0);
		Marcacoes.table.getColumnModel().getColumn(0).setMaxWidth(0);
		Marcacoes.table.getColumnModel().getColumn(1).setPreferredWidth(140);
		Marcacoes.table.getColumnModel().getColumn(2).setPreferredWidth(110);
		Marcacoes.table.getColumnModel().getColumn(3).setPreferredWidth(100);
		Marcacoes.table.getColumnModel().getColumn(4).setPreferredWidth(130);
		Marcacoes.table.getColumnModel().getColumn(5).setPreferredWidth(140);
		Marcacoes.table.getColumnModel().getColumn(6).setPreferredWidth(80);
		Marcacoes.table.getColumnModel().getColumn(7).setMinWidth(0);
		Marcacoes.table.getColumnModel().getColumn(7).setMaxWidth(0);
		Marcacoes.table.getColumnModel().getColumn(8).setMinWidth(0);
		Marcacoes.table.getColumnModel().getColumn(8).setMaxWidth(0);
		Marcacoes.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		Marcacoes.table.getTableHeader().setReorderingAllowed(false);
	}
	public static void efetua_Marcacoes()
	{
		conn_mar = JavaConection.ConnecrDb();
		conn_rem = JavaConection.ConnecrDb(); 
		conn_prato = JavaConection.ConnecrDb(); 
		conn_prato_cod = JavaConection.ConnecrDb(); 
		String dataref = df2.format(Marcacoes.dt_ref.getDate());
		try
		{
			String conta="select count(*) from siar.siar_marcacoes where Num_Mecanog='"+Login.txtUser.getText()+"' and to_char(dta_Refeicao,'dd-mm-yyyy')='"+dataref+"' and Cod_Refeicao='"+Marcacoes.jcodref.getText()+"' and dta_desativo is null";
			pstconn_mar=conn_mar.prepareStatement(conta);
			pstconn_mar=conn_mar.prepareStatement(conta ,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs_conn_mar=pstconn_mar.executeQuery();
			
            String conta2="select count(*) from siar.siar_marcacoes where Num_Mecanog='"+Login.txtUser.getText()+"' and to_char(dta_Refeicao,'dd-mm-yyyy')='"+dataref+"' and Cod_Refeicao='"+Marcacoes.jcodref.getText()+"' and dta_desativo is not null";
			pstprato_rem=conn_rem.prepareStatement(conta2);
            pstprato_rem=conn_rem.prepareStatement(conta2 ,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs_prato_rem=pstprato_rem.executeQuery();

            rs_conn_mar.next();
            int conta_ref = rs_conn_mar.getInt(1);
            rs_prato_rem.next();
            int conta_ref_rem = rs_prato_rem.getInt(1);

            if(conta_ref > 0) // Entra aqui Quando Já Existe o Agendamento Especifico
              {
                if (conta_ref_rem == 0) 
                {	
            	 JOptionPane.showMessageDialog(null,"Essa Refeição Já foi Agendada Noutra Altura!"); 
                 return;
                } 
              }
            else if(conta_ref == 0)
            {  
	             if(conta_ref_rem == 0)	
	             {	 
	            	String sql="insert into siar.siar_marcacoes(Num_Mecanog,Dta_Refeicao,Cod_Refeicao,Cod_Prato,Dta_Desativo,Dta_Registo,Verificacao)values(?,?,?,?,?,?,?)"; 
		            pstprato=conn_prato.prepareStatement(sql);
		            pstprato.setString(1, Login.txtUser.getText());
		            pstprato.setDate(2,Data.convertUtilDateToSqlDate(Marcacoes.dt_ref.getDate()));
		            pstprato.setString(3, Marcacoes.jcodref.getText());
		            pstprato.setString(4, Marcacoes.jcodprato.getText()); 
		            pstprato.setDate(5,null);
		            pstprato.setDate(6,now);
		            pstprato.setString(7, "N");
		            pstprato.executeQuery();
		            JOptionPane.showMessageDialog(null,"Dados Inseridos com Sucesso!");
	             }
		        else if (conta_ref_rem > 0)
		         {
		        	 try{
			            	String sql="update siar.siar_marcacoes set dta_desativo = null, cod_prato = '"+Marcacoes.jcodprato.getText()+"' where Num_Mecanog='"+Login.txtUser.getText()+"'and to_char(dta_Refeicao,'dd-mm-yyyy')='"+dataref+"' and Cod_Refeicao='"+Marcacoes.jcodref.getText()+"' and dta_desativo is not null"; 
				            pstprato=conn_prato.prepareStatement(sql);
				            pstprato.executeQuery();
			                JOptionPane.showMessageDialog(null,"Refeição Remarcada!"); 
		            	  }
		     	       catch(Exception e)
				        {
				           JOptionPane.showMessageDialog(null,e); 
				        }
		              try{
				        	String sql="delete from siar.siar_anulacoes where Num_Mec='"+Login.txtUser.getText()+"'and to_char(dta_Refeicao,'dd-mm-yyyy')='"+dataref+"' and Cod_Refeicao='"+Marcacoes.jcodref.getText()+"'"; 
				        	P_prato_cod=conn_prato_cod.prepareStatement(sql);
				        	P_prato_cod.executeQuery();
				            }
					       catch(Exception e2)
					        {
					           JOptionPane.showMessageDialog(null,e2); 
					        }
		         }
            }
       }
       catch(Exception e)
        {
           JOptionPane.showMessageDialog(null,e); 
        }
		marcacoes.prencher_marcacoes();
	}
	public static void seleciona_linha()
	{
		int row = Marcacoes.table.getSelectedRow();
		conn_mar = JavaConection.ConnecrDb();
	    mostra_data = new Data();
        mostra_data.le_hora();
		if (row >= 0)	
		{	
		try{
					String clica_tabela =(Marcacoes.table.getModel().getValueAt(row, 0).toString());
					String clica_data =(Marcacoes.table.getModel().getValueAt(row, 1)).toString();
					String clica_dta_res =(Marcacoes.table.getModel().getValueAt(row, 5)).toString();
					String clica_codigo =(Marcacoes.table.getModel().getValueAt(row, 7).toString());
					String clica_pra =(Marcacoes.table.getModel().getValueAt(row, 8)).toString(); 
		            String conta="select * from siar.siar_marcacoes where siar.siar_marcacoes.Num_Mecanog='"+clica_tabela+"'and to_char(siar.siar_marcacoes.Dta_Refeicao,'dd-mm-yyyy')='"+clica_data+"'and siar.siar_marcacoes.Cod_refeicao='"+clica_codigo+"'and siar.siar_marcacoes.Cod_prato='"+clica_pra+"'and to_char(siar.siar_marcacoes.Dta_Registo,'dd-mm-yyyy')='"+clica_dta_res+"'";
					pstconn_mar=conn_mar.prepareStatement(conta);
					rs_conn_mar=pstconn_mar.executeQuery();
			           if(rs_conn_mar.next())
			            {
  			        	    String ad1 = rs_conn_mar.getString("Num_Mecanog");
  			        	    Marcacoes.nmec_aux.setText(ad1);
			            	Date ad2 = rs_conn_mar.getDate("Dta_Refeicao");
			            	Marcacoes.dta_ref_aux.setText(df2.format(ad2));
			            	String ad3 = rs_conn_mar.getString("Cod_Refeicao");
			            	Marcacoes.cod_ref_aux.setText(ad3);
			            	String ad4 = rs_conn_mar.getString("Cod_Prato");
			            	Marcacoes.cod_pra_aux.setText(ad4);
			            	Date ad5 = rs_conn_mar.getDate("Dta_Registo");
			            	Marcacoes.dta_registo_aux.setText(df2.format(ad5));
			            }
			}
			catch(Exception e)
			{
				JOptionPane.showMessageDialog(null,e);
				Marcacoes.nmec_aux.setText(null);
				Marcacoes.dta_ref_aux.setText(null);
				Marcacoes.cod_ref_aux.setText(null);
				Marcacoes.cod_pra_aux.setText(null);
				Marcacoes.dta_registo_aux.setText(null);
			}
          }
	}
	public static void anula_refeicao()
	{
		String des_horalimite = CH.check_holiday(41);
		conn_prato = JavaConection.ConnecrDb(); 
    	if((Marcacoes.nmec_aux.getText().length()==0) || (Marcacoes.dta_ref_aux.getText().length()==0) || (Marcacoes.cod_ref_aux.getText().length()==0))
    	{
    	 JOptionPane.showMessageDialog(null,"Nenhum Registo Selecionado!");
    	}
     	else
    	{
	        int p = JOptionPane.showConfirmDialog(null, "Deseja Realmente Desmarcar a Refeição!","Refeição Desmarcada!",JOptionPane.YES_NO_OPTION);
	        if(p==0)
	        {
	        	if ((mostra_data.horamin.compareTo(des_horalimite)>=0) && (Marcacoes.dta_ref_aux.getText().equals(strDate)))
	        	{
	        		JOptionPane.showMessageDialog(null,"Já passa das "+des_horalimite+", Não pode desmarcar a Refeição!");
	        	}
	        	else
	        	{	
		          try{
			        	String sql="insert into siar.siar_anulacoes(Num_Mec,Cod_Refeicao,Cod_Prato,Dta_Refeicao,Dta_Desativo,Dta_Registo_Inicial,Dta_Registo_final)values(?,?,?,?,?,?,?)"; 
			            pstprato=conn_prato.prepareStatement(sql);
			    		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			            java.util.Date invoiceDate = formatter.parse(Marcacoes.dta_ref_aux.getText());
			            java.util.Date invoiceDta_Res = formatter.parse(Marcacoes.dta_registo_aux.getText());

			            java.sql.Date sqlDate = new java.sql.Date(invoiceDate.getTime());
			            java.sql.Date sqlDate_res = new java.sql.Date(invoiceDta_Res.getTime());
			            

			            pstprato.setString(1, Marcacoes.nmec_aux.getText());
			            pstprato.setString(2, Marcacoes.cod_ref_aux.getText());
			            pstprato.setString(3, Marcacoes.cod_pra_aux.getText());
			            pstprato.setDate(4,sqlDate);
			            pstprato.setDate(5,now);
			            pstprato.setDate(6,sqlDate_res);
			            pstprato.setDate(7,now);
			            pstprato.executeQuery();
			          }
				       catch(Exception e2)
				      {
				           JOptionPane.showMessageDialog(null,e2); 
				      }
			         try
			         {
			        	String sql="update siar.siar_marcacoes set dta_desativo = sysdate where Num_Mecanog='"+Marcacoes.nmec_aux.getText()+"' and to_char(Dta_Refeicao,'dd-mm-yyyy')='"+Marcacoes.dta_ref_aux.getText()+"' and Cod_Refeicao='"+Marcacoes.cod_ref_aux.getText()+"'";
			        	pstconn_mar=conn_mar.prepareStatement(sql);
			        	pstconn_mar.executeQuery();
			        	JOptionPane.showMessageDialog(null,"Refeição Desmarcada com Sucesso!"); 
			          }
				       catch(Exception e2)
					  {
				           JOptionPane.showMessageDialog(null,e2);
				      }
			           marcacoes.prencher_marcacoes();
			           marcacoes.seleciona_linha();
	        	}	       
	        } 
    	}   
	}
	public static void Calcular_Dia_Correto()
	{
		
	    mostra_data = new Data();
        mostra_data.le_hora();
		Marcacoes.dt_ref.setDate(now);
		String horalimite = CH.check_holiday(2);
		if((mostra_data.horamin.compareTo(horalimite)>=0))
		{  
			Calendar cal = Calendar.getInstance();
			cal.setTime(Marcacoes.dt_ref.getDate());
			cal.add(Calendar.DAY_OF_MONTH, 2);
			java.util.Date futureDate2 = cal.getTime();	
			Marcacoes.dt_ref.setDate(futureDate2);
		}
		else
		{
			Calendar cal = Calendar.getInstance();
			cal.setTime(Marcacoes.dt_ref.getDate());
			cal.add(Calendar.DAY_OF_MONTH, 1);
			java.util.Date futureDate1 = cal.getTime();	
			Marcacoes.dt_ref.setDate(futureDate1);	
		}
	}

}
