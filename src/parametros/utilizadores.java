package parametros;

import java.awt.Image;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;

import Entities.Product;
import Model.TheModel;
import siar.Administrador;
import siar.JavaConection;
import siar.MyQuery;

public class utilizadores {
	
	public static Connection conn_ativa = null;
	public static PreparedStatement pst_at = null;
	public static ResultSet rs_at = null;
	public static Connection conn_pst_ati_des = null;
	public static CallableStatement  pst_ati_des = null;
	public static PreparedStatement pstinsuti = null;
	public static PreparedStatement pst_des = null;
	public static Connection conn_des = null;
	public static ResultSet rs_des = null;
	static Connection conn_insuti = null;
	static Date now = new Date(System.currentTimeMillis());
	static SimpleDateFormat df2 = new SimpleDateFormat("dd-MM-yyyy");
	public static String strDate = df2.format(now);
	public static final String ATI_DES_UTILIZADOR = "{CALL siar.PKG1.Ati_Des_Uti(?,?)}";
	public static String ad1; 
	public static byte[] IMAGEM=null;
	public static Connection conn_upd = null;
	public static PreparedStatement pst_upd = null;
	public static ResultSet resupd = null;
	public static Connection conn_sel = null;
	public static ResultSet rs_sel = null;
	public static PreparedStatement pst_sel = null;
	public static String filename = null;
	
