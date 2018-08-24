package com.nieat.excelutils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @Author: NieAnTai
 * @Description:
 * @Date: 17:26 2018/8/20
 */
public interface BaseReader {
    /**
     * 读取Excel流文件
     *
     * @param in
     * @throws IOException
     * @throws NullPointerException
     */
    void read(InputStream in) throws IOException, NullPointerException;

    /**
     * 获取读取的数据
     *
     * @return
     */
    List getData();
}
