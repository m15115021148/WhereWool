package com.sitemap.na2ne.views;

import com.saitu.na2ne.R;
import com.sitemap.na2ne.application.MyApplication;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.LinearLayout.LayoutParams;

/**
 * ��ϵ�˶Ի���
 * 
 * @time 2015/11/3
 * @author xym
 * @param context ������
 * 
 *  parent ����view
 * 
 *  contentView ��ʾ��view
 * 
 *  w h ռ��Ļ�ı���
 * 
 *  type ����
 */
public class LinkManPopuWindow {

	private static int myx;
	private static int myy;
	private static int xPos;
	private static int yPos;
	private static PopupWindow popupWindow;
	private static final int ZERO=0,HIGH=250,NINETTEN=19,TWENTY=20;//������λ�ã��߶�

	// չʾ�Ի���
	public static void showpopuwindow(Context context, View parent,
			View contentView, int w, int h, int type) {
		popupWindow = new PopupWindow(contentView);
		popupWindow.setFocusable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.setOutsideTouchable(false);

		// ��ϵ�˶Ի���
		if (type == 1) {
			calculate(context, w, h, popupWindow);
			popupWindow.setWidth(xPos * 2 + 30);
//			popupWindow.setHeight(yPos * 3 + 50);
			popupWindow.setHeight(LayoutParams.WRAP_CONTENT);
			popupWindow.showAtLocation(parent, Gravity.TOP, myx, myy);
			
		}
		if (type == 2) {
			WindowManager wm = (WindowManager) contentView.getContext()
                    .getSystemService(Context.WINDOW_SERVICE);
			// ����SelectPicPopupWindow��������Ŀ�
//			popupWindow.setWidth(wm.getDefaultDisplay().getWidth() * NINETTEN/TWENTY);
			popupWindow.setWidth(LayoutParams.MATCH_PARENT);
			// ����SelectPicPopupWindow��������ĸ�
			popupWindow.setHeight(LayoutParams.WRAP_CONTENT);
			popupWindow.setAnimationStyle(R.style.PopupAnimation);
			popupWindow.showAtLocation(parent, Gravity.BOTTOM
					| Gravity.CENTER_HORIZONTAL, ZERO, 0);
			
		}
		if (type == 3) {
			WindowManager wm = (WindowManager) contentView.getContext()
                    .getSystemService(Context.WINDOW_SERVICE);
			// ����SelectPicPopupWindow��������Ŀ�
//			popupWindow.setWidth(wm.getDefaultDisplay().getWidth() * NINETTEN/TWENTY);
//			popupWindow.setWidth((int) (MyApplication.width*0.8));
			popupWindow.setWidth(LayoutParams.MATCH_PARENT);
			// ����SelectPicPopupWindow��������ĸ�
//			popupWindow.setHeight((int) (MyApplication.height*0.6));
			popupWindow.setHeight(LayoutParams.MATCH_PARENT);
			popupWindow.showAtLocation(parent, Gravity.CENTER
					| Gravity.CENTER, ZERO, 0);
			
		}

	}
	
	public static void dismmis()
	{
		if(popupWindow!=null)
		{
		popupWindow.dismiss();
		}
	}

	/**
	 * �����ֻ���Ļ����Ի���Ĵ�С��λ��
	 * @param context
	 * @param w
	 * @param h
	 * @param popupWindow
	 */
	public static void calculate(Context context, int w, int h,
			PopupWindow popupWindow) {
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		xPos = windowManager.getDefaultDisplay().getWidth() / w
				- popupWindow.getWidth() / w;
		yPos = windowManager.getDefaultDisplay().getHeight() / h
				- popupWindow.getHeight() / h;
		myx = xPos - 50;
		myy = yPos + 20;
	}

}