package com.seaway.bank.model.vc;

/**
 * Created by Leo.Chang on 2017/6/10.
 */

public interface DownloadProgressListener {
    /**
     * 下载进度更新
     *
     * @param bytesRead     下载完成数量
     * @param contentLength 总内容
     * @param done          是否下载完成
     */
    void update(long bytesRead, long contentLength, boolean done);
}
