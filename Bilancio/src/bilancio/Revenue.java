package bilancio;

import java.io.Serializable;

/**
 * Implements a revenue as voice of a balance
 * @author Mirco Romagnoli
 * @version {@value #serialVersionUID}
 * @see Voice
 * @see Serializable
 */
public class Revenue extends Voice implements Serializable {
	
	/**
	 * Class version
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiate a revenue
	 * @param date Date of the revenue
	 * @param amount Amount of the revenue
	 * @param description Description of the revenue
	 */
	public Revenue(StringDate date, double amount, String description) {
		super(date, description, amount) ;
	}
}
