package parametros;

import java.util.ArrayList;

import javax.swing.ListSelectionModel;

import siar.Marcacao;
import siar.Marcacoes;
import siar.MyQuery_Marc;
import siar.TheModel;

public class marcacoes {
	
	public static void prencher_marcacoes()
	{
		MyQuery_Marc mq = new MyQuery_Marc();
		ArrayList<Marcacao> list = mq.Mostra_Marcacoes();
		String[] columnName = {"Num. Mec.","Data da Refeição","Refeição","Prato","Data de Desat.","Data de Reg.","Verificada","Cod.Ref.","Cod.Pra."}; 
		Object [][] rows = new Object[list.size()][9];
		for(int i = 0; i < list.size(); i++)
			{
				rows[i][0] = list.get(i).getNum_mec();
				rows[i][1] = list.get(i).getDta_ref();
				rows[i][2] = list.get(i).getDes_ref();
				rows[i][3] = list.get(i).getDes_pr();
				rows[i][4] = list.get(i).getDta_des();
				rows[i][5] = list.get(i).getDta_reg();
				rows[i][6] = list.get(i).getVerec();
				rows[i][7] = list.get(i).getCod_ref();
				rows[i][8] = list.get(i).getCod_pr();
			}
		TheModel model = new TheModel(rows, columnName);
		Marcacoes.table.setModel(model);
		Marcacoes.table.setRowHeight(30);
		Marcacoes.table.getColumnModel().getColumn(0).setPreferredWidth(45);
		Marcacoes.table.getColumnModel().getColumn(0).setMinWidth(0);
		Marcacoes.table.getColumnModel().getColumn(0).setMaxWidth(0);
		Marcacoes.table.getColumnModel().getColumn(1).setPreferredWidth(140);
		Marcacoes.table.getColumnModel().getColumn(2).setPreferredWidth(110);
		Marcacoes.table.getColumnModel().getColumn(3).setPreferredWidth(100);
		Marcacoes.table.getColumnModel().getColumn(4).setPreferredWidth(130);
		Marcacoes.table.getColumnModel().getColumn(4).setMinWidth(0);
		Marcacoes.table.getColumnModel().getColumn(4).setMaxWidth(0);
		Marcacoes.table.getColumnModel().getColumn(5).setPreferredWidth(240);
		Marcacoes.table.getColumnModel().getColumn(6).setPreferredWidth(80);
		Marcacoes.table.getColumnModel().getColumn(7).setPreferredWidth(0);
		Marcacoes.table.getColumnModel().getColumn(8).setPreferredWidth(0);
		Marcacoes.table.getColumnModel().getColumn(7).setMinWidth(0);
		Marcacoes.table.getColumnModel().getColumn(7).setMaxWidth(0);
		Marcacoes.table.getColumnModel().getColumn(8).setMinWidth(0);
		Marcacoes.table.getColumnModel().getColumn(8).setMaxWidth(0);
		Marcacoes.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		Marcacoes.table.getTableHeader().setReorderingAllowed(false);
	}
	public static void prencher_marcacoes_Passadas()
	{
		MyQuery_Marc mq = new MyQuery_Marc();
		ArrayList<Marcacao> list = mq.Mostra_MarcacoesPassadas();
		String[] columnName = {"Num. Mec.","Data da Refeição","Refeição","Prato","Data de Desat.","Data de Reg.","Verificada","Cod.Ref.","Cod.Pra."}; 
		Object [][] rows = new Object[list.size()][9];
		for(int i = 0; i < list.size(); i++)
			{
				rows[i][0] = list.get(i).getNum_mec();
				rows[i][1] = list.get(i).getDta_ref();
				rows[i][2] = list.get(i).getDes_ref();
				rows[i][3] = list.get(i).getDes_pr();
				rows[i][4] = list.get(i).getDta_des();
				rows[i][5] = list.get(i).getDta_reg();
				rows[i][6] = list.get(i).getVerec();
				rows[i][7] = list.get(i).getCod_ref();
				rows[i][8] = list.get(i).getCod_pr();
			}
		TheModel model = new TheModel(rows, columnName);
		Marcacoes.table.setModel(model);
		Marcacoes.table.setRowHeight(30);
		Marcacoes.table.getColumnModel().getColumn(0).setPreferredWidth(45);
		Marcacoes.table.getColumnModel().getColumn(0).setMinWidth(0);
		Marcacoes.table.getColumnModel().getColumn(0).setMaxWidth(0);
		Marcacoes.table.getColumnModel().getColumn(1).setPreferredWidth(140);
		Marcacoes.table.getColumnModel().getColumn(2).setPreferredWidth(110);
		Marcacoes.table.getColumnModel().getColumn(3).setPreferredWidth(100);
		Marcacoes.table.getColumnModel().getColumn(4).setPreferredWidth(130);
		Marcacoes.table.getColumnModel().getColumn(4).setMinWidth(0);
		Marcacoes.table.getColumnModel().getColumn(4).setMaxWidth(0);
		Marcacoes.table.getColumnModel().getColumn(5).setPreferredWidth(140);
		Marcacoes.table.getColumnModel().getColumn(6).setPreferredWidth(80);
		Marcacoes.table.getColumnModel().getColumn(7).setPreferredWidth(0);
		Marcacoes.table.getColumnModel().getColumn(8).setPreferredWidth(0);
		Marcacoes.table.getColumnModel().getColumn(7).setMinWidth(0);
		Marcacoes.table.getColumnModel().getColumn(7).setMaxWidth(0);
		Marcacoes.table.getColumnModel().getColumn(8).setMinWidth(0);
		Marcacoes.table.getColumnModel().getColumn(8).setMaxWidth(0);
		Marcacoes.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		Marcacoes.table.getTableHeader().setReorderingAllowed(false);
	}
	public static void prencher_marcacoes_Desativadas()
	{
		MyQuery_Marc mq = new MyQuery_Marc();
		ArrayList<Marcacao> list = mq.Mostra_MarcacoesDesativdas();
		String[] columnName = {"Num. Mec.","Data da Refeição","Refeição","Prato","Data de Desat.","Dat.Reg.","Verificada","Cod.Ref.","Cod.Pra."}; 
		Object [][] rows = new Object[list.size()][9];
		for(int i = 0; i < list.size(); i++)
			{
				rows[i][0] = list.get(i).getNum_mec();
				rows[i][1] = list.get(i).getDta_ref();
				rows[i][2] = list.get(i).getDes_ref();
				rows[i][3] = list.get(i).getDes_pr();
				rows[i][4] = list.get(i).getDta_des();
				rows[i][5] = list.get(i).getDta_reg();
				rows[i][6] = list.get(i).getVerec();
				rows[i][7] = list.get(i).getCod_ref();
				rows[i][8] = list.get(i).getCod_pr();
			}
		TheModel model = new TheModel(rows, columnName);
		Marcacoes.table.setModel(model);
		Marcacoes.table.setRowHeight(30);
		Marcacoes.table.getColumnModel().getColumn(0).setPreferredWidth(45);
		Marcacoes.table.getColumnModel().getColumn(0).setMinWidth(0);
		Marcacoes.table.getColumnModel().getColumn(0).setMaxWidth(0);
		Marcacoes.table.getColumnModel().getColumn(1).setPreferredWidth(140);
		Marcacoes.table.getColumnModel().getColumn(2).setPreferredWidth(110);
		Marcacoes.table.getColumnModel().getColumn(3).setPreferredWidth(100);
		Marcacoes.table.getColumnModel().getColumn(4).setPreferredWidth(130);
		Marcacoes.table.getColumnModel().getColumn(5).setPreferredWidth(140);
		Marcacoes.table.getColumnModel().getColumn(6).setPreferredWidth(80);
		Marcacoes.table.getColumnModel().getColumn(7).setPreferredWidth(0);
		Marcacoes.table.getColumnModel().getColumn(8).setPreferredWidth(0);
		Marcacoes.table.getColumnModel().getColumn(7).setMinWidth(0);
		Marcacoes.table.getColumnModel().getColumn(7).setMaxWidth(0);
		Marcacoes.table.getColumnModel().getColumn(8).setMinWidth(0);
		Marcacoes.table.getColumnModel().getColumn(8).setMaxWidth(0);
		Marcacoes.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		Marcacoes.table.getTableHeader().setReorderingAllowed(false);
	}

}
