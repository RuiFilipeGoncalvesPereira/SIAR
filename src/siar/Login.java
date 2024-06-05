package siar;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.*;
import javax.swing.*;

import Data.Data_Read_Values;

public class Login {

	public static JFrame frame;
	private JLabel lblteste;
	private JLabel lblData;
	public static JLabel lblHora;
	static Connection conn_utilizador = null;
	public static JPasswordField passField;
	public static JTextField txtUser;
	static ResultSet rs = null;
	static Statement pst = null;
	String nome_utilizador = null;
	String Nome;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@SuppressWarnings("static-access")
			public void run() {
				try {
					Login window = new Login();
					window.frame.setVisible(true);
					window.frame.setLocationRelativeTo(null);
					window.frame.setLocation(200,100);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@SuppressWarnings("unused")
	public Login() 
	{
		conn_utilizador = JavaConection.ConnecrDb();
		initialize();
		Data_Read_Values.mostra_data = new Data_Read_Values();
		Data_Read_Values.mostra_data.le_data();
		Data_Read_Values.CurrentHour();
        passField = new JPasswordField();
        passField.setBounds(151, 114, 112, 20);
        frame.getContentPane().add(passField);
        Image img = new ImageIcon(this.getClass().getResource("/Siarfundo.jpg")).getImage();
        lblData.setText(                              "Hoje é "+Data_Read_Values.mostra_data.dia_semana+" ,dia "+Data_Read_Values.mostra_data.dia+" de "+Data_Read_Values.mostra_data.mes+" de "+Data_Read_Values.mostra_data.ano);
        
        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon("C:\\Users\\Rui Pereira\\eclipse-workspace\\AlimentacaoJava_Demo\\Img\\Siarfundo.jpg"));
        lblNewLabel.setBounds(0, 0, 459, 261);
        frame.getContentPane().add(lblNewLabel);
	}
  /*public void replace(String str)      
	 {      
	
	  for( int i = 0; i < str.length(); i++ )      
	         if( Character.isDigit( str.charAt( i ) ) == true )   
	         {	 
	        	return;
	         }
	         else
	         {
	        	 JOptionPane.showMessageDialog(null, "O Campo do Número Mecanográfico só pode conter números!"); 
	         }
	 }*/
	private void initialize() {
		frame = new JFrame();
		frame.setSize(new Dimension(512, 300));
		frame.setResizable(false);
		frame.setBounds(100, 100, 475, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Menu de acesso ao Sistema");
		frame.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("Login");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton.setIcon(new ImageIcon("C:\\Users\\Rui Pereira\\Documents\\Icons_Geral\\Icons\\001_06.gif"));
		btnNewButton.setToolTipText("Acesso ao Sistema");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Check_Validation.Check_Validation.Valida_Login();
				} catch (HeadlessException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}	    
		});
		btnNewButton.setBounds(151, 139, 112, 33);
		frame.getContentPane().add(btnNewButton);
		
		JLabel lblUsername = new JLabel("Número Mecan.:");
		lblUsername.setBackground(Color.ORANGE);
		lblUsername.setForeground(new Color(255, 0, 0));
		lblUsername.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblUsername.setBounds(33, 88, 127, 14);
		frame.getContentPane().add(lblUsername);
		lblData = new JLabel("");
		lblData.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 14));
		lblData.setBackground(Color.LIGHT_GRAY);
		lblData.setForeground(new Color(255, 0, 0));
		lblData.setBounds(116, 0, 318, 20);
		frame.getContentPane().add(lblData);
		
		JLabel lblPass = new JLabel("Pass:");
		lblPass.setBackground(Color.ORANGE);
		lblPass.setForeground(new Color(255, 0, 0));
		lblPass.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblPass.setBounds(104, 114, 46, 14);
		frame.getContentPane().add(lblPass);
		
		lblHora = new JLabel();
		lblHora.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 14));
		lblHora.setForeground(new Color(255, 0, 0));
		lblHora.setBackground(Color.GRAY);
		lblHora.setBounds(344, 240, 90, 20);
		frame.getContentPane().add(lblHora);
		
		lblteste = new JLabel("");
		lblteste.setBounds(23, 220, 46, 14);
		frame.getContentPane().add(lblteste);
		
		txtUser = new JTextField();
		txtUser.setBounds(151, 86, 112, 20);
		frame.getContentPane().add(txtUser);
		txtUser.setColumns(10);
		txtUser.addKeyListener(new KeyAdapter() {
		    public void keyTyped(KeyEvent e) 
		    {
		        char caracter = e.getKeyChar();
		        if (((caracter < '0') || (caracter > '9'))) 
		        {
		            e.consume();
		        	Login.txtUser.setText(null);
		        	JOptionPane.showMessageDialog(null,"Only accept numbers!");
		        }
		    }
		 });
	}
 }

