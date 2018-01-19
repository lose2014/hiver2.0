package com.seaway.hiver.model.main.teacher.data.param;

import com.seaway.android.sdk.tools.DeviceUtil;
import com.seaway.hiver.model.common.data.param.BaseInputParam;


/**
 * 登录请求参数
 * Created by Leo.Chang on 2017/5/16.
 */
public class BillListParam extends BaseInputParam {
    private String page;
    private String size;
    private String teacherId;
    private String studentId;

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

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}
