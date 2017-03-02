package com.sitemap.na2ne.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * 
 * @ClassName:     Size.java
 * @Description:   手机固件属性获取类；配置手机的适配性；
 * @author         chenhao
 * @Date           2015-11-14
 */
 

public class Size {

	private int screenHeight;
	private int screenWidth;
//	private float density; // 屏幕密度（像素比例：0.75/1.0/1.5/2.0）
	private int densityDPI; // 屏幕密度（每寸像素：120/160/240/320）

	// 获取屏幕尺寸
	public Size(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		windowManager.getDefaultDisplay().getMetrics(dm);
		this.screenHeight = dm.heightPixels;
		this.screenWidth = dm.widthPixels;
//		density = dm.density;
		densityDPI = dm.densityDpi;

	}

	// 获取合适字体大小
	public double getContentSize(double d) {

		return (double) (d * getScreenPhysicalSize()) - 1;
	}

	// 获取合适字体大小
	public int getFontSize() {

		return (int) (3 * getScreenPhysicalSize()) - 1;
	}

	// 获取屏幕大小，如：5.0英寸
	public double getScreenPhysicalSize() {
		double diagonalPixels = Math.sqrt(Math.pow(screenWidth, 2)
				+ Math.pow(screenHeight, 2));
		return diagonalPixels / densityDPI;
	}

}
