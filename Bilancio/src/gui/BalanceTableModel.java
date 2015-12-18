package gui;

import java.awt.Color;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import bilancio.*;

/**
 * Table model that displays an archive of Voices on a JTable
 * @author Mirco Romagnoli
 * @version {@value #serialVersionUID}
 * @see AbstractTableModel
 */
public class BalanceTableModel extends AbstractTableModel {
	/**
	 * Version of the class
	 */
	private static final long serialVersionUID = 2L;
	/**
	 * Archive of the voices to display on the JTable
	 */
	private Archive<Voice> balance ;
	/** 
	 * JLabel updated with the algebraic sum of the values of revenue/loss
	 * in the table
	 */
	private JLabel lblTotale ;
	/**
	 * Table associated to the model 
	 */
	private JTable table ;
	/**
	 * Column names
	 */
	private final String[] columns = {"Ammontare", "Data", "Descrizione"} ;
	
	/** 
	 * Constructor of the BalanceTableModel
	 * @param balance Archive of the balance
	 * @param lblTotale JLabel that contains the sum of the balance sum
	 * @param table Table associated to the model
	 */
	public BalanceTableModel(Archive<Voice> balance, JLabel lblTotale, JTable table) {
		this.balance = balance ;
		this.lblTotale = lblTotale ;
		this.table = table ;
	}

	@Override
	public String getColumnName(int column)  {
		return columns[column] ;
	}
	
	@Override
	public int getColumnCount() {
		return columns.length;
	}

	@Override
	public int getRowCount() {
		return balance.size();
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true ;
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Voice v = balance.get(rowIndex) ;
		switch (columnIndex) {
		case 0:
			Double newVal = (Double)aValue;
			if (Double.compare(newVal, 0) < 0)
				return ;
			else
				v.setAmount((Double)aValue);
			break;
		case 1:
			try {
				v.setDate((String) aValue);
			} catch (ParseException e) {
				
			}
			break ;
		case 2:
			v.setDescription((String) aValue);
			break ;
		default:
			break;
		}
		fireTableDataChanged();
	}
	
	@Override
	public java.lang.Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return Double.class ;
		case 1:
			return String.class ;
		case 2:
			return String.class;
		default:
			return Object.class;
		}
	}
	
	/**
	 * Notifies that the table has been edited and must be repainted<br>
	 * The lblTotale will be updated with the sum of the visible rows in the table
	 */
	@Override
	public void fireTableDataChanged() {
		super.fireTableDataChanged();
		double tot = 0 ;
		for (int i = 0 ; i < table.getRowCount(); i++)
			tot += balance.get(table.convertRowIndexToModel(i)).getAmount();
		if (tot >= 0)
			lblTotale.setForeground(Color.GREEN);
		else
			lblTotale.setForeground(Color.RED);
		DecimalFormat dFormat = new DecimalFormat("#.00");
		lblTotale.setText(dFormat.format(tot));
	}
	
	/**
	 * @return Archive contained in the model
	 */
	public Archive<Voice> getData() {
		return balance ;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Voice v = balance.get(rowIndex) ;
		switch(columnIndex) {
		case 0:
			return v.getAmount() ;
		case 1:
			return v.getDate().toString() ;
		case 2:
			return v.getDescription() ;
		default:
			return null ;
		}
	}
	
	/**
	 * Searches for occurrences and inserts their index in a vector
	 * @param occurence String to search
	 * @return A Vector containing the indexes of the found occurrences
	 */
	public Vector<Integer> searchOccurence(String occurence) {
		Vector<Integer> indexes = new Vector<>();
		int index = 0 ;
		for (int i = 0 ; i < balance.size() ; i++){
			if (getValueAt(i, 0).toString().contains(occurence)){
				indexes.insertElementAt(i, index);
				index++ ;
			}
			else if (getValueAt(i, 1).toString().contains(occurence)) {
				indexes.insertElementAt(i, index);
				index++ ;
			}
			else if (getValueAt(i, 2).toString().contains(occurence)) {
				indexes.insertElementAt(i, index);
				index++ ;
			}
		}
		return indexes ;
	}
	
	public java.lang.Class<?> getRowClass (int row) {
		return balance.get(row).getClass();
	}
}
