package gui;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * Lets choose a file from the file system and shows a confirm window if the user
 * chooses to save on a pre-existing file
 * @version {@value #serialVersionUID}
 * @author Mirco Romagnoli
 * @see JFileChooser
 *
 */
public class FileChooser extends JFileChooser {
	
	/**
	 * Version of the class
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Builds a FileChooser pointing to the default user's home directory<br>
	 * The default directory is operative system dependent
	 */
	public FileChooser() {
		super() ;
	}
	
	/**
	 * When the user chooses to save the file if the dialog is in Save mode
	 * and the file already exists, the user must confirm to overwrite the file
	 */
	@Override
	public void approveSelection() {
		File file = getSelectedFile() ;
		if (file.exists() && (getDialogType() == FileChooser.SAVE_DIALOG)){
			Object[] options = {"Si", "No"} ;
			int response = JOptionPane.showOptionDialog(this, "Sicuro di voler sovrascrivere il file?", 
						"Conferma", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, 
						null, options, options[1]);
			if (response == JOptionPane.YES_OPTION)
				super.approveSelection();
			else
				return ;
		}
		super.approveSelection();
	}
}
