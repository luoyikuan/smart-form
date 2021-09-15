package cn.bigak.sf.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FormModel2Excel {

	public void createExcel(OutputStream outputStream, Map<String, Object> formModel) throws IOException {
		Workbook wb = new XSSFWorkbook();

		// New Sheet
		String safeName = WorkbookUtil.createSafeSheetName("smartform");
		Sheet sheet = wb.createSheet(safeName);
		
		
		List<Map<String, Object>> title = (List<Map<String, Object>>) formModel.get("title");
		Row tRow = sheet.createRow(0);
		tRow.createCell(0).setCellValue("ID");
		for (int i = 0; i < title.size(); i++) {
			tRow.createCell(i+1).setCellValue(title.get(i).get("name").toString());
		}
		
		List<List<Map<String, Object>>> data = (List<List<Map<String, Object>>>) formModel.get("data");
		for (int i = 0; i < data.size(); i++) {
			Row row = sheet.createRow(i + 1);
			List<Map<String, Object>> rowList = data.get(i);
			row.createCell(0).setCellValue(rowList.get(0).get("index").toString());
			for (int j = 0; j < rowList.size(); j++) {
				row.createCell(j + 1).setCellValue(rowList.get(j).get("value").toString());
			}
		}
		
		wb.write(outputStream);
	}
	
}
