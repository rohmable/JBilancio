package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableRowSorter;

/**
 * Listener della JComboBox nella MainWindow
 * @author Mirco Romagnoli
 *
 */
public class ComboBoxListener implements ActionListener {
	/** Tabella contenuta nella MainWindow */
	private JTable table;

	/**
	 * Costruttore della classe
	 * @param table tabella che dovra' essere filtrata
	 */
	public ComboBoxListener(JTable table) {
		this.table = table ;
	}
	
	/**
	 * Imposta o rimuove un filtro alla tabella basato sulla scelta dell'utente
	 * <br>
	 * Se la scelta e' "Seleziona periodo" fa apparire una DateDialog per permettere
	 * all'utente di selezionare un periodo arbitrario di tempo da filtrare
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JComboBox<String> src =(JComboBox<String>) e.getSource();
		String source = (String)src.getSelectedItem();
		Calendar calendar = Calendar.getInstance(); // Caricamento della data attuale
		Date actualDate = calendar.getTime() ;
		// Sottrazione di un periodo a seconda della scelta dell'utente
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
		else { // Se si sceglie di vedere tutti i dati della tabella il filtro viene tolto
			((TableRowSorter<BilancioTableModel>) table.getRowSorter()).setRowFilter(null);
			((BilancioTableModel)table.getModel()).fireTableDataChanged();
			return ;
		}
		// Inserimento del filtro
		DateRowFilter<BilancioTableModel, Integer> filter = new DateRowFilter<>(calendar.getTime(), actualDate);
		TableRowSorter<BilancioTableModel> sorter = (TableRowSorter<BilancioTableModel>) table.getRowSorter();
		sorter.setRowFilter(filter);
		table.setRowSorter(sorter);
		((BilancioTableModel)table.getModel()).fireTableDataChanged();
	}

}
