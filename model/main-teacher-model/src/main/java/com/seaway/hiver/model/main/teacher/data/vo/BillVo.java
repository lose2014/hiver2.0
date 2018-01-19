package com.seaway.hiver.model.main.teacher.data.vo;


import com.seaway.hiver.model.common.data.vo.BaseOutputVo;

/**
 * 获取图形验证码
 * Created by Leo.Chang on 2017/5/16.
 */
public class BillVo extends BaseOutputVo {
    private String orderId;
    private String payout;
    private String income;
    private int status;
    private String billStudentDesc;
    private String billTeacherDesc;
    private String tradeTime;
    private String teacherId;
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPayout() {
        return payout;
    }

    public void setPayout(String payout) {
        this.payout = payout;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getBillStudentDesc() {
        return billStudentDesc;
    }

    public void setBillStudentDesc(String billStudentDesc) {
        this.billStudentDesc = billStudentDesc;
    }

    public String getBillTeacherDesc() {
        return billTeacherDesc;
    }

    public void setBillTeacherDesc(String billTeacherDesc) {
        this.billTeacherDesc = billTeacherDesc;
    }

    public String getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }
}

