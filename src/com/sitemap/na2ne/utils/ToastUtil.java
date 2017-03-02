package com.sitemap.na2ne.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * com.sitemap.stm.utils
 * 
 * @author chenmeng
 * @Description Toast�����࣬�ֲ�ͬ����������ʾ
 * @date create at 2016��9��12�� ����4:19:07
 */
public class ToastUtil {

	/**
	 * ��ʱ����ʾ λ�þ���
	 * 
	 * @param context
	 *            ���ڵ�activity
	 * @param title
	 *            ��ʾ������
	 */
	public static void showCenterLong(Context context, String title) {
		Toast toast = Toast.makeText(context, title, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	/**
	 * ��ʾʱ�����룬λ�þ���
	 * 
	 * @param context
	 *            ���ڵ�activity
	 * @param title
	 *            ��ʾ������
	 */
	public static void showCenterShort(Context context, String title) {
		Toast toast = Toast.makeText(context, title, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	/**
	 * ��ʱ����ʾ λ�þ���
	 * 
	 * @param context
	 * @param title
	 */
	@SuppressWarnings("deprecation")
	public static void showBottomLong(Context context, String title) {
		Toast toast = Toast.makeText(context, title, Toast.LENGTH_LONG);
		WindowManager manager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		toast.setGravity(Gravity.BOTTOM, 0, manager.getDefaultDisplay()
				.getHeight() / 10);
		toast.show();
	}

	/**
	 * ��ʱ����ʾ λ�þ���
	 * 
	 * @param context
	 * @param title
	 */
	@SuppressWarnings("deprecation")
	public static void showBottomShort(Context context, String title) {
		Toast toast = Toast.makeText(context, title, Toast.LENGTH_SHORT);
		WindowManager manager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		toast.setGravity(Gravity.BOTTOM, 0, manager.getDefaultDisplay()
				.getHeight() / 10);
		toast.show();
	}
}
