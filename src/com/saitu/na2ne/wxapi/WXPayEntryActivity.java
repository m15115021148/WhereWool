package com.saitu.na2ne.wxapi;

import com.saitu.na2ne.R;
import com.sitemap.na2ne.utils.ToastUtil;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * 
 * Description: 微信支付结果处理页面
 * @author chenhao
 * @date   2016-3-11
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler{
	
    private IWXAPI api;//sdk支付对象
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wechatpay_result);
    	api = WXAPIFactory.createWXAPI(this, WechatPayConfig.getAppId());// 通过WXAPIFactory工厂，获取IWXAPI的实例
    	api.registerApp(WechatPayConfig.getAppId());
        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
		
	}
	
/**
 * 微信支付结果主动回调
 */
	@Override
	public void onResp(BaseResp resp) {
		switch (resp.errCode) {
		case BaseResp.ErrCode.ERR_OK://成功
			ToastUtil.showCenterShort(this, "支付成功！");
			break;
		case BaseResp.ErrCode.ERR_USER_CANCEL://用户取消
			ToastUtil.showCenterShort(this, "用户取消！");
		default://失败
			ToastUtil.showCenterShort(this, "支付失败！");
			break;
		}
		this.finish();
	
	}

}