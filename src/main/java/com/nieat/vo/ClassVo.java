package com.nieat.vo;

import com.nieat.excelutils.annotation.ExcelTarget;

import java.util.Date;

/**
 * @Author: NieAnTai
 * @Description:
 * @Date: 11:27 2018/8/23
 */
public class ClassVo {
    @ExcelTarget("姓名")
    private String name;
    @ExcelTarget("班级")
    private Integer classes;
    @ExcelTarget(value = "入学时间", dateFormat = "yyyy/MM/dd")
    private Date enterTime;
    private Boolean state;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getClasses() {
        return classes;
    }

    public void setClasses(Integer classes) {
        this.classes = classes;
    }

    public Date getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(Date enterTime) {
        this.enterTime = enterTime;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "ClassVo{" +
                "name='" + name + '\'' +
                ", classes=" + classes +
                ", enterTime=" + enterTime +
                ", state=" + state +
                '}';
    }
}
