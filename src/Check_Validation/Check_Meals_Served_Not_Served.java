package Check_Validation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import siar.Gestor_Refeicoes;
import siar.JavaConection;

public class Check_Meals_Served_Not_Served {
	
	public static ResultSet rs_val_marc= null;
	public static PreparedStatement pstval_marc = null;
	public static Connection val_marc=null;
	
	public static void Re_Servidas()
	{  
		val_marc = JavaConection.ConnecrDb(); 
		if((Gestor_Refeicoes.nmec_aux.getText().length()==0) || (Gestor_Refeicoes.dta_ref_aux.getText().length()==0) || (Gestor_Refeicoes.cod_ref_aux.getText().length()==0))
        {
	     JOptionPane.showMessageDialog(null,"Nenhum Registo Selecionado!");
	     Gestor_Refeicoes.Check_ref.setSelected(false);
	    }	
	    else	
	    {	 
			if (Gestor_Refeicoes.Check_ref.isSelected())
				{
	            	String sql2="update siar.siar_marcacoes set siar.siar_marcacoes.verificacao='S' where siar.siar_marcacoes.Num_Mecanog='"+Gestor_Refeicoes.nmec_aux.getText()+"' and to_char(siar.siar_marcacoes.Dta_Refeicao,'dd-MM-yyyy')='"+Gestor_Refeicoes.dta_ref_aux.getText()+"' and siar.siar_marcacoes.Cod_Refeicao='"+Gestor_Refeicoes.cod_ref_aux.getText()+"'";
	            	try 
	            	{
						pstval_marc = val_marc.prepareStatement(sql2);
					} 
	            	catch (SQLException e1) 
	            	{
						e1.printStackTrace();
					}
	            	try 
	            	{
						pstval_marc.executeQuery();
					} 
	            	catch (SQLException e1) 
	            	{
						e1.printStackTrace();
					}
				    JOptionPane.showMessageDialog(null,"Refeição Servida!");
				}
			    Gestor_Refeicoes.marcacoes_diarias();
				Gestor_Refeicoes.Check_ref.setSelected(false);
	    }
	}
	public static void Ref_N_Servidas()
	{
		val_marc = JavaConection.ConnecrDb(); 
		if((Gestor_Refeicoes.nmec_aux.getText().length()==0) || (Gestor_Refeicoes.dta_ref_aux.getText().length()==0) || (Gestor_Refeicoes.cod_ref_aux.getText().length()==0))
	    {
	     JOptionPane.showMessageDialog(null,"Nenhum Registo Selecionado!");
	     Gestor_Refeicoes.Check_corr_ref.setSelected(false);
	    }	
	    else
	    {	
			if (Gestor_Refeicoes.Check_corr_ref.isSelected())
				{
	            	String sql2="update siar.siar_marcacoes set siar.siar_marcacoes.verificacao='N' where siar.siar_marcacoes.Num_Mecanog='"+Gestor_Refeicoes.nmec_aux.getText()+"' and to_char(siar.siar_marcacoes.Dta_Refeicao,'dd-MM-yyyy')='"+Gestor_Refeicoes.dta_ref_aux.getText()+"' and siar.siar_marcacoes.Cod_Refeicao='"+Gestor_Refeicoes.cod_ref_aux.getText()+"'";
	            	try 
	            	{
						pstval_marc = val_marc.prepareStatement(sql2);
					}
	            	catch (SQLException e1) 
	            	{
						e1.printStackTrace();
					}
	            	try
	            	{
						pstval_marc.executeQuery();
					}
	            	catch (SQLException e1)
	            	{
						e1.printStackTrace();
					}
				    JOptionPane.showMessageDialog(null,"Refeição não Servida!");
				}
			    parametros.marcacoes.marcacoes_diarias_checadas();
	 			Gestor_Refeicoes.Check_corr_ref.setSelected(false);
	        }
	}
	

}
