package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import bilancio.Revenue;
import bilancio.StringDate;
import bilancio.Loss;
import bilancio.Voice;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;

import java.awt.Dimension;
import javax.swing.JRadioButton;

/**
 * Dialog window to add a new voice to the balance<br>
 * The dialog contains two spinners - one for the date and one for the amount - and a
 * text field to insert the description
 * @version {@value #serialVersionUID}
 * @author Mirco Romagnoli
 * @see JDialog
 * @see ActionListener
 */
public class AddVoice extends JDialog implements ActionListener, FocusListener {

	/**
	 * Version of the class
	 */
	private static final long serialVersionUID = 1L;

	/** Spinner for the date */
	private JSpinner dateSpinner ;
	/** Spinner for the amount */
	private JSpinner ammSpinner ;
	/** Text field for the description */
	private JTextField txtpnDescription ;
	/** Radio button to set the voice as a revenue */
	private JRadioButton rdbtnRevenue ;
	/** Radio button to set the voice as a loss */
	private JRadioButton rdbtnLoss ;
	/**
	 * Boolean used to see if the text inside txtpnDescription is
	 * the hint or the user's description
	 */
	private boolean firstWrite ;

	/**
	 * Builds a non modal dialog window, with specified owner and title<br>
	 * The owner can't be {@code null}
	 * @param parent Owner of the dialog
	 * @param title Title of the dialog
	 */
	public AddVoice(Frame parent, String title) {
		// Dialog creation
		super(parent, title);
		setModal(true);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		firstWrite = true ;
		
		// Creation of the panel that will contain every element
		JPanel contentPanel = new JPanel();
		setBounds(100, 100, 450, 300);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		
		// Creation of the panel that will contain the date spinner
		JPanel datePanel = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) datePanel.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEADING); // The elements will be left-aligned
		contentPanel.add(datePanel);
		JLabel lblData = new JLabel("Data");
		datePanel.add(lblData);
		dateSpinner = new JSpinner();
		Calendar calendar = Calendar.getInstance(); // Loading of today's date
		SpinnerDateModel dateModel = new SpinnerDateModel(calendar.getTime(), null, null, Calendar.DATE);
		dateSpinner.setModel(dateModel);
		dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy"));
		datePanel.add(dateSpinner);
		
		// Creation of the panel that will contain the amount spinner
		JPanel amountPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) amountPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEADING); // The elements will be left-aligned
		contentPanel.add(amountPanel);
		JLabel lblAmmontare = new JLabel("Ammontare");
		amountPanel.add(lblAmmontare);
		ammSpinner = new JSpinner();
		ammSpinner.setPreferredSize(new Dimension(125, 30));
		SpinnerNumberModel ammModel = new SpinnerNumberModel(0.0, 0.0, null, 0.01);
		ammSpinner.setModel(ammModel);
		amountPanel.add(ammSpinner);
		// Adding the buttons to the amountPanel to set the revenue or the loss
		rdbtnRevenue = new JRadioButton("Entrata"); 
		amountPanel.add(rdbtnRevenue);				
		rdbtnLoss = new JRadioButton("Uscita");	
		amountPanel.add(rdbtnLoss);				
		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnRevenue);
		group.add(rdbtnLoss);
		rdbtnRevenue.setSelected(true);
		
		// Creation of the panel that will contain the description
		JPanel descriptionPanel = new JPanel();
		contentPanel.add(descriptionPanel);
		txtpnDescription = new JTextField(30);
		txtpnDescription.setForeground(Color.lightGray);
		txtpnDescription.setText("Descrizione");
		descriptionPanel.add(txtpnDescription);
		txtpnDescription.addFocusListener(this);
		
		// Panel that will contain the buttons to control the dialog window
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		JButton okButton = new JButton("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
		JButton cancelButton = new JButton("Cancel");
		buttonPane.add(cancelButton);
		// Adding ActionListeners
		okButton.addActionListener(this);
		cancelButton.addActionListener(this);
		pack();
		setLocationRelativeTo(parent);
	}

	/**
	 * Button's ActionListener of the dialog window
	 * @param e Event thrown by clicking the buttons
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String src = e.getActionCommand();
		if (src.equals("OK")) {
			StringDate selectedDate ;
			try {
				selectedDate = new StringDate((Date)dateSpinner.getValue());
			} catch (ParseException e1) {
				return ;
			}
			double amount = (double)ammSpinner.getValue() ;
			String desc = txtpnDescription.getText();
			Voice newVoice ;
			if (rdbtnRevenue.isSelected())
				newVoice = new Revenue(selectedDate, amount, desc) ;
			else
				newVoice = new Loss(selectedDate, amount, desc) ;
			MainWindow owner = (MainWindow) getOwner();
			owner.getBilancio().add(newVoice);
			owner.getTableModel().fireTableDataChanged();
		}
		setVisible(false);
	}

	/**
	 * Listener used to display a "gray hint" to the user, when the text field
	 * gets the focus it sets the text to an empty string and sets the foreground
	 * to black
	 */
	@Override
	public void focusGained(FocusEvent e) {
		if (firstWrite) {
			txtpnDescription.setText("");
			txtpnDescription.setForeground(Color.black);
			firstWrite = false ;
		}
		
	}

	/**
	 * Unused listener
	 */
	@Override
	public void focusLost(FocusEvent e) {
		
	}
	
	

}
