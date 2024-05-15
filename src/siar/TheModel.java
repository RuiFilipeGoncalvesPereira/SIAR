package siar;
import javax.swing.Icon;
import javax.swing.table.AbstractTableModel;

public class TheModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private Object[][]rows;
	private String[]columns;
	
	public TheModel() {}
	
	public TheModel(Object[][] data, String[] columnName)
	{
		this.columns = columnName;
		this.rows = data;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Class getColumnClass(int columnIndex)
	{
		if(columnIndex == 5)
		{
			return Icon.class;
		}
		if(columnIndex == 1)
		{
			return int.class;
		}
		if(columnIndex == 2)
		{
			return String.class;
		}
		if(columnIndex == 3)
		{
			return String.class;
		}
		if(columnIndex == 4)
		{
			return String.class;
		}
		else 
		{
			return String.class;
		}
	}

	public int getRowCount() {
		return this.rows.length;
	}

	public int getColumnCount() {
		return this.columns.length;
	}

	public Object getValueAt(int rowIndex, int columnIndex)
	{
       return this.rows[rowIndex][columnIndex];
	}
	
	public String getColumnName(int col)
	{
	  return this.columns[col];	
	}
    public boolean isCellEditable(int row, int col) 
    {
        if (col == 1)
        { 
            return false;
        }
        else if (col == 2)
        { 
            return false;
        }
        else
        {
            return false;
        }
    }
 
}
