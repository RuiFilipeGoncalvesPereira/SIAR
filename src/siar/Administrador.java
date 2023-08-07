package siar;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import net.proteanit.sql.DbUtils;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import java.awt.Image;

import javax.swing.JScrollPane;
import javax.swing.JFileChooser;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import com.toedter.calendar.JDateChooser;

public class Administrador extends JFrame {
	public static JLabel lblnum, lblNome;
	private JPanel contentPane;
	private JLabel lblDesc;
	private JTable table;
	private JTextField Nome_Uti;
	private JTextField Senha;
	private JTable table_1;
	private JTextField Desc_Ref;
	private JTable table_3;
	public static JTextField Desc_Prato;
	public static JTable table_4;
	public static JTextField Dom;
	public static JTextField Val;
	public static JTextField Desc_dom;
	private JTable table_5;
	public static JTextField Val_Par;
	public static JTextField Desc_Par;
	public static JTable table_7;
	public static JTable table_6;
	public static JDateChooser DC_prato;
	public static JDateChooser DC_ref;
	public static JDateChooser dta_feriado;
	Connection conn_utilizador = null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	Connection conn_ativa = null;
	ResultSet rs_at = null;
	PreparedStatement pst_at = null;
	Connection conn_des = null;
	ResultSet rs_des = null;
	PreparedStatement pst_des = null;
	Connection conn_sel = null;
	ResultSet rs_sel = null;
	PreparedStatement pst_sel = null;
	Connection conn_sel_ref = null;
	ResultSet rs_sel_ref = null;
	PreparedStatement pst_sel_ref = null;
	Connection conn_insuti = null;
	PreparedStatement pstinsuti = null;
	PreparedStatement pst_upd = null;
	Connection conn_upd = null;
	ResultSet resupd = null;
	PreparedStatement pst_seqfe = null;
	Connection conn_seqfe = null;
	ResultSet resseqfe = null;
	PreparedStatement pst_insfe = null;
	Connection conn_confe  = null;
	ResultSet rsfe = null;
	Connection conn_atu_fe  = null;
	PreparedStatement pstatu_fe = null;
	Connection conn_conref  = null;
	ResultSet rsref = null;
	Connection conn_atu_ref  = null;
	PreparedStatement pstatu_ref = null;
	private JTable table_2;
	private JTextField nmec_aux;
	private JTextField nome_aux;
	private JTextField email_aux;
	public String ad1; 
	public String ad_feriado; 
	public String ad_refeicao; 
	public JLabel Jlnome;
	public JLabel lblimg2;
	PreparedStatement pst_seqref = null;
	Connection conn_seqref = null;
	ResultSet resseqref = null;
	PreparedStatement pst_insref = null;
	Date now = new Date(System.currentTimeMillis());
    java.util.Date data_hoje = new java.util.Date();
    java.sql.Date sqlDate = new java.sql.Date(data_hoje.getTime());
	Valida_Senha VS = new Valida_Senha();
	byte[] IMAGEM=null;
	String filename = null;
	private JTextField Email;
	private JTextField img;
	private JTextField desc_feriado;
	private JTable table_8;
	public static final String ATI_DES_UTILIZADOR = "{CALL siar.PKG1.Ati_Des_Uti(?,?)}";
	Connection conn_pst_ati_des = null;
	CallableStatement  pst_ati_des = null;
	PreparedStatement pst_feriado = null;
	Connection conn_feriado = null;
	ResultSet resferiado = null;
	
    //Inserir data na tabela dos feriados
	SimpleDateFormat df2 = new SimpleDateFormat("dd-MM-yyyy");
	String strDate = df2.format(now);
	private JTextField cod_aux;
	private JTextField ref_aux;
	private JTextField dta_ref_aux;
	private JTextField Codfed;
	private JTextField Dta_fed;
	private JTextField Desc_Fed;
	public static JTextField aux_cod_pr;
	public static JTextField aux_desc_prato;
	public static JTextField dta_reg_prato;
	public static JTextField val_aux;
	public static JTextField desc_aux;
	public static JTextField txt_cod_aux;
	public static JTextField dom_aux;
	public static JTextField valor_dom_aux;
	public static JTextField desc_aux_dom;
	
