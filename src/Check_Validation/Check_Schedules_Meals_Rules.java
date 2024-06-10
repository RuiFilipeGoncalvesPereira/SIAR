package Check_Validation;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Data.Data_Read_Values;
import siar.Marcacoes;

public class Check_Schedules_Meals_Rules {
	
    static Data_Read_Values mostra_data;
	static SimpleDateFormat df2 = new SimpleDateFormat("dd-MM-yyyy");
	public static Date now = new Date(System.currentTimeMillis());
	static Calendar caldia = Calendar.getInstance();
	static int val = caldia.get(Calendar.DAY_OF_WEEK);
	int hour = caldia.get(Calendar.HOUR_OF_DAY);
	int minute = caldia.get(Calendar.MINUTE);
	static java.util.Date data_hoje = new java.util.Date();
	static java.sql.Date sqlDate = new java.sql.Date(data_hoje.getTime());
	
	public static void ValidaDatas()
	{
	        mostra_data = new Data_Read_Values();
            mostra_data.le_data();  
            mostra_data.le_hora();
        	Check_Holiday CH = new Check_Holiday();
		    String strcalendar = null;
			String strcaldois = null;
			String strcaltres = null;
			String horalimite = CH.check_holiday(2);
			String Imaculada = CH.check_Feriado(1);
			String Natal = CH.check_Feriado(2);
			String AssunSenhora = CH.check_Feriado(3);
			String DPortugal = CH.check_Feriado(4);
			String DTrabalhador = CH.check_Feriado(5);
			String Fmunicipal = CH.check_Feriado(6);
			String Dliberdade = CH.check_Feriado(7);
			String Pascoa = CH.check_Feriado(8);
			String SextaSanta = CH.check_Feriado(9);
			String AnoNovo = CH.check_Feriado(10);
		    String strDate = df2.format(now);
			java.util.Date utilStartDate = Marcacoes.dt_ref.getDate();
			java.sql.Date sqlStartDate = new java.sql.Date(utilStartDate.getTime());
			java.sql.Date sqlDateTrinta =  new java.sql.Date(Marcacoes.calmaiortrinta.getTime().getTime());
	
			SimpleDateFormat sdfcalendar = new SimpleDateFormat("dd-MM-yyyy");
			String dataobtida = df2.format(Marcacoes.dt_ref.getDate());
			
			if (Marcacoes.cal != null) {
				strcalendar = sdfcalendar.format(Marcacoes.cal.getTime());
				}
			if (Marcacoes.caldois != null) {
				strcaldois = sdfcalendar.format(Marcacoes.caldois.getTime());
				}
			if (Marcacoes.caltres != null) {
				strcaltres = sdfcalendar.format(Marcacoes.caltres.getTime());
				}
	        if(((JTextField)Marcacoes.dt_ref.getDateEditor().getUiComponent()).getText().isEmpty())
	         {
	             JOptionPane.showMessageDialog(null, "You must insert meal date!");
	             ((JTextField)Marcacoes.dt_ref.getDateEditor().getUiComponent()).requestFocus();
	             return;
	         }
	         
			if ((strDate.compareTo(Imaculada)==0) && (dataobtida.compareTo(Imaculada)==0))
			{
				 JOptionPane.showMessageDialog(null, "Day of the Immaculate Conception, you can't schedule meals for tomarrow!");
				 Marcacoes.dt_ref.requestFocus();
				 return;
			}
			if ((strDate.compareTo(Natal)==0) && (dataobtida.compareTo(Natal)==0))
			{
				 JOptionPane.showMessageDialog(null, "Cristhmas day, you can't schedule meals for tomarrow!");
				 Marcacoes.dt_ref.requestFocus();
				 return;
			}
			if ((strDate.compareTo(AssunSenhora)==0) && (dataobtida.compareTo(AssunSenhora)==0))
			{
				 JOptionPane.showMessageDialog(null, "Our Lady of the Assumption day, you can't schedule meals for tomarrow!");
				 Marcacoes.dt_ref.requestFocus();
				 return;
			}
			if (((strcalendar.compareTo(DPortugal)==0) || (strDate.compareTo(DPortugal)==0)) && (dataobtida.compareTo(DPortugal)==0))
			{
				 JOptionPane.showMessageDialog(null, "Portugal Day, You can't schedule meals for tomarrow!");
				 Marcacoes.dt_ref.requestFocus();
				 return;
			}
			if ((strDate.compareTo(DTrabalhador)==0) && (dataobtida.compareTo(DTrabalhador)==0))
			{
				 JOptionPane.showMessageDialog(null, "Labor Day, you can't schedule meals for tomarrow");
				 Marcacoes.dt_ref.requestFocus();
				 return;
			}
			if ((strDate.compareTo(Fmunicipal)==0) && (dataobtida.compareTo(Fmunicipal)==0))
			{
				 JOptionPane.showMessageDialog(null, "Municipal holiday, you can't schedule meals for tomarrow");
				 Marcacoes.dt_ref.requestFocus();
				 return;
			}
			if ((strDate.compareTo(Dliberdade)==0) && (dataobtida.compareTo(Dliberdade)==0))
			{
				 JOptionPane.showMessageDialog(null, "Liberty day, you can't schedule meals for tomarrow!");
				 Marcacoes.dt_ref.requestFocus();
				 return;
			}
			if ((strDate.compareTo(Pascoa)==0) && (dataobtida.compareTo(Pascoa)==0))
			{
				 JOptionPane.showMessageDialog(null, "Easter, You can't schedule meals for tomarrow!");
				 Marcacoes.dt_ref.requestFocus();
				 return;
			}
			if ((strDate.compareTo(SextaSanta)==0) && (dataobtida.compareTo(SextaSanta)==0))
			{
				 JOptionPane.showMessageDialog(null, "Good friday, you can't schedule meals for tomarrow!");
				 Marcacoes.dt_ref.requestFocus();
				 return;
			}
			if ((strDate.compareTo(AnoNovo)==0) && (dataobtida.compareTo(AnoNovo)==0))
			{
				 JOptionPane.showMessageDialog(null, "New years day, you can't schedule meals for tomarrow!");
				 Marcacoes.dt_ref.requestFocus();
				 return;
			}
			if ((val == 6) && (dataobtida.compareTo(strcalendar)==0) && (mostra_data.horamin.compareTo(horalimite)>=0))
			{
				 JOptionPane.showMessageDialog(null, "Friday, already past from "+horalimite+" !,You can't schedule meals for Saturday!");
				 Marcacoes.dt_ref.requestFocus();
				 return;
			}
			if ((val == 6) && (dataobtida.compareTo(strcaldois)==0) && (mostra_data.horamin.compareTo(horalimite)>=0))
			{
				 JOptionPane.showMessageDialog(null, "Friday, already past from "+horalimite+" !,You can't schedule meals for Sunday!");
				 Marcacoes.dt_ref.requestFocus();
				 return;
			}
			if ((val == 6) && (dataobtida.compareTo(strcaltres)==0)&& (mostra_data.horamin.compareTo(horalimite)>=0))
			{
				 JOptionPane.showMessageDialog(null, "Friday, already past from "+horalimite+"!, You can't schedule meals for Monday!");
				 Marcacoes.dt_ref.requestFocus();
				 return;
			}
			if ((val == 7) && (dataobtida.compareTo(strcalendar)==0))
			{
				 JOptionPane.showMessageDialog(null, "Saturday!, You can't schecule meals for Sunday!");
				 Marcacoes.dt_ref.requestFocus();
				 return;
			}
			if ((val == 7) && (dataobtida.compareTo(strcaldois)==0))
			{
				 JOptionPane.showMessageDialog(null, "Saturday!, You can't schecule meals for Monday!");
				 Marcacoes.dt_ref.requestFocus();
				 return;
			}
			if ((val == 1) && (dataobtida.compareTo(strcalendar)==0))
			{
				 JOptionPane.showMessageDialog(null, "Sunday!, You can't schecule meals for Monday!");
				 Marcacoes.dt_ref.requestFocus();
				 return;
			}
			if(dataobtida.compareTo(strcalendar)==0 && (mostra_data.horamin.compareTo(horalimite)>=0))
	        {
			 JOptionPane.showMessageDialog(null, "It's already past "+horalimite+" you can´t schedule meals for tomarrow!");
			 Marcacoes.dt_ref.requestFocus();
			 return;
	        }
	        if(sqlStartDate.after(sqlDateTrinta))
	        {
			 JOptionPane.showMessageDialog(null, "You cannot schedule meals for more than 31 days from now!");
			 Marcacoes.dt_ref.requestFocus();
			 return;
	        }
			if(dataobtida.equals(strDate))
	    	{
			 JOptionPane.showMessageDialog(null, "Error!You cannot schedule meals for today!");
			 Marcacoes.dt_ref.requestFocus();
			 return;
			}
			else if(sqlStartDate.before(sqlDate)) 
			{
			 JOptionPane.showMessageDialog(null, "Error!You are trying schedule meals for past dates!");
			 Marcacoes.dt_ref.requestFocus();
			 return;
			}
			parametros.marcacoes.efetua_Marcacoes();
    }

}
