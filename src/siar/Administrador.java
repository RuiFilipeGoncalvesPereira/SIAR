package siar;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;


import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import com.toedter.calendar.JDateChooser;

import Check_Validation.Valida_Senha;
import dados_auxiliares.GetName;

public class Administrador extends JFrame {

	private static final long serialVersionUID = 1L;
	public static JLabel lblnum, lblNome;
	private JPanel contentPane;
	private JLabel lblDesc;
	public static JTable table;
	public static JTextField Nome_Uti;
	public static JTextField Senha;
	private JTable table_1;
	public static JTextField Desc_Ref;
	public static JTable table_3;
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
	public static JButton button_3;
	public static JButton button_4;
	public static JTable table_2;
	public static JTextField nmec_aux;
	public static JTextField nome_aux;
	public static JTextField email_aux;

	 
	public static JLabel Jlnome;
	public static JLabel lblimg2;
    java.util.Date data_hoje = new java.util.Date();
    java.sql.Date sqlDate = new java.sql.Date(data_hoje.getTime());
	Valida_Senha VS = new Valida_Senha();
	public static JTextField Email;
	public static JTextField img;
	public static JTextField desc_feriado;
	public static JTable table_8;
	
	public static JTextField cod_aux;
	public static JTextField ref_aux;
	public static JTextField dta_ref_aux;
	public static JTextField Codfed;
	public static JTextField Dta_fed;
	public static JTextField Desc_Fed;
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


	public Administrador() {
		setAutoRequestFocus(false);
		initialize();
		parametros.utilizadores.prencheUtilizadores();
		parametros.feriados.prencher_Feriados();
		parametros.refeicoes.prencher_Refeicoes();
		parametros.parametros.prenche_parametros();
		parametros.pratos.prencher_pratos();
		parametros.dominios.prenche_dominios();
		GetName Gn = new GetName();
		@SuppressWarnings("static-access")
		String nome = Gn.GETNAME(Integer.parseInt(Login.txtUser.getText()));
		Administrador.lblNome.setText(nome);
		lblnum.setVisible(false);
		DC_ref.setDate(sqlDate);
		DC_prato.setDate(sqlDate);
		button_3.setVisible(true);
		button_4.setVisible(false);
	}
	private void initialize() 
	{
		setTitle("Menu de Administração");
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
		lblNome.setBounds(636, 0, 348, 14);
		contentPane.add(lblNome);
		
		JButton button = new JButton("");
		button.addActionListener(new ActionListener()
		{
			@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent e)
			{
	                dispose();
	        		Login window = new Login();
					window.frame.setVisible(true);
			}
		}
		);
		button.setIcon(new ImageIcon("C:\\Icons_Geral\\Icons\\001_20.gif"));
		button.setBounds(10, 11, 43, 36);
		contentPane.add(button);
		
		lblDesc = new JLabel("Utilizador:");
		lblDesc.setBounds(563, 0, 66, 14);
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
		button_1.setIcon(new ImageIcon("C:\\Icons_Geral\\Icons\\001_44.gif"));
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
		tabbedPane.addTab("Gestão de Utilizadores", null, Ges_Uti, null);
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
		
