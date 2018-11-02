package cc.nonone.generatesql.common.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 读取EXCEL的工具类(POI)
 */
public class ReadExcelUtils {
    /**
     * 读取EXCEL的数据
     * @param is
     * @return
     */
    public static List<String[]> getRowCellValues(InputStream is) {
        String[] list;
        List<String[]> list1 = new ArrayList();
        Workbook book = null;
        Sheet sheet = null;
        Row hssfRow = null;
        Cell cell = null;
        int cellNum = 5;
        try {
            book = new XSSFWorkbook(is);
            sheet = book.getSheetAt(0);
            for (int rowNum = 0; rowNum <= sheet.getLastRowNum(); rowNum++) {
                hssfRow = sheet.getRow(rowNum);
                list = new String[cellNum];
                for (int cellIndex = 0; cellIndex < cellNum; cellIndex++) {
                    cell = hssfRow.getCell(cellIndex);
                    String cellString = null;
                    if (cell != null) {
                        switch (cell.getCellType()) {
                            case STRING:
                                if (cell.getStringCellValue() != null && cell.getStringCellValue() != "") {
                                    cellString = cell.getStringCellValue();
                                }
                                break;
                            case NUMERIC:
                                if (DateUtil.isCellDateFormatted(cell)) {
                                    cellString = DateUtils.formatDateTime(cell.getDateCellValue());
                                } else {
                                    cell.setCellType(CellType.STRING);
                                    cellString = cell.getStringCellValue();
                                }
                                break;
                            case BOOLEAN:
                                cellString = cell.getBooleanCellValue() + "";
                                break;
                            case BLANK: // 空值
                                cellString = null;
                                break;
                            default:
                                cellString = null;
                        }
                        list[cellIndex] = cellString;
                    } else {
                        list[cellIndex] = null;
                    }
                }
                list1.add(list);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list1;
    }
}
