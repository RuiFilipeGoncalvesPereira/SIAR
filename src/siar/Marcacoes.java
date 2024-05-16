package siar;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;

import com.toedter.calendar.JDateChooser;

import net.proteanit.sql.DbUtils;

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
	Connection conn_utilizador = null;
	Connection conn_rem = null;
	Connection conn_prato= null;
	Connection conn_ref= null;
	Connection conn_ref_cod= null;
	Connection conn_prato_cod= null;
	Connection conn_mar= null;
	Connection conn_anuladas= null;
	ResultSet rs_admin = null;
	ResultSet rs_gecan = null;
	public JComboBox<String> jcomboprato,jcomborefeicao;
	public static JDateChooser dt_ref;
	ResultSet rs = null;
	PreparedStatement pst = null;
	ResultSet rs_prato = null;
	PreparedStatement pstprato = null;
	ResultSet rs_ref = null;
	PreparedStatement pstref= null;
	ResultSet rs_ref_cod = null;
	PreparedStatement pstref_cod= null;
	ResultSet rs_conn_prato_cod = null;
	PreparedStatement P_prato_cod= null;
	ResultSet rs_prato_rem = null;
	PreparedStatement pstprato_rem = null;
	ResultSet rs_conn_mar = null;
	PreparedStatement pstconn_mar = null;
	ResultSet rs_conn_anuladas = null;
	PreparedStatement pstconn_anuladas = null;
	PreparedStatement pst_inferiado = null;
	Connection conn_coferiado  = null;
	ResultSet rsinferiado = null;
	public static JLabel lblNome;
	Calendar cal = Calendar.getInstance();
    Calendar calum = Calendar.getInstance();
    Calendar caldois = Calendar.getInstance(); 
    Calendar calmaiortrinta = Calendar.getInstance();
    Calendar caldia = Calendar.getInstance();
    SimpleDateFormat df2 = new SimpleDateFormat("dd-MM-yyyy");
    Data mostra_data;
    Date now = new Date(System.currentTimeMillis());
    String strDate = df2.format(now);
    java.util.Date data_hoje = new java.util.Date();
    java.sql.Date sqlDate = new java.sql.Date(data_hoje.getTime());
	int val = caldia.get(Calendar.DAY_OF_WEEK);
	int hour = caldia.get(Calendar.HOUR_OF_DAY);
	int minute = caldia.get(Calendar.MINUTE);
	public static JButton btn_anula;
	public static JButton btn_back_menu;
	public JTable table;
	public static JTextField jcodref;
	public static JTextField jcodprato;
	public static JTextPane ementa;
	private JTextField nmec_aux;
	private JTextField cod_ref_aux;
	private JTextField cod_pra_aux;
	private JTextField dta_ref_aux;
	private JTextField dta_registo_aux;
	private JScrollPane scrollPane;
	Check_Holiday CH = new Check_Holiday();
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
		conn_utilizador = JavaConection.ConnecrDb(); 
		conn_ref = JavaConection.ConnecrDb(); 
		conn_ref_cod = JavaConection.ConnecrDb(); 
		conn_prato = JavaConection.ConnecrDb(); 
		conn_prato_cod = JavaConection.ConnecrDb(); 
		conn_mar = JavaConection.ConnecrDb(); 
	    conn_rem = JavaConection.ConnecrDb(); 
	    conn_anuladas = JavaConection.ConnecrDb(); 
		conn_coferiado = JavaConection.ConnecrDb();
	    mostra_data = new Data();
        mostra_data.le_data();  
        mostra_data.le_hora();
		initialize();
		FillReFeicao();
		FillPrato();
		GetName Gn = new GetName();
		@SuppressWarnings("static-access")
		String nome = Gn.GETNAME(Integer.parseInt(Login.txtUser.getText()));
		Marcacoes.lblNome.setText(nome);
		lblNum.setVisible(false);
		prencher_marcacoes();
	}
	public void FillPrato()
	{
		try
        {
            String sql_prato = "select * from siar.siar_prato";
            conn_prato.prepareStatement(sql_prato); 
            pstprato=conn_prato.prepareStatement(sql_prato ,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs_prato=pstprato.executeQuery();
            while(rs_prato.next())
            {
               String prato = rs_prato.getString("Desc_Prato");
               jcomboprato.addItem(prato);
            }  
        }
        catch(Exception e)
	     {
	            JOptionPane.showMessageDialog(null, "Erro ao Listar na Tabela1!"+e);
	     }
    }
	private void initialize() {
		this.setTitle("Menu de Marca��o de refeições");
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
                dispose();
        		Login window = new Login();
				window.frame.setVisible(true);
			}
		});
		btnNewButton.setIcon(new ImageIcon("C:\\Users\\Rui Pereira\\Documents\\Icons_Geral\\Icons\\001_20.gif"));
		btnNewButton.setBounds(10, 11, 43, 36);
		contentPane.add(btnNewButton);
		
		JButton button = new JButton("");
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				Alterar_Password();
			}
		});
		button.setIcon(new ImageIcon("C:\\Users\\Rui Pereira\\Documents\\Icons_Geral\\Icons\\001_41.gif"));
		button.setToolTipText("Altera��o de Password");
		button.setBounds(52, 11, 43, 36);
		contentPane.add(button);
		
		jcomborefeicao = new JComboBox<String>();
		jcomborefeicao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Busca_Ref_cod();
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
				Busca_Prato_Cod();
			}
		});
		jcomboprato.setBounds(279, 78, 73, 20);
		contentPane.add(jcomboprato);
		
		JLabel lblData = new JLabel("Data");
		lblData.setBounds(79, 53, 46, 14);
		contentPane.add(lblData);
		
		JLabel lblNewLabel = new JLabel("Refei\u00E7\u00E3o");
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
				ValidaDatas();
			}
		});
		btn_add_Refeicao.setIcon(new ImageIcon("C:\\Users\\Rui Pereira\\Documents\\Icons_Geral\\Icons\\action_add.gif"));
		btn_add_Refeicao.setBounds(645, 74, 27, 28);
		contentPane.add(btn_add_Refeicao);
		
		dt_ref = new JDateChooser();
		dt_ref.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				dados_auxiliares.get_ementa.get_ref(Marcacoes.dt_ref.getDate(),Marcacoes.jcodref.getText(),Marcacoes.jcodprato.getText());
			}
		});
		dt_ref.setDateFormatString("dd-MM-yyyy");
		Calcular_Dia_Correto();
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
				seleciona_linha();	
			}
		});
		scrollPane.setViewportView(table);
		
		btn_anula = new JButton("");
		btn_anula.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				anula_refeicao();
			}
		});
		btn_anula.setToolTipText("Desmarcar Refei\u00E7\u00E3o");
		btn_anula.setIcon(new ImageIcon("C:\\Users\\Rui Pereira\\Documents\\Icons_Geral\\Icons\\action_delete.gif"));
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
				prencher_marcacoes();
			}
		});
		btnNewButton_1.setToolTipText("Agendamentos");
		btnNewButton_1.setIcon(new ImageIcon("C:\\Users\\Rui Pereira\\Documents\\Icons_Geral\\Icons\\db_ins.gif"));
		btnNewButton_1.setBounds(10, 151, 27, 28);
		contentPane.add(btnNewButton_1);
		
		JButton button_1 = new JButton("");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				prencher_marcacoes_Passadas();
			}
		});
		button_1.setToolTipText("Hist\u00F3rico");
		button_1.setIcon(new ImageIcon("C:\\Users\\Rui Pereira\\Documents\\Icons_Geral\\Icons\\database.gif"));
		button_1.setBounds(10, 178, 27, 28);
		contentPane.add(button_1);
		
		JButton button_2 = new JButton("");
		button_2.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				marcacoes_desativadas();
			}
		});
		button_2.setToolTipText("Anula��es");
		button_2.setIcon(new ImageIcon("C:\\Users\\Rui Pereira\\Documents\\Icons_Geral\\Icons\\db_del.gif"));
		button_2.setBounds(10, 205, 27, 28);
		contentPane.add(button_2);
		
		btn_back_menu = new JButton("");
		btn_back_menu.setToolTipText("Back to Food Management");
		btn_back_menu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
    			ativa_desativa_marcacoes.ativa_desativa_marcacoes(Integer.parseInt(Login.txtUser.getText()));
			}
		});
		btn_back_menu.setIcon(new ImageIcon("C:\\Users\\Rui Pereira\\Documents\\Icons_Geral\\Icons\\001_27.gif"));
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
		calum.add(Calendar.DATE,Integer.parseInt(CH.check_holiday(61)));
		caldois.add(Calendar.DATE,Integer.parseInt(CH.check_holiday(71)));
		calmaiortrinta.add(Calendar.DATE,Integer.parseInt(CH.check_holiday(51)));
		
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
	public void Alterar_Password()
	{
        dispose();
        Altera_Password Altera = new Altera_Password();
        Altera.setVisible(true);
        Altera_Password.textNum.setText(Login.txtUser.getText());
	}
	public void Calcular_Dia_Correto()
	{
		dt_ref.setDate(now);
		String horalimite = CH.check_holiday(2);
		//JOptionPane.showMessageDialog(null, "horalimite!"+horalimite);
		//JOptionPane.showMessageDialog(null, "mostra_data.horamin!"+mostra_data.horamin);
		
		if((mostra_data.horamin.compareTo(horalimite)>=0))
		{  
			Calendar cal = Calendar.getInstance();
			cal.setTime(dt_ref.getDate());
			cal.add(Calendar.DAY_OF_MONTH, 2);
			java.util.Date futureDate2 = cal.getTime();	
			dt_ref.setDate(futureDate2);
		}
		else
		{
			Calendar cal = Calendar.getInstance();
			cal.setTime(dt_ref.getDate());
			cal.add(Calendar.DAY_OF_MONTH, 1);
			java.util.Date futureDate1 = cal.getTime();	
			dt_ref.setDate(futureDate1);	
		}

	}
	public void Busca_Prato_Cod()
	{
		try{
	         String sql = "select * from siar.siar_prato where Desc_Prato=?";
	         P_prato_cod=conn_prato_cod.prepareStatement(sql);
	         P_prato_cod=conn_prato_cod.prepareStatement(sql ,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
	         P_prato_cod.setString(1,(String) jcomboprato.getSelectedItem());
	         rs_conn_prato_cod = P_prato_cod.executeQuery();
	            while(rs_conn_prato_cod.next())
	            {
	              jcodprato.setText(rs_conn_prato_cod.getString("Cod_Prato"));
	            }   
	            dados_auxiliares.get_ementa.get_ref(Marcacoes.dt_ref.getDate(),Marcacoes.jcodref.getText(),Marcacoes.jcodprato.getText());   
	       }
	       catch(Exception e3)
	       {
	       JOptionPane.showMessageDialog(null, "Erro ao Listar na Tabela4!"+e3);
	       }
	}
	public void FillReFeicao()
	{
		try
	    {
	        String sqlref = "select * from siar.siar_refeicao";
	        conn_ref.prepareStatement(sqlref); 
	        pstref=conn_ref.prepareStatement(sqlref ,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
	        rs_ref=pstref.executeQuery();
	        while(rs_ref.next())
	        {
	           String refeicao = rs_ref.getString("Desc_Refeicao");
	           jcomborefeicao.addItem(refeicao);
	        }  
	    }
		    catch(Exception e)
	    	{
		        JOptionPane.showMessageDialog(null, "Erro ao Listar na Tabela2!"+e);
		    }
	} 
	public void Busca_Ref_cod()
	{
		try{
	         String sql="select * from siar.siar_refeicao where Desc_refeicao=?";
	         conn_ref_cod.prepareStatement(sql);
	         pstref_cod=conn_ref_cod.prepareStatement(sql ,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
	         pstref_cod.setString(1, (String) jcomborefeicao.getSelectedItem());
             rs_ref_cod=pstref_cod.executeQuery();
	            while(rs_ref_cod.next())
	            {
	            	jcodref.setText(rs_ref_cod.getString("Cod_Refeicao"));
	            }   
	            dados_auxiliares.get_ementa.get_ref(Marcacoes.dt_ref.getDate(),Marcacoes.jcodref.getText(),Marcacoes.jcodprato.getText());  
	       }
		   
	       catch(Exception e3)
	       {
	       JOptionPane.showMessageDialog(null, "Erro ao Listar na Tabela5!"+e3);
	       }
	}
	public void ValidaDatas()
	{
			String strcalendar = null;
			String strcalum = null;
			String strcaldois = null;
			String horalimite = CH.check_holiday(2);
			String Imaculada = CH.check_Feriado(1);
			String Natal = CH.check_Feriado(2);
			String AssunSenhora = CH.check_Feriado(3);
			String DPortugal = CH.check_Feriado(4);
			String DTrabalhador = CH.check_Feriado(5);
			String Fmunicipal = CH.check_Feriado(6);
			String Dliberdade = CH.check_Feriado(7);
			String Pascoa = CH.check_Feriado(8);
			String SextaSanta = CH.check_Feriado(9);
			String AnoNovo = CH.check_Feriado(10);
			java.util.Date utilStartDate = dt_ref.getDate();
			java.sql.Date sqlStartDate = new java.sql.Date(utilStartDate.getTime());
			java.sql.Date sqlDateTrinta =  new java.sql.Date(calmaiortrinta.getTime().getTime());
	
			SimpleDateFormat sdfcalendar = new SimpleDateFormat("dd-MM-yyyy");
			String dataobtida = df2.format(dt_ref.getDate());
			
			if (cal != null) {
				strcalendar = sdfcalendar.format(cal.getTime());
				}
			if (calum != null) {
				strcalum = sdfcalendar.format(calum.getTime());
				}
			if (caldois != null) {
				strcaldois = sdfcalendar.format(caldois.getTime());
				}
			
	         if(((JTextField)dt_ref.getDateEditor().getUiComponent()).getText().isEmpty())
	         {
	             JOptionPane.showMessageDialog(null, "Deve Inserir a Data da Refeição!");
	             ((JTextField)dt_ref.getDateEditor().getUiComponent()).requestFocus();
	             return;
	         }
	         
			if ((strDate.compareTo(Imaculada)==0) && (dataobtida.compareTo(Imaculada)==0))
			{
				 JOptionPane.showMessageDialog(null, "Dia da Imaculada Conçeição não Pode Marcar Refeições para Amanhâ");
				 dt_ref.requestFocus();
				 return;
			}
			if ((strDate.compareTo(Natal)==0) && (dataobtida.compareTo(Natal)==0))
			{
				 JOptionPane.showMessageDialog(null, "Dia de Natal não Pode Marcar Refeições para Amanhâ");
				 dt_ref.requestFocus();
				 return;
			}
			if ((strDate.compareTo(AssunSenhora)==0) && (dataobtida.compareTo(AssunSenhora)==0))
			{
				 JOptionPane.showMessageDialog(null, "Dia de nossa Senhora da Assunção não Pode Marcar Refeições para Amanhã");
				 dt_ref.requestFocus();
				 return;
			}
			if ((strDate.compareTo(DPortugal)==0) && (dataobtida.compareTo(DPortugal)==0))
			{
				 JOptionPane.showMessageDialog(null, "Dia de Portugal não Pode Marcar Refeições para Amanhâ");
				 dt_ref.requestFocus();
				 return;
			}
			if ((strDate.compareTo(DTrabalhador)==0) && (dataobtida.compareTo(DTrabalhador)==0))
			{
				 JOptionPane.showMessageDialog(null, "Dia do trabalhador não Pode Marcar Refeições para Amanhâ");
				 dt_ref.requestFocus();
				 return;
			}
			if ((strDate.compareTo(Fmunicipal)==0) && (dataobtida.compareTo(Fmunicipal)==0))
			{
				 JOptionPane.showMessageDialog(null, "Feriado Municipal Não Pode Marcar refeições para Amanhâ");
				 dt_ref.requestFocus();
				 return;
			}
			if ((strDate.compareTo(Dliberdade)==0) && (dataobtida.compareTo(Dliberdade)==0))
			{
				 JOptionPane.showMessageDialog(null, "� Dia da liberdade Não Pode Marcar refeições para Amanhâ");
				 dt_ref.requestFocus();
				 return;
			}
			if ((strDate.compareTo(Pascoa)==0) && (dataobtida.compareTo(Pascoa)==0))
			{
				 JOptionPane.showMessageDialog(null, "� P�scoa Não Pode Marcar refeições para Amanhâ");
				 dt_ref.requestFocus();
				 return;
			}
			if ((strDate.compareTo(SextaSanta)==0) && (dataobtida.compareTo(SextaSanta)==0))
			{
				 JOptionPane.showMessageDialog(null, "� Sexta Feira Santa Não Pode Marcar refeições para Amanhâ");
				 dt_ref.requestFocus();
				 return;
			}
			if ((strDate.compareTo(AnoNovo)==0) && (dataobtida.compareTo(AnoNovo)==0))
			{
				 JOptionPane.showMessageDialog(null, "� Ano Novo Não Pode Marcar refeições para Amanhâ");
				 dt_ref.requestFocus();
				 return;
			}
			if ((val == 6) && (dataobtida.compareTo(strcalendar)==0) && (mostra_data.horamin.compareTo(horalimite)>=0))
			{
				 JOptionPane.showMessageDialog(null, "Sexta,Já passa das "+horalimite+" !,Não pode marcar refeições para Sábado!");
				 dt_ref.requestFocus();
				 return;
			}
			if ((val == 6) && (dataobtida.compareTo(strcalum)==0) && (mostra_data.horamin.compareTo(horalimite)>=0))
			{
				 JOptionPane.showMessageDialog(null, "Sexta,Já passa das "+horalimite+" !,Não pode marcar refeições para Domingo!");
				 dt_ref.requestFocus();
				 return;
			}
			if ((val == 6) && (dataobtida.compareTo(strcaldois)==0)&& (mostra_data.horamin.compareTo(horalimite)>=0))
			{
				 JOptionPane.showMessageDialog(null, "Sexta,Já passa das "+horalimite+" !,Não pode marcar refeições para Segunda!");
				 dt_ref.requestFocus();
				 return;
			}
			if ((val == 7) && (dataobtida.compareTo(strcalendar)==0))
			{
				 JOptionPane.showMessageDialog(null, "Sábado!,Não pode marcar refeições para domingo!");
				 dt_ref.requestFocus();
				 return;
			}
			if ((val == 7) && (dataobtida.compareTo(strcalum)==0))
			{
				 JOptionPane.showMessageDialog(null, "Sábado!,Não pode marcar refeições para Segunda!");
				 dt_ref.requestFocus();
				 return;
			}
			if ((val == 1) && (dataobtida.compareTo(strcalendar)==0))
			{
				 JOptionPane.showMessageDialog(null, "Domingo!,Não pode marcar refeições para segunda!");
				 dt_ref.requestFocus();
				 return;
			}
			
			if(dataobtida.compareTo(strcalendar)==0 && (mostra_data.horamin.compareTo(horalimite)>=0))
	        {
			 JOptionPane.showMessageDialog(null, "Já passa das "+horalimite+" Não pode marcar refeições para Amanhâ!");
			 dt_ref.requestFocus();
			 return;
	        }
	        if(sqlStartDate.after(sqlDateTrinta))
	        {
			 JOptionPane.showMessageDialog(null, "Não pode marcar refeições para daqui a mais de 31 dias!");
			 dt_ref.requestFocus();
			 return;
	        }
			if(dataobtida.equals(strDate))
	    	{
			 JOptionPane.showMessageDialog(null, "Erro!,Não pode marcar refeições para hoje!");
			 dt_ref.requestFocus();
			 return;
			}
			else if(sqlStartDate.before(sqlDate)) 
			{
			 JOptionPane.showMessageDialog(null, "Erro!,Está a tentar marcar refeições para datas passadas!");
			 dt_ref.requestFocus();
			 return;
			}
			efetua_Marcacoes();
    }
	public void efetua_Marcacoes()
	{
		String dataref = df2.format(dt_ref.getDate());
		try
		{
			String conta="select count(*) from siar.siar_marcacoes where Num_Mecanog='"+Login.txtUser.getText()+"' and to_char(dta_Refeicao,'dd-mm-yyyy')='"+dataref+"' and Cod_Refeicao='"+jcodref.getText()+"' and dta_desativo is null";
			pstconn_mar=conn_mar.prepareStatement(conta);
			pstconn_mar=conn_mar.prepareStatement(conta ,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs_conn_mar=pstconn_mar.executeQuery();
			
            String conta2="select count(*) from siar.siar_marcacoes where Num_Mecanog='"+Login.txtUser.getText()+"' and to_char(dta_Refeicao,'dd-mm-yyyy')='"+dataref+"' and Cod_Refeicao='"+jcodref.getText()+"' and dta_desativo is not null";
			pstprato_rem=conn_rem.prepareStatement(conta2);
            pstprato_rem=conn_rem.prepareStatement(conta2 ,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs_prato_rem=pstprato_rem.executeQuery();

            rs_conn_mar.next();
            int conta_ref = rs_conn_mar.getInt(1);
            rs_prato_rem.next();
            int conta_ref_rem = rs_prato_rem.getInt(1);

            if(conta_ref > 0) // Entra aqui quando Já existe o agendamento especifico
              {
                if (conta_ref_rem == 0) 
                {	
            	 JOptionPane.showMessageDialog(null,"Essa Refeição Já foi Agendada Noutra Altura!"); 
                 return;
                } 
              }
            else if(conta_ref == 0)
            {  
	             if(conta_ref_rem == 0)	
	             {	 
	            	String sql="insert into siar.siar_marcacoes(Num_Mecanog,Dta_Refeicao,Cod_Refeicao,Cod_Prato,Dta_Desativo,Dta_Registo,Verificacao)values(?,?,?,?,?,?,?)"; 
		            pstprato=conn_prato.prepareStatement(sql);
		            pstprato.setString(1, Login.txtUser.getText());
		            pstprato.setDate(2,convertUtilDateToSqlDate(dt_ref.getDate()));
		            pstprato.setString(3, jcodref.getText());
		            pstprato.setString(4, jcodprato.getText()); 
		            pstprato.setDate(5,null);
		            pstprato.setDate(6,now);
		            pstprato.setString(7, "N");
		            pstprato.executeQuery();
		            JOptionPane.showMessageDialog(null,"Dados Inseridos com Sucesso!");
	             }
		        else if (conta_ref_rem > 0)
		         {
		        	 try{
			            	String sql="update siar.siar_marcacoes set dta_desativo = null, cod_prato = '"+jcodprato.getText()+"' where Num_Mecanog='"+Login.txtUser.getText()+"'and to_char(dta_Refeicao,'dd-mm-yyyy')='"+dataref+"' and Cod_Refeicao='"+jcodref.getText()+"' and dta_desativo is not null"; 
				            pstprato=conn_prato.prepareStatement(sql);
				            pstprato.executeQuery();
			                JOptionPane.showMessageDialog(null,"Refeição Remarcada!"); 
		            	  }
		     	       catch(Exception e)
				        {
				           JOptionPane.showMessageDialog(null,e); 
				        }
		              try{
				        	String sql="delete from siar.siar_anulacoes where Num_Mec='"+Login.txtUser.getText()+"'and to_char(dta_Refeicao,'dd-mm-yyyy')='"+dataref+"' and Cod_Refeicao='"+jcodref.getText()+"'"; 
				        	P_prato_cod=conn_prato_cod.prepareStatement(sql);
				        	P_prato_cod.executeQuery();
				            }
					       catch(Exception e2)
					        {
					           JOptionPane.showMessageDialog(null,e2); 
					        }
		         }
            }
       }
       catch(Exception e)
        {
           JOptionPane.showMessageDialog(null,e); 
        }
		prencher_marcacoes();
	}
	public void seleciona_linha()
	{
		int row = table.getSelectedRow();
		if (row >= 0)	
		{	
		try{
					String clica_tabela =(table.getModel().getValueAt(row, 0).toString());
					String clica_data =(table.getModel().getValueAt(row, 1)).toString();
					String clica_dta_res =(table.getModel().getValueAt(row, 5)).toString();
					String clica_codigo =(table.getModel().getValueAt(row, 7).toString());
					String clica_pra =(table.getModel().getValueAt(row, 8)).toString(); 
		            String conta="select * from siar.siar_marcacoes where siar.siar_marcacoes.Num_Mecanog='"+clica_tabela+"'and to_char(siar.siar_marcacoes.Dta_Refeicao,'dd-mm-yyyy')='"+clica_data+"'and siar.siar_marcacoes.Cod_refeicao='"+clica_codigo+"'and siar.siar_marcacoes.Cod_prato='"+clica_pra+"'and to_char(siar.siar_marcacoes.Dta_Registo,'dd-mm-yyyy')='"+clica_dta_res+"'";
					pstconn_mar=conn_mar.prepareStatement(conta);
					rs_conn_mar=pstconn_mar.executeQuery();
			           if(rs_conn_mar.next())
			            {
  			        	    String ad1 = rs_conn_mar.getString("Num_Mecanog");
			            	nmec_aux.setText(ad1);
			            	Date ad2 = rs_conn_mar.getDate("Dta_Refeicao");
			            	dta_ref_aux.setText(df2.format(ad2));
			            	String ad3 = rs_conn_mar.getString("Cod_Refeicao");
			            	cod_ref_aux.setText(ad3);
			            	String ad4 = rs_conn_mar.getString("Cod_Prato");
			            	cod_pra_aux.setText(ad4);
			            	Date ad5 = rs_conn_mar.getDate("Dta_Registo");
			            	dta_registo_aux.setText(df2.format(ad5));
			            }
			}
			catch(Exception e)
			{
				nmec_aux.setText(null);
				dta_ref_aux.setText(null);
				cod_ref_aux.setText(null);
				cod_pra_aux.setText(null);
				dta_registo_aux.setText(null);
			}
          }
	}
	public void anula_refeicao()
	{
		String des_horalimite = CH.check_holiday(41);
    	if((nmec_aux.getText().length()==0) || (dta_ref_aux.getText().length()==0) || (cod_ref_aux.getText().length()==0))
    	{
    	 JOptionPane.showMessageDialog(null,"Nenhum Registo Selecionado!");
    	}
     	else
    	{
	        int p = JOptionPane.showConfirmDialog(null, "Deseja Realmente Desmarcar a Refeição!","Refeição Desmarcada!",JOptionPane.YES_NO_OPTION);
	        if(p==0)
	        {
	        	if ((mostra_data.horamin.compareTo(des_horalimite)>=0) && (dta_ref_aux.getText().equals(strDate)))
	        	{
	        		JOptionPane.showMessageDialog(null,"Já passa das "+des_horalimite+", Não pode desmarcar a Refeição!");
	        	}
	        	else
	        	{	
		          try{
			        	String sql="insert into siar.siar_anulacoes(Num_Mec,Cod_Refeicao,Cod_Prato,Dta_Refeicao,Dta_Desativo,Dta_Registo_Inicial,Dta_Registo_final)values(?,?,?,?,?,?,?)"; 
			            pstprato=conn_prato.prepareStatement(sql);
			    		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			            java.util.Date invoiceDate = formatter.parse(dta_ref_aux.getText());
			            java.util.Date invoiceDta_Res = formatter.parse(dta_registo_aux.getText());

			            java.sql.Date sqlDate = new java.sql.Date(invoiceDate.getTime());
			            java.sql.Date sqlDate_res = new java.sql.Date(invoiceDta_Res.getTime());
			            

			            pstprato.setString(1, nmec_aux.getText());
			            pstprato.setString(2,cod_ref_aux.getText());
			            pstprato.setString(3, cod_pra_aux.getText());
			            pstprato.setDate(4,sqlDate);
			            pstprato.setDate(5,now);
			            pstprato.setDate(6,sqlDate_res);
			            pstprato.setDate(7,now);
			            pstprato.executeQuery();
			          }
				       catch(Exception e2)
				      {
				           JOptionPane.showMessageDialog(null,e2); 
				      }
			         try
			         {
			        	String sql="update siar.siar_marcacoes set dta_desativo = sysdate where Num_Mecanog='"+nmec_aux.getText()+"' and to_char(Dta_Refeicao,'dd-mm-yyyy')='"+dta_ref_aux.getText()+"' and Cod_Refeicao='"+cod_ref_aux.getText()+"'";
			        	pstconn_mar=conn_mar.prepareStatement(sql);
			        	pstconn_mar.executeQuery();
			        	JOptionPane.showMessageDialog(null,"Refeição Desmarcada com Sucesso!"); 
			          }
				       catch(Exception e2)
					  {
				           JOptionPane.showMessageDialog(null,e2);
				      }
					   prencher_marcacoes();
					   seleciona_linha();
	        	}	       
	        } 
    	}   
	}
	public void prencher_marcacoes()
    {
		JTextField Jcolzero = new JTextField();
		JTextField Jcolum = new JTextField();
		JTextField Jcoldois = new JTextField();
		JTextField Jcoltres = new JTextField();
		JTextField Jcolquatro = new JTextField();
		JTextField Jcolcinco = new JTextField();
		JTextField Jcolseis = new JTextField();
		@SuppressWarnings("unused")
		int linha = 0;
		btn_anula.setEnabled(true);
     try
      {
    	 String sql="select siar.siar_marcacoes.Num_Mecanog ,to_char(siar.siar_marcacoes.Dta_Refeicao,'dd-mm-yyyy')dtaref,siar.siar_refeicao.Desc_Refeicao,siar.siar_prato.Desc_Prato,siar.siar_marcacoes.Dta_Desativo,to_char(siar.siar_marcacoes.Dta_Registo,'dd-mm-yyyy')dtareg,siar.siar_marcacoes.verificacao,siar.siar_marcacoes.Cod_Refeicao,siar.siar_marcacoes.Cod_prato from siar.siar_marcacoes,siar.siar_refeicao,siar.siar_prato Where siar.siar_marcacoes.Cod_Refeicao=siar.siar_refeicao.Cod_Refeicao and siar.siar_marcacoes.Cod_Prato=siar.siar_prato.Cod_Prato and siar.siar_marcacoes.Num_Mecanog ='"+Login.txtUser.getText()+"' and trunc(siar.siar_marcacoes.Dta_Refeicao) >= trunc(sysdate) and siar.siar_marcacoes.dta_desativo is null order by siar.siar_marcacoes.Dta_Refeicao";
    	 pstprato = conn_prato.prepareStatement(sql); 
         rs_prato=pstprato.executeQuery();
 		 do
 		 {	
 		  table.setModel(DbUtils.resultSetToTableModel(rs_prato));
   	      TableColumn sportColumnzero = table.getColumnModel().getColumn(0);
   	      sportColumnzero.setCellEditor(new DefaultCellEditor(Jcolzero));
   	      table.getColumnModel().getColumn(0).setResizable(false);
   	      table.getColumnModel().getColumn(0).setPreferredWidth(45);
   	      table.getColumnModel().getColumn(0).setHeaderValue("Num Mec.");
   	      table.getColumnModel().getColumn(0).setMinWidth(0);
   	      table.getColumnModel().getColumn(0).setMaxWidth(0);
  	      TableColumn sportColumnum = table.getColumnModel().getColumn(1);
   	      sportColumnum.setCellEditor(new DefaultCellEditor(Jcolum));
   	      table.getColumnModel().getColumn(1).setResizable(false);
   	      table.getColumnModel().getColumn(1).setPreferredWidth(140);
   	      table.getColumnModel().getColumn(1).setHeaderValue("Data da Refeição");
  	      TableColumn sportColumdois = table.getColumnModel().getColumn(2);
   	      sportColumdois.setCellEditor(new DefaultCellEditor(Jcoldois));
   	      table.getColumnModel().getColumn(2).setResizable(false);
   	      table.getColumnModel().getColumn(2).setPreferredWidth(110);
   	      table.getColumnModel().getColumn(2).setHeaderValue("Refeição");
 	      TableColumn sportColumtres = table.getColumnModel().getColumn(3);
   	      sportColumtres.setCellEditor(new DefaultCellEditor(Jcoltres));
   	      table.getColumnModel().getColumn(3).setResizable(false);
   	      table.getColumnModel().getColumn(3).setPreferredWidth(100);
   	      table.getColumnModel().getColumn(3).setHeaderValue("Prato");
 	      TableColumn sportColumquatro = table.getColumnModel().getColumn(4);
   	      sportColumquatro.setCellEditor(new DefaultCellEditor(Jcolquatro));
     	  table.getColumnModel().getColumn(4).setResizable(false);
     	  table.getColumnModel().getColumn(4).setPreferredWidth(130);
     	  table.getColumnModel().getColumn(4).setHeaderValue("Data de Desac.");
     	  table.getColumnModel().getColumn(4).setMinWidth(0);
     	  table.getColumnModel().getColumn(4).setMaxWidth(0);
 	      TableColumn sportColumcinco = table.getColumnModel().getColumn(5);
   	      sportColumcinco.setCellEditor(new DefaultCellEditor(Jcolcinco));
   	      table.getColumnModel().getColumn(5).setResizable(false);
   	      table.getColumnModel().getColumn(5).setPreferredWidth(100);
   	      table.getColumnModel().getColumn(5).setHeaderValue("Data de Reg.");
 	      TableColumn sportColumseis = table.getColumnModel().getColumn(6);
   	      sportColumseis.setCellEditor(new DefaultCellEditor(Jcolseis));
   	      table.getColumnModel().getColumn(6).setResizable(false);
   	      table.getColumnModel().getColumn(6).setPreferredWidth(100);
   	      table.getColumnModel().getColumn(6).setHeaderValue("Verificada");
   	      table.getColumnModel().getColumn(7).setMinWidth(0);
   	      table.getColumnModel().getColumn(7).setMaxWidth(0);
   	      table.getColumnModel().getColumn(8).setMinWidth(0);
   	      table.getColumnModel().getColumn(8).setMaxWidth(0);
         } while (rs_prato.next());
          DefaultTableModel modelo = (DefaultTableModel)table.getModel();
          modelo.setNumRows(30);
          table.getTableHeader().setReorderingAllowed(false);
   	      Jcolzero.setEditable(false);
   	      Jcolum.setEditable(false);
   	      Jcoldois.setEditable(false);
   	      Jcoltres.setEditable(false);
   	      Jcolquatro.setEditable(false);
   	      Jcolcinco.setEditable(false);
   	      Jcolseis.setEditable(false);
          table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
          table.changeSelection(0, 0, false, false);
          linha = table.getSelectedRow();
          table.requestFocus();
      }
         catch(Exception e)
         {
        	 JOptionPane.showMessageDialog(null, "Erro ao Listar na Tabela2!"+e);
         }
      }
	public void marcacoes_desativadas()
	{
		JTextField Jcolzero = new JTextField();
		JTextField Jcolum = new JTextField();
		JTextField Jcoldois = new JTextField();
		JTextField Jcoltres = new JTextField();
		JTextField Jcolquatro = new JTextField();
	    JTextField Jcolcinco = new JTextField();
	    JTextField Jcolseis = new JTextField();	
	    btn_anula.setEnabled(false);
	 try
	  {
		 String sql="select siar.siar_marcacoes.Num_Mecanog ,to_char(siar.siar_marcacoes.Dta_Refeicao,'dd-mm-yyyy')dtaref,siar.siar_refeicao.Desc_Refeicao,siar.siar_prato.Desc_Prato,to_char(siar.siar_marcacoes.Dta_Desativo,'dd-mm-yyyy'),to_char(siar.siar_marcacoes.Dta_Registo,'dd-mm-yyyy')dtareg,siar.siar_marcacoes.verificacao,siar.siar_marcacoes.Cod_Refeicao,siar.siar_marcacoes.Cod_prato from siar.siar_marcacoes,siar.siar_refeicao,siar.siar_prato Where siar.siar_marcacoes.Cod_Refeicao=siar.siar_refeicao.Cod_Refeicao and siar.siar_marcacoes.Cod_Prato=siar.siar_prato.Cod_Prato and siar.siar_marcacoes.Num_Mecanog='"+Login.txtUser.getText()+"' and trunc(siar.siar_marcacoes.Dta_Refeicao) >= trunc(sysdate) and siar.siar_marcacoes.dta_desativo is not null order by siar.siar_marcacoes.Dta_Refeicao"; 
		 pstconn_anuladas=conn_anuladas.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		 rs_conn_anuladas=pstconn_anuladas.executeQuery();
		 do
		 {	
		  table.setModel(DbUtils.resultSetToTableModel(rs_conn_anuladas));	
	      TableColumn sportColumnzero = table.getColumnModel().getColumn(0);
	      sportColumnzero.setCellEditor(new DefaultCellEditor(Jcolzero));
	      table.getColumnModel().getColumn(0).setResizable(false);
	      table.getColumnModel().getColumn(0).setPreferredWidth(45);
	      table.getColumnModel().getColumn(0).setHeaderValue("Num Mec.");
	      table.getColumnModel().getColumn(0).setMinWidth(0);
	      table.getColumnModel().getColumn(0).setMaxWidth(0);
	      TableColumn sportColumnum = table.getColumnModel().getColumn(1);
	      sportColumnum.setCellEditor(new DefaultCellEditor(Jcolum));
	      table.getColumnModel().getColumn(1).setResizable(false);
	      table.getColumnModel().getColumn(1).setPreferredWidth(140);
	      table.getColumnModel().getColumn(1).setHeaderValue("Data da Refeição");
	      TableColumn sportColumdois = table.getColumnModel().getColumn(2);
	      sportColumdois.setCellEditor(new DefaultCellEditor(Jcoldois));
	      table.getColumnModel().getColumn(2).setResizable(false);
	      table.getColumnModel().getColumn(2).setPreferredWidth(110);
	      table.getColumnModel().getColumn(2).setHeaderValue("Refeição");
	      TableColumn sportColumtres = table.getColumnModel().getColumn(3);
	      sportColumtres.setCellEditor(new DefaultCellEditor(Jcoltres));
	      table.getColumnModel().getColumn(3).setResizable(false);
	      table.getColumnModel().getColumn(3).setPreferredWidth(100);
	      table.getColumnModel().getColumn(3).setHeaderValue("Prato");
	      TableColumn sportColumquatro = table.getColumnModel().getColumn(4);
	      sportColumquatro.setCellEditor(new DefaultCellEditor(Jcolquatro));
	 	  table.getColumnModel().getColumn(4).setResizable(false);
	 	  table.getColumnModel().getColumn(4).setPreferredWidth(130);
	 	  table.getColumnModel().getColumn(4).setHeaderValue("Data de Desac.");
	 	  TableColumn sportColumcinco = table.getColumnModel().getColumn(5);
	      sportColumcinco.setCellEditor(new DefaultCellEditor(Jcolcinco));
	      table.getColumnModel().getColumn(5).setResizable(false);
	      table.getColumnModel().getColumn(5).setPreferredWidth(100);
	      table.getColumnModel().getColumn(5).setHeaderValue("Data de Reg.");
	      TableColumn sportColumseis = table.getColumnModel().getColumn(6);
	      sportColumseis.setCellEditor(new DefaultCellEditor(Jcolseis));
	      table.getColumnModel().getColumn(6).setResizable(false);
	      table.getColumnModel().getColumn(6).setPreferredWidth(100);
	      table.getColumnModel().getColumn(6).setHeaderValue("Verificada");
	      table.getColumnModel().getColumn(7).setMinWidth(0);
	      table.getColumnModel().getColumn(7).setMaxWidth(0);
	      table.getColumnModel().getColumn(8).setMinWidth(0);
	      table.getColumnModel().getColumn(8).setMaxWidth(0);
	     }while(rs_conn_anuladas.next());
	      DefaultTableModel modelo = (DefaultTableModel)table.getModel();
	      modelo.setNumRows(30);
	      table.getTableHeader().setReorderingAllowed(false);
		  Jcolzero.setEditable(false);
		  Jcolum.setEditable(false);
		  Jcoldois.setEditable(false);
		  Jcoltres.setEditable(false);
		  Jcolquatro.setEditable(false);
		  Jcolcinco.setEditable(false);
		  Jcolseis.setEditable(false);
	      table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	      table.requestFocus();
	  }
	     catch(Exception e)
	     {
	    	 JOptionPane.showMessageDialog(null, "Erro ao Listar na Tabela3!"+e);
	     }
	}
	public void prencher_marcacoes_Passadas()
	{
		JTextField Jcolzero = new JTextField();
		JTextField Jcolum = new JTextField();
		JTextField Jcoldois = new JTextField();
		JTextField Jcoltres = new JTextField();
		JTextField Jcolquatro = new JTextField();
	    JTextField Jcolcinco = new JTextField();
	    JTextField Jcolseis = new JTextField();	
	    btn_anula.setEnabled(false);
		try
		  {
			 String sql="select siar.siar_marcacoes.Num_Mecanog ,to_char(siar.siar_marcacoes.Dta_Refeicao,'dd-mm-yyyy')dtaref,siar.siar_refeicao.Desc_Refeicao,siar.siar_prato.Desc_Prato,to_char(siar.siar_marcacoes.Dta_Desativo,'dd-mm-yyyy'),to_char(siar.siar_marcacoes.Dta_Registo,'dd-mm-yyyy')dtareg,siar.siar_marcacoes.verificacao,siar.siar_marcacoes.Cod_Refeicao,siar.siar_marcacoes.Cod_prato from siar.siar_marcacoes,siar.siar_refeicao,siar.siar_prato Where siar.siar_marcacoes.Cod_Refeicao=siar.siar_refeicao.Cod_Refeicao and siar.siar_marcacoes.Cod_Prato=siar.siar_prato.Cod_Prato and siar.siar_marcacoes.Num_Mecanog='"+Login.txtUser.getText()+"' and trunc(siar.siar_marcacoes.Dta_Refeicao) < trunc(sysdate) order by siar.siar_marcacoes.Dta_Refeicao desc"; 
			 pstconn_anuladas=conn_anuladas.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			 rs_conn_anuladas=pstconn_anuladas.executeQuery();
			 do
			 {	
			  table.setModel(DbUtils.resultSetToTableModel(rs_conn_anuladas));	
		      TableColumn sportColumnzero = table.getColumnModel().getColumn(0);
		      sportColumnzero.setCellEditor(new DefaultCellEditor(Jcolzero));
		      table.getColumnModel().getColumn(0).setResizable(false);
		      table.getColumnModel().getColumn(0).setPreferredWidth(45);
		      table.getColumnModel().getColumn(0).setHeaderValue("Num Mec.");
		      table.getColumnModel().getColumn(0).setMinWidth(0);
		      table.getColumnModel().getColumn(0).setMaxWidth(0);
		      TableColumn sportColumnum = table.getColumnModel().getColumn(1);
		      sportColumnum.setCellEditor(new DefaultCellEditor(Jcolum));
		      table.getColumnModel().getColumn(1).setResizable(false);
		      table.getColumnModel().getColumn(1).setPreferredWidth(140);
		      table.getColumnModel().getColumn(1).setHeaderValue("Data da Refeição");
		      TableColumn sportColumdois = table.getColumnModel().getColumn(2);
		      sportColumdois.setCellEditor(new DefaultCellEditor(Jcoldois));
		      table.getColumnModel().getColumn(2).setResizable(false);
		      table.getColumnModel().getColumn(2).setPreferredWidth(110);
		      table.getColumnModel().getColumn(2).setHeaderValue("Refeição");
		      TableColumn sportColumtres = table.getColumnModel().getColumn(3);
		      sportColumtres.setCellEditor(new DefaultCellEditor(Jcoltres));
		      table.getColumnModel().getColumn(3).setResizable(false);
		      table.getColumnModel().getColumn(3).setPreferredWidth(100);
		      table.getColumnModel().getColumn(3).setHeaderValue("Prato");
		      TableColumn sportColumquatro = table.getColumnModel().getColumn(4);
		      sportColumquatro.setCellEditor(new DefaultCellEditor(Jcolquatro));
		 	  table.getColumnModel().getColumn(4).setResizable(false);
		 	  table.getColumnModel().getColumn(4).setPreferredWidth(130);
		 	  table.getColumnModel().getColumn(4).setHeaderValue("Data de Desac.");
		 	  TableColumn sportColumcinco = table.getColumnModel().getColumn(5);
		      sportColumcinco.setCellEditor(new DefaultCellEditor(Jcolcinco));
		      table.getColumnModel().getColumn(5).setResizable(false);
		      table.getColumnModel().getColumn(5).setPreferredWidth(100);
		      table.getColumnModel().getColumn(5).setHeaderValue("Data de Reg.");
		      TableColumn sportColumseis = table.getColumnModel().getColumn(6);
		      sportColumseis.setCellEditor(new DefaultCellEditor(Jcolseis));
		      table.getColumnModel().getColumn(6).setResizable(false);
		      table.getColumnModel().getColumn(6).setPreferredWidth(100);
		      table.getColumnModel().getColumn(6).setHeaderValue("Verificada");
		      table.getColumnModel().getColumn(7).setMinWidth(0);
		      table.getColumnModel().getColumn(7).setMaxWidth(0);
		      table.getColumnModel().getColumn(8).setMinWidth(0);
		      table.getColumnModel().getColumn(8).setMaxWidth(0);
		     }while(rs_conn_anuladas.next());
		      DefaultTableModel modelo = (DefaultTableModel)table.getModel();
		      modelo.setNumRows(30);
		      table.getTableHeader().setReorderingAllowed(false);
			  Jcolzero.setEditable(false);
			  Jcolum.setEditable(false);
			  Jcoldois.setEditable(false);
			  Jcoltres.setEditable(false);
			  Jcolquatro.setEditable(false);
			  Jcolcinco.setEditable(false);
			  Jcolseis.setEditable(false);
		      table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		      table.requestFocus();
		  }
		     catch(Exception e)
		     {
		    	 JOptionPane.showMessageDialog(null, "Erro ao Listar na Tabela das Marcações Passadas!"+e);
		     }	
	}
}

