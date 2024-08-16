package Check_Validation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import Data.Data_Read_Values;
import siar.Gestor_Refeicoes;
import siar.JavaConection;
import siar.Marcacoes;

public class Check_Meals_Served_Not_Served {
	
	public static ResultSet rs_val_marc= null;
	public static PreparedStatement pstval_marc = null;
	public static Connection val_marc=null;
	static Data_Read_Values mostra_data;

	static Check_Holiday CH = new Check_Holiday();
	static String openhour = CH.check_holiday(122);
	static String closehour = CH.check_holiday(123);
	static String openhour_lunch = CH.check_holiday(124);
	static String closehour_lunch = CH.check_holiday(125);
	
	public static void Re_Servidas()
	{  
		val_marc = JavaConection.ConnecrDb(); 
	    mostra_data = new Data_Read_Values();
	    mostra_data.le_data();  
	    mostra_data.le_hora();
		if((Gestor_Refeicoes.nmec_aux.getText().length()==0) || (Gestor_Refeicoes.dta_ref_aux.getText().length()==0) || (Gestor_Refeicoes.cod_ref_aux.getText().length()==0))
        {
		 JOptionPane.showMessageDialog(null,"No regist was selected!");
	     Gestor_Refeicoes.Check_ref.setSelected(false);
	    }	
	    else	
	    {	 
			if((mostra_data.horamin.compareTo(openhour)<=0) || (mostra_data.horamin.compareTo(closehour)>=0)
				||(mostra_data.horamin.compareTo(openhour_lunch)<=0) || (mostra_data.horamin.compareTo(closehour_lunch)>=0))
	        {
			 JOptionPane.showMessageDialog(null, "You can only check meals beetween "+openhour+" and "+closehour+" for dinners and"
			 		+ "\n beetween "+openhour_lunch+" and "+closehour_lunch+" for lunchs!");
			 Gestor_Refeicoes.Check_ref.setSelected(false);
			 return;
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
							JOptionPane.showMessageDialog(null,e1);
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
				    parametros.marcacoes.daily_meals();
					Gestor_Refeicoes.Check_ref.setSelected(false);
			}
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
