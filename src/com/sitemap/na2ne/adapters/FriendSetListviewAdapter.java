package com.sitemap.na2ne.adapters;

import java.util.ArrayList;
import java.util.List;

import com.saitu.na2ne.R;
import com.sitemap.na2ne.application.MyApplication;
import com.sitemap.na2ne.models.UserFriendModel;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 定位记录好友列表适配器
 * 
 * @author zhang create at 2016年1月5日 下午2:02:07
 */

public class FriendSetListviewAdapter extends BaseAdapter {

	private LayoutInflater mInflater;// 载入页面
	private Holder holder;// ListView优化类
	public List<UserFriendModel> luserFriend = new ArrayList<UserFriendModel>();// 所有好友

	/**
	 * ListView优化类
	 */
	private class Holder {
		private TextView friend_item_img;// 好友图片
		private TextView friend_item_show;// 好友显示
		private TextView friend_item_open_type;// 好友开通状态
		private TextView friend_item_time;// 好友添加时间
		private TextView friend_item_type;// 好友计费状态
	}

	public FriendSetListviewAdapter(Context context) {
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		for (int i = 0; i < MyApplication.luserFriend.size(); i++) {
			luserFriend.add(MyApplication.luserFriend.get(i));
		}
	}

	@Override
	public int getCount() {

		return luserFriend.size();
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
			convertView = mInflater.inflate(
					R.layout.activity_friend_listview_item, null);
			holder = new Holder();
			holder.friend_item_img = (TextView) convertView
					.findViewById(R.id.friend_item_img);
			holder.friend_item_show = (TextView) convertView
					.findViewById(R.id.friend_item_show);
			holder.friend_item_open_type = (TextView) convertView
					.findViewById(R.id.friend_item_open_type);
			holder.friend_item_time = (TextView) convertView
					.findViewById(R.id.friend_item_time);
			holder.friend_item_type = (TextView) convertView
					.findViewById(R.id.friend_item_type);
			convertView.setTag(holder);
		}
		if (luserFriend.get(position).getFriendType().equals("0")) {
			holder.friend_item_img.setBackgroundResource(R.drawable.popa);	
		} else {
			holder.friend_item_img.setBackgroundResource(R.drawable.popb);
		}
		if (luserFriend.get(position).getFriendName() != null
				&& !luserFriend.get(position).getFriendName().equals("")) {
			holder.friend_item_show.setText(luserFriend.get(position)
					.getFriendName());
		} else {
			holder.friend_item_show.setText(luserFriend.get(position)
					.getFriendPhone());
		}
		holder.friend_item_time.setText("");
		if (luserFriend.get(position).getIsAgree().equals("0")) {
			holder.friend_item_open_type.setText("未同意");
			holder.friend_item_open_type.setTextColor(Color
					.parseColor("#ffff0000"));
			return convertView;
			}
		else {
			holder.friend_item_open_type.setText("已开通");
			holder.friend_item_open_type.setTextColor(Color
					.parseColor("#6eb348"));
		}
		// 免费
		if (luserFriend.get(position).getPackageType().equals("4")) {
			holder.friend_item_type.setText(luserFriend.get(position)
					.getPackageName());
		}
		// 移动联通电信
		else {
			if (luserFriend.get(position).getSurplusTimes() != null) {
				holder.friend_item_type
				.setText(luserFriend.get(position)
						.getPackageName()+":  剩余"
						+ luserFriend.get(position)
								.getSurplusTimes() + "次");
			}else {
				holder.friend_item_type.setText(luserFriend.get(position)
						.getPackageName());
			}
		}

		return convertView;
	}

}
