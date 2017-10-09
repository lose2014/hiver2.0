package com.seaway.hiver.main.teacher.biz.contract;

import android.support.annotation.NonNull;


import com.seaway.hiver.common.biz.contract.CommonLoginContract;
import com.seaway.hiver.model.common.data.vo.QueryAdvertListVo;
import com.seaway.hiver.model.main.teacher.data.vo.GetIconCodeVo;

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