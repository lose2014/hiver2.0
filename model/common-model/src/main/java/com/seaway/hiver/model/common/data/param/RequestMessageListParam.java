package com.seaway.hiver.model.common.data.param;
/**
 *消息列表
 * */
public class RequestMessageListParam extends BaseInputParam {
 //1表示第1页，默认为1
    private String page;
    private String size;
    //消息所属对象(1-教师;2-学生)
    private String messageBelong;
    /**
     * 用户ID(当messageBelong为1-教师,该值为teacher_id,为2-学生,该值为student_id)
     * */
    private String userId;

    public RequestMessageListParam(String page, String size,String messageBelong, String userId) {

    }


}