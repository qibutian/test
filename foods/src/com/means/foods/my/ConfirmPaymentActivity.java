package com.means.foods.my;

import net.duohuo.dhroid.net.DhNet;
import net.duohuo.dhroid.net.NetTask;
import net.duohuo.dhroid.net.Response;
import net.duohuo.dhroid.util.ViewUtil;

import org.json.JSONException;
import org.json.JSONObject;

import com.means.foods.R;
import com.means.foods.R.layout;
import com.means.foods.api.API;
import com.means.foods.api.Constant;
import com.means.foods.base.FoodsBaseActivity;
import com.means.foods.bean.User;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.Toast;

/**
 * 确认支付
 * 
 * @author Administrator
 * 
 */
public class ConfirmPaymentActivity extends FoodsBaseActivity {

	private IWXAPI api;

	CheckBox checkC;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_confirm_payment);
		api = WXAPIFactory.createWXAPI(this, Constant.WX_APP_KEY);
	}

	@Override
	public void initView() {
		setTitle("确认详情");
		Intent it = getIntent();
		ViewUtil.bindView(findViewById(R.id.name), it.getStringExtra("name"));
		ViewUtil.bindView(findViewById(R.id.price),
				it.getDoubleExtra("price", 0));
		checkC = (CheckBox) findViewById(R.id.check);
		findViewById(R.id.pay).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (checkC.isChecked()) {
					pay(getIntent().getStringExtra("order_id"));
				} else {
					showToast("请选择付款方式!");
				}
			}
		});
	}

	private void pay(String orderid) {

		DhNet net = new DhNet(API.pay);
		net.addParam("uid ", User.getInstance().getUid());
		net.addParam("token  ", User.getInstance().getToken());
		net.addParam("order_id ", orderid);
		net.doPostInDialog(new NetTask(self) {

			@Override
			public void doInUI(Response response, Integer transfer) {
				if (response.isSuccess()) {
					JSONObject json = response.jSONFromData();
					PayReq req = new PayReq();
					// req.appId = "wxf8b4f85f3a794e77"; // 测试用appId
					try {
						req.appId = json.getString("appid");
						req.partnerId = json.getString("partnerid");
						req.prepayId = json.getString("prepayid");
						req.nonceStr = json.getString("noncestr");
						req.timeStamp = json.getString("timestamp");
						req.packageValue = json.getString("package");
						req.sign = json.getString("sign");
						req.extData = "app data"; // optional
						Toast.makeText(self, "正常调起支付", Toast.LENGTH_SHORT)
								.show();
						// 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
						api.sendReq(req);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		});

	}
}
