package com.means.foods.main;

import com.means.foods.R;
import com.means.foods.base.FoodsBaseActivity;
import com.means.foods.bean.LoginEB;
import com.means.foods.bean.RegisterEB;
import com.means.foods.my.LoginActivity;
import com.means.foods.my.EditinfoActivity;
import com.means.foods.my.RegisterOneActivity;

import de.greenrobot.event.EventBus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class ReadyActivity extends FoodsBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ready);
		EventBus.getDefault().register(this);
	}

	@Override
	public void initView() {
		setTitle("食客");

		findViewById(R.id.login).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent it = new Intent(self, LoginActivity.class);
				startActivity(it);
			}
		});

		findViewById(R.id.register).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent it = new Intent(self, RegisterOneActivity.class);
				startActivity(it);
			}
		});
	}

	public void onEventMainThread(LoginEB loginEb) {
		finish();
	}

	public void onEventMainThread(RegisterEB registerEb) {
		finish();
	}

}