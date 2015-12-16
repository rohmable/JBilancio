package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableRowSorter;

/**
 * Listener of the JComboBox in the MainWindow
 * @author Mirco Romagnoli
 * @see JComboBox
 * @see ActionListener
 */
public class ComboBoxListener implements ActionListener {
	/**
	 * Table in the MainWindow
	 */
	private JTable table;

	/**
	 * Constructor of the class
	 * @param table Table to filter
	 */
	public ComboBoxListener(JTable table) {
		this.table = table ;
	}
	
	/**
	 * Sets or remove a filter in the table based on the user's choice<br>
	 * If the choice is "Seleziona periodo" a DateDialog appears to le the users
	 * set an arbitrary filter
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JComboBox<String> src =(JComboBox<String>) e.getSource();
		String source = (String)src.getSelectedItem();
		Calendar calendar = Calendar.getInstance(); // Loading today's date
		Date actualDate = calendar.getTime() ;
		// Subtraction of an amount of time based on the user's choice
		if (source.equals("Giorno"))
			calendar.add(Calendar.DAY_OF_MONTH, -1);
		else if (source.equals("Settimana"))
			calendar.add(Calendar.WEEK_OF_MONTH, -1);
		else if (source.equals("Mese"))
			calendar.add(Calendar.MONTH, -1);
		else if (source.equals("Anno"))
			calendar.add(Calendar.YEAR, -1);
		else if (source.equals("Seleziona periodo")) {
			DateDialog dialog = new DateDialog("Seleziona un periodo di tempo", table);
			dialog.setVisible(true);
			return ;
		} 
		else { // If the user choose to see every data of the table the filter is removed
			((TableRowSorter<BalanceTableModel>) table.getRowSorter()).setRowFilter(null);
			((BalanceTableModel)table.getModel()).fireTableDataChanged();
			return ;
		}
		// Setting the filter
		DateRowFilter<BalanceTableModel, Integer> filter = new DateRowFilter<>(calendar.getTime(), actualDate);
		TableRowSorter<BalanceTableModel> sorter = (TableRowSorter<BalanceTableModel>) table.getRowSorter();
		sorter.setRowFilter(filter);
		table.setRowSorter(sorter);
		((BalanceTableModel)table.getModel()).fireTableDataChanged();
	}

}
