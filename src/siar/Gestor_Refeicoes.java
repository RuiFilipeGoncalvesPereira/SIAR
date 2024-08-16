package siar;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Check_Validation.Check_Meals_Served_Not_Served;
import Data.Data_Read_Values;
import EXCEL.EXCEL_EXPORT;
import dados_auxiliares.GetName;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JCheckBox;
public class Gestor_Refeicoes extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public static JLabel lblnum,lblNome;
	public static JTable table;
	Calendar cal = Calendar.getInstance();
    Calendar calum = Calendar.getInstance();
    public static JTextField nmec_aux;
    public static JTextField cod_ref_aux;
    public static JTextField cod_pra_aux;
    public static JTextField dta_ref_aux;
    public static JTextField dta_registo_aux;
	public static JCheckBox Check_ref;
	public static JCheckBox Check_corr_ref;
    java.util.Date data_hoje = new java.util.Date();
    java.sql.Date sqlDate = new java.sql.Date(data_hoje.getTime());
	SimpleDateFormat df2 = new SimpleDateFormat("dd-MM-yyyy");
	static Data_Read_Values mostra_data;
	//System.out.println(formatter.format(currentTime)); // 13:05:56;

  
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gestor_Refeicoes frame = new Gestor_Refeicoes();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Gestor_Refeicoes() {
		setAutoRequestFocus(false);
	    mostra_data = new Data_Read_Values();
        mostra_data.le_data();  
        mostra_data.le_hora();
		initialize();
		parametros.marcacoes.daily_meals();
		GetName Gn = new GetName();
		@SuppressWarnings("static-access")
		String nome = Gn.GETNAME(Integer.parseInt(Login.txtUser.getText()));
		Gestor_Refeicoes.lblNome.setText(nome);
		lblnum.setVisible(false);
		Check_corr_ref.setEnabled(false);
		
		JButton btn_export_excel = new JButton("");
		btn_export_excel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EXCEL_EXPORT.Print_Excel_Meals();
			}
		});
		btn_export_excel.setIcon(new ImageIcon("C:\\Icons_Geral\\Icons\\export_large.gif"));
		btn_export_excel.setToolTipText("Export to Excel");
		btn_export_excel.setBounds(10, 132, 43, 36);
		contentPane.add(btn_export_excel);
		Check_corr_ref.setVisible(false);
	}
	private void initialize() 
	{
		setTitle("Gestao de Refeições"); 
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
		
		lblnum = new JLabel("");
		lblnum.setBounds(230, 0, 29, 14);
		contentPane.add(lblnum);
		
		lblNome = new JLabel("");
		lblNome.setBounds(376, 0, 306, 14);
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
		});
		button.setIcon(new ImageIcon("C:\\Icons_Geral\\Icons\\001_20.gif"));
		button.setBounds(10, 24, 43, 36);
		contentPane.add(button);
		
		JButton bt_mar = new JButton("");
		bt_mar.setIcon(new ImageIcon("C:\\Icons_Geral\\Icons\\001_44.gif"));
		bt_mar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
                dispose();
                Marcacoes Marc = new Marcacoes();
                Marc.setVisible(true);
                Marcacoes.lblNum.setText(Login.txtUser.getText());
			}
		});
		bt_mar.setToolTipText("Agendar Refeições");
		bt_mar.setBounds(10, 60, 43, 36);
		contentPane.add(bt_mar);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(63, 53, 609, 368);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent arg0) 
			{
				parametros.marcacoes.seleciona_linha_Checked_Meal();	
			}
		});
		scrollPane.setViewportView(table);
		
		JButton button_1 = new JButton("");
		button_1.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				PDF.Print_Meals_PDF.Print_PDF_Meals();
		  }		
		}
		);
		button_1.setIcon(new ImageIcon("C:\\Icons_Geral\\Icons\\pdf.gif"));
		button_1.setToolTipText("");
		button_1.setBounds(10, 96, 43, 36);
		contentPane.add(button_1);
		
		JButton bt_check = new JButton("");
		bt_check.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				parametros.marcacoes.daily_meals();
				Check_corr_ref.setVisible(false);
				Check_corr_ref.setEnabled(false);
				Check_ref.setVisible(true);
				Check_ref.setEnabled(true);
			}
		});
		bt_check.setIcon(new ImageIcon("C:\\Icons_Geral\\Icons\\checkin.gif"));
		bt_check.setToolTipText("Refeicoes Nao Verificadas");
		bt_check.setBounds(10, 168, 43, 36);
		contentPane.add(bt_check);
		
		JButton bt_n_check = new JButton("");
		bt_n_check.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				parametros.marcacoes.marcacoes_diarias_checadas();
				Check_ref.setVisible(false);
				Check_ref.setEnabled(false);
				Check_corr_ref.setVisible(true);
				Check_corr_ref.setEnabled(true);
			}
		});
		bt_n_check.setIcon(new ImageIcon("C:\\Icons_Geral\\Icons\\checkout.gif"));
		bt_n_check.setToolTipText("Refei\u00E7\u00F5es Verificadas");
		bt_n_check.setBounds(10, 204, 43, 36);
		contentPane.add(bt_n_check);
		
		nmec_aux = new JTextField();
		nmec_aux.setColumns(10);
		nmec_aux.setBounds(22, 448, 1, 1);
		contentPane.add(nmec_aux);
		
		cod_ref_aux = new JTextField();
		cod_ref_aux.setColumns(10);
		cod_ref_aux.setBounds(99, 449, 1, 1);
		contentPane.add(cod_ref_aux);
		
		cod_pra_aux = new JTextField();
		cod_pra_aux.setColumns(10);
		cod_pra_aux.setBounds(179, 449, 1, 1);
		contentPane.add(cod_pra_aux);
		
		dta_ref_aux = new JTextField();
		dta_ref_aux.setColumns(10);
		dta_ref_aux.setBounds(104, 353, 1, 1);
		contentPane.add(dta_ref_aux);
		
		dta_registo_aux = new JTextField();
		dta_registo_aux.setColumns(10);
		dta_registo_aux.setBounds(179, 353, 1, 1);
		contentPane.add(dta_registo_aux);
		
		Check_ref = new JCheckBox("Valida Refeicoes");
		Check_ref.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Check_Meals_Served_Not_Served.Re_Servidas();
			}
		});
		Check_ref.setBounds(63, 24, 240, 23);
		contentPane.add(Check_ref);
		
		Check_corr_ref = new JCheckBox("Corrige Validacoes");
		Check_corr_ref.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Check_Meals_Served_Not_Served.Ref_N_Servidas();
			}
		});
		Check_corr_ref.setBounds(63, 24, 240, 23);
		contentPane.add(Check_corr_ref);
	}
}
