/**********************************************************************
 * FILE              ：DefineDialog.java
 * PACKAGE			   ：com.jxky.kuaiyi.util.view
 * AUTHOR            ：mayl
 * DATE				   ：2014-5-5下午2:30:14
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
package com.hiver.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.seaway.android.sdk.tools.AndroidUtil;
import com.hiver.ui.R;
import com.hiver.ui.text.Constant;


/**
 * 项目名称:KuaiYi 
 * 类名称: DefineDialog.java
 * 类描述： 默认弹框
 * 创建人：  mayl
 * 创建时间:2014-5-5下午2:30:14
 * -------------------------------修订历史--------------------------
 * 修改人：mayl
 * 修改时间:2014-5-5下午2:30:14 
 * 修改备注：
 * @version：
 */
public class DefineDialog extends Dialog {
	private Context context;
	private TextView titleTxt;
	private TextView dialogMessageTxt;
	private TextView msgCenterTxt;
	private TextView msgSmallTxt;
	private Button positiveBtn;
	private Button middleBtn;
	private Button negativeBtn;
	private RelativeLayout contentLayout;
	
	public DefineDialog(Context context) {
		super(context,R.style.myDialogTheme);
		this.setCancelable(false);
		this.context = context;
		initView();
	}
	
	/**
	 * 初始化控件
	 */
	private void initView(){
		View view =LayoutInflater.from(context).inflate(R.layout.default_dialog, null);
		titleTxt = (TextView) view.findViewById(R.id.dialog_title);
		dialogMessageTxt=(TextView) view.findViewById(R.id.dialog_message);
		msgCenterTxt=(TextView) view.findViewById(R.id.dialog_message_center);
		msgSmallTxt = (TextView) view.findViewById(R.id.dialog_message_small);
		contentLayout = (RelativeLayout)view.findViewById(R.id.dialog_content_main);
		positiveBtn = (Button) view.findViewById(R.id.dialog_positive_button);
		middleBtn = (Button) view.findViewById(R.id.dialog_middle_button);
		negativeBtn = (Button) view.findViewById(R.id.dialog_negative_button);
		
		if (Constant.SCREEN_WIDTH <= 0) {
			/** 获取屏幕的分辨率 */
			DisplayMetrics dm = new DisplayMetrics();
			((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
			Constant.SCREEN_WIDTH = dm.widthPixels;
			Constant.SCREEN_HEIGHT = dm.heightPixels;
		}

		int sw= AndroidUtil.dip2px( 50);
		LayoutParams params = new LayoutParams(Constant.SCREEN_WIDTH-sw,
				LayoutParams.WRAP_CONTENT);
//		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//				LinearLayout.LayoutParams.WRAP_CONTENT);
		if (params.width <= 0)
			params.width = ViewGroup.LayoutParams.WRAP_CONTENT*7/8;
		this.addContentView(view, params);
	}
	
	public void setTitle(String title){
		titleTxt.setVisibility(View.VISIBLE);
		titleTxt.setText(title);
	}
	 public void setTitle(String title, Drawable icon) {
		 titleTxt.setVisibility(View.VISIBLE);
	     titleTxt.setText(title);
	    if (icon != null) {
				titleTxt.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
			}
		}
	 
	 
	public void setView(View view,LayoutParams param){
		contentLayout.removeAllViews();
		contentLayout.addView(view,param);
	}
	
	public void setView(View view) {
		contentLayout.removeAllViews();
		LayoutParams param = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		param.rightMargin = 10;
		param.leftMargin = 10;
		param.topMargin = 5;
		param.bottomMargin = 5;
		contentLayout.addView(view, param);
	}
	//左边按钮
	public void setPositiveButton(String positive,View.OnClickListener listener){
		positiveBtn.setVisibility(View.VISIBLE);
		positiveBtn.setText(positive);
		positiveBtn.setOnClickListener(listener);
	}
	
	public void setMiddleButton(String middle,View.OnClickListener listener){
		middleBtn.setVisibility(View.VISIBLE);
		middleBtn.setText(middle);
		middleBtn.setOnClickListener(listener);
	}
	//右边按钮
	public void setNegativeButton(String negative,View.OnClickListener listener){
		negativeBtn.setVisibility(View.VISIBLE);
		negativeBtn.setText(negative);
		negativeBtn.setOnClickListener(listener);
	}
	/**
	 * 显示的内容
	 * @time 2014-6-19下午5:00:27
	 * @return void
	 * @TODO
	 */
	public void setMessage(String message){
		contentLayout.setVisibility(View.VISIBLE);
		if(!TextUtils.isEmpty(message) && message.length()<=12){
			dialogMessageTxt.setGravity(Gravity.CENTER);
		}
		dialogMessageTxt.setText(message);
	}
	
	public void setMessageWithColor(SpannableString message){
		contentLayout.setVisibility(View.VISIBLE);
		dialogMessageTxt.setText(message);
	}
	
	public void setMessage(int message){
		String msgString = context.getResources().getString(message);
		this.setMessage(msgString);
	}
 
	public void setMsgCenter(int message){
		contentLayout.setVisibility(View.VISIBLE);
		msgCenterTxt.setVisibility(View.VISIBLE);
		dialogMessageTxt.setVisibility(View.GONE);
		msgCenterTxt.setText(message);
	}
	
	public void setMsgCenter(String message){
		contentLayout.setVisibility(View.VISIBLE);
		msgCenterTxt.setVisibility(View.VISIBLE);
		dialogMessageTxt.setVisibility(View.GONE);
		msgCenterTxt.setText(message);
	}
	
	public void setMsgSmall(int message){
		String msgString = context.getResources().getString(message);
		this.setMsgSmall(msgString);
	}
	
	public void setMsgSmall(String message){
		contentLayout.setMinimumHeight(80);
		RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		param.topMargin = 20;
		dialogMessageTxt.setLayoutParams(param);
		msgSmallTxt.setVisibility(View.VISIBLE);
		msgSmallTxt.setText(message);
	}
	
	public interface OnButtonClickListener {
		public void onClick(DefineDialog dialog, View v);
	}
	
	/**
	 * 单独按钮
	 * @param middle
	 * @param listener
	 * void
	 */
	public void setMiddleButton(String middle,final OnButtonClickListener listener){
		middleBtn.setVisibility(View.VISIBLE);
		middleBtn.setText(middle);
		middleBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DefineDialog.this.dismiss();
				listener.onClick(DefineDialog.this,v);
			}
		});
	}
	
	/**
	 * 双按钮左边按钮
	 * @param positive
	 * @param listener
	 * void
	 */
	public void setPositiveButton(String positive,final OnButtonClickListener listener){
		positiveBtn.setVisibility(View.VISIBLE);
		positiveBtn.setText(positive);
		positiveBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DefineDialog.this.dismiss();
				listener.onClick(DefineDialog.this,v);
			}
		});
	}
	
	/**
	 * 双按钮右边按钮
	 * @param negative
	 * @param listener
	 * void
	 */
	public void setNegativeButton(String negative,final OnButtonClickListener listener){
		negativeBtn.setVisibility(View.VISIBLE);
		negativeBtn.setText(negative);
		negativeBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DefineDialog.this.dismiss();
				listener.onClick(DefineDialog.this,v);
			}
		});
	}

}
