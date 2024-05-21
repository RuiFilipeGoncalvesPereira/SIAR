package Check_Validation;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import siar.Check_Holiday;
import siar.Data;
import siar.Marcacoes;

public class Check_Schedules_Meals_Rules {
	
    static Data mostra_data;
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
	        mostra_data = new Data();
            mostra_data.le_data();  
            mostra_data.le_hora();
        	Check_Holiday CH = new Check_Holiday();
		    String strcalendar = null;
			String strcalum = null;
			String strcaldois = null;
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
			if (Marcacoes.calum != null) {
				strcalum = sdfcalendar.format(Marcacoes.calum.getTime());
				}
			if (Marcacoes.caldois != null) {
				strcaldois = sdfcalendar.format(Marcacoes.caldois.getTime());
				}
			
	         if(((JTextField)Marcacoes.dt_ref.getDateEditor().getUiComponent()).getText().isEmpty())
	         {
	             JOptionPane.showMessageDialog(null, "Deve Inserir a Data da Refeição!");
	             ((JTextField)Marcacoes.dt_ref.getDateEditor().getUiComponent()).requestFocus();
	             return;
	         }
	         
			if ((strDate.compareTo(Imaculada)==0) && (dataobtida.compareTo(Imaculada)==0))
			{
				 JOptionPane.showMessageDialog(null, "Dia da Imaculada Conçeição não Pode Marcar Refeições para Amanhâ");
				 Marcacoes.dt_ref.requestFocus();
				 return;
			}
			if ((strDate.compareTo(Natal)==0) && (dataobtida.compareTo(Natal)==0))
			{
				 JOptionPane.showMessageDialog(null, "Dia de Natal não Pode Marcar Refeições para Amanhâ");
				 Marcacoes.dt_ref.requestFocus();
				 return;
			}
			if ((strDate.compareTo(AssunSenhora)==0) && (dataobtida.compareTo(AssunSenhora)==0))
			{
				 JOptionPane.showMessageDialog(null, "Dia de nossa Senhora da Assunção não Pode Marcar Refeições para Amanhã");
				 Marcacoes.dt_ref.requestFocus();
				 return;
			}
			if ((strDate.compareTo(DPortugal)==0) && (dataobtida.compareTo(DPortugal)==0))
			{
				 JOptionPane.showMessageDialog(null, "Dia de Portugal não Pode Marcar Refeições para Amanhâ");
				 Marcacoes.dt_ref.requestFocus();
				 return;
			}
			if ((strDate.compareTo(DTrabalhador)==0) && (dataobtida.compareTo(DTrabalhador)==0))
			{
				 JOptionPane.showMessageDialog(null, "Dia do trabalhador não Pode Marcar Refeições para Amanhâ");
				 Marcacoes.dt_ref.requestFocus();
				 return;
			}
			if ((strDate.compareTo(Fmunicipal)==0) && (dataobtida.compareTo(Fmunicipal)==0))
			{
				 JOptionPane.showMessageDialog(null, "Feriado Municipal Não Pode Marcar refeições para Amanhâ");
				 Marcacoes.dt_ref.requestFocus();
				 return;
			}
			if ((strDate.compareTo(Dliberdade)==0) && (dataobtida.compareTo(Dliberdade)==0))
			{
				 JOptionPane.showMessageDialog(null, "Dia da liberdade Não Pode Marcar refeições para Amanhâ");
				 Marcacoes.dt_ref.requestFocus();
				 return;
			}
			if ((strDate.compareTo(Pascoa)==0) && (dataobtida.compareTo(Pascoa)==0))
			{
				 JOptionPane.showMessageDialog(null, "Páscoa Não Pode Marcar refeições para Amanhâ");
				 Marcacoes.dt_ref.requestFocus();
				 return;
			}
			if ((strDate.compareTo(SextaSanta)==0) && (dataobtida.compareTo(SextaSanta)==0))
			{
				 JOptionPane.showMessageDialog(null, "Sexta Feira Santa Não Pode Marcar refeições para Amanhâ");
				 Marcacoes.dt_ref.requestFocus();
				 return;
			}
			if ((strDate.compareTo(AnoNovo)==0) && (dataobtida.compareTo(AnoNovo)==0))
			{
				 JOptionPane.showMessageDialog(null, "Ano Novo Não Pode Marcar refeições para Amanhâ");
				 Marcacoes.dt_ref.requestFocus();
				 return;
			}
			if ((val == 6) && (dataobtida.compareTo(strcalendar)==0) && (mostra_data.horamin.compareTo(horalimite)>=0))
			{
				 JOptionPane.showMessageDialog(null, "Sexta,Já passa das "+horalimite+" !,Não pode marcar refeições para Sábado!");
				 Marcacoes.dt_ref.requestFocus();
				 return;
			}
			if ((val == 6) && (dataobtida.compareTo(strcalum)==0) && (mostra_data.horamin.compareTo(horalimite)>=0))
			{
				 JOptionPane.showMessageDialog(null, "Sexta,Já passa das "+horalimite+" !,Não pode marcar refeições para Domingo!");
				 Marcacoes.dt_ref.requestFocus();
				 return;
			}
			if ((val == 6) && (dataobtida.compareTo(strcaldois)==0)&& (mostra_data.horamin.compareTo(horalimite)>=0))
			{
				 JOptionPane.showMessageDialog(null, "Sexta,Já passa das "+horalimite+" !,Não pode marcar refeições para Segunda!");
				 Marcacoes.dt_ref.requestFocus();
				 return;
			}
			if ((val == 7) && (dataobtida.compareTo(strcalendar)==0))
			{
				 JOptionPane.showMessageDialog(null, "Sábado!,Não pode marcar refeições para domingo!");
				 Marcacoes.dt_ref.requestFocus();
				 return;
			}
			if ((val == 7) && (dataobtida.compareTo(strcalum)==0))
			{
				 JOptionPane.showMessageDialog(null, "Sábado!,Não pode marcar refeições para Segunda!");
				 Marcacoes.dt_ref.requestFocus();
				 return;
			}
			if ((val == 1) && (dataobtida.compareTo(strcalendar)==0))
			{
				 JOptionPane.showMessageDialog(null, "Domingo!,Não pode marcar refeições para segunda!");
				 Marcacoes.dt_ref.requestFocus();
				 return;
			}
			
			if(dataobtida.compareTo(strcalendar)==0 && (mostra_data.horamin.compareTo(horalimite)>=0))
	        {
			 JOptionPane.showMessageDialog(null, "Já passa das "+horalimite+" Não pode marcar refeições para Amanhâ!");
			 Marcacoes.dt_ref.requestFocus();
			 return;
	        }
	        if(sqlStartDate.after(sqlDateTrinta))
	        {
			 JOptionPane.showMessageDialog(null, "Não pode marcar refeições para daqui a mais de 31 dias!");
			 Marcacoes.dt_ref.requestFocus();
			 return;
	        }
			if(dataobtida.equals(strDate))
	    	{
			 JOptionPane.showMessageDialog(null, "Erro!,Não pode marcar refeições para hoje!");
			 Marcacoes.dt_ref.requestFocus();
			 return;
			}
			else if(sqlStartDate.before(sqlDate)) 
			{
			 JOptionPane.showMessageDialog(null, "Erro!,Está a tentar marcar refeições para datas passadas!");
			 Marcacoes.dt_ref.requestFocus();
			 return;
			}
			parametros.marcacoes.efetua_Marcacoes();
    }

}
