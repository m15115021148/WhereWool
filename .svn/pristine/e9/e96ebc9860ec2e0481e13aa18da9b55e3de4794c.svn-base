package com.sitemap.na2ne.adapters;

import java.util.ArrayList;
import java.util.List;

import com.saitu.na2ne.R;
import com.sitemap.na2ne.application.MyApplication;
import com.sitemap.na2ne.models.BillModel;

import android.content.Context;
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
 
public class BillListviewAdapter extends BaseAdapter{
	
	
	private LayoutInflater mInflater;//载入页面
	private Holder holder;//ListView优化类
	private List<BillModel> lbill = new ArrayList<BillModel>();// 消费记录集合
	
	/**
	 * ListView优化类
	 * @author zhang
	 * create at 2015年12月23日 下午3:26:03
	 */
	private class Holder {
		private TextView bill_item_img;//图片
		private TextView bill_item_name;//消费对象名称
		private TextView bill_item_phone;//消费对象电话
		private TextView bill_item_money;//消费金额
		private TextView bill_item_time;//消费时间
		private TextView bill_item_nian;//消费年
		private TextView bill_item_type;//消费类型
	}

	public BillListviewAdapter(Context context,List<BillModel> lbill) {
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.lbill=lbill;
	}

	@Override
	public int getCount() {
		return lbill.size();
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
			convertView = mInflater.inflate(R.layout.activity_bill_listview_item,
					null);
			holder = new Holder();
			holder.bill_item_img = (TextView) convertView
					.findViewById(R.id.bill_item_img);
			holder.bill_item_name = (TextView) convertView
					.findViewById(R.id.bill_item_name);
			holder.bill_item_phone = (TextView) convertView
					.findViewById(R.id.bill_item_phone);
			holder.bill_item_money = (TextView) convertView
					.findViewById(R.id.bill_item_money);
			holder.bill_item_time = (TextView) convertView
					.findViewById(R.id.bill_item_time);
			holder.bill_item_nian = (TextView) convertView
					.findViewById(R.id.bill_item_nian);
			holder.bill_item_type = (TextView) convertView
					.findViewById(R.id.bill_item_type);
			convertView.setTag(holder);
		}
			if (lbill.get(position).getFriendType()==0) {
				holder.bill_item_img.setBackgroundResource(R.drawable.listimga);
			}else {
				holder.bill_item_img.setBackgroundResource(R.drawable.listimgb);
			}
			if (lbill.get(position).getFriendName()==null) {
				holder.bill_item_name.setText("该好友已删除");
			}else {
				holder.bill_item_name.setText(lbill.get(position).getFriendName());
			}
		
			holder.bill_item_phone.setText(lbill.get(position).getFriendPhone());
			holder.bill_item_money.setText("￥ "+lbill.get(position).getMoney());
			holder.bill_item_time.setText(lbill.get(position).getTime().substring(5,10));
			holder.bill_item_nian.setText(lbill.get(position).getTime().substring(0,4));
			if (lbill.get(position).getProcess().equals("0")) {
				holder.bill_item_type.setText("已扣款");
			}
			if (lbill.get(position).getProcess().equals("1")) {
				holder.bill_item_type.setText("处理中");
			}	
			if (lbill.get(position).getProcess().equals("2")) {
				holder.bill_item_type.setText("已退款");
			}
		
		
		return convertView;
	}

}
