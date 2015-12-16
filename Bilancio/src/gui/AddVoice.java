package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import bilancio.Entrata;
import bilancio.Uscita;
import bilancio.Voce;
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
 * Dialogo che permette di aggiungere una voce al bilancio.
 * <p>
 * Il dialogo contiene due spinner - uno per la data ed uno per l'ammontare - e una casella
 * di testo per inserire la descrizione
 * @author Mirco Romagnoli
 *
 */
public class AddVoice extends JDialog implements ActionListener {

	/**
	 * Versione della classe
	 */
	private static final long serialVersionUID = 1L;

	/** Spinner contenente la data */
	private JSpinner dateSpinner ;
	/** Spinner contenente l'ammontare della Voce */
	private JSpinner ammSpinner ;
	/** Campo di testo contenente la descrizione della Voce */
	private JTextField txtpnDescription ;
	/** Radio button per impostare la voce come entrata */
	private JRadioButton rdbtnEntrata ;
	/** Radio button per impostare la voce come uscita */
	private JRadioButton rdbtnUscita ;

	/**
	 * Crea una finestra di dialogo non modale, con un owner ed un titolo specificati
	 * <br>
	 * L'owner non puo' essere {@code null}
	 * @param owner
	 * @param title
	 */
	public AddVoice(Frame owner, String title) {
		// Creazione del dialogo
		super(owner, title);
		setModal(true);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		// Creazione del pannello che conterra' tutti gli elementi
		JPanel contentPanel = new JPanel();
		setBounds(100, 100, 450, 300);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		
		// Creazione del pannello che conterra' lo spinner della data
		JPanel datePanel = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) datePanel.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEADING); // Gli elementi verranno allineati a sinistra
		contentPanel.add(datePanel);
		JLabel lblData = new JLabel("Data");
		datePanel.add(lblData);
		dateSpinner = new JSpinner();
		Calendar calendar = Calendar.getInstance(); // Caricamento della data odierna
		SpinnerDateModel dateModel = new SpinnerDateModel(calendar.getTime(), null, null, Calendar.DATE);
		dateSpinner.setModel(dateModel);
		dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy"));
		datePanel.add(dateSpinner);
		
		// Creazione del pannello che conterra' lo spinner dell' ammontare
		JPanel amountPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) amountPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEADING); // Gli elementi verranno allineati a sinistra
		contentPanel.add(amountPanel);
		JLabel lblAmmontare = new JLabel("Ammontare");
		amountPanel.add(lblAmmontare);
		ammSpinner = new JSpinner();
		ammSpinner.setPreferredSize(new Dimension(125, 30));
		SpinnerNumberModel ammModel = new SpinnerNumberModel(0.0, 0.0, null, 0.01);
		ammSpinner.setModel(ammModel);
		amountPanel.add(ammSpinner);
		// Aggiunta dei bottoni all'amountPanel per impostare entrata o uscita
		rdbtnEntrata = new JRadioButton("Entrata"); 
		amountPanel.add(rdbtnEntrata);				
		rdbtnUscita = new JRadioButton("Uscita");	
		amountPanel.add(rdbtnUscita);				
		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnEntrata);
		group.add(rdbtnUscita);
		rdbtnEntrata.setSelected(true);
		
		// Creazione del pannello che conterra' la descrizione
		JPanel descriptionPanel = new JPanel();
		contentPanel.add(descriptionPanel);
		txtpnDescription = new JTextField(30);
		txtpnDescription.setText("Descrizione");
		descriptionPanel.add(txtpnDescription);
		
		// Pannello che conterra' i bottoni per controllare la finestra di dialogo
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		JButton okButton = new JButton("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
		JButton cancelButton = new JButton("Cancel");
		buttonPane.add(cancelButton);
		// Aggiunta degli ActionListener
		okButton.addActionListener(this);
		cancelButton.addActionListener(this);
		pack();
	}

	/**
	 * Action listener dei bottoni della finestra di dialogo
	 * @param e evento lanciato dal click sui bottoni
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String aCommand = e.getActionCommand();
		if (aCommand.equals("OK")) {
			Date selectedDate = (Date) dateSpinner.getValue() ;
			double amount = (double)ammSpinner.getValue() ;
			String desc = txtpnDescription.getText();
			Voce nuovaVoce ;
			if (rdbtnEntrata.isSelected())
				nuovaVoce = new Entrata(selectedDate, amount, desc) ;
			else
				nuovaVoce = new Uscita(selectedDate, amount, desc) ;
			MainWindow owner = (MainWindow) getOwner();
			owner.getBilancio().add(nuovaVoce);
			owner.getTableModel().fireTableDataChanged();
		}
		setVisible(false);
	}
	
	

}
