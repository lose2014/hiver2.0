package com.seaway.hiver.model.main.teacher.data.param;

import com.seaway.hiver.model.common.data.param.BaseInputParam;


/**
 * 登录请求参数
 * Created by Leo.Chang on 2017/5/16.
 */
public class CourselListParam extends BaseInputParam {
    private String page;
    private String size;
    private String teacherId;
    private String subject;
    private int status;

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
