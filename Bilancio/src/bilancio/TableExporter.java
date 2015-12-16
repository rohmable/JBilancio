package bilancio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import org.jopendocument.dom.spreadsheet.SpreadSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.WorkbookUtil;

import javax.swing.JTable;
import javax.swing.table.TableModel;

/**
 * This class exports a {@code JTable} in various types of text file:
 * <ul>
 * 	<li> Excel </li>
 * 	<li> Open Document (.ods) </li>
 * 	<li> CSV (Comma Separated Values) </li>
 * 	<li> Text separated by a string defined by the user </li>
 * </ul>
 * @author Mirco Romagnoli
 */
public abstract class TableExporter {
	/**
	 * Exports the {@code table} in the .xls format<br>
	 * <a href="http://poi.apache.org/">Apache POI</a> libraries are used to export the	
	 * table as an Excel document
	 * @param table Table to export
	 * @param file File to write
	 * @throws IOException Thrown by the FileOutputStream if something at the moment of his
	 * instantiation went wrong
	 * @see JTable
	 * @see File
	 * @see FileOutputStream
	 */
	public static void exportExcel(JTable table, File file) throws IOException {
		Workbook wb = new HSSFWorkbook();
		String safeName = WorkbookUtil.createSafeSheetName("[Bilancio*?]");
		Sheet bal = wb.createSheet(safeName);
		TableModel model = table.getModel();
		
		Row heading = bal.createRow(0);
		// Heading creation
		for (int i = 0 ; i < model.getColumnCount() ; i++) {
			Cell cell = heading.createCell(i);
			cell.setCellValue(model.getColumnName(i));
		}
		
		// Table creation
		for (int i = 0 ; i < model.getRowCount() ; i++) {
			Row row = bal.createRow(i+1);
			for (int j = 0 ; j < model.getColumnCount() ; j++) {
				Cell cell = row.createCell(j);
				cell.setCellValue(model.getValueAt(i, j).toString());
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
	 * @param table Table to export
	 * @param file File to write
	 * @throws FileNotFoundException Thrown is the file is not found
	 * @throws IOException Thrown if an error occurs while writing the table on the file
	 * @see JTable
	 * @see File
	 */
	public static void exportOOP(JTable table, File file) throws FileNotFoundException, IOException {
		TableModel model = table.getModel();
		SpreadSheet.createEmpty(model).saveAs(file);
	}
	
	/**
	 * Exports the table in the CSV format.<br>
	 * The CSV is a text file where every row represent his corresponding on the table
	 * and each column is separated by the other using a comma ','.<br>
	 * This format can be easily imported by a spreadsheet editor (for example: LibreOffice Calc,
	 * Excel)
	 * 
	 * @param table Table to export
	 * @param file File to write
	 * @throws IOException If an error occurs while writing the file
	 * @see JTable
	 * @see File
	 */
	public static void exportCSV(JTable table, File file) throws IOException {
		// Exporting the table using a comma as a column separator
		exportText(table, file, ",");
		
		// Editing the MIME type to text/csv
		UserDefinedFileAttributeView view = Files.getFileAttributeView(file.toPath(), 
				UserDefinedFileAttributeView.class);
		view.write("user.mimetype", Charset.defaultCharset().encode("text/csv"));
	}
	
	/**
	 * Exports the table as a text file separated by a separator string
	 * 
	 * @param table Table to export
	 * @param file File to write
	 * @param columnSeparator Separator string
	 * @throws IOException If an error occurs writing the file
	 * @see JTable
	 * @see File
	 */
	public static void exportText(JTable table, File file, String columnSeparator) 
			throws IOException {
		TableModel model = table.getModel() ;
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
