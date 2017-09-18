package com.seaway.hiver.model.common;

import com.seaway.android.sdk.toolkit.SWVerificationUtil;

/**
 * Created by Leo.Chang on 2017/6/7.
 */

public class NetUtil {
//    public static String web_view_path = "http://192.168.32.93:28600/#";
//public static String web_view_path = "https://192.168.32.93:8443/#";

//    public static String web_view_path = "https://101.69.255.186:38600/#";

    //public static String web_view_path = "https://xtbk2.seaway.net.cn:38600/#";
//    public static String web_view_path = "https://192.168.32.61/#";//陈宝春笔记本
    public static String web_view_path = "https://192.168.32.137";//陈宝春台式机
//      public static String web_view_path = "https://terminal.4008889112.cn:18087";//正式环境测试
//    public static String web_view_path = "https://terminal.lkdoo.com:18087/#";//正式环境测试//
//    public static String web_view_path = "https://www.4008889112.cn:18087";//正式环境//

//      public static String web_view_path = "http://ebao.seaway.net.cn:18600/#";//公司测试环境
//public static String web_view_path = "http://api.59iservice.com:21100/index.html";//正式环境



    public static String getWebViewUrl() {
//        if(SWVerificationUtil.isEmpty(Util.readFromPath())||web_view_path.equals(Util.readFromPath())){
//            return web_view_path+"/#";//正式地址
//        }else{
//            return  Util.readFromPath()+"/#";//正式地址
//        }
        return web_view_path+"/#";//正式地址
//        return "https://192.168.32.137/#";//陈宝春台式机
    }

    public static void setWebViewUrl(String url){
        web_view_path =url;
    }
}
