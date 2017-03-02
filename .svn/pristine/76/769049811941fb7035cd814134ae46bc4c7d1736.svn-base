package com.sitemap.na2ne.adapters;

import java.util.ArrayList;
import java.util.List;

import com.saitu.na2ne.R;
import com.sitemap.na2ne.application.MyApplication;
import com.sitemap.na2ne.models.RefundListItemModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 退款记录列表适配器
 * 
 * @author zhang create at 2015年12月24日
 */

public class RefundOldListviewAdapter extends BaseAdapter {

	private LayoutInflater mInflater;// 载入页面
	private Holder holder;// ListView优化类
	private List<RefundListItemModel> lrefund = new ArrayList<RefundListItemModel>();// 退款记录集合

	/**
	 * ListView优化类
	 * 
	 * @author zhang create at 2015年12月24日
	 */
	private class Holder {
		private TextView refund_time;// 申请退款时间
		private TextView refund_status;// 退款状态
		private TextView refund_show;// 显示
		private TextView refund_show_xiangqing;// 显示详情
	}

	public RefundOldListviewAdapter(Context context,
			List<RefundListItemModel> lrefund) {
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.lrefund = lrefund;
	}

	@Override
	public int getCount() {
		return lrefund.size();
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
					R.layout.activity_refund_old_listview_item, null);
			holder = new Holder();
			holder.refund_time = (TextView) convertView
					.findViewById(R.id.refund_time);
			holder.refund_status = (TextView) convertView
					.findViewById(R.id.refund_status);
			holder.refund_show = (TextView) convertView
					.findViewById(R.id.refund_show);
			holder.refund_show_xiangqing = (TextView) convertView
					.findViewById(R.id.refund_show_xiangqing);
			
			convertView.setTag(holder);
		}
			
			holder.refund_time.setText(lrefund.get(position).getRequestTime());
			switch (lrefund.get(position).getStatus()) {
			case 0:
				holder.refund_status.setText("退款处理中");
				holder.refund_show.setText("退款金额");
				holder.refund_show_xiangqing.setText("￥"+lrefund.get(position).getRefundMoney());
				break;
			case 1:
				holder.refund_status.setText("退款成功");
				holder.refund_show.setText("退款金额");
				holder.refund_show_xiangqing.setText("￥"+lrefund.get(position).getRefundMoney());
				break;
			case 2:
				holder.refund_status.setText("退款失败");
				holder.refund_show.setText("失败原因");
				holder.refund_show_xiangqing.setText(lrefund.get(position).getFailReason());
				break;
			default:
				break;
			}


		return convertView;
	}

}
