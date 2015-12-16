package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;


import java.awt.Container;

import bilancio.Archive;
import bilancio.TableExporter;
import bilancio.Voice;
/**
 * Listener for the menu of the MainWindow, executes the action chosen by
 * the user
 * @author Mirco Romagnoli
 * @see ActionListener
 */
public class MenuActionListener implements ActionListener {
	private Archive<Voice> balance ;
	private JTable table ;
	
	/**
	 * @param balance Balance to work with
	 * @param table Table to export
	 */
	public MenuActionListener(Archive<Voice> balance, JTable table) {
		this.balance = balance ;
		this.table = table ;
	}
	
	/**
	 * Function that shows a FileChooser to save the balance on a user's specified
	 * file
	 * @param parent Parent of the FileChooser
	 * @see TableExporter
	 */
	private void saveFile(Container parent) {
		FileChooser fChooser = new FileChooser();
		// Filter for the extensions
		FileNameExtensionFilter filter = new FileNameExtensionFilter("File di bilancio",
				"bil");
		fChooser.setFileFilter(filter);
		fChooser.setMultiSelectionEnabled(false);
		int response = fChooser.showSaveDialog(parent);
		if (response != FileChooser.APPROVE_OPTION)
			return ;
		File selectedFile = fChooser.getSelectedFile();
		FileOutputStream file = null ;
		ObjectOutputStream oStream = null ;
		// Serializing the balance
		try {
			file = new FileOutputStream(selectedFile) ;
			oStream = new ObjectOutputStream(file);
			oStream.writeObject(balance);
			oStream.flush();
			oStream.close();
		} catch (IOException e) {
			// If an IOException is thrown shows an error
			JOptionPane.showMessageDialog(parent, "Errore durante il salvataggio del file: " + e, 
					"Errore", JOptionPane.ERROR_MESSAGE);
			return ;
		}
		// Success message
		JOptionPane.showMessageDialog(parent, "Salvataggio completato", 
				"Successo", JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * Prints the table using a system dialog
	 * @see JTable
	 */
	private void printTable() {
		try {
			boolean complete = table.print(JTable.PrintMode.FIT_WIDTH, null, null, true, null, true, null);
			if (complete)
				JOptionPane.showMessageDialog(table.getTopLevelAncestor(), "Stampa completata", "Stampa", JOptionPane.INFORMATION_MESSAGE);
			else
				JOptionPane.showMessageDialog(table.getTopLevelAncestor(), "Stampa cancellata", "Stampa", JOptionPane.INFORMATION_MESSAGE);
		}
		catch (PrinterException e) {
			JOptionPane.showMessageDialog(table.getTopLevelAncestor(), "Stampa fallita: " + e, "Stampa", JOptionPane.ERROR_MESSAGE);
			
		}
	}
	
	/**
	 * Exports the table in a CSV format
	 * @param panel Parent for the FileChooser
	 * @see TableExporter
	 */
	private void exportCSV(Container panel) {
		FileChooser chooser = new FileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV file", ".csv") ;
		chooser.setFileFilter(filter);
		chooser.setMultiSelectionEnabled(false);
		int response = chooser.showSaveDialog(panel);
		if (response != FileChooser.APPROVE_OPTION)
			return ;
		File selectedFile = chooser.getSelectedFile();
		try {
			TableExporter.exportCSV(table, selectedFile);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(panel, "Impossibile esportare il file: " + e, 
					"Errore", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Exports the table in a text separted by spaces
	 * @param panel Parent for the FileChooser
	 * @see TableExporter
	 */
	private void exportText(Container panel) {
		FileChooser chooser = new FileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("File di testo", ".txt") ;
		chooser.setFileFilter(filter);
		chooser.setMultiSelectionEnabled(false);
		int response = chooser.showSaveDialog(panel);
		if (response != FileChooser.APPROVE_OPTION)
			return ;
		File selectedFile = chooser.getSelectedFile();
		try {
			TableExporter.exportText(table, selectedFile, " ");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(panel, "Impossibile esportare il file: " + e, 
					"Errore", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Exports the table in ODS format
	 * @param panel Parent for the FileChooser
	 * @see TableExporter
	 */
	private void exportLibreOffice(Container panel) {
		FileChooser chooser = new FileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("File ODS", ".ods") ;
		chooser.setFileFilter(filter);
		chooser.setMultiSelectionEnabled(false);
		int response = chooser.showSaveDialog(panel);
		if (response != FileChooser.APPROVE_OPTION)
			return ;
		File selectedFile = chooser.getSelectedFile();
		try {
			TableExporter.exportOOP(table, selectedFile);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(panel, "Impossibile esportare il file: " + e, 
					"Errore", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Exports the table in Excel format
	 * @param panel Parent fort the FileChooser
	 * @see TableExporter
	 */
	private void exportExcel(Container panel) {
		FileChooser chooser = new FileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("File Excel", ".xls") ;
		chooser.setFileFilter(filter);
		chooser.setMultiSelectionEnabled(false);
		int response = chooser.showSaveDialog(panel);
		if (response != FileChooser.APPROVE_OPTION)
			return ;
		File selectedFile = chooser.getSelectedFile();
		try {
			TableExporter.exportExcel(table, selectedFile);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(panel, "Impossibile esportare il file: " + e, 
					"Errore", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * ActionListener to perform the actions provided by the menu of the MainWindow<br>
	 * The actions are: <ul>
	 * <li> Saving the balance </li>
	 * <li> Searching for occurrences in the table </li>
	 * <li> Print </li>
	 * <li> Export the table in these formats: <ul>
	 * 		<li> CSV </li>
	 * 		<li> Text </li>
	 * 		<li> Excel </li>
	 * 		<li> ODS </li></ul></li></ul>
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command.equals("Salva")) {
			Container parent = table.getTopLevelAncestor();
			saveFile(parent) ;
		}
		else if (command.equals("Cerca")) {
			SearchDialog dialog = new SearchDialog(table);
			dialog.setVisible(true);
		}
		else if (command.equals("Stampa")) {
			printTable();
		}
		else if (command.equals("File CSV")) {
			Container panel = table.getTopLevelAncestor();
			exportCSV(panel);
		}
		else if (command.equals("File di testo")) {
			Container panel = table.getTopLevelAncestor();
			exportText(panel);
		}
		else if (command.equals("File ODS")) {
			Container panel = table.getTopLevelAncestor();
			exportLibreOffice(panel);
		}
		else if (command.equals("File Excel")) {
			Container panel = table.getTopLevelAncestor();
			exportExcel(panel);
		}
	}

}
