package com.means.foods.main;

import com.means.foods.R;
import com.means.foods.base.FoodsBaseActivity;

import android.os.Bundle;

public class SplashActivity extends FoodsBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
	}

	@Override
	public void initView() {
		setTitle("食客");
	}

}
