package com.seaway.hiver.model.main.teacher.data.vo;


import com.seaway.hiver.model.common.data.vo.BaseOutputVo;

/**
 *在线问答详情
 * Created by Leo.Chang on 2017/5/16.
 */
public class QuestionVo extends BaseOutputVo {
    private String questionId;
    private String subject;
    private String stage;
    private String grade;
    private int status;
    private String unit;
    private String content;
    private String questionPrice;
    private String createTime;

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getQuestionPrice() {
        return questionPrice;
    }

    public void setQuestionPrice(String questionPrice) {
        this.questionPrice = questionPrice;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
