package com.seaway.hiver.main.teacher.biz.contract;

import android.support.annotation.NonNull;

import com.seaway.hiver.common.biz.contract.CommonLoginContract;
import com.seaway.hiver.model.common.data.vo.QueryAdvertListVo;
import com.seaway.hiver.model.main.teacher.data.vo.GetBillListVo;
import com.seaway.hiver.model.main.teacher.data.vo.GetCourseListVo;
import com.seaway.hiver.model.main.teacher.data.vo.GetIconCodeVo;

import java.util.Map;


public interface TWekeContract {

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