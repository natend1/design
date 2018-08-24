package com.nieat.excelutils;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: NieAnTai
 * @Description: 读取Excel2007 工具类
 * @Date: 10:40 2018/8/21
 */
public class ReaderExcel2007 extends AbstractReaderFactory {
    private XSSFWorkbook wb;

    public ReaderExcel2007(Class zClass) {
        super(zClass);
    }

    @Override
    List ready(InputStream in) throws IOException {
        wb = new XSSFWorkbook(in);
        // 默认读取第一个Sheet页
        XSSFSheet sheet = wb.getSheetAt(0);
        List<Field> fields = annotationUtils.getAnnField();
        // 读取的结果集
        List<Object> data = new ArrayList<>();
        try {
            // 跳过第一行标题
            for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                Object tmp = zClass.newInstance();
                XSSFRow row = sheet.getRow(rowNum);
                if (row == null) {
                    continue;
                }
                for (int cellNum = 0; cellNum < fields.size(); cellNum++) {
                    XSSFCell cell = row.getCell(cellNum);
                    if (cell == null) {
                        continue;
                    }
                    CellType type = cell.getCellTypeEnum();
                    Object value = null;
                    switch (type) {
                        case STRING:
                            value = cell.getStringCellValue();
                            break;
                        case NUMERIC:
                            if (fields.get(cellNum).getType().isAssignableFrom(Date.class)) {
                                value = cell.getDateCellValue();
                            } else {
                                value = cell.getNumericCellValue();
                            }
                            break;
                        case BOOLEAN:
                            value = cell.getBooleanCellValue();
                            break;
                        case BLANK:
                        case FORMULA:
                        case ERROR:
                        case _NONE:
                            break;
                        default:
                            throw new IllegalStateException("无法识别Excel表格数据类型");
                    }
                    annotationUtils.setFieldPojo(fields.get(cellNum), value, tmp);
                }
                data.add(tmp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("初始对象赋值失败!!");
        }
        return data;
    }
}
