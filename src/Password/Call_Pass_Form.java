package Password;

import siar.Login;

public class Call_Pass_Form {
	
	public static void Alterar_Password()
	{
        Altera_Password Altera = new Altera_Password();
        Altera.setVisible(true);
        Altera_Password.textNum.setText(Login.txtUser.getText());
	}

}
