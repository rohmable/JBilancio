package gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.attribute.UserDefinedFileAttributeView;

import javax.swing.JTable;
import javax.swing.table.TableModel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

/**
 * Table hh
 * @author Mirco Romagnoli
 *
 */
public class BalanceTable extends JTable {
	/**
	 * Class version
	 */
	private static final long serialVersionUID = 1L;
	
	public BalanceTable() {
		super();
	}
	
	public BalanceTable(TableModel tableModel) {
		super(tableModel);
	}
	
	/**
	 * Exports the table in the .xls format<br>
	 * <a href="http://poi.apache.org/">Apache POI</a> libraries are used to export the	
	 * table as an Excel document
	 * @param file File to write
	 * @throws IOException Thrown by the FileOutputStream if something at the moment of his
	 * instantiation went wrong
	 * @see File
	 * @see FileOutputStream
	 */
	public void exportExcel (File file) throws IOException {
		Workbook wb = new HSSFWorkbook();
		String safeName = WorkbookUtil.createSafeSheetName("[Bilancio*?]");
		Sheet bal = wb.createSheet(safeName);
		TableModel model = getModel();
		
		Row heading = bal.createRow(0);
		// Heading creation
		for (int i = 0 ; i < model.getColumnCount() ; i++) {
			Cell cell = heading.createCell(i);
			cell.setCellValue(model.getColumnName(i));
		}
		
		// Table creation
		for (int i = 0 ; i < getRowCount() ; i++) {
			Row row = bal.createRow(i+1);
			for (int j = 0 ; j < getColumnCount() ; j++) {
				Cell cell = row.createCell(j);
				cell.setCellValue(model.getValueAt(convertRowIndexToModel(i), j).toString());
			}
		}
		
		// Writing on file
		FileOutputStream stream = new FileOutputStream(file);
		wb.write(stream);
		stream.close();
		wb.close();
	}
	
	/**
	 * Exports the table in the .ods format.<br>
	 * <a href="http://www.jopendocument.org/">JOpenDocument</a> libraries are used to 
	 * export the table.
	 *
	 * @param file File to write
	 * @throws FileNotFoundException Thrown is the file is not found
	 * @throws IOException Thrown if an error occurs while writing the table on the file
	 * @see File
	 */
	public void exportOOP(File file) throws FileNotFoundException, IOException {
		TableModel model = getModel();
		SpreadSheet.createEmpty(model).saveAs(file);
	}
	
	/**
	 * Exports the table in the CSV format.<br>
	 * The CSV is a text file where every row represent his corresponding on the table
	 * and each column is separated by the other using a comma ','.<br>
	 * This format can be easily imported by a spreadsheet editor (for example: LibreOffice Calc,
	 * Excel)
	 * 
	 * @param file File to write
	 * @throws IOException If an error occurs while writing the file
	 * @see File
	 */
	public void exportCSV(File file) throws IOException {
		// Exporting the table using a comma as a column separator
		exportText(file, ",");
		
		// Editing the MIME type to text/csv
		UserDefinedFileAttributeView view = Files.getFileAttributeView(file.toPath(), 
				UserDefinedFileAttributeView.class);
		view.write("user.mimetype", Charset.defaultCharset().encode("text/csv"));
	}
	
	/**
	 * Exports the table as a text file separated by a separator string
	 * 
	 * @param file File to write
	 * @param columnSeparator Separator string
	 * @throws IOException If an error occurs writing the file
	 * @see File
	 */
	public void exportText(File file, String columnSeparator) 
			throws IOException {
		TableModel model = getModel() ;
		String lineSep = System.getProperty("line.separator");
		int rows = model.getRowCount(),
				columns = model.getColumnCount();
		FileWriter writer = new FileWriter(file) ;
		for (int i = 0 ; i < rows ; i++) {
			for (int j = 0 ; j < columns ; j++) {
				if (j == columns - 1)
					writer.write(model.getValueAt(i, j).toString());
				else
					writer.write(model.getValueAt(i, j).toString() + columnSeparator);
			}
			writer.write(lineSep);
		}
		writer.close();
	}
}
