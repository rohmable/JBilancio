/**
 * 
 */
package bilancio;

import javax.swing.*;
import gui.* ;

/**
 * Questa classe fa partire il programma creando una WelcomeScreen e rendendola visibile
 * @author Mirco Romagnoli
 *
 */
public class Main {
	
	/**
	 * Impedisce l'istanzazione di un oggetto di questa classe
	 */
	public Main() {
	}

	/**
	 * Main del programma, imposta il look & feel a quello del sistema e crea una WelcomeScreen
	 * @param args argomenti della funzione main (non utilizzati)
	 * @see WelcomeScreen
	 */
	public static void main(String[] args) {
		try {
        UIManager.setLookAndFeel(
            UIManager.getSystemLookAndFeelClassName());
		} 
		catch (UnsupportedLookAndFeelException e) {
			System.out.println("Look & Feel non supportato");
		}
		catch (ClassNotFoundException e) {
			System.out.println(e);
		}
		catch (InstantiationException e) {
			System.out.println("L'oggetto non puo' essere istanziato");
		}
		catch (IllegalAccessException e) {
			System.out.println("e");
		}
		
		WelcomeScreen main = new WelcomeScreen("Gestore bilancio") ;
		main.setVisible(true);

	}

}
