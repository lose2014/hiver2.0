/**********************************************************************
 * FILE              ：ErrorHintUtil.java
 * PACKAGE			   ：com.seaway.ky.util
 * AUTHOR            ：mayl
 * DATE				   ：2014-5-9上午10:03:57
 * FUNCTION          ：
 *
 * 杭州思伟版权所有
 *======================================================================
 * CHANGE HISTORY LOG
 *----------------------------------------------------------------------
 * MOD. NO.|  DATE      | NAME       | REASON       | CHANGE REQ.
 *----------------------------------------------------------------------
 *         | 2014-5-9    | mayl    | Created      |
 *
 * DESCRIPTION:
 *
 ***********************************************************************/
package com.hiver.ui.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.SpannableString;
import android.view.View;
import android.widget.Button;


import com.hiver.ui.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

/**
 * 项目名称:KuaiYi 
 * 类名称: ErrorHintUtil.java
 * 类描述： 
 * 创建人：  mayl
 * 创建时间:2014-5-9上午10:03:57
 * -------------------------------修订历史--------------------------
 * 修改人：mayl
 * 修改时间:2014-5-9上午10:03:57 
 * 修改备注：
 * @version：
 */
@SuppressLint("SimpleDateFormat")
public class ErrorHintUtil {
	/** 保存所有状态码及其对应的解释 */
	public static Hashtable<String, String> state = new Hashtable<String, String>();

