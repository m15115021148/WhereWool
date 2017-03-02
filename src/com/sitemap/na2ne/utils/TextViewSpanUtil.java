package com.sitemap.na2ne.utils;

import com.sitemap.na2ne.activities.RefundActivity;
import com.sitemap.na2ne.application.MyApplication;
import com.sitemap.na2ne.config.WebUrlConfig;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;

/**
 * com.sitemap.na2ne.utils.TextViewSpanUtil
 * @author zhang
 * create at 2016年3月31日 下午3:27:19
 */
public class TextViewSpanUtil extends ClickableSpan {
	private String str;//内容
	private    Context context;//布局
	private int color;//显示的颜色
	    public TextViewSpanUtil(String str,Context context,int color){
	        super();
	        this.str= str;
	        this.context = context;
	        this.color=color;
	    }

	@Override
	public void onClick(View widget) {
//		Log.i("TAG", "aaaaaaaaaaaaaaaaaaaaaaa");
		AlertDialog.Builder normalDia = new AlertDialog.Builder(
				context);
		normalDia.setTitle("提示");
		normalDia.setMessage("是否拨打客服电话？");

		normalDia.setPositiveButton("确定",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent myIntentDial=new Intent("android.intent.action.CALL",Uri.parse("tel:"+str));
						context.startActivity(myIntentDial);
					}
				});
		normalDia.setNegativeButton("取消",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		normalDia.create().show();
	
	}

	@Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor(color);
    }
	
	
}
