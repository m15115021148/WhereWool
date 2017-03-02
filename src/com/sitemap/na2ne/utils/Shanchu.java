/**
 * 
 */
package com.sitemap.na2ne.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.xutils.http.RequestParams;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import cn.jpush.android.api.TagAliasCallback;

import com.alibaba.fastjson.JSON;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.SpatialRelationUtil;
import com.saitu.na2ne.R;
import com.sitemap.na2ne.activities.HomePageActivity;
import com.sitemap.na2ne.activities.LoginActivity;
import com.sitemap.na2ne.application.MyApplication;
import com.sitemap.na2ne.config.WebUrlConfig;
import com.sitemap.na2ne.models.CodeModel;
import com.sitemap.na2ne.models.FenceInfoModel;
import com.sitemap.na2ne.models.LocationInfoModel;
import com.sitemap.na2ne.models.MyPointModel;
import com.sitemap.na2ne.views.MyProgressDialog;

/**
 * com.sitemap.na2ne.utils.Shanchu
 * @author zhang
 * @category 
 * create at 2017��1��18�� ����3:55:54
 */
public class Shanchu {
	/**
	 * 
	 * com.sitemap.na2ne.activities.MessageReceiver
	 * 
	 * @author zhang ������Ϣ������ create at 2016��3��10�� ����5:07:17
	 */
	// public class MessageReceiver extends BroadcastReceiver {
	//
	// @Override
	// public void onReceive(Context context, Intent intent) {
	// if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
	// String messge = intent.getStringExtra("jpushTitle");
	// String extras = intent.getStringExtra("jpushMsg");
	// // if (extras!=null&&!extras.equals("")) {
	// // ToastUtil.showCenterShort(context, extras);
	// // }
	// if (messge!=null&&!messge.equals("")) {
	//
	// // Log.i("TAG",extras);
	// //����ص���Χ������
	// if (messge.equals("setFenceStart")) {
	// JPushInfo=extras;
	// handler.sendEmptyMessage(FOURTEEN);
	// }
	// //����ص���Χ���ر�
	// if (messge.equals("setFenceEnd")) {
	// // Log.i("TAG","�ر���");
	// // JPushInfo=extras;
	// handler.sendEmptyMessage(EIGHTEEN);
	// }
	// //���յ���������
	// if (messge.equals("sendWarn")) {
	// // Log.i("TAG","�ر���");
	// // JPushInfo=extras;
	// baojingMessage=extras;
	// handler.sendEmptyMessage(SEVENTEEN);
	// }
	// }
	// }
	// }
	// }
	
	
	// MyApplication.intent =new Intent();
			// MyApplication.intent.setAction("com.sitemap.na2ne.service.subService");
			// MyApplication.intent.setPackage(getPackageName());//��������Ҫ������Ӧ�õİ���
			// startService(MyApplication.intent);
	
	// jPushReceiver=new MessageReceiver();
			// IntentFilter filter = new IntentFilter();
			// filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
			// filter.addAction(MESSAGE_RECEIVED_ACTION);
			// registerReceiver(jPushReceiver, filter);
	
	// unregisterReceiver(jPushReceiver);
	
