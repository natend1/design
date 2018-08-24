package com.nieat.excelutils;

import java.util.List;

/**
 * @Author: NieAnTai
 * @Description: Excel 工具类
 * @Date: 11:06 2018/8/21
 */
public class ExcelUtils {
    public static final String EXCEL = "application/vnd.ms-excel";

    /**
     * 返回BaseReader类
     *
     * @param type
     * @param zClass
     * @return
     */
    public static BaseReader newReaderExcel(ExcelType type, Class zClass) {
        if (type == ExcelType.XLS) {
            return new ReaderExcel2003(zClass);
        } else if (type == ExcelType.XLSX) {
            return new ReaderExcel2007(zClass);
        } else {
            return null;
        }
    }

    public static BaseReader newReaderExcel(String filename, Class zClass) {
        if (filename.endsWith(".xls")) {
            return new ReaderExcel2003(zClass);
        } else if (filename.endsWith(".xlsx")) {
            return new ReaderExcel2007(zClass);
        } else {
            return null;
        }
    }

    public static BaseWrite newGeneraWrite(List t, Class zClass) {
        BaseWrite write = new GeneralExcel(zClass, t);
        return write;
    }
}
