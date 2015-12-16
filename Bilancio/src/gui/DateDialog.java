package gui;

import java.awt.BorderLayout;
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

public class DateDialog extends JDialog implements ActionListener {

	/**
	 * Versione della classe
	 */
	private static final long serialVersionUID = 1L;
	
	private JTable table ;
	private JSpinner dataStartSpinner, dataEndSpinner ;

	public DateDialog(String title, JTable table) {
		JPanel contentPanel = new JPanel();
		setModal(true);
		setTitle(title);
		this.table = table ;
		dialogInit();
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		JPanel panel = new JPanel();
		contentPanel.add(panel);
		JLabel lblDataDiInizio = new JLabel("Data di inizio:");
		panel.add(lblDataDiInizio);
		dataStartSpinner = new JSpinner();
		Calendar calendar = Calendar.getInstance();
		SpinnerDateModel startModel = new SpinnerDateModel(calendar.getTime(), null, null, Calendar.DATE),
				endModel = new SpinnerDateModel(calendar.getTime(), null, null, Calendar.DATE); 
		dataStartSpinner.setModel(startModel);
		panel.add(dataStartSpinner);
		JPanel panel1 = new JPanel();
		contentPanel.add(panel1);
		JLabel lblDataDiFine = new JLabel("Data di fine:");
		panel1.add(lblDataDiFine);
		dataEndSpinner = new JSpinner();
		dataEndSpinner.setModel(endModel);
		panel1.add(dataEndSpinner);
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		JButton okButton = new JButton("OK");
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
		okButton.addActionListener(this);
		cancelButton.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String src = e.getActionCommand();
		if (src.equals("OK")) {
			BilancioTableModel tableModel = (BilancioTableModel)table.getModel();
			Date start = (Date) dataStartSpinner.getModel().getValue(),
					end = (Date) dataEndSpinner.getModel().getValue();
			DateRowFilter<BilancioTableModel, Integer> filter = new DateRowFilter<>(start, end);
			TableRowSorter<BilancioTableModel> sorter = (TableRowSorter<BilancioTableModel>) table.getRowSorter();
			sorter.setRowFilter(filter);
			table.setRowSorter(sorter);
			tableModel.fireTableDataChanged();
		}
		setVisible(false);
	}

}
