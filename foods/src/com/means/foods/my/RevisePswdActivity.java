package com.means.foods.my;

import android.os.Bundle;

import com.means.foods.R;
import com.means.foods.base.FoodsBaseActivity;

public class RevisePswdActivity extends FoodsBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_revise_pswd);
	}

	@Override
	public void initView() {
		setTitle("修改密码");
	}

}
