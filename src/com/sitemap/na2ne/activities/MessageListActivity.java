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
 *	��Ϣ�б�ҳ��
 */
public class MessageListActivity extends Activity implements OnClickListener{
	/**����*/ 
	private MessageListActivity mContext;
	/**listview*/ 
	private ListView mLv;
	/**������һ��*/ 
	private LinearLayout mBack;
	/**��Ϣ����*/ 
	private List<MessageModel> mList = null;
	/**������s*/ 
	private MessageListViewAdapter mAdapter;
	/**��ȡ��Ϣtag*/ 
	private final int GETMSG = 0x2001;
	/**������*/ 
	private static MyProgressDialog progressDialog; 
	/**����������������*/ 
	private HttpUtil http = null;//����������������

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		// ����Ӧ�ó���ı�����������ǰactivity�ı�����
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
				progressDialog.dismiss();// �رս�����
			}
			switch (msg.what) {
			case HttpUtil.SUCCESS:
				//��Ϣ
				if(msg.arg1 == GETMSG){
					mList = new ArrayList<MessageModel>();
					mList.clear();
					mList = JSON.parseArray(msg.obj.toString(), MessageModel.class);
					initListView(mList);
					mAdapter.notifyDataSetChanged();										
				}
				break;
			case HttpUtil.EMPTY:				
				 View view=EmptyView.getView(mContext,"������Ϣ");
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
	 * ��ʼ��view
	 */
	private void initView() {
		mBack = (LinearLayout) findViewById(R.id.base_back_lay);
		mLv = (ListView) findViewById(R.id.msg_listview);
	}

	/**
	 * ��ʼ������
	 */
	private void initData() {
		mBack.setOnClickListener(this);
		if (MyApplication.getNetObject().isNetConnected()) {
			if (progressDialog == null ||!progressDialog.isShowing()) {
				progressDialog=MyProgressDialog.createDialog(this);
				progressDialog.setMessage("������...");
				progressDialog.show();
			}
			getMessageData();
		} else {
			ToastUtil.showCenterShort(this, RequestCode.NONETWORK);
		}		
	}
	
	/**
	 * ��ʼ��listview
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
	 * ��ȡ��Ϣ
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
