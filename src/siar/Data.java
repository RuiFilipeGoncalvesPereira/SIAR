package siar;
import java.util.Date;
import java.text.SimpleDateFormat;
public class Data {
		  String mes,dia,ano,dia_semana,hora,horamin;
		  SimpleDateFormat horaformatada = new SimpleDateFormat("HH:mm:ss");
		  SimpleDateFormat horaminform = new SimpleDateFormat("HH:mm");
		    
		    public void le_hora()
		    {
		       Date horaAtual = new Date();
		       hora = horaformatada.format(horaAtual);
		       horamin = horaminform.format(horaAtual);
		    }        
		    
		    @SuppressWarnings("deprecation")
			public void le_data()
		    {
		       Date data = new Date();
		       dia = ""+data.getDate();
		       ano = ""+(1900+data.getYear());
		       
		    switch(data.getDay())
		    {
		        case 0: dia_semana ="Domingo";break;
		        case 1: dia_semana ="Segunda-Feira";break;
		        case 2: dia_semana ="Ter�a-Feira";break;
		        case 3: dia_semana ="Quarta-Feira";break;
		        case 4: dia_semana ="Quinta-Feira";break;
		        case 5: dia_semana ="Sexta-Feira";break;
		        case 6: dia_semana ="S�bado";break;    
		    }    
		    switch(data.getMonth())
		    {
		        case 0: mes ="Janeiro";break;
		        case 1: mes ="Fevereiro";break;
		        case 2: mes ="Mar�o";break;
		        case 3: mes ="Abril";break;
		        case 4: mes ="Maio";break;
		        case 5: mes ="Junho";break;
		        case 6: mes ="Julho";break;
		        case 7: mes ="Agosto";break;
		        case 8: mes ="Setembro";break;
		        case 9: mes ="Outubro";break;
		        case 10: mes ="Novembro";break;
		        case 11: mes ="Dezembro";break;    
		    }
	    
	    }  

}