	public static DefineDialog dialog;
	/**会话超时*/
	public final static String SESSION_TIMEOUT ="111111";
	static {
		// 自定义错误码
		state.put("-000001", "timeout");
		state.put("111111", "error");
	}
	/**
	 * 	默认确定按钮不处理任何操作
	 * @param context
	 * @param errorCode：errorMessage为空，则根据errorCode显示；
	 * @param errorMessage：errorMessage不为空，则显示errorMessage；
	 */
	public static void  showErrorMessage(final Context context,String errorCode,Object errorMessage){
		if(dialog!=null && dialog.isShowing()){
			dialog.dismiss();
			dialog=null;
		}
		if(handleSessionTimeOut(context, errorCode, errorMessage)){
			return;
		}
		dialog = new DefineDialog(context);
		String errorString = "";
		dialog.setCancelable(false);

		if(errorMessage==null || errorMessage.equals("")){
			errorString =context.getResources().getString(R.string.e1001_net_timeout);
		}else{
			errorString = errorMessage.toString();
		}
		dialog.setMessage(errorString);
		dialog.setMiddleButton("确定", new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				dialog = null;
			}
		});
		dialog.show();
	}

	public static void showMessageDiolog(final Context context,String errorString,String middle,DefineDialog.OnButtonClickListener middleListener){
		try{
			if(dialog!=null && dialog.isShowing()){
				dialog.dismiss();
				dialog=null;
			}
			dialog = new DefineDialog(context);
			dialog.setCancelable(false);
			dialog.setMessage(errorString);
			dialog.setMiddleButton(middle, middleListener);
			dialog.show();
		}catch(NullPointerException ne){
			ne.printStackTrace();
		}
	}
    /**
     * 
     * @time 2014-6-23上午11:34:21
     * @return void
     * @TODO
     */
	public static void showErrorMessage(final Context context,String errorCode,Object errorMessage,String positive,DefineDialog.OnButtonClickListener positiveListener,String negative,DefineDialog.OnButtonClickListener negativeListener){
		if(dialog!=null && dialog.isShowing()){
			dialog.dismiss();
			dialog=null;
		}
		if(handleSessionTimeOut(context, errorCode, errorMessage)){
			return;
		}
		dialog = new DefineDialog(context);
		
		String errorString = "";
		dialog.setCancelable(false);
		if(errorMessage==null || errorMessage.equals("")){
			errorString =(null == state.get(errorCode)) ? context.getResources().getString(R.string.e1001_net_timeout):(state.get(errorCode));
		}else{
			errorString = errorMessage.toString();
		}
		dialog.setMessage(errorString);
		dialog.setPositiveButton(positive, positiveListener);
		dialog.setNegativeButton(negative, negativeListener);
		dialog.show();
	}
	/**
	 * 通用提醒框
	 * @time 2014-5-9上午10:03:21
	 * @return void
	 * @TODO
	 */
	public static void showMessageDiolog(final Context context,String errorString,String positive,DefineDialog.OnButtonClickListener positiveListener,String negative,DefineDialog.OnButtonClickListener negativeListener){
		try{
			if(dialog!=null && dialog.isShowing()){
				dialog.dismiss();
				dialog=null;
			}
			dialog = new DefineDialog(context);

			dialog.setCancelable(false);
			dialog.setMessage(errorString);
			dialog.setPositiveButton(positive, positiveListener);
			dialog.setNegativeButton(negative, negativeListener);
			dialog.show();
		}catch(NullPointerException ne){
			ne.printStackTrace();
		}
	}

	/**
	 * 
	 * @time 2014-5-15下午6:17:10
	 * @return void
	 * @TODO
	 */
	public static void showMessageDiologWithColor(final Context context,SpannableString errorString,String positive,DefineDialog.OnButtonClickListener positiveListener,String negative,DefineDialog.OnButtonClickListener negativeListener){
		try{
			if(dialog!=null && dialog.isShowing()){
				dialog.dismiss();
				dialog=null;
			}
			dialog = new DefineDialog(context);

			dialog.setCancelable(true);
			dialog.setMessageWithColor(errorString);
			dialog.setPositiveButton(positive, positiveListener);
			dialog.setNegativeButton(negative, negativeListener);
			dialog.show();
		}catch(NullPointerException ne){
			ne.printStackTrace();
		}
	}
	

	public static void showErroMsg(Context context, String message) {
		if ((null == message) || "".equals(message.trim())) {
			return;
		}
		dialog = new DefineDialog(context);
		dialog.setCancelable(true);
		dialog.setMessage(message);
		dialog.show();
	}
	
	public static void showErroMsg(Context context, int message) {
		dialog = new DefineDialog(context);
		dialog.setCancelable(true);
		dialog.setMessage(message);
		dialog.show();
	}
	
	public static void dismissErroMsg() {
		if(dialog!=null && dialog.isShowing()){
			dialog.dismiss();
			dialog=null;
		}
	}
	/**
	 * 处理会话超时
	 * @param context
	 * @param errorCode
	 * @param errorMessage
	 * void
	 */
	public static boolean handleSessionTimeOut(final Context context ,String errorCode,Object errorMessage){
		if(errorCode.equals(SESSION_TIMEOUT)){
			dialog = new DefineDialog(context);
			String errorString = "";
			dialog.setCancelable(false);
			 //如果返回的结果是error,请求3次后，session失效，退回到登录页
			if(null==errorMessage || errorMessage.equals("error")){
				errorString = "会话超时，请重新登录！";
			}else{
				errorString = errorMessage.toString();
			}
			dialog.setMessage(errorString);
			dialog.setMiddleButton("确定", new DefineDialog.OnButtonClickListener() {

				@Override
				public void onClick(DefineDialog dialog, View v) {
//					Activity mActivity = (Activity)context;
//				    KuaiYiApplication application = (KuaiYiApplication) mActivity.getApplication();
//				    if(mActivity instanceof MainActivity){
//				    	SWLog.LogE("mActivity IS MainActivity");
//						application.logout();
//						Intent intent=new Intent(context,UserLoginActivity.class);
//						context.startActivity(intent);
//						mActivity.finish();
//					}else{
//						SWLog.LogE("mActivity IS NOT MainActivity");
//						application.logout();
//					}
				}
			});
			dialog.show();
			return true;
		}
		return false;
	}
	
	
	
	
	
	/**
	 * 项目名称:KuaiYi 
	 * 类名称: ErrorHintUtil.java
	 * 类描述：查询条件通用帮助类 
	 * 创建人：  yanzhj
	 * 创建时间:2014年5月15日下午2:54:23
	 * -------------------------------修订历史--------------------------
	 * 修改人：yanzhj
	 * 修改时间:2014年5月15日下午2:54:23 
	 * 修改备注：
	 * @version：
	 */
	public static class QueryUtil{
		/**
		 * @author yanzhj
		 * @time   2014年5月15日下午2:49:24
		 * @return 返回yyyy-MM-dd格式的日期字符串
		 * @TODO 得到今天的日期
		 */
//		public static String getCurrentDate() {
//			//返回指定格式的日期，也可以是2014/05/08
//			return SWTimerUtil.getCurrentDate("yyyy-MM-dd");
//		}
		
		/**
		 * 计算两个日期之间的天数差
		 * 创建人：yanzhj
		 * 创建时间:2014年6月27日上午11:37:26
		 * @param format 日期格式
		 * @param time1 前面的日期
		 * @param time2 后面的日期
		 * @return 前面的日期减去后面的日期的差
		 */
		public static int getDateDiffer(String format, String time1, String time2){
			long quot = 0;
			int result = 0;
			SimpleDateFormat ft = new SimpleDateFormat(format);
			try {
				Date date1 = ft.parse( time1 );
				Date date2 = ft.parse( time2 );
				quot = date1.getTime() - date2.getTime();
				quot = quot / 1000 / 60 / 60 / 24;
			} catch (ParseException e) {
				e.printStackTrace();
			}
			result = Integer.parseInt(String.valueOf(quot));
			return result;
		}

		/**
		 * 开始日期和结束日期是否正确
		 * 创建人：yanzhj
		 * 创建时间:2014年5月19日上午11:03:24
		 * @param context
		 * @param startDate
		 * @param endDate
		 * @return
		 */
//		public static boolean isTrueDates(Context context, String startDate, String endDate){
//			boolean result = true;
//			if(getDateDiffer("yyyy-MM-dd", getCurrentDate(), startDate) < 0){//开始日期是否超过今天
//				CustomToast.showMessage(context, R.string.e1100_start_date_error_notice);
//				return false;
//			}else if(getDateDiffer("yyyy-MM-dd", getCurrentDate(), endDate) < 0){//结束日期是否超过今天
//				CustomToast.showMessage(context, R.string.e1101_end_date_error_notice);
//				return false;
//			}else if(getDateDiffer("yyyy-MM-dd", startDate, endDate) > 0){//开始日期是否超过结束日期
//				CustomToast.showMessage(context, R.string.e1102_start_end_date_error_notice);
//				return false;
//			}else if(getDateDiffer("yyyy-MM-dd", endDate, startDate) > 10){//日期间隔不得超过十天
//				CustomToast.showMessage(context, R.string.e1103_interval_date_error_notice);
//				return false;
//			}
//			return result;
//		}
		
		/**
		 * 是否是在查询周期内的日期，选择的日期不能超过今天十天
		 * 创建人：yanzhj
		 * 创建时间:2014年6月30日下午4:23:25
		 * @param inDate
		 * @return 返回传入inDate与当前日期的差是否小于等于10，小于等于返回True，否则返回false
		 */
//		public static boolean isCycleDate(Context context, String inDate){
//			if(getDateDiffer("yyyy-MM-dd", getCurrentDate(), inDate) > 10){//日期间隔不得超过十天
//				CustomToast.showMessage(context, R.string.e1104_start_curret_date_error_notice_);
//				return false;
//			}
//			return true;
//		}
	}
}
