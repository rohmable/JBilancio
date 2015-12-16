package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;


import java.awt.Container;

import bilancio.Archivio;
import bilancio.TableExporter;
import bilancio.Voce;

public class MenuActionListener implements ActionListener {
	private JMenuBar menuBar ;
	private Archivio<Voce> bilancio ;
	private JTable table ;
	
	public MenuActionListener(Archivio<Voce> bilancio, JMenuBar menuBar, JTable table) {
		this.bilancio = bilancio ;
		this.menuBar = menuBar ;
		this.table = table ;
	}
	
	private void saveFile(JMenuItem source) {
		FileChooser fChooser = new FileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("File di bilancio",
				"bil");
		fChooser.setFileFilter(filter);
		fChooser.setMultiSelectionEnabled(false);
		Container parent = source.getTopLevelAncestor();
		int response = fChooser.showSaveDialog(parent);
		if (response != FileChooser.APPROVE_OPTION)
			return ;
		File selectedFile = fChooser.getSelectedFile();
		FileOutputStream file = null ;
		ObjectOutputStream oStream = null ;
		try {
			file = new FileOutputStream(selectedFile) ;
			oStream = new ObjectOutputStream(file);
			oStream.writeObject(bilancio);
			oStream.flush();
			oStream.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(parent, "Errore durante il salvataggio del file: " + e, 
					"Errore", JOptionPane.ERROR_MESSAGE);
			return ;
		}
		JOptionPane.showMessageDialog(parent, "Salvataggio completato", 
				"Successo", JOptionPane.INFORMATION_MESSAGE);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command.equals("Salva")) {
			saveFile((JMenuItem)e.getSource()) ;
		}
		else if (command.equals("Cerca")) {
			SearchDialog dialog = new SearchDialog(table);
			dialog.setVisible(true);
		}
		else if (command.equals("Stampa")) {
			try {
				boolean complete = table.print(JTable.PrintMode.FIT_WIDTH, null, null, true, null, true, null);
				
				if (complete) {
					JOptionPane.showMessageDialog(table.getTopLevelAncestor(), "Stampa completata", "Stampa", JOptionPane.INFORMATION_MESSAGE);
				}
				else
					JOptionPane.showMessageDialog(table.getTopLevelAncestor(), "Stampa cancellata", "Stampa", JOptionPane.INFORMATION_MESSAGE);
			}
			catch (PrinterException pe) {
				JOptionPane.showMessageDialog(table.getTopLevelAncestor(), "Stampa fallita: " + pe, "Stampa", JOptionPane.ERROR_MESSAGE);
				
			}
		}
		else if (command.equals("File CSV")) {
			FileChooser chooser = new FileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV file", ".csv") ;
			chooser.setFileFilter(filter);
			chooser.setMultiSelectionEnabled(false);
			Container panel = ((JMenuItem)e.getSource()).getTopLevelAncestor();
			int response = chooser.showSaveDialog(panel);
			if (response != FileChooser.APPROVE_OPTION)
				return ;
			File selectedFile = chooser.getSelectedFile();
			try {
				TableExporter.exportCSV(table, selectedFile);
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(panel, "Impossibile esportare il file: " + e, 
						"Errore", JOptionPane.ERROR_MESSAGE);
			}
		}
		else if (command.equals("File di testo")) {
			FileChooser chooser = new FileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("File di testo", ".txt") ;
			chooser.setFileFilter(filter);
			chooser.setMultiSelectionEnabled(false);
			Container panel = ((JMenuItem)e.getSource()).getTopLevelAncestor();
			int response = chooser.showSaveDialog(panel);
			if (response != FileChooser.APPROVE_OPTION)
				return ;
			File selectedFile = chooser.getSelectedFile();
			try {
				TableExporter.exportText(table, selectedFile);
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(panel, "Impossibile esportare il file: " + e, 
						"Errore", JOptionPane.ERROR_MESSAGE);
			}
		}
		else if (command.equals("File ODS")) {
			FileChooser chooser = new FileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("File ODS", ".ods") ;
			chooser.setFileFilter(filter);
			chooser.setMultiSelectionEnabled(false);
			Container panel = ((JMenuItem)e.getSource()).getTopLevelAncestor();
			int response = chooser.showSaveDialog(panel);
			if (response != FileChooser.APPROVE_OPTION)
				return ;
			File selectedFile = chooser.getSelectedFile();
			try {
				TableExporter.exportOOP(table, selectedFile);
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(panel, "Impossibile esportare il file: " + e, 
						"Errore", JOptionPane.ERROR_MESSAGE);
			}
		}
		else if (command.equals("File Excel")) {
			FileChooser chooser = new FileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("File Excel", ".xls") ;
			chooser.setFileFilter(filter);
			chooser.setMultiSelectionEnabled(false);
			Container panel = ((JMenuItem)e.getSource()).getTopLevelAncestor();
			int response = chooser.showSaveDialog(panel);
			if (response != FileChooser.APPROVE_OPTION)
				return ;
			File selectedFile = chooser.getSelectedFile();
			try {
				TableExporter.exportExcel(table, selectedFile);
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(panel, "Impossibile esportare il file: " + e, 
						"Errore", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

}
