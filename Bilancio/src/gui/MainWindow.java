package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

//import com.sun.glass.ui.Menu;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import bilancio.Archivio;
import bilancio.Voce;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JComboBox;

public class MainWindow extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JLabel lblTotale ;
	private JButton btnRimuoviVoce, btnAggiungiVoce ;
	private Archivio<Voce> bilancio ;
	private BilancioTableModel tableModel ;

	/**
	 * Create the frame.
	 */
	public MainWindow(String titolo, Archivio<Voce> bilancio ) {
		super(titolo) ;
		this.bilancio = bilancio ;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 375);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenuItem mntmSalva = new JMenuItem("Salva");
		menuBar.add(mntmSalva);
		
		JMenuItem mntmCerca = new JMenuItem("Cerca");
		menuBar.add(mntmCerca);
		
		JMenuItem mntmStampa = new JMenuItem("Stampa");
		menuBar.add(mntmStampa);
	
		JMenu mnEsporta = new JMenu("Esporta");
		menuBar.add(mnEsporta);
		
		JMenuItem mntmFileCsv = new JMenuItem("File CSV");
		mnEsporta.add(mntmFileCsv);
		
		JMenuItem mntmFileDiTesto = new JMenuItem("File di testo");
		mnEsporta.add(mntmFileDiTesto);
		
		JMenuItem mntmFileOds = new JMenuItem("File ODS");
		mnEsporta.add(mntmFileOds);
		
		JMenuItem mntmFileExcel = new JMenuItem("File Excel");
		mnEsporta.add(mntmFileExcel);
		
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.WEST);
		
		JLabel lblAmmontare = new JLabel("Ammontare:");
		panel_1.add(lblAmmontare);
		
		lblTotale = new JLabel("Totale");
		panel_1.add(lblTotale);
		tableModel = new BilancioTableModel(bilancio, lblTotale, table);
		table.setModel(tableModel);
		table.setAutoCreateRowSorter(true);
		table.setDefaultRenderer(Double.class, new TableAmountRenderer());
		tableModel.fireTableDataChanged();
		
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2, BorderLayout.EAST);
		
		btnRimuoviVoce = new JButton("Rimuovi Voce");
		btnRimuoviVoce.addActionListener(this);
		panel_2.add(btnRimuoviVoce);
		
		btnAggiungiVoce = new JButton("Aggiungi Voce");
		btnAggiungiVoce.addActionListener(this);
		panel_2.add(btnAggiungiVoce);
		
		JComboBox<String> comboBox = new JComboBox<>();
		comboBox.addItem("Tutto");
		comboBox.addItem("Giorno");
		comboBox.addItem("Settimana");
		comboBox.addItem("Mese");
		comboBox.addItem("Anno");
		comboBox.addItem("Seleziona periodo");
		comboBox.addActionListener(new ComboBoxListener(table));
		contentPane.add(comboBox, BorderLayout.NORTH);
		
		MenuActionListener mActionListener = new MenuActionListener(bilancio, menuBar, table);
		mntmSalva.addActionListener(mActionListener);
		mntmCerca.addActionListener(mActionListener);
		mntmStampa.addActionListener(mActionListener);
		mntmFileCsv.addActionListener(mActionListener);
		mntmFileDiTesto.addActionListener(mActionListener);
		mntmFileExcel.addActionListener(mActionListener);
		mntmFileOds.addActionListener(mActionListener);
	}
	
	public BilancioTableModel getTableModel() {
		return tableModel;
	}
	
	public Archivio<Voce> getBilancio() {
		return bilancio;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		JButton src = (JButton) e.getSource();
		if (src.equals(btnAggiungiVoce)) {
			AddVoice dialog = new AddVoice(this, "Aggiungi voce");
			dialog.setVisible(true);
		}
		else {
			int index = table.getSelectedRow() ;
			if (index != -1) {
				Object [] options = {"Si", "No"} ;
				int response = JOptionPane.showOptionDialog(this, "Sei sicuro di voler eliminare la voce?", 
						"Conferma", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, 
						null, options, options[1]) ;
				if (response == JOptionPane.YES_OPTION) {
					bilancio.remove(index);
					tableModel.fireTableDataChanged();
				}
			}
			else
				JOptionPane.showMessageDialog(this, "E' necessario selezionare una riga della tabella",
						"Errore", JOptionPane.ERROR_MESSAGE);
		}
	}
}
