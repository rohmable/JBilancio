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

import bilancio.Archive;
import bilancio.Voice;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

/**
 * Welcome screen where the user can choose if:<ul>
 * <li> Create a new balance </li>
 * <li> Load a pre-existing balance </li>
 * <li> Exit </li> </ul>
 * @author Mirco Romagnoli
 * @see JFrame
 * @see ActionListener
 * @version {@value #serialVersionUID}
 *
 */
public class WelcomeScreen extends JFrame implements ActionListener {

	/**
	 * Version of the class
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Builds the welcome screen of the program with a welcome text and
	 * three buttons to decide the action to perform
	 * @param title Frame title
	 */
	public WelcomeScreen(String title) {
		// Building the frame
		super(title) ;
		JPanel contentPanel = new JPanel();
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		
		// Building the welcome label
		JLabel lblWelcome = new JLabel("Benvenuto nel gestore di Bilancio, scegli un azione per continuare");
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		contentPanel.add(lblWelcome, BorderLayout.CENTER);
		
		// Building the button panel
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		JButton btnNewBalance = new JButton("Nuovo Bilancio");
		buttonPane.add(btnNewBalance);
		getRootPane().setDefaultButton(btnNewBalance);
		JButton btnLoadBalance = new JButton("Carica Bilancio");
		btnLoadBalance.setActionCommand("Carica Bilancio");
		buttonPane.add(btnLoadBalance);
		JButton btnEsci = new JButton("Esci");
		buttonPane.add(btnEsci);
		
		// Linking the listener
		btnNewBalance.addActionListener(this);
		btnLoadBalance.addActionListener(this);
		btnEsci.addActionListener(this);
	}
	
	/**
	 * Loads a balance from user's defined file via FileChooser<br>
	 * When the loading is complete, creates a MainWindow and shows it, hiding
	 * himself
	 * @see FileChooser
	 * @see MainWindow
	 */
	private void loadBalance() {
		FileChooser fChooser = new FileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("File di bilancio", "bil");
		fChooser.setFileFilter(filter);
		fChooser.setMultiSelectionEnabled(false);
		int response = fChooser.showOpenDialog(this);
		if (response == FileChooser.APPROVE_OPTION) {
			File file = fChooser.getSelectedFile();
			FileInputStream fStream = null ;
			ObjectInputStream inputStream = null ;
			Archive<Voice> bal = null ;
			try {
				fStream = new FileInputStream(file);
				inputStream = new ObjectInputStream(fStream);
				bal = (Archive<Voice>) inputStream.readObject();
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
			MainWindow window = new MainWindow("Gestore Bilancio", bal);
			window.setVisible(true);
			setVisible(false);
		}
	}
	
	/**
	 * Listener of the button panel
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String src = e.getActionCommand();
		if (src.equals("Nuovo Bilancio")) {
			Archive<Voice> bal = new Archive<>() ; 
			MainWindow window = new MainWindow("Gestore Bilancio", bal) ;
			window.setVisible(true);
			setVisible(false);
		}
		else if (src.equals("Carica Bilancio")) {
			loadBalance();
		}
		else
			System.exit(0);
	}
	
	

}
