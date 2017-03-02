package com.sitemap.na2ne.adapters;

import java.util.ArrayList;
import java.util.List;

import com.saitu.na2ne.R;
import com.sitemap.na2ne.application.MyApplication;
import com.sitemap.na2ne.models.LocationDetailedModel;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 定位记录明细列表适配器
 * 
 * @author zhang create at 2015年12月22日 下午2:02:07
 */
 
public class LocationDetailedListviewAdapter extends BaseAdapter{
	
	
	private LayoutInflater mInflater;//载入页面
	private Holder holder;//ListView优化类
	private List<LocationDetailedModel> ldetailed = new ArrayList<LocationDetailedModel>();// 定位记录集合
	
	/**
	 * ListView优化类
	 * @author zhang
	 * create at 2015年12月23日 下午3:26:03
	 */
	private class Holder {
		private TextView location_detailed_time;//定位时间
		private TextView location_detailed_location;//定位地址
		private TextView location_detailed_status;//定位状态
	}

	public LocationDetailedListviewAdapter(Context context,List<LocationDetailedModel> ldetailed) {
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.ldetailed=ldetailed;
	}

	@Override
	public int getCount() {
		return ldetailed.size();
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
			convertView = mInflater.inflate(R.layout.activity_location_detailed_listview_item,
					null);
			holder = new Holder();
			holder.location_detailed_time = (TextView) convertView
					.findViewById(R.id.location_detailed_time);
			holder.location_detailed_location = (TextView) convertView
					.findViewById(R.id.location_detailed_location);
			holder.location_detailed_status= (TextView) convertView
					.findViewById(R.id.location_detailed_status);
			convertView.setTag(holder);
		}
			
			holder.location_detailed_time.setText(ldetailed.get(position).getTime());
			holder.location_detailed_location.setText(ldetailed.get(position).getLocation());
			if (ldetailed.get(position).getStatus()==0) {
				holder.location_detailed_status.setText("定位成功");
				holder.location_detailed_status.setTextColor(Color.parseColor("#1cace9"));
			}else {
				holder.location_detailed_status.setText("定位失败");
				holder.location_detailed_status.setTextColor(Color.parseColor("#e95558"));
			}
			

		
		
		return convertView;
	}

}