	//SimpleDateFormat ddath = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try
				{
					Administrador frame = new Administrador();
					frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		);
	 }

	/**
	 * Create the frame.
	 */
	public Administrador() {
		setAutoRequestFocus(false);
		conn_utilizador = JavaConection.ConnecrDb(); 
		conn_ativa = JavaConection.ConnecrDb();
		conn_des = JavaConection.ConnecrDb();
		conn_sel = JavaConection.ConnecrDb();
		conn_insuti = JavaConection.ConnecrDb();
		conn_upd = JavaConection.ConnecrDb();
		conn_seqfe = JavaConection.ConnecrDb();
		conn_confe = JavaConection.ConnecrDb();
		conn_pst_ati_des = JavaConection.ConnecrDb();
		conn_atu_fe = JavaConection.ConnecrDb();
        conn_feriado = JavaConection.ConnecrDb();
        conn_seqref = JavaConection.ConnecrDb();
    	conn_atu_ref = JavaConection.ConnecrDb();
    	conn_conref = JavaConection.ConnecrDb();
    	conn_sel_ref = JavaConection.ConnecrDb();
		initialize();
		prencheUtilizadores();
		prencheFeriados();
		prencherRefeicao();
		parametros.parametros.prenche_parametros();
		parametros.prato.prencher_pratos();
		parametros.dominios.prenche_dominios();
		GetName Gn = new GetName();
		@SuppressWarnings("static-access")
		String nome = Gn.GETNAME(Integer.parseInt(Login.txtUser.getText()));
		Administrador.lblNome.setText(nome);
		lblnum.setVisible(false);
		DC_ref.setDate(sqlDate);
		DC_prato.setDate(sqlDate);
	}
	private void initialize() 
	{
		setTitle("Menu de Administra\u00E7\u00E3o");
		setAutoRequestFocus(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 1000, 491);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblnum = new JLabel("");
		lblnum.setBounds(358, 0, 25, 14);
		contentPane.add(lblnum);
		
		lblNome = new JLabel("");
		lblNome.setBounds(334, 0, 348, 14);
		contentPane.add(lblNome);
		
		JButton button = new JButton("");
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
	                dispose();
	        		Login window = new Login();
					window.frame.setVisible(true);
			}
		}
		);
		button.setIcon(new ImageIcon("C:\\Users\\Rui Pereira\\Documents\\Icons_Geral\\Icons\\001_20.gif"));
		button.setBounds(10, 11, 43, 36);
		contentPane.add(button);
		
		lblDesc = new JLabel("Utilizador:");
		lblDesc.setBounds(261, 0, 66, 14);
		contentPane.add(lblDesc);
		
		JButton button_1 = new JButton("");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                dispose();
                Marcacoes Marc = new Marcacoes();
                Marc.setVisible(true);
                Marcacoes.lblNum.setText(Login.txtUser.getText());
			}
		});
		button_1.setIcon(new ImageIcon("C:\\Users\\Rui Pereira\\Documents\\Icons_Geral\\Icons\\001_44.gif"));
		button_1.setBounds(10, 47, 43, 36);
		contentPane.add(button_1);
		
		JButton button_2 = new JButton("");
		button_2.setIcon(new ImageIcon("C:\\Users\\Rui Pereira\\Documents\\Icons_Geral\\Icons\\pdf.gif"));
		button_2.setBounds(10, 83, 43, 36);
		contentPane.add(button_2);
		button_2.setEnabled(false);
		button_2.setVisible(false);
		button_2.setSize(0,0);

		
		table = new JTable();
		table.setBounds(32, 70, 1, 1);
		contentPane.add(table);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(63, 16, 899, 425);
		contentPane.add(tabbedPane);
		
		JPanel Ges_Uti = new JPanel();
		tabbedPane.addTab("Gest\u00E3o de Utilizadores", null, Ges_Uti, null);
		Ges_Uti.setLayout(null);
		
		Nome_Uti = new JTextField();
		Nome_Uti.setBounds(10, 26, 361, 20);
		Ges_Uti.add(Nome_Uti);
		Nome_Uti.setColumns(10);
		
		Senha = new JTextField();
		Senha.setBounds(606, 26, 115, 20);
		Ges_Uti.add(Senha);
		Senha.setColumns(10);
		Senha.setEnabled(false);
		
		JButton button_3 = new JButton("");
		button_3.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				pesq_utilizador();
			}
		});
		button_3.setToolTipText("Pesquisar Utilizador");
		button_3.setIcon(new ImageIcon("C:\\Users\\Rui Pereira\\Documents\\Icons_Geral\\Icons\\001_38.gif"));
		button_3.setBounds(820, 58, 27, 28);
		Ges_Uti.add(button_3);
		
		JButton button_4 = new JButton("");
		button_4.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				prencheUtilizadores();
			}
		});
		button_4.setToolTipText("Ver Todos  os Utilizadores");
		button_4.setIcon(new ImageIcon("C:\\Users\\Rui Pereira\\Documents\\Icons_Geral\\Icons\\001_57.gif"));
		button_4.setBounds(857, 58, 27, 28);
		Ges_Uti.add(button_4);
		
		JButton button_5 = new JButton("");
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
					Insere_Utilizador();
					prencheUtilizadores();
				} 
				catch (SQLException e1)
				{
					e1.printStackTrace();
				}
			}
		});
		button_5.setToolTipText("Inserir Utilizador");
		button_5.setIcon(new ImageIcon("C:\\Users\\Rui Pereira\\Documents\\Icons_Geral\\Icons\\action_add.gif"));

		button_5.setBounds(857, 25, 27, 28);
		Ges_Uti.add(button_5);
		
		JLabel lblNewLabel_1 = new JLabel("Nome do Utilizador");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(107, 11, 161, 14);
		Ges_Uti.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Senha");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(636, 11, 46, 14);
		Ges_Uti.add(lblNewLabel_2);
		
		table_1 = new JTable();
		table_1.setBounds(10, 373, 586, -299);
		Ges_Uti.add(table_1);
		
		JButton button_6 = new JButton("");
		button_6.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try
				{
					ativa_desat();
					String mess = pst_ati_des.getString(2);
				    if(mess == null)
				    {	
					  ativa_uti();
				    }  
				    else
				    {
				      JOptionPane.showMessageDialog(null, mess);
				    }
				} 
				catch (SQLException e1)
				{
					e1.printStackTrace();
			    }
			}	
		});
		button_6.setIcon(new ImageIcon("C:\\Users\\Rui Pereira\\Documents\\Icons_Geral\\Icons\\001_06.gif"));
		button_6.setToolTipText("Ativar Utilizador");
		button_6.setBounds(804, 225, 27, 28);
		Ges_Uti.add(button_6);
		
		JButton button_7 = new JButton("");
		button_7.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				try
				{
					ativa_desat();
					String mess = pst_ati_des.getString(2);
				    if(mess == null)
				    {	
         			  desativa_uti();
				    }  
				    else
				    {
				      JOptionPane.showMessageDialog(null, mess);
				    }
				} 
				catch (SQLException e1)
				{
					e1.printStackTrace();
				}
			}
		});
		button_7.setIcon(new ImageIcon("C:\\Users\\Rui Pereira\\Documents\\Icons_Geral\\Icons\\001_05.gif"));
		button_7.setToolTipText("Desativar Utilizador");
		button_7.setBounds(835, 225, 27, 28);
		Ges_Uti.add(button_7);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 68, 784, 286);
		Ges_Uti.add(scrollPane);
		
		table_2 = new JTable();
		table_2.addMouseListener(new MouseAdapter()
		{
			/*public void mouseClicked(MouseEvent arg0)
			{
				seleciona_linha();	
			}*/
			@Override
			public void mousePressed(MouseEvent e) {
				seleciona_linha();	
			}
		});
		scrollPane.setViewportView(table_2);
		
		JButton button_16 = new JButton("");
		button_16.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				atualiza_uti();
			}
		});
		button_16.setIcon(new ImageIcon("C:\\Users\\Rui Pereira\\Documents\\Icons_Geral\\Icons\\affldsav.gif"));
		button_16.setToolTipText("Ativar Utilizador");
		button_16.setBounds(804, 253, 27, 28);
		Ges_Uti.add(button_16);
		
		nmec_aux = new JTextField();
		nmec_aux.setBounds(10, 373, 50, 20);
		Ges_Uti.add(nmec_aux);
		nmec_aux.setEditable(false);
		nmec_aux.setColumns(10);
		
		nome_aux = new JTextField();
		nome_aux.setBounds(70, 373, 361, 20);
		Ges_Uti.add(nome_aux);
		nome_aux.setColumns(10);
		
		email_aux = new JTextField();
		email_aux.setBounds(441, 373, 215, 20);
		Ges_Uti.add(email_aux);
		email_aux.setColumns(10);
		
		JLabel lblNMec = new JLabel("N\u00BA Mec");
		lblNMec.setHorizontalAlignment(SwingConstants.CENTER);
		lblNMec.setBounds(10, 358, 50, 14);
		Ges_Uti.add(lblNMec);
		
		JLabel label = new JLabel("Nome do Utilizador");
		label.setBounds(176, 358, 161, 14);
		Ges_Uti.add(label);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmail.setBounds(532, 358, 46, 14);
		Ges_Uti.add(lblEmail);
		
		Email = new JTextField();
		Email.setColumns(10);
		Email.setBounds(381, 26, 215, 20);
		Ges_Uti.add(Email);
		
		JLabel label_1 = new JLabel("Email");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(465, 11, 46, 14);
		Ges_Uti.add(label_1);
		
		img = new JTextField();
		img.setBounds(731, 26, 80, 20);
		Ges_Uti.add(img);
		img.setColumns(10);
		
		Jlnome = new JLabel("");
		Jlnome.setBounds(804, 90, 80, 102);
		Ges_Uti.add(Jlnome);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				upload_imagem();
			}
		});
		btnNewButton.setIcon(new ImageIcon("C:\\Users\\Rui Pereira\\Documents\\Icons_Geral\\Icons\\affldopn.gif"));
		btnNewButton.setBounds(820, 25, 27, 28);
		Ges_Uti.add(btnNewButton);
		
		JLabel Img = new JLabel("Imagem");
		Img.setBounds(748, 11, 46, 14);
		Ges_Uti.add(Img);
		
		lblimg2 = new JLabel("");
		lblimg2.setBounds(804, 300, 80, 93);
		Ges_Uti.add(lblimg2);
		
		JLabel lblNewLabel = new JLabel("Imagem Atual");
		lblNewLabel.setBounds(804, 280, 80, 20);
		Ges_Uti.add(lblNewLabel);
		
		JButton button_17 = new JButton("");
		button_17.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				upload_imagem_upd();
			}
		});
		button_17.setIcon(new ImageIcon("C:\\Users\\Rui Pereira\\Documents\\Icons_Geral\\Icons\\folder_files.gif"));
		button_17.setBounds(666, 365, 27, 28);
		Ges_Uti.add(button_17);
		
	
		JPanel Ges_Ref = new JPanel();
		tabbedPane.addTab("Gest\u00E3o de Refei\u00E7\u00F5es", null, Ges_Ref, null);
		Ges_Ref.setLayout(null);
		
		Desc_Ref = new JTextField();
		Desc_Ref.setBounds(88, 26, 153, 20);
		Ges_Ref.add(Desc_Ref);
		Desc_Ref.setColumns(10);
		
		JButton button_8 = new JButton("");
		button_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
					Insere_Refeicao();
					prencherRefeicao();
				} 
				catch (SQLException e1)
				{
					e1.printStackTrace();
				}
			}
		});
		button_8.setIcon(new ImageIcon("C:\\Users\\Rui Pereira\\Documents\\Icons_Geral\\Icons\\action_add.gif"));
		button_8.setToolTipText("Inserir Refeicão");
		button_8.setBounds(412, 18, 27, 28);
		Ges_Ref.add(button_8);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(88, 69, 468, 286);
		Ges_Ref.add(scrollPane_1);
		
		table_3 = new JTable();
		table_3.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent arg0)
			{
				seleciona_linha_ref();	
			}
		});
		scrollPane_1.setViewportView(table_3);
		
		JLabel lblNewLabel_3 = new JLabel("Refei\u00E7\u00E3o");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setBounds(135, 11, 65, 14);
		Ges_Ref.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Data de Registo");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setBounds(271, 11, 91, 14);
		Ges_Ref.add(lblNewLabel_4);
		
		JButton button_9 = new JButton("");
		button_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				remove_refeicao();
			}
		});
		button_9.setIcon(new ImageIcon("C:\\Users\\Rui Pereira\\Documents\\Icons_Geral\\Icons\\001_05.gif"));
		button_9.setToolTipText("Desativar Refei\u00E7\u00E3o");
		button_9.setBounds(566, 69, 27, 28);
		Ges_Ref.add(button_9);
		
		DC_ref = new JDateChooser();
		DC_ref.getCalendarButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		/*DC_ref.setDateFormatString("dd-MM-yyyy HH:mm:ss");*/
		DC_ref.setDateFormatString("dd-MM-yyyy");
		DC_ref.setBounds(251, 26, 151, 20);
		Ges_Ref.add(DC_ref);
		
		cod_aux = new JTextField();
		cod_aux.setEditable(false);
		cod_aux.setBounds(154, 366, 53, 20);
		Ges_Ref.add(cod_aux);
		cod_aux.setColumns(10);
		
		JLabel lblNewLabel_12 = new JLabel("Codigo:");
		lblNewLabel_12.setBounds(98, 369, 46, 14);
		Ges_Ref.add(lblNewLabel_12);
		
		ref_aux = new JTextField();
		ref_aux.setBounds(217, 366, 160, 20);
		Ges_Ref.add(ref_aux);
		ref_aux.setColumns(10);
		
		dta_ref_aux = new JTextField();
		dta_ref_aux.setBounds(385, 366, 160, 20);
		Ges_Ref.add(dta_ref_aux);
		dta_ref_aux.setColumns(10);
		
		JPanel Ges_Pr = new JPanel();
		tabbedPane.addTab("Gest\u00E3o de Pratos", null, Ges_Pr, null);
		Ges_Pr.setLayout(null);
		
		Desc_Prato = new JTextField();
		Desc_Prato.setColumns(10);
		Desc_Prato.setBounds(88, 26, 153, 20);
		Ges_Pr.add(Desc_Prato);
		
		JButton button_10 = new JButton("");
		button_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					parametros.prato.Insere_prato();
					parametros.prato.prencher_pratos();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		button_10.setIcon(new ImageIcon("C:\\Users\\Rui Pereira\\Documents\\Icons_Geral\\Icons\\action_add.gif"));
		button_10.setToolTipText("Inserir Utilizador");
		button_10.setBounds(412, 18, 27, 28);
		Ges_Pr.add(button_10);
		
		JButton button_11 = new JButton("");
		button_11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parametros.prato.remove_prato();
			}
		});
		button_11.setIcon(new ImageIcon("C:\\Users\\Rui Pereira\\Documents\\Icons_Geral\\Icons\\001_05.gif"));
		button_11.setToolTipText("Desativar Refei\u00E7\u00E3o");
		button_11.setBounds(412, 69, 27, 28);
		Ges_Pr.add(button_11);
		
		JLabel Lbl_prato = new JLabel("Prato");
		Lbl_prato.setHorizontalAlignment(SwingConstants.CENTER);
		Lbl_prato.setBounds(135, 11, 46, 14);
		Ges_Pr.add(Lbl_prato);
		
		JLabel lblNewLabel_5 = new JLabel("Data de Registo");
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_5.setBounds(271, 11, 89, 14);
		Ges_Pr.add(lblNewLabel_5);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(88, 69, 314, 286);
		Ges_Pr.add(scrollPane_2);
		
		table_4 = new JTable();
		table_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				parametros.prato.seleciona_linha_prat();
			}
		});
		scrollPane_2.setViewportView(table_4);
		
		DC_prato = new JDateChooser();
		DC_prato.setDateFormatString("dd-MM-yyyy HH:mm:ss");
		DC_prato.setBounds(251, 26, 151, 20);
		Ges_Pr.add(DC_prato);
		
		JLabel lblNewLabel_14 = new JLabel("Prato:");
		lblNewLabel_14.setBounds(88, 366, 46, 20);
		Ges_Pr.add(lblNewLabel_14);
		
		aux_cod_pr = new JTextField();
		aux_cod_pr.setBounds(88, 0, 0, 0);
		Ges_Pr.add(aux_cod_pr);
		aux_cod_pr.setColumns(10);
		
		aux_desc_prato = new JTextField();
		aux_desc_prato.setBounds(140, 366, 89, 20);
		Ges_Pr.add(aux_desc_prato);
		aux_desc_prato.setColumns(10);
		
		dta_reg_prato = new JTextField();
		dta_reg_prato.setBounds(239, 366, 163, 20);
		Ges_Pr.add(dta_reg_prato);
		dta_reg_prato.setColumns(10);
		
		JPanel Ges_Par = new JPanel();
		tabbedPane.addTab("Gest\u00E3o de Parametros", null, Ges_Par, null);
		Ges_Par.setLayout(null);
		
		Val_Par = new JTextField();
		Val_Par.setBounds(88, 26, 74, 20);
		Ges_Par.add(Val_Par);
		Val_Par.setColumns(10);
		
		Desc_Par = new JTextField();
		Desc_Par.setBounds(172, 26, 385, 20);
		Ges_Par.add(Desc_Par);
		Desc_Par.setColumns(10);
		
		JButton button_14 = new JButton("");
		button_14.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					parametros.parametros.Insere_parametros();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				parametros.parametros.prenche_parametros();
			}
		});
		button_14.setIcon(new ImageIcon("C:\\Users\\Rui Pereira\\Documents\\Icons_Geral\\Icons\\action_add.gif"));
		button_14.setToolTipText("Inserir Utilizador");
		button_14.setBounds(568, 18, 27, 28);
		Ges_Par.add(button_14);
		
		JScrollPane scrollPane_4 = new JScrollPane();
		scrollPane_4.setBounds(88, 69, 601, 286);
		Ges_Par.add(scrollPane_4);
		
		table_7 = new JTable();
		table_7.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				parametros.parametros.seleciona_linha_param();
			}
		});
		scrollPane_4.setViewportView(table_7);
		
		JButton button_15 = new JButton("");
		button_15.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parametros.parametros.remove_parametro();
			}
		});
		button_15.setIcon(new ImageIcon("C:\\Users\\Rui Pereira\\Documents\\Icons_Geral\\Icons\\001_05.gif"));
		button_15.setToolTipText("Desativar Refei\u00E7\u00E3o");
		button_15.setBounds(699, 69, 27, 28);
		Ges_Par.add(button_15);
		
		JLabel lblNewLabel_10 = new JLabel("Valor");
		lblNewLabel_10.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_10.setBounds(102, 11, 46, 14);
		Ges_Par.add(lblNewLabel_10);
		JLabel lblNewLabel_11 = new JLabel("Descri\u00E7\u00E3o do P\u00E2rametro");
		lblNewLabel_11.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_11.setBounds(259, 11, 197, 14);
		Ges_Par.add(lblNewLabel_11);
		
		JLabel lblNewLabel_15 = new JLabel("Par\u00E2metros:");
		lblNewLabel_15.setBounds(88, 372, 75, 14);
		Ges_Par.add(lblNewLabel_15);
		
		val_aux = new JTextField();
		val_aux.setBounds(200, 372, 70, 20);
		Ges_Par.add(val_aux);
		val_aux.setColumns(10);
		
		desc_aux = new JTextField();
		desc_aux.setColumns(10);
		desc_aux.setBounds(280, 372, 385, 20);
		Ges_Par.add(desc_aux);
		
		txt_cod_aux = new JTextField();
		txt_cod_aux.setBounds(165, 372, 25, 20);
		Ges_Par.add(txt_cod_aux);
		txt_cod_aux.setColumns(10);
		
		JButton button_19_1 = new JButton("");
		button_19_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parametros.parametros.update_param();
				parametros.parametros.prenche_parametros();
			}
		});
		button_19_1.setIcon(new ImageIcon("C:\\Users\\Rui Pereira\\Documents\\Icons_Geral\\Icons\\affldsav.gif"));
		button_19_1.setToolTipText("Ativar Utilizador");
		button_19_1.setBounds(699, 100, 27, 28);
		Ges_Par.add(button_19_1);
		
		JPanel Ges_Dom = new JPanel();
		tabbedPane.addTab("Gest\u00E3o de Dominios", null, Ges_Dom, null);
		Ges_Dom.setLayout(null);
		
		Dom = new JTextField();
		Dom.setColumns(10);
		Dom.setBounds(43, 26, 73, 20);
		Ges_Dom.add(Dom);
		
		Val = new JTextField();
		Val.setBounds(126, 26, 62, 20);
		Ges_Dom.add(Val);
		Val.setColumns(10);
		
		Desc_dom = new JTextField();
		Desc_dom.setBounds(198, 26, 292, 20);
		Ges_Dom.add(Desc_dom);
		Desc_dom.setColumns(10);
		
		JButton button_12 = new JButton("");
		button_12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					parametros.dominios.Insere_dominios();
					parametros.dominios.prenche_dominios();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		button_12.setIcon(new ImageIcon("C:\\Users\\Rui Pereira\\Documents\\Icons_Geral\\Icons\\action_add.gif"));
		button_12.setToolTipText("Inserir Utilizador");
		button_12.setBounds(500, 18, 27, 28);
		Ges_Dom.add(button_12);
		
		table_5 = new JTable();
		table_5.setBounds(76, 79, 1, 1);
		Ges_Dom.add(table_5);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(43, 69, 446, 286);
		Ges_Dom.add(scrollPane_3);
		
		table_6 = new JTable();
		table_6.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				parametros.dominios.seleciona_linha_dom();
			}
		});
		scrollPane_3.setViewportView(table_6);
		
		JButton button_13 = new JButton("");
		button_13.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parametros.dominios.remove_dominio();
				parametros.dominios.prenche_dominios();
			}
		});
		button_13.setIcon(new ImageIcon("C:\\Users\\Rui Pereira\\Documents\\Icons_Geral\\Icons\\001_05.gif"));
		button_13.setToolTipText("Desativar Refei\u00E7\u00E3o");
		button_13.setBounds(500, 69, 27, 28);
		Ges_Dom.add(button_13);
		
		JLabel lblNewLabel_6 = new JLabel("Dominio");
		lblNewLabel_6.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_6.setBounds(57, 11, 46, 14);
		Ges_Dom.add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("New label");
		lblNewLabel_7.setBounds(142, 11, 1, 4);
		Ges_Dom.add(lblNewLabel_7);
		
		JLabel lblNewLabel_8 = new JLabel("Valor");
		lblNewLabel_8.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_8.setBounds(131, 11, 46, 14);
		Ges_Dom.add(lblNewLabel_8);
		
		JLabel lblNewLabel_9 = new JLabel("Descric\u00E3o do Dominio");
		lblNewLabel_9.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_9.setBounds(274, 11, 147, 14);
		Ges_Dom.add(lblNewLabel_9);
		
		JLabel lblNewLabel_16 = new JLabel("Dominio:");
		lblNewLabel_16.setBounds(43, 366, 60, 14);
		Ges_Dom.add(lblNewLabel_16);
		
		JButton button_19_2 = new JButton("");
		button_19_2.setIcon(new ImageIcon("C:\\Users\\Rui Pereira\\Documents\\Icons_Geral\\Icons\\afsave_postfix.gif"));
		button_19_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parametros.dominios.update_dom();
				parametros.dominios.prenche_dominios();
			}
		});
		button_19_2.setToolTipText("Ativar Utilizador");
		button_19_2.setBounds(500, 98, 27, 28);
		Ges_Dom.add(button_19_2);
		
		dom_aux = new JTextField();
		dom_aux.setBounds(104, 366, 52, 20);
		Ges_Dom.add(dom_aux);
		dom_aux.setColumns(10);
		
		valor_dom_aux = new JTextField();
		valor_dom_aux.setBounds(166, 366, 86, 20);
		Ges_Dom.add(valor_dom_aux);
		valor_dom_aux.setColumns(10);
		
		desc_aux_dom = new JTextField();
		desc_aux_dom.setBounds(262, 366, 228, 20);
		Ges_Dom.add(desc_aux_dom);
		desc_aux_dom.setColumns(10);
		JPanel Ges_Fer = new JPanel();
		tabbedPane.addTab("Gest\u00E3o de Feriados", null, Ges_Fer, null);
		Ges_Fer.setLayout(null);
		
		JLabel lbl_date = new JLabel("Data do Feriado");
		lbl_date.setBounds(70, 11, 89, 14);
		Ges_Fer.add(lbl_date);
		
		desc_feriado = new JTextField();
		desc_feriado.setBounds(203, 26, 315, 20);
		Ges_Fer.add(desc_feriado);
		desc_feriado.setColumns(10);
		
		dta_feriado = new JDateChooser();
		dta_feriado.setBounds(43, 26, 150, 20);
		Ges_Fer.add(dta_feriado);
		
		JButton button_18 = new JButton("");
		button_18.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					Insere_Feriado();
					prencheFeriados();
				} catch (SQLException e1)
				{
					e1.printStackTrace();
				}
			}
		});
		button_18.setIcon(new ImageIcon("C:\\Users\\Rui Pereira\\Documents\\Icons_Geral\\Icons\\action_add.gif"));
		button_18.setToolTipText("Inserir Utilizador");
		button_18.setBounds(528, 18, 27, 28);
		Ges_Fer.add(button_18);
		
		JLabel lbldescFe = new JLabel("Descri\u00E7\u00E3o do Feriado");
		lbldescFe.setBounds(304, 11, 126, 14);
		Ges_Fer.add(lbldescFe);
		
		JScrollPane scrollPane_5 = new JScrollPane();
		Ges_Fer.add(scrollPane_5);
		
		table_8 = new JTable();
		table_8.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				seleciona_linha_fer();
			}
		});
		scrollPane_5.setBounds(43, 65, 475, 200);
		scrollPane_5.setViewportView(table_8);
		
		JButton button_19 = new JButton("");
		button_19.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				atualiza_feriados();
			}
		});
		button_19.setIcon(new ImageIcon("C:\\Users\\Rui Pereira\\Documents\\Icons_Geral\\Icons\\affldsav.gif"));
		button_19.setToolTipText("Ativar Utilizador");
		button_19.setBounds(528, 65, 27, 28);
		Ges_Fer.add(button_19);
		
		JLabel lblNewLabel_13 = new JLabel("Feriado:");
		lblNewLabel_13.setBounds(43, 278, 46, 17);
		Ges_Fer.add(lblNewLabel_13);
		
		Codfed = new JTextField();
		Codfed.setBounds(0, 0, 0, 0);
		Ges_Fer.add(Codfed);
		Codfed.setColumns(10);
		
		Dta_fed = new JTextField();
		Dta_fed.setBounds(99, 276, 70, 20);
		Ges_Fer.add(Dta_fed);
		Dta_fed.setColumns(10);
		
		Desc_Fed = new JTextField();
		Desc_Fed.setBounds(179, 276, 170, 20);
		Ges_Fer.add(Desc_Fed);
		Desc_Fed.setColumns(10);
	}
	public static java.sql.Date convertUtilDateToSqlDate(java.util.Date date)
	{
	    if(date != null) 
	    {
	        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
	        return sqlDate;
	    }
	    return null;
	}
	public void upload_imagem_upd()
	{
		JFileChooser chooser=new JFileChooser();
		chooser.showOpenDialog(null);
		File f =chooser.getSelectedFile();
		lblimg2.setIcon(new ImageIcon(f.toString()));
		filename=f.getAbsolutePath();
				try 
				{
					File image = new File(filename);
					FileInputStream  fis = new FileInputStream(image);
					ByteArrayOutputStream bos=new ByteArrayOutputStream();
					byte[] buf = new byte[1024];
					for(int readNum; (readNum=fis.read(buf))!=-1;)
					   {
					       bos.write(buf,0,readNum);
					   }
					IMAGEM=bos.toByteArray();
				} 
				catch (IOException e1)
				{
					JOptionPane.showMessageDialog(null, "Erro ao Pegar Imagem!"+e1);
				}
	}
	public void upload_imagem()
	{
		JFileChooser chooser=new JFileChooser();
		chooser.showOpenDialog(null);
		File f =chooser.getSelectedFile();
		Jlnome.setIcon(new ImageIcon(f.toString()));
		filename=f.getAbsolutePath();
		img.setText(filename);
				try 
				{
					File image = new File(filename);
					FileInputStream fis=new FileInputStream(image);
					ByteArrayOutputStream bos=new ByteArrayOutputStream();
					byte[] buf = new byte[1024];
					for(int readNum; (readNum=fis.read(buf))!=-1;)
					   {
					       bos.write(buf,0,readNum);
					   }
					IMAGEM=bos.toByteArray();
				} 
				catch (IOException e1)
				{
					JOptionPane.showMessageDialog(null, "Erro ao Pegar Imagem!"+e1);
				}
	}

	public void atualiza_feriados()
	{
		/* int count = table_8.getRowCount();
		    Feriado Fe = new Feriado();
			String getCodf = Fe.getCodf();
			String getDta_feriado = Fe.getDta_feriado();
			String getDesc_fe = Fe.getDesc_fe();
		    String Dta_Fe[] = new String[count];Dta_Fe[i] = table_8.getValueAt(i,1).toString();
		   for (int i = 0; i<count; i++)
		    {
			     if(table_8.getValueAt(i, 0) != null) 
			     {	 
			        getCodf = table_8.getValueAt(i,0).toString();
			     }
			     else
			     {
			    	getCodf = null; 
			     }
			     if(table_8.getValueAt(i, 1) != null) 
			     {	 
				    getDta_feriado = table_8.getValueAt(i,1).toString();
			     }
			     else
			     {
			    	getDta_feriado = null; 
			     }
			     if(table_8.getValueAt(i, 2) != null) 
			     {	 
					getDesc_fe = table_8.getValueAt(i,2).toString();
			     }
			     else
			     {
			    	getDesc_fe = null; 
			     }*/
		if((Codfed.getText().length()==0)||(Desc_Fed.getText().length()==0)||(Dta_fed.getText().length()==0))
    	{
    	 JOptionPane.showMessageDialog(null,"Nenhum Registo Selecionado!");
    	}
    	else
    	{	
	            try 
	            {
	            	String sql="update siar.siar_feriado set siar.siar_feriado.Dta_Feriado='"+Dta_fed.getText()+"',siar.siar_feriado.desc_feriado='"+Desc_Fed.getText()+"' where to_char(siar.siar_feriado.cod_feriado)='"+Codfed.getText()+"'";
	            	pstatu_fe=conn_atu_fe.prepareStatement(sql);
	            	pstatu_fe.execute();
		        } 
		         catch (Exception e)
		        {
		        	 JOptionPane.showMessageDialog(null, "Erro ao actualizar data de feriado!"+e);
		        }  
 		    }
            JOptionPane.showMessageDialog(null, "updated"); 
	        prencheFeriados();
	        
	}
	public void prencheUtilizadores()
	{
		MyQuery mq = new MyQuery();
		ArrayList<Product> list = mq.BindTable();
		String[] columnName = {"Núm.Mec.","Nome do Utilizador","Senha","Dta.Desativo","Email","Imagem"}; 
		Object [][] rows = new Object[list.size()][6];
		for(int i = 0; i < list.size(); i++)
			{
				rows[i][0] = list.get(i).getNmec();
				rows[i][1] = list.get(i).getN_uti();
				rows[i][2] = list.get(i).getSenha();
				if(list.get(i).getDta_desativo() != null)
				{	
				  rows[i][3] = list.get(i).getDta_desativo();
				}
				else
				{
				  rows[i][3] = null;	
				}
				if(list.get(i).getEmail() != null)
				{	
				  rows[i][4] = list.get(i).getEmail();
				}
				else
				{
				  rows[i][4] = null;	
				}
				
				if(list.get(i).getMyImage() != null)
				{	
					ImageIcon image = new ImageIcon(new ImageIcon(list.get(i).getMyImage())
					.getImage().getScaledInstance(90, 30, Image.SCALE_SMOOTH));			
					rows[i][5] = image;
				}
				else
				{
					rows[i][5] = null;
				}
			}
		TheModel model = new TheModel(rows, columnName);
		table_2.setModel(model);
		table_2.setRowHeight(30);
		table_2.getColumnModel().getColumn(0).setPreferredWidth(25);
		table_2.getColumnModel().getColumn(1).setPreferredWidth(170);
		table_2.getColumnModel().getColumn(2).setPreferredWidth(60);
		table_2.getColumnModel().getColumn(3).setPreferredWidth(35);
		table_2.getColumnModel().getColumn(4).setPreferredWidth(100);
		table_2.getColumnModel().getColumn(5).setPreferredWidth(80);
		table_2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table_2.getTableHeader().setReorderingAllowed(false);
	}
	public void prencheFeriados()
	{
		JTextField Jcolzero = new JTextField();
		JTextField Jcolum = new JTextField();
		JTextField Jcoldois = new JTextField();
     try
      {
    	 String sql="select siar.siar_feriado.cod_feriado codfe,siar.siar_feriado.dta_feriado dtafe,siar.siar_feriado.desc_feriado descfe from siar.siar_feriado order by cod_feriado";
    	 pst_feriado=conn_feriado.prepareStatement(sql); 
    	 resferiado=pst_feriado.executeQuery();
 		 do
 		 {	
 		  table_8.setModel(DbUtils.resultSetToTableModel(resferiado));
   	      TableColumn sportColumnzero = table_8.getColumnModel().getColumn(0);
   	      sportColumnzero.setCellEditor(new DefaultCellEditor(Jcolzero));
   	      table_8.getColumnModel().getColumn(0).setResizable(false);
   	      table_8.getColumnModel().getColumn(0).setPreferredWidth(25);
   	      table_8.getColumnModel().getColumn(0).setHeaderValue("Código Feriado");
  	      TableColumn sportColumnum = table_8.getColumnModel().getColumn(1);
   	      sportColumnum.setCellEditor(new DefaultCellEditor(Jcolum));
    	  table_8.getColumnModel().getColumn(1).setResizable(false);
   	      table_8.getColumnModel().getColumn(1).setPreferredWidth(60);
   	      table_8.getColumnModel().getColumn(1).setHeaderValue("Data do Feriado");
  	      TableColumn sportColumdois = table_8.getColumnModel().getColumn(2);
   	      sportColumdois.setCellEditor(new DefaultCellEditor(Jcoldois));
   	      table_8.getColumnModel().getColumn(2).setResizable(false);
   	      table_8.getColumnModel().getColumn(2).setPreferredWidth(170);
   	      table_8.getColumnModel().getColumn(2).setHeaderValue("Descrição do Feriado");
         } while(resferiado.next());
          DefaultTableModel modelo = (DefaultTableModel)table_8.getModel();
          modelo.setNumRows(30);
          table_8.getTableHeader().setReorderingAllowed(false);
   	      Jcolzero.setEditable(false);
   	      Jcolum.setEditable(true);
   	      Jcoldois.setEditable(true);
   	      table_8.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
   	      table_8.changeSelection(0, 0, false, false);
      }
         catch(Exception e)
         {
        	 JOptionPane.showMessageDialog(null, "Erro ao Listar na Tabela2!"+e);
         }
    }
	public void prencherRefeicao()
	{
		JTextField Jcolzero = new JTextField();
		JTextField Jcolum = new JTextField();
		JTextField Jcoldois = new JTextField();
     try
      {
    	 String sql="select siar.siar_refeicao.cod_refeicao codref,siar.siar_refeicao.desc_refeicao desre,siar.siar_refeicao.dta_registo dtare from siar.siar_refeicao order by cod_refeicao";
    	 pstatu_ref=conn_atu_ref.prepareStatement(sql); 
    	 rsref=pstatu_ref.executeQuery();
 		 do
 		 {	
 		  table_3.setModel(DbUtils.resultSetToTableModel(rsref));
   	      TableColumn sportColumnzero = table_3.getColumnModel().getColumn(0);
   	      sportColumnzero.setCellEditor(new DefaultCellEditor(Jcolzero));
   	      table_3.getColumnModel().getColumn(0).setResizable(false);
   	      table_3.getColumnModel().getColumn(0).setPreferredWidth(25);
   	      table_3.getColumnModel().getColumn(0).setHeaderValue("Código");
  	      TableColumn sportColumnum = table_3.getColumnModel().getColumn(1);
   	      sportColumnum.setCellEditor(new DefaultCellEditor(Jcolum));
    	  table_3.getColumnModel().getColumn(1).setResizable(false);
   	      table_3.getColumnModel().getColumn(1).setPreferredWidth(60);
   	      table_3.getColumnModel().getColumn(1).setHeaderValue("Descrição");
  	      TableColumn sportColumdois = table_3.getColumnModel().getColumn(2);
   	      sportColumdois.setCellEditor(new DefaultCellEditor(Jcoldois));
   	      table_3.getColumnModel().getColumn(2).setResizable(false);
   	      table_3.getColumnModel().getColumn(2).setPreferredWidth(170);
   	      table_3.getColumnModel().getColumn(2).setHeaderValue("Data da Refeicão");
         } while(rsref.next());
          DefaultTableModel modelo = (DefaultTableModel)table_3.getModel();
          modelo.setNumRows(30);
          table_3.getTableHeader().setReorderingAllowed(false);
   	      Jcolzero.setEditable(false);
   	      Jcolum.setEditable(true);
   	      Jcoldois.setEditable(true);
   	      table_3.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
   	      table_3.changeSelection(0, 0, false, false);
      }
         catch(Exception e)
         {
        	 JOptionPane.showMessageDialog(null, "Erro ao Listar as refeições!"+e);
         }
    }
	public void pesq_utilizador()
	{
		MyQuery mq = new MyQuery();
		ArrayList<Product> list = mq.pesq_utilizador(Nome_Uti.getText());
		String[] columnName = {"Número Mec.","Nome do Utilizador","Senha","Dta.Desativo","Email","Imagem"}; 
		Object [][] rows = new Object[list.size()][6];
			for(int i = 0; i < list.size(); i++)
			{
				rows[i][0] = list.get(i).getNmec();
				rows[i][1] = list.get(i).getN_uti();
				rows[i][2] = list.get(i).getSenha();
				if(list.get(i).getDta_desativo() != null)
				{	
				  rows[i][3] = list.get(i).getDta_desativo();
				}
				else
				{
				  rows[i][3] = null;	
				}
				if(list.get(i).getEmail() != null)
				{	
				  rows[i][4] = list.get(i).getEmail();
				}
				else
				{
				  rows[i][4] = null;	
				}
				
				if(list.get(i).getMyImage() != null)
				{	
					ImageIcon image = new ImageIcon(new ImageIcon(list.get(i).getMyImage())
					.getImage().getScaledInstance(90, 30, Image.SCALE_SMOOTH));			
					rows[i][5] = image;
				}
				else
				{
					rows[i][5] = null;
				}
			}
			TheModel model = new TheModel(rows, columnName);
			table_2.setModel(model);
			table_2.setRowHeight(30);
			table_2.getColumnModel().getColumn(0).setPreferredWidth(25);
			table_2.getColumnModel().getColumn(1).setPreferredWidth(170);
			table_2.getColumnModel().getColumn(2).setPreferredWidth(60);
			table_2.getColumnModel().getColumn(3).setPreferredWidth(35);
			table_2.getColumnModel().getColumn(4).setPreferredWidth(100);
			table_2.getColumnModel().getColumn(5).setPreferredWidth(80);
			table_2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			table_2.getTableHeader().setReorderingAllowed(false);
	}
	public void desativa_uti()
    {
		if((nmec_aux.getText().length()==0)||(nome_aux.getText().length()==0))
    	{
    	 JOptionPane.showMessageDialog(null,"Nenhum Registo Selecionado!");
    	}
    	else
    	{		
		   try
		     {
     			 String sql="update siar.siar_utilizadores set dta_desativo = sysdate, activo = 'N' where siar.siar_utilizadores.num_mecanog='"+nmec_aux.getText()+"'";
		    	 pst_at=conn_ativa.prepareStatement(sql); 
		    	 rs_at=pst_at.executeQuery();
		    	 prencheUtilizadores();
		     }
	         catch(Exception e)
	         {
	        	 JOptionPane.showMessageDialog(null, "Erro ao desativar utilizador!"+e);
	         }
    	}
     }
	public void ativa_desat() throws SQLException
    {
		pst_ati_des = conn_pst_ati_des.prepareCall(ATI_DES_UTILIZADOR);
		pst_ati_des.setString(1,strDate);
		pst_ati_des.registerOutParameter(2, Types.VARCHAR);
		pst_ati_des.execute();
    }
	public void ativa_uti()
    {
    	if((nmec_aux.getText().length()==0)||(nome_aux.getText().length()==0))
    	{
    	 JOptionPane.showMessageDialog(null,"Nenhum Registo Selecionado!");
    	}
    	else
    	{
			try
		      {
		    	 String sql="update siar.siar_utilizadores set dta_desativo = null, activo = 'S' where siar.siar_utilizadores.num_mecanog='"+nmec_aux.getText()+"'";
		    	 pst_des = conn_des.prepareStatement(sql); 
		    	 rs_des=pst_des.executeQuery();
		    	 prencheUtilizadores();
		      }
	         catch(Exception e)
	         {
	        	 JOptionPane.showMessageDialog(null, "Erro ao desativar utilizador!"+e);
	         }
    	}
      }
	public void atua_seq()
    {
		try
	      {
	    	 String sql="select siar.GERA_NMEC_SEQ.nextval as seguinte from dual";
	    	 pst_des = conn_des.prepareStatement(sql); 
	    	 rs_des=pst_des.executeQuery();
	           if(rs_des.next())
	            {
		        	 ad1 =rs_des.getString("seguinte");
	            }
	      }
         catch(Exception e)
         {
        	 JOptionPane.showMessageDialog(null, "Erro ao atualizar Sequência!"+e);
         }
      }
	public void atua_seq_feriado()
    {
		try
	      {
	    	 String sql="select siar.SIAR_S_FERIADO.nextval as seguinte_fe from dual";
	    	 pst_seqfe = conn_seqfe.prepareStatement(sql); 
	    	 resseqfe=pst_seqfe.executeQuery();
	           if(resseqfe.next())
	            {
	        	   ad_feriado =resseqfe.getString("seguinte_fe");
	            }
	      }
         catch(Exception e)
         {
        	 JOptionPane.showMessageDialog(null, "Erro ao atualizar Sequência feriado!"+e);
         }
      }
	public void atua_seq_refeicao()
    {
		try
	      {
	    	 String sql="select siar.SIAR_S_refeicao.nextval as seguinte_ref from dual";
	    	 pst_seqref = conn_seqref.prepareStatement(sql); 
	    	 resseqref=pst_seqref.executeQuery();
	           if(resseqref.next())
	            {
	        	   ad_refeicao =resseqref.getString("seguinte_ref");
	            }
	      }
         catch(Exception e)
         {
        	 JOptionPane.showMessageDialog(null, "Erro ao atualizar Sequência refeicao!"+e);
         }
      }
	public void Insere_Utilizador() throws SQLException
	{
		String email_Pattern = "^[a-zA-Z0-9_+&*-]+(?:\\."+  
				               "[a-zA-Z0-9_+&*-]+)*@" + 
				               "(?:[a-zA-Z0-9-]+\\.)+[a-z"+ 
				               "A-Z]{2,7}$";      
        Pattern pattern = Pattern.compile(email_Pattern);
		Matcher regexMaster = pattern.matcher(Email.getText());
		if((Nome_Uti.getText().length()==0))
    	{
    	 JOptionPane.showMessageDialog(null,"Deve Introduzir o Nome!");
    	}
    	if((Email.getText().length()==0))
    	{
    	 JOptionPane.showMessageDialog(null,"Deve Introduzir o Email!");
    	}
		if((img.getText().length()==0))
		{
			JOptionPane.showMessageDialog(null,"Deve Introduzir a Imagem!");	
		}
		else if((!regexMaster.matches())) 
		{
		    JOptionPane.showMessageDialog(null, "Formato email "+Email.getText()+", inválido!");
		}
    	else
    	{	
			CallableStatement cstmt = conn_insuti.prepareCall("BEGIN ? := siar.GERA_PASS_USER_NOME(?); END;");
			String sql="insert into siar.siar_utilizadores(Num_mecanog,Nome_Utilizador,Senha,Dta_Desativo,Creation_Date,Email,Imagem_nome,Imagem)values(?,?,?,?,?,?,?,?)"; 
	        pstinsuti=conn_insuti.prepareStatement(sql);
	        atua_seq();
	        pstinsuti.setString(1, ad1);
	        pstinsuti.setString(2,Nome_Uti.getText());
	        cstmt.registerOutParameter(1,Types.VARCHAR);
	        cstmt.setString(2,Nome_Uti.getText());
	        cstmt.execute();
	        String output = cstmt.getString(1);
	        Senha.setText(output);
	        pstinsuti.setString(3, output);
	        pstinsuti.setDate(4, null); 
	        pstinsuti.setDate(5,now);
	        pstinsuti.setString(6,Email.getText());
	        pstinsuti.setString(7,img.getText());
	        pstinsuti.setBytes(8,IMAGEM);
	        pstinsuti.executeQuery();
	        JOptionPane.showMessageDialog(null,"Dados Inseridos com Sucesso!");
	        Nome_Uti.setText(null);
	        Senha.setText(null);
	        Email.setText(null);
	        Jlnome.setIcon(new ImageIcon());
    	}
    }  
	public void Insere_Feriado() throws SQLException
	{
        if(((JTextField)dta_feriado.getDateEditor().getUiComponent()).getText().isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Deve Inserir a Data do Feriado!");
            ((JTextField)dta_feriado.getDateEditor().getUiComponent()).requestFocus();
            return;
        }
    	if((desc_feriado.getText().length()==0))
    	{
    	 JOptionPane.showMessageDialog(null,"Deve Introduzir a descrição!");
    	}
    	else
    	{	
    		String dataobtida = df2.format(dta_feriado.getDate());
    		String sql="insert into siar.siar_Feriado(cod_feriado,dta_feriado,desc_feriado)values(?,?,?)"; 
			pst_insfe =conn_confe.prepareStatement(sql);
	        atua_seq_feriado();
	        pst_insfe.setString(1,ad_feriado);
	        pst_insfe.setString(2,dataobtida);
	        pst_insfe.setString(3,desc_feriado.getText());
	        pst_insfe.executeQuery();
	        JOptionPane.showMessageDialog(null,"Dados Inseridos com Sucesso!");
	        dta_feriado.setDate(null);
	        desc_feriado.setText(null);
    	}
    }  
	public void Insere_Refeicao() throws SQLException
	{
        if(((JTextField)DC_ref.getDateEditor().getUiComponent()).getText().isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Deve Inserir a Data da Refeição!");
            ((JTextField)DC_ref.getDateEditor().getUiComponent()).requestFocus();
            return;
        }
    	if((Desc_Ref.getText().length()==0))
    	{
    	 JOptionPane.showMessageDialog(null,"Deve Introduzir a Descrição da Refeição!");
    	}
    	else
    	{	
    		String sql="insert into siar.siar_refeicao(cod_refeicao,desc_refeicao,dta_registo)values(?,?,?)"; 
			pst_insref=conn_conref.prepareStatement(sql);
	        atua_seq_refeicao();
	        pst_insref.setString(1,ad_refeicao);
	        pst_insref.setString(2,Desc_Ref.getText());
	        pst_insref.setDate(3,convertUtilDateToSqlDate(DC_ref.getDate()));
	        pst_insref.executeQuery();
	        JOptionPane.showMessageDialog(null,"Refeição Inserida com Sucesso!");
	        DC_ref.setDate(null);
	        Desc_Ref.setText(null);
    	}
    }  
	public void seleciona_linha()
	{
		int row = table_2.getSelectedRow();
		if (row >= 0)	
		{	
		try
		 {
					String clica_tabela =(table_2.getModel().getValueAt(row, 0).toString());
    				ImageIcon image1 = (ImageIcon)table_2.getValueAt(row, 5);
		            String conta="select siar.siar_utilizadores.Num_Mecanog Num_Mecanog,siar.siar_utilizadores.nome_utilizador Nome_Utilizador,siar.siar_utilizadores.senha pass, Dta_Desativo dta_des,siar.siar_utilizadores.email email,siar.siar_utilizadores.imagem from siar.siar_utilizadores "
		            		+ " where siar.siar_utilizadores.num_mecanog='"+clica_tabela+"'";
		            pst_sel=conn_sel.prepareStatement(conta);
					rs_sel =pst_sel.executeQuery();
			           if(rs_sel.next())
			            {
  			        	    String ad1 =rs_sel.getString("Num_Mecanog");
			            	nmec_aux.setText(ad1);
  			        	    String ad2 =rs_sel.getString("Nome_Utilizador");
  			        	    nome_aux.setText(ad2);
  			        	    if(table_2.getModel().getValueAt(row, 4)!= null)
  			        	    {
  	  			        	    String ad3 =rs_sel.getString("email");
  	  			        	    email_aux.setText(ad3);
  			        	    }
  			        	    else
  			        	    {
  			        	    	email_aux.setText("");
  			        	    }
  							if(table_2.getValueAt(row, 5)!= null)
  							{
	  							Image image2 = image1.getImage().getScaledInstance(Jlnome.getWidth(),Jlnome.getHeight(),Image.SCALE_SMOOTH);
	  							ImageIcon image3 = new ImageIcon(image2);
	  							lblimg2.setIcon(image3);
  							}
  							else
  							{
  								lblimg2.setIcon(new ImageIcon());
  							}
			            }
			}
			catch(Exception e)
			{
				nmec_aux.setText("");
				nome_aux.setText("");
				email_aux.setText("");
				JOptionPane.showMessageDialog(null,e+"erro ao selecionar");
			}
          }
	}
	public void seleciona_linha_ref()
	{
		int row = table_3.getSelectedRow();
		if (row >= 0)	
		{	
		try
		 {
					String clica_tabela =(table_3.getModel().getValueAt(row, 0)).toString();
					String clica_nome =(table_3.getModel().getValueAt(row, 1)).toString();
					String dta_reg = (table_3.getModel().getValueAt(row, 2)).toString();
		            String conta="select cod_refeicao, desc_refeicao, dta_registo from siar.siar_refeicao where siar.siar_refeicao.cod_refeicao='"+clica_tabela+"'";
		            pst_sel_ref=conn_sel_ref.prepareStatement(conta);
					rs_sel_ref =pst_sel_ref.executeQuery();
			           if(rs_sel_ref.next())
			            {
  			        	    String ad1 =rs_sel_ref.getString("cod_refeicao");
  			        	    cod_aux.setText(ad1);
  			        	    String ad2 =clica_nome;
  			        	    ref_aux.setText(ad2);
  			        	    String ad3 =dta_reg;
  			        	    dta_ref_aux.setText(ad3);
			            }
			}
			catch(Exception e)
			{
				cod_aux.setText(null);
				ref_aux.setText(null);
				dta_ref_aux.setText(null);
			}
          }
	}
	public void seleciona_linha_fer()
	{
		int row = table_8.getSelectedRow();
		if (row >= 0)	
		{	
		try
		 {
					String clica_tabela =(table_8.getModel().getValueAt(row, 0)).toString();
					String clica_nome =(table_8.getModel().getValueAt(row, 2)).toString();
					String dta_reg = (table_8.getModel().getValueAt(row, 1)).toString();
		            String conta="select cod_feriado, Dta_Feriado, desc_feriado from siar.siar_feriado where siar.siar_feriado.cod_feriado='"+clica_tabela+"'";
					pst_sel_ref=conn_sel_ref.prepareStatement(conta);
					rs_sel_ref =pst_sel_ref.executeQuery();
			           if(rs_sel_ref.next())
			            {
  			        	    String ad1 =rs_sel_ref.getString("cod_feriado");
  			        	    Codfed.setText(ad1);
  			        	    String ad2 =dta_reg;
  			        	    Dta_fed.setText(ad2);
  			        	    String ad3 =clica_nome;
  			        	    Desc_Fed.setText(ad3);
			            }
			}
			catch(Exception e)
			{
				Codfed.setText(null);
				Dta_fed.setText(null);
				Desc_Fed.setText(null);
			}
          }
	}
	public void remove_refeicao()
	{
		/*String des_horalimite = CH.check_holiday(41);*/
    	if((cod_aux.getText().length()==0) || (ref_aux.getText().length()==0) || (dta_ref_aux.getText().length()==0))
    	{
    	 JOptionPane.showMessageDialog(null,"Nenhum Registo Selecionado!");
    	}
     	else
    	{
	        int p = JOptionPane.showConfirmDialog(null, "Deseja Realmente Remover a Refeição!","Refeição Removida!",JOptionPane.YES_NO_OPTION);
	        if(p==0)
	        {
		          try{
			        	String sql="delete from siar.siar_refeicao where cod_refeicao='"+cod_aux.getText()+"'"; 
			        	pstatu_ref=conn_conref.prepareStatement(sql);
			        	pstatu_ref.executeQuery();
			          }
				       catch(Exception e2)
				      {
				           JOptionPane.showMessageDialog(null,e2); 
				      }
					   prencherRefeicao();
					   seleciona_linha_ref();
	        	}	       
	        } 
    }   
	public void atualiza_uti()
	{
		String email_Pattern = "^[a-zA-Z0-9_+&*-]+(?:\\."+  
	               "[a-zA-Z0-9_+&*-]+)*@" + 
	               "(?:[a-zA-Z0-9-]+\\.)+[a-z"+ 
	               "A-Z]{2,7}$";  
		Pattern pattern = Pattern.compile(email_Pattern);
		Matcher regexMaster = pattern.matcher(email_aux.getText());
		if((nmec_aux.getText().length()==0)||(nome_aux.getText().length()==0))
    	{
    	    JOptionPane.showMessageDialog(null,"Nenhum Registo Selecionado!");
    	}
		else if((!regexMaster.matches())) 
		{
		    JOptionPane.showMessageDialog(null, "Formato email "+email_aux.getText()+", inválido!");
		} 
		else
		{		
		   try
		     {
			   String sql="update siar.siar_utilizadores set nome_utilizador = ?,email= ? ,imagem_nome= ?,imagem= ? where siar.siar_utilizadores.num_mecanog='"+nmec_aux.getText()+"'"; 
		       pst_upd=conn_upd.prepareStatement(sql); 
		       pst_upd.setString(1,nome_aux.getText());
		       pst_upd.setString(2,email_aux.getText());
		       pst_upd.setString(3,img.getText());
		       pst_upd.setBytes(4,IMAGEM);	 
		       resupd=pst_upd.executeQuery();
		       prencheUtilizadores();
		       Jlnome.setIcon(new ImageIcon());
		     }
	         catch(Exception e)
	         {
	        	 JOptionPane.showMessageDialog(null, "Erro ao atualizar informação do utilizador!"+e);
	         }
    	}
	}
}
