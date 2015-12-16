package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import bilancio.Archivio;
import bilancio.Voce;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

public class WelcomeScreen extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JButton btnNuovoBilancio, btnCaricaBilancio, btnEsci ;

	/**
	 * Create the dialog.
	 */
	public WelcomeScreen(String titolo) {
		super(titolo) ;
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		JLabel lblBenvenutoNelGestore = new JLabel("Benvenuto nel gestore di Bilancio, scegli un azione per continuare");
		lblBenvenutoNelGestore.setHorizontalAlignment(SwingConstants.CENTER);
		contentPanel.add(lblBenvenutoNelGestore, BorderLayout.CENTER);
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		btnNuovoBilancio = new JButton("Nuovo Bilancio");
		buttonPane.add(btnNuovoBilancio);
		getRootPane().setDefaultButton(btnNuovoBilancio);
		btnCaricaBilancio = new JButton("Carica Bilancio");
		btnCaricaBilancio.setActionCommand("Carica Bilancio");
		buttonPane.add(btnCaricaBilancio);
		btnEsci = new JButton("Esci");
		buttonPane.add(btnEsci);
		btnNuovoBilancio.addActionListener(this);
		btnCaricaBilancio.addActionListener(this);
		btnEsci.addActionListener(this);
	}
	
	private void caricaBilancio() {
		FileChooser fChooser = new FileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("File di bilancio", "bil");
		fChooser.setFileFilter(filter);
		fChooser.setMultiSelectionEnabled(false);
		int response = fChooser.showOpenDialog(this);
		if (response == FileChooser.APPROVE_OPTION) {
			File file = fChooser.getSelectedFile();
			FileInputStream fStream = null ;
			ObjectInputStream inputStream = null ;
			Archivio<Voce> bil = null ;
			try {
				fStream = new FileInputStream(file);
				inputStream = new ObjectInputStream(fStream);
				bil = (Archivio<Voce>) inputStream.readObject();
				inputStream.close();
			} 
			catch (IOException e) {
				JOptionPane.showMessageDialog(this, "Errore nel caricamento del file: " + e,
						"Errore", JOptionPane.ERROR_MESSAGE);
				return ;
			} 
			catch (ClassNotFoundException e) {
				JOptionPane.showMessageDialog(this, "Errore nel caricamento del file: " + e,
						"Errore", JOptionPane.ERROR_MESSAGE);
				try {
					inputStream.close();
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(this, "Errore nel caricamento del file: " + e,
							"Errore", JOptionPane.ERROR_MESSAGE);
					return ;
				}
				return ;
			}
			MainWindow window = new MainWindow("Gestore Bilancio", bil);
			window.setVisible(true);
			setVisible(false);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton eventButton = (JButton) e.getSource();
		if (eventButton.equals(btnNuovoBilancio)) {
			Archivio<Voce> bil = new Archivio<>() ; 
			MainWindow window = new MainWindow("Gestore Bilancio", bil) ;
			window.setVisible(true);
			setVisible(false);
		}
		else if (eventButton.equals(btnCaricaBilancio)) {
			caricaBilancio();
		}
		else
			System.exit(0);
	}
	
	

}
