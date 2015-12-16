/**
 * 
 */
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

/**Questa classe permette di esportare una tabella in vari tipi di file di testo:
 * <ul>
 * 	<li> Excel </li>
 * 	<li> Open Document (.ods) </li>
 * 	<li> CSV (Comma Separated Values) </li>
 * 	<li> Testo separato da spazi </li>
 * </ul>
 * 
 * @author Mirco Romagnoli
 *
 */
public class TableExporter {
	/** Impedisce di creare istanze di TableExporter */
	public TableExporter() {
		
	}
	
	
	/**
	 * Permette di esportare la tabella nel formato .xls
	 * <p>
	 * Vengono utilizzate le librerie <a href="http://poi.apache.org/">Apache POI</a> per esportare
	 * il file come documento di Windows
	 * @param table la tabella da cui costruire il file excel
	 * @param file il file su cui scrivere
	 * @throws IOException lanciata dall' FileOutputStream se vi e' qualche problema nella sua
	 * 			istanzazione
	 * @see <a href="https://docs.oracle.com/javase/8/docs/api/javax/swing/JTable.html">JTable</a>,
	 * <a href="https://docs.oracle.com/javase/8/docs/api/java/io/File.html">File</a>,
	 * <a href="https://docs.oracle.com/javase/8/docs/api/java/io/FileOutputStream.html">FileOutputStream</a>
	 */
	public static final void exportExcel(JTable table, File file) throws IOException {
		Workbook wb = new HSSFWorkbook();
		String safeName = WorkbookUtil.createSafeSheetName("[Bilancio*?]");
		Sheet bil = wb.createSheet(safeName);
		TableModel model = table.getModel();
		
		Row intestazione = bil.createRow(0);
		// Creazione dell'intestazione
		for (int i = 0 ; i < model.getColumnCount() ; i++) {
			Cell cell = intestazione.createCell(i);
			cell.setCellValue(model.getColumnName(i));
		}
		
		// Creazione della tabella
		for (int i = 0 ; i < model.getRowCount() ; i++) {
			Row row = bil.createRow(i+1);
			for (int j = 0 ; j < model.getColumnCount() ; j++) {
				Cell cell = row.createCell(j);
				cell.setCellValue(model.getValueAt(i, j).toString());
			}
		}
		
		// Scrittura sul file
		FileOutputStream stream = new FileOutputStream(file);
		wb.write(stream);
		stream.close();
		wb.close();
	}
	
	/**
	 * Permette di esportare la tabella nel formato .ods
	 * <p>
	 * Vengono utilizzate le librerie <a href="http://www.jopendocument.org/">JOpenDocument</a> per
	 * esportare la tabella.
	 * 
	 * @param table la tabella da esportare
	 * @param file il file in cui salvare il documento
	 * @throws FileNotFoundException se il file non viene trovato
	 * @throws IOException se vi e' qualche errore nella scrittura della tabella sul file
	 * @see <a href="https://docs.oracle.com/javase/8/docs/api/javax/swing/JTable.html">JTable</a>,
	 * <a href="https://docs.oracle.com/javase/8/docs/api/java/io/File.html">File</a>
	 */
	public static final void exportOOP(JTable table, File file) throws FileNotFoundException, IOException {
		TableModel model = table.getModel();
		SpreadSheet.createEmpty(model).saveAs(file);
	}
	
	/**
	 * Permette di esportare la tabella nel formato CSV.
	 * <p>
	 * Il formato CSV e' un tipo di file di testo in cui ogni riga rappresenta una riga della tabella
	 * e ogni colonna e' separata dall'altra tramite una virgola ',', puo' essere facilmente importato tramite
	 * un edito di fogli di calcolo (Excel, LibreOffice Calc)
	 * 
	 * @param table la tabella da esportare
	 * @param file il file in cui salvare la tabella
	 * @throws IOException se avviene un errore durante la scrittura del file
	 * @see <a href="https://docs.oracle.com/javase/8/docs/api/javax/swing/JTable.html">JTable</a>,
	 * <a href="https://docs.oracle.com/javase/8/docs/api/java/io/File.html">File</a>
	 */
	public static final void exportCSV(JTable table, File file) throws IOException {
		TableModel model = table.getModel();
		String lineSep = System.getProperty("line.separator");
		int rows = model.getRowCount(),
				columns = model.getColumnCount();
		FileWriter writer = new FileWriter(file) ;
		for (int i = 0 ; i < rows ; i++) {
			for (int j = 0 ; j < columns ; j++) {
				if (j == columns - 1)
					writer.write(model.getValueAt(i, j).toString());
				else
					writer.write(model.getValueAt(i, j).toString() + ',');
			}
			writer.write(lineSep);
		}
		writer.close();
		UserDefinedFileAttributeView view = Files.getFileAttributeView(file.toPath(), 
				UserDefinedFileAttributeView.class);
		view.write("user.mimetype", Charset.defaultCharset().encode("text/csv"));
	}
	
	/**
	 * Esporta la tabella come semplice file di testo separato da spazi
	 * @param table la tabella da esportare
	 * @param file il file in cui esportare la tabella
	 * @throws IOException se avviene un errore durante la scrittura del file
	 * @see <a href="https://docs.oracle.com/javase/8/docs/api/javax/swing/JTable.html">JTable</a>,
	 * <a href="https://docs.oracle.com/javase/8/docs/api/java/io/File.html">File</a>
	 */
	public static final void exportText(JTable table, File file) throws IOException {
		TableModel model = table.getModel() ;
		String lineSep = System.getProperty("line.separator");
		int rows = model.getRowCount(),
				columns = model.getColumnCount();
		FileWriter writer = new FileWriter(file) ;
		for (int i = 0 ; i < rows ; i++) {
			for (int j = 0 ; j < columns ; j++)
				writer.write(model.getValueAt(i, j).toString() + ' ');
			writer.write(lineSep);
		}
		writer.close();
	}
}
