package com.seaway.hiver.model.main.teacher.data.vo;


import com.seaway.hiver.model.common.NetUtil;
import com.seaway.hiver.model.common.data.vo.BaseOutputVo;

/**
 * 课程信息
 * Created by Leo.Chang on 2017/5/16.
 */
public class CourseVo extends BaseOutputVo {
    private String courseId;
    private String courseName;
    private String stage;
    private String grade;
    private int courseType;
    private int status;
    private String teaRealname;
    private String coursePrice;
    private String createTime;
    private String courseIntroduce;//courseIntroduce
    private String courseVideoUrl;//courseVideoUrl地址都是相对地址，要拼接NetUtil.getWebViewUrl()作为开头
    private String courseCoverUrl;//课程封面地址

    public String getCourseCoverUrl() {
        return courseCoverUrl;
    }

    public void setCourseCoverUrl(String courseCoverUrl) {
        this.courseCoverUrl = courseCoverUrl;
    }

    public String getCourseIntroduce() {
        return courseIntroduce;
    }

    public void setCourseIntroduce(String courseIntroduce) {
        this.courseIntroduce = courseIntroduce;
    }

    public String getCourseVideoUrl() {
        return courseVideoUrl;
    }

    public void setCourseVideoUrl(String courseVideoUrl) {
        this.courseVideoUrl = courseVideoUrl;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
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

    public int getCourseType() {
        return courseType;
    }

    public void setCourseType(int courseType) {
        this.courseType = courseType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTeaRealname() {
        return teaRealname;
    }

    public void setTeaRealname(String teaRealname) {
        this.teaRealname = teaRealname;
    }

    public String getCoursePrice() {
        return coursePrice;
    }

    public void setCoursePrice(String coursePrice) {
        this.coursePrice = coursePrice;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
