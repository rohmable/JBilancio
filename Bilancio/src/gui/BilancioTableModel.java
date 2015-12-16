package gui;

import java.awt.Color;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import bilancio.*;

/**
 * Table model che gestisce un archivio di Voci
 * @author Mirco Romagnoli
 *
 */
public class BilancioTableModel extends AbstractTableModel {
	/**
	 * Versione della classe
	 */
	private static final long serialVersionUID = 1L;
	/** Archivio delle voci da mostrare nella JTable */
	private Archivio<Voce> voci ;
	/** 
	 * JLabel che viene aggiornata con la somma algebrica dei valori di entrata/uscita delle
	 * righe della tabella
	 */
	private JLabel lblTotale ;
	/** Tabella a cui e' associato il modello */
	private JTable table ;
	/** Nomi delle colonne */
	private final String[] columns = {"Ammontare", "Data", "Descrizione"} ;
	
	/** 
	 * Costruttore del BilancioTableModel
	 * @param voci archivio delle voci del bilancio
	 * @param lblTotale JLabel che contiene il totale dei valori di entrata/uscita delle righe della tabella
	 * @param table tabella a cui e' associato il modello
	 */
	public BilancioTableModel(Archivio<Voce> voci, JLabel lblTotale, JTable table) {
		this.voci = voci ;
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
		return voci.size();
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true ;
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Voce v = voci.get(rowIndex) ;
		switch (columnIndex) {
		case 0:
			Double newVal = (Double)aValue;
			if (v instanceof Entrata && Double.compare(newVal, 0) < 0 )
				return ;
			if (v instanceof Uscita && Double.compare(newVal, 0) >= 0)
				return ;
			v.setAmm((Double)aValue);
			break;
		case 1:
			v.setData((Date) aValue);
			break ;
		case 2:
			v.setDescrizione((String) aValue);
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
			return Date.class ;
		case 2:
			return String.class;
		default:
			return Object.class;
		}
	}
	
	/**
	 * Notifica che la tabella e' stata modificata e che deve essere ridisegnata
	 * <br>
	 * Inoltre viene anche aggiornata la lblTotale che conterra' la somma degli elementi
	 * visibili nella tabella
	 */
	@Override
	public void fireTableDataChanged() {
		super.fireTableDataChanged();
		double tot = 0 ;
		for (int i = 0 ; i < table.getRowCount(); i++)
			tot += voci.get(table.convertRowIndexToModel(i)).getAmm();
		if (tot >= 0)
			lblTotale.setForeground(Color.GREEN);
		else
			lblTotale.setForeground(Color.RED);
		DecimalFormat dFormat = new DecimalFormat("#.00");
		lblTotale.setText(dFormat.format(tot));
	}
	
	/**
	 * @return l'archivio contenuto nel model
	 */
	public Archivio<Voce> getData() {
		return voci ;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Voce v = voci.get(rowIndex) ;
		switch(columnIndex) {
		case 0:
			return v.getAmm() ;
		case 1:
			return v.getData() ;
		case 2:
			return v.getDescrizione() ;
		default:
			return null ;
		}
	}
	
	/**
	 * Ricerca le occorrenze e le inserisce in un vettore
	 * @param occurence stringa da ricercare
	 * @return un vettore contenente gli indici dell'archivio in cui sono contenute le occorrenze
	 * della stringa cercata
	 */
	public Vector<Integer> searchOccurence(String occurence) {
		Vector<Integer> indexes = new Vector<>();
		int index = 0 ;
		for (int i = 0 ; i < voci.size() ; i++){
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
}
