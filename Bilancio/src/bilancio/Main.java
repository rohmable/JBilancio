/**
 * 
 */
package bilancio;

import javax.swing.*;
import gui.* ;

/**
 * Starts the program creating and setting visible a {@code WelcomeScreen}
 * @author Mirco Romagnoli
 * @see WelcomeScreen
 */
public class Main {
	
	/**
	 * Prevents the instantiation of an object of this class
	 */
	public Main() {
	}

	/**
	 * Main of the application, sets the system's look and feel and creates a WelcomeScreen
	 * @param args Arguments of the main function (unused)
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
