package com.means.foods.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.means.foods.R;
import com.means.foods.base.FoodsBaseActivity;
import com.means.foods.bean.LoginEB;
import com.means.foods.bean.RegisterEB;

import de.greenrobot.event.EventBus;

public class RegisterOneActivity extends FoodsBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_one);
		EventBus.getDefault().register(this);
	}

	@Override
	public void initView() {
		setTitle("注册1/3");
		findViewById(R.id.invitation).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent it = new Intent(self, RegisterTwoActivity.class);
				startActivity(it);
			}
		});

		findViewById(R.id.login).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent it = new Intent(self, LoginActivity.class);
				startActivity(it);
			}
		});
	}

	public void onEventMainThread(RegisterEB registerEb) {
		finish();
	}
	
	public void onEventMainThread(LoginEB loginEb) {
		finish();
	}

}
