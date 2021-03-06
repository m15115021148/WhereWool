package com.sitemap.na2ne.activities;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

import org.json.JSONObject;
import org.xutils.http.RequestParams;

import com.alibaba.fastjson.JSON;
import com.saitu.na2ne.R;
import com.sitemap.na2ne.application.MyApplication;
import com.sitemap.na2ne.config.RequestCode;
import com.sitemap.na2ne.config.WebUrlConfig;
import com.sitemap.na2ne.http.HttpUtil;
import com.sitemap.na2ne.models.CodeModel;
import com.sitemap.na2ne.utils.ToastUtil;
import com.sitemap.na2ne.utils.ImageFactory;
import com.sitemap.na2ne.views.MyProgressDialog;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 意见页面
 * 
 * @author zhang create at 2015年12月24日
 */
public class YiJianActivity extends Activity implements OnClickListener {
	public static YiJianActivity context;// 本类
	private LinearLayout base_back_lay;// 返回键
	private TextView back;// 回退
	private EditText yijian_title;// 意见标题
	private EditText yijian_context;// 意见内容
	private EditText yijian_phone;// 意见电话
	private ImageView pic1, pic2, pic3;// 图片1、2、3
	private TextView add_pic;// 添加图片
	private TextView yijian_sub;// 提交
	private static int pic = 1;// 第几张
	private static MyProgressDialog progressDialog;// 进度条
	private HttpUtil http = null;//网络请求帮助类对象
	private final int ZERO = 0, ONE = 1, TWO = 2;// 0.1.2
	private String url1, url2, url3;// 3张图片
	private Bitmap bt1,bt2,bt3;//3个图片
	private static final String savePath = Environment.getExternalStorageDirectory().toString()+"/sitemap/";//文件路径

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// 隐藏应用程序的标题栏，即当前activity的标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_yijian);
		progressDialog = MyProgressDialog.createDialog(this);
		context = this;
		if(http == null){
			http = new HttpUtil(handler);
		}
		if (MyApplication.isLogin<0) {
			Intent intent=new Intent(YiJianActivity.this,HomePageActivity.class);
			startActivity(intent);
			finish();
		}
		initview();
	}

	@Override
	public void onResume() {
		super.onResume();
		// 友盟统计
		MobclickAgent.onResume(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		// 友盟统计
		MobclickAgent.onPause(this);
	}

	private final Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
//			if (progressDialog != null && progressDialog.isShowing()) {
//				progressDialog.dismiss();// 关闭进度条
//			}
			switch (msg.what) {
			case HttpUtil.SUCCESS:
				// 获得功能页面
				if (msg.arg1 == TWO) {
					if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();// 关闭进度条
                    }
					if (msg.obj.toString() != null) {
						int status = -1;
						CodeModel cm=JSON.parseObject(msg.obj.toString(), CodeModel.class);
						if (cm!=null) {
							status=cm.getStatus();
						}
						switch (status) {
						case ZERO:
							ToastUtil.showCenterShort(context, "提交反馈成功！");
							pic=1;
							context.finish();
							break;
						case ONE:
							ToastUtil.showCenterShort(context, cm.getErrorMessage());
							break;
						case TWO:
							ToastUtil.showCenterShort(context, RequestCode.NULL);
							break;
						default:
							break;
						}
					}
				} else {
					ToastUtil.showCenterShort(context, RequestCode.NULL);
				}

				break;
			case HttpUtil.EMPTY:
				if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();// 关闭进度条
                }
				// 返回数据为null
				if (msg.arg1 == ONE) {
					ToastUtil.showCenterShort(context, RequestCode.NULL);
				}
				if (msg.arg1 == TWO) {
					ToastUtil.showCenterShort(context, RequestCode.NULL);
				}		
				break;
			case HttpUtil.FAILURE:
				if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();// 关闭进度条
                }
				ToastUtil.showCenterShort(context, RequestCode.ERRORINFO);
				break;
			case HttpUtil.LOADING:
				if(msg.arg1 == TWO){
					progressDialog.setMessage(msg.arg2 + "%");
				}				
				break;
			case 11:
				switch (pic) {
				case 2:
						url1="";
						pic=1;
						pic1.setVisibility(View.GONE);
					break;
				case 3:
						url1=url2;
						url2="";
						pic=2;
						pic1.setImageBitmap(bt2);
						pic2.setVisibility(View.GONE);
					break;
				case 4:
						url1=url2;
						url2=url3;
						url3="";
						pic=3;
						pic1.setImageBitmap(bt2);
						pic2.setImageBitmap(bt3);
						pic3.setVisibility(View.GONE);
					break;
				default:
					break;
				}
				break;
				
				
			case 12:
				switch (pic) {
				case 3:
						url2="";
						pic=2;
						pic2.setVisibility(View.GONE);
					break;
				case 4:
						url2=url3;
						url3="";
						pic=3;
						pic2.setImageBitmap(bt3);
						pic3.setVisibility(View.GONE);
					break;
				default:
					break;
				}
				
			case 13:
				switch (pic) {
				case 4:
						url3="";
						pic=3;
						pic3.setVisibility(View.GONE);
					break;
				default:
					break;
				}
				break;
			default:
				break;
		}

		}

	};

	/**
	 * 初始化控件
	 */
	private void initview() {
		back = (TextView) findViewById(R.id.back_tv);
		back.setOnClickListener(this);
		base_back_lay = (LinearLayout) findViewById(R.id.base_back_lay);
		base_back_lay.setOnClickListener(this);
		yijian_sub = (TextView) findViewById(R.id.yijian_sub);
		yijian_sub.setOnClickListener(this);
		add_pic = (TextView) findViewById(R.id.add_pic);
		add_pic.setOnClickListener(this);
		pic1 = (ImageView) findViewById(R.id.pic1);
		pic2 = (ImageView) findViewById(R.id.pic2);
		pic3 = (ImageView) findViewById(R.id.pic3);
		pic1.setOnClickListener(this);
		pic2.setOnClickListener(this);
		pic3.setOnClickListener(this);
		yijian_title = (EditText) findViewById(R.id.yijian_title);
		yijian_context = (EditText) findViewById(R.id.yijian_context);
		yijian_phone = (EditText) findViewById(R.id.yijian_phone);
	}

	@Override
	public void onClick(View v) {
		if (v==pic1) {
			delImg(11);
		}
		if (v==pic2) {
			delImg(12);
		}
		if (v==pic3) {
			delImg(13);
		}
		if (v == base_back_lay) {
			pic=1;
			finish();
		}
		if (v == back) {
			pic=1;
			finish();
		}
		if (v == add_pic) {
			if (pic == 4) {
				ToastUtil.showCenterShort(this, "最多只能上传3张图片！");
				return;
			}
			Intent intent = new Intent(
					Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(intent, 0);
		}
		if (v == yijian_sub) {
			if (yijian_title.getText().toString().trim().equals("")
					|| yijian_title.getText().toString().trim() == null) {
				ToastUtil.showCenterShort(this, "标题不能为空！");
				return;
			}
			if (yijian_context.getText().toString().trim().equals("")
					|| yijian_context.getText().toString().trim() == null) {
				ToastUtil.showCenterShort(this, "内容不能为空！");
				return;
			}
			showNormalDia();
			
		}
	}

	/**
	 * 对话框
	 */
	private void showNormalDia() {
		// AlertDialog.Builder normalDialog=new
		// AlertDialog.Builder(getApplicationContext());
		AlertDialog.Builder normalDia = new AlertDialog.Builder(
				YiJianActivity.this);
		normalDia.setTitle("提示");
		normalDia.setMessage("是否要提交反馈？");

		normalDia.setPositiveButton("确定",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (progressDialog != null && !progressDialog.isShowing()) {
							progressDialog.setMessage("提交中...");
							progressDialog.show();
						}

						String url = WebUrlConfig.toAdvice();
						RequestParams params = http.getParams(url);
						params.addBodyParameter("userID",
								MyApplication.userModel.getUserID());
						params.addBodyParameter("adviceTitle", yijian_title.getText()
								.toString().trim());
						params.addBodyParameter("adviceInfo", yijian_context.getText()
								.toString().trim());
						params.addBodyParameter("linkphone", yijian_phone.getText()
								.toString().trim());
						params.addBodyParameter("versionCode",MyApplication.versionName);
						switch (pic) {
						case 2:
							Log.i("TAG", "pic"+pic);
							File file1 = new File(url1);
							params.addBodyParameter("img1", file1);
							break;
						case 3:
							Log.i("TAG", "pic"+pic);
							File file21 = new File(url1);
							params.addBodyParameter("img1", file21);
							File file22 = new File(url2);
							params.addBodyParameter("img2", file22);
							break;
						case 4:
							Log.i("TAG", "pic"+pic);
							File file31 = new File(url1);
							params.addBodyParameter("img1", file31);
							File file32 = new File(url2);
							params.addBodyParameter("img2", file32);
							File file33 = new File(url3);
							params.addBodyParameter("img3", file33);
							break;
						default:
							break;
						}
//						File file1 = new File(url1);
//						httpUtil.addRequestParams("img1", file1);
						http.uploadFile(TWO,params);

					}
				});
		normalDia.setNegativeButton("取消",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		normalDia.create().show();
	}

	/**
	 * 删除图片
	 */
	private void delImg(final int index) {
		AlertDialog.Builder normalDia = new AlertDialog.Builder(
				YiJianActivity.this);
		normalDia.setTitle("提示");
		normalDia.setMessage("是否要取消上传该图片？");

		normalDia.setPositiveButton("确定",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						handler.sendEmptyMessage(index);
						dialog.dismiss();
					}
				});
		normalDia.setNegativeButton("取消",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		normalDia.create().show();
	}
	
	
	/**
	 * 解码图片
	 * 
	 * @param uri
	 */
	private void bitmapFactory(Uri uri, int pic) {
		BitmapFactory.Options options = new BitmapFactory.Options();
//		 options.inSampleSize = 8;//设置生成的图片为原图的八分之一
		options.inJustDecodeBounds = true;// 这将通知
											// BitmapFactory类只须返回该图像的范围,而无须尝试解码图像本身
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width = dm.widthPixels;
		int height = dm.heightPixels;
		int heightRadio = (int) Math.ceil(options.outHeight / (float) height);
		int widthRadio = (int) Math.ceil(options.outWidth / (float) width);
		if (heightRadio > 1 && widthRadio > 1) {
			if (heightRadio > widthRadio) {
				options.inSampleSize = heightRadio;
			} else {
				options.inSampleSize = widthRadio;
			}
		}
		// 真正解码图片
		options.inJustDecodeBounds = false;
		Bitmap b;
		try {
			b = BitmapFactory.decodeStream(getContentResolver()
					.openInputStream(uri), null, options);
//			b=ImageFactory.ratio(b, 480f, 800f);
			
			switch (pic) {
			case 1:
				pic1.setVisibility(View.VISIBLE);
				bt1=ImageFactory.ratio(b, 480f, 800f);
				pic1.setImageBitmap(bt1);
//			    ByteArrayOutputStream baos = new ByteArrayOutputStream();       
//			    ByteArrayOutputStream baos1 = new ByteArrayOutputStream(); 
//			    b.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//			    bt1.compress(Bitmap.CompressFormat.JPEG, 100, baos1);
//			    Log.i("TAG", baos.toByteArray().length+":b");
//			    Log.i("TAG", baos1.toByteArray().length+":bt1");
			    saveBitmapFile(bt1,url1);
				YiJianActivity.pic = 2;
				break;
			case 2:
				pic2.setVisibility(View.VISIBLE);
				bt2=ImageFactory.ratio(b, 480f, 800f);
				pic2.setImageBitmap(bt2);
				saveBitmapFile(bt2,url2);
				YiJianActivity.pic = 3;
				
				break;
			case 3:
				pic3.setVisibility(View.VISIBLE);
				bt3=ImageFactory.ratio(b, 480f, 800f);
				pic3.setImageBitmap(bt3);
				saveBitmapFile(bt3,url3);
				YiJianActivity.pic = 4;
				break;
			default:
				break;
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_CANCELED) {
			return;
		}
		Log.i("TAG", 0 + "");
		if (requestCode == 0) {
			Uri uri = data.getData();
			switch (pic) {
			case 1:
				url1 = getRealPathFromURI(uri);
//				Log.i("TAG",url1);
				bitmapFactory(uri, pic);
				break;
			case 2:
				url2 = getRealPathFromURI(uri);
				bitmapFactory(uri, pic);
				break;
			case 3:
				url3 = getRealPathFromURI(uri);
				bitmapFactory(uri, pic);
				break;

			default:
				break;
			}

		}
	}

	/**
	 * 获得真实地址
	 * @param contentUri
	 * @return
	 */
	public String getRealPathFromURI(Uri contentUri) {
		String res = null;
		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor cursor = getContentResolver().query(contentUri, proj, null,
				null, null);
		if (cursor.moveToFirst()) {
			;
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			res = cursor.getString(column_index);
		}
		cursor.close();
		return res;
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			pic=1;
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	/**
	 * 将bitmap转文件
	 * @param bitmap
	 */
	public void saveBitmapFile(Bitmap bitmap,String url){
		Log.i("TAG", "aaaaaaaaaaaaaa");
        File file=new File(url);//将要保存图片的路径
        if (!file.isFile()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
        try {
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                bos.flush();
                bos.close();
        } catch (IOException e) {
                e.printStackTrace();
        }
        
}
	static boolean saveBitmap2file(Bitmap bmp){ 
		CompressFormat format= Bitmap.CompressFormat.JPEG; 
		int quality = 100; 
		OutputStream stream = null; 
		try { 
		stream = new FileOutputStream(savePath+"img1.jpg"); 
		} catch (FileNotFoundException e) { 
		// TODO Auto-generated catch block 
		e.printStackTrace(); 
		} 

		return bmp.compress(format, quality, stream); 
		}
}
