package bilancio;

import java.io.Serializable;

/**
 * Implements a loss as a balance voice
 * @author Mirco Romagnoli
 * @version {@value #serialVersionUID}
 * @see Voice
 * @see Serializable
 */
public class Loss extends Voice implements Serializable {
	/**
	 * Version of the class
	 */
	private static final long serialVersionUID = 3L;

	/**
	 * Instantiates a loss
	 * @param date Date of the loss
	 * @param amount Amount of the loss
	 * @param description Description of the loss
	 */
	public Loss(StringDate date, double amount, String description) {
		super(date, description, amount) ;
	}
	
	@Override
	public String toString() {
		return '-' + String.valueOf(getAmount()) ;
	}
}
