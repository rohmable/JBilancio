package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * Dialog that allows to make incremental searches on the balance table
 * @author Mirco Romagnoli
 * @see JDialog
 * @see ActionListener
 * @see DocumentListener
 * @version {@value #serialVersionUID}
 *
 */
public class SearchDialog extends JDialog implements ActionListener, DocumentListener {

	/**
	 * Version of the class
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Text field to that hosts the text of the research
	 */
	private JTextField textField;
	/**
	 * Table to search
	 */
	private JTable table ;
	/**
	 * Vector of the indexes of the correspondences
	 */
	private Vector<Integer> occurences ;
	/**
	 * Index of the last found element
	 */
	private int lastOccurence ;

	/**
	 * Builds the dialog window
	 * @param table Table to search
	 */
	public SearchDialog(JTable table) {
		// Building the dialog
		this.table = table ;
		dialogInit();
		setAlwaysOnTop(true);
		JPanel contentPanel = new JPanel();
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		
		// Building the research area
		JLabel lblStringSearch = new JLabel("Scrivi la stringa da cercare");
		lblStringSearch.setHorizontalAlignment(SwingConstants.CENTER);
		contentPanel.add(lblStringSearch, BorderLayout.NORTH);
		textField = new JTextField();
		contentPanel.add(textField, BorderLayout.SOUTH);
		textField.setColumns(10);
		
		// Building the button panel
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		JButton nextButton = new JButton("Successivo");
		buttonPane.add(nextButton);
		getRootPane().setDefaultButton(nextButton);
		JButton exitButton = new JButton("Esci");
		exitButton.setActionCommand("Cancel");
		buttonPane.add(exitButton);
		nextButton.addActionListener(this);
		exitButton.addActionListener(this);
		textField.getDocument().addDocumentListener(this);
		
		// Initializing the search variables
		lastOccurence = 0 ;
		occurences = null ;
		
		pack();
		Component parent = table.getTopLevelAncestor();
		setLocationRelativeTo(parent);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String src = e.getActionCommand();
		if (src.equals("Successivo")) {
			if (occurences == null) { // If the vector is not null the research is not made
				String search = textField.getText();
				BalanceTableModel model = (BalanceTableModel)table.getModel();
				occurences = model.searchOccurence(search);
			}
			if (lastOccurence == occurences.size()) // Restart the research
				lastOccurence = 0 ;
			else { // The new occurrence is selected on the table
				table.setRowSelectionInterval(occurences.get(lastOccurence), occurences.get(lastOccurence));
				lastOccurence++ ;
			}
		}
		else
			setVisible(false);
	}

	/**
	 * Unused listener
	 */
	@Override
	public void changedUpdate(DocumentEvent e) {
	}

	/**
	 * If the text of the research is changed the vector of the occurences and the index
	 * are reset
	 */
	@Override
	public void insertUpdate(DocumentEvent e) {
		occurences = null;
		lastOccurence = 0;
		
	}

	/**
	 * If the text of the research is changed the vector of the occurences and the index
	 * are reset
	 */
	@Override
	public void removeUpdate(DocumentEvent e) {
		occurences = null;
		lastOccurence = 0;
		
	}

}
