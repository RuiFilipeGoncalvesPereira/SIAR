package siar;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import Check_Validation.Check_Holiday;
import Check_Validation.Check_Meals_Served_Not_Served;
import dados_auxiliares.GetName;
import net.proteanit.sql.DbUtils;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JCheckBox;
public class Gestor_Refeicoes extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public static JLabel lblnum,lblNome;
	public static JTable table;
	static Connection conn_verifica= null;
	Connection conn_mar=null;
	static ResultSet rs_conn_verifica= null;
	static PreparedStatement pstconn_verifica = null;
	ResultSet rs_conn_mar= null;
	PreparedStatement pstconn_mar = null;
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
	static Check_Holiday CH = new Check_Holiday();
	static Data mostra_data;
	SimpleDateFormat df2 = new SimpleDateFormat("dd-MM-yyyy");
	//System.out.println(formatter.format(currentTime)); // 13:05:56;

  
	/**
	 * Launch the application.
	 */
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

	/**
	 * Create the frame.
	 */
	public Gestor_Refeicoes() {
		setAutoRequestFocus(false);
		conn_verifica = JavaConection.ConnecrDb();
		conn_mar = JavaConection.ConnecrDb();
	    mostra_data = new Data();
        mostra_data.le_data();  
        mostra_data.le_hora();
		initialize();
		marcacoes_diarias();
		GetName Gn = new GetName();
		@SuppressWarnings("static-access")
		String nome = Gn.GETNAME(Integer.parseInt(Login.txtUser.getText()));
		Gestor_Refeicoes.lblNome.setText(nome);
		lblnum.setVisible(false);
		Check_corr_ref.setEnabled(false);
		Check_corr_ref.setVisible(false);
	}
	private void initialize() 
	{
		setTitle("Gest�o de Refeições"); 
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
		button.setIcon(new ImageIcon("C:\\Users\\Rui Pereira\\Documents\\Icons_Geral\\Icons\\001_20.gif"));
		button.setBounds(10, 24, 43, 36);
		contentPane.add(button);
		
		JButton bt_mar = new JButton("");
		bt_mar.setIcon(new ImageIcon("C:\\Users\\Rui Pereira\\Documents\\Icons_Geral\\Icons\\001_44.gif"));
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
		scrollPane.setBounds(63, 53, 572, 327);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent arg0) 
			{
				seleciona_linha();	
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
		button_1.setIcon(new ImageIcon("C:\\Users\\Rui Pereira\\Documents\\Icons_Geral\\Icons\\pdf.gif"));
		button_1.setToolTipText("");
		button_1.setBounds(10, 96, 43, 36);
		contentPane.add(button_1);
		
		JButton bt_check = new JButton("");
		bt_check.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				marcacoes_diarias();
				Check_corr_ref.setVisible(false);
				Check_corr_ref.setEnabled(false);
				Check_ref.setVisible(true);
				Check_ref.setEnabled(true);
			}
		});
		bt_check.setIcon(new ImageIcon("C:\\Users\\Rui Pereira\\Documents\\Icons_Geral\\Icons\\checkin.gif"));
		bt_check.setToolTipText("Refei\u00E7\u00F5es N\u00E3o Verificadas");
		bt_check.setBounds(10, 132, 43, 36);
		contentPane.add(bt_check);
		
		JButton bt_n_check = new JButton("");
		bt_n_check.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				marcacoes_diarias_checadas();
				Check_ref.setVisible(false);
				Check_ref.setEnabled(false);
				Check_corr_ref.setVisible(true);
				Check_corr_ref.setEnabled(true);
			}
		});
		bt_n_check.setIcon(new ImageIcon("C:\\Users\\Rui Pereira\\Documents\\Icons_Geral\\Icons\\checkout.gif"));
		bt_n_check.setToolTipText("Refei\u00E7\u00F5es Verificadas");
		bt_n_check.setBounds(10, 168, 43, 36);
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
		
		Check_ref = new JCheckBox("Valida Refei\u00E7\u00E3o");
		Check_ref.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Check_Meals_Served_Not_Served.Re_Servidas();
			}
		});
		Check_ref.setBounds(63, 24, 240, 23);
		contentPane.add(Check_ref);
		
		Check_corr_ref = new JCheckBox("Corrige Valida\u00E7\u00F5es");
		Check_corr_ref.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Check_Meals_Served_Not_Served.Ref_N_Servidas();
			}
		});
		Check_corr_ref.setBounds(63, 24, 240, 23);
		contentPane.add(Check_corr_ref);
	}
	public static void marcacoes_diarias()
	{
		JTextField Jcolzero = new JTextField();
		JTextField Jcolum = new JTextField();
		JTextField Jcoldois = new JTextField();
		JTextField Jcoltres = new JTextField();
		JTextField Jcolquatro = new JTextField();
	    JTextField Jcolcinco = new JTextField();
	    @SuppressWarnings("unused")
		int linha = 0;
	 try
	  {
		String horamin = CH.check_holiday(81);
		String horamax = CH.check_holiday(83);
		String horaminja = CH.check_holiday(84);
		String horamaxja = CH.check_holiday(85);
		if ((mostra_data.horamin.compareTo(horamin)>=0) && (mostra_data.horamin.compareTo(horamax)<0))//&& (dta_ref_aux.getText().equals(strDate)))
		{
		 //JOptionPane.showMessageDialog(null, "S� pode validar Refeições entre as "+CH.check_holiday(81)+" e as "+CH.check_holiday(83));
			 String sql="select siar.siar_marcacoes.Num_Mecanog,siar.siar_utilizadores.nome_utilizador,to_char(siar.siar_marcacoes.Dta_Refeicao,'dd-mm-yyyy')dtaref,siar.siar_refeicao.Desc_Refeicao,siar.siar_prato.Desc_Prato,to_char(siar.siar_marcacoes.Dta_Registo,'dd-mm-yyyy')dtareg,siar.siar_marcacoes.Cod_Refeicao,siar.siar_marcacoes.Cod_prato from siar.siar_marcacoes,siar.siar_refeicao,siar.siar_prato,siar.siar_utilizadores Where siar.siar_marcacoes.Cod_Refeicao=siar.siar_refeicao.Cod_Refeicao and siar.siar_marcacoes.Cod_Prato=siar.siar_prato.Cod_Prato and siar.siar_marcacoes.Num_Mecanog=siar.siar_utilizadores.Num_Mecanog and trunc(siar.siar_marcacoes.Dta_Refeicao) = trunc(sysdate) and siar.siar_marcacoes.verificacao = 'N' and siar.siar_marcacoes.Cod_Refeicao = 1 order by siar.siar_marcacoes.Dta_Registo desc"; 
			 pstconn_verifica=conn_verifica.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			 rs_conn_verifica=pstconn_verifica.executeQuery();
			 do
			 {	
			  table.setModel(DbUtils.resultSetToTableModel(rs_conn_verifica));	
		      TableColumn sportColumnzero = table.getColumnModel().getColumn(0);
		      sportColumnzero.setCellEditor(new DefaultCellEditor(Jcolzero));
		      table.getColumnModel().getColumn(0).setResizable(false);
		      table.getColumnModel().getColumn(0).setPreferredWidth(45);
		      table.getColumnModel().getColumn(0).setHeaderValue("N�");
		      TableColumn sportColumnum = table.getColumnModel().getColumn(1);
		      sportColumnum.setCellEditor(new DefaultCellEditor(Jcolum));
		      table.getColumnModel().getColumn(1).setResizable(false);
		      table.getColumnModel().getColumn(1).setPreferredWidth(340);
		      table.getColumnModel().getColumn(1).setHeaderValue("Nome");
		      TableColumn sportColumdois = table.getColumnModel().getColumn(2);
		      sportColumdois.setCellEditor(new DefaultCellEditor(Jcoldois));
		      table.getColumnModel().getColumn(2).setResizable(false);
		      table.getColumnModel().getColumn(2).setPreferredWidth(140);
		      table.getColumnModel().getColumn(2).setHeaderValue("Dta Ref.");
		      TableColumn sportColumtres = table.getColumnModel().getColumn(3);
		      sportColumtres.setCellEditor(new DefaultCellEditor(Jcoltres));
		      table.getColumnModel().getColumn(3).setResizable(false);
		      table.getColumnModel().getColumn(3).setPreferredWidth(110);
		      table.getColumnModel().getColumn(3).setHeaderValue("Refeição");
		      TableColumn sportColumquatro = table.getColumnModel().getColumn(4);
		      sportColumquatro.setCellEditor(new DefaultCellEditor(Jcolquatro));
		      table.getColumnModel().getColumn(4).setResizable(false);
		      table.getColumnModel().getColumn(4).setPreferredWidth(100);
		      table.getColumnModel().getColumn(4).setHeaderValue("Prato");
		   	  TableColumn sportColumcinco = table.getColumnModel().getColumn(5);
		      sportColumcinco.setCellEditor(new DefaultCellEditor(Jcolcinco));
		      table.getColumnModel().getColumn(5).setResizable(false);
		      table.getColumnModel().getColumn(5).setPreferredWidth(100);
		      table.getColumnModel().getColumn(5).setHeaderValue("Dta Reg.");
		     }while(rs_conn_verifica.next());	
        }
		else if((mostra_data.horamin.compareTo(horaminja)>=0) && (mostra_data.horamin.compareTo(horamaxja)<0))
		{
			 String sql="select siar.siar_marcacoes.Num_Mecanog,siar.siar_utilizadores.nome_utilizador,to_char(siar.siar_marcacoes.Dta_Refeicao,'dd-mm-yyyy')dtaref,siar.siar_refeicao.Desc_Refeicao,siar.siar_prato.Desc_Prato,to_char(siar.siar_marcacoes.Dta_Registo,'dd-mm-yyyy')dtareg,siar.siar_marcacoes.Cod_Refeicao,siar.siar_marcacoes.Cod_prato from siar.siar_marcacoes,siar.siar_refeicao,siar.siar_prato,siar.siar_utilizadores Where siar.siar_marcacoes.Cod_Refeicao=siar.siar_refeicao.Cod_Refeicao and siar.siar_marcacoes.Cod_Prato=siar.siar_prato.Cod_Prato and siar.siar_marcacoes.Num_Mecanog=siar.siar_utilizadores.Num_Mecanog and trunc(siar.siar_marcacoes.Dta_Refeicao) = trunc(sysdate) and siar.siar_marcacoes.verificacao = 'N' and siar.siar_marcacoes.Cod_Refeicao = 2 order by siar.siar_marcacoes.Dta_Registo desc"; 
			 pstconn_verifica=conn_verifica.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			 rs_conn_verifica=pstconn_verifica.executeQuery();
			 do
			 {	
			  table.setModel(DbUtils.resultSetToTableModel(rs_conn_verifica));	
		      TableColumn sportColumnzero = table.getColumnModel().getColumn(0);
		      sportColumnzero.setCellEditor(new DefaultCellEditor(Jcolzero));
		      table.getColumnModel().getColumn(0).setResizable(false);
		      table.getColumnModel().getColumn(0).setPreferredWidth(45);
		      table.getColumnModel().getColumn(0).setHeaderValue("N�");
		      TableColumn sportColumnum = table.getColumnModel().getColumn(1);
		      sportColumnum.setCellEditor(new DefaultCellEditor(Jcolum));
		      table.getColumnModel().getColumn(1).setResizable(false);
		      table.getColumnModel().getColumn(1).setPreferredWidth(340);
		      table.getColumnModel().getColumn(1).setHeaderValue("Nome");
		      TableColumn sportColumdois = table.getColumnModel().getColumn(2);
		      sportColumdois.setCellEditor(new DefaultCellEditor(Jcoldois));
		      table.getColumnModel().getColumn(2).setResizable(false);
		      table.getColumnModel().getColumn(2).setPreferredWidth(140);
		      table.getColumnModel().getColumn(2).setHeaderValue("Dta Ref.");
		      TableColumn sportColumtres = table.getColumnModel().getColumn(3);
		      sportColumtres.setCellEditor(new DefaultCellEditor(Jcoltres));
		      table.getColumnModel().getColumn(3).setResizable(false);
		      table.getColumnModel().getColumn(3).setPreferredWidth(110);
		      table.getColumnModel().getColumn(3).setHeaderValue("Refeição");
		      TableColumn sportColumquatro = table.getColumnModel().getColumn(4);
		      sportColumquatro.setCellEditor(new DefaultCellEditor(Jcolquatro));
		      table.getColumnModel().getColumn(4).setResizable(false);
		      table.getColumnModel().getColumn(4).setPreferredWidth(100);
		      table.getColumnModel().getColumn(4).setHeaderValue("Prato");
		   	  TableColumn sportColumcinco = table.getColumnModel().getColumn(5);
		      sportColumcinco.setCellEditor(new DefaultCellEditor(Jcolcinco));
		      table.getColumnModel().getColumn(5).setResizable(false);
		      table.getColumnModel().getColumn(5).setPreferredWidth(100);
		      table.getColumnModel().getColumn(5).setHeaderValue("Dta Reg.");
		      table.getColumnModel().getColumn(6).setMinWidth(0);
		      table.getColumnModel().getColumn(6).setMaxWidth(0);
		      table.getColumnModel().getColumn(7).setMinWidth(0);
		      table.getColumnModel().getColumn(7).setMaxWidth(0);
		     }while(rs_conn_verifica.next());
		}	 
		 else
		 {
			 String sql="select siar.siar_marcacoes.Num_Mecanog,siar.siar_utilizadores.nome_utilizador,to_char(siar.siar_marcacoes.Dta_Refeicao,'dd-mm-yyyy')dtaref,siar.siar_refeicao.Desc_Refeicao,siar.siar_prato.Desc_Prato,to_char(siar.siar_marcacoes.Dta_Registo,'dd-mm-yyyy')dtareg,siar.siar_marcacoes.Cod_Refeicao,siar.siar_marcacoes.Cod_prato from siar.siar_marcacoes,siar.siar_refeicao,siar.siar_prato,siar.siar_utilizadores Where siar.siar_marcacoes.Cod_Refeicao=siar.siar_refeicao.Cod_Refeicao and siar.siar_marcacoes.Cod_Prato=siar.siar_prato.Cod_Prato and siar.siar_marcacoes.Num_Mecanog=siar.siar_utilizadores.Num_Mecanog and trunc(siar.siar_marcacoes.Dta_Refeicao) = trunc(sysdate) and siar.siar_marcacoes.verificacao = 'N' order by siar.siar_marcacoes.Dta_Registo desc"; 
			 pstconn_verifica=conn_verifica.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			 rs_conn_verifica=pstconn_verifica.executeQuery();
			 do
			 {	
			  table.setModel(DbUtils.resultSetToTableModel(rs_conn_verifica));	
		      TableColumn sportColumnzero = table.getColumnModel().getColumn(0);
		      sportColumnzero.setCellEditor(new DefaultCellEditor(Jcolzero));
		      table.getColumnModel().getColumn(0).setResizable(false);
		      table.getColumnModel().getColumn(0).setPreferredWidth(45);
		      table.getColumnModel().getColumn(0).setHeaderValue("N�");
		      TableColumn sportColumnum = table.getColumnModel().getColumn(1);
		      sportColumnum.setCellEditor(new DefaultCellEditor(Jcolum));
		      table.getColumnModel().getColumn(1).setResizable(false);
		      table.getColumnModel().getColumn(1).setPreferredWidth(340);
		      table.getColumnModel().getColumn(1).setHeaderValue("Nome");
		      TableColumn sportColumdois = table.getColumnModel().getColumn(2);
		      sportColumdois.setCellEditor(new DefaultCellEditor(Jcoldois));
		      table.getColumnModel().getColumn(2).setResizable(false);
		      table.getColumnModel().getColumn(2).setPreferredWidth(140);
		      table.getColumnModel().getColumn(2).setHeaderValue("Dta Ref.");
		      TableColumn sportColumtres = table.getColumnModel().getColumn(3);
		      sportColumtres.setCellEditor(new DefaultCellEditor(Jcoltres));
		      table.getColumnModel().getColumn(3).setResizable(false);
		      table.getColumnModel().getColumn(3).setPreferredWidth(110);
		      table.getColumnModel().getColumn(3).setHeaderValue("Refeição");
		      TableColumn sportColumquatro = table.getColumnModel().getColumn(4);
		      sportColumquatro.setCellEditor(new DefaultCellEditor(Jcolquatro));
		      table.getColumnModel().getColumn(4).setResizable(false);
		      table.getColumnModel().getColumn(4).setPreferredWidth(100);
		      table.getColumnModel().getColumn(4).setHeaderValue("Prato");
		   	  TableColumn sportColumcinco = table.getColumnModel().getColumn(5);
		      sportColumcinco.setCellEditor(new DefaultCellEditor(Jcolcinco));
		      table.getColumnModel().getColumn(5).setResizable(false);
		      table.getColumnModel().getColumn(5).setPreferredWidth(100);
		      table.getColumnModel().getColumn(5).setHeaderValue("Dta Reg.");
		      table.getColumnModel().getColumn(6).setMinWidth(0);
		      table.getColumnModel().getColumn(6).setMaxWidth(0);
		      table.getColumnModel().getColumn(7).setMinWidth(0);
		      table.getColumnModel().getColumn(7).setMaxWidth(0);
		     }while(rs_conn_verifica.next());
		 }
		   	
	      DefaultTableModel modelo = (DefaultTableModel)table.getModel();
	      modelo.setNumRows(30);
	      table.getTableHeader().setReorderingAllowed(false);
		  Jcolzero.setEditable(false);
		  Jcolum.setEditable(false);
		  Jcoldois.setEditable(false);
		  Jcoltres.setEditable(false);
		  Jcolquatro.setEditable(false);
		  Jcolcinco.setEditable(false);
	      table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	      table.changeSelection(0, 0, false, false);
	      linha = table.getSelectedRow();
	      table.requestFocus();
	  }
	     catch(Exception e)
	     {
	    	 JOptionPane.showMessageDialog(null, "Erro ao Listar na Tabela3!"+e);
	     }
	}
	public void seleciona_linha()
	{
		int row = table.getSelectedRow();
		if (row >= 0)	
		{	
		try{
					String clica_tabela =(table.getModel().getValueAt(row, 0).toString());
					String clica_data =(table.getModel().getValueAt(row, 2)).toString();
					String clica_dta_res =(table.getModel().getValueAt(row, 5)).toString();
					String clica_codigo =(table.getModel().getValueAt(row, 6).toString());
					String clica_pra =(table.getModel().getValueAt(row, 7)).toString(); 
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
	public static void marcacoes_diarias_checadas()
	{
		JTextField Jcolzero = new JTextField();
		JTextField Jcolum = new JTextField();
		JTextField Jcoldois = new JTextField();
		JTextField Jcoltres = new JTextField();
		JTextField Jcolquatro = new JTextField();
	    JTextField Jcolcinco = new JTextField();
	    @SuppressWarnings("unused")
		int linha = 0;
	 try
	  {
		 String sql="select siar.siar_marcacoes.Num_Mecanog,siar.siar_utilizadores.nome_utilizador,to_char(siar.siar_marcacoes.Dta_Refeicao,'dd-mm-yyyy')dtaref,siar.siar_refeicao.Desc_Refeicao,siar.siar_prato.Desc_Prato,to_char(siar.siar_marcacoes.Dta_Registo,'dd-mm-yyyy')dtareg,siar.siar_marcacoes.Cod_Refeicao,siar.siar_marcacoes.Cod_prato from siar.siar_marcacoes,siar.siar_refeicao,siar.siar_prato,siar.siar_utilizadores Where siar.siar_marcacoes.Cod_Refeicao=siar.siar_refeicao.Cod_Refeicao and siar.siar_marcacoes.Cod_Prato=siar.siar_prato.Cod_Prato and siar.siar_marcacoes.Num_Mecanog=siar.siar_utilizadores.Num_Mecanog and trunc(siar.siar_marcacoes.Dta_Refeicao) = trunc(sysdate) and siar.siar_marcacoes.verificacao = 'S' order by siar.siar_marcacoes.Dta_Registo desc"; 
		 pstconn_verifica=conn_verifica.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		 rs_conn_verifica=pstconn_verifica.executeQuery();
		 do
		 {	
		  table.setModel(DbUtils.resultSetToTableModel(rs_conn_verifica));	
	      TableColumn sportColumnzero = table.getColumnModel().getColumn(0);
	      sportColumnzero.setCellEditor(new DefaultCellEditor(Jcolzero));
	      table.getColumnModel().getColumn(0).setResizable(false);
	      table.getColumnModel().getColumn(0).setPreferredWidth(45);
	      table.getColumnModel().getColumn(0).setHeaderValue("N�");
	      TableColumn sportColumnum = table.getColumnModel().getColumn(1);
	      sportColumnum.setCellEditor(new DefaultCellEditor(Jcolum));
	      table.getColumnModel().getColumn(1).setResizable(false);
	      table.getColumnModel().getColumn(1).setPreferredWidth(340);
	      table.getColumnModel().getColumn(1).setHeaderValue("Nome");
	      TableColumn sportColumdois = table.getColumnModel().getColumn(2);
	      sportColumdois.setCellEditor(new DefaultCellEditor(Jcoldois));
	      table.getColumnModel().getColumn(2).setResizable(false);
	      table.getColumnModel().getColumn(2).setPreferredWidth(140);
	      table.getColumnModel().getColumn(2).setHeaderValue("Dta Ref.");
	      TableColumn sportColumtres = table.getColumnModel().getColumn(3);
	      sportColumtres.setCellEditor(new DefaultCellEditor(Jcoltres));
	      table.getColumnModel().getColumn(3).setResizable(false);
	      table.getColumnModel().getColumn(3).setPreferredWidth(110);
	      table.getColumnModel().getColumn(3).setHeaderValue("Refeição");
	      TableColumn sportColumquatro = table.getColumnModel().getColumn(4);
	      sportColumquatro.setCellEditor(new DefaultCellEditor(Jcolquatro));
	      table.getColumnModel().getColumn(4).setResizable(false);
	      table.getColumnModel().getColumn(4).setPreferredWidth(100);
	      table.getColumnModel().getColumn(4).setHeaderValue("Prato");
	   	  TableColumn sportColumcinco = table.getColumnModel().getColumn(5);
	      sportColumcinco.setCellEditor(new DefaultCellEditor(Jcolcinco));
	      table.getColumnModel().getColumn(5).setResizable(false);
	      table.getColumnModel().getColumn(5).setPreferredWidth(100);
	      table.getColumnModel().getColumn(5).setHeaderValue("Dta Reg.");
	      table.getColumnModel().getColumn(6).setMinWidth(0);
	      table.getColumnModel().getColumn(6).setMaxWidth(0);
	      table.getColumnModel().getColumn(7).setMinWidth(0);
	      table.getColumnModel().getColumn(7).setMaxWidth(0);
	     }while(rs_conn_verifica.next());
	      DefaultTableModel modelo = (DefaultTableModel)table.getModel();
	      modelo.setNumRows(30);
	      table.getTableHeader().setReorderingAllowed(false);
		  Jcolzero.setEditable(false);
		  Jcolum.setEditable(false);
		  Jcoldois.setEditable(false);
		  Jcoltres.setEditable(false);
		  Jcolquatro.setEditable(false);
		  Jcolcinco.setEditable(false);
	      table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	      table.changeSelection(0, 0, false, false);
	      linha = table.getSelectedRow();
	      table.requestFocus();
	  }
	     catch(Exception e)
	     {
	    	 JOptionPane.showMessageDialog(null, "Erro ao Listar na Tabela3!"+e);
	     }
	}
}
