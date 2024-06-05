package Query;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Check_Validation.Check_Holiday;
import Data.Data_Read_Values;
import Entities.Marcacao;
import Entities.Marcacao_Checada;
import siar.JavaConection;
import siar.Login;

public class MyQuery_Marc {
	
	Connection conn_mar= null;
	ResultSet rs_conn_mar = null;
	Statement pstconn_mar = null;
	static Connection conn_verifica= null;
	static ResultSet rs_conn_verifica= null;
	static Statement pstconn_verifica = null;
	static Check_Holiday CH = new Check_Holiday();
	static Data_Read_Values mostra_data;
	
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
			rs_conn_mar = pstconn_mar.executeQuery("select siar.siar_marcacoes.Num_Mecanog num_mec,to_char(siar.siar_marcacoes.Dta_Refeicao,'dd-mm-yyyy') dtaref,siar.siar_refeicao.Desc_Refeicao descref,siar.siar_prato.Desc_Prato descpra,to_char(siar.siar_marcacoes.Dta_Desativo,'dd-mm-yyyy') dtades,to_char(siar.siar_marcacoes.Dta_Registo,'dd-mm-yyyy') as dtar_,siar.siar_marcacoes.verificacao ver,siar.siar_marcacoes.Cod_Refeicao cod_ref,siar.siar_marcacoes.Cod_prato cod_pra from siar.siar_marcacoes,siar.siar_refeicao,siar.siar_prato Where siar.siar_marcacoes.Cod_Refeicao=siar.siar_refeicao.Cod_Refeicao and siar.siar_marcacoes.Cod_Prato=siar.siar_prato.Cod_Prato and siar.siar_marcacoes.Num_Mecanog='"+Login.txtUser.getText()+"' and trunc(siar.siar_marcacoes.Dta_Refeicao) < trunc(sysdate) order by siar.siar_marcacoes.Dta_Refeicao desc");
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
			rs_conn_mar = pstconn_mar.executeQuery("select siar.siar_marcacoes.Num_Mecanog num_mec,to_char(siar.siar_marcacoes.Dta_Refeicao,'dd-mm-yyyy') dtaref,siar.siar_refeicao.Desc_Refeicao descref,siar.siar_prato.Desc_Prato descpra,to_char(siar.siar_marcacoes.Dta_Desativo,'dd-mm-yyyy') dtades,to_char(siar.siar_marcacoes.Dta_Registo,'dd-mm-yyyy') dtareg,siar.siar_marcacoes.verificacao ver,siar.siar_marcacoes.Cod_Refeicao cod_ref,siar.siar_marcacoes.Cod_prato cod_pra from siar.siar_marcacoes,siar.siar_refeicao,siar.siar_prato Where siar.siar_marcacoes.Cod_Refeicao=siar.siar_refeicao.Cod_Refeicao and siar.siar_marcacoes.Cod_Prato=siar.siar_prato.Cod_Prato and siar.siar_marcacoes.Num_Mecanog='"+Login.txtUser.getText()+"' and trunc(siar.siar_marcacoes.Dta_Refeicao) >= trunc(sysdate) and siar.siar_marcacoes.dta_desativo is not null order by siar.siar_marcacoes.Dta_Refeicao");
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
	public ArrayList<Marcacao_Checada> marcacoes_diarias_checadas()
	{
		conn_mar = JavaConection.ConnecrDb();
		ArrayList<Marcacao_Checada> list = new ArrayList<Marcacao_Checada>();
		try
		{
			pstconn_mar = conn_mar.createStatement();
			rs_conn_mar = pstconn_mar.executeQuery("select siar.siar_marcacoes.Num_Mecanog num_mec,siar.siar_utilizadores.nome_utilizador nome ,to_char(siar.siar_marcacoes.Dta_Refeicao,'dd-mm-yyyy')dtaref,siar.siar_refeicao.Desc_Refeicao desc_ref,siar.siar_prato.Desc_Prato desc_pra,to_char(siar.siar_marcacoes.Dta_Registo,'dd-mm-yyyy')dtareg,siar.siar_marcacoes.Cod_Refeicao cod_ref,siar.siar_marcacoes.Cod_prato cod_pra from siar.siar_marcacoes,siar.siar_refeicao,siar.siar_prato,siar.siar_utilizadores Where siar.siar_marcacoes.Cod_Refeicao=siar.siar_refeicao.Cod_Refeicao and siar.siar_marcacoes.Cod_Prato=siar.siar_prato.Cod_Prato and siar.siar_marcacoes.Num_Mecanog=siar.siar_utilizadores.Num_Mecanog and trunc(siar.siar_marcacoes.Dta_Refeicao) = trunc(sysdate) and siar.siar_marcacoes.verificacao = 'S' order by siar.siar_marcacoes.Dta_Registo desc");
			Marcacao_Checada p;
			while(rs_conn_mar.next())
			{
				p = new Marcacao_Checada(
						   rs_conn_mar.getInt("num_mec"),
						   rs_conn_mar.getString("nome"),
						   rs_conn_mar.getString("dtaref"),
						   rs_conn_mar.getString("desc_ref"),
						   rs_conn_mar.getString("desc_pra"),
						   rs_conn_mar.getString("dtareg"),
						   rs_conn_mar.getInt("cod_ref"),
						   rs_conn_mar.getInt("cod_pra")
							);
				list.add(p);	
			}
			
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Error getting checked Appointments!"+e);
		}
		return list;
	}
	public ArrayList<Marcacao_Checada> shecules_from_day()
	{
		conn_verifica = JavaConection.ConnecrDb();
		ArrayList<Marcacao_Checada> list = new ArrayList<Marcacao_Checada>();
		mostra_data = new Data_Read_Values();
        mostra_data.le_data();  
        mostra_data.le_hora();
		String horamin = CH.check_holiday(122);
		String horamax = CH.check_holiday(123);
		String horaminja = CH.check_holiday(124);
		String horamaxja = CH.check_holiday(125);
		try
		{
			pstconn_verifica = conn_verifica.createStatement();
			if ((mostra_data.horamin.compareTo(horamin)>=0) && (mostra_data.horamin.compareTo(horamax)<0))//&& (dta_ref_aux.getText().equals(strDate)))
			{
			rs_conn_verifica = pstconn_verifica.executeQuery("select siar.siar_marcacoes.Num_Mecanog num_mec,siar.siar_utilizadores.nome_utilizador nome,to_char(siar.siar_marcacoes.Dta_Refeicao,'dd-mm-yyyy')dtaref,siar.siar_refeicao.Desc_Refeicao desc_ref,siar.siar_prato.Desc_Prato desc_pra,to_char(siar.siar_marcacoes.Dta_Registo,'dd-mm-yyyy')dtareg,siar.siar_marcacoes.Cod_Refeicao cod_ref,siar.siar_marcacoes.Cod_prato cod_pra from siar.siar_marcacoes,siar.siar_refeicao,siar.siar_prato,siar.siar_utilizadores Where siar.siar_marcacoes.Cod_Refeicao=siar.siar_refeicao.Cod_Refeicao and siar.siar_marcacoes.Cod_Prato=siar.siar_prato.Cod_Prato and siar.siar_marcacoes.Num_Mecanog=siar.siar_utilizadores.Num_Mecanog and trunc(siar.siar_marcacoes.Dta_Refeicao) = trunc(sysdate) and siar.siar_marcacoes.verificacao = 'N' and siar.siar_marcacoes.Cod_Refeicao = 1 order by siar.siar_marcacoes.Dta_Registo desc");
	        }
			else if((mostra_data.horamin.compareTo(horaminja)>=0) && (mostra_data.horamin.compareTo(horamaxja)<0))
			{
			rs_conn_verifica = pstconn_verifica.executeQuery("select siar.siar_marcacoes.Num_Mecanog num_mec,siar.siar_utilizadores.nome_utilizador nome,to_char(siar.siar_marcacoes.Dta_Refeicao,'dd-mm-yyyy')dtaref,siar.siar_refeicao.Desc_Refeicao desc_ref,siar.siar_prato.Desc_Prato desc_pra,to_char(siar.siar_marcacoes.Dta_Registo,'dd-mm-yyyy')dtareg,siar.siar_marcacoes.Cod_Refeicao cod_ref,siar.siar_marcacoes.Cod_prato cod_pra from siar.siar_marcacoes,siar.siar_refeicao,siar.siar_prato,siar.siar_utilizadores Where siar.siar_marcacoes.Cod_Refeicao=siar.siar_refeicao.Cod_Refeicao and siar.siar_marcacoes.Cod_Prato=siar.siar_prato.Cod_Prato and siar.siar_marcacoes.Num_Mecanog=siar.siar_utilizadores.Num_Mecanog and trunc(siar.siar_marcacoes.Dta_Refeicao) = trunc(sysdate) and siar.siar_marcacoes.verificacao = 'N' and siar.siar_marcacoes.Cod_Refeicao = 2 order by siar.siar_marcacoes.Dta_Registo desc"); 
			}
			else 
			{
			rs_conn_verifica = pstconn_verifica.executeQuery("select siar.siar_marcacoes.Num_Mecanog num_mec,siar.siar_utilizadores.nome_utilizador nome,to_char(siar.siar_marcacoes.Dta_Refeicao,'dd-mm-yyyy')dtaref,siar.siar_refeicao.Desc_Refeicao desc_ref,siar.siar_prato.Desc_Prato desc_pra,to_char(siar.siar_marcacoes.Dta_Registo,'dd-mm-yyyy')dtareg,siar.siar_marcacoes.Cod_Refeicao cod_ref,siar.siar_marcacoes.Cod_prato cod_pra from siar.siar_marcacoes,siar.siar_refeicao,siar.siar_prato,siar.siar_utilizadores Where siar.siar_marcacoes.Cod_Refeicao=siar.siar_refeicao.Cod_Refeicao and siar.siar_marcacoes.Cod_Prato=siar.siar_prato.Cod_Prato and siar.siar_marcacoes.Num_Mecanog=siar.siar_utilizadores.Num_Mecanog and trunc(siar.siar_marcacoes.Dta_Refeicao) = trunc(sysdate) and siar.siar_marcacoes.verificacao = 'N' order by siar.siar_marcacoes.Dta_Registo desc"); 	
			}
			Marcacao_Checada p;
			while(rs_conn_verifica.next())
			{
				p = new Marcacao_Checada(
						   rs_conn_verifica.getInt("num_mec"),
						   rs_conn_verifica.getString("nome"),
						   rs_conn_verifica.getString("dtaref"),
						   rs_conn_verifica.getString("desc_ref"),
						   rs_conn_verifica.getString("desc_pra"),
						   rs_conn_verifica.getString("dtareg"),
						   rs_conn_verifica.getInt("cod_ref"),
						   rs_conn_verifica.getInt("cod_pra")
							);
						list.add(p);
			}
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Error check daily Appointments!"+e);
		}
		return list;
	}
}
