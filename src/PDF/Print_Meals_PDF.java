package PDF;

import java.io.FileOutputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import siar.Gestor_Refeicoes;

public class Print_Meals_PDF {
	
	public static SimpleDateFormat df2 = new SimpleDateFormat("dd-MM-yyyy");
	public static Date now = new Date(System.currentTimeMillis());
	public static String strDate = df2.format(now);
	public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
	public static LocalTime currentTime = LocalTime.now();
	
	public static void Print_PDF_Meals()
	{
	    //Imprimir valores de items em relat�rios
	   //string value1=txt_empregado.getText();
	  //string value2=txt_empregadoid.getText();
	 //string value3=combo_age.getSelectedItem().toString();		
			
	 int count=Gestor_Refeicoes.table.getRowCount();
	 String path ="";
	 String numero ="Número";
	 String nome ="Nome";
	 String Dta_Refeicao ="Data Refeição";
	 String Refeicao ="Refeição";
	 String Prato ="Prato";
	 String dta_reg ="Data Registo";
	 String value1="";
	 String value2="";
	 String value3="";
	 String value4="";
	 String value5="";
	 String value6="";
 	if(count == 0)
 	{
 		JOptionPane.showMessageDialog(null,"There is  no meals to Print");
 	}
	else
	{
		JFileChooser j= new JFileChooser();
		j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int x = j.showSaveDialog(null);
		
		if(x==JFileChooser.APPROVE_OPTION)
		{	
		 path = j.getSelectedFile().getPath(); 				
	    }	
		try{
			Document document=new Document(PageSize.A4);
			PdfWriter.getInstance(document,new FileOutputStream(path+"\\"+"Refeicoes_DIA.pdf"));
			document.open();
			Image logo = Image.getInstance("C:\\Icons_Geral\\logo.png");
            document.add(logo);
			PdfPTable tab=new PdfPTable(6);
			PdfPCell cell = new PdfPCell(new Paragraph("Refeições"));
			cell.setColspan(6);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(BaseColor.BLUE);
			tab.addCell(cell);

			tab.addCell(numero);
			tab.addCell(nome);
			tab.addCell(Dta_Refeicao);
			tab.addCell(Refeicao);
			tab.addCell(Prato);
			tab.addCell(dta_reg);
			for(int i=0;i<count;i++)
			{
			Object obj1 = GetData(Gestor_Refeicoes.table, i, 0);
			Object obj2 = GetData(Gestor_Refeicoes.table, i, 1);
			Object obj3 = GetData(Gestor_Refeicoes.table, i, 2);
			Object obj4 = GetData(Gestor_Refeicoes.table, i, 3);
			Object obj5 = GetData(Gestor_Refeicoes.table, i, 4);
			Object obj6 = GetData(Gestor_Refeicoes.table, i, 5);
			
			if (obj1 != null)
			{	
			 value1=obj1.toString();
			}
			else
			{
			 value1= null;
			}
			if (obj2 != null)
			{	
			 value2=obj2.toString();
			}
			else
			{
			 value2= null;
			}
			if (obj3 != null)
			{	
			 value3=obj3.toString();
			}
			else
			{
			 value3= null;
			}
			if (obj4 != null)
			{	
			 value4=obj4.toString();
			}
			else
			{
			 value4= null;
			}
			if (obj5 != null)
			{	
			 value5=obj5.toString();
			}
			else
			{
			 value5= null;
			}
			if (obj6 != null)
			{	
			 value6=obj6.toString();
			}
			else
			{
			 value6= null;
			}
			tab.addCell(value1);
			tab.addCell(value2);
			tab.addCell(value3);
			tab.addCell(value4);
			tab.addCell(value5);
			tab.addCell(value6);
			}
			Paragraph title = new Paragraph("Refeições do dia",FontFactory.getFont(FontFactory.TIMES_BOLD,18,Font.BOLD,BaseColor.RED));
		    title.setAlignment(Element.ALIGN_CENTER);
			document.add(title);
		    Paragraph rtime = new Paragraph("Produzido por "+Gestor_Refeicoes.lblNome.getText()+" em " + strDate + " ás " + formatter.format(currentTime),FontFactory.getFont(FontFactory.TIMES_BOLD,11,Font.BOLD,BaseColor.BLACK));
			rtime.setAlignment(Element.ALIGN_CENTER);
			document.add(rtime);
		    Paragraph rtime2 = new Paragraph("\n"); 
			document.add(rtime2);
			document.add(tab);
			//Colocar uma lista de itens num report
			/*com.itextpdf.text.List list = new com.itextpdf.text.List(true,20);
			list.add("First Item");
			list.add("Second Item");
			list.add("Third Item");
			list.add("Fourth Item");
			list.add("Five Item");
			list.add("Sixth Item");
			document.add(list);*/
			
			//Colocar uma imagem num relat�rio
			/*com.itextpdf.text.Image image1 = com.itextpdf.text.Image.getInstance("C:\\Users\\Rui Pereira\\eclipse-workspace\\AlimentacaoJava_Demo\\Img\\chart.png");
			image1.setRotationDegrees(45.0f);
			image1.scaleAbsolute(480, 300);
			document.add(image1);*/
			
			document.close();
			//https://www.tabnine.com/code/java/methods/com.itextpdf.text.Document/newPage
			//https://www.youtube.com/watch?v=74pE3kLerAU&list=PLS1QulWo1RIayTWHnCGE_ttQG1_ajCBI4&index=15
            //https://pt.stackoverflow.com/questions/340500/banco-de-dados-trocando-acento-por
			//https://www.youtube.com/@easy2excel927/playlists
		 }
		catch(Exception e2){
			return;
		 }
	}
		//Juntar dois Relat�rios PDF
		/*try
		 {
			 String path_ ="";	
			 JFileChooser j_= new JFileChooser();
				j_.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int x_ = j_.showSaveDialog(null);
				
				if(x_==JFileChooser.APPROVE_OPTION)
				{	
				 path_ = j_.getSelectedFile().getPath(); 				
			    }	
			PdfReader report = new PdfReader(path_+"\\"+"Refeicoes_DIA.pdf");
			PdfReader report1 = new PdfReader(path_+"\\"+"Refeicoes_DIA_2.pdf"); 
			PdfCopyFields copy = new PdfCopyFields(new FileOutputStream(path_+"\\"+"Joined_Report.pdf"));
			copy.addDocument(report); 
			copy.addDocument(report1);
			copy.close();
			 JOptionPane.showMessageDialog(null,"Copy Saved!");
		 }
		 catch(Exception e3) 
		 {
			 return;
		 }*/
	}	
	public static Object GetData(JTable table, int row_index, int col_index){
		return table.getModel().getValueAt(row_index, col_index);
		}
}
