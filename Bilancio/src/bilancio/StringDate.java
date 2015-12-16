package bilancio;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class represents a Date formatted as dd/MM/yyyy, omitting the hour
 * @author Mirco Romagnoli
 * @version {@value #serialVersionUID}
 * @see Date
 */
public class StringDate extends Date {
	/**
	 * Class version
	 */
	private static final long serialVersionUID = 2L;

	/** SimpleDateFormatter to format the date as {@code dd/MM/yyyy}
	 * @see SimpleDateFormat */
	private final DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy") ;

	/**
	 * Instantiates an object from a string formatted as {@code dd/MM/yyyy}
	 * @param date String formatted as {@code dd/MM/yyyy}
	 * @throws ParseException If the String is not in the correct format
	 * @see SimpleDateFormat
	 */
	public StringDate(String date) throws ParseException {
		super();
		Date temp = formatter.parse(date);
		setTime(temp.getTime());
	}
	
	/**
	 * Instantiate an object from a {@code Date}
	 * @param date Date
	 * @throws ParseException This exception should never be thrown because the date
	 * should be always correct
	 * @see Date
	 */
	public StringDate(Date date) throws ParseException {
		super();
		String tempString = formatter.format(date);
		Date tempDate = formatter.parse(tempString);
		setTime(tempDate.getTime());
	}
	
	/**
	 * @return A string representation of the date formatted as {@code dd/MM/yyyy}
	 */
	@Override
	public String toString() {
		Date temp = new Date(getTime());
		return formatter.format(temp);
	}
	
	/**
	 * Sets the date from a string formatted as {@code dd/MM/yyyy}
	 * @param date Date
	 * @throws ParseException If the string is not in the correct format
	 */
	public void setDate(String date) throws ParseException {
		Date temp = formatter.parse(date);
		setTime(temp.getTime());
	}
}
