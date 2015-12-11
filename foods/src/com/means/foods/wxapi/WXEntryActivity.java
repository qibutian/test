package com.means.foods.wxapi;

import net.duohuo.dhroid.dialog.IDialog;
import net.duohuo.dhroid.ioc.IocContainer;

import com.means.foods.api.Constant;
import com.means.foods.base.FoodsBaseActivity;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import android.app.Activity;
import android.os.Bundle;

public class WXEntryActivity extends FoodsBaseActivity implements
		IWXAPIEventHandler {
	// IWXAPI 是第三方app和微信通信的openapi接口
	private IWXAPI api;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		api = WXAPIFactory.createWXAPI(this, Constant.WX_APP_KEY, false);
		api.handleIntent(getIntent(), this);
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onReq(BaseReq arg0) {
	}

	@Override
	public void onResp(BaseResp resp) {
		// LogManager.show(TAG, "resp.errCode:" + resp.errCode + ",resp.errStr:"
		// + resp.errStr, 1);
		dialoger = IocContainer.getShare().get(IDialog.class);
		switch (resp.errCode) {
		case BaseResp.ErrCode.ERR_OK:
			showToast("分享成功!");
			finish();
			// 分享成功
			break;
		case BaseResp.ErrCode.ERR_USER_CANCEL:
			showToast("分享取消!");
			finish();
			// 分享取消
			break;
		case BaseResp.ErrCode.ERR_AUTH_DENIED:
			showToast("分享拒绝!");
			finish();
			// 分享拒绝
			break;
		}
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub

	}
}
