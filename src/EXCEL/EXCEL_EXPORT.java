package EXCEL;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

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
    	if(Gestor_Refeicoes.table.getRowCount() == 0)
    	{
    		//throw new MealsExeption("There is  no meals to Print");	
    		JOptionPane.showMessageDialog(null,"There is  no meals to Print");
    	}
    	else
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
						if (Gestor_Refeicoes.table.getColumnName(i) != "Cod.Ref." 
						&& Gestor_Refeicoes.table.getColumnName(i) != "Cod.Pra." ) {
							cell.setCellValue(Gestor_Refeicoes.table.getColumnName(i));	
						}
					}	
					for(int j=0;j<Gestor_Refeicoes.table.getRowCount();j++)
					{
						XSSFRow row = sheet.createRow(j+1);
						for(int k=0;k<Gestor_Refeicoes.table.getColumnCount()-2;k++)
						{
							XSSFCell cell = row.createCell(k);
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
					JOptionPane.showMessageDialog(null,"Error Generete File");	
				}
			}
		    catch(FileNotFoundException e)
			{
		    	JOptionPane.showMessageDialog(null,e);
			}
			catch(IOException io)
			{
				JOptionPane.showMessageDialog(null,io);
			}
    	}
	}
}
