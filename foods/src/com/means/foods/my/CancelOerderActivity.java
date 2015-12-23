package com.means.foods.my;

import net.duohuo.dhroid.net.DhNet;
import net.duohuo.dhroid.net.NetTask;
import net.duohuo.dhroid.net.Response;
import net.duohuo.dhroid.util.ViewUtil;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.means.foods.R;
import com.means.foods.api.API;
import com.means.foods.base.FoodsBaseActivity;
import com.means.foods.bean.CancelOrderEB;
import com.means.foods.bean.User;

import de.greenrobot.event.EventBus;

public class CancelOerderActivity extends FoodsBaseActivity {

	String order_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cancel_order);
	}

	@Override
	public void initView() {
		setTitle("取消订单");
		order_id = getIntent().getStringExtra("order_id");
		ViewUtil.bindView(findViewById(R.id.order_id), order_id);
		ViewUtil.bindView(findViewById(R.id.price),
				getIntent().getStringExtra("price") + "元");

		findViewById(R.id.cancel).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				cancleOrder();
			}
		});
	}

	private void cancleOrder() {

		User user = User.getInstance();
		DhNet net = new DhNet(API.ordercancle);
		net.addParam("orderId", order_id);
		net.addParam("token", user.getToken());
		net.addParam("uid", user.getUid());
		net.doPostInDialog(new NetTask(self) {

			@Override
			public void doInUI(Response response, Integer transfer) {
				if (response.isSuccess()) {
					showToast("取消成功");
					EventBus.getDefault().post(new CancelOrderEB());
					finish();
				}
			}
		});
	}

}
