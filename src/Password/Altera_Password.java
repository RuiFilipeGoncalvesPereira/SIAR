package Password;
import java.awt.EventQueue;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import siar.JavaConection;
import siar.Login;
import siar.Marcacoes;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class Altera_Password extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public static JTextField textNum;
	Connection conn_utilizador = null;
	ResultSet rs = null;
	CallableStatement  pst = null;
	PreparedStatement  pst_ = null;
	static ResultSet rs_admin = null;
	static ResultSet rs_gecan = null;
	static ResultSet  rs_ement = null;
	String mess;
	private JPasswordField pass_velha;
	private JPasswordField pass_Nova;
	private JPasswordField Pass_nova_2;
	public static final String VAL_PASS = "{CALL siar.PKUTENTE.Altera_Password(?,?,?)}";
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Altera_Password frame = new Altera_Password();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public Altera_Password() {
		conn_utilizador = JavaConection.ConnecrDb(); 
		initialize();
	}
	private void initialize() {
		this.setTitle("Changing Password Screen");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 344, 243);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JButton btnNewButton = new JButton("Changing of Password");
		btnNewButton.addActionListener(new ActionListener()
		{
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) 
			{
				if(pass_velha.getText().length()==0){
		             JOptionPane.showMessageDialog(null, "O campo da senha anterior não pode ser nulo!");
		             pass_velha.requestFocus();
		             return;
		        }
				if(pass_Nova.getText().length()==0){
		             JOptionPane.showMessageDialog(null, "O campo da senha nova não pode ser nulo!");
		             pass_Nova.requestFocus();
		             return;
		        }
				if(pass_Nova.getText().length()> 100){
		             JOptionPane.showMessageDialog(null, "O campo da senha nova não pode ter mais de 100 caracteres!");
		             pass_Nova.requestFocus();
		             return;
		        }
				if(Pass_nova_2.getText().length()==0){
		             JOptionPane.showMessageDialog(null, "Deve introduzir novamente a nova Senha!");
		             Pass_nova_2.requestFocus();
		             return;
		        }
				if(Pass_nova_2.getText().length()!=pass_Nova.getText().length()){
		             JOptionPane.showMessageDialog(null, "A senha deve ser idêntica ao valor da nova Senha!");
		             pass_Nova.requestFocus();
		             return;
		        }
				try {
					pst = conn_utilizador.prepareCall(VAL_PASS);
					pst.setString(1, Pass_nova_2.getText());
					pst.setInt(2, Integer.parseInt(textNum.getText()));
					pst.registerOutParameter(3, Types.VARCHAR);
					pst.execute();
					String mess = pst.getString(3);
    			JOptionPane.showMessageDialog(null, mess);
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Não foi possivél alterar a password!" + e1);
				}
				 catch (Exception e2) {
						JOptionPane.showMessageDialog(null, "Erro de Extração!" + e2);
					}
			}

		});
		btnNewButton.setIcon(new ImageIcon("C:\\Users\\Rui Pereira\\Documents\\Icons_Geral\\Icons\\lock_key.gif"));
		btnNewButton.setBounds(78, 177, 212, 23);
		contentPane.add(btnNewButton);
		
		textNum = new JTextField();
		textNum.setEditable(false);
		textNum.setBounds(146, 61, 28, 20);
		contentPane.add(textNum);
		textNum.setColumns(10);
		
		JButton button = new JButton("");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				conn_utilizador = JavaConection.ConnecrDb();
				String sql_admin="select * from siar.siar_dominios b"
		           		+ " Where b.dominio='admin' and valor='"+Integer.parseInt(Login.txtUser.getText())+"'";
		           try {
					conn_utilizador.prepareStatement(sql_admin);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
		           try {
		        	   pst_=conn_utilizador.prepareStatement(sql_admin,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
		           try {
					rs_admin=pst_.executeQuery();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
		           
		           String sql_gecan="select * from siar.siar_dominios b"
		               		+ " Where b.dominio='gecan' and valor='"+Integer.parseInt(Login.txtUser.getText())+"'";
		           try {
					conn_utilizador.prepareStatement(sql_gecan);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
		           try {
		        	   pst_=conn_utilizador.prepareStatement(sql_gecan,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
		         try {
					rs_gecan=pst_.executeQuery();
				} catch (SQLException e1) {
					e1.printStackTrace();
				} 
		         
		           String sql_ement="select * from siar.siar_dominios b"
		               		+ " Where b.dominio='gemen' and valor='"+Integer.parseInt(Login.txtUser.getText())+"'";
		           try {
					conn_utilizador.prepareStatement(sql_ement);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
		           try {
		        	   pst_=conn_utilizador.prepareStatement(sql_ement,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
		         try {
					rs_ement=pst_.executeQuery();
				} catch (SQLException e1) {
					e1.printStackTrace();
				} 
    
	    	  try {
					if(rs_admin.first())
					   {
		                dispose();
		        		Marcacoes Marc = new Marcacoes();
						Marc.setVisible(true);
						Marcacoes.btn_back_menu.setVisible(true);
					   } 
					else
						try {
							if(rs_gecan.first())
							  {
				                dispose();
				        		Marcacoes Marc = new Marcacoes();
								Marc.setVisible(true);
								Marcacoes.btn_back_menu.setVisible(true);
							   }
							else if(rs_ement.first())
							  {
				                dispose();
				        		Marcacoes Marc = new Marcacoes();
								Marc.setVisible(true);
								Marcacoes.btn_back_menu.setVisible(true);
							   }
							else
							{
				                dispose();
				        		Marcacoes Marc = new Marcacoes();
								Marc.setVisible(true);
								Marcacoes.btn_back_menu.setVisible(false);
							}
						    } 
						    catch (SQLException e1)
						    {
								e1.printStackTrace();
							}
				    } 
		    	   catch (SQLException e1)
		    	   {
					e1.printStackTrace();
				   }
			}
		});
		button.setIcon(new ImageIcon("C:\\Icons_Geral\\Icons\\001_20.gif"));
		button.setBounds(10, 11, 43, 36);
		contentPane.add(button);
		
		JLabel lblNewLabel = new JLabel("N\u00BA Mecanogr\u00E1fico:");
		lblNewLabel.setBounds(20, 64, 127, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Velha Password:");
		lblNewLabel_1.setBounds(20, 92, 98, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Nova PassWord:");
		lblNewLabel_2.setBounds(20, 119, 98, 14);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Repita PassWord:");
		lblNewLabel_3.setBounds(20, 144, 119, 14);
		contentPane.add(lblNewLabel_3);
		
		pass_velha = new JPasswordField();
		pass_velha.setBounds(146, 89, 144, 20);
		contentPane.add(pass_velha);
		
		pass_Nova = new JPasswordField();
		pass_Nova.setBounds(146, 116, 144, 20);
		contentPane.add(pass_Nova);
		
		Pass_nova_2 = new JPasswordField();
		Pass_nova_2.setBounds(146, 146, 144, 20);
		contentPane.add(Pass_nova_2);
		
	}
}