	// weilan = (RadioButton) findViewById(R.id.radiobutton_index_weilan);
			// weilan.setOnClickListener(this);
	
//	MyApplication.locationSet.setLocationStartTime("08:00");
//	MyApplication.locationSet.setLocationEndTime("22:30");
//	MyApplication.locationSet.setLocationSpace("300*1000");
//	MyApplication.locationSet.setUploadSpace("1800*1000");
	
	
	// JPushInterface.setAliasAndTags(getApplicationContext(),MyApplication.userModel.getUserID(),null, mAliasCallback);
	// JPushInterface.setAlias(getApplicationContext(), MyApplication.userModel.getUserID(), mAliasCallback);
	// ToastUtil.showCenterShort(context, "��¼�ɹ���");
	// if (MyApplication.getNetObject().isNetConnected()) {
	// httpUtil = new XUtilsHelper(WebUrlConfig.systemConfig(), handler);
	// httpUtil.sendGet(locationset);
	// } else {}
	
	
	
//	// ��APP��������Χ��
//	if (msg.arg1 == TWELVE) {
//		int status = -1;
//		CodeModel cm = JSON.parseObject(msg.obj.toString(),CodeModel.class);
//		if (cm != null) {
//			status = cm.getStatus();
//			switch (status) {
//			case ZERO:
//				ToastUtil.showCenterShort(context,"��������Χ���ɹ���");
//				index_dzwl_draw.setImageResource(R.drawable.index_dzwl_end);
//				flag = "4";
//				isWeilan = true;
//				handler.sendEmptyMessage(TWELVE);
//				break;
//			case one:
//				ToastUtil.showCenterShort(context,"�ύ����ʧ�ܣ�ʧ��ԭ��" + cm.getErrorMessage());
//				break;
//			default:
//				break;
//			}
//		}
//	}
	
	
	
	
//	// ��APP����Χ����λ�Ա�
//	if (msg.arg1 == THIRTEEN) {
//		locationInfo = JSON.parseObject(msg.obj.toString(),
//				LocationInfoModel.class);
//		addArea(area);
//
//		if (locationInfo.getLatitude() != null
//				&& !locationInfo.getLatitude().equals("")
//				&& locationInfo.getLongitude() != null
//				&& !locationInfo.getLongitude().equals("")) {
//			boolean isInArea = isInArea(
//					Double.parseDouble(locationInfo.getLatitude()),
//					Double.parseDouble(locationInfo.getLongitude()));
//			switch (weilanSelect) {
//			// �뿪���
//			case ZERO:
//				// ��������
//				if (!isInArea) {
//					// ����������������������������������
//					VibratorUtil.Vibrate(context, 2000);
//					AlertDialog.Builder normalDia = new AlertDialog.Builder(
//							HomePageActivity.this);
//					normalDia.setTitle("��ʾ");
//
//					normalDia.setMessage((mHour1 < 10 ? "0"
//							+ mHour1 : mHour1)
//							+ ":"
//							+ ((mMinute1 < 10) ? "0" + mMinute1
//									: mMinute1) + "�����Ѿ��뿪Χ������");
//
//					normalDia.setPositiveButton("ȷ��",
//							new DialogInterface.OnClickListener() {
//								@Override
//								public void onClick(
//										DialogInterface dialog,
//										int which) {
//
//								}
//							});
//					normalDia.create().show();
//					System.out.println("���������˰�����������");
//				}
//				break;
//			// ������
//			case one:
//				// ������
//				if (isInArea) {
//					// ��������������������������������������
//					VibratorUtil.Vibrate(context, 2000);
//					AlertDialog.Builder normalDia = new AlertDialog.Builder(
//							HomePageActivity.this);
//					normalDia.setTitle("��ʾ");
//
//					normalDia.setMessage((mHour1 < 10 ? "0"
//							+ mHour1 : mHour1)
//							+ ":"
//							+ ((mMinute1 < 10) ? "0" + mMinute1
//									: mMinute1) + "�����Ѿ�����Χ������");
//
//					normalDia.setPositiveButton("ȷ��",
//							new DialogInterface.OnClickListener() {
//								@Override
//								public void onClick(
//										DialogInterface dialog,
//										int which) {
//
//								}
//							});
//					normalDia.create().show();
//					System.out.println("���������˰�����������");
//				}
//				break;
//			default:
//				break;
//			}
//		}
//	}
//	// ��APP�رյ���Χ��
//	else if (msg.arg1 == FOURTEEN) {
//		int status = -1;
//		CodeModel cm = JSON.parseObject(msg.obj.toString(),
//				CodeModel.class);
//		if (cm != null) {
//			status = cm.getStatus();
//			switch (status) {
//			case ZERO:
//				ToastUtil.showCenterShort(context,
//						"�رյ���Χ���ɹ���");
//				mBaiduMap.clear();
//				mapClear.setVisibility(View.GONE);
//				mapStop.setVisibility(View.GONE);
//				if (timer5 != null) {
//					timer5.cancel();
//
//				}
//				iszhuizong = false;
//				isWeilan = false;
//				// �ر�Χ������
//				closeWeilanLay();
//				break;
//			case one:
//				ToastUtil.showCenterShort(context,
//						"�رյ���Χ��ʧ�ܣ�ʧ��ԭ��" + cm.getErrorMessage());
//
//				break;
//
//			default:
//
//				break;
//			}
//		}
//	}
//	// �����ʷΧ��
//	else if (msg.arg1 == FIFTEEN) {
//		// System.out.println(msg.obj.toString());
//		fenceInfo = new FenceInfoModel();
//		fenceInfo = JSON.parseObject(msg.obj.toString(),
//				FenceInfoModel.class);
//		if (fenceInfo.getLocationInfo() != null
//				&& !fenceInfo.getLocationInfo().equals("")) {
//			StringBuilder etime = new StringBuilder("");
//			if (isLargeTime(fenceInfo.getStartTime())) {
//
//				startTime.setText(fenceInfo.getStartTime());
//			} else {
//				if (mHour1 < 10) {
//					startTime.setText(etime
//							.append("0" + mHour1)
//							.append(":")
//							.append((mMinute1 < 10) ? "0"
//									+ mMinute1 : mMinute1));
//				} else {
//					startTime.setText(etime
//							.append(mHour1)
//							.append(":")
//							.append((mMinute1 < 10) ? "0"
//									+ mMinute1 : mMinute1));
//				}
//			}
//
//			endTime.setText(fenceInfo.getEndTime());
//			weilanSelect = Integer.parseInt(fenceInfo
//					.getMonitoringType());
//			weilanSp.setText(weilanType[weilanSelect]);
//			mMapView.getMap().clear();
//			listPoint.clear();
//			String[] areaList = fenceInfo.getLocationInfo().split(
//					";");
//			for (int i = 0; i < areaList.length; i++) {
//				listPoint.add(new MyPointModel(areaList[i]
//						.split(",")[0], areaList[i].split(",")[1]));
//			}
//
//			if (listPoint.size() >= THREE) {
//				draw(listPoint);
//			}
//			index_dzwl_old.setClickable(false);
//			index_dzwl_old
//					.setImageResource(R.drawable.index_dzwl_old_hui);
//		}
//	}
//	// ����û���������Χ��
//	if (msg.arg1 == SIXTEEN) {
//		// System.out.println(msg.obj.toString());
//		int status = -1;
//		CodeModel cm = JSON.parseObject(msg.obj.toString(),
//				CodeModel.class);
//		if (cm != null) {
//			status = cm.getStatus();
//			switch (status) {
//			case ZERO:
//				ToastUtil.showCenterShort(context,
//						"��������Χ���ɹ���");
//				index_dzwl_draw
//						.setImageResource(R.drawable.index_dzwl_end);
//				flag = "4";
//				isWeilan = true;
//				handler.sendEmptyMessage(FIFTEEN);
//				break;
//			case one:
//				ToastUtil.showCenterShort(context,
//						"�ύ����ʧ�ܣ�ʧ��ԭ��" + cm.getErrorMessage());
//
//				break;
//
//			default:
//
//				break;
//			}
//		}
//	}
//
//	// ��������Χ������
//	if (msg.arg1 == SEVENTEEN) {
//		System.out.println(msg.obj.toString());
//		int status = -1;
//		CodeModel cm = JSON.parseObject(msg.obj.toString(),
//				CodeModel.class);
//		if (cm != null) {
//			status = cm.getStatus();
//			switch (status) {
//			case ZERO:
//				// ToastUtil.showCenterShort(context,
//				// "�����ˣ���");
//				// index_dzwl_draw
//				// .setImageResource(R.drawable.index_dzwl_end);
//				// flag = "4";
//				// isWeilan=true;
//				// handler.sendEmptyMessage(SIXTEEN);
//				break;
//			case one:
//				// ToastUtil.showCenterShort(context,
//				// "�ύ����ʧ�ܣ�ʧ��ԭ��" + cm.getErrorMessage());
//
//				break;
//			default:
//				break;
//			}
//		}
//	}
//
//	// ���͹رյ���Χ������
//	if (msg.arg1 == EIGHTEEN) {
//		System.out.println(msg.obj.toString());
//		int status = -1;
//		CodeModel cm = JSON.parseObject(msg.obj.toString(),
//				CodeModel.class);
//		if (cm != null) {
//			status = cm.getStatus();
//			switch (status) {
//			case ZERO:
//				ToastUtil.showCenterShort(context,
//						"�رյ���Χ���ɹ���");
//				mBaiduMap.clear();
//				mapClear.setVisibility(View.GONE);
//				mapStop.setVisibility(View.GONE);
//
//				if (timer5 != null) {
//					timer5.cancel();
//
//				}
//				index_dzwl_old.setClickable(true);
//				index_dzwl_old
//						.setImageResource(R.drawable.index_dzwl_old);
//				iszhuizong = false;
//				isWeilan = false;
//				listPoint.clear();
//				// �ر�Χ������
//				closeWeilanLay();
//				break;
//			case one:
//				ToastUtil.showCenterShort(context,
//						"�ύ����ʧ�ܣ�ʧ��ԭ��" + cm.getErrorMessage());
//
//				break;
//			default:
//				break;
//			}
//		}
//	}
	
	
	
	
//case TWELVE:// ��APP����Χ����ʱ
//	timer5 = new Timer();
//	timer5.schedule(new TimerTask() {
//		@Override
//		public void run() {
//			if (weilanCount >= weilanAllCount) {
//				Log.i("TAG", "������APP��ʱ");
//				weilanCount = ZERO;
//				weilanAllCount = ZERO;
//				isWeilan = false;
//				handler.sendEmptyMessage(THIRTEEN);
//				index_dzwl_draw
//						.setImageResource(R.drawable.index_dzwl_draw);
//				flag = "1";
//				timer5.cancel();
//				return;
//			} else {
//				// Χ���ж�
//				Log.i("TAG", "��APP�ж�");
//				weilanCount++;
//				String url = WebUrlConfig
//						.getFriendPosition(
//								userFriend.getFriendID(),
//								userFriend.getFriendPhone(),
//								userFriend.getNumberType(),
//								userFriend.getPackageID());
//				http.sendGet(THIRTEEN,url);
//			}
//		}
//
//	}, TEN, weilanJiange);
//	break;
//case THIRTEEN:// �ر�Χ����ʾ
//	ToastUtil.showCenterShort(context, "����Χ����ɣ�");
//	mapStop.setVisibility(View.GONE);
//	break;
//case MSG_SET_ALIAS:// ���ü������ͱ���
//	// JPushInterface.setAliasAndTags(getApplicationContext(),MyApplication.userModel.getUserID(),
//	// null, mAliasCallback);
//	// JPushInterface.setAlias(getApplicationContext(),
//	// MyApplication.userModel.getUserID(), mAliasCallback);
//	break;
//case FOURTEEN:// ����û����յ�����Χ�����
//	try {
//		org.json.JSONObject jsonObject = new org.json.JSONObject(
//				JPushInfo);
//		String fence = jsonObject.getString("fenceInfo");
//		senderUserID = jsonObject.getString("senderUserID");
//		fenceInfo = JSON.parseObject(fence, FenceInfoModel.class);
//
//		beiweilanSelect = Integer.parseInt(fenceInfo
//				.getMonitoringType());
//
//		beiweilanStartHour = Integer.parseInt(fenceInfo
//				.getStartTime().substring(0, 2));
//		beiweilanStartMin = Integer.parseInt(fenceInfo
//				.getStartTime().substring(3));
//		beiweilanEndHou = Integer.parseInt(fenceInfo.getEndTime()
//				.substring(0, 2));
//		beiweilanEndMin = Integer.parseInt(fenceInfo.getEndTime()
//				.substring(3));
//		if (beiweilanEndHou < beiweilanStartHour) {
//			// ToastUtil.showCenterShort(context,
//			// "����ʱ�������ڿ�ʼʱ�䣡");
//			// return;
//		} else {
//
//			if (beiweilanEndMin < beiweilanStartMin) {
//				beiweilanTime = (beiweilanEndHou
//						- beiweilanStartHour - 1)
//						* 60
//						+ (60 - (beiweilanStartMin - beiweilanEndMin));
//			}
//			if (beiweilanStartMin <= beiweilanEndMin) {
//				beiweilanTime = (beiweilanEndHou - beiweilanStartHour)
//						* 60
//						+ (beiweilanEndMin - beiweilanStartMin);
//			}
//
//		}
//		if (beiweilanEndHou == beiweilanStartHour) {
//			if (beiweilanEndMin <= beiweilanStartMin) {
//				// ToastUtil.showCenterShort(context,
//				// "����ʱ�������ڿ�ʼʱ�䣡");
//				// return;
//			} else {
//				beiweilanTime = beiweilanEndMin - beiweilanStartMin;
//			}
//		}
//		if (beiweilanTime < FIVE) {
//			// ToastUtil.showCenterShort(context,
//			// "Χ��ʱ��������5���ӣ�");
//			return;
//		}
//		// System.out.println("weilanTime:" + weilanTime);
//		beiweilanAllCount = beiweilanTime / 5;
//
//		timer6 = new Timer();
//		timer6.schedule(new TimerTask() {
//
//			@Override
//			public void run() {
//				if (beiweilanCount >= beiweilanAllCount) {
//					beiweilanCount = ZERO;
//					beiweilanAllCount = ZERO;
//					timer6.cancel();
//					return;
//				} else {
//					// Χ���ж�
//					beiweilanCount++;
//					handler.sendEmptyMessage(SIXTEEN);
//
//				}
//			}
//
//		}, TEN, 30 * 1000);
//
//		// Log.i("TAG",fenceInfo);
//
//	} catch (JSONException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//	break;
//case FIFTEEN:// �������û���Χ����ʱ
//	timer5 = new Timer();
//	timer5.schedule(new TimerTask() {
//
//		@Override
//		public void run() {
//			if (weilanCount >= weilanAllCount) {
//				weilanCount = ZERO;
//				weilanAllCount = ZERO;
//				isWeilan = false;
//				handler.sendEmptyMessage(THIRTEEN);
//				index_dzwl_draw
//						.setImageResource(R.drawable.index_dzwl_draw);
//				flag = "1";
//				timer5.cancel();
//				return;
//			} else {
//				// Χ���ж�
//				weilanCount++;
//			}
//		}
//
//	}, TEN, weilanJiange);
//	break;
//case SIXTEEN:// ����û�Χ���ж�
//	area = fenceInfo.getLocationInfo();
//	addArea(area);
//	mLocationClient.requestLocation();
//	Log.i("TAG", lat + "");
//	if (lat != 0) {
//		LatLng zuobiao = changebaidu(new LatLng(lat, lng));
//		boolean isInArea = isInArea(zuobiao.latitude,
//				zuobiao.longitude);
//		switch (weilanSelect) {
//		// �뿪���
//		case ZERO:
//			// ��������
//			if (!isInArea) {
//				// ����������������������������������
//				System.out.println("���������˰�����������");
//			} else {
//				System.out.println("��������");
//			}
//			break;
//		// ������
//		case one:
//			// ������
//			if (isInArea) {
//				// ��������������������������������������
//				System.out.println("���������˰�����������");
//			} else {
//				System.out.println("����û��ȥ");
//			}
//			break;
//		default:
//			break;
//		}
//		final Calendar c = Calendar.getInstance();
//		mHour1 = c.get(Calendar.HOUR_OF_DAY);
//		mMinute1 = c.get(Calendar.MINUTE);
//		StringBuilder etime = new StringBuilder("");
//		if (mHour1 < 10) {
//			startTime.setText(etime
//					.append("0" + mHour1)
//					.append(":")
//					.append((mMinute1 < 10) ? "0" + mMinute1
//							: mMinute1));
//		} else {
//			startTime.setText(etime
//					.append(mHour1)
//					.append(":")
//					.append((mMinute1 < 10) ? "0" + mMinute1
//							: mMinute1));
//		}
//		String url = WebUrlConfig.sendWarn(
//				senderUserID, etime.toString());
//		http.sendGet(SEVENTEEN,url);
//	}
//	break;
//case SEVENTEEN:// ���յ�����
//	try {
//		org.json.JSONObject jsonObject = new org.json.JSONObject(
//				baojingMessage);
//		String warnTime = jsonObject.getString("warnTime");
//		VibratorUtil.Vibrate(context, 2000);
//		AlertDialog.Builder normalDia = new AlertDialog.Builder(
//				HomePageActivity.this);
//		normalDia.setTitle("��ʾ");
//
//		normalDia.setMessage(warnTime + "���Ѵ�������Χ����");
//
//		normalDia.setPositiveButton("ȷ��",
//				new DialogInterface.OnClickListener() {
//					@Override
//					public void onClick(DialogInterface dialog,
//							int which) {
//
//					}
//				});
//		normalDia.create().show();
//
//	} catch (JSONException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//	break;
//case EIGHTEEN:// ���յ��ر�Χ��������
//	if (timer6 != null) {
//		beiweilanCount = ZERO;
//		beiweilanAllCount = ZERO;
//		timer6.cancel();
//	}
//	break;
	
	
//	if (index_weilan_layout.getVisibility() == View.VISIBLE) {
//		// �ر�Χ������
//		closeWeilanLay();
//		return;
//	}
	
	
	
