package com.seaway.bank.biz.vc.contract;


import com.seaway.bank.model.vc.DownloadProgressListener;
import com.seaway.hiver.common.biz.IBasePresenter;
import com.seaway.hiver.common.biz.IBaseView;
import com.seaway.hiver.model.common.data.vo.CheckVersionVo;

/**
 * Created by Leo.Chang on 2017/6/10.
 */
public interface VersionCheckContract {

    interface View extends IBaseView<Presenter> {
        /**
         * 无需升级
         */
        void checkVersionNoUpdate();

        /**
         * 强制更新
         */
        void checkVersionMustUpdate(CheckVersionVo vo);

        /**
         * 非强制更新
         */
        void checkVersionOptUpdate(CheckVersionVo vo);

        /**
         * 版本检测失败
         *
         * @param reason 失败原因
         */
        void checkVersionFail(String reason);
    }

    interface Presenter extends IBasePresenter {
        /**
         * 客户端版本检测
         */
        void checkVersion();

        /**
         * 下载
         */
        void download(String url, DownloadProgressListener downloadProgressListener);
    }

}
