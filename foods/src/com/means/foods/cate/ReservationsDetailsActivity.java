package com.means.foods.cate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.means.foods.R;
import com.means.foods.base.FoodsBaseActivity;

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
