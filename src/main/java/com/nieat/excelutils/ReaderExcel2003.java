package com.nieat.excelutils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: NieAnTai
 * @Description: 读取Excel2003工具类
 * @Date: 10:39 2018/8/21
 */
public class ReaderExcel2003 extends AbstractReaderFactory {
    private HSSFWorkbook wb;

    public ReaderExcel2003(Class zClass) {
        super(zClass);
    }

    @Override
    List ready(InputStream in) throws IOException {
        wb = new HSSFWorkbook(in);
        // 默认读取第一个Sheet页
        HSSFSheet sheet = wb.getSheetAt(0);
        List<Field> fields = annotationUtils.getAnnField();
        List<Object> data = new ArrayList<>();
        try {
            // 跳过第一行标题
            for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                HSSFRow row = sheet.getRow(rowNum);
                Object nObje = zClass.newInstance();
                if (row == null) {
                    continue;
                }
                for (int cellNum = 0; cellNum < fields.size(); cellNum++) {
                    HSSFCell cell = row.getCell(cellNum);
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
                    annotationUtils.setFieldPojo(fields.get(cellNum), value, nObje);
                }
                data.add(nObje);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("初始对象赋值失败!!");
        }
        return data;
    }
}
