package com.means.foods.cate;

import android.os.Bundle;

import com.means.foods.R;
import com.means.foods.base.FoodsBaseActivity;

/**
 * 预定详情
 * @author dell
 *
 */
public class ReservationsDetailsActivity extends FoodsBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reservations_details);
	}

	@Override
	public void initView() {
		setTitle("预定详情");
		
	}
}
