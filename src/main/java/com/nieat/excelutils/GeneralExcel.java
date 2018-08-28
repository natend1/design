package com.nieat.excelutils;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.List;

/**
 * @Author: NieAnTai
 * @Description: 导出Excel表格
 * @Date: 16:51 2018/8/20
 */
public class GeneralExcel implements WriteInterface {
    private XSSFWorkbook wb;
    private XSSFSheet sheet;
    private Class c;
    private List data;
    protected AnnotationUtils annotationUtils;

    public GeneralExcel(Class c, List data) {
        this.c = c;
        this.data = data;
        annotationUtils = new AnnotationUtils(c);
        wb = new XSSFWorkbook();
        sheet = wb.createSheet("Sheet1");
        sheet.setDefaultRowHeight((short) 500);
    }

    @Override
    public void write(OutputStream out) throws IOException, NullPointerException {
        if (!annotationUtils.getFlag()) {
            throw new NullPointerException("没有设置ExcelTarget注解类");
        }
        thead();
        context();
        wb.write(out);
    }

    /**
     * 填充表格内容
     */
    private void context() throws IOException {
        List<Field> fields = annotationUtils.getAnnField();

        Font font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 10);
        font.setBold(false);

        XSSFCellStyle style_2 = wb.createCellStyle();
        style_2.setBorderTop(BorderStyle.THIN);
        style_2.setBorderRight(BorderStyle.THIN);
        style_2.setBorderLeft(BorderStyle.THIN);
        style_2.setBorderBottom(BorderStyle.THIN);
        style_2.setAlignment(HorizontalAlignment.CENTER);
        style_2.setVerticalAlignment(VerticalAlignment.CENTER);
        // style_2.setWrapText(true);
        style_2.setFont(font);

        try {
            for (int r = 1; r <= this.data.size(); r++) {
                Object tObje = this.data.get(r - 1);
                XSSFRow row = sheet.createRow(r);
                for (int _c = 0; _c < fields.size(); _c++) {
                    Field f = fields.get(_c);
                    String value = annotationUtils.getFieldPojo(f, tObje);
                    XSSFCell _cell = row.createCell(_c);
                    _cell.setCellValue(value);
                    _cell.setCellStyle(style_2);
                }
            }
        } catch (Exception e) {
            throw new IOException("读取数据失败");
        }
    }

    /**
     * 设置表头
     */
    private void thead() {
        List<String> theads = annotationUtils.getAnnValue();
        XSSFRow row = sheet.createRow(0);

        Font font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 12);
        font.setBold(true);

        XSSFCellStyle style = wb.createCellStyle();
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFillForegroundColor(HSSFColor.LIGHT_ORANGE.index);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setWrapText(true);
        style.setFont(font);

        for (int c = 0; c < theads.size(); c++) {
            XSSFCell cell = row.createCell(c);
            sheet.setColumnWidth(c, 2500);
            cell.setCellValue(theads.get(c));
            cell.setCellStyle(style);
        }
    }
}
