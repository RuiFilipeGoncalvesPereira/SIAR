package siar;
import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import java.awt.*;
import javax.swing.*;


public class Login {

	public static JFrame frame;
	private JLabel lblteste;
	private JLabel lblData;
	private JLabel lblHora;
	static Connection conn_utilizador = null;
	Data mostra_data;
	public static JPasswordField passField;
	public static JTextField txtUser;
	static ResultSet rs = null;
	static PreparedStatement pst = null;
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

	/**
	 * Create the application.
	 */
	public Login() 
	{
		conn_utilizador = JavaConection.ConnecrDb();
		initialize();
		mostra_data = new Data();
        mostra_data.le_data();
        CurrentHour();
        passField = new JPasswordField();
        passField.setBounds(151, 114, 112, 20);
        frame.getContentPane().add(passField);
        
        JLabel lblNewLabel = new JLabel("");
        Image img = new ImageIcon(this.getClass().getResource("/Siarfundo.jpg")).getImage();
        lblData.setText(                              "Hoje é "+mostra_data.dia_semana+" ,dia "+mostra_data.dia+" de "+mostra_data.mes+" de "+mostra_data.ano);
        lblNewLabel.setIcon(new ImageIcon(img));
        lblNewLabel.setBounds(0, 0, 444, 271);
        frame.getContentPane().add(lblNewLabel);
	}
  public void replace(String str)      
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
	 }
  public void CurrentHour()
   {
	  Thread clock=new Thread()
	  {
		  public void run()
		  {
			  for(;;)
			  {
			       mostra_data.le_hora();	  
			       lblHora.setText("Hora: "+mostra_data.hora);	
                try {
		              sleep(1000);
	                }
                catch (InterruptedException e) 
                   {
                	e.printStackTrace();
	               }
             }
         }
      };
    clock.start();
  }
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Rui Pereira\\Desktop\\Siarfundo.jpg"));
		frame.setResizable(false);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Menu de acesso ao Sistema");
		frame.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("Login");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton.setIcon(new ImageIcon("C:\\Users\\Rui Pereira\\Documents\\Icons_Geral\\Icons\\001_06.gif"));
		btnNewButton.setToolTipText("Acesso ao Sistema");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Check_Validation.Check_Validation.Valida_Login();
			}	    
		});
		btnNewButton.setBounds(151, 139, 112, 33);
		frame.getContentPane().add(btnNewButton);
		
		JLabel lblUsername = new JLabel("N\u00BA Mecanogr\u00E1fico:");
		lblUsername.setBackground(Color.ORANGE);
		lblUsername.setForeground(Color.BLACK);
		lblUsername.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		lblUsername.setBounds(23, 89, 127, 14);
		frame.getContentPane().add(lblUsername);
		lblData = new JLabel("");
		lblData.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 14));
		lblData.setBackground(Color.LIGHT_GRAY);
		lblData.setForeground(SystemColor.textHighlight);
		lblData.setBounds(116, 0, 318, 20);
		frame.getContentPane().add(lblData);
		
		JLabel lblPass = new JLabel("Pass:");
		lblPass.setBackground(Color.ORANGE);
		lblPass.setForeground(Color.BLACK);
		lblPass.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblPass.setBounds(104, 114, 46, 14);
		frame.getContentPane().add(lblPass);
		
		lblHora = new JLabel();
		lblHora.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 14));
		lblHora.setForeground(SystemColor.textHighlight);
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

