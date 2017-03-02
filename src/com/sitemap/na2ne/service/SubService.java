package com.sitemap.na2ne.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.xutils.http.RequestParams;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.SpatialRelationUtil;
import com.sitemap.na2ne.activities.HomePageActivity;
import com.sitemap.na2ne.activities.LoginActivity;
import com.sitemap.na2ne.activities.SetActivity;
import com.sitemap.na2ne.activities.UserActivity;
import com.sitemap.na2ne.application.MyApplication;
import com.sitemap.na2ne.config.WebHostConfig;
import com.sitemap.na2ne.config.WebUrlConfig;
import com.sitemap.na2ne.http.HttpUtil;
import com.sitemap.na2ne.models.CodeModel;
import com.sitemap.na2ne.models.UploadLocationInfoModel;
import com.sitemap.na2ne.models.UserFriendModel;
import com.sitemap.na2ne.utils.ToastUtil;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.Service;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * 
 * com.sitemap.na2ne.service.SubService
 * 
 * @author zhang �����ϱ����� create at 2016��2��19�� ����2:26:08
 */
public class SubService extends Service {

	private LocationClient mLocationClient;

	public MyLocationListener myListener = new MyLocationListener();
	private double lat;// γ��
	private double lng;// ����
	private String tempcoor = "gcj02";// ���ݸ�ʽ
	private int count = 0;// ��λ����
	private int mHour;//��ǰʱ
	private int mMinute;//��ǰ��
	private int mSecond;//��ǰ��
	private int startHour;//��ʼʱ
	private int endHour;//����ʱ
	private int startMin;//��ʼ��
	private int endMin;//������
	private HttpUtil http = null;//����������������
	// ��ǰ��
		private int mYear;
		//��ǰ��
		private int mMonth;
		// ��ǰ��
		private int mDay;
	
	private String location;//��ַ
	private List<UploadLocationInfoModel> lUpLocation=new ArrayList<UploadLocationInfoModel>();//�ϱ���Ϣ����
	private List<UploadLocationInfoModel> lOldUpLocation=new ArrayList<UploadLocationInfoModel>();//�ϱ�ʧ����Ϣ����
	private SharedPreferences preferences ;//preferences
	private String lostLocation="";//δ�ϴ���λ��Ϣ
	private final int zero=0,one=1,two=2;//0/1/2
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		initLocation();
		final Calendar c = Calendar.getInstance();// ʱ��
		mHour = c.get(Calendar.HOUR_OF_DAY);
		mMinute = c.get(Calendar.MINUTE);
		mSecond=c.get(Calendar.SECOND);
		preferences = getSharedPreferences("user",
				Context.MODE_PRIVATE);
//		Timer time=new Timer();
//		time.schedule(new TimerTask() {
//			
//			@Override
//			public void run() {
//				Log.i("geek", String.valueOf(count));
//			}
//		}, 1000,30000);
	}
	
	private final  Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case HttpUtil.SUCCESS:
				if (msg.arg1==zero) {
					int  status = -1;
					CodeModel cm=JSON.parseObject(msg.obj.toString(), CodeModel.class);
					if (cm!=null) {
						status=cm.getStatus();
						switch (status) {
						case zero:
							lUpLocation.clear();
							lOldUpLocation.clear();
							break;
						case one:
							Editor editor = preferences.edit();
							editor.putString("lostLocation", JSON.toJSONString(lUpLocation));
							editor.commit();
							break;
						default:
							break;
						}
					}
				}
				break;
			case HttpUtil.EMPTY:
							
				break;
			case HttpUtil.FAILURE:
