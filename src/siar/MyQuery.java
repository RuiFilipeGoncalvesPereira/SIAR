package siar;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class MyQuery {
	
	Statement pst_arr = null;
	Connection conn_arr = null;
	ResultSet resarr = null;
	Statement pst_upd = null;
	Connection conn_upd = null;
	ResultSet resupd = null;
	Statement pst_feriado = null;
	Connection conn_feriado = null;
	ResultSet resferiado = null;
	Statement pst_parametro = null;
	Connection conn_parametro = null;
	ResultSet resparametro= null;

			public ArrayList<Product> BindTable()
			{
				conn_arr = JavaConection.ConnecrDb();
				ArrayList<Product> list = new ArrayList<Product>();
				try
				{
					pst_arr = conn_arr.createStatement();
					resarr = pst_arr.executeQuery("select siar.siar_utilizadores.Num_Mecanog Nmec,siar.siar_utilizadores.nome_utilizador nuti,siar.siar_utilizadores.senha pass, to_char(siar.siar_utilizadores.Dta_Desativo,'dd-mm-yyyy') dta_des,siar.siar_utilizadores.email em,siar.siar_utilizadores.imagem img From siar.siar_utilizadores order by siar.siar_utilizadores.num_mecanog");
					Product p;
					while(resarr.next())
					{
						p = new Product(
								   resarr.getInt("Nmec"),
								   resarr.getString("nuti"),
								   resarr.getString("pass"),
								   resarr.getString("dta_des"),
								   resarr.getString("em"),
								   resarr.getBytes("img")
								);
								list.add(p);
					}
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, "Erro MyQuery!"+e);
				}
				return list;
			}
			public ArrayList<Product> pesq_utilizador(String nome)
			{
				conn_upd = JavaConection.ConnecrDb();
				ArrayList<Product> list = new ArrayList<Product>();
				try
				{
					pst_upd = conn_upd.createStatement();
					resupd = pst_upd.executeQuery("select siar.siar_utilizadores.Num_Mecanog Nmec,siar.siar_utilizadores.nome_utilizador nuti,siar.siar_utilizadores.senha pass, Dta_Desativo dta_des,siar.siar_utilizadores.email em,siar.siar_utilizadores.imagem img from siar.siar_utilizadores where UPPER(siar.siar_utilizadores.nome_utilizador) LIKE '%"+nome.toUpperCase().replace("", "%")+"%' and siar.siar_utilizadores.num_mecanog is not null and siar.siar_utilizadores.dta_desativo is null");
					Product p;
					while(resupd.next())
					{
						p = new Product(
								   resupd.getInt("Nmec"),
								   resupd.getString("nuti"),
								   resupd.getString("pass"),
								   resupd.getString("dta_des"),
								   resupd.getString("em"),
								   resupd.getBytes("img")
								);
								list.add(p);
					}
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, "Erro MyQuery2!"+e);
				}
				return list;
			}
			public ArrayList<Feriado> Mostra_Feriados()
			{
				conn_feriado = JavaConection.ConnecrDb();
				ArrayList<Feriado> list = new ArrayList<Feriado>();
				try
				{
					pst_feriado = conn_feriado.createStatement();
					resferiado = pst_feriado.executeQuery("select siar.siar_feriado.cod_feriado codfe,siar.siar_feriado.dta_feriado dtafe,siar.siar_feriado.desc_feriado descfe from siar.siar_feriado order by cod_feriado");
					Feriado p;
					while(resferiado.next())
					{
						p = new Feriado(
								   resferiado.getString("codfe"),
								   resferiado.getString("dtafe"),
								   resferiado.getString("descfe")
									);
								list.add(p);
					}
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, "Erro MyQuery3!"+e);
				}
				return list;
			}
			public ArrayList<Parametro> Mostra_Parametros()
			{
				conn_parametro = JavaConection.ConnecrDb();
				ArrayList<Parametro> list = new ArrayList<Parametro>();
				try
				{
					pst_parametro = conn_parametro.createStatement();
					resparametro = pst_parametro.executeQuery("select siar.siar_parametros.cod_parametro codpa,siar.siar_parametros.valor_parametro valpa,siar.siar_parametros.descricao_parametro descpa from siar.siar_parametros order by cod_parametro");
					Parametro p;
					while(resparametro.next())
					{
						p = new Parametro(
								   resparametro.getInt("codpa"),
								   resparametro.getString("valpa"),
								   resparametro.getString("descpa")
									);
								list.add(p);
					}
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, "Erro na querie de Parametros!"+e);
				}
				return list;
			}
}
