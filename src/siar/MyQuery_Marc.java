package siar;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class MyQuery_Marc {
	
	Connection conn_mar= null;
	ResultSet rs_conn_mar = null;
	Statement pstconn_mar = null;
	
	public ArrayList<Marcacao> Mostra_Marcacoes()
	{
		conn_mar = JavaConection.ConnecrDb();
		ArrayList<Marcacao> list = new ArrayList<Marcacao>();
		try
		{
			pstconn_mar = conn_mar.createStatement();
			rs_conn_mar = pstconn_mar.executeQuery("select siar.siar_marcacoes.Num_Mecanog num_mec,to_char(siar.siar_marcacoes.Dta_Refeicao,'dd-mm-yyyy') dtaref,siar.siar_refeicao.Desc_Refeicao descref,siar.siar_prato.Desc_Prato descpra,to_char(siar.siar_marcacoes.Dta_Desativo,'dd-mm-yyyy') dtades,to_char(siar.siar_marcacoes.Dta_Registo,'dd-mm-yyyy') as dtareg,siar.siar_marcacoes.verificacao ver,siar.siar_marcacoes.Cod_Refeicao cod_ref,siar.siar_marcacoes.Cod_prato cod_pra from siar.siar_marcacoes,siar.siar_refeicao,siar.siar_prato Where siar.siar_marcacoes.Cod_Refeicao=siar.siar_refeicao.Cod_Refeicao and siar.siar_marcacoes.Cod_Prato=siar.siar_prato.Cod_Prato and siar.siar_marcacoes.Num_Mecanog ='"+Login.txtUser.getText()+"' and trunc(siar.siar_marcacoes.Dta_Refeicao) >= trunc(sysdate) and siar.siar_marcacoes.dta_desativo is null order by siar.siar_marcacoes.Dta_Refeicao");
			Marcacao p;
			while(rs_conn_mar.next())
			{
				p = new Marcacao(
						   rs_conn_mar.getInt("num_mec"),
						   rs_conn_mar.getString("dtaref"),
						   rs_conn_mar.getString("descref"),
						   rs_conn_mar.getString("descpra"),
						   rs_conn_mar.getString("dtades"),
						   rs_conn_mar.getString("dtareg"),
						   rs_conn_mar.getString("ver"),
						   rs_conn_mar.getInt("cod_ref"),
						   rs_conn_mar.getInt("cod_pra")
							);
						list.add(p);
			}
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Error getting Current Appointments!"+e);
		}
		return list;
	}
	public ArrayList<Marcacao> Mostra_MarcacoesPassadas()
	{
		conn_mar = JavaConection.ConnecrDb();
		ArrayList<Marcacao> list = new ArrayList<Marcacao>();
		try
		{
			pstconn_mar = conn_mar.createStatement();
			rs_conn_mar = pstconn_mar.executeQuery("select siar.siar_marcacoes.Num_Mecanog num_mec,to_char(siar.siar_marcacoes.Dta_Refeicao,'dd-mm-yyyy') dtaref,siar.siar_refeicao.Desc_Refeicao descref,siar.siar_prato.Desc_Prato descpra,to_char(siar.siar_marcacoes.Dta_Desativo,'dd-mm-yyyy') dtades,to_char(siar.siar_marcacoes.Dta_Registo,'dd-mm-yyyy hh:mm:ss') as dtar_,siar.siar_marcacoes.verificacao ver,siar.siar_marcacoes.Cod_Refeicao cod_ref,siar.siar_marcacoes.Cod_prato cod_pra from siar.siar_marcacoes,siar.siar_refeicao,siar.siar_prato Where siar.siar_marcacoes.Cod_Refeicao=siar.siar_refeicao.Cod_Refeicao and siar.siar_marcacoes.Cod_Prato=siar.siar_prato.Cod_Prato and siar.siar_marcacoes.Num_Mecanog='"+Login.txtUser.getText()+"' and trunc(siar.siar_marcacoes.Dta_Refeicao) < trunc(sysdate) order by siar.siar_marcacoes.Dta_Refeicao desc");
			Marcacao p;
			while(rs_conn_mar.next())
			{
				p = new Marcacao(
						   rs_conn_mar.getInt("num_mec"),
						   rs_conn_mar.getString("dtaref"),
						   rs_conn_mar.getString("descref"),
						   rs_conn_mar.getString("descpra"),
						   rs_conn_mar.getString("dtades"),
						   rs_conn_mar.getString("dtar_"),
						   rs_conn_mar.getString("ver"),
						   rs_conn_mar.getInt("cod_ref"),
						   rs_conn_mar.getInt("cod_pra")
							);
						list.add(p);
			}
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Error getting Past Appointments!"+e);
		}
		return list;
	}
	public ArrayList<Marcacao> Mostra_MarcacoesDesativdas()
	{
		conn_mar = JavaConection.ConnecrDb();
		ArrayList<Marcacao> list = new ArrayList<Marcacao>();
		try
		{
			pstconn_mar = conn_mar.createStatement();
			rs_conn_mar = pstconn_mar.executeQuery("select siar.siar_marcacoes.Num_Mecanog num_mec,to_char(siar.siar_marcacoes.Dta_Refeicao,'dd-mm-yyyy') dtaref,siar.siar_refeicao.Desc_Refeicao descref,siar.siar_prato.Desc_Prato descpra,to_char(siar.siar_marcacoes.Dta_Desativo,'dd-mm-yyyy') dtades,to_char(siar.siar_marcacoes.Dta_Registo,'dd-mm-yyyy')dtareg,siar.siar_marcacoes.verificacao ver,siar.siar_marcacoes.Cod_Refeicao cod_ref,siar.siar_marcacoes.Cod_prato cod_pra from siar.siar_marcacoes,siar.siar_refeicao,siar.siar_prato Where siar.siar_marcacoes.Cod_Refeicao=siar.siar_refeicao.Cod_Refeicao and siar.siar_marcacoes.Cod_Prato=siar.siar_prato.Cod_Prato and siar.siar_marcacoes.Num_Mecanog='"+Login.txtUser.getText()+"' and trunc(siar.siar_marcacoes.Dta_Refeicao) >= trunc(sysdate) and siar.siar_marcacoes.dta_desativo is not null order by siar.siar_marcacoes.Dta_Refeicao");
			Marcacao p;
			while(rs_conn_mar.next())
			{
				p = new Marcacao(
						   rs_conn_mar.getInt("num_mec"),
						   rs_conn_mar.getString("dtaref"),
						   rs_conn_mar.getString("descref"),
						   rs_conn_mar.getString("descpra"),
						   rs_conn_mar.getString("dtades"),
						   rs_conn_mar.getString("dtareg"),
						   rs_conn_mar.getString("ver"),
						   rs_conn_mar.getInt("cod_ref"),
						   rs_conn_mar.getInt("cod_pra")
							);
						list.add(p);
			}
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Error getting desativated Appointments!"+e);
		}
		return list;
	}

}
