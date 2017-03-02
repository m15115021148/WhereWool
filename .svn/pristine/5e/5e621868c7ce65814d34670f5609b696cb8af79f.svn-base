package com.sitemap.na2ne.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.saitu.na2ne.R;
import com.sitemap.na2ne.application.MyApplication;

/**
 * 
 * @ClassName:     FriendListviewAdapter.java
 * @Description:   用户好友列表数据适配器
 * @author         chenhao
 * @Date           2015-11-14
 */
 
public class FriendListviewAdapter extends BaseAdapter{
	
	
	private LayoutInflater mInflater;
	private Holder holder;
	
	private class Holder {
		private TextView popup_list_img;//好友类型图标
		private TextView popup_list_show;//好友显示
		private LinearLayout popup_bg;//背景色
		private TextView popup_list_sel;//是否选中
		
		
	}

	public FriendListviewAdapter(Context context) {
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (MyApplication.lOpenUserFriend.size()>0) {
			MyApplication.lOpenUserFriend.clear();
		}
		for (int i = 0; i < MyApplication.luserFriend.size(); i++) {
			if (MyApplication.luserFriend.get(i).getIsAgree().equals("1")) {
				MyApplication.lOpenUserFriend.add(MyApplication.luserFriend.get(i));
			}
		}
	}

	@Override
	public int getCount() {
		
		return MyApplication.lOpenUserFriend.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView != null) {
			holder = (Holder) convertView.getTag();
		} else {
			convertView = mInflater.inflate(R.layout.popupwinow_linkman_listview_item,
					null);
			holder = new Holder();
			holder.popup_list_img = (TextView) convertView
					.findViewById(R.id.popup_list_img);
			holder.popup_list_show = (TextView) convertView
					.findViewById(R.id.popup_list_show);
			holder.popup_bg = (LinearLayout) convertView
					.findViewById(R.id.popup_bg);
			holder.popup_list_sel = (TextView) convertView
					.findViewById(R.id.popup_list_sel);
			convertView.setTag(holder);
		}
		if (MyApplication.isselect&&position==0) {
			holder.popup_bg.setBackgroundColor(Color.parseColor("#dceff8"));
			holder.popup_list_sel.setVisibility(View.VISIBLE);
		}else {
			holder.popup_bg.setBackgroundColor(Color.parseColor("#ffffff"));
			holder.popup_list_sel.setVisibility(View.GONE);
		}
		if (MyApplication.lOpenUserFriend.get(position)
				.getFriendName()!=null&&!MyApplication.lOpenUserFriend.get(position)
				.getFriendName().equals("")) {
			holder.popup_list_show.setText(MyApplication.lOpenUserFriend.get(position)
					.getFriendName());
		}else {
			holder.popup_list_show.setText(MyApplication.lOpenUserFriend.get(position)
					.getFriendPhone());
		}
			if (MyApplication.lOpenUserFriend.get(position).getFriendType().equals("0")) {
				holder.popup_list_img.setBackgroundResource(R.drawable.popa);
			}else {
				holder.popup_list_img.setBackgroundResource(R.drawable.popb);
			}
			holder.popup_list_show.setTextColor(Color.parseColor("#535c67"));
			
		
		return convertView;
	}

}
