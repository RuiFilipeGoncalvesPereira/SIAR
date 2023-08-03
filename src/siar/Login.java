package siar;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import java.awt.*;
import javax.swing.*;


public class Login {

	JFrame frame;
	private JLabel lblteste;
	private JLabel lblData;
	private JLabel lblHora;
	Connection conn_utilizador = null;
	Connection conn_utilizador_des = null;
	Data mostra_data;
	int conta=0;  
	private JPasswordField passField;
	public static JTextField txtUser;
	ResultSet rs = null;
	ResultSet rs_des = null;
	ResultSet rs_admin = null;
	ResultSet rs_gecan = null;
	PreparedStatement pst = null;
	PreparedStatement pst_des = null;
	PreparedStatement stmt =null;
	String nome_utilizador = null;
    public static String numero;
	String Nome;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
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
		conn_utilizador_des = JavaConection.ConnecrDb();
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
				Valida_Login();
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
		lblData.setBounds(168, 0, 266, 20);
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
	                }
	            }
		 });
	}
	public String prodQty(int num_mec)
	 {
	     String name = null;
		  try
		  {
	      String sql="select nome_utilizador from siar.siar_utilizadores where Num_Mecanog='"+num_mec+"'";
	      conn_utilizador.prepareStatement(sql);
          pst=conn_utilizador.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
          rs=pst.executeQuery();
	      while(rs.next())
	        {
	    	  name=rs.getString(1);
	        }
	      }
	   catch (SQLException ex)
		 {
		   JOptionPane.showMessageDialog(null, ex);
	     }
	  return name;
    }
	@SuppressWarnings("deprecation")
	public void Valida_Login()
	{ 
		    if(txtUser.getText().length()==0)
		    {
	             JOptionPane.showMessageDialog(null, "O Campo do Número Mecanográfico não pode ser nulo2!");
	             txtUser.requestFocus();
	             return; 
	        }
			if(passField.getText().length()==0)
			{
	             JOptionPane.showMessageDialog(null, "O Campo de Password não pode ser nulo!");
	             passField.requestFocus();
	             return;
	        }
				        if(conta<= 2)
				         {    
				            try
				            {
				               String sql="select * from siar.siar_utilizadores Where Num_Mecanog='"+txtUser.getText()+"' and Senha='"+passField.getText()+"'";
				               conn_utilizador.prepareStatement(sql);
				               pst=conn_utilizador.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				               rs=pst.executeQuery();
				               
				               String sql_des="select * from siar.siar_utilizadores Where Num_Mecanog='"+txtUser.getText()+"' and Senha='"+passField.getText()+"'"
				               		+ "and dta_desativo is not null";
				               conn_utilizador_des.prepareStatement(sql_des);
				               pst_des=conn_utilizador_des.prepareStatement(sql_des,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				               rs_des=pst_des.executeQuery();
				               
				               String sql_admin="select * from siar.siar_dominios b"
				               		+ " Where b.dominio='admin' and valor='"+txtUser.getText()+"'";
				               conn_utilizador.prepareStatement(sql_admin);
				               pst=conn_utilizador.prepareStatement(sql_admin,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				               rs_admin=pst.executeQuery();
				               
				               String sql_gecan="select * from siar.siar_dominios b"
					               		+ " Where b.dominio='gecan' and valor='"+txtUser.getText()+"'";
				               conn_utilizador.prepareStatement(sql_gecan);
				               pst=conn_utilizador.prepareStatement(sql_gecan,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				               rs_gecan=pst.executeQuery();
				               
				               numero = txtUser.getText(); 
				               
				               if(rs_des.first())
				               {
				            	   JOptionPane.showMessageDialog(null, "Your acess is bloked, contact the administrator!"); 
				               }
				               	else
				               {
				            	      if(rs.first())
						               {
						            	   if(rs_admin.first())
						            	   {
						            		     JOptionPane.showMessageDialog(null, "Bem vindo senhor " + prodQty(Integer.parseInt(txtUser.getText())));
								                 frame.dispose();
						                         Administrador Admin = new Administrador();
						                         Admin.setVisible(true);
						                         Administrador.lblnum.setText(numero);
						            	   }
						            	   else if(rs_gecan.first()){
								            	 JOptionPane.showMessageDialog(null, "Bem vindo senhor " + prodQty(Integer.parseInt(txtUser.getText())));
								                 frame.dispose();
						                         Gestor_Refeicoes Ges = new Gestor_Refeicoes();
						                         Ges.setVisible(true);
						                         Gestor_Refeicoes.lblnum.setText(numero);
						            	   }
						            	   else
						            	   {
								                 JOptionPane.showMessageDialog(null, "Bem vindo senhor " + prodQty(Integer.parseInt(txtUser.getText())));
								                 frame.dispose();
						                         Marcacoes Marc = new Marcacoes();
						                         Marc.setVisible(true);
						                         Marcacoes.lblNum.setText(numero);
						                         Marcacoes.btn_back_menu.setVisible(false);
						            	   }
						               }
						               else
						               {
						                   conta++; 
						                   if(conta==1)
						                   {    
						                     JOptionPane.showMessageDialog(null, "Validação Incorecta!,Têm mais uma Oportunidade!");
						                   }
						                   else
						                   {
						                     JOptionPane.showMessageDialog(null, "Falhou outra Vez!");
						                     System.exit(0);
						                   }
						               }
				               } 
					  }   
				            catch(Exception e)
				            {
				                JOptionPane.showMessageDialog(null,e);
				            }
				    }
	     }        
 }

