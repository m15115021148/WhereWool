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
 * Description: ΢��֧���������ҳ��
 * @author chenhao
 * @date   2016-3-11
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler{
	
    private IWXAPI api;//sdk֧������
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wechatpay_result);
    	api = WXAPIFactory.createWXAPI(this, WechatPayConfig.getAppId());// ͨ��WXAPIFactory��������ȡIWXAPI��ʵ��
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
 * ΢��֧����������ص�
 */
	@Override
	public void onResp(BaseResp resp) {
		switch (resp.errCode) {
		case BaseResp.ErrCode.ERR_OK://�ɹ�
			ToastUtil.showCenterShort(this, "֧���ɹ���");
			break;
		case BaseResp.ErrCode.ERR_USER_CANCEL://�û�ȡ��
			ToastUtil.showCenterShort(this, "�û�ȡ����");
		default://ʧ��
			ToastUtil.showCenterShort(this, "֧��ʧ�ܣ�");
			break;
		}
		this.finish();
	
	}

}