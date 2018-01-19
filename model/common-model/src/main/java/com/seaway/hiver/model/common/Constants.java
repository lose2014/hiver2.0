package com.seaway.hiver.model.common;

/**
 * Created by Leo.Chang on 2017/5/16.
 */

public class Constants {
    public static long tokenId = 1;
    /**
     * 请求成功标识
     */
    public static final String SUCCESS_CODE = "000000";

//	/**
//	 * 动态密码错误
//	 */
//	public static final String DYNAMIC_PASSWORD_ERROR_CODE = "000006";



    /**
     * 网络连接失败标识
     */
    public static final String CONNECTION_FAIL_CODE = "-000001";

    /**
     * 服务端返回会话超时标识
     */
    public final static String SESSION_TIMEOUT = "201002";

    /**
     * 用户被锁定
     */
    public final static String USER_STATUS_LOCKED = "202001";

    /**
     * 用户被冻结
     */
    public final static String USER_STATUS_FREEZE = "202002";

    /**
     * 用户已注销
     */
    public final static String USER_STATUS_DISABLE = "202003";

    /**
     * 网络连接超时标识
     */
    public final static String CONNECTION_TIME_OUT_CODE = "000100";
}