		button_3 = new JButton("");
		button_3.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				parametros.utilizadores.pesq_utilizador();
			}
		});
		button_3.setToolTipText("Pesquisar Utilizador");
		button_3.setIcon(new ImageIcon("C:\\Icons_Geral\\Icons\\image275.gif"));
		button_3.setBounds(820, 58, 27, 28);
		Ges_Uti.add(button_3);

		
		button_4 = new JButton("");
		button_4.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				parametros.utilizadores.prencheUtilizadores();
			}
		});
		button_4.setToolTipText("Ver Todos  os Utilizadores");
		button_4.setIcon(new ImageIcon("C:\\Users\\Rui Pereira\\Documents\\Icons_Geral\\Icons\\image274.gif"));
		button_4.setBounds(820, 58, 27, 28);
		Ges_Uti.add(button_4);
	
		
		JButton button_5 = new JButton("");
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
					parametros.utilizadores.Insere_Utilizador();
					parametros.utilizadores.prencheUtilizadores();
				} 
				catch (SQLException e1)
				{
					e1.printStackTrace();
				}
			}
		});
		button_5.setToolTipText("Inserir Utilizador");
		button_5.setIcon(new ImageIcon("C:\\Icons_Geral\\Icons\\action_add.gif"));

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
					parametros.utilizadores.ativa_desat();
					String mess = parametros.utilizadores.pst_ati_des.getString(2);
				    if(mess == null)
				    {	
				    	parametros.utilizadores.ativa_uti();
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
		button_6.setIcon(new ImageIcon("C:\\Icons_Geral\\Icons\\001_06.gif"));
		button_6.setToolTipText("Ativar Utilizador");
		button_6.setBounds(804, 197, 27, 28);
		Ges_Uti.add(button_6);
		
		JButton button_7 = new JButton("");
		button_7.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				try
				{
					parametros.utilizadores.ativa_desat();
					String mess = parametros.utilizadores.pst_ati_des.getString(2);
				    if(mess == null)
				    {	
				    	parametros.utilizadores.desativa_uti();
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
		button_7.setIcon(new ImageIcon("C:\\Icons_Geral\\Icons\\001_05.gif"));
		button_7.setToolTipText("Desativar Utilizador");
		button_7.setBounds(804, 225, 27, 28);
		Ges_Uti.add(button_7);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 68, 784, 286);
		Ges_Uti.add(scrollPane);
		
		table_2 = new JTable();
		table_2.addMouseListener(new MouseAdapter()
		{
			public void mousePressed(MouseEvent e) {
				parametros.utilizadores.seleciona_linha();	
			}
		});
		scrollPane.setViewportView(table_2);
		
		JButton button_16 = new JButton("");
		button_16.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				parametros.utilizadores.atualiza_uti();
			}
		});
		button_16.setIcon(new ImageIcon("C:\\Icons_Geral\\Icons\\affldsav.gif"));
		button_16.setToolTipText("Ativar Utilizador");
		button_16.setBounds(804, 253, 27, 28);
		Ges_Uti.add(button_16);
		
		nmec_aux = new JTextField();
		nmec_aux.setBounds(86, 373, 80, 20);
		Ges_Uti.add(nmec_aux);
		nmec_aux.setEditable(false);
		nmec_aux.setColumns(10);
		
		nome_aux = new JTextField();
		nome_aux.setBounds(176, 373, 361, 20);
		Ges_Uti.add(nome_aux);
		nome_aux.setColumns(10);
		
		email_aux = new JTextField();
		email_aux.setBounds(547, 373, 215, 20);
		Ges_Uti.add(email_aux);
		email_aux.setColumns(10);
		
		JLabel lblNMec = new JLabel("Número Mec");
		lblNMec.setHorizontalAlignment(SwingConstants.CENTER);
		lblNMec.setBounds(86, 358, 80, 14);
		Ges_Uti.add(lblNMec);
		
		JLabel label = new JLabel("Nome do Utilizador");
		label.setBounds(259, 358, 161, 14);
		Ges_Uti.add(label);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmail.setBounds(620, 358, 46, 14);
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
		btnNewButton.setToolTipText("Upload Image");
		btnNewButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				parametros.utilizadores.upload_imagem();
			}
		});
		btnNewButton.setIcon(new ImageIcon("C:\\Icons_Geral\\Icons\\pt_files.png"));
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
				parametros.utilizadores.upload_imagem_upd();
			}
		});
		button_17.setIcon(new ImageIcon("C:\\Icons_Geral\\Icons\\folder_files.gif"));
		button_17.setBounds(767, 365, 27, 28);
		Ges_Uti.add(button_17);
		
	
		JPanel Ges_Ref = new JPanel();
		tabbedPane.addTab("Gestão de Refeições", null, Ges_Ref, null);
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
					parametros.refeicoes.Insere_Refeicao();
					parametros.refeicoes.prencher_Refeicoes();
				} 
				catch (SQLException e1)
				{
					e1.printStackTrace();
				}
			}
		});
		button_8.setIcon(new ImageIcon("C:\\Icons_Geral\\Icons\\action_add.gif"));
		button_8.setToolTipText("Inserir Refeição");
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
				parametros.refeicoes.seleciona_linha_ref();	
			}
		});
		scrollPane_1.setViewportView(table_3);
		
		JLabel lblNewLabel_3 = new JLabel("Refeição");
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
				parametros.refeicoes.remove_refeicao();
			}
		});
		button_9.setIcon(new ImageIcon("C:\\Icons_Geral\\Icons\\001_05.gif"));
		button_9.setToolTipText("Desativar Refeição");
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
		tabbedPane.addTab("Gestão de Pratos", null, Ges_Pr, null);
		Ges_Pr.setLayout(null);
		
		Desc_Prato = new JTextField();
		Desc_Prato.setColumns(10);
		Desc_Prato.setBounds(88, 26, 153, 20);
		Ges_Pr.add(Desc_Prato);
		
		JButton button_10 = new JButton("");
		button_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					parametros.pratos.Insere_prato();
					parametros.pratos.prencher_pratos();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		button_10.setIcon(new ImageIcon("C:\\Icons_Geral\\Icons\\action_add.gif"));
		button_10.setToolTipText("Inserir Utilizador");
		button_10.setBounds(412, 18, 27, 28);
		Ges_Pr.add(button_10);
		
		JButton button_11 = new JButton("");
		button_11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parametros.pratos.remove_prato();
			}
		});
		button_11.setIcon(new ImageIcon("C:\\Icons_Geral\\Icons\\001_05.gif"));
		button_11.setToolTipText("Desativar Refeição");
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
				parametros.pratos.seleciona_linha_prat();
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
		tabbedPane.addTab("Gestão de Parametros", null, Ges_Par, null);
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
		button_14.setIcon(new ImageIcon("C:\\Icons_Geral\\Icons\\action_add.gif"));
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
		button_15.setIcon(new ImageIcon("C:\\Icons_Geral\\Icons\\001_05.gif"));
		button_15.setToolTipText("Desativar Refeição");
		button_15.setBounds(699, 69, 27, 28);
		Ges_Par.add(button_15);
		
		JLabel lblNewLabel_10 = new JLabel("Valor");
		lblNewLabel_10.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_10.setBounds(102, 11, 46, 14);
		Ges_Par.add(lblNewLabel_10);
		JLabel lblNewLabel_11 = new JLabel("Descrição do Pârametro");
		lblNewLabel_11.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_11.setBounds(259, 11, 197, 14);
		Ges_Par.add(lblNewLabel_11);
		
		JLabel lblNewLabel_15 = new JLabel("Pârametros:");
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
		button_19_1.setIcon(new ImageIcon("C:\\Icons_Geral\\Icons\\affldsav.gif"));
		button_19_1.setToolTipText("Ativar Utilizador");
		button_19_1.setBounds(699, 100, 27, 28);
		Ges_Par.add(button_19_1);
		
		JPanel Ges_Dom = new JPanel();
		tabbedPane.addTab("Gestão de Dominios", null, Ges_Dom, null);
		Ges_Dom.setLayout(null);
		
		Dom = new JTextField();
		Dom.setColumns(10);
		Dom.setBounds(43, 26, 73, 20);
		Ges_Dom.add(Dom);
		Dom.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
                if (Character.isLetter(c)||Character.isISOControl(c)) // || !Character.isWhitespace(c)
                {
                	return;
                }
                else
                {
                	e.consume();
                	Dom.setText(null);
                	JOptionPane.showMessageDialog(null,"Only accept letters!");
                }
				
			}
		});
		
		Val = new JTextField();
		Val.setBounds(126, 26, 62, 20);
		Ges_Dom.add(Val);
		Val.setColumns(10);
		Val.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) 
            {
                char caracter = e.getKeyChar();
                if (((caracter < '0') || (caracter > '9'))) 
                {
                    e.consume();
                    Val.setText(null);
                	JOptionPane.showMessageDialog(null,"Only accept numbers!");
                }
            }
	    });
		
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
		button_12.setIcon(new ImageIcon("C:\\Icons_Geral\\Icons\\action_add.gif"));
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
		button_13.setIcon(new ImageIcon("C:\\Icons_Geral\\Icons\\001_05.gif"));
		button_13.setToolTipText("Desativar Refeição");
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
		
		JLabel lblNewLabel_9 = new JLabel("Descrição do Dominio");
		lblNewLabel_9.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_9.setBounds(274, 11, 147, 14);
		Ges_Dom.add(lblNewLabel_9);
		
		JLabel lblNewLabel_16 = new JLabel("Dominio:");
		lblNewLabel_16.setBounds(43, 366, 60, 14);
		Ges_Dom.add(lblNewLabel_16);
		
		JButton button_19_2 = new JButton("");
		button_19_2.setIcon(new ImageIcon("C:\\Icons_Geral\\Icons\\affldsav.gif"));
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
		tabbedPane.addTab("Gestão de Feriados", null, Ges_Fer, null);
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
					parametros.feriados.Insere_Feriado();
					parametros.feriados.prencher_Feriados();
				} catch (SQLException e1)
				{
					e1.printStackTrace();
				}
			}
		});
		button_18.setIcon(new ImageIcon("C:\\Icons_Geral\\Icons\\action_add.gif"));
		button_18.setToolTipText("Inserir Utilizador");
		button_18.setBounds(528, 18, 27, 28);
		Ges_Fer.add(button_18);
		
		JLabel lbldescFe = new JLabel("Descrição do Feriado");
		lbldescFe.setBounds(304, 11, 126, 14);
		Ges_Fer.add(lbldescFe);
		
		JScrollPane scrollPane_5 = new JScrollPane();
		Ges_Fer.add(scrollPane_5);
		
		table_8 = new JTable();
		table_8.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				parametros.feriados.seleciona_linha_fer();
			}
		});
		scrollPane_5.setBounds(43, 65, 475, 200);
		scrollPane_5.setViewportView(table_8);
		
		JButton button_19 = new JButton("");
		button_19.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				parametros.feriados.atualiza_feriados();
			}
		});
		button_19.setIcon(new ImageIcon("C:\\Icons_Geral\\Icons\\affldsav.gif"));
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
}