//				ToastUtil.showCenterShort(context, RequestCode.ERRORINFO);
				break;
			case HttpUtil.LOADING:
				
				break;
			default:
				break;
		}
		}

	};

	/**
	 * ��ʼ����λ
	 */
	private void initLocation() {
		LocationClientOption option = new LocationClientOption();
		option.setCoorType(tempcoor);// ���صĶ�λ����ǰٶȾ�γ�ȣ�Ĭ��ֵgcj02
		mLocationClient = new LocationClient(this);
		mLocationClient.registerLocationListener(myListener);
		option.setOpenGps(true);// ��gps
		option.setAddrType("all");// ���صĶ�λ���������ַ��Ϣ
		option.setScanSpan(1000*60*5);
		option.setLocationMode(LocationMode.Hight_Accuracy);// ���ö�λģʽ
		mLocationClient.setLocOption(option);
		mLocationClient.start();
	}

	/**
	 * 
	 * com.sitemap.na2ne.service.MyLocationListener
	 * 
	 * @author zhang ��λ���� create at 2016��2��19�� ����2:26:45
	 */
	public class MyLocationListener implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (MyApplication.isLogin == 2) {
				final Calendar c = Calendar.getInstance();// ʱ��
				mHour = c.get(Calendar.HOUR_OF_DAY);
				mMinute = c.get(Calendar.MINUTE);
				mYear = c.get(Calendar.YEAR);
				mMonth = c.get(Calendar.MONTH);
				mDay = c.get(Calendar.DAY_OF_MONTH);
				mSecond=c.get(Calendar.SECOND);
				startHour=Integer.parseInt(MyApplication.locationSet.getLocationStartTime().substring(0, 2));
				startMin=Integer.parseInt(MyApplication.locationSet.getLocationStartTime().substring(3));
				endHour=Integer.parseInt(MyApplication.locationSet.getLocationEndTime().substring(0, 2));
				endMin=Integer.parseInt(MyApplication.locationSet.getLocationEndTime().substring(3));
//				System.out.println("startHour:"+startHour+",startMin:"+startMin+",endHour:"+endHour+",endMin:"+endMin);
				if (isInTime()) {
					count++;
					UploadLocationInfoModel upLocation=new UploadLocationInfoModel();
					upLocation.setLat(String.valueOf(location.getLatitude()));
					upLocation.setLng(String.valueOf(location.getLongitude()));
					upLocation.setLocation(location.getAddrStr());
					
					StringBuilder time=new StringBuilder().append(mYear).append("-")
							.append((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1))
							.append("-").append((mDay < 10) ? "0" + mDay : mDay);
					if (mHour < 10) {
						time.append(" 0" + mHour).append(":")
								.append((mMinute < 10) ? "0" + mMinute : mMinute).append(":"+mSecond);
					} else {
						time.append(" "+mHour).append(":")
								.append((mMinute < 10) ? "0" + mMinute : mMinute).append(":"+mSecond);
					}
					
					upLocation.setTime(time.toString());
					
					if (location.getLocType() == BDLocation.TypeGpsLocation) {
						upLocation.setType("0");
					}else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
						upLocation.setType("2");
					}
				lUpLocation.add(upLocation);
				System.out.println(JSON.toJSONString(lUpLocation));
				if (count%5==0) {
					lostLocation=preferences.getString("lostLocation", "");
					if (!lostLocation.equals("")&&lostLocation!=null) {
						lOldUpLocation=JSONObject.parseArray(
								lostLocation, UploadLocationInfoModel.class);
						for (int i = 0; i < lOldUpLocation.size(); i++) {
							lUpLocation.add(lOldUpLocation.get(i));
						}
					
					}
					if(http == null){
						http = new HttpUtil(handler);
					}
//					System.out.println(JSON.toJSONString(lUpLocation));
					RequestParams params = http.getParams(WebUrlConfig.uploadLocation("", ""));
					params.addBodyParameter("userID", MyApplication.userModel.getUserID());
					params.addBodyParameter("locationInfo",JSON.toJSONString(lUpLocation));
					params.addBodyParameter("phoneNumber",MyApplication.userModel.getPhoneNumber());
					http.sendPost(zero,params);
				}
				}
			}
			// �����Զ���ͼ��
			lat = location.getLatitude();
			lng = location.getLongitude();
			MyApplication.lat = location.getLatitude();
			MyApplication.lng = location.getLongitude();
			MyApplication.city = location.getCity();
			System.out.println("jinrulis");
			// System.out.println(lat+"");
			// System.out.println(lng+"");
			StringBuffer sb = new StringBuffer(256);
			sb.append("time : ");
			sb.append(location.getTime());
			sb.append("\nerror code : ");
			sb.append(location.getLocType());
			sb.append("\nlatitude : ");
			sb.append(location.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(location.getLongitude());
			sb.append("\nradius : ");
			sb.append(location.getRadius());
			if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS��λ���
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());// ��λ������ÿСʱ
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
				sb.append("\nheight : ");
				sb.append(location.getAltitude());// ��λ����
				sb.append("\ndirection : ");
				sb.append(location.getDirection());// ��λ��
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				sb.append("\ndescribe : ");
				sb.append("gps��λ�ɹ�");

			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// ���綨λ���
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				// ��Ӫ����Ϣ
				sb.append("\noperationers : ");
				sb.append(location.getOperators());
				sb.append("\ndescribe : ");
				sb.append("���綨λ�ɹ�");
			}
			System.out.println(sb.toString());
			// MyApplication.lat=lat;
			// MyApplication.lng=lng;
			// MyApplication.mp.setLat(lat + "");
			// MyApplication.mp.setLng(lng + "");
			// LatLng ll = new LatLng(lat, lng);
		}

	}

	// public boolean isinarea(){
	// LatLng pt = new LatLng(MyApplication.lat, MyApplication.lng);
	// System.out.println(MyApplication.lat+"");
	// System.out.println(MyApplication.lng+"");
	// boolean aaa=SpatialRelationUtil.isPolygonContainsPoint(MyApplication.pts,
	// pt);
	// System.out.println(aaa);
	// return aaa;
	// }
	
	/**
	 * �жϵ�ǰʱ���Ƿ����ϱ�ʱ����
	 * @return
	 */
	public boolean isInTime(){
		if (mHour>startHour&&mHour<endHour) {
			return true;
		}
		if (mHour==startHour&&mMinute>startMin) {
			return true;
		}
		if (mHour==endHour&&mMinute<endMin) {
			return true;
		}
		return false;
	}

	@Override
	public void onDestroy() {
		mLocationClient.stop();

		super.onDestroy();
	}
}