package com.seaway.hiver.main.teacher.biz.contract;

import com.seaway.hiver.common.biz.contract.CommonLoginContract;
import com.seaway.hiver.model.main.teacher.data.vo.GetBillListVo;
import com.seaway.hiver.model.main.teacher.data.vo.GetCourseListVo;


public interface TAccountContract {

    interface View extends CommonLoginContract.View<Presenter> {
        /**
         * 获取账单信息成功
         *
         * @param getCourseListVo 账单信息
         */
        void queryBillListSuccess(GetBillListVo getCourseListVo);

    }

    interface Presenter extends CommonLoginContract.Presenter {

        /**
         * 账单查询
         */
        void queryBillList(String page, String id);


    }
}