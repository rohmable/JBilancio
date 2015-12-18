package gui;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.RowFilter;

import bilancio.StringDate;

/**
 * Filter that extends a RowFilter, it is used to filter a table to let see
 * only the values with a date between the minimum and the maximum
 * @author Mirco Romagnoli
 *
 * @param <M> Type of the model
 * @param <I> Type of the identifier (Integer if used for a TableRowSorter)
 */
public class DateRowFilter<M, I> extends RowFilter<M, I> {
	private Date minimumDate ;
	private Date maximumDate ;
	
	/**
	 * Costructor of the filter, sets minimum and maximum date
	 * @param minimumDate Minimum date to display
	 * @param maximumDate Maximum date to display
	 */
	public DateRowFilter(Date minimumDate, Date maximumDate) {
		super();
		this.minimumDate = minimumDate ;
		this.maximumDate = maximumDate ;
	}

	/**
	 * Returns true if the entry must be shown, return false otherwise<br>
	 * If the entry's date is between the values of minimumDate and maximumDate
	 * return true
	 */
	@Override
	public boolean include(javax.swing.RowFilter.Entry<? extends M, ? extends I> entry) {
	    String entryStringDate = (String) entry.getValue(1);
	    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	    Date entryDate = null ;
	    try {
			entryDate = formatter.parse(entryStringDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	    if (entryDate.after(minimumDate) && entryDate.before(maximumDate))
	    	return true ;
	    else
	    	return false;
	}

}