	// ����Χ��
//	if (v == weilan) {
//		if (MyApplication.isLogin < 0) {
//			ToastUtil.showCenterShort(context, "���ȵ�¼��");
//			Intent UserActivityIntent = new Intent(this,
//					LoginActivity.class);
//			startActivity(UserActivityIntent);
//			finish();
//		} else {
//			if (index_weilan_layout.getVisibility() == View.VISIBLE) {
//				// �ر�Χ������
//				closeWeilanLay();
//				return;
//			}
//			if (!ischeck) {
//				ToastUtil.showCenterShort(context,
//						"�������Ϸ����б�ѡ�����ӱ���λ���룡");
//				return;
//			}
//			if (iszhuizong) {
//				ToastUtil.showCenterShort(context,
//						"����׷�٣���Ҫ���õ���Χ������ֹͣ׷�٣�");
//				return;
//			}
//			index_weilan_layout.setVisibility(View.VISIBLE);
//
//			updateTimeDisplay(1);
//			updateTimeDisplay1(1);
//			if (isWeilan) {
//				index_dzwl_old.setClickable(false);
//				index_dzwl_old
//						.setImageResource(R.drawable.index_dzwl_old_hui);
//				startTime.setText(fenceInfo.getStartTime());
//				endTime.setText(fenceInfo.getEndTime());
//				weilanSelect = Integer.parseInt(fenceInfo
//						.getMonitoringType());
//				weilanSp.setText(weilanType[weilanSelect]);
//				mMapView.getMap().clear();
//				if (listPoint.size() >= THREE) {
//					draw(listPoint);
//				}
//			}
//			index_dzwl_old.setClickable(true);
//			index_dzwl_old.setImageResource(R.drawable.index_dzwl_old);
//		}
//	}

	
	
	
	
//	if (v == index_dzwl_draw) {
//		// ��Ȧ
//		if ("1".equals(flag)) {
//			tishi.setVisibility(View.VISIBLE);
//			tishi.setText("������ָ����Ļ�ϻ���ָ����������");
//			submit.setText("����");
//			fugai.setVisibility(View.VISIBLE);
//			flag = "2";
//			bottom.setVisibility(View.VISIBLE);
//			index_dzwl_old.setClickable(false);
//			index_dzwl_old.setImageResource(R.drawable.index_dzwl_old_hui);
//			index_weilan_layout.setVisibility(View.GONE);
//			return;
//		}
//		// ��ʼ��ذ�ť
//		else if ("3".equals(flag)) {
//			weilanStartHour = Integer.parseInt(startTime.getText()
//					.toString().substring(0, 2));
//			weilanStartMin = Integer.parseInt(startTime.getText()
//					.toString().substring(3));
//			weilanEndHou = Integer.parseInt(endTime.getText().toString()
//					.substring(0, 2));
//			weilanEndMin = Integer.parseInt(endTime.getText().toString()
//					.substring(3));
//			if (weilanEndHou < weilanStartHour) {
//				ToastUtil.showCenterShort(context, "����ʱ�������ڿ�ʼʱ�䣡");
//				return;
//			} else {
//
//				if (weilanEndMin < weilanStartMin) {
//					weilanTime = (weilanEndHou - weilanStartHour - 1) * 60
//							+ (60 - (weilanStartMin - weilanEndMin));
//				}
//				if (weilanStartMin <= weilanEndMin) {
//					weilanTime = (weilanEndHou - weilanStartHour) * 60
//							+ (weilanEndMin - weilanStartMin);
//				}
//
//			}
//			if (weilanEndHou == weilanStartHour) {
//				if (weilanEndMin <= weilanStartMin) {
//					ToastUtil.showCenterShort(context,
//							"����ʱ�������ڿ�ʼʱ�䣡");
//					return;
//				} else {
//					weilanTime = weilanEndMin - weilanStartMin;
//				}
//			}
//			if (weilanTime < FIVE) {
//				ToastUtil.showCenterShort(context, "Χ��ʱ��������5���ӣ�");
//				return;
//			}
//			// System.out.println("weilanTime:" + weilanTime);
//			weilanAllCount = weilanTime / 5;
//			if (!userFriend.getNumberType().equals("1")) {
//
//				weilanDialog("�ú���Ϊ�������Ʒ��û�������Χ�����ܲ����߶����(5���ӽ���һ�ζ�λ�ж�)����ѡʱ���ط��ô��Ϊ��"
//						+ weilanAllCount
//						+ "Ԫ(Χ�������ڼ��벻Ҫ�ر�APP��APP�رպ�Χ�����������У�)");
//
//			} else {
//				weilanDialog("�Ƿ�������Χ��?(Χ�������ڼ��벻Ҫ�ر�APP��APP�رպ�Χ�����������У�)");
//			}
//		}
//		// �رյ���Χ��
//		else if ("4".equals(flag)) {
//			AlertDialog.Builder normalDia = new AlertDialog.Builder(
//					HomePageActivity.this);
//			normalDia.setTitle("��ʾ");
//
//			normalDia.setMessage("�Ƿ�رյ���Χ����");
//
//			normalDia.setPositiveButton("ȷ��",
//					new DialogInterface.OnClickListener() {
//						@Override
//						public void onClick(DialogInterface dialog,
//								int which) {
//							// if
//							// (MyApplication.getNetObject().isNetConnected())
//							// {
//							//
//							// httpUtil = new
//							// XUtilsHelper(WebUrlConfig.setFenceEnd(userFriend.getFriendID()),
//							// handler);
//							//
//							// httpUtil.sendGet(FOURTEEN);;
//							// } else {
//							// handler.sendEmptyMessage(11);
//							// }
//							if (userFriend.getPackageType().equals("4")) {
//								http.sendGet(EIGHTEEN,WebUrlConfig
//										.setFenceEnd(userFriend
//												.getFriendPhone()));
//							} else {
//								ToastUtil.showCenterShort(context,
//										"�رյ���Χ���ɹ���");
//								mBaiduMap.clear();
//								mapClear.setVisibility(View.GONE);
//								mapStop.setVisibility(View.GONE);
//
//								if (timer5 != null) {
//									timer5.cancel();
//
//								}
//								index_dzwl_old.setClickable(true);
//								index_dzwl_old
//										.setImageResource(R.drawable.index_dzwl_old);
//								iszhuizong = false;
//								isWeilan = false;
//								listPoint.clear();
//								// �ر�Χ������
//								closeWeilanLay();
//							}
//
//						}
//					});
//			normalDia.setNegativeButton("ȡ��",
//					new DialogInterface.OnClickListener() {
//						@Override
//						public void onClick(DialogInterface dialog,
//								int which) {
//							dialog.dismiss();
//						}
//					});
//			normalDia.create().show();
//		}
//	}
//	// Χ�������ı������¼�
//	if (v == weilanSp) {
//		View outerView = LayoutInflater.from(context).inflate(
//				R.layout.wheel_view, null);
//		WheelView wv = (WheelView) outerView
//				.findViewById(R.id.wheel_view_wv);
//		wv.setOffset(2);
//		wv.setItems(Arrays.asList(weilanType));
//		wv.setSeletion(weilanSelect);
//		wv.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
//			@Override
//			public void onSelected(int selectedIndex, String item) {
//				// Log.d(TAG, "[Dialog]selectedIndex: " +
//				// selectedIndex + ", item: " + item);
//				weilanSelect = selectedIndex - 2;
//				weilanSp.setText(weilanType[weilanSelect]);
//
//			}
//		});
//		new AlertDialog.Builder(context).setTitle("��������")
//				.setView(outerView).setPositiveButton("ȷ��", null).show();
//	}
//	// ����Χ�������ť
//	if (v == clean) {
//		mMapView.getMap().clear();
//		listPoint.clear();
//		tishi.setVisibility(View.GONE);
//		fugai.setVisibility(View.GONE);
//		bottom.setVisibility(View.GONE);
//		flag = "1";
//		index_weilan_layout.setVisibility(View.VISIBLE);
//	}
//	// ����Χ��ȷ����ť
//	if (v == submit) {
//		if (flag.equals("2")) {
//			if (listPoint.size() == 0) {
//				ToastUtil.showCenterShort(context, "�����Χ������");
//				return;
//			}
//			String str = "";
//			for (int i = 0; i < listPoint.size(); i++) {
//				str = listPoint.get(i).getLat() + ","
//						+ listPoint.get(i).getLng() + ";" + str;
//			}
//			if (str.length() >= 4500) {
//				ToastUtil.showCenterShort(context,
//						"Χ������̫�����ӣ������»��ƣ�");
//				return;
//			}
//			index_weilan_layout.setVisibility(View.VISIBLE);
//			index_dzwl_draw.setImageResource(R.drawable.index_dzwl_start);
//			tishi.setVisibility(View.GONE);
//			fugai.setVisibility(View.GONE);
//			bottom.setVisibility(View.GONE);
//			flag = "3";
//			return;
//		}
//	}
//	// ��ʷΧ����Ϣ��ť
//	if (v == index_dzwl_old) {
//		if (MyApplication.getNetObject().isNetConnected()) {
//			if (progressDialog != null && !progressDialog.isShowing()) {
//				progressDialog = MyProgressDialog.createDialog(context);
//				progressDialog.setMessage("����ϴ�Χ����Ϣ��...");
//				progressDialog.show();
//			}
//
//			http.sendGet(FIFTEEN,WebUrlConfig.getFence(userFriend
//					.getFriendID()));
//		} else {
//			ToastUtil.showCenterShort(this, "�����޷����ӣ�");
//		}
//	}
	
	
	
//	fugai.setOnTouchListener(new View.OnTouchListener() {
//		@Override
//		public boolean onTouch(View v, MotionEvent event) {
//			switch (event.getActionMasked()) {
//			case MotionEvent.ACTION_DOWN:
//				listPoint.clear();
//				Point p = new Point((int) event.getX(), (int) event.getY());
//				LatLng lal = mBaiduMap.getProjection().fromScreenLocation(p);
//				point1 = lal;
//				listPoint.add(new MyPointModel(String.valueOf(lal.latitude), String.valueOf(lal.longitude)));
//				break;
//			case MotionEvent.ACTION_MOVE:
//				p = new Point((int) event.getX(), (int) event.getY());
//				lal = mBaiduMap.getProjection().fromScreenLocation(p);
//				List<LatLng> points = new ArrayList<LatLng>();
//				points.add(point1);
//				points.add(lal);
//				OverlayOptions ooPolyline = new PolylineOptions().width(TEN).color(0xAAFF0000).points(points);
//				mBaiduMap.addOverlay(ooPolyline);
//				listPoint.add(new MyPointModel(String.valueOf(lal.latitude), String.valueOf(lal.longitude)));
//				point1 = lal;
//				break;
//			case MotionEvent.ACTION_UP:
//				mMapView.getMap().clear();
//				if (listPoint.size() >= THREE) {
//					draw(listPoint);
//				}
//				break;
//			default:
//				break;
//			}
//			return true;
//		}
//	});
//	fugai.setOnClickListener(new OnClickListener() {
//		@Override
//		public void onClick(View v) {
//		}
//	});
	
	
	/**
	 * ��ͼ�ϻ�Ȧ
	 * @param list
	 */
//	private void draw(List<MyPointModel> list) {
//		List<LatLng> pts = new ArrayList<LatLng>();
//		for (int i = 0; i < list.size(); i++) {
//			LatLng pt = new LatLng(Double.parseDouble(list.get(i).getLat()),
//					Double.parseDouble(list.get(i).getLng()));
//			pts.add(pt);
//		}
//		// �����û����ƶ���ε�Option����
//		OverlayOptions polygonOption = new PolygonOptions().points(pts).stroke(new Stroke(FIVE, 0xAA00FF00)).fillColor(0xAAFFFF00);
//		// �ڵ�ͼ����Ӷ����Option��������ʾ
//		mBaiduMap.addOverlay(polygonOption);
//	}
	
	
	
