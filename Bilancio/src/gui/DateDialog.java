package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableRowSorter;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerDateModel;

/**
 * Dialog window that lets the user apply a filter to a table, basing on
 * a start date and an end date.<br>
 * The window contains two spinners - one for the start date and one for the
 * end date -
 * @version {@value #serialVersionUID}
 * @see JDialog
 * @see ActionListener
 * @see DateRowFilter
 * @author Mirco Romagnoli
 *
 */
public class DateDialog extends JDialog implements ActionListener {

	/**
	 * Class version
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Table to filter
	 */
	private JTable table ;
	/**
	 * Spinner of the start date
	 */
	private JSpinner startDateSpinner ;
	/**
	 * Spinner of the end date
	 */
	private JSpinner endDateSpinner ;

	/**
	 * Builds a non modal dialog window with a title and the table to filter
	 * @param title Title of the dialog window
	 * @param table Table to filter
	 */
	public DateDialog(String title, JTable table) {
		// Creation of the JDialog
		JPanel contentPanel = new JPanel();
		setModal(true);
		setTitle(title);
		this.table = table ;
		dialogInit();
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		// Setting the layout
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		
		// Creation of the start date spinner
		JPanel panel = new JPanel();
		contentPanel.add(panel);
		JLabel lblDataDiInizio = new JLabel("Data di inizio:");
		panel.add(lblDataDiInizio);
		startDateSpinner = new JSpinner();
		Calendar calendar = Calendar.getInstance();
		SpinnerDateModel startModel = new SpinnerDateModel(calendar.getTime(), null, null, Calendar.DATE); 
		startDateSpinner.setModel(startModel);
		startDateSpinner.setEditor(new JSpinner.DateEditor(startDateSpinner, "dd/MM/yyyy"));
		panel.add(startDateSpinner);
		
		// Creation of the end date spinner
		JPanel panel1 = new JPanel();
		contentPanel.add(panel1);
		JLabel lblDataDiFine = new JLabel("Data di fine:");
		panel1.add(lblDataDiFine);
		endDateSpinner = new JSpinner();
		SpinnerDateModel endModel = new SpinnerDateModel(calendar.getTime(), null, null, Calendar.DATE);
		endDateSpinner.setModel(endModel);
		endDateSpinner.setEditor(new JSpinner.DateEditor(endDateSpinner, "dd/MM/yyyy"));
		panel1.add(endDateSpinner);
		
		// Creation of the button panel
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		JButton okButton = new JButton("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
		JButton cancelButton = new JButton("Cancel");
		buttonPane.add(cancelButton);
		okButton.addActionListener(this);
		cancelButton.addActionListener(this);
		pack();
		Component parent = table.getTopLevelAncestor();
		setLocationRelativeTo(parent);
	}

	/**
	 * Action thrown by the buttons of the dialog, the DateRowFilter
	 * is applied using the spinners' values
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String src = e.getActionCommand();
		if (src.equals("OK")) {
			BalanceTableModel tableModel = (BalanceTableModel)table.getModel();
			Date start = (Date) startDateSpinner.getModel().getValue(),
					end = (Date) endDateSpinner.getModel().getValue();
			DateRowFilter<BalanceTableModel, Integer> filter = new DateRowFilter<>(start, end);
			TableRowSorter<BalanceTableModel> sorter = (TableRowSorter<BalanceTableModel>) table.getRowSorter();
			sorter.setRowFilter(filter);
			table.setRowSorter(sorter);
			tableModel.fireTableDataChanged();
		}
		setVisible(false);
	}

}
