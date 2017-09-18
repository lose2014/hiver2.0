/**********************************************************************
 * FILE              ：Constant.java
 * PACKAGE			   ：com.jxky.kuaiyi.config
 * AUTHOR            ：mayl
 * DATE				   ：2014-5-5下午1:58:31
 * FUNCTION          ：
 *
 * 杭州思伟版权所有
 *======================================================================
 * CHANGE HISTORY LOG
 *----------------------------------------------------------------------
 * MOD. NO.|  DATE      | NAME       | REASON       | CHANGE REQ.
 *----------------------------------------------------------------------
 *         | 2014-5-5    | mayl    | Created      |
 *
 * DESCRIPTION:
 *
 ***********************************************************************/
package com.hiver.ui.text;

/**
 * 项目名称:KuaiYi 
 * 类名称: Constant.java
 * 类描述： 
 * 创建人：  mayl
 * 创建时间:2014-5-5下午1:58:31
 * -------------------------------修订历史-------------------------- 
 * 修改时间:2014-5-5下午1:58:31 
 * 修改备注：
 * @version：
 */
public class Constant {

	/** 屏幕宽 */
	public static int SCREEN_WIDTH = 0;
	/** 屏幕高 */
	public static int SCREEN_HEIGHT = 0;

	/** 请求超时时间 */
	public final static int CONNECTION_TIME_OUT = 30 * 1000;
	
	/** 运营商 */
	public static int OPERATOR = 0; // 0：移动 1：联通 2：电信

	/** 系统版本号 */
	public static int SYSTEMVERSION = 7;
	/** 当前版本号 */
	public static int VERSIONCODE = 4;
	/** 版本名 */
	public static String VERSIONNAME = "1.0.1.0";
	/** 新版本名 */
	public static String NEW_VERSIONNAME="";
	/** 手机型号 */
	public static String MODEL = "";
	/** 手机制造商 */
	public static String MANUFACTURER = "";
	
	/**安装包保存路径*/
    public static final String BANK_DOWNLOAD_PATH = "KuaiYi";
    
    /** 字符编码 */
    public static final String CHAR_ENCODE="ISO-8859-1";
    
    /** 产品ID */
	public static final String PRODUCT_ID="1";
    
	public static int BEGIN = 100;
	 
    /** 取消请求标识*/
 	public static final int CANCEL_POST_IDENTIFIER = ++BEGIN;
 	 /**安全退出*/
    public static final int LOGOUT = ++BEGIN;
    /**登录*/
    public static final int LOGIN = ++BEGIN;
    /**登录验证码*/
    public static final int LOGIN_CODE = ++BEGIN;
    /**修改初始密码*/
    public static final int MODIFY_INITPWD = ++BEGIN;
    /**验证接口*/
    public static final int AUTHINFO= ++BEGIN;
    /**保持在线接口*/
    public static final int KEEPLIVE= ++BEGIN;
    /**获取登录验证码*/
    public static final int GET_AUTH_CODE= ++BEGIN;
    /**绑定手机号码*/
    public static final int BIND_PHONE= ++BEGIN;
    /**获取短信验证码*/
    public static final int  GET_SMS_CODE=++BEGIN;
    /**验证短信验证码*/
    public static final int AUTH_SMS_CODE= ++BEGIN;
    
    /**获取代理商信息*/
    public static final int MERCHANT_INFO= ++BEGIN;
    
    /**省内电信查询*/
    public static final int IN_PROVINCE_QUERY= ++BEGIN;
    
    /**省外电信查询*/
    public static final int OUT_PROVINCE_QUERY= ++BEGIN;
    
    /**省内电信充值*/
    public static final int IN_PROVINCE_RECHARGE= ++BEGIN;
    
    /**省外电信充值*/
    public static final int OUT_PROVINCE_RECHARGE= ++BEGIN;
    
    /**电力查询*/
    public static final int DL_QUERY= ++BEGIN;
    
    /**电力缴费*/
    public static final int DL_PAYMENT= ++BEGIN;
    
    /**电信查询*/
    public static final int DX_QUERY= ++BEGIN;
    
    
    
    /**获取公告信息*/
    public static final int NOTICE_QUERY= ++BEGIN;
    
    /**交易明细查询*/
    public static final int TRADE_DETAIL_QUERY= ++BEGIN;
    
    /**交易统计查询*/
    public static final int TRADE_STATISTE_QUERY= ++BEGIN;
    
    /**自助银行预存款明细查询*/
    public static final int ATM_DEPOSITE_DETAIL_QUERY= ++BEGIN;
    
    /**签约预存款明细查询(电信，电力)*/
    public static final int SIGN_DEPOSITE_DETAIL_QUERYA = ++BEGIN;
    public static final int SIGN_DEPOSITE_DETAIL_QUERYB = ++BEGIN;
    
    /**银行列表*/
    public static final int BANK_LIST= ++BEGIN;
    
    /**自助银行预存款*/
    public static final int AUTO_SAVING_ACCOUNT= ++BEGIN;
    
    /**电信预存款*/
    public static final int DX_SAVING_ACCOUNT= ++BEGIN;
    
    /**电力预存款*/
    public static final int DL_SAVING_ACCOUNT= ++BEGIN;
    /**小票补打*/
    public static final int TRANSSCTION_DETAIL_REPRINT= ++BEGIN;
    /**每次请求查询时条数*/
    public static final int QUERY_DETAILED_LIST_COUNT = 10;
    
    
}
