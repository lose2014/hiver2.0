package com.seaway.hiver.model.common.data.vo;

/**
 * Created by Leo.Chang on 2017/6/3.
 */
public class GetAgreementVo extends BaseOutputVo {
    private String agmTitle;
    private String agmContent;

    public String getAgmTitle() {
        return agmTitle;
    }

    public void setAgmTitle(String agmTitle) {
        this.agmTitle = agmTitle;
    }

    public String getAgmContent() {
        return agmContent;
    }

    public void setAgmContent(String agmContent) {
        this.agmContent = agmContent;
    }
}
