package com.sitemap.na2ne.views;

import com.saitu.na2ne.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

/**
 * 列表无数据时，显示的控件
 * Description: 
 * @author chenhao
 * @date   2016-12-30
 */
public class EmptyView {

	public static View getView(Context context, String title) {
		View view = LayoutInflater.from(context).inflate(R.layout.empty_view,null);
		view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));  
		TextView textView = (TextView) view.findViewById(R.id.title);
		textView.setText(title);
		return view;
	}

}
