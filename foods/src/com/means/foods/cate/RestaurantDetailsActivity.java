package com.means.foods.cate;

import com.means.foods.R;
import com.means.foods.R.layout;
import com.means.foods.base.FoodsBaseActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_restaurant_details);
	}

	@Override
	public void initView() {
		setTitle("餐厅详情");
		reservedV = findViewById(R.id.reserved);
		reservedV.setOnClickListener(this);
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
