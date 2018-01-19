package com.seaway.hiver.model.main.teacher.data.vo;


import com.seaway.hiver.model.common.data.vo.BaseOutputVo;

import java.util.List;

/**
 * 获取图形验证码
 * Created by Leo.Chang on 2017/5/16.
 */
public class GetBillListVo extends BaseOutputVo {
    private String totalPages;
    private List<BillVo> items;
    private String pageSize;
    private String totalItems;

    public String getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(String totalPages) {
        this.totalPages = totalPages;
    }

    public List<BillVo> getItems() {
        return items;
    }

    public void setItems(List<BillVo> items) {
        this.items = items;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(String totalItems) {
        this.totalItems = totalItems;
    }
}
