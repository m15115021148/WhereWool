package com.sitemap.na2ne.adapters;

import java.util.ArrayList;
import java.util.List;

import com.saitu.na2ne.R;
import com.sitemap.na2ne.application.MyApplication;
import com.sitemap.na2ne.models.PackageModel;
import com.sitemap.na2ne.models.RefundListItemModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;


/**
 * 
 * com.sitemap.na2ne.adapters.PackageListviewAdapter
 * @author zhang
 * 套餐类型adapter
 * create at 2016年2月29日 下午5:05:57
 */
public class PackageListviewAdapter extends BaseAdapter {

	private LayoutInflater mInflater;// 载入页面
	private Holder holder;// ListView优化类
	private List<PackageModel> lPackage=new ArrayList<PackageModel>();//要显示的套餐的集合
	private int oldPackage=-1;//默认选中的套餐
	private int oldPosition=0;//默认选中的套餐的下标识

/**
 * 
 * com.sitemap.na2ne.adapters.Holder
 * @author zhang
 * create at 2016年2月29日 下午5:06:03
 */
	private class Holder {
		private TextView package_ischecked;// 是否选中
		private TextView package_name;//套餐名称
	}

	public PackageListviewAdapter(Context context,List<PackageModel> lPackage,int oldPackage) {
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.lPackage=lPackage;
		this.oldPackage=oldPackage;
	}
	
	/**
	 * 选中的套餐
	 * @param checkPackage
	 */
	public void setCheckPackage(int checkPackage){
		this.oldPackage=checkPackage;
	}

	/**
	 * 默认选中套餐的下标
	 * @return
	 */
	public int getCheckPackage(){
		return oldPosition;
	}

	@Override
	public int getCount() {
		return lPackage.size();
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
					R.layout.package_listview_item, null);
			holder = new Holder();
			holder.package_ischecked = (TextView) convertView
					.findViewById(R.id.package_ischecked);
			holder.package_name = (TextView) convertView
					.findViewById(R.id.package_name);
			convertView.setTag(holder);
		}
		holder.package_name.setText(lPackage.get(position).getPackageName());
		holder.package_ischecked.setBackgroundResource(R.drawable.packageuncheck);
		if (Integer.parseInt(lPackage.get(position).getPackageID())==oldPackage) {
			oldPosition=position;
			holder.package_ischecked.setBackgroundResource(R.drawable.packagecheck);
		}


		return convertView;
	}

}
