package com.seaway.hiver.model.common.data.vo;


/**
 * Created by Leo.Chang on 2017/6/10.
 */

public class CheckVersionVo extends BaseOutputVo {
    private String update;
    private String version;
    private String isOptional;
    private String url;
    private String size;
    private String description;
    private String md5;
    private String hasIncPkg;
    private String incUrl;
    private String incMd5;
    private String incSize;

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getIsOptional() {
        return isOptional;
    }

    public void setIsOptional(String isOptional) {
        this.isOptional = isOptional;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getHasIncPkg() {
        return hasIncPkg;
    }

    public void setHasIncPkg(String hasIncPkg) {
        this.hasIncPkg = hasIncPkg;
    }

    public String getIncUrl() {
        return incUrl;
    }

    public void setIncUrl(String incUrl) {
        this.incUrl = incUrl;
    }

    public String getIncMd5() {
        return incMd5;
    }

    public void setIncMd5(String incMd5) {
        this.incMd5 = incMd5;
    }

    public String getIncSize() {
        return incSize;
    }

    public void setIncSize(String incSize) {
        this.incSize = incSize;
    }
}
