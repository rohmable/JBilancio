package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableRowSorter;

import bilancio.StringDate;

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
	 * Action commands
	 */
	private final String[] actionCommands = {"Giorno", "Settimana", "Mese", "Anno", "Seleziona periodo", "Tutto"};
	private final int DAY = 0, WEEK = 1, MONTH = 2, YEAR = 3, SELECT_PERIOD = 4, ALL = 5 ;

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
		Calendar c = Calendar.getInstance(); // Loading today's date
		Calendar filterDate = Calendar.getInstance(); // Loading filter's date
		Date actualDate = c.getTime() ;
		// Subtraction of an amount of time based on the user's choice
		if (source.equals(actionCommands[WEEK]))
			c.add(Calendar.DAY_OF_WEEK, -c.get(Calendar.DAY_OF_WEEK) + 2); // The DAY_OF_WEEK starts from saturday
		else if (source.equals(actionCommands[MONTH]))
			c.add(Calendar.DAY_OF_MONTH, -c.get(Calendar.DAY_OF_MONTH) + 1);
		else if (source.equals(actionCommands[YEAR]))
			c.add(Calendar.DAY_OF_YEAR, -c.get(Calendar.DAY_OF_YEAR) + 1);
		else if (source.equals(actionCommands[ALL])) {
			DateDialog dialog = new DateDialog("Seleziona un periodo di tempo", table);
			dialog.setVisible(true);
			return ;
		} 
		else if (source.equals(actionCommands[4])) { // If the user choose to see every data of the table the filter is removed
			((TableRowSorter<BalanceTableModel>) table.getRowSorter()).setRowFilter(null);
			((BalanceTableModel)table.getModel()).fireTableDataChanged();
			return ;
		}
		// Setting the filter			
		filterDate.clear();
		filterDate.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), 
				0, 0, 0);
		DateRowFilter<BalanceTableModel, Integer> filter = new DateRowFilter<>(filterDate.getTime(), actualDate);
		TableRowSorter<BalanceTableModel> sorter = (TableRowSorter<BalanceTableModel>) table.getRowSorter();
		sorter.setRowFilter(filter);
		table.setRowSorter(sorter);
		((BalanceTableModel)table.getModel()).fireTableDataChanged();
	}

}
