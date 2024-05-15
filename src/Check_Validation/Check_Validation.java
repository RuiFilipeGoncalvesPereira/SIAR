package Check_Validation;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import javax.swing.JOptionPane;

import siar.Administrador;
import siar.Gestor_Cantina;
import siar.Gestor_Refeicoes;
import siar.JavaConection;
import siar.Login;
import siar.Marcacoes;

public class Check_Validation {
	
	static int conta=0;  
	static Connection conn_utilizador = null;
    public static String numero;
    static PreparedStatement pst = null;
    static PreparedStatement pst_des = null;
    static ResultSet rs = null;
    static ResultSet rs_des = null;
    static ResultSet rs_admin = null;
    static ResultSet rs_gecan = null;
    static ResultSet rs_geementa = null;
    static Connection conn_utilizador_des = null;
    static Date now = new Date(System.currentTimeMillis());
    
	@SuppressWarnings("deprecation")
	public static void Valida_Login()
	{ 
		    if(siar.Login.txtUser.getText().length()==0)
		    {
		    	 JOptionPane.showMessageDialog(null, "O Campo do Número Mecanográfico não pode ser nulo!");
	             Login.frame.setVisible(true);
	             siar.Login.txtUser.requestFocus();
	        }
		    else if(siar.Login.passField.getText().length()==0)
			{
	             JOptionPane.showMessageDialog(null, "O Campo de Password não pode ser nulo!");
	             Login.frame.setVisible(true);
	             siar.Login.passField.requestFocus();
	        }
			else
			{
			  Login.frame.setVisible(false);
			  conn_utilizador = JavaConection.ConnecrDb();	  
			  conn_utilizador_des = JavaConection.ConnecrDb();	  
			  if(conta< 3)
				         {    
				            try
				            {
				               String sql="select * from siar.siar_utilizadores Where Num_Mecanog='"+siar.Login.txtUser.getText()+"' and Senha='"+siar.Login.passField.getText()+"'";
				               conn_utilizador.prepareStatement(sql);
				               pst=conn_utilizador.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				               rs=pst.executeQuery();
				               
				               String sql_des="select * from siar.siar_utilizadores Where Num_Mecanog='"+siar.Login.txtUser.getText()+"' and Senha='"+siar.Login.passField.getText()+"'"
				               		+ "and dta_desativo is not null";
				               conn_utilizador_des.prepareStatement(sql_des);
				               pst_des=conn_utilizador_des.prepareStatement(sql_des,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				               rs_des=pst_des.executeQuery();
				               
				               String sql_admin="select * from siar.siar_dominios b"
				               		+ " Where b.dominio='admin' and valor='"+siar.Login.txtUser.getText()+"'";
				               conn_utilizador.prepareStatement(sql_admin);
				               pst=conn_utilizador.prepareStatement(sql_admin,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				               rs_admin=pst.executeQuery();
				               
				               String sql_gecan="select * from siar.siar_dominios b"
					               		+ " Where b.dominio='gecan' and valor='"+siar.Login.txtUser.getText()+"'";
				               conn_utilizador.prepareStatement(sql_gecan);
				               pst=conn_utilizador.prepareStatement(sql_gecan,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				               rs_gecan=pst.executeQuery();
				               
				               String sql_gementa="select * from siar.siar_dominios b"
					               		+ " Where b.dominio='gemen' and valor='"+siar.Login.txtUser.getText()+"'";
				               conn_utilizador.prepareStatement(sql_gementa);
				               pst=conn_utilizador.prepareStatement(sql_gementa,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				               rs_geementa=pst.executeQuery();
				               
				               numero = siar.Login.txtUser.getText(); 
				               
				               if(rs_des.first())
				               {
				            	   JOptionPane.showMessageDialog(null, "Your acess is bloked, contact the administrator!"); 
				               }
				               	else
				               {
				            	      if(rs.first())
						               {
						            	   if(rs_admin.first())
						            	   {
						            		     JOptionPane.showMessageDialog(null, "Bem vindo senhor/a " + prodQty(Integer.parseInt(siar.Login.txtUser.getText())));
						                         Administrador Admin = new Administrador();
						                         Admin.setVisible(true);
						                         Administrador.lblnum.setText(numero);
						            	   }
						            	   else if(rs_gecan.first()){
								            	 JOptionPane.showMessageDialog(null, "Bem vindo senhor/a " + prodQty(Integer.parseInt(siar.Login.txtUser.getText())));
						                         Gestor_Refeicoes Ges = new Gestor_Refeicoes();
						                         Ges.setVisible(true);
						                         Gestor_Refeicoes.lblnum.setText(numero);
						            	   }
						            	   else if(rs_geementa.first()){
								            	 JOptionPane.showMessageDialog(null, "Bem vindo senhor/a " + prodQty(Integer.parseInt(siar.Login.txtUser.getText())));
								                 Gestor_Cantina Ges_em = new Gestor_Cantina();
								                 Ges_em.setVisible(true);
								                 Gestor_Cantina.lblnum.setText(numero);
						            	   }
						            	   else
						            	   {
								                 JOptionPane.showMessageDialog(null, "Bem vindo senhor/a " + prodQty(Integer.parseInt(siar.Login.txtUser.getText())));
						                         Marcacoes Marc = new Marcacoes();
						                         Marc.setVisible(true);
						                         Marcacoes.lblNum.setText(numero);
						                         Marcacoes.btn_back_menu.setVisible(false);
						            	   }
						               }
						               else
						               {
						                   conta++; 
						                   if(conta==2)
						                   {    
						                     JOptionPane.showMessageDialog(null, "Validação Incorecta!,Têm mais "+conta+" Oportunidade/s!");
						                   }
						                   else if(conta==1)
						                   {  
						                	   JOptionPane.showMessageDialog(null, "Validação Incorecta!,Têm mais "+conta+" Oportunidade/s!");
						                   }
						                   else
						                   {
						                     JOptionPane.showMessageDialog(null, "Falhou outra Vez!");
						                     System.exit(0);
						                   }
						               }
				               } 
					    }   
				            catch(Exception e)
				            {
				                JOptionPane.showMessageDialog(null,e);
				            }
				      }
			  }
	     }  
	public static String prodQty(int num_mec)
	 {
	     String name = null;
		  try
		  {
	      String sql="select nome_utilizador from siar.siar_utilizadores where Num_Mecanog='"+num_mec+"'";
	      conn_utilizador.prepareStatement(sql);
         pst=conn_utilizador.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
         rs=pst.executeQuery();
	      while(rs.next())
	        {
	    	  name=rs.getString(1);
	        }
	      }
	   catch (SQLException ex)
		 {
		   JOptionPane.showMessageDialog(null, ex);
	     }
	  return name;
     }
	public static void Calcular_Dia_Correto()
	{
		Gestor_Cantina.dt_ementa.setDate(now);
		Calendar cal = Calendar.getInstance();
		cal.setTime(Gestor_Cantina.dt_ementa.getDate());
		cal.add(Calendar.DAY_OF_MONTH, 1);
		java.util.Date futureDate2 = cal.getTime();	
		Gestor_Cantina.dt_ementa.setDate(futureDate2);
	}
}