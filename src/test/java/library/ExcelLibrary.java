package library;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * The Class ExcelLibrary used to read and write data to the given excel file.
 */
public class ExcelLibrary 
{
	
	/**
	 * Gets the excel data.
	 *
	 * @param filePath the file path
	 * @param sheetName the sheet name
	 * @param rowNo the row no
	 * @param cellNo the cell no
	 * @return the excel data
	 */
	public static String getExcelData(String filePath, String sheetName, int rowNo, int cellNo)
	{
		try
		{
			
			File f = new File(filePath);
			FileInputStream fileInput = new FileInputStream(f);
			Workbook wb = WorkbookFactory.create(fileInput);
			
			//--------------------------------//
			
			//wb.setForceFormulaRecalculation(true);
			//wb.getSheet(sheetName).setForceFormulaRecalculation(true);
			//wb.getCreationHelper().createFormulaEvaluator().evaluateAll();	
			
			//-------------------------------//
			Sheet st = wb.getSheet(sheetName);
			Row r = st.getRow(rowNo);
			Cell c = r.getCell(cellNo);
			String data = c.getStringCellValue();
			return data;
		}
		catch(Exception e)
		{
			//e.printStackTrace();
			return " ";
		}
	}
	
		/**
		 * Writes excel data.
		 *
		 * @param filePath the file path
		 * @param sheetName the sheet name
		 * @param rowNo the row no
		 * @param cellNo the cell no
		 * @param data the data
		 */
		public static void writeExcelData(String filePath, String sheetName, int rowNo, int cellNo, String data)
		{
			try
			{
				FileInputStream fileInput = new FileInputStream(filePath);
				Workbook wb = WorkbookFactory.create(fileInput);
				
				//---------------------------Re evaluate formulas for all sheets ------------------------------------------//
				wb.setForceFormulaRecalculation(true);
				wb.getSheet(sheetName).setForceFormulaRecalculation(true);
				wb.getCreationHelper().createFormulaEvaluator().evaluateAll();				
				//---------------------------------------------------------------------------------------------------------//
				
				//wb.createSheet();
				Sheet st = wb.getSheet(sheetName);
				Row r = st.getRow(rowNo);
				if (r == null)
			        r = st.createRow(rowNo);
				Cell c = r.createCell(cellNo);
				if (c == null)
			        c = r.createCell(cellNo);
				c.setCellType(Cell.CELL_TYPE_STRING);
				c.setCellValue(data);
				FileOutputStream fileOut = new FileOutputStream(filePath);
				wb.write(fileOut);	
				fileOut.close();
			}
			catch(Exception e)
			{
				
			}
			
	}
		
		/**
		 * Gets the excel row count.
		 *
		 * @param filePath the file path
		 * @param sheetName the sheet name
		 * @return the excel row count
		 */
		public static int getExcelRowCount(String filePath, String sheetName)
		{
			int rowNo = 0;
			try
			{
				FileInputStream fileInput = new FileInputStream(filePath);
				Workbook wb = WorkbookFactory.create(fileInput);
				Sheet st = wb.getSheet(sheetName);
				rowNo = st.getLastRowNum();
				
			}
			catch(Exception e)
			{
				
			}
			return rowNo;
	}
		
		/**
		 * Gets the excel cell count.
		 *
		 * @param filePath the file path
		 * @param sheetName the sheet name
		 * @param rowNo the row no
		 * @return the excel cell count
		 */
		public static int getExcelCellCount(String filePath, String sheetName, int rowNo)
		{
			try
			{
				FileInputStream fileInput = new FileInputStream(filePath);
				Workbook wb = WorkbookFactory.create(fileInput);
				Sheet st = wb.getSheet(sheetName);
				Row r = st.getRow(rowNo);
				return r.getLastCellNum();
			}
			catch(Exception e)
			{
				return -1;
			}
	}

}

