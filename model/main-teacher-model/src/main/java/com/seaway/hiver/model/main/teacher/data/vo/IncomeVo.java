package com.seaway.hiver.model.main.teacher.data.vo;


import com.seaway.hiver.model.common.data.vo.BaseOutputVo;

/**
 * 课程信息
 * Created by Leo.Chang on 2017/5/16.
 */
public class IncomeVo extends BaseOutputVo {
    private String income;
    private String courseName;

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
