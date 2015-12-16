package gui;

import java.util.Date;

import javax.swing.RowFilter;

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
	    Date entryDate = (Date) entry.getValue(1);
	    if (entryDate.after(minimumDate) && entryDate.before(maximumDate))
	    	return true ;
	    else
	    	return false;
	}

}
