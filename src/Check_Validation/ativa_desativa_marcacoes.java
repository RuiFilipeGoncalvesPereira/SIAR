package Check_Validation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dados_auxiliares.GetName;
import siar.Administrador;
import siar.Gestor_Cantina;
import siar.Gestor_Refeicoes;
import siar.JavaConection;


public class ativa_desativa_marcacoes {
	
	static Connection conn_utilizador = null;
	static ResultSet rs_admin = null;
	static ResultSet rs_gecan = null;
	static ResultSet rs_gemen = null;
	static PreparedStatement pst = null; 

	public static void ativa_desativa_marcacoes_(int num_mec) {
		conn_utilizador = JavaConection.ConnecrDb();
		String sql_admin="select * from siar.siar_dominios b"
           		+ " Where b.dominio='admin' and valor='"+num_mec+"'";
           try {
			conn_utilizador.prepareStatement(sql_admin);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
           try {
			pst=conn_utilizador.prepareStatement(sql_admin,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
           try {
			rs_admin=pst.executeQuery();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
           
           String sql_gecan="select * from siar.siar_dominios b"
               		+ " Where b.dominio='gecan' and valor='"+num_mec+"'";
           try {
			conn_utilizador.prepareStatement(sql_gecan);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
           try {
			pst=conn_utilizador.prepareStatement(sql_gecan,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
           try {
			rs_gecan=pst.executeQuery();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
           String sql_gemen="select * from siar.siar_dominios b"
              		+ " Where b.dominio='gemen' and valor='"+num_mec+"'";
          try {
			conn_utilizador.prepareStatement(sql_gemen);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
          try {
			pst=conn_utilizador.prepareStatement(sql_gemen,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
          try {
        	  rs_gemen=pst.executeQuery();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}   
           
    	   try {
			if(rs_admin.first())
			   {
			         Administrador Admin = new Administrador();
			         Admin.setVisible(true);
			         Administrador.lblnum.setText(GetName.GETNAME(num_mec));
			   } 
			else if(rs_gemen.first())
				   {
						     Gestor_Cantina Ges = new Gestor_Cantina();
					         Ges.setVisible(true);
					         Gestor_Cantina.lblnum.setText(GetName.GETNAME(num_mec));
				    } 
			else
				try {
					if(rs_gecan.first())
					  {
					         Gestor_Refeicoes Ges = new Gestor_Refeicoes();
					         Ges.setVisible(true);
					         Gestor_Refeicoes.lblnum.setText(GetName.GETNAME(num_mec));
					   }
				    } 
			    catch (SQLException e1)
			    {
					e1.printStackTrace();
				}
		    } 
    	   catch (SQLException e1)
    	   {
			e1.printStackTrace();
		   }
		
	}
	
}
