package com.seaway.hiver.model.common.data.vo;

/**
 * 消息列表
 * Created by Leo.Chang on 2017/5/18.
 */
public class RequestMessageListVo extends BaseOutputVo {
    private String totalItems;
    private String pageSize;
    private String totalPages;
    private RequestMessageListItemsVo items;

    public String getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(String totalItems) {
        this.totalItems = totalItems;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(String totalPages) {
        this.totalPages = totalPages;
    }

    public RequestMessageListItemsVo getItems() {
        return items;
    }

    public void setItems(RequestMessageListItemsVo items) {
        this.items = items;
    }
}
