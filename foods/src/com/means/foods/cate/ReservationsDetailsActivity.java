package com.means.foods.cate;

import net.duohuo.dhroid.net.DhNet;
import net.duohuo.dhroid.net.JSONUtil;
import net.duohuo.dhroid.net.NetTask;
import net.duohuo.dhroid.net.Response;
import net.duohuo.dhroid.util.ViewUtil;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.means.foods.FoodsValueFix;
import com.means.foods.R;
import com.means.foods.api.API;
import com.means.foods.base.FoodsBaseActivity;
import com.means.foods.bean.User;
import com.means.foods.view.TouchWebView;

/**
 * 预定详情
 * 
 * @author dell
 * 
 */
public class ReservationsDetailsActivity extends FoodsBaseActivity implements
		OnClickListener {

	// 立即预定按钮
	View reservedV;

	JSONObject jo;

	Button cancleB, editB;
	
	TouchWebView webV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reservations_details);
	}

	@Override
	public void initView() {
		setTitle("预定详情");
		reservedV = findViewById(R.id.reserved);
		reservedV.setOnClickListener(this);
		webV = (TouchWebView) findViewById(R.id.web);
		webV.getSettings().setDefaultTextEncodingName("UTF-8");
		webV.getSettings().setJavaScriptEnabled(true);
		String intentjo = getIntent().getStringExtra("jo");
		try {
			jo = new JSONObject(intentjo);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		webV.loadUrl("http://www.foodies.im/wap.php?g=Wap&c=Food&a=map&address="
				+ JSONUtil.getString(jo, "address"));
		ViewUtil.bindView(findViewById(R.id.name),
				JSONUtil.getString(jo, "store_name"));
		ViewUtil.bindView(findViewById(R.id.order_id),
				"订单号  : " + JSONUtil.getString(jo, "order_id"));
		String arrtime = FoodsValueFix.getStandardTime(
				Long.parseLong(JSONUtil.getString(jo, "arrive_time")),
				"yyyy年MM月dd");
		ViewUtil.bindView(findViewById(R.id.date),
				arrtime + JSONUtil.getString(jo, "hour"));
		ViewUtil.bindView(findViewById(R.id.address),
				JSONUtil.getString(jo, "store_address"));
		ViewUtil.bindView(findViewById(R.id.tel),
				JSONUtil.getString(jo, "store_phone"));
		ViewUtil.bindView(findViewById(R.id.tuijian),
				JSONUtil.getString(jo, "store_feature"));
		ViewUtil.bindView(findViewById(R.id.tips),
				JSONUtil.getString(jo, "store_tips"));

		cancleB = (Button) findViewById(R.id.cancle);
		editB = (Button) findViewById(R.id.edit);
		cancleB.setOnClickListener(this);
		editB.setOnClickListener(this);
		// ViewUtil.bindView(findViewById(R.id.name), JSONUtil.getString(jo,
		// "store_name"));

	}

	@Override
	public void onClick(View v) {
		Intent it;
		switch (v.getId()) {
		case R.id.reserved:
			it = new Intent(self, ConfirmDetailsActivity.class);
			startActivity(it);
			break;

		case R.id.cancel:
			cancleOrder(JSONUtil.getString(jo, "order_id"));
			break;

		case R.id.edit:
			it = new Intent(self, ConfirmDetailsActivity.class);
			startActivity(it);
			break;

		default:
			break;
		}
	}

	private void cancleOrder(String orderId) {
		User user = User.getInstance();
		DhNet net = new DhNet(API.ordercancle);
		net.addParam("orderId", orderId);
		net.addParam("token", user.getToken());
		net.addParam("uid", user.getUid());
		net.doPostInDialog(new NetTask(self) {

			@Override
			public void doInUI(Response response, Integer transfer) {
				if (response.isSuccess()) {
					showToast("取消成功");
					finish();
				}
			}
		});
	}
}
