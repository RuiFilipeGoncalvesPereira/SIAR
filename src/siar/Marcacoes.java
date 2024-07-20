package siar;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.util.Calendar;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JTextPane;

import com.toedter.calendar.JDateChooser;

import Check_Validation.Check_Holiday;
import Check_Validation.Check_Schedules_Meals_Rules;
import Check_Validation.ativa_desativa_marcacoes;
import Data.Data_Read_Values;
import dados_auxiliares.GetName;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.SystemColor;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.SwingConstants;
import java.awt.Font;

@SuppressWarnings("serial")
public class Marcacoes extends JFrame {
	private JPanel contentPane;
	public static JLabel lblNum;
	public static JComboBox<String> jcomboprato;
	public static JComboBox<String> jcomborefeicao;
	public static JDateChooser dt_ref;
	public static JLabel lblNome;
	public static Calendar cal = Calendar.getInstance();
	public static Calendar calum = Calendar.getInstance();
	public static Calendar caldois = Calendar.getInstance();
	public static Calendar caltres = Calendar.getInstance(); 
    Check_Holiday CH = new Check_Holiday();
    public static Calendar calmaiortrinta = Calendar.getInstance();
    Data_Read_Values mostra_data;
	public static JButton btn_anula;
	public static JButton btn_back_menu;
	public static JTable table;
	public static JTextField jcodref;
	public static JTextField jcodprato;
	public static JTextPane ementa;
	public static JTextField nmec_aux;
	public static JTextField cod_ref_aux;
	public static JTextField cod_pra_aux;
	public static JTextField dta_ref_aux;
	public static JTextField dta_registo_aux;
	public static JScrollPane scrollPane;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_2_1;
	private JLabel lblNewLabel_2_2;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Marcacoes frame = new Marcacoes();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the frame.
	 */
	public Marcacoes() {
		setBackground(SystemColor.control);
	    mostra_data = new Data_Read_Values();
        mostra_data.le_data();  
        mostra_data.le_hora();
		initialize();
		dados_auxiliares.getfill_meals.FillReFeicao();
		dados_auxiliares.getfill_dishes.FillPrato();
		GetName Gn = new GetName();
		@SuppressWarnings("static-access")
		String nome = Gn.GETNAME(Integer.parseInt(Login.txtUser.getText()));
		Marcacoes.lblNome.setText(nome);
		lblNum.setVisible(false);
		parametros.marcacoes.prencher_marcacoes();
	}
	private void initialize() {
		this.setTitle("Menu de Marcação de refeições");
		setAutoRequestFocus(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 698, 471);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbldesc = new JLabel("Utilizador:");
		lbldesc.setBounds(308, 0, 66, 14);
		contentPane.add(lbldesc);
		
		lblNum = new JLabel("");
		lblNum.setBounds(337, 0, 37, 14);
		contentPane.add(lblNum);
		
		lblNome = new JLabel("");
		lblNome.setBounds(373, 0, 309, 14);
		contentPane.add(lblNome);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setToolTipText("Inico");
		btnNewButton.addActionListener(new ActionListener() 
		{
			@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent arg0)
			{
				cal.add(Calendar.DATE,-Integer.parseInt(CH.check_holiday(31)));
				caldois.add(Calendar.DATE,-Integer.parseInt(CH.check_holiday(61)));
				caltres.add(Calendar.DATE,-Integer.parseInt(CH.check_holiday(71)));
				calmaiortrinta.add(Calendar.DATE,-Integer.parseInt(CH.check_holiday(51)));
				dispose();
        		Login window = new Login();
				window.frame.setVisible(true);
			}
		});
		btnNewButton.setIcon(new ImageIcon("C:\\Icons_Geral\\Icons\\001_20.gif"));
		btnNewButton.setBounds(10, 11, 43, 36);
		contentPane.add(btnNewButton);
		
