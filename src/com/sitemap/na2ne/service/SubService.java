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
 * @author zhang 数据上报服务 create at 2016年2月19日 下午2:26:08
 */
public class SubService extends Service {

	private LocationClient mLocationClient;

	public MyLocationListener myListener = new MyLocationListener();
	private double lat;// 纬度
	private double lng;// 经度
	private String tempcoor = "gcj02";// 数据格式
	private int count = 0;// 定位次数
	private int mHour;//当前时
	private int mMinute;//当前分
	private int mSecond;//当前秒
	private int startHour;//开始时
	private int endHour;//结束时
	private int startMin;//开始分
	private int endMin;//结束分
	private HttpUtil http = null;//网络请求帮助类对象
	// 当前年
		private int mYear;
		//当前月
		private int mMonth;
		// 当前日
		private int mDay;
	
	private String location;//地址
	private List<UploadLocationInfoModel> lUpLocation=new ArrayList<UploadLocationInfoModel>();//上报信息集合
	private List<UploadLocationInfoModel> lOldUpLocation=new ArrayList<UploadLocationInfoModel>();//上报失败信息集合
	private SharedPreferences preferences ;//preferences
	private String lostLocation="";//未上传定位信息
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
		final Calendar c = Calendar.getInstance();// 时间
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
	 * 初始化定位
	 */
	private void initLocation() {
		LocationClientOption option = new LocationClientOption();
		option.setCoorType(tempcoor);// 返回的定位结果是百度经纬度，默认值gcj02
		mLocationClient = new LocationClient(this);
		mLocationClient.registerLocationListener(myListener);
		option.setOpenGps(true);// 打开gps
		option.setAddrType("all");// 返回的定位结果包含地址信息
		option.setScanSpan(1000*60*5);
		option.setLocationMode(LocationMode.Hight_Accuracy);// 设置定位模式
		mLocationClient.setLocOption(option);
		mLocationClient.start();
	}

	/**
	 * 
	 * com.sitemap.na2ne.service.MyLocationListener
	 * 
	 * @author zhang 定位监听 create at 2016年2月19日 下午2:26:45
	 */
	public class MyLocationListener implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (MyApplication.isLogin == 2) {
				final Calendar c = Calendar.getInstance();// 时间
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
			// 设置自定义图标
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
			if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());// 单位：公里每小时
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
				sb.append("\nheight : ");
				sb.append(location.getAltitude());// 单位：米
				sb.append("\ndirection : ");
				sb.append(location.getDirection());// 单位度
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				sb.append("\ndescribe : ");
				sb.append("gps定位成功");

			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				// 运营商信息
				sb.append("\noperationers : ");
				sb.append(location.getOperators());
				sb.append("\ndescribe : ");
				sb.append("网络定位成功");
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
	 * 判断当前时间是否在上报时间内
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
