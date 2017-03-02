package com.sitemap.na2ne.adapters;

import java.util.List;

import org.xutils.x;

import cn.jpush.a.a.h;

import com.saitu.na2ne.R;
import com.sitemap.na2ne.models.MessageModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/** 
 * com.sitemap.na2ne.adapters
 * @author chenmeng
 * @Description 消息页面 适配器
 * @date create at  2016年6月28日 上午9:21:53
 */
public class MessageListViewAdapter extends BaseAdapter{
	private Context mContext;	
	private List<MessageModel> mList;
	private Holder holder;
	
	public MessageListViewAdapter(Context context,List<MessageModel> list){
		this.mContext = context;
		this.mList = list;
	}
	
	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_msg_listview_item, null);
			holder = new Holder();
			holder.msgImg = (ImageView) convertView.findViewById(R.id.msg_img);
			holder.msgTitle = (TextView) convertView.findViewById(R.id.msg_title);
			holder.msgContent = (TextView) convertView.findViewById(R.id.msg_content);
			holder.msgTime = (TextView) convertView.findViewById(R.id.msg_time);
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
		MessageModel model = mList.get(position);
		
		//加载图片
		if(!model.getImg().equals("")&&model.getImg()!=null){
//			BitmapUtils utils = new BitmapUtils(mContext);
//			utils.display(holder.msgImg, model.getImg());
			x.image().bind(holder.msgImg, model.getImg());
		}else{
			
		}		
		
		holder.msgTitle.setText(model.getTitle());
		holder.msgContent.setText(model.getBrief());
		holder.msgTime.setText(model.getTime());

		
		return convertView;
	}
	
	private class Holder{
		ImageView msgImg;//图片 
		TextView msgTitle,msgContent,msgTime;//标题  内容 时间
	}

}