		JButton button = new JButton("");
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				cal.add(Calendar.DATE,-Integer.parseInt(CH.check_holiday(31)));
				caldois.add(Calendar.DATE,-Integer.parseInt(CH.check_holiday(61)));
				caltres.add(Calendar.DATE,-Integer.parseInt(CH.check_holiday(71)));
				calmaiortrinta.add(Calendar.DATE,-Integer.parseInt(CH.check_holiday(51)));
				dispose();
				Password.Call_Pass_Form.Alterar_Password();
			}
		});
		button.setIcon(new ImageIcon("C:\\Icons_Geral\\Icons\\001_41.gif"));
		button.setToolTipText("Alteração de Password");
		button.setBounds(52, 11, 43, 36);
		contentPane.add(button);
		
		jcomborefeicao = new JComboBox<String>();
		jcomborefeicao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			 dados_auxiliares.getfill_meals_cod.Busca_Ref_cod();
			}
		});
		jcomborefeicao.setBounds(196, 78, 73, 20);
		contentPane.add(jcomborefeicao);
		
		jcodref = new JTextField();
		jcodref.setBounds(57, 105, 1, 1);
		contentPane.add(jcodref);
		
		jcodprato = new JTextField();
		jcodprato.setBounds(94, 105, 1, 1);
		contentPane.add(jcodprato);
		
		jcomboprato = new JComboBox<String>();
		jcomboprato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dados_auxiliares.getfill_dishes_cod.Busca_Prato_Cod();
			}
		});
		jcomboprato.setBounds(279, 78, 73, 20);
		contentPane.add(jcomboprato);
		
		JLabel lblData = new JLabel("Data");
		lblData.setBounds(79, 53, 46, 14);
		contentPane.add(lblData);
		
		JLabel lblNewLabel = new JLabel("Refeições");
		lblNewLabel.setBounds(196, 53, 64, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Prato");
		lblNewLabel_1.setBounds(279, 53, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		ementa = new JTextPane();
		ementa.setFont(new Font("Tahoma", Font.PLAIN, 10));
		ementa.setBackground(SystemColor.menu);
		ementa.setBounds(448, 74, 187, 66);
		contentPane.add(ementa);
		
		JButton btn_add_Refeicao = new JButton("");
		btn_add_Refeicao.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				Check_Schedules_Meals_Rules.ValidaDatas();
			}
		});
		btn_add_Refeicao.setIcon(new ImageIcon("C:\\Icons_Geral\\Icons\\action_add.gif"));
		btn_add_Refeicao.setBounds(645, 74, 27, 28);
		contentPane.add(btn_add_Refeicao);
		
		dt_ref = new JDateChooser();
		dt_ref.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				dados_auxiliares.get_ementa.get_ref(Marcacoes.dt_ref.getDate(),Marcacoes.jcodref.getText(),Marcacoes.jcodprato.getText());
			}
		});
		dt_ref.setDateFormatString("dd-MM-yyyy");
		parametros.marcacoes.Calcular_Dia_Correto();
		dt_ref.setBounds(79, 78, 107, 20);
		dt_ref.getJCalendar().setPreferredSize(new Dimension(300, 200));  
		contentPane.add(dt_ref);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(67, 151, 572, 270);
		scrollPane.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		contentPane.add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				parametros.marcacoes.seleciona_linha();	
			}
		});
		scrollPane.setViewportView(table);
		
		btn_anula = new JButton("");
		btn_anula.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parametros.marcacoes.anula_refeicao();
			}
		});
		btn_anula.setToolTipText("Desmarcar Refei\u00E7\u00E3o");
		btn_anula.setIcon(new ImageIcon("C:\\Icons_Geral\\Icons\\001_05.gif"));
		btn_anula.setBounds(645, 151, 27, 28);
		contentPane.add(btn_anula);
		
		nmec_aux = new JTextField();
		nmec_aux.setBounds(28, 117, 1, 1);
		contentPane.add(nmec_aux);
		nmec_aux.setColumns(10);
		
		cod_ref_aux = new JTextField();
		cod_ref_aux.setBounds(105, 118, 1, 1);
		contentPane.add(cod_ref_aux);
		cod_ref_aux.setColumns(10);
		
		cod_pra_aux = new JTextField();
		cod_pra_aux.setBounds(185, 118, 1, 1);
		contentPane.add(cod_pra_aux);
		cod_pra_aux.setColumns(10);
		
		dta_ref_aux = new JTextField();
		dta_ref_aux.setBounds(110, 22, 1, 1);
		contentPane.add(dta_ref_aux);
		dta_ref_aux.setColumns(10);
		
		dta_registo_aux = new JTextField();
		dta_registo_aux.setBounds(185, 22, 1, 1);
		contentPane.add(dta_registo_aux);
		dta_registo_aux.setColumns(10);
		
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btn_anula.setVisible(true);
				parametros.marcacoes.prencher_marcacoes();
			}
		});
		btnNewButton_1.setToolTipText("Agendamentos");
		btnNewButton_1.setIcon(new ImageIcon("C:\\Icons_Geral\\Icons\\db_ins.gif"));
		btnNewButton_1.setBounds(10, 151, 27, 28);
		contentPane.add(btnNewButton_1);
		
		JButton button_1 = new JButton("");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btn_anula.setVisible(false);
				parametros.marcacoes.prencher_marcacoes_Passadas();
			}
		});
		button_1.setToolTipText("Histórico");
		button_1.setIcon(new ImageIcon("C:\\Icons_Geral\\Icons\\database.gif"));
		button_1.setBounds(10, 178, 27, 28);
		contentPane.add(button_1);
		
		JButton button_2 = new JButton("");
		button_2.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				btn_anula.setVisible(false);
				parametros.marcacoes.prencher_marcacoes_Desativadas();
			}
		});
		button_2.setToolTipText("Anula��es");
		button_2.setIcon(new ImageIcon("C:\\Icons_Geral\\Icons\\db_del.gif"));
		button_2.setBounds(10, 205, 27, 28);
		contentPane.add(button_2);
		
		btn_back_menu = new JButton("");
		btn_back_menu.setToolTipText("Back to Food Management");
		btn_back_menu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cal.add(Calendar.DATE,-Integer.parseInt(CH.check_holiday(31)));
				caldois.add(Calendar.DATE,-Integer.parseInt(CH.check_holiday(61)));
				caltres.add(Calendar.DATE,-Integer.parseInt(CH.check_holiday(71)));
				calmaiortrinta.add(Calendar.DATE,-Integer.parseInt(CH.check_holiday(51)));
				dispose();
    			ativa_desativa_marcacoes.ativa_desativa_marcacoes_(Integer.parseInt(Login.txtUser.getText()));
			}
		});
		btn_back_menu.setIcon(new ImageIcon("C:\\Icons_Geral\\Icons\\001_27.gif"));
		btn_back_menu.setBounds(94, 11, 43, 36);
		contentPane.add(btn_back_menu);
		
		JLabel lblNewLabel_1_1 = new JLabel("Ementa");
		lblNewLabel_1_1.setBounds(448, 53, 46, 14);
		contentPane.add(lblNewLabel_1_1);
		
		lblNewLabel_2 = new JLabel("          Sopa:");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(373, 74, 68, 14);
		contentPane.add(lblNewLabel_2);
		
		lblNewLabel_2_1 = new JLabel("   Refei\u00E7\u00E3o:");
		lblNewLabel_2_1.setVerticalAlignment(SwingConstants.BOTTOM);
		lblNewLabel_2_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2_1.setBounds(373, 89, 73, 14);
		contentPane.add(lblNewLabel_2_1);
		
		lblNewLabel_2_2 = new JLabel("Sobremesa:");
		lblNewLabel_2_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2_2.setBounds(373, 103, 73, 14);
		contentPane.add(lblNewLabel_2_2);
		
		cal.add(Calendar.DATE,Integer.parseInt(CH.check_holiday(31)));
		caldois.add(Calendar.DATE,Integer.parseInt(CH.check_holiday(61)));
		caltres.add(Calendar.DATE,Integer.parseInt(CH.check_holiday(71)));
		calmaiortrinta.add(Calendar.DATE,Integer.parseInt(CH.check_holiday(51)));
		
        }
}

