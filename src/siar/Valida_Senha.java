package siar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

public class Valida_Senha
{
	public String check_senha(String pass, String nome) 
    {
		String str =pass;
		@SuppressWarnings("unused")
		int pequena = 0, digits = 0, blanks = 0,grande=0,c_espec=0;
		char ch; 
		String mess = null;
		for(int i = 0; i < str.length(); i ++)
		{
			ch = str.charAt(i);
			if
			(ch == 'a'|| ch == 'b' || ch == 'c' || 
			ch == 'd' || ch == 'e' || ch == 'f' || ch == 'g' || ch == 'h'|| 
			ch == 'i' || ch == 'j' || ch == 'l' || ch == 'm' || ch == 'n'|| 
			ch == 'o' || ch == 'p' || ch == 'q' || ch == 'r' || ch == 's'|| 
			ch == 't' || ch == 'u' || ch == 'v' || ch == 'x' || ch == 'z'||
			ch == 'w' || ch == 'y')
			pequena ++;
			else if
			(ch == 'A'|| ch == 'B' || ch == 'C' || ch == 'D' || ch == 'E'|| 
			ch == 'F' || ch == 'G' || ch == 'H' || ch == 'I' || ch == 'J'|| 
			ch == 'L' || ch == 'M' || ch == 'N' || ch == 'O' || ch == 'P'|| 
			ch == 'Q' || ch == 'R' || ch == 'S' || ch == 'T' || ch == 'U'|| 
			ch == 'V' || ch == 'X' || ch == 'Z' || ch == 'W' || ch == 'Y' )
			grande++;  
			else if
			(ch == '!'|| ch == '#' || ch == '@' || ch == '$' || ch == '%'|| 
			 ch == '&'|| ch == '?')
			c_espec++;
			else if(Character.isDigit(ch))
				digits ++;
			else if(Character.isWhitespace(ch))
				blanks ++;
		}
			if((pequena<4))
			{		
			JOptionPane.showMessageDialog(null, "Têm de inserir pelo menos 4 letras minúsculas!");
			mess="";
			/*return;*/
			}
			if((grande<2))
			{		
			JOptionPane.showMessageDialog(null, "Têm de inserir pelo menos duas letras maiúsculas!");
			mess="";
			}
			if((c_espec<2))
			{		
			JOptionPane.showMessageDialog(null, "A senha de deve conter pelo menos dois caracteres do tipo !#@$%&?");
			mess="";		
			}
			if((digits<2))
			{		
			JOptionPane.showMessageDialog(null, "Têm de inserir pelo dois digitos!");
			mess="";		
			}
			String Senha_Pattern = "^[a-zA-Z0-9]{1,9}$";
		    String Letras_Pattern = "[A-Za-z���������������]+(\\s[A-Za-z���������������]+)*";
			
		    Pattern pattern = Pattern.compile(Senha_Pattern);
			Pattern L_pattern = Pattern.compile(Letras_Pattern);
			
			Matcher regexMaster = pattern.matcher(pass);
			Matcher l_Master = L_pattern.matcher(nome);
				if((!regexMaster.matches())) //&& (l_Master.matches()))
				   {
				    JOptionPane.showMessageDialog(null, "Formato Incorrecto na Senha "+pass+", Caracter inválido!");
				    mess="";
				   }  
				else if ((!l_Master.matches())) //((regexMaster.matches()) && 
				   {
				    JOptionPane.showMessageDialog(null, "O campo nome só aceita letras!");
				    mess="";
				   } 
	 return mess;		
    }
}
