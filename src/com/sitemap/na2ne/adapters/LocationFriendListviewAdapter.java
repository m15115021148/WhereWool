package com.sitemap.na2ne.adapters;

import com.saitu.na2ne.R;
import com.sitemap.na2ne.application.MyApplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 定位记录好友列表适配器
 * 
 * @author zhang create at 2015年12月22日 下午2:02:07
 */
 
public class LocationFriendListviewAdapter extends BaseAdapter{
	
	
	private LayoutInflater mInflater;//载入页面
	private Holder holder;//ListView优化类
	
	/**
	 * ListView优化类
	 * @author zhang
	 * create at 2015年12月23日 下午3:26:03
	 */
	private class Holder {
		private TextView location_listview_img;//好友图片
		private TextView location_listview_name;//好友名称
		private TextView location_listview_phone;//好友电话
		private TextView location_listview_time;//最后定位时间
	}

	public LocationFriendListviewAdapter(Context context) {
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		MyApplication.lOpenUserFriend.clear();
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
			convertView = mInflater.inflate(R.layout.activity_location_listview_item,
					null);
			holder = new Holder();
			holder.location_listview_img = (TextView) convertView
					.findViewById(R.id.location_listview_img);
			holder.location_listview_name = (TextView) convertView
					.findViewById(R.id.location_listview_name);
			holder.location_listview_phone= (TextView) convertView
					.findViewById(R.id.location_listview_phone);
			holder.location_listview_time= (TextView) convertView
			.findViewById(R.id.location_listview_time);
			convertView.setTag(holder);
		}
			
			if (MyApplication.lOpenUserFriend.get(position).getFriendType().equals("0")) {
				holder.location_listview_img.setBackgroundResource(R.drawable.listimga);
			}else {
				holder.location_listview_img.setBackgroundResource(R.drawable.listimgb);
			}
			holder.location_listview_name.setText(MyApplication.lOpenUserFriend.get(position).getFriendName());
			holder.location_listview_phone.setText(MyApplication.lOpenUserFriend.get(position).getFriendPhone());
			holder.location_listview_time.setText("");

		
		
		return convertView;
	}

}
