package com.seaway.hiver.model.common.data.vo;

import android.text.TextUtils;

import java.util.List;

/**
 * Created by Leo.Chang on 2017/6/12.
 */
public class QueryAdvertListVo extends BaseOutputVo {
    private String count = "0";
    private String loopView = "0";
    private String viewTime = "4";
    private List<AdvertVo> advertInfos;

    public QueryAdvertListVo(){}

    public QueryAdvertListVo(String count) {
        this.count = count;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getLoopView() {
        return loopView;
    }

    public void setLoopView(String loopView) {
        this.loopView = loopView;
    }

    public String getViewTime() {
        return viewTime;
    }

    public void setViewTime(String viewTime) {
        this.viewTime = viewTime;
    }

    public List<AdvertVo> getAdvertInfos() {
        return advertInfos;
    }

    public void setAdvertInfos(List<AdvertVo> advertInfos) {
        this.advertInfos = advertInfos;
    }

    @Override
    public boolean equals(Object obj) {
        if (null == obj || !(obj instanceof QueryAdvertListVo)) {
            return false;
        }

        QueryAdvertListVo vo = (QueryAdvertListVo) obj;
        if (count.equalsIgnoreCase(vo.getCount()) && loopView.equalsIgnoreCase(vo.getLoopView()) && viewTime.equalsIgnoreCase(vo.getViewTime())) {
            if (null == vo.getAdvertInfos() && null == advertInfos) {
                return true;
            } else if (null != vo.getAdvertInfos() && null != advertInfos && vo.getAdvertInfos().equals(advertInfos)) {
                return true;
            }
        }

        return false;
    }
}