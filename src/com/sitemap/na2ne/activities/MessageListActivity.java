package com.sitemap.na2ne.activities;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.saitu.na2ne.R;
import com.sitemap.na2ne.adapters.MessageListViewAdapter;
import com.sitemap.na2ne.application.MyApplication;
import com.sitemap.na2ne.config.RequestCode;
import com.sitemap.na2ne.config.WebUrlConfig;
import com.sitemap.na2ne.http.HttpUtil;
import com.sitemap.na2ne.models.MessageModel;
import com.sitemap.na2ne.utils.ToastUtil;
import com.sitemap.na2ne.views.EmptyView;
import com.sitemap.na2ne.views.MyProgressDialog;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author chenmeng
 *	消息列表页面
 */
public class MessageListActivity extends Activity implements OnClickListener{
	/**本类*/ 
	private MessageListActivity mContext;
	/**listview*/ 
	private ListView mLv;
	/**返回上一层*/ 
	private LinearLayout mBack;
	/**消息数据*/ 
	private List<MessageModel> mList = null;
	/**适配器s*/ 
	private MessageListViewAdapter mAdapter;
	/**获取消息tag*/ 
	private final int GETMSG = 0x2001;
	/**进度条*/ 
	private static MyProgressDialog progressDialog; 
	/**网络请求帮助类对象*/ 
	private HttpUtil http = null;//网络请求帮助类对象

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		// 隐藏应用程序的标题栏，即当前activity的标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_message_list);
		mContext = this;
		if(http == null){
			http = new HttpUtil(handler);
		}
		if (MyApplication.isLogin<0) {
			Intent intent=new Intent(MessageListActivity.this,HomePageActivity.class);
			startActivity(intent);
			finish();
		}
		initView();
		initData();
	}
	
	private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			if (progressDialog != null && progressDialog.isShowing()) {
				progressDialog.dismiss();// 关闭进度条
			}
			switch (msg.what) {
			case HttpUtil.SUCCESS:
				//消息
				if(msg.arg1 == GETMSG){
					mList = new ArrayList<MessageModel>();
					mList.clear();
					mList = JSON.parseArray(msg.obj.toString(), MessageModel.class);
					initListView(mList);
					mAdapter.notifyDataSetChanged();										
				}
				break;
			case HttpUtil.EMPTY:				
				 View view=EmptyView.getView(mContext,"暂无消息");
                 ((ViewGroup)mLv.getParent()).addView(view);  
                 mLv.setEmptyView(view);
				break;
			case HttpUtil.FAILURE:
				ToastUtil.showCenterShort(mContext, RequestCode.ERRORINFO);
				break;
			case HttpUtil.LOADING:
				
				break;
			default:
				break;
		}
		}
	};

	/**
	 * 初始化view
	 */
	private void initView() {
		mBack = (LinearLayout) findViewById(R.id.base_back_lay);
		mLv = (ListView) findViewById(R.id.msg_listview);
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		mBack.setOnClickListener(this);
		if (MyApplication.getNetObject().isNetConnected()) {
			if (progressDialog == null ||!progressDialog.isShowing()) {
				progressDialog=MyProgressDialog.createDialog(this);
				progressDialog.setMessage("加载中...");
				progressDialog.show();
			}
			getMessageData();
		} else {
			ToastUtil.showCenterShort(this, RequestCode.NONETWORK);
		}		
	}
	
	/**
	 * 初始化listview
	 */
	private void initListView(final List<MessageModel> list){
		mAdapter = new MessageListViewAdapter(mContext,list);
		mLv.setAdapter(mAdapter);
		mLv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String url= list.get(position).getContent();
				if(!TextUtils.isEmpty(url)){
					Intent intent = new Intent(mContext,MessageWebViewActivity.class);
					intent.putExtra("msgUrl",url);
					startActivity(intent);
				}
				
			}
		});		
	}
	
	/**
	 * 获取消息
	 */
	private void getMessageData(){
		http.sendGet(GETMSG,WebUrlConfig.getMessage(MyApplication.userModel.getUserID()));
	}

	@Override
	public void onClick(View v) {
		if(v == mBack){
			onBackPressed();
			finish();
		}
	}
	
}
