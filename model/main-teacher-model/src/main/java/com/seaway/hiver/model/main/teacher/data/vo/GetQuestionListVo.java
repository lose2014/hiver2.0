package com.seaway.hiver.model.main.teacher.data.vo;


import com.seaway.hiver.model.common.data.vo.BaseOutputVo;

import java.util.List;

/**
 * 3.17问题列表查询(POST /question/list)
 * Created by Leo.Chang on 2017/5/16.
 */
public class GetQuestionListVo extends BaseOutputVo {
    private String totalPages;
    private List<QuestionVo> items;
    private String pageSize;
    private int totalItems;

    public String getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(String totalPages) {
        this.totalPages = totalPages;
    }

    public List<QuestionVo> getItems() {
        return items;
    }

    public void setItems(List<QuestionVo> items) {
        this.items = items;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }
}
