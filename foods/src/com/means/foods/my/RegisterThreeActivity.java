package com.means.foods.my;

import com.means.foods.R;
import com.means.foods.base.FoodsBaseActivity;

import android.os.Bundle;

public class RegisterThreeActivity extends FoodsBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_three);
	}

	@Override
	public void initView() {
		setTitle("注册完成");

	}

}
