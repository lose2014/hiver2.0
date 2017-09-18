package com.seaway.hiver.model.common.data.vo;

import java.io.Serializable;

/**
 * Created by Leo.Chang on 2017/6/12.
 */

public class AdvertVo extends BaseOutputVo implements Serializable {

    private String id;
    private String title;
    private String iconUrl = "";
    private String urlPath;
    private String content;
    private String startTime;
    private String endTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AdvertVo) {
            AdvertVo vo = (AdvertVo) obj;
            if (!isPropertyEquals(id, vo.getId())) {
                return false;
            }
            if (!isPropertyEquals(title, vo.getTitle())) {
                return false;
            }
            if (!isPropertyEquals(iconUrl, vo.getIconUrl())) {
                return false;
            }
            if (!isPropertyEquals(urlPath, vo.getUrlPath())) {
                return false;
            }
            if (!isPropertyEquals(content, vo.getContent())) {
                return false;
            }
            if (!isPropertyEquals(startTime, vo.getStartTime())) {
                return false;
            }
            if (!isPropertyEquals(endTime, vo.getEndTime())) {
                return false;
            }

            return true;
        }

        return false;
    }

    /**
     * 比较两个对象的值是否相同
     *
     * @param obj1
     * @param obj2
     * @return
     */
    private boolean isPropertyEquals(Object obj1, Object obj2) {
        if (null == obj1) {
            return null == obj2;
        } else {
            return obj1.equals(obj2);
        }
    }
}
