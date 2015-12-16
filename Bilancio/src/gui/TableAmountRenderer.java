package gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import bilancio.Revenue;

/**
 * Renderer of the table, allows to change the colour of the cell's text if a row
 * is a revenue or a loss
 * @author Mirco Romagnoli
 * @version {@value #serialVersionUID}
 * @see DefaultTableCellRenderer
 *
 */
public class TableAmountRenderer extends DefaultTableCellRenderer {
	
	/**
	 * Versione of the table
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Sets the color of the table's amount column in green if the row is a revenue and of red
	 * if is a loss
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		BalanceTableModel model = (BalanceTableModel) table.getModel() ;
		if (model.getData().get(row) instanceof Revenue)
			c.setForeground(Color.GREEN);
		else
			c.setForeground(Color.RED);
		return c;
	}
}
