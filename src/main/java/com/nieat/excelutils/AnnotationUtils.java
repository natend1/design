package com.nieat.excelutils;

import com.nieat.excelutils.annotation.ExcelTarget;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: NieAnTai
 * @Description: 注解分析工具类
 * @Date: 16:10 2018/8/22
 */
public class AnnotationUtils {
    /**
     * 注解类
     */
    private Class annClass = ExcelTarget.class;
    /**
     * 标记类
     */
    private Class oClass;
    /**
     * 是否被标记
     */
    private boolean flag = false;
    /**
     * 注解字段
     */
    private List<Field> annField;
    /**
     * 注解内容
     */
    private List<String> annValue;

    public AnnotationUtils(Class oClass) {
        this.oClass = oClass;
        analyz();
    }

    /**
     * 解析注解信息
     */
    private void analyz() {
        Field[] fields = oClass.getDeclaredFields();
        List<Field> annField = new ArrayList<>();
        List<String> annValue = new ArrayList<>();
        for (int i = 0; i < fields.length; i++) {
            Field f = fields[i];
            if (fields[i].isAnnotationPresent(annClass)) {
                flag = true;
                ExcelTarget ann = (ExcelTarget) f.getAnnotation(annClass);
                annField.add(f);
                annValue.add(ann.value());
            }
        }
        if (!flag) {
            return;
        }
        this.annField = annField;
        this.annValue = annValue;
    }

    /**
     * 反射,给类字段设置值
     *
     * @param field 类字段
     * @param value 值
     * @param nObj  实例类
     */
    public void setFieldPojo(Field field, Object value, Object nObj) throws Exception {
        if (value == null) {
            return;
        }
        try {
            String name = field.getName();
            name = name.substring(0, 1).toUpperCase() + name.substring(1);
            String typeName = field.getGenericType().getTypeName();
            Method setMethod = nObj.getClass().getMethod("set" + name, new Class[]{Class.forName(typeName)});

            if (field.getType().isAssignableFrom(String.class)) {
                value = String.valueOf(value);
            } else if (field.getType().isAssignableFrom(Integer.class)) {
                // 取整数
                String dou = String.valueOf(value);
                int lastIndex = dou.lastIndexOf(".");
                if (lastIndex != -1) {
                    dou = dou.substring(0, lastIndex);
                }
                value = Integer.valueOf(dou);
            }

            setMethod.invoke(nObj, new Object[]{value});
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new IllegalStateException("请使用包装数据类型");
        }
    }

    /**
     * 反射 获得类字段的值
     *
     * @param field
     * @param nObj
     * @return
     * @throws Exception
     */
    public String getFieldPojo(Field field, Object nObj) throws Exception {
        String name = field.getName();
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        Method getMethod = nObj.getClass().getMethod("get" + name);
        Object v = getMethod.invoke(nObj);
        if (field.getType().isAssignableFrom(Date.class)) {
            // 日期格式化
            ExcelTarget target = (ExcelTarget) field.getAnnotation(annClass);
            String template = target.dateFormat();
            return new SimpleDateFormat(template).format((Date) v);
        } else {
            return v == null ? "" : String.valueOf(v);
        }
    }

    public boolean getFlag() {
        return flag;
    }

    public List<Field> getAnnField() {
        return this.annField;
    }

    public List<String> getAnnValue() {
        return this.annValue;
    }
}
