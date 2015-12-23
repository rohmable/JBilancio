package bilancio;

import java.io.Serializable;
import java.text.ParseException;

/**
 * Implements the voice of a balance
 * @version {@value #serialVersionUID}
 * @author Mirco Romagnoli
 * @see Serializable
 *
 */
public abstract class Voice implements Serializable {
	/**
	 * Version of the class
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Date of the voice
	 */
	private StringDate date ;
	/**
	 * Description of the voice as a user's defined description
	 */
	private String description ;
	/**
	 * Amount of the voice
	 */
	private double amount ;
	
	/**
	 * Instantiates a voice
	 * @param date Date of the voice
	 * @param description Description of the voice
	 * @param amount Amount of the voice
	 */
	public Voice(StringDate date, String description, double amount) {
		this.date = date ;
		this.description = description;
		this.amount = amount ;
	}

	/**
	 * @return Date of the voice
	 */
	public StringDate getDate() {
		return date;
	}

	/**
	 * Sets the date of the voice
	 * @param date New date
	 */
	public void setDate(StringDate date) {
		this.date = date;
	}

	/**
	 * Sets the date of the voice, the date must be in the format {@code dd/MM/yyyy}
	 * @param date New date
	 * @throws ParseException Thrown if the date is not in the correct format
	 */
	public void setDate(String date) throws ParseException {
		this.date.setDate(date);
	}
	
	/**
	 * @return Description of the voice
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the voice description
	 * @param description New description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return The amount of the voice
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * Sets the amount of the voice
	 * @param amount New amount
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	@Override
	public boolean equals (Object obj) {
		Voice comp = (Voice)obj ;
		return date.equals(comp.getDate()) && description.equals(comp.getDescription()) && 
				(amount == comp.getAmount()) ;
	}
	
	public String amountToString() {
		return String.valueOf(amount);
	}
}
