package gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import bilancio.Entrata;

public class TableAmountRenderer extends DefaultTableCellRenderer {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		BilancioTableModel model = (BilancioTableModel) table.getModel() ;
		if (model.getData().get(row) instanceof Entrata)
			c.setForeground(Color.GREEN);
		else
			c.setForeground(Color.RED);
		return c;
	}
}
