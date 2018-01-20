package com.seaway.hiver.main.teacher.biz.contract;

import com.seaway.hiver.common.biz.contract.CommonLoginContract;
import com.seaway.hiver.model.main.teacher.data.vo.GetCourseListVo;


public interface VideoPlayContract {

    interface View extends CommonLoginContract.View<Presenter> {

        /**
         * 获取账单信息成功
         *
         * @param getCourseListVo 账单信息
         */
        void queryCourseListSuccess(GetCourseListVo getCourseListVo);
    }


    interface Presenter extends CommonLoginContract.Presenter {

        /**
         * 账单查询
         */
        void queryCourseList(String page, String id, int status, String subject);


    }
}