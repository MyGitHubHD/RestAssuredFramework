package automation.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.util.*;

public final class ExcelUtils {
    private ExcelUtils(){}

    public static List<Map<String,String>> readSheet(String classpathResource, String sheetName) {
        try (InputStream is = ExcelUtils.class.getClassLoader().getResourceAsStream(classpathResource);
             Workbook wb = new XSSFWorkbook(is)) {
            Sheet sheet = (sheetName == null) ? wb.getSheetAt(0) : wb.getSheet(sheetName);
            if (sheet == null) throw new IllegalArgumentException("Sheet not found: " + sheetName);

            Iterator<Row> it = sheet.iterator();
            if (!it.hasNext()) return Collections.emptyList();
            Row headerRow = it.next();
            int cols = headerRow.getLastCellNum();
            String[] headers = new String[cols];
            for (int i=0;i<cols;i++){
                Cell c = headerRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                headers[i] = c.toString().trim();
            }

            List<Map<String,String>> rows = new ArrayList<Map<String,String>>();
            while (it.hasNext()){
                Row r = it.next();
                Map<String,String> map = new HashMap<String,String>();
                for (int i=0;i<cols;i++){
                    Cell c = r.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    map.put(headers[i], c.toString().trim());
                }
                rows.add(map);
            }
            return rows;
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
