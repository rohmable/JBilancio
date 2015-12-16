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
import bilancio.Archive;
import bilancio.Voice;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JComboBox;

/**
 * Main window, containing:<ul>
 * <li> JTable with balance voices </li>
 * <li> JComboBox to filter the balance voices in the table </li>
 * <li> JMenu to save, search, print and export </li>
 * <li> JButton to add and remove balance voices </li>
 * </ul>
 * @author Mirco Romagnoli
 * @version {@value #serialVersionUID}
 * @see JFrame
 * @see ActionListener
 */
public class MainWindow extends JFrame implements ActionListener {

	/**
	 * Version of the class
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Table that displays balance voices
	 */
	private JTable table;
	/**
	 * Archive that contains balance voices
	 */
	private Archive<Voice> balance ;
	/**
	 * Table model associated to the table
	 */
	private BalanceTableModel tableModel ;

	/**
	 * Builds the window
	 * @param title Title of the window
	 * @param balance Balance to display on the table
	 */
	public MainWindow(String title, Archive<Voice> balance ) {
		// Building the frame
		super(title) ;
		this.balance = balance ;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 375);
		
		// Building the menu
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenuItem mntmSave = new JMenuItem("Salva");
		menuBar.add(mntmSave);
		JMenuItem mntmSearch = new JMenuItem("Cerca");
		menuBar.add(mntmSearch);
		JMenuItem mntmPrint = new JMenuItem("Stampa");
		menuBar.add(mntmPrint);
		JMenu mnExport = new JMenu("Esporta");
		menuBar.add(mnExport);
		// Adding elements to mnExport
		JMenuItem mntmFileCsv = new JMenuItem("File CSV");
		mnExport.add(mntmFileCsv);
		JMenuItem mntmTextFile = new JMenuItem("File di testo");
		mnExport.add(mntmTextFile);
		JMenuItem mntmFileOds = new JMenuItem("File ODS");
		mnExport.add(mntmFileOds);
		JMenuItem mntmFileExcel = new JMenuItem("File Excel");
		mnExport.add(mntmFileExcel);
		
		// Building the principal panel
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		// Inserting the table at the center of the main panel
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		table = new JTable();
		scrollPane.setViewportView(table);
		
		// Building the panel that contains the amount
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));
		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.WEST);
		JLabel lblAmount = new JLabel("Ammontare:");
		panel_1.add(lblAmount);
		JLabel lblTotal = new JLabel("Totale");
		panel_1.add(lblTotal);
		// Initializing the table model now that we have all the elements
		tableModel = new BalanceTableModel(balance, lblTotal, table);
		table.setModel(tableModel);
		table.setAutoCreateRowSorter(true);
		// Setting the renderer and updating the table model
		table.setDefaultRenderer(Double.class, new TableAmountRenderer());
		tableModel.fireTableDataChanged();
		
		// Building the button panel
		JPanel panel_2 = new JPanel();
		panel.add(panel_2, BorderLayout.EAST);
		JButton btnRemoveVoice = new JButton("Rimuovi Voce");
		btnRemoveVoice.addActionListener(this);
		panel_2.add(btnRemoveVoice);
		JButton btnAddVoice = new JButton("Aggiungi Voce");
		btnAddVoice.addActionListener(this);
		panel_2.add(btnAddVoice);
		
		// Building the combo box
		JComboBox<String> comboBox = new JComboBox<>();
		comboBox.addItem("Tutto");
		comboBox.addItem("Giorno");
		comboBox.addItem("Settimana");
		comboBox.addItem("Mese");
		comboBox.addItem("Anno");
		comboBox.addItem("Seleziona periodo");
		comboBox.addActionListener(new ComboBoxListener(table));
		contentPane.add(comboBox, BorderLayout.NORTH);
		
		// Linking lister to menu elements
		MenuActionListener mActionListener = new MenuActionListener(balance, table);
		mntmSave.addActionListener(mActionListener);
		mntmSearch.addActionListener(mActionListener);
		mntmPrint.addActionListener(mActionListener);
		mntmFileCsv.addActionListener(mActionListener);
		mntmTextFile.addActionListener(mActionListener);
		mntmFileExcel.addActionListener(mActionListener);
		mntmFileOds.addActionListener(mActionListener);
	}
	
	/**
	 * @return The BalanceTableModel associated to the table
	 */
	public BalanceTableModel getTableModel() {
		return tableModel;
	}
	
	/**
	 * @return The balance shown by the table
	 */
	public Archive<Voice> getBilancio() {
		return balance;
	}

	/**
	 * Listener linked to the JButtons to add and remove a balance voice
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String src = e.getActionCommand();
		if (src.equals("Aggiungi Voce")) {
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
					balance.remove(index);
					tableModel.fireTableDataChanged();
				}
			}
			else
				JOptionPane.showMessageDialog(this, "E' necessario selezionare una riga della tabella",
						"Errore", JOptionPane.ERROR_MESSAGE);
		}
	}
}
