package com.means.foods.cate;

import org.json.JSONObject;

import net.duohuo.dhroid.net.DhNet;
import net.duohuo.dhroid.net.NetTask;
import net.duohuo.dhroid.net.Response;

import com.means.foods.R;
import com.means.foods.R.layout;
import com.means.foods.api.API;
import com.means.foods.base.FoodsBaseActivity;
import com.means.foods.bean.User;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * 餐厅详情
 * 
 * @author dell
 * 
 */
public class RestaurantDetailsActivity extends FoodsBaseActivity implements
		OnClickListener {

	// 立即预定按钮
	View reservedV;

	// 餐厅id
	String store_id;
	/**
	 * percapitaT人均，
	 */
	TextView percapitaT;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_restaurant_details);
	}

	@Override
	public void initView() {
		store_id = getIntent().getStringExtra("store_id");
		setTitle("餐厅详情");
		reservedV = findViewById(R.id.reserved);
		reservedV.setOnClickListener(this);

		initData();
	}

	private void initData() {
		DhNet net = new DhNet(API.restaurantDetail);
		net.addParam("store_id", store_id);
		net.addParam("uid", User.getInstance().getUid());
		net.doGet(new NetTask(self) {

			@Override
			public void doInUI(Response response, Integer transfer) {

				if (response.isSuccess()) {
					JSONObject jo=response.jSONFromData();
				}

			}
		});
	}

	@Override
	public void onClick(View v) {
		Intent it;
		switch (v.getId()) {
		case R.id.reserved:
			it = new Intent(self, ConfirmDetailsActivity.class);
			startActivity(it);
			break;

		default:
			break;
		}
	}
}
