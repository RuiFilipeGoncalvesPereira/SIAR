package siar;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Entities.Dominio;
import Entities.Ementas;
import Entities.Feriado;
import Entities.Parametro;
import Entities.Product;
import Entities.Refeicao;
import Entities.prato;

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
	Statement pst_prato = null;
	Connection conn_prato = null;
	ResultSet resprato= null;
	Statement pst_refeicao = null;
	Connection conn_refeicao = null;
	ResultSet resrefeicao= null;
	Statement pst_dominio = null;
	Connection conn_dominio = null;
	ResultSet resdominio = null;
	Statement pst_ementas = null;
	Connection conn_ementas = null;
	ResultSet resementas= null;
    SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
    Date now = new Date(System.currentTimeMillis());
    String strDate = df2.format(now);

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
				//.replace("", "%")
				try
				{
					pst_upd = conn_upd.createStatement();
					//resupd = pst_upd.executeQuery("select siar.siar_utilizadores.Num_Mecanog Nmec,siar.siar_utilizadores.nome_utilizador nuti,siar.siar_utilizadores.senha pass, Dta_Desativo dta_des,siar.siar_utilizadores.email em,siar.siar_utilizadores.imagem img from siar.siar_utilizadores where UPPER(siar.siar_utilizadores.nome_utilizador) LIKE '%"+nome.toUpperCase()+"%' and siar.siar_utilizadores.num_mecanog is not null"
							//+ "");
					resupd = pst_upd.executeQuery("select siar.siar_utilizadores.Num_Mecanog Nmec,siar.siar_utilizadores.nome_utilizador nuti,siar.siar_utilizadores.senha pass, Dta_Desativo dta_des,siar.siar_utilizadores.email em,siar.siar_utilizadores.imagem img from siar.siar_utilizadores where UPPER(siar.siar_utilizadores.nome_utilizador) LIKE '"+nome.toUpperCase()+"' and siar.siar_utilizadores.num_mecanog is not null"
					+ "");
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
			public ArrayList<prato> Mostra_Pratos()
			{
				conn_prato = JavaConection.ConnecrDb();
				ArrayList<prato> list = new ArrayList<prato>();
				try
				{
					pst_prato = conn_prato.createStatement();
					resprato = pst_prato.executeQuery("select siar_prato.cod_prato as codpra,siar_prato.desc_prato as descpra,to_char(siar_prato.Dta_registo,'dd-mm-yyyy') as dtapra from siar.siar_prato");
					prato p;
					while(resprato.next())
					{
						p = new prato(
								   resprato.getInt("codpra"),
								   resprato.getString("descpra"),
								   resprato.getString("dtapra")
									);
								list.add(p);
					}
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, "Erro na querie de Pratos!"+e);
				}
				return list;
			}
			public ArrayList<Refeicao> Mostra_Refeicoes()
			{
				conn_refeicao = JavaConection.ConnecrDb();
				ArrayList<Refeicao> list = new ArrayList<Refeicao>();
				try
				{
					pst_refeicao = conn_refeicao.createStatement();
					resrefeicao = pst_refeicao.executeQuery("select siar.siar_refeicao.cod_refeicao codref,siar.siar_refeicao.desc_refeicao desre,to_char(siar.siar_refeicao.dta_registo,'dd-MM-YYYY hh24:mi:ss') dtare from siar.siar_refeicao order by cod_refeicao");
					Refeicao p;
					while(resrefeicao.next())
					{
						p = new Refeicao(
							       resrefeicao.getInt("codref"),
							       resrefeicao.getString("desre"),
							       resrefeicao.getString("dtare")
									);
								list.add(p);
					}
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, "Erro na querie de obtenção de Refeições!"+e);
				}
				return list;
			}
			public ArrayList<Dominio> Mostra_Dominio()
			{
				conn_dominio = JavaConection.ConnecrDb();
				ArrayList<Dominio> list = new ArrayList<Dominio>();
				try
				{
					pst_dominio = conn_dominio.createStatement();
					resdominio = pst_dominio.executeQuery("select siar.siar_dominios.valor val,siar.siar_dominios.dominio dom,siar.siar_dominios.desc_dominio desc_ FROM siar.siar_dominios order by Valor");
					Dominio d;
					while(resdominio.next())
					{
						d = new Dominio(
								   resdominio.getInt("val"),
								   resdominio.getString("dom"),
								   resdominio.getString("desc_")
									);
								list.add(d);
					}
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, "Erro na querie de Dominio!"+e);
				}
				return list;
			}
			public ArrayList<Ementas> Mostra_Ementas()
			{
				conn_ementas = JavaConection.ConnecrDb();
				ArrayList<Ementas> list = new ArrayList<Ementas>();
				try
				{
					pst_ementas = conn_ementas.createStatement();
					resementas = pst_ementas.executeQuery("select to_char(siar.siar_ementas.dta_refeicao,'dd/MM/yyyy')"
							+ " dta_ref,siar.siar_ementas.cod_refeicao c_ref,siar.siar_ementas.cod_prato c_pr,siar.siar_refeicao.desc_refeicao cdr,siar.siar_prato.desc_prato cdp"
							+ " FROM siar.siar_ementas,siar.siar_prato,siar.siar_refeicao"
							+ " where siar.siar_ementas.cod_prato = siar.siar_prato.cod_prato and siar.siar_ementas.cod_refeicao = siar.siar_refeicao.cod_refeicao and siar.siar_ementas.dta_refeicao > sysdate "
							+ " order by dta_ref,cdr asc");
					Ementas d;
					while(resementas.next())
					{
						d = new Ementas(
							       resementas.getString("dta_ref"),
							       resementas.getString("cdr"),
							       resementas.getString("cdp"),
							       resementas.getInt("c_ref"),
							       resementas.getInt("c_pr")
									);
								list.add(d);
					}
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, "Erro na querie de Ementas!"+e);
				}
				return list;
			}
}
