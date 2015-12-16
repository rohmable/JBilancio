package gui;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class FileChooser extends JFileChooser {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FileChooser() {
		super() ;
	}
	
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
