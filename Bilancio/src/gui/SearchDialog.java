package gui;

import java.awt.BorderLayout;
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
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;

public class SearchDialog extends JDialog implements ActionListener, DocumentListener {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JButton nextButton, exitButton ;
	private JTable table ;
	private Vector<Integer> occurences ;
	private int lastOccurence ;

	/**
	 * Create the dialog.
	 */
	public SearchDialog(JTable table) {
		this.table = table ;
		lastOccurence = 0 ;
		setAlwaysOnTop(true);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		JLabel lblScriviLaStringa = new JLabel("Scrivi la stringa da cercare");
		lblScriviLaStringa.setHorizontalAlignment(SwingConstants.CENTER);
		contentPanel.add(lblScriviLaStringa, BorderLayout.NORTH);
		textField = new JTextField();
		contentPanel.add(textField, BorderLayout.SOUTH);
		textField.setColumns(10);
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		nextButton = new JButton("Successivo");
		buttonPane.add(nextButton);
		getRootPane().setDefaultButton(nextButton);
		exitButton = new JButton("Esci");
		exitButton.setActionCommand("Cancel");
		buttonPane.add(exitButton);
		pack();
		nextButton.addActionListener(this);
		exitButton.addActionListener(this);
		textField.getDocument().addDocumentListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton src = (JButton) e.getSource();
		if (src.equals(nextButton)) {
			if (occurences == null) {
				String search = textField.getText();
				BilancioTableModel model = (BilancioTableModel)table.getModel();
				occurences = model.searchOccurence(search);
			}
			if (lastOccurence == occurences.size())
				JOptionPane.showMessageDialog(this, "Nessun risultato trovato", null, JOptionPane.INFORMATION_MESSAGE);
			else {
				table.setRowSelectionInterval(occurences.get(lastOccurence), occurences.get(lastOccurence));
				lastOccurence++ ;
			}
		}
		else
			setVisible(false);
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		System.out.println("Resetting changes");
		occurences = null;
		lastOccurence = 0;
		
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		occurences = null;
		lastOccurence = 0;
		
	}

}
