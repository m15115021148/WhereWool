package com.sitemap.na2ne.adapters;

import java.util.ArrayList;
import java.util.List;

import com.saitu.na2ne.R;
import com.sitemap.na2ne.models.PayModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 充值记录列表适配器
 * 
 * @author zhang create at 2015年12月22日 下午2:02:07
 */
 
public class RechargeOldListviewAdapter extends BaseAdapter{
	
	
	private LayoutInflater mInflater;//载入页面
	private Holder holder;//ListView优化类
	private List<PayModel> lpay = new ArrayList<PayModel>();// 充值记录集合
	
	/**
	 * ListView优化类
	 * @author zhang
	 * create at 2015年12月23日 下午3:26:03
	 */
	private class Holder {
		private TextView recharge_time;// 申请充值时间
		private TextView recharge_status;// 充值状态
		private TextView recharge_show;// 显示
		private TextView recharge_show_xiangqing;// 显示详情
	}

	public RechargeOldListviewAdapter(Context context,List<PayModel> lpay) {
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.lpay=lpay;
	}

	@Override
	public int getCount() {
		return lpay.size();
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
			convertView = mInflater.inflate(R.layout.activity_recharge_old_listview_item,
					null);
			holder = new Holder();
			holder.recharge_time = (TextView) convertView
					.findViewById(R.id.recharge_time);
			holder.recharge_status = (TextView) convertView
					.findViewById(R.id.recharge_status);
			holder.recharge_show = (TextView) convertView
					.findViewById(R.id.recharge_show);
			holder.recharge_show_xiangqing = (TextView) convertView
					.findViewById(R.id.recharge_show_xiangqing);
			convertView.setTag(holder);
		}
			
			holder.recharge_time.setText(lpay.get(position).getPayTime());
			if (lpay.get(position).getStatus().equals("TRADE_SUCCESS")||lpay.get(position).getStatus().equals("SUCCESS")) {
				holder.recharge_status.setText("充值成功");
			}else {
				holder.recharge_status.setText("充值失败");
			}
			holder.recharge_show_xiangqing.setText("￥ +"+lpay.get(position).getPayMoney());

		
		
		return convertView;
	}

}
