package gui;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class SearchResultTable extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private final String[] columnNames = new String[] {"Char", "Pinyin", "Definition", "Level"};
	
	public ArrayList<String[]> tableData;
	
	
	public SearchResultTable(ArrayList<String[]> tableData) {
		this.tableData = tableData;
	}
	
	@Override
	public int getRowCount() {
		return tableData.size();
	}

	@Override
	public int getColumnCount() {
		if (!tableData.isEmpty()) {
			return tableData.get(0).length;
		}
		else {
			return 0;
		}
	}

	public String getColumnName(int columnIndex) {
		return columnNames[columnIndex];
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (!tableData.isEmpty()) {
			return tableData.get(rowIndex)[columnIndex];
		}
		else {
			return null;
		}
	}
	
	@Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }
 
    public void setValueAt(String aValue, int rowIndex, int columnIndex) {
        if (rowIndex <= getRowCount() && columnIndex <= getColumnCount()) {
        	tableData.get(rowIndex)[columnIndex] = aValue;
        	fireTableDataChanged();
        }
    }
    
    public void deleteRow(int rowNumber) {
    	this.tableData.set(rowNumber, new String[] {"", "", "", ""});
        fireTableDataChanged();
    }
    
    public void resetTableData(ArrayList<String[]> tableData) {
        this.tableData = tableData;
        fireTableDataChanged();
    }  
    
    
}
