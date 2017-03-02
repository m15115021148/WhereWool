package com.sitemap.na2ne.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * Description: 强制回收图片资源，减少内存占用；
 * @author chenhao
 * @date   2016-5-10
 */
public class RecycleImgUtil {
	
	/**
	 * 
	 * @param ImageView
	 */
	public static void releaseImageResouce(ImageView imageView) {
        if (imageView == null) return;
        Drawable drawable = imageView.getDrawable();
        if (drawable != null && drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            Bitmap bitmap = bitmapDrawable.getBitmap();
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
    }
	
	/**
	 * 
	 * @param LinearLayout
	 */
	public static void releaseImageResouce(LinearLayout linearLayout) {
        if (linearLayout == null) return;
        Drawable drawable = linearLayout.getBackground();
        if (drawable != null && drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            Bitmap bitmap = bitmapDrawable.getBitmap();
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
    }
	
	
	/**
	 * 
	 * @param TextView
	 */
	public static void releaseImageResouce(TextView view) {
        if (view == null) return;
        Drawable drawable = view.getBackground();
        if (drawable != null && drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            Bitmap bitmap = bitmapDrawable.getBitmap();
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
    }
}
