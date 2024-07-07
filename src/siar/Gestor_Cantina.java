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
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import com.toedter.calendar.JDateChooser;

import Data.Data_Read_Values;
import dados_auxiliares.GetName;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.SystemColor;
import javax.swing.SwingConstants;
import java.awt.Font;

@SuppressWarnings("serial")
public class Gestor_Cantina extends JFrame {

	private JPanel contentPane;
	public static JLabel lblnum,lblNome;
	public static JTextField txt_prato;
	public static JTextField txt_ref;
	public static JTable table_can;
	public static JDateChooser dt_ementa;
	public static JComboBox<String> jcomborefeicao_can;
	public static JComboBox<String> jcomboprato_can; 
    SimpleDateFormat df2 = new SimpleDateFormat("dd-MM-yyyy");
    Data_Read_Values mostra_data;
    Date now = new Date(System.currentTimeMillis());
    String strDate = df2.format(now);
    public static JTextField dt_ementa_aux;
    public static JTextField cod_ref_aux;
    public static JTextField cod_pr_aux;
    public static JTextPane txt_ementa;
    public static JTextPane txt_ementa_dia;
    private JLabel lblNewLabel;
    private JLabel lblNewLabel_1;
    private JLabel lblNewLabel_2;
    private JLabel lblNewLabel_3;
    private JLabel lblNewLabel_2_4;
    private JLabel lblNewLabel_2_5;
    private JLabel lblNewLabel_2_6;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gestor_Cantina frame = new Gestor_Cantina();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Gestor_Cantina() {
		initialize();
		GetName Gn = new GetName();
		@SuppressWarnings("static-access")
		String nome = Gn.GETNAME(Integer.parseInt(Login.txtUser.getText()));
		Gestor_Cantina.lblNome.setText(nome);
		lblnum.setVisible(false);
		parametros.ementas.prenche_ementas();
		dados_auxiliares.get_ref.FillReFeicao();
		dados_auxiliares.get_pr.FillPrato();
		Check_Validation.Check_Validation.Calcular_Dia_Correto();
		dt_ementa_aux.setVisible(false);
		cod_ref_aux.setVisible(false);
		cod_pr_aux.setVisible(false);
	}
	private void initialize() 
	{
		setTitle("Gestão da Cantina"); 
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
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                dispose();
        		@SuppressWarnings("unused")
				Login window = new Login();
				Login.frame.setVisible(true);
			}
		});
		button.setIcon(new ImageIcon("C:\\Users\\Rui Pereira\\Documents\\Icons_Geral\\Icons\\001_20.gif"));
		button.setBounds(10, 11, 43, 36);
		contentPane.add(button);
		
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
		
		jcomborefeicao_can = new JComboBox<String>();
		jcomborefeicao_can.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dados_auxiliares.get_ref.Busca_Ref_cod();
			}
		});
		jcomborefeicao_can.setBounds(198, 51, 73, 20);
		contentPane.add(jcomborefeicao_can);
		
		txt_prato = new JTextField();
		txt_prato.setBounds(70, 78, 1, 1);
		contentPane.add(txt_prato);
		
		txt_ref = new JTextField();
		txt_ref.setBounds(107, 78, 1, 1);
		contentPane.add(txt_ref);
		
		jcomboprato_can = new JComboBox<String>();
		jcomboprato_can.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dados_auxiliares.get_pr.Busca_Prato_Cod();
			}
		});
		jcomboprato_can.setBounds(281, 51, 73, 20);
		contentPane.add(jcomboprato_can);

		dt_ementa = new JDateChooser();
		dt_ementa.setDateFormatString("dd-MM-yyyy");
		dt_ementa.setBounds(81, 51, 107, 20);
		contentPane.add(dt_ementa);
		dt_ementa.setDate(now);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(65, 119, 374, 270);
		contentPane.add(scrollPane);
		
		table_can = new JTable();
		table_can.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				parametros.ementas.seleciona_linha_ementa();
				dados_auxiliares.get_ementa.get_ref(Gestor_Cantina.dt_ementa_aux.getText(),Gestor_Cantina.cod_ref_aux.getText(),Gestor_Cantina.cod_pr_aux.getText());
			}
		});
		table_can.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table_can);
		
		JButton btn_add_Refeicao = new JButton("");
		btn_add_Refeicao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					dados_auxiliares.get_ref.Busca_Ref_cod();
					parametros.ementas.Insere_ementas();
					parametros.ementas.prenche_ementas();
				} catch (SQLException e1) {
			           JOptionPane.showMessageDialog(null, "e1! "+e1);
				}
			}
		});
		btn_add_Refeicao.setIcon(new ImageIcon("C:\\Users\\Rui Pereira\\Documents\\Icons_Geral\\Icons\\action_add.gif"));
		btn_add_Refeicao.setBounds(647, 47, 27, 28);
		contentPane.add(btn_add_Refeicao);
		
		dt_ementa_aux = new JTextField();
		dt_ementa_aux.setBounds(65, 400, 86, 20);
		contentPane.add(dt_ementa_aux);
		dt_ementa_aux.setColumns(10);
		
		cod_ref_aux = new JTextField();
		cod_ref_aux.setBounds(158, 400, 86, 20);
		contentPane.add(cod_ref_aux);
		cod_ref_aux.setColumns(10);
		
		cod_pr_aux = new JTextField();
		cod_pr_aux.setBounds(250, 400, 86, 20);
		contentPane.add(cod_pr_aux);
		cod_pr_aux.setColumns(10);
		
		txt_ementa = new JTextPane();
		txt_ementa.setFont(new Font("Tahoma", Font.PLAIN, 10));
		txt_ementa.setBackground(SystemColor.inactiveCaptionBorder);
		txt_ementa.setBounds(450, 51, 187, 66);
		contentPane.add(txt_ementa);
		
		txt_ementa_dia = new JTextPane();
		txt_ementa_dia.setFont(new Font("Tahoma", Font.PLAIN, 10));
		txt_ementa_dia.setBackground(SystemColor.menu);
		txt_ementa_dia.setBounds(515, 128, 167, 66);
		contentPane.add(txt_ementa_dia);
		
		lblNewLabel = new JLabel("Data");
		lblNewLabel.setBounds(81, 33, 46, 14);
		contentPane.add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel("Refei\u00E7\u00E3o");
		lblNewLabel_1.setBounds(198, 33, 61, 14);
		contentPane.add(lblNewLabel_1);
		
		lblNewLabel_2 = new JLabel("Prato");
		lblNewLabel_2.setBounds(281, 33, 46, 14);
		contentPane.add(lblNewLabel_2);
		
		lblNewLabel_3 = new JLabel("Ementa");
		lblNewLabel_3.setBounds(450, 33, 46, 14);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_2_1 = new JLabel("Sopa:");
		lblNewLabel_2_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2_1.setBounds(393, 51, 46, 14);
		contentPane.add(lblNewLabel_2_1);
		
		JLabel lblNewLabel_2_2 = new JLabel("Refei\u00E7\u00E3o:");
		lblNewLabel_2_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2_2.setVerticalAlignment(SwingConstants.BOTTOM);
		lblNewLabel_2_2.setBounds(364, 66, 75, 14);
		contentPane.add(lblNewLabel_2_2);
		
		JLabel lblNewLabel_2_3 = new JLabel("Sobremesa:");
		lblNewLabel_2_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2_3.setBounds(442, 157, 73, 14);
		contentPane.add(lblNewLabel_2_3);
		
		lblNewLabel_2_4 = new JLabel("          Sopa:");
		lblNewLabel_2_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2_4.setBounds(442, 128, 68, 14);
		contentPane.add(lblNewLabel_2_4);
		
		lblNewLabel_2_5 = new JLabel("   Refei\u00E7\u00E3o:");
		lblNewLabel_2_5.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2_5.setVerticalAlignment(SwingConstants.BOTTOM);
		lblNewLabel_2_5.setBounds(442, 143, 73, 14);
		contentPane.add(lblNewLabel_2_5);
		
		lblNewLabel_2_6 = new JLabel("Sobremesa:");
		lblNewLabel_2_6.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2_6.setBounds(347, 79, 92, 14);
		contentPane.add(lblNewLabel_2_6);
		txt_ementa.addKeyListener(new KeyAdapter() {
	        @Override
	        public void keyPressed(KeyEvent e) {
	            if(e.getKeyCode() == KeyEvent.VK_ENTER)
	            {
	            	String virgula = ",";
	            	String conteudo = txt_ementa.getText();
	            	txt_ementa.setText(conteudo+virgula);
	            }
	        }

	    });
	}
}
