package EXCEL;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFileChooser;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import parametros.marcacoes;
import siar.Gestor_Refeicoes;

public class EXCEL_EXPORT {
	
	//https://www.youtube.com/watch?v=FLLeDTtFBbQ&list=PL-27958n0CeU8HLeek9p6MRWTaU3w9ejg
	
	public static void Print_Excel_Meals()
	{	
		try {
			JFileChooser jFileChooser = new JFileChooser();
			jFileChooser.showSaveDialog(jFileChooser);
			File saveFile = jFileChooser.getSelectedFile();
			if (saveFile != null)
			{
				saveFile = new File(saveFile.toString()+".xlsx");
				XSSFWorkbook wb = new XSSFWorkbook();
				XSSFSheet sheet = wb.createSheet("Daily_Meals");
				
				XSSFRow rowCol = sheet.createRow(0);
				for(int i =0;i<Gestor_Refeicoes.table.getColumnCount();i++)
				{
					XSSFCell cell = rowCol.createCell(i);
					cell.setCellValue(Gestor_Refeicoes.table.getColumnName(i));
				}	
				for(int j=0;j<Gestor_Refeicoes.table.getRowCount();j++)
				{
					XSSFRow row = sheet.createRow(j+1);
					for(int k=0;k<Gestor_Refeicoes.table.getColumnCount();k++)
					{
						XSSFCell cell = row.createCell(k);
						System.out.println(Gestor_Refeicoes.table.getValueAt(j,k).toString());
						if(Gestor_Refeicoes.table.getValueAt(j, k)!= null)
						{
							 cell.setCellValue(Gestor_Refeicoes.table.getValueAt(j,k).toString());
						}
					}
				}
				FileOutputStream out = new FileOutputStream(new File(saveFile.toString()));
				wb.write(out);
				wb.close();
				out.close();
				marcacoes.openfile(saveFile.toString());
			}else
			{
				System.out.println("Error Generete File");	
			}
		}
	    catch(FileNotFoundException e)
		{
	    	System.out.println(e);
		}
		catch(IOException io)
		{
			System.out.println(io);
		}
	}
}