	/**
	 * �رյ���Χ������
	 */
//	private void closeWeilanLay() {
//		mMapView.getMap().clear();
//		if (!isWeilan) {
//			flag = "1";
//			index_dzwl_draw.setImageResource(R.drawable.index_dzwl_draw);
//		}
//
//		tishi.setVisibility(View.GONE);
//		fugai.setVisibility(View.GONE);
//		bottom.setVisibility(View.GONE);
//		index_weilan_layout.setVisibility(View.GONE);
//	}
//	private void weilanDialog(String msg) {
//		AlertDialog.Builder normalDia = new AlertDialog.Builder(
//				HomePageActivity.this);
//		normalDia.setTitle("��ʾ");
//		normalDia.setMessage(msg);
//		normalDia.setPositiveButton("ȷ��",
//				new DialogInterface.OnClickListener() {
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						// ToastUtil.showCenterShort(context, "Χ��������");
//						area = "";
//						for (int i = 0; i < listPoint.size(); i++) {
//							area = listPoint.get(i).getLat() + ","
//									+ listPoint.get(i).getLng() + ";" + area;
//						}
//						fenceInfo = new FenceInfoModel();
//						fenceInfo.setStartTime(startTime.getText().toString());
//						fenceInfo.setEndTime(endTime.getText().toString());
//						fenceInfo.setMonitoringType(String
//								.valueOf(weilanSelect));
//						fenceInfo.setLocationInfo(area);
//						String locationInfo = JSON.toJSONString(fenceInfo);
//						if (MyApplication.getNetObject().isNetConnected()) {
//							// �������������û�
//							if (userFriend.getPackageType().equals("4")) {
//								RequestParams params = http.getParams(WebUrlConfig
//										.setFenceStart("", "", "", ""));
//								params.addBodyParameter("userID",
//										MyApplication.userModel.getUserID());
//								params.addBodyParameter("phoneNumber",
//										userFriend.getFriendPhone());
//								params.addBodyParameter("fenceInfo",
//										locationInfo);
//								params.addBodyParameter("friendID",
//										userFriend.getFriendID());
//
//								http.sendPost(SIXTEEN,params);
//							} else {
//								RequestParams params = http.getParams(WebUrlConfig
//										.saveFence("", ""));
//								params.addBodyParameter("friendID",
//										userFriend.getFriendID());
//								params.addBodyParameter("fenceInfo",
//										locationInfo);
//
//								http.sendPost(TWELVE,params);
//							}
//						} else {
//							handler.sendEmptyMessage(11);
//						}
//					}
//				});
//		normalDia.setNegativeButton("ȡ��",
//				new DialogInterface.OnClickListener() {
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						dialog.dismiss();
//					}
//				});
//		normalDia.create().show();
//	}
	
	
	/**
	 * �жϵ��Ƿ�������
	 * @return
	 */
//	public boolean isInArea(double lat, double lng) {
//		LatLng pt = new LatLng(lat, lng);
//		boolean aaa = SpatialRelationUtil.isPolygonContainsPoint(larea, pt);
//		return aaa;
//	}
	
	
	/**
	 * ����ת��
	 * @param area
	 */
//	public void addArea(String area) {
//		String[] areas = area.split(";");
//		larea.clear();
//		for (int i = 0; i < areas.length; i++) {
//			String[] pt = areas[i].split(",");
//			double lat = Double.parseDouble(pt[0]);
//			double lng = Double.parseDouble(pt[1]);
//			LatLng pt1 = new LatLng(lat, lng);
//			larea.add(pt1);
//		}
//	}
	
	

	/**
	 * ���ü�������ص�
	 */
//	private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
//		@Override
//		public void gotResult(int code, String alias, Set<String> tags) {
//			String logs;
//			switch (code) {
//			case 0:
//				logs = "Set tag and alias success";
//				Log.i("TAG", logs);
//				break;
//			case 6002:
//				logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
//				Log.i("TAG", logs);
//				if (MyApplication.getNetObject().isNetConnected()) {
//					handler.sendMessageDelayed(
//							handler.obtainMessage(MSG_SET_ALIAS, alias),
//							1000 * 60);
//				} else {
//					ToastUtil.showCenterShort(context, "�����޷����ӣ�");
//				}
//				break;
//			default:
//				logs = "Failed with errorCode = " + code;
//			}
//			ToastUtil.showCenterShort(context, logs);
//		}
//
//	};

}
