package com.means.foods.my;

import android.os.Bundle;

import com.means.foods.R;
import com.means.foods.base.FoodsBaseActivity;

public class ReviseEmailActivity extends FoodsBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_revise_email);
	}

	@Override
	public void initView() {
		setTitle("修改邮箱");
	}
}
