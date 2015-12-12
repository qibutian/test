package com.means.foods.my;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import net.duohuo.dhroid.net.DhNet;
import net.duohuo.dhroid.net.NetTask;
import net.duohuo.dhroid.net.Response;
import net.duohuo.dhroid.util.ViewUtil;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.means.foods.R;
import com.means.foods.R.layout;
import com.means.foods.api.API;
import com.means.foods.api.Constant;
import com.means.foods.base.FoodsBaseActivity;
import com.means.foods.bean.User;
import com.means.foods.utils.MD5;
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

	private PayReq req;

	private StringBuffer sb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_confirm_payment);
		api = WXAPIFactory.createWXAPI(this, Constant.WX_APP_KEY);
		api.registerApp(Constant.WX_APP_KEY);
	}

	@Override
	public void initView() {
		setTitle("确认详情");
		Intent it = getIntent();
		ViewUtil.bindView(findViewById(R.id.name), it.getStringExtra("name"));
		ViewUtil.bindView(findViewById(R.id.price),
				"￥"+it.getDoubleExtra("price", 0));
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

	private void pay(final String orderid) {

		DhNet net = new DhNet(API.pay);
		net.addParam("uid ", User.getInstance().getUid());
		net.addParam("token  ", User.getInstance().getToken());
		net.addParam("order_id ", orderid);
		net.doPostInDialog(new NetTask(self) {

			@Override
			public void doInUI(Response response, Integer transfer) {
				if (response.isSuccess()) {
					JSONObject json = response.jSONFromData();
					sb = new StringBuffer();
					req = new PayReq();
					// req.appId = "wxf8b4f85f3a794e77"; // 测试用appId
					try {
						genPayReq(json.getString("prepayId"));
						// req.appId = json.getString("appId");
						// req.partnerId = json.getString("partnerId");
						// req.prepayId = json.getString("prepayId");
						// req.nonceStr = json.getString("nonceStr");
						// req.timeStamp = json.getString("timeStamp");
						// req.packageValue = json.getString("package");
						// req.sign = json.getString("paySign");
						api.sendReq(req);
						Toast.makeText(self, "正常调起支付", Toast.LENGTH_SHORT)
								.show();
						// 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		});

	}

	private void genPayReq(String prepayId) {
		req.appId = Constant.WX_APP_KEY;
		req.partnerId = Constant.partnerId;
		req.prepayId = prepayId;
		req.packageValue = "Sign=WXPay";
		req.nonceStr = genNonceStr();
		req.timeStamp = String.valueOf(genTimeStamp());
		// req.extData = "android data";

		List<BasicNameValuePair> signParams = new LinkedList<BasicNameValuePair>();
		signParams.add(new BasicNameValuePair("appid", req.appId));
		signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
		signParams.add(new BasicNameValuePair("package", req.packageValue));
		signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
		signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
		signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));
		// signParams.add(new BasicNameValuePair("signType", "MD5"));
		req.sign = genAppSign(signParams);
		sb.append("sign\n" + req.sign + "\n\n");
		api.sendReq(req);
	}

	private String genAppSign(List<BasicNameValuePair> params) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < params.size(); i++) {
			sb.append(params.get(i).getName());
			sb.append('=');
			sb.append(params.get(i).getValue());
			sb.append('&');
		}
		sb.append("key=");
		sb.append(Constant.WX_API_SECRET);
		this.sb.append("sign str\n" + sb.toString() + "\n\n");
		String appSign = MD5.getMessageDigest(sb.toString().getBytes())
				.toUpperCase(Locale.CHINA);
		return appSign;
	}

	/**
	 * 随机数
	 * 
	 * @return
	 */
	private String genNonceStr() {
		Random random = new Random();
		return MD5.getMessageDigest(String.valueOf(random.nextInt(10000))
				.getBytes());
	}

	/**
	 * 时间戳
	 * 
	 * @return
	 */
	private long genTimeStamp() {
		return System.currentTimeMillis() / 1000;
	}
}
