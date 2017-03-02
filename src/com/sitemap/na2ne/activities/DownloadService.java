package com.sitemap.na2ne.activities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.saitu.na2ne.R;
import com.sitemap.na2ne.activities.NotificationUpdateActivity.ICallbackResult;
import com.sitemap.na2ne.application.MyApplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.RemoteViews;

public class DownloadService extends Service {
	private static final int NOTIFY_ID = 0;//0
	private int progress;//进度条
	private NotificationManager mNotificationManager;//状态栏
	private boolean canceled;//取消
	// 返回的安装包url
	private String apkUrl = MyApplication.downUrl;//下载地址
//	 private String apkUrl = "http:\/\/www.na2ne.com:20015\/NaErNe\/apk\/Na2Ne.apk";
	/* 下载包安装路径 */
	private static final String savePath = Environment.getExternalStorageDirectory().toString()+"/sitemap/updateApk/";

	private static final String saveFileName = savePath + "Na2Ne.apk";//apk名字
	private ICallbackResult callback;//返回
	private DownloadBinder binder;//下载服务
	private MyApplication app;//application对象
	private boolean serviceIsDestroy = false;//服务是否停止

	private Context mContext = this;//本类
	private String isforce="";
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				app.setDownload(false);
				// 下载完毕
				// 取消通知
				mNotificationManager.cancel(NOTIFY_ID);
				installApk();
				break;
			case 2:
				app.setDownload(false);
				// 这里是用户界面手动取消，所以会经过activity的onDestroy();方法
				// 取消通知
				mNotificationManager.cancel(NOTIFY_ID);
				break;
			case 1:
				int rate = msg.arg1;
				app.setDownload(true);
				if (rate < 100) {
					RemoteViews contentview = mNotification.contentView;
					contentview.setTextViewText(R.id.tv_progress, rate + "%");
					contentview.setProgressBar(R.id.progressbar, 100, rate,
							false);
				} else {
					System.out.println("下载完毕!!!!!!!!!!!");
					// 下载完毕后变换通知形式
					mNotification.flags = Notification.FLAG_AUTO_CANCEL;
					mNotification.contentView = null;
					Intent intent = new Intent(mContext,
							NotificationUpdateActivity.class);
					intent.putExtra("isforce", isforce);
					// 告知已完成
					intent.putExtra("completed", "yes");
					// 更新参数,注意flags要使用FLAG_UPDATE_CURRENT
					PendingIntent contentIntent = PendingIntent.getActivity(
							mContext, 0, intent,
							PendingIntent.FLAG_UPDATE_CURRENT);
					mNotification.setLatestEventInfo(mContext, "下载完成",
							"文件已下载完毕", contentIntent);
					//
					serviceIsDestroy = true;
					stopSelf();// 停掉服务自身
					
				}
				mNotificationManager.notify(NOTIFY_ID, mNotification);
				break;
			}
		}
	};

	//
	// @Override
	// public int onStartCommand(Intent intent, int flags, int startId) {
	// // TODO Auto-generated method stub
	// return START_STICKY;
	// }

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		System.out.println("是否执行了 onBind");
		isforce=intent.getStringExtra("isforce");
		return binder;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		System.out.println("downloadservice ondestroy");
		// 假如被销毁了，无论如何都默认取消了。
		app.setDownload(false);
	}

	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		System.out.println("downloadservice onUnbind");
		return super.onUnbind(intent);
	}

	@Override
	public void onRebind(Intent intent) {
		// TODO Auto-generated method stub

		super.onRebind(intent);
		System.out.println("downloadservice onRebind");
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		binder = new DownloadBinder();
		mNotificationManager = (NotificationManager) getSystemService(android.content.Context.NOTIFICATION_SERVICE);
		app = (MyApplication) getApplication();
	}

	/**
	 * 
	 * com.sitemap.na2ne.activities.DownloadBinder
	 * @author zhang
	 * 下载线程
	 * create at 2015年12月30日 下午5:01:16
	 */
	public class DownloadBinder extends Binder {
		/**
		 * 开始
		 */
		public void start() {
			if (downLoadThread == null || !downLoadThread.isAlive()) {

				progress = 0;
				setUpNotification();
				new Thread() {
					public void run() {
						// 下载
						startDownload();
					};
				}.start();
			}
		}
		/**
		 * 取消
		 */
		public void cancel() {
			canceled = true;
		}
		/**
		 * 进度条
		 */
		public int getProgress() {
			return progress;
		}
		/**
		 * 是否取消
		 */
		public boolean isCanceled() {
			return canceled;
		}
		/**
		 * 服务是否销毁
		 */
		public boolean serviceIsDestroy() {
			return serviceIsDestroy;
		}
		/**
		 * 取消
		 */
		public void cancelNotification() {
			mHandler.sendEmptyMessage(2);
		}
		/**
		 * 添加返回
		 */
		public void addCallback(ICallbackResult callback) {
			DownloadService.this.callback = callback;
		}
	}

	/**
	 * 开始下载
	 */
	private void startDownload() {
		// TODO Auto-generated method stub
		canceled = false;
		downloadApk();
	}

	
	Notification mNotification;// 通知栏

	
	/**
	 * 创建通知
	 */
	private void setUpNotification() {
		int icon = R.drawable.na2ne;
		CharSequence tickerText = "开始下载";
		long when = System.currentTimeMillis();
		mNotification = new Notification(icon, tickerText, when);
		;
		// 放置在"正在运行"栏目中
		mNotification.flags = Notification.FLAG_ONGOING_EVENT;

		RemoteViews contentView = new RemoteViews(getPackageName(),
				R.layout.download_notification_layout);
		contentView.setTextViewText(R.id.name, "Na2Ne.apk 正在下载...");
		// 指定个性化视图
		mNotification.contentView = contentView;

		Intent intent = new Intent(this, NotificationUpdateActivity.class);
		intent.putExtra("isforce", isforce);
		// 下面两句是 在按home后，点击通知栏，返回之前activity 状态;
		// 有下面两句的话，假如service还在后台下载， 在点击程序图片重新进入程序时，直接到下载界面，相当于把程序MAIN 入口改了 - -
		// 是这么理解么。。。
		// intent.setAction(Intent.ACTION_MAIN);
		// intent.addCategory(Intent.CATEGORY_LAUNCHER);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				intent, PendingIntent.FLAG_UPDATE_CURRENT);

		// 指定内容意图
		mNotification.contentIntent = contentIntent;
		mNotificationManager.notify(NOTIFY_ID, mNotification);
	}

	//
	/**
	 * 下载apk
	 * 
	 * @param url
	 */
	private Thread downLoadThread;

	private void downloadApk() {
		downLoadThread = new Thread(mdownApkRunnable);
		downLoadThread.start();
	}

	/**
	 * 安装apk
	 * 
	 * @param url
	 */
	private void installApk() {
		File apkfile = new File(saveFileName);
		if (!apkfile.exists()) {
			return;
		}
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
				"application/vnd.android.package-archive");
		mContext.startActivity(i);
		callback.OnBackResult("finish");

	}

	private int lastRate = 0;//进度值
	private Runnable mdownApkRunnable = new Runnable() {
		@Override
		public void run() {
			try {
				URL url = new URL(apkUrl);

				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.connect();
				int length = conn.getContentLength();
				
				InputStream is = conn.getInputStream();

				File file = new File(savePath);
				if (!file.exists()) {
					file.mkdirs();
				}
				String apkFile = saveFileName;
				File ApkFile = new File(apkFile);
				FileOutputStream fos = new FileOutputStream(ApkFile);

				int count = 0;
				byte buf[] = new byte[1024];
				MyApplication.length=length/1000;
				java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00");  
				df.format(MyApplication.length);
				do {
					int numread = is.read(buf);
					count += numread;
					progress = (int) (((float) count / length) * 100);
					// 更新进度
					Message msg = mHandler.obtainMessage();
					msg.what = 1;
					msg.arg1 = progress;
					if (progress >= lastRate + 1) {
						mHandler.sendMessage(msg);
						lastRate = progress;
						if (callback != null)
							callback.OnBackResult(progress);
					}
					if (numread <= 0) {
						// 下载完成通知安装
						mHandler.sendEmptyMessage(0);
						// 下载完了，cancelled也要设置
						canceled = true;
						break;
					}
					fos.write(buf, 0, numread);
				} while (!canceled);// 点击取消就停止下载.

				fos.close();
				is.close();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	};

}
