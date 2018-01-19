package com.seaway.hiver.main.teacher.biz.contract;

import android.support.annotation.NonNull;


import com.seaway.hiver.common.biz.contract.CommonLoginContract;
import com.seaway.hiver.model.common.data.vo.LoginVo;
import com.seaway.hiver.model.common.data.vo.QueryAdvertListVo;
import com.seaway.hiver.model.main.teacher.data.param.BillListParam;
import com.seaway.hiver.model.main.teacher.data.vo.GetBillListVo;
import com.seaway.hiver.model.main.teacher.data.vo.GetCourseListVo;
import com.seaway.hiver.model.main.teacher.data.vo.GetIconCodeVo;
import com.seaway.hiver.model.main.teacher.data.vo.GetQuestionListVo;
import com.seaway.hiver.model.main.teacher.data.vo.IncomeVo;

import java.util.Map;


public interface TMainContract {

    interface View extends CommonLoginContract.View<Presenter> {
        /**
         * 显示界面
         *
         * @param dataSource 数据源
         */
        void showPortal(@NonNull Map<Integer, Object> dataSource);

        /**
         * 广告页查询成功
         *
         * @param advertVo 广告位信息
         * @param flag     true：要更新；false：不需要更新
         */
        void queryAdvertListSuccess(QueryAdvertListVo advertVo, boolean flag);

        /**
         * 获取缓存广告信息成功
         *
         * @param advertListVo 广告信息
         */
        void queryAdvertListInCacheSuccess(QueryAdvertListVo advertListVo);
        /**
         * 获取账单信息成功
         *
         * @param getBillListVo 账单信息
         */
        void queryBillListSuccess(GetBillListVo getBillListVo);
        /**
         * 获取账单信息成功
         *
         * @param getCourseListVo 账单信息
         */
        void queryCourseListSuccess(GetCourseListVo getCourseListVo);
        /**
         * 获取收入成功
         *
         * @param incomeVo  收入
         */
        void queryMonthIncomeSuccess(IncomeVo incomeVo);

        /**
         * 获取缓存广告信息成功
         *
         * @param advertListVo 广告信息
         */
        void queryQuestionListSuccess(GetQuestionListVo advertListVo);

        /**
         * 理财产品查询成功
         *
         * @param financial 理财产品
         */
        void queryFinancialSuccess(GetIconCodeVo financial);

        /**
         * 获取缓存理财产品成功
         *
         * @param financial 理财产品
         */
        void queryFinancialInCacheSuccess(GetIconCodeVo financial);
    }


    interface Presenter extends CommonLoginContract.Presenter {
        /**
         * 初始化银行首页数据源
         */
        void initBankPortalDataSource();

        /**
         * 账单查询
         */
        void queryBillList(String page, String id);
        /**
         * 收入查询
         */
        void queryMonthIncome();
        /**
         * 在线问答查询
         */
        void queryQuestionList(String page, String id,String grade,String unit,String content, String status, String subject);

        /**
         * 课程查询
         */
        void queryCourseList(String page, String id, int status, String subject);
        /**
         * 查询广告位
         *
         * @param type 0：缓存数据；1：远程数据；
         */
        void queryAdvertList(int type);

        /**
         * 查询理财产品
         *
         * @param type 0：缓存数据；1：远程数据；
         */
        void queryFinancialInfo(int type);

    }
}