package com.nieat.excelutils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @Author: NieAnTai
 * @Description: Excel 读取工厂类
 * @Date: 16:08 2018/8/21
 */
public abstract class AbstractReaderFactory implements ReaderInterface {
    /**
     * 读取的数据
     */
    private List data;
    /**
     * 注解类
     */
    protected Class zClass;

    protected AnnotationUtils annotationUtils;

    public AbstractReaderFactory(Class zClass) {
        this.zClass = zClass;
        annotationUtils = new AnnotationUtils(zClass);
    }

    /**
     * 子类具体实现
     *
     * @param in
     * @return
     */
    abstract List ready(InputStream in) throws IOException;

    @Override
    public void read(InputStream in) throws IOException, NullPointerException {
        if (!annotationUtils.getFlag()) {
            throw new NullPointerException("没有设置ExcelTarget注解类");
        }
        this.data = ready(in);
    }

    @Override
    public List getData() {
        return data;
    }
}
