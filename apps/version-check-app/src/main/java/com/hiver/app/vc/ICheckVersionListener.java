package com.hiver.app.vc;

/**
 * 版本检测监听接口
 * Created by Leo.Chang on 2017/6/10.
 */
public interface ICheckVersionListener {
    /**
     * 无需更新
     */
    public static final int CHECK_VERSION_NO_UPDATE = 0;

    public static final int CHECK_VERSION_COMPLETE_AND_DOWNLOAD_FINISH = 1;

    /**
     * 版本检测完成
     *
     * @param result 结果
     */
    public void onCheckVersionComplete(int result);
}
