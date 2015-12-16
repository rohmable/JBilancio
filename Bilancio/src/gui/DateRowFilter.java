package gui;

import java.util.Date;

import javax.swing.RowFilter;


public class DateRowFilter<M, I> extends RowFilter<M, I> {
	private Date minimumDate, maximumDate ;
	
	public DateRowFilter(Date minimumDate, Date maximumDate) {
		super();
		this.minimumDate = minimumDate ;
		this.maximumDate = maximumDate ;
	}

	@Override
	public boolean include(javax.swing.RowFilter.Entry<? extends M, ? extends I> entry) {
	    Date entryData = (Date) entry.getValue(1);
	    if (entryData.after(minimumDate) && entryData.before(maximumDate))
	    	return true ;
	    else
	    	return false;
	}

}