	public static void prencheUtilizadores()
	{
		Administrador.button_4.setVisible(false);
		Administrador.button_3.setVisible(true);
		MyQuery mq = new MyQuery();
		ArrayList<Product> list = mq.BindTable();
		String[] columnName = {"Num.Mec.","Nome do Utilizador","Senha","Dta.Desativo","Email","Imagem"}; 
		Object [][] rows = new Object[list.size()][6];
		for(int i = 0; i < list.size(); i++)
			{
				rows[i][0] = list.get(i).getNmec();
				rows[i][1] = list.get(i).getN_uti();
				rows[i][2] = "*********";
				//rows[i][2] = list.get(i).getSenha();
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
		Administrador.table_2.setModel(model);
		Administrador.table_2.setRowHeight(30);
		Administrador.table_2.getColumnModel().getColumn(0).setPreferredWidth(25);
		Administrador.table_2.getColumnModel().getColumn(1).setPreferredWidth(170);
		Administrador.table_2.getColumnModel().getColumn(2).setPreferredWidth(60);
		Administrador.table_2.getColumnModel().getColumn(3).setPreferredWidth(35);
		Administrador.table_2.getColumnModel().getColumn(4).setPreferredWidth(100);
		Administrador.table_2.getColumnModel().getColumn(5).setPreferredWidth(80);
		Administrador.table_2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		Administrador.table_2.getTableHeader().setReorderingAllowed(false);
	}
	public static void pesq_utilizador()
	{
		Administrador.button_3.setVisible(false);
		Administrador.button_4.setVisible(true);
		MyQuery mq = new MyQuery();
		ArrayList<Product> list = mq.pesq_utilizador(Administrador.Nome_Uti.getText());
		String[] columnName = {"Número Mec.","Nome do Utilizador","Senha","Dta.Desativo","Email","Imagem"}; 
		Object [][] rows = new Object[list.size()][6];
			for(int i = 0; i < list.size(); i++)
			{
				rows[i][0] = list.get(i).getNmec();
				rows[i][1] = list.get(i).getN_uti();
				rows[i][2] = "*********";
				//rows[i][2] = list.get(i).getSenha();
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
			Administrador.table_2.setModel(model);
			Administrador.table_2.setRowHeight(30);
			Administrador.table_2.getColumnModel().getColumn(0).setPreferredWidth(25);
			Administrador.table_2.getColumnModel().getColumn(1).setPreferredWidth(170);
			Administrador.table_2.getColumnModel().getColumn(2).setPreferredWidth(60);
			Administrador.table_2.getColumnModel().getColumn(3).setPreferredWidth(35);
			Administrador.table_2.getColumnModel().getColumn(4).setPreferredWidth(100);
			Administrador.table_2.getColumnModel().getColumn(5).setPreferredWidth(80);
			Administrador.table_2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			Administrador.table_2.getTableHeader().setReorderingAllowed(false);
	}
	public static void desativa_uti()
    {
		conn_ativa = JavaConection.ConnecrDb();
		if((Administrador.nmec_aux.getText().length()==0)||(Administrador.nome_aux.getText().length()==0))
    	{
    	 JOptionPane.showMessageDialog(null,"Nenhum Registo Selecionado!");
    	}
    	else
    	{		
		   try
		     {
     			 String sql="update siar.siar_utilizadores set dta_desativo = sysdate, activo = 'N' where siar.siar_utilizadores.num_mecanog='"+Administrador.nmec_aux.getText()+"'";
		    	 pst_at=conn_ativa.prepareStatement(sql); 
		    	 rs_at=pst_at.executeQuery();
		    	 utilizadores.prencheUtilizadores();
		     }
	         catch(Exception e)
	         {
	        	 JOptionPane.showMessageDialog(null, "Erro ao desativar utilizador!"+e);
	         }
    	}
     }
	public static void ativa_desat() throws SQLException
    {
		conn_pst_ati_des = JavaConection.ConnecrDb();
		pst_ati_des = conn_pst_ati_des.prepareCall(ATI_DES_UTILIZADOR);
		pst_ati_des.setString(1,strDate);
		pst_ati_des.registerOutParameter(2, Types.VARCHAR);
		pst_ati_des.execute();
    }
	public static void Insere_Utilizador() throws SQLException
	{
		conn_insuti = JavaConection.ConnecrDb();
		String email_Pattern = "^[a-zA-Z0-9_+&*-]+(?:\\."+  
				               "[a-zA-Z0-9_+&*-]+)*@" + 
				               "(?:[a-zA-Z0-9-]+\\.)+[a-z"+ 
				               "A-Z]{2,7}$";      
        Pattern pattern = Pattern.compile(email_Pattern);
		Matcher regexMaster = pattern.matcher(Administrador.Email.getText());
		if((Administrador.Nome_Uti.getText().length()==0))
    	{
    	 JOptionPane.showMessageDialog(null,"Deve Introduzir o Nome!");
    	}
    	if((Administrador.Email.getText().length()==0))
    	{
    	 JOptionPane.showMessageDialog(null,"Deve Introduzir o Email!");
    	}
		if((Administrador.img.getText().length()==0))
		{
			JOptionPane.showMessageDialog(null,"Deve Introduzir a Imagem!");	
		}
		else if((!regexMaster.matches())) 
		{
		    JOptionPane.showMessageDialog(null, "Formato email "+Administrador.Email.getText()+", inválido!");
		}
    	else
    	{	
			CallableStatement cstmt = conn_insuti.prepareCall("BEGIN ? := siar.GERA_PASS_USER_NOME(?); END;");
			String sql="insert into siar.siar_utilizadores(Num_mecanog,Nome_Utilizador,Senha,Dta_Desativo,Creation_Date,Email,Imagem_nome,Imagem)values(?,?,?,?,?,?,?,?)"; 
	        pstinsuti=conn_insuti.prepareStatement(sql);
	        atua_seq();
	        pstinsuti.setString(1, ad1);
	        pstinsuti.setString(2,Administrador.Nome_Uti.getText());
	        cstmt.registerOutParameter(1,Types.VARCHAR);
	        cstmt.setString(2,Administrador.Nome_Uti.getText());
	        cstmt.execute();
	        String output = cstmt.getString(1);
	        Administrador.Senha.setText(output);
	        pstinsuti.setString(3, output);
	        pstinsuti.setDate(4, null); 
	        pstinsuti.setDate(5,now);
	        pstinsuti.setString(6,Administrador.Email.getText());
	        pstinsuti.setString(7,Administrador.img.getText());
	        pstinsuti.setBytes(8,IMAGEM);
	        pstinsuti.executeQuery();
	        JOptionPane.showMessageDialog(null,"Dados Inseridos com Sucesso!");
	        Administrador.Nome_Uti.setText(null);
	        Administrador.Senha.setText(null);
	        Administrador.Email.setText(null);
	        Administrador.Jlnome.setIcon(new ImageIcon());
    	}
    } 
	public static void atua_seq()
    {
		conn_des = JavaConection.ConnecrDb();
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
        	 JOptionPane.showMessageDialog(null, "Erro ao atualizar Sequ�ncia!"+e);
         }
      }	
	public static void ativa_uti()
    {
		conn_des = JavaConection.ConnecrDb();
		if((Administrador.nmec_aux.getText().length()==0)||(Administrador.nome_aux.getText().length()==0))
    	{
    	 JOptionPane.showMessageDialog(null,"Nenhum Registo Selecionado!");
    	}
    	else
    	{
			try
		      {
		    	 String sql="update siar.siar_utilizadores set dta_desativo = null, activo = 'S' where siar.siar_utilizadores.num_mecanog='"+Administrador.nmec_aux.getText()+"'";
		    	 pst_des = conn_des.prepareStatement(sql); 
		    	 rs_des=pst_des.executeQuery();
		    	 utilizadores.prencheUtilizadores();
		      }
	         catch(Exception e)
	         {
	        	 JOptionPane.showMessageDialog(null, "Erro ao desativar utilizador!"+e);
	         }
    	}
      }
	public static void atualiza_uti()
	{
		conn_upd = JavaConection.ConnecrDb();
		String email_Pattern = "^[a-zA-Z0-9_+&*-]+(?:\\."+  
	               "[a-zA-Z0-9_+&*-]+)*@" + 
	               "(?:[a-zA-Z0-9-]+\\.)+[a-z"+ 
	               "A-Z]{2,7}$";  
		Pattern pattern = Pattern.compile(email_Pattern);
		Matcher regexMaster = pattern.matcher(Administrador.email_aux.getText());
		if((Administrador.nmec_aux.getText().length()==0)||(Administrador.nome_aux.getText().length()==0))
    	{
    	    JOptionPane.showMessageDialog(null,"Nenhum Registo Selecionado!");
    	}
		else if((!regexMaster.matches())) 
		{
		    JOptionPane.showMessageDialog(null, "Formato email "+Administrador.email_aux.getText()+", inválido!");
		} 
		else
		{		
		   try
		     {
			   String sql="update siar.siar_utilizadores set nome_utilizador = ?,email= ? ,imagem_nome= ?,imagem= ? where siar.siar_utilizadores.num_mecanog='"+Administrador.nmec_aux.getText()+"'"; 
		       pst_upd=conn_upd.prepareStatement(sql); 
		       pst_upd.setString(1,Administrador.nome_aux.getText());
		       pst_upd.setString(2,Administrador.email_aux.getText());
		       pst_upd.setString(3,Administrador.img.getText());
		       pst_upd.setBytes(4,IMAGEM);	 
		       resupd=pst_upd.executeQuery();
		       utilizadores.prencheUtilizadores();
		       Administrador.Jlnome.setIcon(new ImageIcon());
		     }
	         catch(Exception e)
	         {
	        	 JOptionPane.showMessageDialog(null, "Erro ao atualizar informação do utilizador!"+e);
	         }
    	}
	}
	public static void seleciona_linha()
	{
		conn_sel = JavaConection.ConnecrDb();
		int row = Administrador.table_2.getSelectedRow();
		if (row >= 0)	
		{	
		try
		 {
					String clica_tabela =(Administrador.table_2.getModel().getValueAt(row, 0).toString());
    				ImageIcon image1 = (ImageIcon)Administrador.table_2.getValueAt(row, 5);
		            String conta="select siar.siar_utilizadores.Num_Mecanog Num_Mecanog,siar.siar_utilizadores.nome_utilizador Nome_Utilizador,siar.siar_utilizadores.senha pass, Dta_Desativo dta_des,siar.siar_utilizadores.email email,siar.siar_utilizadores.imagem from siar.siar_utilizadores "
		            		+ " where siar.siar_utilizadores.num_mecanog='"+clica_tabela+"'";
		            pst_sel=conn_sel.prepareStatement(conta);
					rs_sel =pst_sel.executeQuery();
			           if(rs_sel.next())
			            {
  			        	    String ad1 =rs_sel.getString("Num_Mecanog");
  			        	    Administrador.nmec_aux.setText(ad1);
  			        	    String ad2 =rs_sel.getString("Nome_Utilizador");
  			        	    Administrador.nome_aux.setText(ad2);
  			        	    if(Administrador.table_2.getModel().getValueAt(row, 4)!= null)
  			        	    {
  	  			        	    String ad3 =rs_sel.getString("email");
  	  			        	    Administrador.email_aux.setText(ad3);
  			        	    }
  			        	    else
  			        	    {
  			        	    	Administrador.email_aux.setText("");
  			        	    }
  							if(Administrador.table_2.getValueAt(row, 5)!= null)
  							{
	  							Image image2 = image1.getImage().getScaledInstance(Administrador.Jlnome.getWidth(),Administrador.Jlnome.getHeight(),Image.SCALE_SMOOTH);
	  							ImageIcon image3 = new ImageIcon(image2);
	  							Administrador.lblimg2.setIcon(image3);
  							}
  							else
  							{
  								Administrador.lblimg2.setIcon(new ImageIcon());
  							}
			            }
			}
			catch(Exception e)
			{
				Administrador.nmec_aux.setText("");
				Administrador.nome_aux.setText("");
				Administrador.email_aux.setText("");
				JOptionPane.showMessageDialog(null,e+"erro ao selecionar");
			}
          }
	}
	public static void upload_imagem_upd()
	{
		JFileChooser chooser=new JFileChooser();
		chooser.showOpenDialog(null);
		File f =chooser.getSelectedFile();
		Administrador.lblimg2.setIcon(new ImageIcon(f.toString()));
		filename=f.getAbsolutePath();
				try 
				{
					File image = new File(filename);
					@SuppressWarnings("resource")
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
	public static void upload_imagem()
	{
		JFileChooser chooser=new JFileChooser();
		chooser.showOpenDialog(null);
		File f =chooser.getSelectedFile();
		Administrador.Jlnome.setIcon(new ImageIcon(f.toString()));
		filename=f.getAbsolutePath();
		Administrador.img.setText(filename);
				try 
				{
					File image = new File(filename);
					@SuppressWarnings("resource")
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

}
